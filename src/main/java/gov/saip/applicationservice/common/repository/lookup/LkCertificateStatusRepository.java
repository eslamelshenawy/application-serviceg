package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkCertificateStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface LkCertificateStatusRepository extends BaseLkRepository<LkCertificateStatus, Integer> {

}
