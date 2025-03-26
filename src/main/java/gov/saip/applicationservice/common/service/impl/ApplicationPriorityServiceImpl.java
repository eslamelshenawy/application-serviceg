package gov.saip.applicationservice.common.service.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.StageKeyEnum;
import gov.saip.applicationservice.common.mapper.ApplicationPriorityMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ApplicationPriorityRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationPriorityStatusRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.validators.PrioritiesValidator;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.util.Constants.PRIORITY_CLAIM_DAYS_CODE;


@Service

public class ApplicationPriorityServiceImpl extends BaseServiceImpl<ApplicationPriority, Long> implements ApplicationPriorityService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationPriorityServiceImpl.class);

    private final ApplicationPriorityRepository applicationPriorityRepository;
    private final DocumentsService documentsService;
    private final ApplicationPriorityMapper applicationPriorityMapper;

    private final ApplicationInfoService applicationInfoService;
    private final ConfigParameterService configParameterService;
    private final NotificationCaller notificationCaller;
    private final LkApplicationPriorityStatusRepository lkApplicationPriorityStatusRepository;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final CustomerServiceFeignClient customerServiceFeignClient;

    private final ApplicationCustomerService applicationCustomerService;
    private final ApplicationNotesService applicationNotesService;

    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    
    private final CustomerClient customerClient;
    private final PrioritiesValidator prioritiesValidator;
    private final BPMCallerService bpmCallerService;
    
    
    @Value("${link.contactus}")
    String contactUs;
    
    @Override
    protected BaseRepository<ApplicationPriority, Long> getRepository() {
        return applicationPriorityRepository;
    }
    @Autowired
    public ApplicationPriorityServiceImpl(ApplicationPriorityRepository applicationPriorityRepository, DocumentsService documentsService,
                                          ApplicationPriorityMapper applicationPriorityMapper, @Lazy ApplicationInfoService applicationInfoService,
                                          ConfigParameterService configParameterService, NotificationCaller notificationCaller,
                                          LkApplicationPriorityStatusRepository lkApplicationPriorityStatusRepository, BPMCallerFeignClient bpmCallerFeignClient,
                                          CustomerServiceFeignClient customerServiceFeignClient, ApplicationCustomerService applicationCustomerService,
                                          @Lazy ApplicationNotesService applicationNotesService, ApplicationSupportServicesTypeService applicationSupportServicesTypeService,
                                          CustomerClient customerClient, PrioritiesValidator prioritiesValidator, BPMCallerService bpmCallerService) {
        this.applicationPriorityRepository = applicationPriorityRepository;
        this.documentsService = documentsService;
        this.applicationPriorityMapper = applicationPriorityMapper;
        this.applicationInfoService = applicationInfoService;
        this.configParameterService = configParameterService;
        this.notificationCaller = notificationCaller;
        this.lkApplicationPriorityStatusRepository = lkApplicationPriorityStatusRepository;
        this.bpmCallerFeignClient = bpmCallerFeignClient;
        this.customerServiceFeignClient = customerServiceFeignClient ;
        this.applicationCustomerService = applicationCustomerService ;
        this.applicationNotesService = applicationNotesService;
        this.applicationSupportServicesTypeService = applicationSupportServicesTypeService ;
        this.customerClient = customerClient;
        this.prioritiesValidator = prioritiesValidator;
        this.bpmCallerService = bpmCallerService;
    }

    @Override
    public int hardDeleteByApplicationInfoId(Long applicationInfoId) {
        return applicationPriorityRepository.hardDeleteByApplicationInfoId(applicationInfoId);
    }

    @Override
    public void softDeleteByAppId(Long id) {
            ApplicationSupportServicesType applicationSupportServicesType = applicationSupportServicesTypeService.getSupportServiceTypeById(id);
            Long applicationInfoId = applicationSupportServicesType.getApplicationInfo().getId();
            documentsService.softDeleteDocumentByAppIdANDDocumentType(applicationInfoId, "Prioirty Document", "Prioirty Document Translation");
            applicationPriorityRepository.softdeleteAppById(applicationInfoId);
    }

    @Transactional
    @Override
    public void setExpiredAndSendNotifiction() {
        String nDaysConfigForPriorityClaim =  bpmCallerFeignClient.getRequestTypeConfigValue(PRIORITY_CLAIM_DAYS_CODE);
        Long nDaysForPriorityClaim = Long.valueOf(nDaysConfigForPriorityClaim);
        List<ApplicationPriority> priorities = applicationPriorityRepository.getAppsOutOfDate(nDaysForPriorityClaim);
//        Long priorityDocumentsAllowanceDays = getPriorityDocumentsAllowanceDays();
//        List<ApplicationPriority> priorities = applicationPriorityRepository.getAppsOutOfDate(priorityDocumentsAllowanceDays);
        logger.info().message("priorityList size " + priorities.size()).log();
        if (priorities.size() <= 0) {
            return;
        }
        Set<Long> applicationIds = new HashSet<>();
        for (ApplicationPriority app : priorities) {
            app.setIsExpired(true);
            String email = app.getApplicationInfo().getEmail();
            ApplicantsDto mainApplicant = applicationInfoService.listMainApplicant(app.getApplicationInfo().getId());
            String nameAr = mainApplicant.getNameAr();
            String nameEn = mainApplicant.getNameEn();
            Map<String, Object> param = new HashMap<>();
            param.put("name", nameAr);
            param.put("link", contactUs);
            if (!applicationIds.contains(app.getApplicationInfo().getId())) {
                applicationIds.add(app.getApplicationInfo().getId());
                LkApplicationStatus lkApplicationStatus = new LkApplicationStatus();
                lkApplicationStatus.setId(4l);
                app.getApplicationInfo().setApplicationStatus(lkApplicationStatus);
                applicationInfoService.saveApplicationInfoEntity(app.getApplicationInfo());
                String applicationNumber = app.getApplicationInfo().getApplicationNumber();
                if (Objects.nonNull(applicationNumber) || !applicationNumber.isEmpty()) {
                    ProcessInstanceDto dto = new ProcessInstanceDto();
                    dto.setSuspended(true);
                    bpmCallerFeignClient.suspendApplicationProcessByType(dto, app.getId(), app.getApplicationInfo().getCategory().getSaipCode());
                }
            }

            notificationCaller.sendEmail(email, Constants.MailSubject.REJECTION_SUBJECT, Constants.MailMessage.REJECTION_MESSAGE, param, Constants.TemplateType.REQUEST_REJECT);
            applicationPriorityRepository.save(app);
        }
    }


    @Transactional
    @Override
    public Long createUpdateApplicationPriority(ApplicationPriorityDto dto, Long applicationInfoId) {
        try {
            ApplicationInfo applicationInfoExisted = applicationInfoService.findById(applicationInfoId);

            if(applicationInfoExisted.getCategory().getSaipCode().equals(ApplicationCategoryEnum.PATENT.name())){
                if(applicationInfoService.checkIsPriorityConfirmed(applicationInfoId)){
                    throw new BusinessException(Constants.ErrorKeys.PRIORITY_DOCUMENTS_CONFIRMED, HttpStatus.METHOD_NOT_ALLOWED, new String[]{applicationInfoId.toString()});
                }
            }

            LkApplicationCategory category = applicationInfoExisted.getCategory();
            if (category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.TRADEMARK.name())) {
                if (Objects.isNull(dto.getDasCode())) {
                    throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
                }
            } else if (category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name()) ||
                    category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.PATENT.name())) {
                if (Objects.isNull(dto.getPriorityApplicationNumber())) {
                    throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
                }
            }
            Optional<ApplicationPriority> applicationPriorityOptional = null;
            if (Objects.nonNull(dto.getId())) {
                applicationPriorityOptional = applicationPriorityRepository.findById(dto.getId());
            }
            ApplicationPriority applicationPriority = null;
            if (Objects.isNull(applicationPriorityOptional) || applicationPriorityOptional.isEmpty()) {
                applicationPriority = new ApplicationPriority();
            } else {
                applicationPriority = applicationPriorityOptional.get();
            }
            validatePriorityExpiry(applicationPriority);
            applicationPriority.setPriorityApplicationNumber(dto.getPriorityApplicationNumber());
            applicationPriority.setCountryId(dto.getCountryId());
            applicationPriority.setFilingDate(dto.getFilingDate());

            if (dto.getApplicationClass() != null) {
                applicationPriority.setApplicationClass(dto.getApplicationClass());
            }
            /*Should always provide according to https://tpms.saip.gov.sa/browse/IPV-98*/
            if(category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.PATENT.name())){
                dto.setProvideDocLater(false);
            }

            applicationPriority.setProvideDocLater(dto.getProvideDocLater());
            if (!dto.getProvideDocLater()) {
                if (dto.getPriorityDocument() != null) {
                    Document priorityDocument = new Document();
                    if (!Objects.isNull(applicationPriorityOptional) && !applicationPriorityOptional.isEmpty()) {

                        if (applicationPriority.getPriorityDocument() !=  null && !Objects.equals(dto.getPriorityDocument(), applicationPriority.getPriorityDocument().getId())) {
                            documentsService.updateIsDeleted(applicationPriority.getPriorityDocument().getId(), 1);
                        }
                    }
                    priorityDocument.setId(dto.getPriorityDocument());
                    applicationPriority.setPriorityDocument(priorityDocument);
                }
                                if (dto.getTranslatedDocument() != null) {
                    Document translatedDocument = new Document();
                    translatedDocument.setId(dto.getTranslatedDocument());
                    applicationPriority.setTranslatedDocument(translatedDocument);
                }
            }
            if(dto.getProvideDocLater() == true){
                applicationPriority.setPriorityDocument(null);
                applicationPriority.setTranslatedDocument(null);
            }
            if (dto.getDasCode() != null) {
                applicationPriority.setDasCode(dto.getDasCode());
            }
            ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.setId(applicationInfoId);
            applicationPriority.setApplicationInfo(applicationInfo);
            return applicationPriorityRepository.save(applicationPriority).getId();
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public Long deleteApplicationPriorityFile(Long id, String fileKey) {
        try {
            ApplicationPriority applicationPriority = applicationPriorityRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Application priority document not found with id: " + id));

            switch (fileKey) {
                case "priorityDocument":
                    Document priorityDocument = applicationPriority.getPriorityDocument();
                    if (priorityDocument != null && priorityDocument.getId() != null) {
                        documentsService.softDeleteDocumentById(priorityDocument.getId());
                        applicationPriority.setPriorityDocument(null);
                    }
                    break;

                case "translatedDocument":
                    Document translatedDocument = applicationPriority.getTranslatedDocument();
                    if (translatedDocument != null && translatedDocument.getId() != null) {
                        documentsService.softDeleteDocumentById(translatedDocument.getId());
                        applicationPriority.setTranslatedDocument(null);
                    }
                    break;

                default:
                    throw new BadRequestException("Invalid field key: " + fileKey);
            }

            applicationPriorityRepository.save(applicationPriority);

            return id;

        } catch (BusinessException ex) {
            logger.error().message(ex.toString()).log();
            throw ex;

        } catch (Exception ex) {
            logger.error().exception("exception", ex).message(ex.getMessage()).log();
            throw ex;
        }
    }


    @Override
    public Long deleteApplicationPriority(Long id) {
        try {
            ApplicationPriority applicationPriority = findById(id);
            applicationPriority.setIsDeleted(1);
            applicationPriorityRepository.save(applicationPriority);
            return id;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public Long updatePriorityDocs(Long id, ApplicationPriorityDocsDto applicationPriorityDocsDto) {
        try {
            ApplicationPriority applicationPriority = findById(id);
            validatePriorityExpiry(applicationPriority);
            if (Objects.nonNull(applicationPriorityDocsDto.getPriorityDocument())) {
                Document document = new Document();
                document.setId(applicationPriorityDocsDto.getPriorityDocument());
                applicationPriority.setPriorityDocument(document);
            }
            if (Objects.nonNull(applicationPriorityDocsDto.getTranslatedDocument())) {
                Document document = new Document();
                document.setId(applicationPriorityDocsDto.getTranslatedDocument());
                applicationPriority.setTranslatedDocument(document);
            }
            applicationPriorityRepository.save(applicationPriority);
            return id;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public Long updatePriorityDocsAndCompleteTask(Long id, ApplicationPriorityDocsDto applicationPriorityDocsDto){
        updatePriorityDocs(id, applicationPriorityDocsDto);
        ApplicationInfo applicationInfo = applicationPriorityRepository.findApplicationInfoBy(id);
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndTypeIfExists(RequestTypeEnum.valueOf(applicationInfo.getCategory().getSaipCode()), applicationInfo.getId());
        completeTask(requestTasksDto, "UPDATE");
        return id;
    }

    private void completeTask(RequestTasksDto requestTasksDto, String approvedValue) {
        CompleteTaskRequestDto completeTaskRequestDto = buildCompleteTaskRequestDto(approvedValue);
        bpmCallerService.completeTaskToUser(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    private static CompleteTaskRequestDto buildCompleteTaskRequestDto(String approvedValue) {
        Map<String, Object> variables = buildTaskVariableMap(approvedValue);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(variables);
        return completeTaskRequestDto;
    }

    private static Map<String, Object> buildTaskVariableMap(String approvedValue) {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> completedPetitionRecovery = new HashMap<>();
        completedPetitionRecovery.put("value", approvedValue);
        variables.put("approved", completedPetitionRecovery);
        return variables;
    }

    private void validatePriorityExpiry(ApplicationPriority applicationPriority) {
        if (Objects.nonNull(applicationPriority)
                && Objects.nonNull(applicationPriority.getIsExpired())
                && applicationPriority.getIsExpired()) {
            throw new BusinessException(Constants.ErrorKeys.Priority_Expired, HttpStatus.BAD_REQUEST, null);
        }
    }

    @Override
    public Long getPriorityDocumentsAllowanceDays() {
        return configParameterService.getLongByKey("PRIORITY_DOCUMENTS_MAX_ALLOWANCE_DAYS");
    }

    @Override
    @Transactional
    public void setAboutExpiredAndSendNotifiction() {
        String nDaysConfigForPriorityClaim =  bpmCallerFeignClient.getRequestTypeConfigValue(PRIORITY_CLAIM_DAYS_CODE);
        Long nDaysForPriorityClaim = Long.valueOf(nDaysConfigForPriorityClaim);
//        Long priorityDocumentsAllowanceDays = getPriorityDocumentsAllowanceDays();
        List<ApplicationPriority> priorities = applicationPriorityRepository.getAppsAboutToExpire(nDaysForPriorityClaim - 2L, 23L);
        logger.info().message("priorityList size " + priorities.size()).log();
        if (priorities.size() <= 0) return;
        for (ApplicationPriority app : priorities) {
            String email = app.getApplicationInfo().getEmail();
            String mobile = app.getApplicationInfo().getMobileCode() + app.getApplicationInfo().getMobileNumber();
            ApplicantsDto mainApplicant = applicationInfoService.listMainApplicant(app.getApplicationInfo().getId());
            String nameAr = mainApplicant.getNameAr();
            String nameEn = mainApplicant.getNameEn();
            Map<String, Object> param = new HashMap<>();
            param.put("name", nameAr);
            param.put("link", contactUs);
            notificationCaller.sendEmail(email, Constants.MailSubject.PENDING_SUBJECT, Constants.MailMessage.PENDING_MESSAGE, param, Constants.TemplateType.REQUEST_PENDING);

        }

    }

    @Override
    @Transactional
    public List<Long> createUpdateApplicationPriority(ApplicationPriorityBulkDto applicationPriorityBulkDto, Long applicationInfoId) {
        List<Long> idsToDeleted = applicationPriorityBulkDto.getToBeDeleted();
        if (CollectionUtils.isNotEmpty(idsToDeleted)) {
            for (Long id : idsToDeleted) {
                ApplicationPriority applicationPriority = applicationPriorityRepository.findById(id).get();
                applicationPriority.setIsDeleted(1);
                applicationPriorityRepository.save(applicationPriority);
            }
        }

        List<ApplicationPriorityDto> list = applicationPriorityBulkDto.getApplicationPriorityDtoList();
        List<Long> result = new LinkedList<>();
        for (ApplicationPriorityDto applicationPriorityDto : list) {
             result.add(createUpdateApplicationPriority(applicationPriorityDto, applicationInfoId));
        }
        if(applicationPriorityBulkDto.getIsPriorityConfirmed()){
            applicationInfoService.disableAddingApplicationPriority(applicationInfoId);
        }


        sendNotificationPriority(applicationInfoId);

        return result;
    }

    private void sendNotificationPriority(Long applicationInfoId) {
        // send the notification

        Map<String, Object> notificationParams = new HashMap<>();
        notificationParams.put("id", applicationInfoId);

        ApplicationNotificationData notificationDate = applicationCustomerService.findapplicationNotificationData(applicationInfoId);
        String customerName = customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(notificationDate.getCustomerId())).getPayload();

        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(applicationInfoId);
        String titleAr = applicationInfoDto.getTitleAr();
        notificationParams.put("title", titleAr);
        notificationParams.put("link",contactUs);

        NotificationDto emailDto = NotificationDto
                .builder()
                .to(notificationDate.getEmail())
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();

        NotificationDto smsDto = NotificationDto
                .builder()
                .to(notificationDate.getMobileNumber())
                .build();


        AppNotificationDto appDto = AppNotificationDto.builder()
                .rowId(applicationInfoId.toString())
                .routing(ApplicationCategoryEnum.PATENT.name())
                .date(LocalDateTime.now())
                .userNames(Arrays.asList(customerName)).build();

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(NotificationTemplateCode.PATENT_PRIORITY)
                .templateParams(notificationParams)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();


        notificationCaller.sendAllToSpecificUser(notificationRequest);
    }

    @Override
    public List<ApplicationPriorityListDto> listApplicationPriorites(Long applicationId) {
        List<ApplicationPriorityListDto> applicationPriorityListDtos = applicationPriorityMapper.map(applicationPriorityRepository.getPrioritesOfByApplicationId(applicationId, 0));
        applicationPriorityListDtos.stream().forEach(val -> {
            val.setCountry(customerClient.getCountryById(val.getCountryId()).getPayload());
        });
        return applicationPriorityListDtos;
    }

    public Boolean doesApplicationHasPrioritiesThatHaveNoActionTaken(Long applicationId){
        // here action means that single priority has status
        final int nonDeletedPrioritiesFlag =0;
        return applicationPriorityRepository.doesApplicationHasPrioritiesThatNoActionTaken(applicationId, nonDeletedPrioritiesFlag);
    }
    @Transactional
    public Long updatePriorityStatusAndComment(Long id, ApplicationPriorityUpdateStatusAndCommentsDto updateStatuscommentDto) {

        ApplicationPriority applicationPriority = findById(id);

        if (Objects.nonNull(updateStatuscommentDto.getCode())) {
            LkApplicationPriorityStatus applicationPriorityStatus = lkApplicationPriorityStatusRepository.getByCode(updateStatuscommentDto.getCode());
            applicationPriority.setPriorityStatus(applicationPriorityStatus);
        }
        if (Objects.nonNull(updateStatuscommentDto.getComment())) {
            applicationPriority.setComment(updateStatuscommentDto.getComment());
        }
        applicationPriorityRepository.save(applicationPriority);

        ApplicationInfo applicationInfo = applicationPriority.getApplicationInfo();

        addApplicationNotesToPriority(id, updateStatuscommentDto, applicationInfo);

        return id;

    }

    private void addApplicationNotesToPriority(Long id, ApplicationPriorityUpdateStatusAndCommentsDto updateStatuscommentDto, ApplicationInfo applicationInfo) {
        if (ApplicationCategoryEnum.TRADEMARK.name().equals(applicationInfo.getCategory().getSaipCode())) {
            ApplicationNotesReqDto applicationNotesReqDto = new ApplicationNotesReqDto();
            applicationNotesReqDto.setApplicationId(applicationInfo.getId());
            applicationNotesReqDto.setDescription("REJECTED".equals(updateStatuscommentDto.getCode()) ? updateStatuscommentDto.getComment() : null);
            applicationNotesReqDto.setPriorityId(id);
            applicationNotesReqDto.setStageKey(StageKeyEnum.SUBSTANTIVE_EXAMINATION_TM.name());
            applicationNotesReqDto.setSectionCode("FILES");
            applicationNotesReqDto.setAttributeCode("PRIORITY");
            applicationNotesReqDto.setApplicationSectionNotesDtos(new ArrayList<>());

            applicationNotesService.updateAppNote(applicationNotesReqDto);
        }
    }
    
    public List<ApplicationPriorityLightResponseDto> setPrioritiesDetailsIfValid(ApplicationInfoDto applicationInfoDto) {
        
        List<ApplicationPriorityResponseDto> validPriorities = listValidPriorities(applicationInfoDto.getApplicationPriority());
        List<ApplicationPriorityLightResponseDto> applicationPriorities = new ArrayList<>();
        
        if (!validPriorities.isEmpty()) {
            List<Long> countriesIds = validPriorities.stream().map(ApplicationPriorityResponseDto::getCountryId).toList();
            List<CountryDto> countries = customerClient.getCountriesByIds(countriesIds).getPayload();
            applicationPriorities = validPriorities.stream().map(priority -> {
                ApplicationPriorityLightResponseDto applicationPriority = ApplicationPriorityLightResponseDto.builder()
                        .priorityApplicationNumber(priority.getPriorityApplicationNumber())
                        .filingDate(priority.getFilingDate()).build();
                if (countries != null && !countries.isEmpty() && priority.getCountryId() != null) {
                    Optional<CountryDto> country = countries.stream().filter(countryDto -> priority.getCountryId().equals(countryDto.getId())).findFirst();
                    country.ifPresent(applicationPriority::setCountry);
                }
                return applicationPriority;
            }).toList();
        }
        return applicationPriorities;
    }
    
    
    private List<ApplicationPriorityResponseDto> listValidPriorities(List<ApplicationPriorityResponseDto> priorities) {
        List<ApplicationPriorityResponseDto> vaildPriorites = new ArrayList<>();
        for (ApplicationPriorityResponseDto priority : priorities) {
            if (Objects.isNull(priority.getIsExpired()) || !priority.getIsExpired())
                vaildPriorites.add(priority);
        }
        return vaildPriorites;
    }

    public Boolean checkApplicationPrioritiesProvideDocLater(Long appId){
        return applicationPriorityRepository.checkApplicationPrioritiesProvideDocLater(appId);
    }

    public Boolean checkApplicationPriorities(Long appId){
        return applicationPriorityRepository.checkApplicationPriorities(appId);
    }
    public Boolean checkApplicationPrioritiesDocuments(@Param("appId")Long appId){
        return applicationPriorityRepository.checkApplicationPrioritiesDocuments(appId);
    }

    @Transactional
    @Override
    public Long createUpdateApplicationPriorityWithComplete(ApplicationPriorityDto dto, Long applicationInfoId) {
        dto.setApplicationId(applicationInfoId);
        prioritiesValidator.validate(dto,null);
        try {
            ApplicationInfo applicationInfoExisted = applicationInfoService.findById(applicationInfoId);

            if(applicationInfoExisted.getCategory().getSaipCode().equals(ApplicationCategoryEnum.PATENT.name())){
                if(applicationInfoService.checkIsPriorityConfirmed(applicationInfoId)){
                    throw new BusinessException(Constants.ErrorKeys.PRIORITY_DOCUMENTS_CONFIRMED, HttpStatus.METHOD_NOT_ALLOWED, new String[]{applicationInfoId.toString()});
                }
            }

            LkApplicationCategory category = applicationInfoExisted.getCategory();
            if (category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.TRADEMARK.name())) {
                if (Objects.isNull(dto.getDasCode())) {
                    throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
                }
            } else if (category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name()) ||
                    category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.PATENT.name())) {
                if (Objects.isNull(dto.getPriorityApplicationNumber())) {
                    throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);
                }
            }
            Optional<ApplicationPriority> applicationPriorityOptional = null;
            if (Objects.nonNull(dto.getId())) {
                applicationPriorityOptional = applicationPriorityRepository.findById(dto.getId());
            }
            ApplicationPriority applicationPriority = null;
            if (Objects.isNull(applicationPriorityOptional) || applicationPriorityOptional.isEmpty()) {
                applicationPriority = new ApplicationPriority();
            } else {
                applicationPriority = applicationPriorityOptional.get();
            }
            validatePriorityExpiry(applicationPriority);
            applicationPriority.setPriorityApplicationNumber(dto.getPriorityApplicationNumber());
            applicationPriority.setCountryId(dto.getCountryId());
            applicationPriority.setFilingDate(dto.getFilingDate());

            if (dto.getApplicationClass() != null) {
                applicationPriority.setApplicationClass(dto.getApplicationClass());
            }
            /*Should always provide according to https://tpms.saip.gov.sa/browse/IPV-98*/
            if(category.getSaipCode().equalsIgnoreCase(ApplicationCategoryEnum.PATENT.name())){
                dto.setProvideDocLater(false);
            }

            applicationPriority.setProvideDocLater(dto.getProvideDocLater());
            if (!dto.getProvideDocLater()) {
                if (dto.getPriorityDocument() != null) {
                    Document priorityDocument = new Document();
                    if (!Objects.isNull(applicationPriorityOptional) && !applicationPriorityOptional.isEmpty()) {

                        if (applicationPriority.getPriorityDocument() !=  null && !Objects.equals(dto.getPriorityDocument(), applicationPriority.getPriorityDocument().getId())) {
                            documentsService.updateIsDeleted(applicationPriority.getPriorityDocument().getId(), 1);
                        }
                    }
                    priorityDocument.setId(dto.getPriorityDocument());
                    applicationPriority.setPriorityDocument(priorityDocument);
                }


                if (dto.getTranslatedDocument() != null) {
                    Document translatedDocument = new Document();
                    translatedDocument.setId(dto.getTranslatedDocument());
                    applicationPriority.setTranslatedDocument(translatedDocument);
                }
            }

            if (dto.getDasCode() != null) {
                applicationPriority.setDasCode(dto.getDasCode());
            }
            ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.setId(applicationInfoId);
            applicationPriority.setApplicationInfo(applicationInfo);
            Long appId =applicationPriorityRepository.save(applicationPriority).getId();

            if (applicationPriority.getPriorityDocument() != null && applicationPriority.getProvideDocLater().equals(Boolean.FALSE))
                completeApplicantPriorityDocument(applicationInfoId, category.getSaipCode());

            return appId;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }




    @Override
    public void completeApplicantPriorityDocument(Long applicationId , String categoryCode) {
        RequestTasksDto taskByRowId = bpmCallerService.getTaskByRowId(applicationId, categoryCode);
        Map<String, Object> acceptApplicantModifications = new HashMap<>();
        acceptApplicantModifications.put("value", "YES");
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", acceptApplicantModifications);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(variables);
        bpmCallerFeignClient.completeUserTask(taskByRowId.getTaskId(), completeTaskRequestDto);
    }


    @Override
    public List<ApplicationPriorityListDto> getPrioritiesThatHaveNotPriorityDocument(Long appId) {
        List<ApplicationPriorityListDto> applicationPriorityListDtos = applicationPriorityMapper.map(applicationPriorityRepository.getPrioritiesThatHaveNotPriorityDocument(appId));
        return applicationPriorityListDtos;
    }

    @Override
    public ApplicationPriorityListDto getApplicationPriorityById(Long priorityId) {
        ApplicationPriority applicationPriority = super.findById(priorityId);
        return applicationPriorityMapper.map(applicationPriority);
    }

}
