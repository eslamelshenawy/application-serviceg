package gov.saip.applicationservice.common.facade;

import gov.saip.applicationservice.common.dto.CustomerLightDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;

public interface CustomerFacade {
    CustomerLightDto getCustomerUserGroup(Long appId);
    
    public CustomerSampleInfoDto getCustomerInfo(Long appId, ApplicationCustomerType applicationCustomerType);
}
