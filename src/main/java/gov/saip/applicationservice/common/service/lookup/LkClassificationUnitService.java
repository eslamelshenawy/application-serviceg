package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ClassificationUnitLightDto;
import gov.saip.applicationservice.common.dto.LkClassificationUnitDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.LkClassificationUnit;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LkClassificationUnitService extends BaseService<LkClassificationUnit, Long> {

    List<LkClassificationUnitDto> getClassificationVersions();
    List<LkClassificationUnitDto> getCategoryUnits(List<String> categories);
    PaginationDto filter (int page, int limit, String search , Long categoryId , Integer versionId);
    PaginationDto getIndustrialCategoryWithLastLocarnoVersion (int page, int limit, String search , Long categoryId);
    List<LkClassificationUnitDto> getAll();
    Long insert (LkClassificationUnitDto dto);
    Long update (LkClassificationUnitDto dto);


    List<ClassificationUnitLightDto> getAllClassificationUnits();

    PaginationDto getAllClassificationUnitsWithClassificationIds(int page , int limit);

    void softDeleteById(Long id);

    void reedLocarnoSheet(MultipartFile file ,Long categoryId , Integer versionId);
    List<LkClassificationUnitDto> findByVersion(Integer id);


}
