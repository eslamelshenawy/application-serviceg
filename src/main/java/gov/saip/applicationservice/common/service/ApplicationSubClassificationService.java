package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationSubClassificationDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import gov.saip.applicationservice.common.model.ApplicationSubClassification;

import java.util.List;

public interface ApplicationSubClassificationService extends BaseService<ApplicationSubClassification, Long> {

    Long createApplicationSubClassification(ApplicationSubClassificationDto subClassificationDto, Long companyId);

    Long updateApplicationSubClassification(ApplicationSubClassificationDto subClassificationDto, Long companyId);
    void deleteBySubClassIdInAndTrademarkDetailId(List<Long> ids, Long trademarkDetailId, Long categoryId);

    List<ApplicationSubClassification> findByApplicationInfoId(Long applicationId);

    long countByApplicationInfoId(Long applicationId);

    void deleteByAppIdAndClassId(Long appId, Long classId);

    List<SubClassificationDto> listApplicationSubClassifications(Long id, int page, int limit);
    List<SubClassificationDto> listApplicationSubClassifications(Long id);

    void deleteByAppIdAndSubClassificationIds(Long appId, List<Long> subClassificationIds);

    void deleteAllByApplicationId(Long applicationId);
}
