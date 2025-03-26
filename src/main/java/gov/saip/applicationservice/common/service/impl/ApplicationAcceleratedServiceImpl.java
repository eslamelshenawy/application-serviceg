package gov.saip.applicationservice.common.service.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.RequestActivityLogEnum;
import gov.saip.applicationservice.common.mapper.ApplicationAcceleratedMapper;
import gov.saip.applicationservice.common.model.ApplicationAccelerated;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LkFastTrackExaminationTargetArea;
import gov.saip.applicationservice.common.repository.ApplicationAcceleratedRepository;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.ApplicationAcceleratedService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.util.SupportServiceActivityLogHelper;
import gov.saip.applicationservice.common.validators.ApplicationAcceleratedValidator;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApplicationAcceleratedServiceImpl implements ApplicationAcceleratedService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationAcceleratedServiceImpl.class);

    private final ApplicationAcceleratedRepository applicationAcceleratedRepository;

    private final ApplicationAcceleratedMapper applicationAcceleratedMapper;

    private final ApplicationAcceleratedValidator applicationAcceleratedValidator;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final DocumentsService documentsService;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final BPMCallerServiceImpl bpmCallerService;

    @Autowired
    @Lazy
    private SupportServiceActivityLogHelper supportServiceActivityLogHelper;

    public static final String APPLICATION_ACCELERATED_LOG_TASK_NAME_AR = "طلب مسار سريع";
    public static final String APPLICATION_ACCELERATED_LOG_TASK_NAME_EN = "Application Accelerated Request";

    public ApplicationAcceleratedServiceImpl(ApplicationAcceleratedRepository applicationAcceleratedRepository, ApplicationAcceleratedMapper applicationAcceleratedMapper, ApplicationAcceleratedValidator applicationAcceleratedValidator, BPMCallerFeignClient bpmCallerFeignClient, DocumentsService documentsService, ApplicationInfoRepository applicationInfoRepository, BPMCallerServiceImpl bpmCallerService) {
        this.applicationAcceleratedRepository = applicationAcceleratedRepository;
        this.applicationAcceleratedMapper = applicationAcceleratedMapper;
        this.applicationAcceleratedValidator = applicationAcceleratedValidator;
        this.bpmCallerFeignClient = bpmCallerFeignClient;
        this.documentsService = documentsService;
        this.applicationInfoRepository = applicationInfoRepository;
        this.bpmCallerService = bpmCallerService;
    }

    public Long addOrUpdateApplicationAccelerated(ApplicationAcceleratedDto applicationAcceleratedDto) {
        try {
            ApplicationAccelerated applicationAccelerated = toApplicationAccelerated(applicationAcceleratedDto);
            if (applicationAccelerated.getId() == null) {
                applicationAcceleratedValidator.validateApplicationAccelerated(applicationAccelerated);
                applicationAccelerated.setRefused(false);
                applicationAcceleratedRepository.save(applicationAccelerated);
                addActivityLogWithAcceleratedRequest(applicationAcceleratedDto);

                // here should i reterive the timer events and complete them
                String processRequestTypeCode = ApplicationCategoryEnum.valueOf(applicationInfoRepository.findCategoryByApplicationId(applicationAcceleratedDto.getApplicationInfoId())).getProcessTypeCode();
                String processInstanceId = bpmCallerFeignClient.getProcessInstanceByRowIdIfExist(applicationAcceleratedDto.getApplicationInfoId(),processRequestTypeCode).getPayload();
                if(Objects.nonNull(processInstanceId)){
                    List<JobDto> timers = bpmCallerFeignClient.getAllJobs(processInstanceId,"Event_1wxt92h");
                    if(Objects.nonNull(timers)&&!timers.isEmpty()){
                        LocalDateTime localDateTime = LocalDateTime.now().minusDays(3);
                        ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes(0, 0);
                        OffsetDateTime outputOffsetDateTime = localDateTime.atOffset(zoneOffset);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                        String outputDateString = outputOffsetDateTime.format(formatter);
                        DueDateDto dueDateDto = new DueDateDto();
                        dueDateDto.setDuedate(outputDateString);
                        dueDateDto.setCascade(false);
                        bpmCallerFeignClient.setJobDueDate(timers.get(0).getId(), dueDateDto);
                    }
                    timers = bpmCallerFeignClient.getAllJobs(processInstanceId,"Event_0gx9lwf");
                    if(Objects.nonNull(timers)&&!timers.isEmpty()){
                        LocalDateTime localDateTime = LocalDateTime.now().minusDays(3);
                        ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes(0, 0);
                        OffsetDateTime outputOffsetDateTime = localDateTime.atOffset(zoneOffset);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                        String outputDateString = outputOffsetDateTime.format(formatter);
                        DueDateDto dueDateDto = new DueDateDto();
                        dueDateDto.setDuedate(outputDateString);
                        dueDateDto.setCascade(false);
                        bpmCallerFeignClient.setJobDueDate(timers.get(0).getId(), dueDateDto);
                    }

                }


            } else {
                Optional<ApplicationAccelerated> existingApplicationAccelerated = applicationAcceleratedRepository.findById(applicationAccelerated.getId());
                if (existingApplicationAccelerated.isPresent()) {
                    ApplicationAccelerated existingAppAcc = existingApplicationAccelerated.get();
                    if (!existingAppAcc.getApplicationInfo().getId().equals(applicationAccelerated.getApplicationInfo().getId())) {
                        throw new BusinessException(Constants.ErrorKeys.APP_INFO_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);
                    }
                    applicationAcceleratedRepository.save(applicationAccelerated);
                } else {
                    applicationAcceleratedValidator.validateApplicationAccelerated(applicationAccelerated);
                    applicationAcceleratedRepository.save(applicationAccelerated);
                }
            }
            return applicationAccelerated.getId();
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    private void addActivityLogWithAcceleratedRequest(ApplicationAcceleratedDto applicationAcceleratedDto) {
        String applicationStatus = applicationInfoRepository.getApplicationsStatusCodeByApplicationId(applicationAcceleratedDto.getApplicationInfoId());
        supportServiceActivityLogHelper.buildActivityLogAndInsert(RequestActivityLogEnum.APPLICATION_ACCELERATED_REQUEST, applicationStatus, Utilities.getCustomerCodeFromHeaders(), applicationAcceleratedDto.getApplicationInfoId(), APPLICATION_ACCELERATED_LOG_TASK_NAME_AR, APPLICATION_ACCELERATED_LOG_TASK_NAME_EN);
    }


    @Override
    public Long deleteApplicationAcceleratedFile(Long id, String fileKey) {
        try {
            ApplicationAccelerated dto = applicationAcceleratedRepository.findById(id).orElseThrow(() -> new NotFoundException("Application document not found with id: " + id));

            switch (fileKey) {
                case "comparative":
                    Document comparativeDocument = dto.getComparative();
                    if (comparativeDocument != null && comparativeDocument.getId() != null) {
                        documentsService.softDeleteDocumentById(comparativeDocument.getId());
                        dto.setComparative(null);
                    }
                    break;
                case "latestPatentableClaimsFile":
                    Document latestPatentableClaimsFile = dto.getLatestPatentableClaimsFile();
                    if (latestPatentableClaimsFile != null && latestPatentableClaimsFile.getId() != null) {
                        documentsService.softDeleteDocumentById(latestPatentableClaimsFile.getId());
                        dto.setLatestPatentableClaimsFile(null);
                    }
                    break;
                case "closestPriorArtDocumentsRelatedToCitedReferencesFile":
                    Document closestPriorArtDocumentsRelatedToCitedReferencesFile = dto.getClosestPriorArtDocumentsRelatedToCitedReferencesFile();
                    if (closestPriorArtDocumentsRelatedToCitedReferencesFile != null && closestPriorArtDocumentsRelatedToCitedReferencesFile.getId() != null) {
                        documentsService.softDeleteDocumentById(closestPriorArtDocumentsRelatedToCitedReferencesFile.getId());
                        dto.setClosestPriorArtDocumentsRelatedToCitedReferencesFile(null);
                    }
                    break;
                default:
                    throw new BadRequestException("Invalid field key: " + fileKey);
            }

            applicationAcceleratedRepository.save(dto);
            return id;
        } catch (
                BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public Optional<ApplicationAccelerated> getByApplicationInfo(ApplicationInfo applicationInfo) {
        return applicationAcceleratedRepository.findByApplicationInfo(applicationInfo);
    }


    public void buildApplicationAccelerated(ApplicationAcceleratedDto dto, ApplicationAccelerated applicationAccelerated) {
        applicationAccelerated.setAcceleratedExamination(dto.getAcceleratedExamination());
        applicationAccelerated.setFastTrackExamination(dto.getFastTrackExamination());
        applicationAccelerated.setPphExamination(dto.getPphExamination());

        Document doc = null;
        if (Objects.nonNull(dto.getComparativeId())) {
            doc = new Document();
            doc.setId(dto.getComparativeId());
        }
        applicationAccelerated.setComparative(doc);

        doc = null;
        if (Objects.nonNull(dto.getLatestPatentableClaimsFileId())) {
            doc = new Document();
            doc.setId(dto.getLatestPatentableClaimsFileId());
        }
        applicationAccelerated.setLatestPatentableClaimsFile(doc);

        doc = null;
        if (Objects.nonNull(dto.getClosestPriorArtDocumentsRelatedToCitedReferencesFileId())) {
            doc = new Document();
            doc.setId(dto.getClosestPriorArtDocumentsRelatedToCitedReferencesFileId());
        }
        applicationAccelerated.setClosestPriorArtDocumentsRelatedToCitedReferencesFile(doc);

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(dto.getApplicationInfoId());
        applicationAccelerated.setApplicationInfo(applicationInfo);

        LkFastTrackExaminationTargetArea lkFastTrackExaminationTargetArea = null;
        if (Objects.nonNull(dto.getFastTrackExaminationTargetAreaId())) {
            lkFastTrackExaminationTargetArea = new LkFastTrackExaminationTargetArea();
            lkFastTrackExaminationTargetArea.setId(dto.getFastTrackExaminationTargetAreaId());
        }
        applicationAccelerated.setFastTrackExaminationTargetArea(lkFastTrackExaminationTargetArea);
    }

    public static ApplicationAccelerated toApplicationAccelerated(ApplicationAcceleratedDto dto) {
        ApplicationAccelerated applicationAccelerated = new ApplicationAccelerated();
        applicationAccelerated.setId(dto.getId());
        applicationAccelerated.setAcceleratedExamination(dto.getAcceleratedExamination());
        applicationAccelerated.setFastTrackExamination(dto.getFastTrackExamination());
        applicationAccelerated.setPphExamination(dto.getPphExamination());
//        applicationAccelerated.setPphPctExamination(dto.getPphPctExamination());
//        applicationAccelerated.setFastTrackType(dto.getFastTrackType());
        if (dto.getLatestPatentableClaimsFileId() != null) {
            Document latestPatentableClaimsFile = new Document();
            latestPatentableClaimsFile.setId(dto.getLatestPatentableClaimsFileId());
            applicationAccelerated.setLatestPatentableClaimsFile(latestPatentableClaimsFile);
        }
        if (dto.getClosestPriorArtDocumentsRelatedToCitedReferencesFileId() != null) {
            Document closestPriorArtDocumentsRelatedToCitedReferencesFile = new Document();
            closestPriorArtDocumentsRelatedToCitedReferencesFile.setId(dto.getClosestPriorArtDocumentsRelatedToCitedReferencesFileId());
            applicationAccelerated.setClosestPriorArtDocumentsRelatedToCitedReferencesFile(closestPriorArtDocumentsRelatedToCitedReferencesFile);
        }
        if (dto.getComparativeId() != null) {
            Document comparative = new Document();
            comparative.setId(dto.getComparativeId());
            applicationAccelerated.setComparative(comparative);
        }
        if (dto.getApplicationInfoId() != null) {
            ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.setId(dto.getApplicationInfoId());
            applicationAccelerated.setApplicationInfo(applicationInfo);
        }
        if (dto.getFastTrackExaminationTargetAreaId() != null) {
            LkFastTrackExaminationTargetArea fastTrackExaminationTargetArea = new LkFastTrackExaminationTargetArea();
            fastTrackExaminationTargetArea.setId(dto.getFastTrackExaminationTargetAreaId());
            applicationAccelerated.setFastTrackExaminationTargetArea(fastTrackExaminationTargetArea);
        }
        return applicationAccelerated;
    }
    @Override
    public Boolean checkIfApplicationAccelrated(Long id) {
        return applicationAcceleratedRepository.checkIfApplicationAccelrated(id);
    }
    public Boolean isApplicationInfoHasAcceleratedApplication(Long id) {
        return applicationAcceleratedRepository.checkIfApplicationAccelratedExist(id);
    }


    @Override
    public ApplicationAcceleratedLightDto getByApplicationId(Long id) {
        List<ApplicationAccelerated> applicationAccelerated =
                applicationAcceleratedRepository.findByApplicationInfoId(id);
        return applicationAccelerated.isEmpty() ? null : applicationAcceleratedMapper.mapAppAccelerated(applicationAccelerated.get(0));
    }

    @Override
    @Transactional
    public void updateApplicationAcceleratedStatus(Long appId, Boolean refused) {
        ApplicationInfo appInfo = new ApplicationInfo();
        appInfo.setId(appId);
        ApplicationAccelerated applicationAccelerated = applicationAcceleratedRepository.findByApplicationInfo(appInfo).
                orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, null));
        applicationAccelerated.setRefused(refused);
        applicationAcceleratedRepository.save(applicationAccelerated);
    }
        @Override
    @Transactional
    public void updateApplicationAcceleratedDecision(Long appId, String decision) {
        ApplicationInfo appInfo = new ApplicationInfo();
        appInfo.setId(appId);
        ApplicationAccelerated applicationAccelerated = applicationAcceleratedRepository.findByApplicationInfo(appInfo).
                orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, null));
        applicationAccelerated.setDecision(decision);
        applicationAcceleratedRepository.save(applicationAccelerated);
    }

    @Override
    public Boolean checkIfApplicationAcceleratedHasDecisionTakenYet(Long appId) {
        return applicationAcceleratedRepository.checkApplicationAcceleratedHasDecisionTakenYet(appId);
    }

    @Transactional
    @Override
    public void deleteByApplicationId(Long appId) {
        applicationAcceleratedRepository.deleteByApplicationId(appId);
        
    }

    @Override
    public Map<Long, ApplicationAcceleratedDto> getAcceleratedApplications(List<Long> appIds) {
        List<ApplicationAccelerated> acceleratedApps = applicationAcceleratedRepository.findByApplicationIds(appIds);
        List<ApplicationAcceleratedDto> acceleratedAppsDtos = applicationAcceleratedMapper.map(acceleratedApps);
        if (acceleratedAppsDtos.isEmpty())
            return null;
        return acceleratedAppsDtos.stream()
                .collect(Collectors.toMap(ApplicationAcceleratedDto::getApplicationInfoId, Function.identity()));
    }
   @Override
    public ApplicationAcceleratedDto getAcceleratedApplicationByApplicationId (Long appId){
        Optional<ApplicationAccelerated> applicationAccelerated = applicationAcceleratedRepository.findOptionalAcceleratedByApplicationId(appId);
        if(applicationAccelerated.isPresent()){
            return applicationAcceleratedMapper.map(applicationAccelerated.get());
        }
        return null;
    }

    @Override
    public ApplicationAcceleratedDto findAcceleratedByApplicationId(Long appId) {
        return applicationAcceleratedRepository.findAcceleratedByApplicationId(appId);
    }
}
