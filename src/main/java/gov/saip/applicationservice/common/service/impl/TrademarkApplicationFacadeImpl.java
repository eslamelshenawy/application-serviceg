package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationInfoRequestLightDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.supportService.application_edit_trademark_image_request.ApplicationEditTrademarkImageRequest;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.TrademarkApplicationFacade;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditTrademarkImageRequestService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrademarkApplicationFacadeImpl implements TrademarkApplicationFacade {
    
    private final DocumentsService documentsService;
    private final TrademarkDetailService trademarkDetailService;
    private final ApplicationEditTrademarkImageRequestService applicationEditTrademarkImageRequestService;
    private final ApplicationInfoService applicationInfoService;
    
    
    @Transactional
    public Long updateApplicationTrademarkDetailsImg(ApplicationInfoRequestLightDto request) {
        TrademarkDetail trademarkDetails = getTrademarkDetails(request.getApplicationId());
        ApplicationEditTrademarkImageRequest editImageRequest = getEditImageRequest(request.getSupportServiceId());
        ApplicationInfo applicationInfo = getApplicationInfo(request.getApplicationId());
        
        updateDocumentType(editImageRequest);
        updateTrademarkDetails(trademarkDetails, editImageRequest);
        updateApplicationInfo(applicationInfo, editImageRequest);
        
        return editImageRequest.getId();
    }
    
    private void updateApplicationInfo(ApplicationInfo applicationInfo, ApplicationEditTrademarkImageRequest editImageRequest) {
        if (editImageRequest.getNewNameAr() != null) {
            applicationInfo.setTitleAr(editImageRequest.getNewNameAr());
        }
        if (editImageRequest.getNewNameEn() != null) {
            applicationInfo.setTitleEn(editImageRequest.getNewNameEn());
        }
        
        applicationInfoService.update(applicationInfo);
    }
    
    private ApplicationInfo getApplicationInfo(Long applicationId) {
        return applicationInfoService.findById(applicationId);
    }
    
    private TrademarkDetail getTrademarkDetails(Long applicationId) {
        return trademarkDetailService.findByApplicationId(applicationId);
    }
    
    private ApplicationEditTrademarkImageRequest getEditImageRequest(Long supportServiceId) {
        return applicationEditTrademarkImageRequestService.findById(supportServiceId);
    }
    
    private void updateDocumentType(ApplicationEditTrademarkImageRequest editImageRequest) {
        documentsService.updateDocumentType(editImageRequest.getNewDocument().getId(), "Trademark Image/voice");
    }
    
    private void updateTrademarkDetails(TrademarkDetail trademarkDetails, ApplicationEditTrademarkImageRequest editImageRequest) {
        if (editImageRequest.getNewNameAr() != null) {
            trademarkDetails.setNameAr(editImageRequest.getNewNameAr());
        }
        if (editImageRequest.getNewNameEn() != null) {
            trademarkDetails.setNameEn(editImageRequest.getNewNameEn());
        }
        trademarkDetails.setMarkDescription(editImageRequest.getNewDescription());
        
        trademarkDetailService.update(trademarkDetails);
    }
    
    
}
