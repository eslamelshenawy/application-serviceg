package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.ApplicationSubClassification;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.SubClassification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface SubClassificationService extends BaseService<SubClassification, Long> {
    PaginationDto getAllSubClass(int page, int limit, String query, Boolean isShortcut
            , long applicationId, long categoryId);

    PaginationDto findSubClassificationByTrademarkId(int page, int limit, String tmoCustomerCode,Long trademarkId, Long subClassificationId, String code);

    List<SubClassification> findByIdIn(List<Long> selectedSubClass);

    List<SubClassification> findSubClassByIsShortcutAndCategoryId(Long categoryId);
    boolean checkSubClassSelected(long productId, long applicationId);
    
    public List<ApplicationSubClassification> getAppSubClassSelected(List<Long> subClassIds, long applicationId);
    
    PaginationDto getAllSubClassificationsByClassificationId(int page , int limit , String query , long classId);

    PaginationDto revokeProductsSubClassification(int page, int limit, Long classificationId, Long applicationId, String query , Long basicNumber);

    void reedLocarnoSheet(MultipartFile file , Map<Long, Classification> classificationMap);

}
