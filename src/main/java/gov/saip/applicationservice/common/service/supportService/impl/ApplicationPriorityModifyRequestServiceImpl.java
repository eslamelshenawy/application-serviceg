package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityModifyRequestLightDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequest;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequestDetails;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.supportService.ApplicationPriorityModifyRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationPriorityService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityModifyRequestDetailsService;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityModifyRequestService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.SupportServiceType.PATENT_PRIORITY_MODIFY;


@Service
@RequiredArgsConstructor
public class ApplicationPriorityModifyRequestServiceImpl extends SupportServiceRequestServiceImpl<ApplicationPriorityModifyRequest>
        implements ApplicationPriorityModifyRequestService
{

    private final ApplicationPriorityModifyRequestRepository applicationPriorityModifyRequestRepository;
    private final ApplicationPriorityModifyRequestDetailsService applicationPriorityModifyRequestDetailsService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationPriorityService applicationPriorityService;
    private final DocumentsService documentsService;
    private final SupportServiceStatusChangeLogService supportServiceStatusChangeLogService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationInfoService applicationInfoService;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return applicationPriorityModifyRequestRepository;
    }

    @Override
    public ApplicationPriorityModifyRequest insert(ApplicationPriorityModifyRequest entity) {
        ApplicationPriorityModifyRequest result = super.insert(PATENT_PRIORITY_MODIFY, entity);
        insertApplicationPriorityModifyRequestDetails(result, entity.getApplicationPriorityModifyRequestDetails());
        processPaymentAndCallback(entity);
        return result;
    }

    private void processPaymentAndCallback(ApplicationPriorityModifyRequest applicationPriorityModifyRequest) {
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceCaller.getCustomerInfoFromRequest();
        ApplicationInfoBaseDto applicationInfoBaseDto = applicationInfoService.getAppBasicInfo(applicationPriorityModifyRequest.getApplicationInfo().getId());
        Double cost = applicationInfoService.calculateServiceCost(customerSampleInfoDto, ApplicationPaymentMainRequestTypesEnum.PATENT_PRIORITY_MODIFY,
                ApplicationCategoryEnum.valueOf(applicationInfoBaseDto.getApplicationCategory().getSaipCode()));

        if (cost == 0) {
            handleZeroCostInvoice(applicationPriorityModifyRequest);
        }
    }

    private void handleZeroCostInvoice(ApplicationPriorityModifyRequest applicationPriorityModifyRequest) {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setApplicationPaymentMainRequestTypesEnum(ApplicationPaymentMainRequestTypesEnum.PATENT_PRIORITY_MODIFY);
        paymentCallBackHandler(applicationPriorityModifyRequest.getId(), applicationNumberGenerationDto);
    }

    @Override
    public ApplicationPriorityModifyRequest update(ApplicationPriorityModifyRequest request) {
        ApplicationPriorityModifyRequest entity = findById(request.getId());
        if(isDraftStatus(entity) || isConditionalRejectionStatus(entity)){
            this.updateEntityFields(entity, request);
            ApplicationPriorityModifyRequest updatedRequest = super.update(entity);
            RequestTasksDto requestTasksDto = this.completeUserTask(entity);
            this.updateRequestStatus(entity.getId(), requestTasksDto);

            return updatedRequest;
        }else{
            throw new BusinessException(Constants.ErrorKeys.REQUEST_STATUS_IS_NOT_VALID, HttpStatus.METHOD_NOT_ALLOWED, null);
        }
    }

    @Override
    public void updateApplicationPriority(ApplicationPriorityModifyRequestLightDto dto) {
        ApplicationPriorityModifyRequest modifyRequestEntity = findById(dto.getSupportServiceId());
        applicationPriorityService.hardDeleteByApplicationInfoId(dto.getApplicationId());
        //deleteOldPriorityDocuments(modifyRequestEntity.getApplicationPriorityModifyRequestDetails());
        insertNewApplicationPriority(modifyRequestEntity.getApplicationPriorityModifyRequestDetails(), dto.getApplicationId());
    }

    void deleteOldPriorityDocuments(List<ApplicationPriorityModifyRequestDetails> modifyRequestList){
        for (ApplicationPriorityModifyRequestDetails modifyRequestDetails : modifyRequestList) {
             documentsService.hardDeleteDocumentById(modifyRequestDetails.getPriorityDocument().getId());
             documentsService.hardDeleteDocumentById(modifyRequestDetails.getTranslatedDocument().getId());
        }
    }

    void insertNewApplicationPriority(List<ApplicationPriorityModifyRequestDetails> modifyRequestList, Long appId){
        for (ApplicationPriorityModifyRequestDetails modifyRequestDetails : modifyRequestList) {
            ApplicationPriorityDto applicationPriorityDto = new ApplicationPriorityDto();
            applicationPriorityDto.setCountryId(modifyRequestDetails.getCountryId());
            applicationPriorityDto.setPriorityApplicationNumber(modifyRequestDetails.getPriorityApplicationNumber());
            applicationPriorityDto.setFilingDate(modifyRequestDetails.getFilingDate());
            applicationPriorityDto.setApplicationClass(modifyRequestDetails.getApplicationClass());
            applicationPriorityDto.setPriorityDocument(modifyRequestDetails.getPriorityDocument().getId());
            applicationPriorityDto.setTranslatedDocument(modifyRequestDetails.getTranslatedDocument().getId());
            applicationPriorityDto.setDasCode(modifyRequestDetails.getDasCode());
            applicationPriorityDto.setProvideDocLater(Boolean.FALSE);
            applicationPriorityService.createUpdateApplicationPriority(applicationPriorityDto, appId);
        }
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        ApplicationPriorityModifyRequestPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    public List<Long> insertApplicationPriorityModifyRequestDetails(ApplicationPriorityModifyRequest priorityModifyRequest, List<ApplicationPriorityModifyRequestDetails> priorityModifyRequestDetailsList) {
        List<Long> result = new LinkedList<>();
        for (ApplicationPriorityModifyRequestDetails obj : priorityModifyRequestDetailsList) {
             if(validatePriorityFillingDate(obj)){
                 obj.setApplicationPriorityModifyRequest(priorityModifyRequest);
                 result.add(applicationPriorityModifyRequestDetailsService.insert(obj).getId());
             }
        }
        return result;
    }

    boolean validatePriorityFillingDate(ApplicationPriorityModifyRequestDetails obj){
//        LocalDateTime currentDate = LocalDateTime.now();
//        if(currentDate.isAfter(obj.getFilingDate().
//                plus(configParameterService.getLongByKey("PRIORITY_MODIFY_REQUEST_FILLING_DATE_ALLOWANCE_DAYS"), ChronoUnit.DAYS))){
//            throw new BusinessException(PRIORITY_FILLING_DATE_EXPIRED, HttpStatus.BAD_REQUEST, null);
//        }
        return true;
    }

    private void ApplicationPriorityModifyRequestPaymentCallBack(Long id) {
        ApplicationPriorityModifyRequest entity = findById(id);
        ApplicationInfo applicationInfo = applicationInfoService.findById(entity.getApplicationInfo().getId());
        entity.setApplicationInfo(applicationInfo);
        if (entity.getProcessRequestId() != null) {
            return;
        }
        StartProcessDto startProcessDto = super.prepareSupportServiceProcessRequestPaymentCallback(entity,
                "patent_priority_modify_process", "PATENT_PRIORITY_MODIFY");

        startSupportServiceProcess(entity, startProcessDto);
    }

    private void updateEntityFields(ApplicationPriorityModifyRequest entity, ApplicationPriorityModifyRequest request) {
        if(isConditionalRejectionStatus(entity)){
            entity.setRequestUpdated(true);
        }
        updatePriorityModifyRequestDetails(entity, request.getApplicationPriorityModifyRequestDetails());
    }

    private void updatePriorityModifyRequestDetails(ApplicationPriorityModifyRequest priorityModifyRequest, List<ApplicationPriorityModifyRequestDetails> requestList){
        applicationPriorityModifyRequestDetailsService.hardDeleteByApplicationPriorityModifyRequestId(priorityModifyRequest.getId());
        for (ApplicationPriorityModifyRequestDetails entity : requestList) {
            if(validatePriorityFillingDate(entity)) {
                entity.setApplicationPriorityModifyRequest(priorityModifyRequest);
                applicationPriorityModifyRequestDetailsService.update(entity).getId();
            }
        }
    }

    private boolean isDraftStatus(ApplicationPriorityModifyRequest entity) {
        return SupportServiceRequestStatusEnum.DRAFT.name().equals(entity.getRequestStatus().getCode());
    }

    private boolean isConditionalRejectionStatus(ApplicationPriorityModifyRequest entity) {
        return SupportServiceRequestStatusEnum.CONDITIONAL_REJECTION.name().equals(entity.getRequestStatus().getCode());
    }

    private void updateRequestStatus(Long serviceId, RequestTasksDto requestTasksDto) {
        SupportServiceStatusChangeLogDto statusChangeLogDto = new SupportServiceStatusChangeLogDto();
        statusChangeLogDto.setSupportServicesTypeId(serviceId);
        statusChangeLogDto.setNewStatusCode(SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name());
        statusChangeLogDto.setTaskDefinitionKey(requestTasksDto.getTaskDefinitionKey());
        statusChangeLogDto.setTaskInstanceId(requestTasksDto.getProcessInstanceId());
        supportServiceStatusChangeLogService.insert(statusChangeLogDto);
    }

    public RequestTasksDto completeUserTask(ApplicationPriorityModifyRequest request){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient
                .getTaskByRowIdAndType(RequestTypeEnum.PATENT_PRIORITY_MODIFY, request.getId()).getPayload();

        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);

        return requestTasksDto;
    }
}
