package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVPropertyOptions;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LKPVPropertyService extends BaseService<LKPVProperty, Long> {
    void softDeleteById(Long id);
    PaginationDto getAllPaginatedPvProperties(int page , int limit , String search, Long lkVegetarianTypeId, PVPropertyType type, PVExcellence excellence,Boolean isActive,String language);
    PaginationDto getAllPaginatedPvPropertiesThatHaveOptionsOnly(int page , int limit ,Long lkVegetarianTypeId, PVPropertyType type);
    List<LKPVPropertyDto> getAllPvPropertiesWithoutPaging(Long lkVegetarianTypeId, PVPropertyType type, PVExcellence excellence);
    List<LKPVPropertyDto> getAllPvPropertiesThatHaveOptionsOnlyWithoutPaging(Long lkVegetarianTypeId,PVExcellence excellence, PVPropertyType type);
    String processExcelFile(MultipartFile file,Long lkVegetarianTypeId,PVExcellence excellence,PVPropertyType type);
}
