package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.model.PetitionRecoveryRequest;
import gov.saip.applicationservice.common.repository.PetitionRecoveryRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationCheckingReportService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.PetitionRecoveryRequestService;
import gov.saip.applicationservice.common.service.bpm.TaskListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class PetitionRecoveryRequestServiceImpl extends SupportServiceRequestServiceImpl<PetitionRecoveryRequest> implements PetitionRecoveryRequestService {

    private final PetitionRecoveryRequestRepository petitionRecoveryRequestRepository;
    private final BPMCallerServiceImpl bpmCallerService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationInfoService applicationService;
    private final ApplicationCheckingReportService reportService;
    private final DocumentsServiceImpl documentsService;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final TaskListService taskListService;




    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return petitionRecoveryRequestRepository;
    }

    @Override
    public PetitionRecoveryRequest insert(PetitionRecoveryRequest entity) {
        return super.insert(SupportServiceType.PETITION_RECOVERY, entity);
    }

    @Override
    public PetitionRecoveryRequest update(PetitionRecoveryRequest entity){
        PetitionRecoveryRequest petitionRecoveryRequest = findById(entity.getId());
        petitionRecoveryRequest.setRecoveryDocument(entity.getRecoveryDocument() != null ? entity.getRecoveryDocument() : petitionRecoveryRequest.getRecoveryDocument());
        petitionRecoveryRequest.setEvictionDocument(entity.getEvictionDocument() != null ? entity.getEvictionDocument() : petitionRecoveryRequest.getEvictionDocument());
        petitionRecoveryRequest.setLkSupportServiceType(entity.getLkSupportServiceType() != null ? entity.getLkSupportServiceType() : petitionRecoveryRequest.getLkSupportServiceType());
        petitionRecoveryRequest.setJustification(entity.getJustification());
        petitionRecoveryRequest.setJustificationDocuments(entity.getJustificationDocuments());
        PetitionRecoveryRequest persistedEntity = super.update(petitionRecoveryRequest);
        checkTaskTermination(petitionRecoveryRequest, persistedEntity);
        return super.update(petitionRecoveryRequest);
    }

    private void checkTaskTermination(PetitionRecoveryRequest petitionRecoveryRequest, PetitionRecoveryRequest persistedEntity) {
        if(persistedEntity.getRequestStatus().getCode().equals(SupportServiceRequestStatusEnum.CONDITIONAL_REJECTION.toString())){
            this.updateRequestStatusByCode(petitionRecoveryRequest.getId(), SupportServiceRequestStatusEnum.REOPENED);
            completeUserTask(petitionRecoveryRequest);
        }
    }


    public void completeUserTask(PetitionRecoveryRequest petitionRecoveryRequest){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.PETITION_RECOVERY, petitionRecoveryRequest.getId()).getPayload();
        Map<String, Object> processVars = new LinkedHashMap<>();
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        PetitionRecoveryRequest petitionRecoveryRequest = findById(id);
        Long applicationId = petitionRecoveryRequest.getApplicationInfo().getId();
        RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndType(RequestTypeEnum.PATENT, applicationId).getPayload();
        if(Objects.nonNull(requestTasksDto)){
            petitionRecoveryStartProcess(id,requestTasksDto);
            completeMainApplicationTask(requestTasksDto);
            super.paymentCallBackHandler(id, applicationNumberGenerationDto);
        }


    }
    private void completeMainApplicationTask(RequestTasksDto requestTasksDto){

        Map<String, Object> variables = new HashMap<>();
        Map<String,Object> petitionRecoveryDecision = new HashMap<>();
        petitionRecoveryDecision.put("value","YES");
        variables.put("petitionRecoveryDecision",petitionRecoveryDecision);
        CompleteTaskRequestDto completeTaskRequestDto= new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(variables);
        if(requestTasksDto != null && requestTasksDto.getTaskId() != null){
            taskListService.completeTaskToUser(requestTasksDto.getTaskId(), completeTaskRequestDto);
        }
    }


    private void petitionRecoveryStartProcess(Long id,RequestTasksDto requestTasksDto){
        PetitionRecoveryRequest petitionRecoveryRequest = findById(id);
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(petitionRecoveryRequest.getCreatedByCustomerCode()).getPayload();
        Map<String, Object> vars = new HashMap<>();
        vars.put("drop_reason_text", PetitopnRecoveryTaskEnum.valueOf(requestTasksDto.getTaskDefinitionKey()).getReason());
        vars.put("drop_document_type", PetitopnRecoveryTaskEnum.valueOf(requestTasksDto.getTaskDefinitionKey()).getReportsType());
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(petitionRecoveryRequest.getId().toString())
                .fullNameAr(customerSampleInfoDto.getNameAr())
                .fullNameEn(customerSampleInfoDto.getNameEn())
                .mobile(customerSampleInfoDto.getMobile())
                .email(customerSampleInfoDto.getEmail())
                .identifier(customerSampleInfoDto.getIdentifier())
                .applicationCategory(ApplicationCategoryEnum.PATENT.name())
                .applicantUserName(petitionRecoveryRequest.getCreatedByUser())
                .processName("petition_recovery_request")
                .requestTypeCode("PETITION_RECOVERY")
                .supportServiceCode(petitionRecoveryRequest.getLkSupportServices().getCode().name())
                .requestNumber(petitionRecoveryRequest.getRequestNumber())
                .applicationIdColumn(petitionRecoveryRequest.getApplicationInfo().getId().toString())
                .variables(vars)
                .build();
        startSupportServiceProcess(petitionRecoveryRequest, startProcessDto);
    }

    @Override
    public ApplicationInfoDto getMainApplicationInfo(Long applicationId) {
        ApplicationInfoDto appInfo = applicationService.getApplication(applicationId);
        appInfo.setClassification((appInfo.getClassifications() != null && !appInfo.getClassifications().isEmpty()) ? appInfo.getClassifications().get(0) : null);
        ApplicationCheckingReportDto legalDocumentsDto = reportService.getLastReportByReportType(applicationId, ReportsType.DroppedRequestReport);
        if(legalDocumentsDto==null) return appInfo;
        DocumentDto documentDto = documentsService.findDocumentById(legalDocumentsDto.getDocumentId());
        appInfo.setLegalDocuments(documentDto);
        return appInfo;
    }
}