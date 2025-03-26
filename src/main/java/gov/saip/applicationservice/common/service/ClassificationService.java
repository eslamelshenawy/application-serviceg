package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.ListApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.LkClassificationUnit;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface ClassificationService extends BaseService<Classification, Long> {
    PaginationDto getAllClassifications(int page, int limit, String search, Integer versionId, String saipCode, Long categoryId, Long unitId);

    List<ClassificationDto> findByCategoryId(Long categoryId);

    List<ClassificationDto> findByUnitIdAndCategory(String categorySaipCode, Long unitId);

    List<ClassificationDto> findBySaipCode(String categorySaipCode);

    List<ClassificationDto> findByUnitIdIn(List<Long> unitId);
    List<Classification> findByIdIn(List<Long> inventoryIdList);
    List<ClassificationLightDto> getClassificationsByUnit(Long unitId);
    List<ClassificationLightDto> getAllClassifications(String saipCode, Integer versionIdLong);

    List<ListApplicationClassificationDto> listApplicationClassification(Long id);

    Long addClassification(ClassificationDto classificationDto);

    Long updateClassification(ClassificationDto classificationDto);

    List<Classification> getAllClassificationByVersionIdAndCategoryCode(String saipCode ,Integer versionId);

    boolean hasClassificationsByVersionId(Long unitId);

    void reedLocarnoSheet(MultipartFile file , Map<Long, LkClassificationUnit> classificationUnitMap,Long categoryId , Integer versionId);

}