package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPlantDetails;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LKPlantDetailsRepository extends BaseLkRepository<LKPlantDetails, Integer> {



    List<LKPlantDetails> findByMainCode(@Param("mainCode")String mainCode);
}
