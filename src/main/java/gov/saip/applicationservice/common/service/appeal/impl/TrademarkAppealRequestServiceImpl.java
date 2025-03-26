package gov.saip.applicationservice.common.service.appeal.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.CustomerCodeListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.appeal.TrademarkAppealRequestDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.support_services_enums.TrademarkAppealRequestType;
import gov.saip.applicationservice.common.mapper.appeal.TrademarkAppealRequestMapper;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.appeal.TrademarkAppealRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.appeal.TrademarkAppealRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.appeal.TrademarkAppealRequestService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.JsonUtils;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.SupportServiceType.TRADEMARK_APPEAL_REQUEST;
import static gov.saip.applicationservice.common.enums.support_services_enums.TrademarkAppealRequestType.SUBSTANTIVE_EXAMINATION_REJECTION;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TrademarkAppealRequestServiceImpl extends SupportServiceRequestServiceImpl<TrademarkAppealRequest> implements TrademarkAppealRequestService {

    private final TrademarkAppealRequestRepository trademarkAppealRequestRepository;
    private final CustomerServiceCaller customerServiceCaller;
    private final BPMCallerServiceImpl bpmCallerService;
    private final SupportServiceCustomerService supportServiceCustomerService;
    private final ApplicationInfoService applicationInfoService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final TrademarkAppealRequestMapper trademarkAppealRequestMapper;

    // the name of pending for appeal task will be renamed instead of Activity_14u418i like change trademark image
    private static List<String> APPEAL_RESULT_TASKS = List.of("PENDING_FOR_APPEAL_REQUEST_RESULT_AETIM", "Activity_14u418i");

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return trademarkAppealRequestRepository;
    }


    @Override
    public TrademarkAppealRequest insert(TrademarkAppealRequest entity) {
        Long applicationId = entity.getApplicationInfo().getId();
//        Long parentSupportServiceId =  TrademarkAppealRequestType.CHANGE_TM_IMAGE_SERVICE.equals(entity.getAppealRequestType()) ? entity.getSupportServicesType().getId() : null;
        log.info("start to insert new appeal request for application id {} ", applicationId);
        Long parentService = setSupportServiceTypeForApplication(entity);
        //validateMainProcessAppealTask(applicationId, parentSupportServiceId, requestTasksDto);
        TrademarkAppealRequest inserted = super.insert(TRADEMARK_APPEAL_REQUEST, entity, parentService);
        entity.setRequestStatus(lKSupportServiceRequestStatusService.findByCode(SupportServiceRequestStatusEnum.PENDING_FEES_COMPLAINT.name()));
        handleChangeStatusForMainApplicationOrServiceRequest(entity, applicationId);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
            public void afterCommit() {
                getTaskInMainProcessAndCompleteIfPendingForAppeal(entity);
            }
        });

        return inserted;
    }

    private void getTaskInMainProcessAndCompleteIfPendingForAppeal(TrademarkAppealRequest entity) {
        try {
            RequestTasksDto requestTasksDto = getOpenedTaskInMainProcess(entity);
            if (requestTasksDto.getTaskDefinitionKey().contains("PENDING_FOR_APPEAL_REQUEST")) {
                completeAppealTaskInMainProcess(requestTasksDto);
                log.info("main process pending for appeal request completed now the main process waiting for appeal result the appeal id is {} ", entity.getId());
            }
        } catch (Exception ex) {
            log.info("complete task in main process has failed appeal id is {} ", entity.getId());
        }
    }

    private Long setSupportServiceTypeForApplication(TrademarkAppealRequest entity) {
        Long id = null;
        TrademarkAppealRequestType appealRequestType = entity.getAppealRequestType();
        if (TrademarkAppealRequestType.CHANGE_TM_IMAGE_SERVICE.equals(appealRequestType)) {
            id = applicationSupportServicesTypeService.getLastSupportServiceByTypeAndApplicationِAndStatus(SupportServiceType.EDIT_TRADEMARK_IMAGE, entity.getApplicationInfo().getId(), List.of(SupportServiceRequestStatusEnum.REJECTED.name()));
            entity.setSupportServicesType(new ApplicationSupportServicesType(id));
        }
        return id;
    }

    private void handleChangeStatusForMainApplicationOrServiceRequest(TrademarkAppealRequest entity, Long applicationId) {
        if (entity.getAppealRequestType().isApplicationRegistration()) {
            applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE.name(), AppStatusChangeLogDescriptionCode.APPEAL_REQUEST_REQUESTED.name(), applicationId);
            //applicationInfoService.updateStatusByCodeAndId(applicationId, ApplicationStatusEnum.AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE.name());
        }
    }


    private RequestTasksDto getOpenedTaskInMainProcess(TrademarkAppealRequest entity) {
        Long mainProcessRowID = getMainProcessRowID(entity);
        RequestTasksDto taskByRowIdAndTypeIfExists = bpmCallerService.getTaskByRowIdAndTypeIfExists(entity.getAppealRequestType().getRequestType(), mainProcessRowID);
        if (taskByRowIdAndTypeIfExists == null) {
            log.info("there is no task for id {} of type {}", mainProcessRowID, entity.getAppealRequestType().getRequestType());
           throw new BusinessException(Constants.ErrorKeys.VALIDATION_CANNOT_APPEAL, HttpStatus.NOT_FOUND);
        }
        return taskByRowIdAndTypeIfExists;
    }
    
    public RequestTasksDto getOpenedAppealTaskInMainProcess(Long application, Long parentService) {
        Long rowId = parentService != null ? parentService : application;
        RequestTypeEnum type = parentService != null ? RequestTypeEnum.EDIT_TRADEMARK_IMAGE : RequestTypeEnum.TRADEMARK;
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndTypeIfExists(type, rowId);
        validateMainProcessAppealTask(application, parentService, requestTasksDto);
        return requestTasksDto;
    }

    private void validateMainProcessAppealTask(Long applicationId, Long parentSupportServiceId, RequestTasksDto requestTasksDto) {
        if (requestTasksDto == null) {
            log.info("requestTasksDto is null in the main process of application {} or support service {}", applicationId, parentSupportServiceId);
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_CANNOT_APPEAL, HttpStatus.BAD_REQUEST, new String[]{null, SupportServiceType.APPEAL_REQUEST.toString()});
        }

        // make sure no appeal requests in DB for this application this to handle case PENDING_FOR_APPEAL_REQUEST task completed but the appeal request not saved in DB and the customer try to insert a new one
        // this issue in production so we added this code to handle it in the future no need to it because we moved complete task after transaction commit while inserting the  request

        if (APPEAL_RESULT_TASKS.contains(requestTasksDto.getTaskDefinitionKey())) {
            boolean applicationOrSupportServiceAppealed = trademarkAppealRequestRepository.isApplicationOrSupportServiceAppealed(applicationId, parentSupportServiceId);
            if (applicationOrSupportServiceAppealed) {
                log.info("the main process of application {} or support service {} not have appeal task ", applicationId, parentSupportServiceId);
                throw new BusinessException(Constants.ErrorKeys.VALIDATION_CANNOT_APPEAL, HttpStatus.BAD_REQUEST, new String[]{null, SupportServiceType.APPEAL_REQUEST.toString()});
            }
        } else if (!requestTasksDto.getTaskDefinitionKey().contains("PENDING_FOR_APPEAL_REQUEST")) {
            log.info("the main process of application {} or support service {} not have appeal task ", applicationId, parentSupportServiceId);
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_CANNOT_APPEAL, HttpStatus.BAD_REQUEST, new String[]{null, SupportServiceType.APPEAL_REQUEST.toString()});
        }
    }

    private static Long getMainProcessRowID(TrademarkAppealRequest entity) {
        return (entity.getSupportServicesType() != null && entity.getSupportServicesType().getId() != null)
                ? entity.getSupportServicesType().getId() : entity.getApplicationInfo().getId();
    }

    private Long getRemainingTimeToAppeal(RequestTasksDto requestTasksDto) {
        OffsetDateTime dueDate = Utilities.convertStringIntoOffsetDateTime(requestTasksDto.getDue(), Constants.DateFormats.DATE_FORMAT_WITH_X_OFFSET.getFormat());
        return Duration.between(LocalDateTime.now(), dueDate).toDays();
    }

    private void completeAppealTaskInMainProcess(RequestTasksDto requestTasksDto) {
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.addVariableToVarMap("completedAppealDate", LocalDateTime.now());
        completeTaskRequestDto.addVariableToVarMap("appealRemainingDays", getRemainingTimeToAppeal(requestTasksDto));
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    @Override
    public TrademarkAppealRequest update(TrademarkAppealRequest entity) {
        log.info("start to update appeal request with id ", entity.getId());
        TrademarkAppealRequest appealRequest = trademarkAppealRequestRepository.findById(entity.getId()).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
        RequestTasksDto tasksDto = getTaskByProcessRequestId(appealRequest);
        checkAppealRequestIsValidToUpdate(tasksDto, appealRequest);
        appealRequest.setAppealReason(entity.getAppealReason());
        appealRequest.setDocuments(entity.getDocuments());

        // prevent change status & complete task if appeal request is not paid
        if (SupportServicePaymentStatus.PAID.equals(appealRequest.getPaymentStatus())) {
            appealRequest.setRequestStatus(lKSupportServiceRequestStatusService.findByCode(SupportServiceRequestStatusEnum.COMPLAINANT_TO_COMMITTEE.name()));
            completeTaskAfterCustomerUpdatedRequest(appealRequest);
        } else {
            // complete pending task in main process [double check to handle complete task failure in insert request]
            getTaskInMainProcessAndCompleteIfPendingForAppeal(appealRequest);
        }

        TrademarkAppealRequest update = super.update(appealRequest);
        return update;
    }

    private RequestTasksDto getTaskByProcessRequestId(TrademarkAppealRequest appealRequest) {
        if (appealRequest.getProcessRequestId() != null) {
            return bpmCallerService.getCurrentTaskByRequestId(appealRequest.getProcessRequestId());
        }
        return null;
    }

    private static void checkAppealRequestIsValidToUpdate(RequestTasksDto tasksDto, TrademarkAppealRequest appealRequest) {
        String taskDefinitionKey = tasksDto == null ? null : tasksDto.getTaskDefinitionKey();
        boolean isUserHasTaskOrNotPaid = "UPDATE_REQUEST_DOCUMENTS_TO_COORINATOR_TMAPPREQ".equals(taskDefinitionKey)
                || "UPDATE_REQUEST_DOCUMENTS_TO_HEAD_TMAPPREQ".equals(taskDefinitionKey)
                || SupportServicePaymentStatus.UNPAID.equals(appealRequest.getPaymentStatus());

        if (!isUserHasTaskOrNotPaid) {
            log.info("can not update this appeal request because it's paid and no task assigned to the user");
            throw new BusinessException("APPEAL_REQUEST_CAN_NOT_MODIFIED", HttpStatus.BAD_REQUEST);
        }
    }

    private void completeTaskAfterCustomerUpdatedRequest(TrademarkAppealRequest appealRequest) {
        try {
            RequestTasksDto requestTasksDto = bpmCallerService.getCurrentTaskByRequestId(appealRequest.getProcessRequestId());
            CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
            completeTaskRequestDto.setVariables(new HashMap<>());
            bpmCallerService.completeTaskToUser(requestTasksDto.getTaskId(), completeTaskRequestDto);
            log.info("customer updated the request and his task has completed");
        } catch (Exception e) {
            log.info("there is an issue in complete task after update may be no task or update in request that has no process started");
        }
    }

    @Override
    public void updateDepartmentReply(Long id, String reply) {
        trademarkAppealRequestRepository.updateDepartmentReply(id, reply);
    }

    @Override
    public void updateFinalDecision(Long id, String notes) {
        TrademarkAppealRequest appealRequest = trademarkAppealRequestRepository.findById(id).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
        appealRequest.setFinalDecisionNotes(notes);
        trademarkAppealRequestRepository.save(appealRequest);
        resetStatusIfRequestRejected(appealRequest);
    }

    private void resetStatusIfRequestRejected(TrademarkAppealRequest appealRequest) {
        if (SupportServiceRequestStatusEnum.REJECTED.name().equals(appealRequest.getRequestStatus().getCode()) ||
            SupportServiceRequestStatusEnum.REJECTED_BY_APPEAL_COMMITTEE.name().equals(appealRequest.getRequestStatus().getCode())) {
            updateMainRequestStatusAfterRejection(appealRequest);
        } else {
            updateMainRequestStatusAfterApproval(appealRequest);
        }
    }

    private void updateMainRequestStatusAfterRejection(TrademarkAppealRequest appealRequest) {
        switch (appealRequest.getAppealRequestType()) {
            case SUBSTANTIVE_EXAMINATION_REJECTION:
            case ACCEPTANCE_WITH_CONDITION:
                applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.REJECTED_BY_THE_GRIEVANCES_COMMITTEE.name(), AppStatusChangeLogDescriptionCode.APPEAL_REQUEST_REJECTED.name(),appealRequest.getApplicationInfo().getId());
                //applicationInfoService.updateStatusByCodeAndId(appealRequest.getApplicationInfo().getId(), ApplicationStatusEnum.REJECTED_BY_THE_GRIEVANCES_COMMITTEE.name());
                break;
        }
    }

    private void updateMainRequestStatusAfterApproval(TrademarkAppealRequest appealRequest) {
        switch (appealRequest.getAppealRequestType()) {
            case CHANGE_TM_IMAGE_SERVICE:
                Integer newStatusId = lKSupportServiceRequestStatusService.findIdByCode(SupportServiceRequestStatusEnum.PENDING_IMG_FEES_MOD_PUB);
                applicationSupportServicesTypeService.updateRequestStatusById(appealRequest.getSupportServicesType().getId(), newStatusId);
                break;
            case SUBSTANTIVE_EXAMINATION_REJECTION:
            case ACCEPTANCE_WITH_CONDITION:
                applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.PUBLICATION_FEES_ARE_PENDING.name(), AppStatusChangeLogDescriptionCode.APPEAL_REQUEST_ACCEPTED.name(), appealRequest.getApplicationInfo().getId());
                //applicationInfoService.updateStatusByCodeAndId(appealRequest.getApplicationInfo().getId(), ApplicationStatusEnum.PUBLICATION_FEES_ARE_PENDING.name());
                break;
        }
    }

    @Override
    public TrademarkAppealRequestType getTrademarkAppealRequestType(Long applicationId) {
        String applicationStatus = applicationInfoService.getApplicationStatus(applicationId);
        switch (ApplicationStatusEnum.valueOf(applicationStatus)) {
            case ACCEPTANCE:
            case THE_TRADEMARK_IS_REGISTERED:
                return TrademarkAppealRequestType.CHANGE_TM_IMAGE_SERVICE;
            default: return getTrademarkRegistertionAppealType(applicationId);
        }
    }

    private TrademarkAppealRequestType getTrademarkRegistertionAppealType(Long applicationId) {
        boolean isExists = applicationStatusChangeLogService.isExistsByAppIdTaskDefinitionKey(applicationId, "APPROVE_ACCEPT_WITH_CONDITION_TM");
        if (isExists) {
            return TrademarkAppealRequestType.ACCEPTANCE_WITH_CONDITION;
        } else {
            return SUBSTANTIVE_EXAMINATION_REJECTION;
        }
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        TrademarkAppealRequest appealRequest = trademarkAppealRequestRepository.findById(id).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (appealRequest.getProcessRequestId() != null) {
            return;
        }
        // complete pending task in main process [double check to handle complete task failure in insert request]
        getTaskInMainProcessAndCompleteIfPendingForAppeal(appealRequest);
        startProcess(appealRequest);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }



    private void startProcess(TrademarkAppealRequest entity) {
        List<CustomerSampleInfoDto> customerList = getRequestCreatorInfo(entity);
        CustomerSampleInfoDto customerInfo = customerList.get(0);

        RequestTasksDto openedTaskInMainProcess = getOpenedTaskInMainProcess(entity);
        Map<ApplicationCustomerType, String> serviceCustomerCodes = supportServiceCustomerService.getServiceCustomerCodes(entity.getId());
        Map<String, Object> vars = new HashMap<>();
        vars.put("CREATED_BY_CUSTOMER_CODE", entity.getCreatedByCustomerCode());
        vars.put("SERVICE_CUSTOMERS", JsonUtils.convertToJson(serviceCustomerCodes));
        vars.put("TM_TITLE_AR", entity.getApplicationInfo().getTitleAr());
        vars.put("TM_TITLE_EN", entity.getApplicationInfo().getTitleEn());
        vars.put("APPLICATION_NUMBER", entity.getApplicationInfo().getApplicationNumber());
        vars.put("REQUEST_NUMBER", entity.getRequestNumber());
        vars.put("APPEAL_REQUEST_TYPE_AR", entity.getAppealRequestType().getNameAr());
        vars.put("APPEAL_REQUEST_TYPE_EN", entity.getAppealRequestType().getNameEn());
        vars.put("APPEAL_REQUEST_TYPE", entity.getAppealRequestType().name());
        vars.put("OPENED_PROCESS_INSTANCE_ID", openedTaskInMainProcess.getProcessInstanceId());
        vars.put("APPLICATION_REQUEST_NUMBER", entity.getApplicationInfo().getApplicationRequestNumber());

        StartProcessDto startProcessDto =  StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(customerInfo.getNameAr())
                .fullNameEn(customerInfo.getNameEn())
                .mobile(customerInfo.getMobile())
                .email(customerInfo.getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName("trademark_appeal_request_process")
                .requestTypeCode(RequestTypeEnum.TRADEMARK_APPEAL_REQUEST.name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .applicationIdColumn(entity.getApplicationInfo() == null || entity.getApplicationInfo().getId() == null ? null : entity.getApplicationInfo().getId().toString())
                .identifier(entity.getId().toString())
                .requestNumber(entity.getRequestNumber())
                .variables(vars)
                .build();
        startSupportServiceProcess(entity, startProcessDto);
        handleChangeStatusForMainApplicationOrServiceRequestAfterPayment(entity);
    }

    private void handleChangeStatusForMainApplicationOrServiceRequestAfterPayment(TrademarkAppealRequest entity) {
        if (entity.getAppealRequestType().isApplicationRegistration()) {
            applicationStatusChangeLogService.changeApplicationStatusAndLog(ApplicationStatusEnum.COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE.name(), AppStatusChangeLogDescriptionCode.APPEAL_REQUEST_PAID.name(), entity.getApplicationInfo().getId());
            //applicationInfoService.updateStatusByCodeAndId(entity.getApplicationInfo().getId(), ApplicationStatusEnum.COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE.name());
        }
    }

    private List<CustomerSampleInfoDto> getRequestCreatorInfo(TrademarkAppealRequest entity) {
        List<CustomerSampleInfoDto> customerList = customerServiceCaller.getCustomerByListOfCode(new CustomerCodeListDto(List.of(entity.getCreatedByCustomerCode()))).getPayload();
        if (customerList == null || customerList.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        return customerList;
    }

    @Override
    public void handleApplicantTaskTimeout(Long id) {
        this.updateRequestStatusByCode(id, SupportServiceRequestStatusEnum.WITHDRAWAL);
        updateMainRequestStatusAfterRejection(trademarkAppealRequestRepository.findById(id).get());
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.COMPLAINANT_TO_COMMITTEE;
    }

    private ApplicationStatusChangeLog createApplicationLog(Long applicationId, String descriptionCode, String status) {
        ApplicationStatusChangeLog changeLog = new ApplicationStatusChangeLog();
        changeLog.setNewStatusByCode(status);
        changeLog.setDescriptionCode(descriptionCode);
        changeLog.setApplicationById(applicationId);
        return changeLog;
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public TrademarkAppealRequestDto findDetailsByById(Long aLong) {
        TrademarkAppealRequest appealRequest = super.findById(aLong);
        TrademarkAppealRequestDto requestDto = trademarkAppealRequestMapper.map(appealRequest);

        String createdByCustomerCode = appealRequest.getCreatedByCustomerCode();
        if (createdByCustomerCode == null) {
            return requestDto;
        }

        CustomerSampleInfoDto clientCustomerSampleInfoDto = customerServiceCaller.getCustomerInfoByCustomerCode(createdByCustomerCode);

        if (clientCustomerSampleInfoDto == null) {
            return requestDto;
        }

        requestDto.getApplicationInfo().setOwnerNameAr(clientCustomerSampleInfoDto.getNameAr());
        requestDto.getApplicationInfo().setOwnerNameEn(clientCustomerSampleInfoDto.getNameEn());
        requestDto.getApplicationInfo().setMobileCode(clientCustomerSampleInfoDto.getMobileCountryCode());
        requestDto.getApplicationInfo().setMobileNumber(clientCustomerSampleInfoDto.getMobile());
        requestDto.getApplicationInfo().setAddress(clientCustomerSampleInfoDto.getCustomerAddressAsString());

        return requestDto;
    }
}
