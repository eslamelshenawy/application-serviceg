package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface LkApplicationPriorityStatusRepository extends BaseRepository<LkApplicationPriorityStatus, Long> {



    LkApplicationPriorityStatus getByCode(String code);

}
