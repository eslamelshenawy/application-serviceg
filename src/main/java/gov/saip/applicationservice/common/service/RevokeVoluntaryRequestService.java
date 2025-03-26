package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;

public interface RevokeVoluntaryRequestService extends SupportServiceRequestService<RevokeVoluntaryRequest> {



    RevokeVoluntaryRequest findByAppId(Long appId);

}
