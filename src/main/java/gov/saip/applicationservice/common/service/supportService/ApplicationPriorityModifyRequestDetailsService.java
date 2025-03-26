package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequestDetails;

public interface ApplicationPriorityModifyRequestDetailsService extends BaseService<ApplicationPriorityModifyRequestDetails, Long> {

    int hardDeleteByApplicationPriorityModifyRequestId(Long applicationPriorityModifyRequestId);
}
