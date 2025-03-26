package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.mapper.OppositionRevokeLicenceRequestMapper;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.OppositionRevokeLicenceRequest;
import gov.saip.applicationservice.common.model.RevokeLicenceRequest;
import gov.saip.applicationservice.common.repository.OppositionRevokeLicenceRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.OppositionRevokeLicenceRequestService;
import gov.saip.applicationservice.common.service.RevokeLicenceRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.SupportServiceType.OPPOSITION_REVOKE_LICENCE_REQUEST;

@Service
@Transactional
@Slf4j
public class OppositionRevokeLicenceRequestServiceImpl extends SupportServiceRequestServiceImpl<OppositionRevokeLicenceRequest> implements OppositionRevokeLicenceRequestService {

    private OppositionRevokeLicenceRequestRepository oppositionRevokeLicenceRequestRepository;
    private OppositionRevokeLicenceRequestMapper oppositionRevokeLicenceRequestMapper;
    private  CustomerServiceCaller customerServiceCaller;
    private ApplicationInfoService applicationInfoService;
    private BPMCallerFeignClient bpmCallerFeignClient;
    @Lazy
    @Autowired
    private RevokeLicenceRequestService revokeLicenceRequestService;


    public OppositionRevokeLicenceRequestServiceImpl(OppositionRevokeLicenceRequestRepository oppositionRevokeLicenceRequestRepository, OppositionRevokeLicenceRequestMapper oppositionRevokeLicenceRequestMapper,  CustomerServiceCaller customerServiceCaller, ApplicationInfoService applicationInfoService, BPMCallerFeignClient bpmCallerFeignClient){
        this.oppositionRevokeLicenceRequestMapper= oppositionRevokeLicenceRequestMapper;
        this.customerServiceCaller= customerServiceCaller;
        this.applicationInfoService=applicationInfoService;
        this.bpmCallerFeignClient =bpmCallerFeignClient;
        this.oppositionRevokeLicenceRequestRepository =oppositionRevokeLicenceRequestRepository;
    }

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return oppositionRevokeLicenceRequestRepository;
    }


    @Override
    public OppositionRevokeLicenceRequest insert(OppositionRevokeLicenceRequest entity) {
        RevokeLicenceRequest revokeLicenceRequest = revokeLicenceRequestService.findById(entity.getRevokeLicenceRequest().getId());
        entity.setApplicationInfo(revokeLicenceRequest.getApplicationInfo());
        entity.setPaymentStatus(SupportServicePaymentStatus.FREE);
        entity.setProcessRequestId(revokeLicenceRequest.getProcessRequestId());
        entity = super.insert(OPPOSITION_REVOKE_LICENCE_REQUEST,entity, revokeLicenceRequest.getId());
        completeUserTask(entity.getRevokeLicenceRequest().getId(), buildCompleteTaskRequestDto("YES"));
        updateRevokeLicenseRequestStatus(entity.getRevokeLicenceRequest().getId(), SupportServiceRequestStatusEnum.UNDER_OPPOSITION);
        return entity;
    }

    private void updateRevokeLicenseRequestStatus(Long revokeLicenseRequestId, SupportServiceRequestStatusEnum newStatus) {
        revokeLicenceRequestService.updateRequestStatusByCode(revokeLicenseRequestId, newStatus);
    }

    @Override
    public OppositionRevokeLicenceRequest update(OppositionRevokeLicenceRequest entity) {
        OppositionRevokeLicenceRequest oppositionRevokeLicenceRequest = oppositionRevokeLicenceRequestRepository.getOppositionRevokeLicenceRequestByIdOrRevokeLicenceId(entity.getId());
        getCurrentStatusAndValidateItsInValidState(oppositionRevokeLicenceRequest, Arrays.asList(SupportServiceRequestStatusEnum.REQUEST_CORRECTION));
        oppositionRevokeLicenceRequest.setObjectionReason(entity.getObjectionReason());
        oppositionRevokeLicenceRequest.setDocuments(entity.getDocuments() != null ? entity.getDocuments() : oppositionRevokeLicenceRequest.getDocuments());
        oppositionRevokeLicenceRequest = super.update(oppositionRevokeLicenceRequest);
        completeUserTask(oppositionRevokeLicenceRequest.getRevokeLicenceRequest().getId(), buildCompleteTaskRequestDto("COMPLETED"));
        this.updateRequestStatusByCode(oppositionRevokeLicenceRequest.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        return oppositionRevokeLicenceRequest;
    }

    @Override
    public Long getUnderProcedureOppositionIdByRevokeLicenceRequest(Long revokeLicenceRequestId) {
        return oppositionRevokeLicenceRequestRepository.getUnderProcedureOppositionIdByRevokeLicenceRequest(revokeLicenceRequestId);
    }

    @Override
    public OppositionRevokeLicenceRequestApplicationSummaryDto getApplicationSummaryByOppositionRequestLicenseId(Long id) {
        OppositionRevokeLicenceRequestDto oppositionRevokeLicenceRequestDto = findByServiceId(id);
        ApplicationInfoSummaryDto applicationInfoSummaryDto = (ApplicationInfoSummaryDto) applicationInfoService.getApplicationSummary(oppositionRevokeLicenceRequestDto.getApplicationId(), null);
        OppositionRevokeLicenceRequestApplicationSummaryDto oppositionRevokeLicenceRequestApplicationSummaryDto= new OppositionRevokeLicenceRequestApplicationSummaryDto(applicationInfoSummaryDto, oppositionRevokeLicenceRequestDto);
        return oppositionRevokeLicenceRequestApplicationSummaryDto;
    }

    @Override
    public OppositionRevokeLicenceRequestDto findByServiceId(Long id) {
        OppositionRevokeLicenceRequest oppositionRevokeLicenceRequest = oppositionRevokeLicenceRequestRepository.getOppositionRevokeLicenceRequestByIdOrRevokeLicenceId(id);
        OppositionRevokeLicenceRequestDto oppositionRevokeLicenceRequestDto = oppositionRevokeLicenceRequestMapper.map(oppositionRevokeLicenceRequest);
        updateRevokeLicenceRequestDtoWithApplicantName(oppositionRevokeLicenceRequestDto, oppositionRevokeLicenceRequest.getCreatedByCustomerCode());
        updateRevokeLicenceRequestDtoWithCustomerInfo(oppositionRevokeLicenceRequestDto, oppositionRevokeLicenceRequest.getRevokeLicenceRequest().getLicenceRequest().getCustomerId());
        return oppositionRevokeLicenceRequestDto;
    }

    private void updateRevokeLicenceRequestDtoWithCustomerInfo(OppositionRevokeLicenceRequestDto oppositionRevokeLicenceRequestDto, Long customerId){
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceCaller.getAnyCustomerDetails(customerId);
        oppositionRevokeLicenceRequestDto.getRevokeLicenceRequest().getLicenceRequest().setCustomer(customerSampleInfoDto);
    }

    private void updateRevokeLicenceRequestDtoWithApplicantName(OppositionRevokeLicenceRequestDto oppositionRevokeLicenceRequestDto, String applicantCode) {
        CustomerSampleInfoDto customerSampleInfoDto =  applicantCode == null ? null : customerServiceCaller.getCustomerInfoByCustomerCode(applicantCode);

        if(customerSampleInfoDto != null) {
            oppositionRevokeLicenceRequestDto.setApplicantNameAr(customerSampleInfoDto.getNameAr());
            oppositionRevokeLicenceRequestDto.setApplicantNameEn(customerSampleInfoDto.getNameEn());
            oppositionRevokeLicenceRequestDto.setApplicantCustomerCode(customerSampleInfoDto.getCode());
        }
    }

    @Override
    @Transactional
    public void withdrawOppositionRevokeLicenseRequest(Long id){
        log.info("withdraw id {}", id);
        Long revokeLicenseRequestId = oppositionRevokeLicenceRequestRepository.getRevokeLicenceRequestIdByOppositionId(id);
        log.info("withdraw revoke id {}", id);
        log.info("withdraw opposition");
        completeUserTask(revokeLicenseRequestId, buildCompleteTaskRequestDto("WITHDRAWAL"));
        log.info("completed withdraw opposition");
        log.info("update opposition request status {}", id);
        this.updateRequestStatusByCode(id, SupportServiceRequestStatusEnum.WITHDRAWAL);
        log.info("update revoke request status {}", id);
        updateRevokeLicenseRequestStatus(revokeLicenseRequestId, SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        log.info("done");
    }

    private void completeUserTask( Long oppositionRevokeLicenseId, CompleteTaskRequestDto completeTaskRequestDto){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndTypeIfExissts(RequestTypeEnum.REVOKE_LICENSE_REQUEST, oppositionRevokeLicenseId).getPayload();
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    private CompleteTaskRequestDto buildCompleteTaskRequestDto(String value) {
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", value);
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        return completeTaskRequestDto;
    }

    @Override
    public boolean revokeLicenceRequestHasUnderProcedureOpposition(Long revokeLicenseId){
        return oppositionRevokeLicenceRequestRepository.revokeLicenceRequestHasUnderProcedureOpposition(revokeLicenseId);
    }

    @Override
    public OppositionRevokeLicenceRequestDto getUnderProcedureOppositionRevokeLicenceRequestByRevokeLicenseId(Long revokeLicenseId){
        OppositionRevokeLicenceRequest oppositionRevokeLicenceRequest = getOppositionRevokeLicenceRequestByRevokeLicenseId(revokeLicenseId);
        OppositionRevokeLicenceRequestDto oppositionRevokeLicenceRequestDto = oppositionRevokeLicenceRequestMapper.map(oppositionRevokeLicenceRequest);
        if(oppositionRevokeLicenceRequestDto != null)
            updateRevokeLicenceRequestDtoWithApplicantName(oppositionRevokeLicenceRequestDto, oppositionRevokeLicenceRequest.getCreatedByCustomerCode());
        return oppositionRevokeLicenceRequestDto;
    }

    @Override
    public String getUnderProcedureOppositionRevokeLicenceRequestNumberByRevokeLicenseId(Long revokeLicenseId) {
        return oppositionRevokeLicenceRequestRepository.getUnderProcedureOppositionRevokeLicenceRequestNumber(revokeLicenseId);
    }

    private OppositionRevokeLicenceRequest getOppositionRevokeLicenceRequestByRevokeLicenseId(Long revokeLicenseId) {
        OppositionRevokeLicenceRequest oppositionRevokeLicenceRequest = oppositionRevokeLicenceRequestRepository.getUnderProcedureOppositionRevokeLicenceRequest(revokeLicenseId);
        return oppositionRevokeLicenceRequest;
    }

    @Override
    public boolean checkRevokeLicenseRequestHasUnderProcedureOppositionRevokeLicenseRequest(Long revokeLicenseRequestId){
        return oppositionRevokeLicenceRequestRepository.checkRevokeLicenseRequestHasUnderProcedureOppositionRevokeLicenseRequest(revokeLicenseRequestId, Constants.VALIDATE_SUPPORT_SERVICE_REQUEST_STATUSES);
    }

    @Override
    public void updateOppositionRevokeLicenseRequestWithCourtDocuments(OppositionRevokeLicenceCourtDocumentsDto oppositionRevokeLicenceCourtDocumentsDto) {
        OppositionRevokeLicenceRequest oppositionRevokeLicenceRequest = oppositionRevokeLicenceRequestRepository.getOppositionRevokeLicenceRequestByIdOrRevokeLicenceId(oppositionRevokeLicenceCourtDocumentsDto.getId());
        getCurrentStatusAndValidateItsInValidState(oppositionRevokeLicenceRequest, Arrays.asList(SupportServiceRequestStatusEnum.COURT_DOCUMENTS_CORRECTION, SupportServiceRequestStatusEnum.PENDING));
        oppositionRevokeLicenceRequest.setCourtDocumentNotes(oppositionRevokeLicenceCourtDocumentsDto.getCourtDocumentNotes());
        oppositionRevokeLicenceRequest.setCourtDocuments(
                !Collections.isEmpty(oppositionRevokeLicenceCourtDocumentsDto.getDocumentIds())? getDocumentsFromDocumentIds(oppositionRevokeLicenceCourtDocumentsDto.getDocumentIds()) : oppositionRevokeLicenceRequest.getCourtDocuments());
        super.update(oppositionRevokeLicenceRequest);
        completeUserTask(oppositionRevokeLicenceRequest.getRevokeLicenceRequest().getId(), buildCompleteTaskRequestDto("COMPLETED"));
        this.updateRequestStatusByCode(oppositionRevokeLicenceRequest.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
    }

    private List<Document> getDocumentsFromDocumentIds(List<Long> documentIds){
        List<Document> documents = new ArrayList<>();
        if (!Collections.isEmpty(documentIds)){
            documents = documentIds.stream()
                    .map(id -> {
                        Document doc = new Document();
                        doc.setId(id);
                        return doc;
                    })
                    .collect(Collectors.toList());
        }
        return documents;
    }

    private void getCurrentStatusAndValidateItsInValidState(OppositionRevokeLicenceRequest oppositionRevokeLicenceRequest, List<SupportServiceRequestStatusEnum> expectedStatuses) {
        String currentStatus = oppositionRevokeLicenceRequest.getRequestStatus().getCode();
        validateOppositionRevokeLicenceRequestInValidStatus(SupportServiceRequestStatusEnum.valueOf(currentStatus), expectedStatuses);
    }

    private void validateOppositionRevokeLicenceRequestInValidStatus(SupportServiceRequestStatusEnum currentStatus, List<SupportServiceRequestStatusEnum> expectedStatuses) {
        if(!expectedStatuses.contains(currentStatus))
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_STATUS_IS_NOT_VALID, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
