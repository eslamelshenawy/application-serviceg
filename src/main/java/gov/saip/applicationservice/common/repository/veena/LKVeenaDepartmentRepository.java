package gov.saip.applicationservice.common.repository.veena;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.veena.LKVeenaDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LKVeenaDepartmentRepository extends BaseLkRepository<LKVeenaDepartment, Long> {

    List<LKVeenaDepartment> getByVeenaClassificationId(Long id);

}
