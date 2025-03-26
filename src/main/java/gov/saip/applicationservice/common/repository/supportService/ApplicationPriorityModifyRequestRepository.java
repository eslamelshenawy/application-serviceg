package gov.saip.applicationservice.common.repository.supportService;

import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequest;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationPriorityModifyRequestRepository extends SupportServiceRequestRepository<ApplicationPriorityModifyRequest> {
}
