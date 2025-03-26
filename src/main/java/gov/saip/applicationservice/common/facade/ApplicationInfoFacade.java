package gov.saip.applicationservice.common.facade;

import gov.saip.applicationservice.common.dto.ApplicationInfoRequestLightDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;

public interface ApplicationInfoFacade {
    
    public void updateApplicationNameAddressInfo(ApplicationInfoRequestLightDto request);
    
    public void updateApplicationNewNameAddressInfo(ApplicationInfoRequestLightDto request);
    
    public void setOwnerNameAndAddress(ApplicationInfo applicationInfo, CustomerSampleInfoDto customerInfo);
    
    
}
