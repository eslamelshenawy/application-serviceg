package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LKPublicationType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LKPublicationTypeRepository extends BaseLkRepository<LKPublicationType , Integer> {

    List<LKPublicationType> findByApplicationCategoryId(Long categoryId);
    
    Optional<LKPublicationType> findByCodeAndApplicationCategoryId(String code, Long categoryId);
    
}

