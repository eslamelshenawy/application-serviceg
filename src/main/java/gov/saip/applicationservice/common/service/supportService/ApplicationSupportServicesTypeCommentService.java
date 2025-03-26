package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;

import java.util.List;

public interface ApplicationSupportServicesTypeCommentService extends BaseService<ApplicationSupportServicesTypeComment, Long> {

    List<ApplicationSupportServicesTypeComment> getAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId(Long applicationSupportServiceId);
    ApplicationSupportServicesTypeComment getLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId(Long applicationSupportServiceId);

}
