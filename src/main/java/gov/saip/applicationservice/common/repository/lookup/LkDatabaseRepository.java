package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkDatabase;
import org.springframework.stereotype.Repository;

@Repository
public interface LkDatabaseRepository extends BaseLkRepository<LkDatabase, Integer> {

}
