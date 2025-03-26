package gov.saip.applicationservice.common.service.patent.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.client.JasperReportsClient;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.UserManageClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.patent.*;
import gov.saip.applicationservice.common.dto.payment.ApplicationBillLightDTO;
import gov.saip.applicationservice.common.dto.pltDurationPaymentInfo.PltDurationPaymentInfoDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.patent.PatentDetailsMapper;
import gov.saip.applicationservice.common.model.ApplicationCheckingReport;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import gov.saip.applicationservice.common.model.patent.Pct;
import gov.saip.applicationservice.common.repository.patent.PatentDetailsRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.patent.PatentAttributeChangeLogService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.common.service.trademark.impl.CustomMultipartFile;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_MAIN;
import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_PATENT;


/**
 * The {@code PatentDetailsServiceImpl} class provides an implementation of the {@link PatentDetailsService} interface
 * that manages patent details.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatentDetailsServiceImpl extends BaseServiceImpl<PatentDetails, Long> implements PatentDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(PatentDetailsServiceImpl.class);
    private final PatentDetailsRepository patentDetailsRepository;
    private final PatentDetailsMapper patentDetailsMapper;
    private final ApplicationInfoService applicationInfoService;

    private final ApplicationCheckingReportService applicationCheckingReportService;
    private final DocumentsService documentsService;
    private final ApplicationPriorityService applicationPriorityService;
    private final ApplicationInfoGenericService applicationInfoGenericService;
    private final ApplicationPublicationService applicationPublicationService;
    private final PatentAttributeChangeLogService patentAttributeChangeLogService;
    private final PaymentFeeCostFeignClient paymentFeeCostFeignClient;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final JasperReportsClient jasperReportsClient;
    private final ApplicationUserService applicationUserService;
    private final UserManageClient userManageClient;
    private final PctService pctService;
    @Autowired
    @Lazy
    private  ApplicationNotesService applicationNotesService;
    @Override
    protected BaseRepository<PatentDetails, Long> getRepository() {
        return patentDetailsRepository;
    }

    /**
     * Saves a new patent with the specified details and application ID.
     *
     * @param dto           the patent details request DTO
     * @param applicationId the application ID
     * @return the saved patent details entity
     * @throws BusinessException if the application ID is duplicated
     */
    @Override
    @Transactional
    public PatentDetails savePatent(PatentDetailsRequestDto dto, Long applicationId) {
        if (findByApplicationId(applicationId) != null && Objects.isNull(dto.getId())) {
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_ID_DUPLICATED, HttpStatus.BAD_REQUEST, null);
        }

        try {
            PatentDetails patentDetails = patentDetailsMapper.mapRequestToEntity(dto);
            patentDetails.setApplicationId(applicationId);
            PatentDetails savedPatentDetails = patentDetailsRepository.save(patentDetails);
            patentAttributeChangeLogService.savePatentAttributeChangeLog(savedPatentDetails, dto);
            return savedPatentDetails;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    /**
     * Finds a patent by ID and retrieves the associated document.
     *
     * @param id the patent ID
     * @return the patent details entity with the associated document DTO
     * @throws BusinessException if the patent details are not found
     */
    @Override
    public PatentDetails findByPatentId(Long id) {
        try {
            Optional<PatentDetails> patentDetails = patentDetailsRepository.findById(id);
            if (patentDetails.isEmpty()) {
                throw new BusinessException(Constants.ErrorKeys.PATENT_DETAILS_NOT_FOUND, HttpStatus.NOT_FOUND, null);
            }
            DocumentDto document = documentsService.findDocumentById(patentDetails.get().getSpecificationsDocId());
            patentDetails.get().setDocumentDto(document);
            return patentDetails.get();
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public PatentDetailDtoWithChangeLog findDtoByApplicationId(Long id) {
        PatentDetailDtoWithChangeLog patentDetailDtoWithChangeLog = null;
        PatentDetails patentDetails = findByApplicationId(id);
        if (patentDetails != null) {
            Map<String, List<PatentAttributeChangeLogDto>> patentAttributeChangeLogDtoMap = patentAttributeChangeLogService.getPatentAttributeChangeLogByPatentId(patentDetails.getId());
            patentDetailDtoWithChangeLog = buildPatentDetailDtoWithChangeLog(patentDetails, patentAttributeChangeLogDtoMap);
        }
        return patentDetailDtoWithChangeLog;
    }

    @Override
    public PatentDetailDtoWithChangeLog findDtoByApplicationIdWithOnlyLatestVersionOfLogs(Long id) {
        PatentDetailDtoWithChangeLog patentDetailDtoWithChangeLog = null;
        PatentDetails patentDetails = findByApplicationId(id);
        if (patentDetails != null) {
            Map<String, List<PatentAttributeChangeLogDto>> patentAttributeChangeLogDtoMap = patentAttributeChangeLogService.getPatentAttributeLatestChangeLogByPatentId(patentDetails.getId());
            patentDetailDtoWithChangeLog = buildPatentDetailDtoWithChangeLog(patentDetails, patentAttributeChangeLogDtoMap);
        }
        return patentDetailDtoWithChangeLog;
    }

    private PatentDetailDtoWithChangeLog buildPatentDetailDtoWithChangeLog(PatentDetails patentDetails, Map<String, List<PatentAttributeChangeLogDto>> patentAttributeChangeLogDtoMap) {
        return PatentDetailDtoWithChangeLog.builder().patentDetails(patentDetailsMapper.map(patentDetails)).patentAttributeChangeLog(patentAttributeChangeLogDtoMap).build();
    }

    /**
     * Finds a patent by application ID and retrieves the associated document.
     *
     * @param id the application ID
     * @return the patent details entity with the associated document DTO, or {@code null} if the patent details are not found
     */

    private PatentDetails findByApplicationId(Long id) {
        try {
            Optional<PatentDetails> patentDetails = patentDetailsRepository.findByApplicationId(id);
            if (!patentDetails.isPresent()) {
                return null;
            }

            if (Objects.nonNull(patentDetails.get().getSpecificationsDocId())) {
                DocumentDto document = documentsService.findDocumentById(patentDetails.get().getSpecificationsDocId());
                patentDetails.get().setDocumentDto(document);
            }
            return patentDetails.get();
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    public PatentDetails findPatentDetailsByApplicationId(Long id) {
        Optional<PatentDetails> patentDetails = patentDetailsRepository.findByApplicationId(id);
        if (!patentDetails.isPresent())
            return null;
        return patentDetails.get();

    }

    /**
     * Retrieves the substantive examination details for the application with the specified ID.
     *
     * @param id the application ID
     * @return an {@link ApplicationPatentDetailsDto} object containing the substantive examination details and the application details
     */
    @Override
    public ApplicationPatentDetailsDto retrieveSubstantiveExaminationDetails(Long id) {

        PatentDetails patentDetails = patentDetailsRepository.findByApplicationId(id).get();
        ApplicationPatentDetailsDto applicationPatentDetailsDto = patentDetailsMapper.mapToApplicationPatentDetailsDto(patentDetails);
        applicationPatentDetailsDto.setApplication(applicationInfoService.getApplicationSubstantiveExamination(id));

        return applicationPatentDetailsDto;
    }

    @Override
    public PublicationDetailDto getPublicationDetails(Long applicationId, Long applicationPublicationId,String publicationCode) {
        List<ApplicantsDto> applicants = applicationInfoService.listApplicants(applicationId);

        String inventorName = findInventorName(applicants);
        ApplicantsDto applicantsMainDto = findMainApplicant(applicants);

        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(applicationId);
        CustomerSampleInfoDto applicationCurrentAgent = applicationInfoService.getCurrentAgentsInfoByApplicationId(applicationId);
        DocumentDto documentDto = findLatestDocument(applicationId);
        List<DocumentDto> references = documentDto != null ? List.of(documentDto) : new ArrayList<>();

        PatentDetails patentDetails = findByApplicationId(applicationId);
        Pct pct = pctService.findByPatentDetailsId(patentDetails.getId());
        Map<String, List<PatentAttributeChangeLogDto>> patentSummery = findDtoByApplicationIdAndAttributeName(List.of("arSummary", "enSummary"), patentDetails.getId());
        Optional<LocalDate> patentPublicationDate = applicationPublicationService.findApplicationPublicationDateByAppIdAndType(applicationId, getPublicationType(publicationCode));
        PublicationDetailDto publicationDetailDto = buildPublicationDetailDto(applicationInfoDto, applicantsMainDto, inventorName, applicationCurrentAgent, references, patentDetails, patentSummery);
        if (publicationDetailDto != null && patentPublicationDate.isPresent()){
            publicationDetailDto.setPublicationDate(patentPublicationDate.get());
        }
        if (pct != null){
            publicationDetailDto.setPct(pct.getPctApplicationNo());
        }

        setPublicationDateIfNotNull(applicationPublicationId, publicationDetailDto);

        ApplicationBillLightDTO billLightDto = paymentFeeCostFeignClient.getBillDetailsByApplicationIdAndMainRequestType(applicationId,"FILE_NEW_APPLICATION").getPayload();
        if(billLightDto != null) {
            publicationDetailDto.setFilingInvoicePaymentDate(billLightDto.getPaymentDate());
        }

        addExaminerNamesToPublicationDetailDto(applicationId, publicationDetailDto);

        List<ApplicationPriorityLightResponseDto> applicationPriorities = applicationPriorityService.setPrioritiesDetailsIfValid(applicationInfoDto);
        publicationDetailDto.setApplicationPriorities(applicationPriorities);
        return publicationDetailDto;
    }
    private static String getPublicationType(String publicationCode) {
        return Objects.nonNull(publicationCode) ? PublicationType.valueOf(publicationCode).name() : PublicationType.PATENT_REGISTRATION.name();
    }
    private void addExaminerNamesToPublicationDetailDto(Long applicationId, PublicationDetailDto publicationDetailDto) {
        List<String> examinersUsernames = applicationUserService.listUsersByByApplicationIdAndRole(applicationId, ApplicationUserRoleEnum.EXAMINER);
        List<String> examinerNames = userManageClient.getFullNameByUsernames(examinersUsernames).getPayload();
        publicationDetailDto.setExaminerNames(examinerNames);
    }

    private String findInventorName(List<ApplicantsDto> applicants) {
        for (ApplicantsDto applicantsDto : applicants) {
            if (applicantsDto.isInventor()) {
                return applicantsDto.getNameAr();
            }
        }
        return null;
    }

    private ApplicantsDto findMainApplicant(List<ApplicantsDto> applicants) {
        for (ApplicantsDto applicantsDto : applicants) {
            if (applicantsDto.getType().equals(Applicant_MAIN)) {
                return applicantsDto;
            }
        }
        return null;
    }

    private DocumentDto findLatestDocument(Long applicationId) {
        return documentsService.findLatestDocumentByApplicationIdAndDocumentType(applicationId, "Substansive Report");
    }

    private PublicationDetailDto buildPublicationDetailDto(ApplicationInfoDto applicationInfoDto, ApplicantsDto applicantsMainDto, String inventorName,
                                                           CustomerSampleInfoDto applicationCurrentAgent, List<DocumentDto> references,
                                                           PatentDetails patentDetails, Map<String, List<PatentAttributeChangeLogDto>> patentSummery) {
        return PublicationDetailDto.builder()
                .applicationNumber(applicationInfoDto.getApplicationNumber())
                .applicationStatus(applicationInfoDto.getApplicationStatus().getCode())
                .applicationFilingDate(applicationInfoDto.getFilingDate().toLocalDate())
                .grantNumber(applicationInfoDto.getGrantNumber())
                .inventorNameAr(inventorName)
                .applicantNameAr(applicantsMainDto != null ? applicantsMainDto.getNameAr() : null)
                .applicantNameEn(applicantsMainDto != null ? applicantsMainDto.getNameEn() : null)
                .applicantAdress(applicantsMainDto != null ? applicantsMainDto.getAddress() : null)
                .agentNameAr(applicationCurrentAgent.getNameAr())
                .agentNameEn(applicationCurrentAgent.getNameEn())
                .patentSummeryAr(patentDetails.getIpdSummaryAr())
                .patentSummeryEn(patentDetails.getIpdSummaryEn())
                .task(applicationInfoDto.getTask())
                .applicationTitleAr(applicationInfoDto.getTitleAr())
                .applicationTitleEn(applicationInfoDto.getTitleEn())
                .processRequestTypeCode(Objects.nonNull(ApplicationCategoryEnum.valueOf(applicationInfoDto.getCategory().getSaipCode()).getProcessTypeCode())?ApplicationCategoryEnum.valueOf(applicationInfoDto.getCategory().getSaipCode()).getProcessTypeCode():null)
                .pct(applicationInfoDto.getPctDto() == null ? null : applicationInfoDto.getPctDto().getPctApplicationNo())
                .grantDate(applicationInfoDto.getGrantDate() == null ? null : applicationInfoDto.getGrantDate().toLocalDate())
                .references(references)
                .patentSummery(patentSummery)
                .documents(applicationInfoDto.getDocuments())
                .inventors(applicationInfoDto.getInventors())
                .build();
    }

    private void setPublicationDateIfNotNull(Long applicationPublicationId, PublicationDetailDto publicationDetailDto) {
        if (applicationPublicationId != null) {
            LocalDateTime publicationDate = applicationPublicationService.getPublicationDateById(applicationPublicationId);
            publicationDetailDto.setPublicationDate(publicationDate.toLocalDate());
        }
    }
    
    public Map<String, List<PatentAttributeChangeLogDto>> findDtoByApplicationIdAndAttributeName(List<String> attributeNames, Long patentId) {

        Map<String, List<PatentAttributeChangeLogDto>> patentAttributeChangeLogDtoMap;

        patentAttributeChangeLogDtoMap = patentAttributeChangeLogService
                .getPatentAttributesLatestChangeLogByPatentId(attributeNames, patentId);

        return patentAttributeChangeLogDtoMap;
    }

    /**
     * Retrieves XML formatted file that contains the patent applications information
     *
     * @param applicationId the ID of the application
     * @return an {@link ByteArrayResource} object containing the XML file content
     * @throws BusinessException if a JAXB exception is thrown while generating the XML
     */
    public ByteArrayResource getApplicationInfoXml(Long applicationId) {
        PatentApplicationInfoXmlDto dto = getApplicationInfoXmlDto(applicationId);
        return applicationInfoGenericService.getApplicationInfoXml(dto);
    }

    @Override
    @SneakyThrows
    public void generateUploadSaveXmlForApplication(Long applicationId, String documentType) {

        ByteArrayResource file = getApplicationInfoXml(applicationId);
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), "patent.xml",
                        "text/xml", false, file.getByteArray().length, file);
        files.add(multipartFile);
        documentsService.addDocuments(files, documentType, IPRS_PATENT.name(), applicationId);
    }

    @Override
    public List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String documentType) throws IOException {
        // need call dashboard api
        Long applicationId = Long.valueOf(dto.getParams().get("APPLICATION_ID").toString());
        ApplicationCheckingReport reportEntity = applicationCheckingReportService.findByApplicationInfoIdAndReportType(applicationId, ReportsType.valueOf(documentType));
        if (Objects.isNull(reportEntity)){
            reportEntity=new ApplicationCheckingReport();
            reportEntity.setReportType(ReportsType.valueOf(documentType));
            reportEntity.setApplicationInfo(new ApplicationInfo(applicationId));
            reportEntity= applicationCheckingReportService.insert(reportEntity);
        }
        Map<String, Object> jasperParams = dto.getParams();
        jasperParams.put("REPORT_ID", reportEntity.getId());
        dto.setParams(jasperParams);
        ResponseEntity<ByteArrayResource> file=  jasperReportsClient.exportToPdf(dto,documentType);
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), documentType+".jrxml",
                        "application/pdf", false, file.getHeaders().getContentLength(), file.getBody());
        files.add(multipartFile);
        List<DocumentDto> documentDtos = documentsService.addDocuments(files, documentType, IPRS_PATENT.name(), applicationId);
        reportEntity.setDocumentId(documentDtos.get(0).getId());
        applicationCheckingReportService.insert(reportEntity);
        return documentDtos;
    }


    /**
     * Retrieves the patent application information needed for the XML file
     *
     * @param applicationId the ID of the application
     * @return {@link PatentApplicationInfoXmlDto} object containing the patent application information needed
     * for the XML file
     */
    private PatentApplicationInfoXmlDto getApplicationInfoXmlDto(Long applicationId) {
        PatentApplicationInfoXmlDataDto dto = patentDetailsRepository.getApplicationInfoXmlDataDto(applicationId)
                .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND,
                        HttpStatus.NOT_FOUND));
        return PatentApplicationInfoXmlDto.builder()
                .patentApplicationInfoXmlDataDtoList(Collections.singletonList(dto))
                .build();
    }

    @Override
    public PltDurationPaymentInfoDto getApplicationPaymentInfoAndDurationConfigured(Long appId, String mainRequestType) {
        PltDurationPaymentInfoDto pltDurationPaymentInfoDto = new PltDurationPaymentInfoDto();

        if (Objects.nonNull(appId)) {
            ApplicationBillLightDTO billDetailsDto = paymentFeeCostFeignClient.getBillDetailsByApplicationIdAndMainRequestType(appId, mainRequestType).getPayload();

            if (Objects.nonNull(billDetailsDto)) {
                if (billDetailsDto.getPaymentStatus().equals("PAID"))
                    pltDurationPaymentInfoDto.setIsAppPaid(true);
                else
                    pltDurationPaymentInfoDto.setIsAppPaid(false);
            }
        }
        pltDurationPaymentInfoDto.setApplicationPaymentDays(Duration.parse(bpmCallerFeignClient.getRequestTypeConfigValue("BILL_DURATION_OF_PATENT_PLT")).toDays());
        pltDurationPaymentInfoDto.setApplicationModificationDays(Duration.parse(bpmCallerFeignClient.getRequestTypeConfigValue("DURATION_OF_PATENT_PLT")).toDays());

        return pltDurationPaymentInfoDto;
    }

    @Override
    public DocumentDto generateReportByDocumentType(Long applicationId, String documentType) throws IOException {
        Map<String, Object> jasperParams = new HashMap<>();
        jasperParams.put("APPLICATION_ID", applicationId);
        ApplicationCheckingReport reportEntity =
                applicationCheckingReportService
                        .findByApplicationInfoIdAndReportType(applicationId, ReportsType.valueOf(documentType));
        if (Objects.isNull(reportEntity)) {
            reportEntity = new ApplicationCheckingReport();
            reportEntity.setReportType(ReportsType.valueOf(documentType));
            reportEntity.setApplicationInfo(new ApplicationInfo(applicationId));
            reportEntity = applicationCheckingReportService.insert(reportEntity);
        }
        jasperParams.put("REPORT_ID", reportEntity.getId());
        ReportRequestDto dto = ReportRequestDto.builder().fileName(documentType).params(jasperParams).build();
        uploadReportToNexus(applicationId, documentType, dto);
        DocumentDto lastReportDocumentInfo = documentsService.findLatestDocumentByApplicationIdAndDocumentType(applicationId, documentType);
        reportEntity.setDocumentId(lastReportDocumentInfo.getId());
        applicationCheckingReportService.insert(reportEntity);
        return lastReportDocumentInfo;
    }


    private void uploadReportToNexus(Long applicationId, String documentType, ReportRequestDto dto) throws IOException {
        ResponseEntity<ByteArrayResource> file = jasperReportsClient.exportToPdf(dto, documentType);
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), documentType + ".jrxml",
                        "application/pdf", false, file.getHeaders().getContentLength(), file.getBody());
        files.add(multipartFile);
        documentsService.addDocuments(files, documentType, IPRS_PATENT.name(), applicationId);
    }


    public ByteArrayResource generatePatentExaminerReports(Long applicationId, String documentType) throws IOException {
        log.info(" first application id {}  report type {}", applicationId, documentType);

        Map<String, Object> jasperParams = new HashMap<>();
        jasperParams.put("APPLICATION_ID", applicationId);
        ApplicationCheckingReport reportEntity =
                applicationCheckingReportService
                        .findByApplicationInfoIdAndReportType(applicationId, ReportsType.valueOf(documentType));

        if (Objects.isNull(reportEntity)) {
            reportEntity = new ApplicationCheckingReport();
            reportEntity.setReportType(ReportsType.valueOf(documentType));
            reportEntity.setApplicationInfo(new ApplicationInfo(applicationId));

            reportEntity = applicationCheckingReportService.insert(reportEntity);
        }
        jasperParams.put("REPORT_ID", reportEntity.getId());
        ReportRequestDto dto = ReportRequestDto.builder().fileName(documentType).params(jasperParams).build();
        return jasperReportsClient.exportToPdf(dto, documentType).getBody();
    }

    @Override
    @SneakyThrows
    public List<DocumentDto> generateJasperPdfForSupportServicesWithNoApplicationId(ReportRequestDto dto, String documentType) {
        Long serviceId = Long.valueOf(dto.getParams().get("SERVICE_ID").toString());
        ApplicationCheckingReport reportEntity = applicationCheckingReportService.findBySupportServicesTypeIdAndReportType(serviceId, ReportsType.valueOf(documentType));
        if (Objects.isNull(reportEntity)){
            reportEntity=new ApplicationCheckingReport();
            reportEntity.setSupportServicesType(new ApplicationSupportServicesType(serviceId));
            reportEntity.setReportType(ReportsType.valueOf(documentType));
            reportEntity= applicationCheckingReportService.insert(reportEntity);
        }
        Map<String, Object> jasperParams = dto.getParams();
        jasperParams.put("REPORT_ID", reportEntity.getId());
        dto.setParams(jasperParams);
        ResponseEntity<ByteArrayResource> file = jasperReportsClient.exportToPdf(dto, documentType);
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile =
                new CustomMultipartFile(serviceId.toString(), documentType + ".jrxml",
                        "application/pdf", false, file.getHeaders().getContentLength(), file.getBody());
        files.add(multipartFile);
        List<DocumentDto> documentDtos = documentsService.addDocuments(files, documentType, IPRS_PATENT.name(), null);
        reportEntity.setDocumentId(documentDtos.get(0).getId());
        applicationCheckingReportService.insert(reportEntity);
        return documentDtos;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void setPatentOppositionFlag(Long appId) {
        patentDetailsRepository.setPatentOppositionFlag(appId);
    }
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void setPatentOppositionFlagWithTrue(Long appId) {
        patentDetailsRepository.setPatentOppositionFlagWithTrue(appId);
    }

    @Override
    public Boolean getPatentOpposition(Long appId) {
        return Objects.nonNull(patentDetailsRepository.getPatentOpposition(appId)) ? patentDetailsRepository.getPatentOpposition(appId) : Boolean.FALSE;
    }

    @Override
    @Transactional
    public Long applicantOppositionForCorrectionInvitations(ApplicationNotesReqDto applicationNotesReqDto) {
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.PATENT, applicationNotesReqDto.getApplicationId()).getPayload();
        ApplicationNotesReqDto savedApplicationNotesReqDto = new ApplicationNotesReqDto();
        savedApplicationNotesReqDto.setApplicationId(applicationNotesReqDto.getApplicationId());
        savedApplicationNotesReqDto.setDescription(applicationNotesReqDto.getDescription());
        savedApplicationNotesReqDto.setTaskDefinitionKey(requestTasksDto.getTaskDefinitionKey());
        savedApplicationNotesReqDto.setSectionCode("PT_APPLICANT_OPPOSITION");
        Long savedApplicationNotesId = applicationNotesService.addOrUpdateAppNotes(savedApplicationNotesReqDto);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(new HashMap<>());
        completeTaskRequestDto.setTaskDefinitionKey(requestTasksDto.getTaskDefinitionKey());
        completeTaskRequestDto.setRequestTypeEnum(RequestTypeEnum.PATENT);
        completeTaskRequestDto.setNotes(applicationNotesReqDto.getDescription());
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
        applicationInfoService.changeApplicationStatusId(applicationNotesReqDto.getApplicationId(), "REPLY_TO_FORMAL_PROCESS_WITH_OPPOSITION_PAT");
        return savedApplicationNotesId;
    }

    @Override
    public String getPTLastApplicantOppositionForInvitationCorrection(Long appId, String taskKey) {
        return applicationNotesService.getPTLastApplicantOppositionForInvitationCorrection(appId,taskKey);
    }

}
