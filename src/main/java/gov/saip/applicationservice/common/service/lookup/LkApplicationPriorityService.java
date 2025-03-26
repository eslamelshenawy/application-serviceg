package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import org.springframework.stereotype.Service;

@Service
public interface LkApplicationPriorityService extends BaseService<LkApplicationPriorityStatus, Long> {

}
