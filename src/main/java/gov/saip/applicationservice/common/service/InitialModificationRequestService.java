package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.InitialModificationRequest;

public interface InitialModificationRequestService extends SupportServiceRequestService<InitialModificationRequest> {
    SupportedServiceCode getApplicationSupportedServiceType(Long appId);

    void finishInitialModificationRequestByApplicationId(Long appId);



}
