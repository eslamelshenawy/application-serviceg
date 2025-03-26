package gov.saip.applicationservice.common.service.appeal.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.appeal.AppealDetailsDto;
import gov.saip.applicationservice.common.dto.appeal.AppealRequestDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.appeal.AppealCheckerDecision;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.mapper.appeal.AppealCommitteeOpinionMapper;
import gov.saip.applicationservice.common.mapper.appeal.AppealRequestMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.appeal.AppealRequestRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.appeal.AppealRequestService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;


@Service
@RequiredArgsConstructor
public class AppealRequestServiceImpl extends SupportServiceRequestServiceImpl<AppealRequest> implements AppealRequestService {

    private final AppealRequestRepository appealRequestRepository;
    private final AppealCommitteeOpinionMapper appealCommitteeOpinionMapper;
    private final ApplicationAgentService applicationAgentService;
    private final ApplicationInfoService applicationInfoService;
    private final DocumentMapper documentMapper;
    private final DocumentsService documentsService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final BPMCallerServiceImpl bpmCallerService;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final AppealRequestMapper appealRequestMapper;
    private final ApplicationUserService applicationUserService;
    private final CustomerServiceCaller customerServiceCaller;

    @Lazy
    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return appealRequestRepository;
    }

    @Override
    @Transactional
    public AppealRequest insert(AppealRequest entity) {

        Long id = entity.getApplicationInfo().getId();
        ApplicationInfo applicationInfo = applicationInfoService.findById(id);
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndType(getRequestTypeEnum(applicationInfo.getCategory().getSaipCode()), entity.getApplicationInfo().getId()).getPayload();
        Long remainingTime = validateDueDate(requestTasksDto);

        if (remainingTime > 0) {
            AppealRequest appealRequest= super.insert(SupportServiceType.APPEAL_REQUEST, entity);
            applicationInfoService.changeApplicationStatusId(entity.getApplicationInfo().getId(), ApplicationStatusEnum.COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE.name());
            processPaymentAndCallback(appealRequest);
            completeTaskAppeal( remainingTime,requestTasksDto);
            return appealRequest;
        }

        String[] param = {applicationInfo.getApplicationNumber(), SupportServiceType.APPEAL_REQUEST.toString()};
        throw new BusinessException(Constants.ErrorKeys.APPLICATION_NOT_MEET_SERVICE_CRITERIA, HttpStatus.BAD_REQUEST, param);
    }

    private void processPaymentAndCallback(AppealRequest appealRequest) {
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceCaller.getCustomerInfoFromRequest();
        ApplicationInfoBaseDto applicationInfoBaseDto = applicationInfoService.getAppBasicInfo(appealRequest.getApplicationInfo().getId());
        Double cost = applicationInfoService.calculateServiceCost(customerSampleInfoDto, ApplicationPaymentMainRequestTypesEnum.APPEAL_REQUEST,
                ApplicationCategoryEnum.valueOf(applicationInfoBaseDto.getApplicationCategory().getSaipCode()));

        if (cost == 0) {
            handleZeroCostInvoice(appealRequest);
        }
    }

    private void handleZeroCostInvoice(AppealRequest appealRequest) {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setApplicationPaymentMainRequestTypesEnum(ApplicationPaymentMainRequestTypesEnum.APPEAL_REQUEST);
        processAppealRequestPayment(applicationNumberGenerationDto, appealRequest.getId());
    }

    private void completeTaskAppeal(Long remainingTime,RequestTasksDto requestTasksDto)
    {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> completedAppealDate = new HashMap<>();
        completedAppealDate.put("value", LocalDateTime.now());
        Map<String, Object> appealRemainingDays = new HashMap<>();
        appealRemainingDays.put("value", remainingTime);
        variables.put("completedAppealDate", completedAppealDate);
        variables.put("appealRemainingDays", appealRemainingDays);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(variables);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    private Long validateDueDate(RequestTasksDto requestTasksDto) {
        if (requestTasksDto.getTaskDefinitionKey().contains("PENDING_FOR_APPEAL_REQUEST")) {
            if (Objects.nonNull(requestTasksDto.getDue())) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime taskDueDate = parseDateTime(requestTasksDto.getDue());
                Long remainingTime = calculateDuration(currentDateTime, taskDueDate).toDays();
                if (remainingTime > 0)
                    return remainingTime;
            }
        }
        return -10000L;
    }
    private Duration calculateDuration(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime); // end - start
    }

    @Override
    @Transactional
    public Long addCheckerDecision(AppealRequestDto dto) {
        LKSupportServiceRequestStatus status = null;
        SupportServiceRequestStatusEnum tmpStatus = null;

        switch(dto.getCheckerDecision()) {
            case REJECTED: tmpStatus = SupportServiceRequestStatusEnum.REJECTED; break;
            case SEND_BACK: tmpStatus = SupportServiceRequestStatusEnum.REQUEST_CORRECTION; break;
            default: tmpStatus = SupportServiceRequestStatusEnum.UNDER_PROCEDURE; break;
        }

        status = getLKSupportServiceRequestStatusByCode(tmpStatus);
        activityLogService.insertSupportServicesActivityLogStatus(dto.getTaskId(), dto.getTaskDefinitionKey(), dto.getId(), status.getCode());
        appealRequestRepository.addCheckerDecision(dto.getId(), dto.getCheckerDecision(), dto.getCheckerFinalNotes(), status);

        if(status.getCode().equals(SupportServiceRequestStatusEnum.REJECTED.name())) {
            ApplicationSupportServicesType applicationSupportServicesType = applicationSupportServicesTypeService.getSupportServiceTypeById(dto.getId());
            applicationStatusChangeLogService.changeApplicationStatusAndLog(APPEAL_FORMAL_REJECTION.name(), null, applicationSupportServicesType.getApplicationInfo().getId());
        }
        return dto.getId();
    }

    private LKSupportServiceRequestStatus getLKSupportServiceRequestStatusByCode(SupportServiceRequestStatusEnum supportServiceRequestStatusEnum){
        return lKSupportServiceRequestStatusService.findByCode(supportServiceRequestStatusEnum.name());
    }

    @Override
    @Transactional
    public Long addOfficialLetter(AppealRequestDto dto) {
        insertAttachedDocument(dto);

        appealRequestRepository.addOfficialLetter(dto.getId(), dto.getOfficialLetterComment());
        return dto.getId();
    }


    @Override
    @Transactional
    public Long addAppealCommitteeDecision(AppealRequestDto dto) {
        AppealCommitteeDecision appealCommitteeDecision = dto.getAppealCommitteeDecision();
        SupportServiceRequestStatusEnum statusCode =
                AppealCommitteeDecision.ACCEPTED.equals(appealCommitteeDecision) ?
                        SupportServiceRequestStatusEnum.APPROVED:
                        SupportServiceRequestStatusEnum.REJECTED;

        insertAttachedDocument(dto);
        Integer statusId = lKSupportServiceRequestStatusService.findIdByCode(statusCode);
        appealRequestRepository.addAppealCommitteeDecision(dto.getId(), appealCommitteeDecision, dto.getAppealCommitteeDecisionComment(), statusId);

        ApplicationSupportServicesType applicationSupportServicesType = applicationSupportServicesTypeService.getSupportServiceTypeById(dto.getId());
        LKSupportServiceRequestStatus lkSupportServiceRequestStatus = new LKSupportServiceRequestStatus();

        if(statusCode.equals(SupportServiceRequestStatusEnum.REJECTED)){
            applicationStatusChangeLogService.changeApplicationStatusAndLog(REJECTED_BY_THE_GRIEVANCES_COMMITTEE.name(), null, applicationSupportServicesType.getApplicationInfo().getId());
            lkSupportServiceRequestStatus = lKSupportServiceRequestStatusService.getStatusByCode(SupportServiceRequestStatusEnum.REJECTED_BY_APPEAL_COMMITTEE.name());
        }
        else {
            applicationStatusChangeLogService.changeApplicationStatusAndLog(ACCEPTED_BY_THE_GRIEVANCES_COMMITTEE.name(), null, applicationSupportServicesType.getApplicationInfo().getId());
            lkSupportServiceRequestStatus = lKSupportServiceRequestStatusService.getStatusByCode(SupportServiceRequestStatusEnum.ACCEPTED_BY_COMMITTEE.name());
        }

        activityLogService.insertSupportServicesActivityLogStatus(dto.getTaskId(), dto.getTaskDefinitionKey(), dto.getId(), lkSupportServiceRequestStatus.getCode());
        applicationSupportServicesType.setRequestStatus(lkSupportServiceRequestStatus);
        applicationSupportServicesTypeService.update(applicationSupportServicesType);
        return dto.getId();
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public AppealDetailsDto getTradeMarkAppealDetails(Long appealId) {
        AppealRequest appealRequest = appealRequestRepository.findById(appealId).get();
        ApplicationInfo applicationInfo = applicationInfoService.findById(appealRequest.getApplicationInfo().getId());
        ApplicationAgentSummaryDto mainApplicantAgent = applicationAgentService.getCurrentApplicationAgentSummary(appealRequest.getApplicationInfo().getId());
        ApplicantsDto mainApplicantInfo = applicationInfoService.listMainApplicant(appealRequest.getApplicationInfo().getId());
        List<DocumentDto> allDocuments = documentMapper.mapToDtoss(appealRequest.getDocuments());
        String typeName = "Trademark Image/voice";
        allDocuments.add(documentsService.getTradeMarkApplicationDocument(applicationInfo.getId(), typeName));

        AppealDetailsDto appealDetailsDto = AppealDetailsDto.builder()
                .requestNumber(appealRequest.getRequestNumber())
                .requestStatus(appealRequest.getRequestStatus())
                .createdDate(appealRequest.getCreatedDate())
                .documents(allDocuments)
                .reasons(appealRequest.getAppealReason())
                .applicationNumber(applicationInfo.getApplicationNumber())
                .appStatusCode(applicationInfo.getApplicationStatus().getCode())
                .appStatusEn(applicationInfo.getApplicationStatus().getIpsStatusDescEn())
                .appStatusAr(applicationInfo.getApplicationStatus().getIpsStatusDescAr())
                .applicationTitleAr(applicationInfo.getTitleAr())
                .applicationTitleEn(applicationInfo.getTitleEn())
                .appealCommitteeDecision(appealRequest.getAppealCommitteeDecision() == null ? null : appealRequest.getAppealCommitteeDecision().name())
                .officialLetterComment(appealRequest.getOfficialLetterComment())
                .appealCommitteeDecisionComment(appealRequest.getAppealCommitteeDecisionComment() == null ? null : appealRequest.getAppealCommitteeDecisionComment())
                .applicantNameAr(mainApplicantInfo.getNameAr())
                .applicantNameEn(mainApplicantInfo.getNameEn())
                .applicantMobile(mainApplicantInfo.getMobile())
                .applicantAddress(mainApplicantInfo.getAddress())
                .agentNameAr(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameAr() : "")
                .agentNameEn(Objects.nonNull(mainApplicantAgent) ? mainApplicantAgent.getNameEn() : "")
                .appealCommitteeOpinionDtos(appealCommitteeOpinionMapper.map(appealRequest.getOpinions()))
                .checkerFinalNotes(appealRequest.getCheckerFinalNotes())
                .checkerDecision(appealRequest.getCheckerDecision() == null ? null : appealRequest.getCheckerDecision().name())
                .build();
        appealDetailsDto.getAppealCommitteeOpinionDtos().forEach(dto -> {
            dto.setDocumentDto(documentsService.findDocumentById(dto.getDocumentId()));
        });

        return appealDetailsDto;
    }

    @Override
    public Long updateAppealRequest(AppealRequestDto appealRequestDto, String taskId) {
        String taskDefinitionKey = bpmCallerFeignClient.getTaskDefinitionKeyByTaskId(taskId).getPayload();
        AppealRequest appealRequest = appealRequestMapper.unMap(appealRequestDto);
        AppealRequest updatedAppealRequest = findById(appealRequest.getId());
        List<DocumentDto> documentDtoList = documentsService.findDocumentByIds(appealRequestDto.getDocumentIds());
        updatedAppealRequest.setAppealReason(appealRequest.getAppealReason());
        updatedAppealRequest.setDocuments(documentMapper.mapRequestToEntity(documentDtoList));
        appealRequestRepository.save(updatedAppealRequest);
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);

        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(taskId, completeTaskRequestDto);
        activityLogService.insertSupportServicesActivityLogStatus(taskId, taskDefinitionKey, updatedAppealRequest.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name());
        return appealRequest.getId();

    }

    @Override
    public void processAppealRequestPayment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        Optional<AppealRequest> optionalAppealRequest = this.getById(id);
        if (optionalAppealRequest.isEmpty())
            return;

        AppealRequest appealRequest = optionalAppealRequest.get();
        if (!SupportServicePaymentStatus.UNPAID.equals(appealRequest.getPaymentStatus()))
            throw new BusinessException(Constants.ErrorKeys.GENERAL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, null);

        super.paymentCallBackHandler(id, applicationNumberGenerationDto);

        ApplicationInfo applicationInfo = applicationInfoService.findById(appealRequest.getApplicationInfo().getId());
        ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(applicationInfo.getId());

        // start the process
        ProcessRequestDto processRequestDto = new ProcessRequestDto();
        processRequestDto.setProcessId("application_appeal_process");

        Map<String, Object> vars = new HashMap<>();
        // set complainer details
        vars.put("fullNameAr", applicantsDto.getNameAr());
        vars.put("fullNameEn", applicantsDto.getNameEn());
        vars.put("email", applicantsDto.getEmail());
        vars.put("mobile", applicantsDto.getMobile());
        vars.put("APPLICANT_USER_NAME", applicantsDto.getUsername());

        // application
        vars.put("identifier", applicationInfo.getIpcNumber());
        vars.put("REQUESTS_APPLICATION_ID_COLUMN", String.valueOf(applicationInfo.getId()));
        vars.put("applicationCategory", applicationInfo.getCategory().getSaipCode());
        vars.put("APPLICATION_TITLE_AR", applicationInfo.getTitleAr());
        vars.put("APPLICATION_TITLE_EN", applicationInfo.getTitleEn());
        vars.put("APPLICATION_NUMBER", applicationInfo.getApplicationNumber());

        // APPEAL
        vars.put("id", id.toString());
        vars.put("requestTypeCode", RequestTypeEnum.APPEAL_REQUEST.name());

        // get app checker to exclude
        String checkerUsername = applicationUserService.getUsernameByAppAndRole(applicationInfo.getId(), ApplicationUserRoleEnum.CHECKER);
        vars.put("PREVIOUS_CHECKER_USER_NAME", checkerUsername);

        String checkerGroup = "";
        String HeadCheckerGroup = "";
        Long categoryId = 0L;

        switch (ApplicationCategoryEnum.valueOf(applicationInfo.getCategory().getSaipCode())) {
            case PATENT -> {
                checkerGroup = "PATENT_CHECKER";
                HeadCheckerGroup = "PATENT_HEAD_OF_CHECKER";
                categoryId = 1L;
            }
            case INDUSTRIAL_DESIGN -> {
                checkerGroup = "INDUSTRIAL_DESIGN_CHECKER";
                HeadCheckerGroup = "INDUSTRIAL_DESIGN_HEAD_OF_CHECKER";
                categoryId = 2L;
            }
            case TRADEMARK -> {
                checkerGroup = "TRADEMARK_CHECKER";
                HeadCheckerGroup = "TRADEMARK_HEAD_OF_CHECKER";
                categoryId = 5L;
            }
            default -> {
            }
        }

        vars.put("CHECKER_GROUP", checkerGroup);
        vars.put("HEAD_CHECKER_GROUP", HeadCheckerGroup);

        processRequestDto.setVariables(vars);
        processRequestDto.setCategoryId(categoryId);
        StartProcessResponseDto startProcessResponseDto = bpmCallerService.startApplicationProcess(processRequestDto);
        activityLogService.insertSupportServicesActivityLogStatus(startProcessResponseDto.getTaskHistoryUIDto(), appealRequest);
    }

    private void insertAttachedDocument(AppealRequestDto dto) {
        if (Objects.nonNull(dto.getDocumentIds()) && !dto.getDocumentIds().isEmpty())
            dto.getDocumentIds().forEach(docId -> appealRequestRepository.addAppealDocument(dto.getId(), docId));
    }

    private RequestTypeEnum getRequestTypeEnum(String code) {

        if (code.equals("PATENT"))
            return RequestTypeEnum.PATENT;
        if (code.equals("TRADEMARK"))
            return RequestTypeEnum.TRADEMARK;
        if (code.equals("INDUSTRIAL_DESIGN"))
            return RequestTypeEnum.INDUSTRIAL_DESIGN;
        return null;
    }
    private LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
