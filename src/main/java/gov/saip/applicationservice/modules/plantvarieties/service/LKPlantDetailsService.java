package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPlantDetails;

import java.util.List;

public interface LKPlantDetailsService extends BaseLkService<LKPlantDetails,Integer> {

    List<LKPlantDetails> findByMainCode(String mainCode);
}
