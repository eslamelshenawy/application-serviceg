package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.LKPVPropertyOptionsLightDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVPropertyOptions;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LKPVPropertyOptionsService extends BaseService<LKPVPropertyOptions,Long> {
    void softDeleteById(Long id);
    PaginationDto getAllPaginatedPVPropertyOptions(int page, int limit, Long lkPVPropertyId, PVPropertyType type, PVExcellence excellence,Long lkVegetarianTypeId,Boolean isActive,String language, String search) ;
    List<LKPVPropertyOptions> getAllPVPropertyOptions(Long lkPVPropertyId, PVPropertyType type, PVExcellence excellence, String search) ;
    List<LKPVPropertyOptions> findByLKPVPropertyId(Long id);

    List<LKPVPropertyOptionsLightDto> getAllPvPropertiesOptionsLightDto(Long lkPVPropertyId);
    String processExcelFile(MultipartFile file, Long lkPVPropertyId);

}


