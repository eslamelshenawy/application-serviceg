package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.modules.plantvarieties.dto.FillingRequestInOtherCountryDto;
import gov.saip.applicationservice.modules.plantvarieties.model.FillingRequestInOtherCountry;

import java.util.List;

public interface FillingRequestInOtherCountryPlantVarietyService extends BaseService<FillingRequestInOtherCountry, Long> {

    Long softFillingRequestInOtherCountryById(Long id);
     List<FillingRequestInOtherCountryDto> findAllByPlantDetailsId(Long plantId);
}
