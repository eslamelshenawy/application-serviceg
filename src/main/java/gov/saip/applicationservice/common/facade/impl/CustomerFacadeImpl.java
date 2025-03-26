package gov.saip.applicationservice.common.facade.impl;


import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.CustomerLightDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.facade.CustomerFacade;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Setter
@RequiredArgsConstructor
public class CustomerFacadeImpl implements CustomerFacade {
    
    private final ApplicationCustomerService applicationCustomerService;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    
    
    @Override
    public CustomerLightDto getCustomerUserGroup(Long appId) {
        CustomerSampleInfoDto customerInfo = getCustomerInfo(appId, ApplicationCustomerType.MAIN_OWNER);
        CustomerLightDto customerLightDto = CustomerLightDto.builder()
                .userGroup(customerInfo.getUserGroupCode().name())
                .build();
        setIdentifierBasedOnUserGroup(customerLightDto, customerInfo);
        return customerLightDto;
    }
    
    private void setIdentifierBasedOnUserGroup(CustomerLightDto customerLightDto, CustomerSampleInfoDto customerInfo) {
        String userGroupCode = customerInfo.getUserGroupCode().name();
        List<String> usersWithEstablishmentId = Constants.USERS_GROUPS_HAVE_ESTABLISHMENT_ID;
        List<String> usersWithIqamaId = Constants.USERS_GROUPS_HAVE_IQAMA_ID;

        if (customerInfo.getIdentifier().startsWith("7")) {
            customerLightDto.setEstablishmentId(customerInfo.getIdentifier());
        } else if (customerInfo.getIdentifier().startsWith("1") || customerInfo.getIdentifier().startsWith("2")) {
            customerLightDto.setIqamaNumber(customerInfo.getIdentifier());
        }
    }
    
    
    public CustomerSampleInfoDto getCustomerInfo(Long appId, ApplicationCustomerType applicationCustomerType) {
        ApplicationCustomer applicationCustomer = applicationCustomerService.getAppCustomerByAppIdAndType(appId, applicationCustomerType);
        return customerServiceFeignClient.getAnyCustomerById(applicationCustomer.getCustomerId()).getPayload();
    }
    
}
