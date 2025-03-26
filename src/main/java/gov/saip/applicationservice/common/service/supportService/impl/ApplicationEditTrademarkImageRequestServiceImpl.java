package gov.saip.applicationservice.common.service.supportService.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.supportService.ApplicationEditTrademarkImageRequestDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.ValidationType;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationEditTrademarkImageRequestMapper;
import gov.saip.applicationservice.common.model.projections.ApplicationSupportServiceProjectionDetails;
import gov.saip.applicationservice.common.model.supportService.application_edit_trademark_image_request.ApplicationEditTrademarkImageRequest;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.supportService.ApplicationEditTrademarkImageRequestRepository;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditTrademarkImageRequestService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.SupportServiceType.EDIT_TRADEMARK_IMAGE;

@Service
@RequiredArgsConstructor
public class ApplicationEditTrademarkImageRequestServiceImpl extends SupportServiceRequestServiceImpl<ApplicationEditTrademarkImageRequest>
        implements ApplicationEditTrademarkImageRequestService {
    
    private final ApplicationEditTrademarkImageRequestRepository editTrademarkImageRequestRepository;
    private final TrademarkDetailService trademarkDetailService;
    private final DocumentsService documentsService;
    private final DocumentMapper documentMapper;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationEditTrademarkImageRequestMapper mapper;
    private final CustomerServiceFeignClient customerServiceFeignClient;

    
    
    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return editTrademarkImageRequestRepository;
    }
    
    
    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public ApplicationEditTrademarkImageRequestDto getDetailsBySupportServiceId(Long serviceId) {
        ApplicationSupportServiceProjectionDetails<ApplicationEditTrademarkImageRequest> detailsBySupportServiceId =
                getSupportServiceRequestRepository().findApplicationDetailsBySupportServiceId(serviceId);
        ApplicationEditTrademarkImageRequestDto serviceDto = mapper.mapProjectionToDto(detailsBySupportServiceId);
        CustomerSampleInfoDto applicantInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(detailsBySupportServiceId.getRequest().getCreatedByCustomerCode()).getPayload();
        serviceDto.setApplicantNameAr(applicantInfoDto.getNameAr());
        serviceDto.setApplicantNameEn(applicantInfoDto.getNameEn());
        return serviceDto;
    }
    
    
    @Override
    public ApplicationEditTrademarkImageRequest insert(ApplicationEditTrademarkImageRequest entity) {
        Long appId = entity.getApplicationInfo().getId();
        TrademarkDetail trademarkDetails = trademarkDetailService.findByApplicationId(appId);
        
        if (trademarkDetails == null) {
            return new ApplicationEditTrademarkImageRequest();
        }
        
        String typeName = "Trademark Image/voice";
        DocumentDto documentDto = documentsService.findLatestDocumentByApplicationIdAndDocumentType(appId, typeName);
        
        setOldDetails(entity, trademarkDetails, documentDto);
        
        ApplicationEditTrademarkImageRequest editTrademarkImageRequest = super.insert(EDIT_TRADEMARK_IMAGE, entity);
        updateRequestStatus(SupportServiceRequestStatusEnum.PENDING_IMG_FEES_MOD_REQ, entity);
        
        return editTrademarkImageRequest;
    }
    
    private void setOldDetails(ApplicationEditTrademarkImageRequest entity, TrademarkDetail trademarkDetails, DocumentDto documentDto) {
        entity.setOldDescription(trademarkDetails.getMarkDescription());
        entity.setOldDocument(documentMapper.mapRequestToEntity(documentDto));
        entity.setOldNameAr(trademarkDetails.getNameAr());
        entity.setNewNameAr(trademarkDetails.getNameAr());
        entity.setOldNameEn(trademarkDetails.getNameEn());
        entity.setNewNameEn(trademarkDetails.getNameEn());
    }
    
    
    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        ApplicationEditNameAddressRequestPaymentCallBack(id);
        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }
    
    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.UNDER_REVIEW_VISUAL;
    }
    
    private void ApplicationEditNameAddressRequestPaymentCallBack(Long id) {
        ApplicationEditTrademarkImageRequest entity = findById(id);
        StartProcessDto startProcessDto = super.prepareSupportServiceProcessRequestPaymentCallback(entity,
                "application_edit_tm_image_process", "EDIT_TRADEMARK_IMAGE");
        startSupportServiceProcess(entity, startProcessDto);
    }
    
    
    @Override
    public ApplicationEditTrademarkImageRequest update(ApplicationEditTrademarkImageRequest request) {
        ApplicationEditTrademarkImageRequest entity = findById(request.getId());
        
        updateEntityFields(entity, request);
        
        ApplicationEditTrademarkImageRequest updatedRequest = super.update(entity);
        
        if (!isDraftStatus(entity) && !isUnderReviewVisual(entity) && !isUnderReviewAuditor(entity)) {
            completeUserTask(entity);
            updateRequestStatus(SupportServiceRequestStatusEnum.UNDER_REVIEW_VISUAL, entity);
        }
        
        return updatedRequest;
    }
    
    
    private boolean isDraftStatus(ApplicationEditTrademarkImageRequest entity) {
        return SupportServiceRequestStatusEnum.PENDING_IMG_FEES_MOD_REQ.name()
                .equals(entity.getRequestStatus().getCode());
    }
    
    private boolean isUnderReviewVisual(ApplicationEditTrademarkImageRequest entity) {
        return SupportServiceRequestStatusEnum.UNDER_REVIEW_VISUAL.name()
                .equals(entity.getRequestStatus().getCode());
    }
    
    private boolean isUnderReviewAuditor(ApplicationEditTrademarkImageRequest entity) {
        return SupportServiceRequestStatusEnum.UNDER_REVIEW_AUDITOR.name()
                .equals(entity.getRequestStatus().getCode());
    }
    
    private void updateEntityFields(ApplicationEditTrademarkImageRequest entity, ApplicationEditTrademarkImageRequest request) {
        TrademarkDetail trademarkDetails = trademarkDetailService.findByApplicationId(request.getApplicationInfo().getId());
        
        entity.setOldNameAr(trademarkDetails.getNameAr());
        entity.setOldNameEn(trademarkDetails.getNameEn());
        entity.setOldDescription(trademarkDetails.getMarkDescription());
        
        entity.setNewNameAr(request.getNewNameAr() == null ? entity.getNewNameAr() : request.getNewNameAr());
        entity.setNewNameEn(request.getNewNameEn() == null ? entity.getNewNameEn() : request.getNewNameEn());
        entity.setNewDescription(request.getNewDescription() == null ? entity.getNewDescription() : request.getNewDescription());
        entity.setNewDocument(request.getNewDocument() == null ? entity.getNewDocument() : request.getNewDocument());
        
    }
    
    private void updateRequestStatus(SupportServiceRequestStatusEnum newStatus, ApplicationEditTrademarkImageRequest entity) {
        this.updateRequestStatusByCode(entity.getId(), newStatus);
    }
    
    
    public void completeUserTask(ApplicationEditTrademarkImageRequest request) {
        RequestTasksDto requestTasksDto = bpmCallerFeignClient
                .getTaskByRowIdAndType(RequestTypeEnum.EDIT_TRADEMARK_IMAGE, request.getId()).getPayload();
        
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }
    
}
