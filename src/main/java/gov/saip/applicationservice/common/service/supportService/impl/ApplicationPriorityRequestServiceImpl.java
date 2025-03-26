package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.supportService.ApplicationPriorityRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityRequestService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.SupportServiceType.PATENT_PRIORITY_REQUEST;


@Service
@RequiredArgsConstructor
public class ApplicationPriorityRequestServiceImpl extends SupportServiceRequestServiceImpl<ApplicationPriorityRequest> implements ApplicationPriorityRequestService {

    private final SupportServiceProcess supportServiceProcess;
    private final ApplicationPriorityRequestRepository applicationPriorityRequestRepository;
    private final ApplicationInfoService applicationInfoService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final SupportServiceStatusChangeLogService supportServiceStatusChangeLogService;
    private final DocumentsService documentsService;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return applicationPriorityRequestRepository;
    }

    @Override
    public ApplicationPriorityRequest insert(ApplicationPriorityRequest entity) {
        return super.insert(PATENT_PRIORITY_REQUEST, entity);
    }

    void checkIfDocumentDeleted(Long priorityRequestId){
        Long documentId = applicationPriorityRequestRepository.findDocumentIdByPriorityRequestId(priorityRequestId);
        if(documentId != null){
            Long isDeleted = documentsService.getIsDeletedByDocumentId(documentId);
            if(isDeleted != 0){
                applicationPriorityRequestRepository.deletePriorityDocument(priorityRequestId);
            }
        }
    }

    @Override
    public ApplicationPriorityRequest update(ApplicationPriorityRequest request) {
        this.checkIfDocumentDeleted(request.getId());
        ApplicationPriorityRequest entity = findById(request.getId());
            if(isDraftStatus(entity) || isConditionalRejectionStatus(entity)){
                this.updateEntityFields(entity, request);
                ApplicationPriorityRequest updatedRequest = super.update(entity);
                RequestTasksDto requestTasksDto = this.completeUserTask(entity);
                this.updateRequestStatus(entity.getId(), requestTasksDto);

                return updatedRequest;
            }else{
                throw new BusinessException(Constants.ErrorKeys.REQUEST_STATUS_IS_NOT_VALID, HttpStatus.METHOD_NOT_ALLOWED, null);
            }
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        ApplicationPriorityRequestPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    private void ApplicationPriorityRequestPaymentCallBack(Long id) {
        ApplicationPriorityRequest entity = findById(id);
        if (entity.getProcessRequestId() != null) {
            return;
        }
        StartProcessDto startProcessDto = super.prepareSupportServiceProcessRequestPaymentCallback(entity,
                "patent_priority_request_process", "PATENT_PRIORITY_REQUEST");

        startSupportServiceProcess(entity, startProcessDto);
    }

    private void updateEntityFields(ApplicationPriorityRequest entity, ApplicationPriorityRequest request) {
        if(isConditionalRejectionStatus(entity)){
            entity.setRequestUpdated(true);
        }
        entity.setModifyType(request.getModifyType());
        entity.setReason(request.getReason());
        if(request.getDocument() != null && request.getDocument().getId() != null){
            Document newDoc = new Document();
            newDoc.setId(request.getDocument().getId());
            entity.setDocument(newDoc);
        }
    }

    private boolean isDraftStatus(ApplicationPriorityRequest entity) {
        return SupportServiceRequestStatusEnum.DRAFT.name()
                .equals(entity.getRequestStatus().getCode());
    }

    private boolean isConditionalRejectionStatus(ApplicationPriorityRequest entity) {
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

    public RequestTasksDto completeUserTask(ApplicationPriorityRequest request){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient
                .getTaskByRowIdAndType(RequestTypeEnum.PATENT_PRIORITY_REQUEST, request.getId()).getPayload();

        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);

        return requestTasksDto;
    }
    @Override
    public boolean applicationSupportServicesTypePeriorityEditOnlyExists(Long appId){
        return applicationPriorityRequestRepository.applicationSupportServicesTypePeriorityEditOnlyExists(appId);
    }
}
