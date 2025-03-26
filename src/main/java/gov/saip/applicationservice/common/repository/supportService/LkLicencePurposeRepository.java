package gov.saip.applicationservice.common.repository.supportService;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.supportService.LkLicencePurpose;
import gov.saip.applicationservice.common.model.supportService.LkLicenceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LkLicencePurposeRepository extends BaseLkRepository<LkLicencePurpose, Long> {

}
