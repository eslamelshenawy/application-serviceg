package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.PlantDetailsTestingDifferenceExcellenceDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.ProveExcellenceLightDto;
import gov.saip.applicationservice.modules.plantvarieties.model.ProveExcellence;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProveExcellenceService extends BaseService<ProveExcellence,Long> {
    Long softDeleteProveExcellenceById(Long id);
    List<ProveExcellenceLightDto>  findApplicationProveExcellenceByApplicationId(Long appId);
    Long updateProveExcellenceWithApplication(ProveExcellenceDto proveExcellenceDto);
    PaginationDto<List<ProveExcellenceLightDto>> listProveExcellenceApplication(Integer page, Integer limit,Long appId, Long lkVegetarianTypesId,Sort.Direction sortDirection);
    PlantDetailsTestingDifferenceExcellenceDto findProveExcellenceByPlantDetailsId(Long appId,Long lkVegetarianTypesId);
    Long saveProveExcellence(ProveExcellenceDto dto);
}
