package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.EvictionRequest;
import gov.saip.applicationservice.common.model.ProtectionExtendRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProtectionExtendRequestRepository extends SupportServiceRequestRepository<ProtectionExtendRequest> {

}
