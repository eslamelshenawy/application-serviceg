package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkApplicationService;
import org.springframework.stereotype.Repository;


@Repository
public interface LkApplicationServiceRepository extends BaseRepository<LkApplicationService, Long> {

    LkApplicationService findByCode(String code);
}
