package gov.saip.applicationservice.modules.plantvarieties.service;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.LkVegetarianTypeDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LkVegetarianTypeService extends BaseService<LkVegetarianTypes, Long> {

    void softDeleteById(Long id);

    PaginationDto getAllPaginatedVegetarianTypes(String search,Boolean isActive,int page , int limit );

    List<LkVegetarianTypes> getAllVegetarianTypesThatHaveOnlyPropertiesAndOptions(PVExcellence excellence);
    String processExcelFile(MultipartFile file);

}
