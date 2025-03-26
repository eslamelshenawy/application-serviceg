package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.ChangeOwnershipCustomer;
import gov.saip.applicationservice.common.model.ChangeOwnershipRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangeOwnershipCustomerRepository extends BaseRepository<ChangeOwnershipCustomer, Long> {

}
