package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.LKSupportServiceType;

import java.util.List;

public interface LKSupportServiceTypeService extends BaseService<LKSupportServiceType, Long> {
    List<LKSupportServiceType> getAllByRequestType(String requestType, Long id);
    LKSupportServiceType findByCode(SupportedServiceCode code);
}
