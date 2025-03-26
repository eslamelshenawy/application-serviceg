package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.ApplicationTypeEnum;
import gov.saip.applicationservice.common.model.LkNexuoUser;
import org.springframework.stereotype.Repository;

@Repository
public interface LkNexuoUserRepository extends BaseRepository<LkNexuoUser, Long> {

    LkNexuoUser findByType(ApplicationTypeEnum type);
}
