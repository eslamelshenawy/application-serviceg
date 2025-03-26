package gov.saip.applicationservice.common.facade.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationInfoRequestLightDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.facade.ApplicationInfoFacade;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.supportService.application_edit_name_address.ApplicationEditNameAddressRequest;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditNameAddressRequestService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Setter
@RequiredArgsConstructor
public class ApplicationInfoFacadeImpl implements ApplicationInfoFacade {
    
    private final ApplicationInfoService applicationInfoService;
    
    private final ApplicationCustomerService applicationCustomerService;
    
    private final CustomerServiceFeignClient customerServiceFeignClient;
    
    private final ApplicationEditNameAddressRequestService applicationEditNameAddressRequestService;
    
    @Override
    @Transactional
    public void updateApplicationNameAddressInfo(ApplicationInfoRequestLightDto requestDto) {
        ApplicationCustomer applicationCustomer = getApplicationCustomer(requestDto);
        CustomerSampleInfoDto customerInfo = getCustomerInfo(applicationCustomer);
        ApplicationInfo applicationInfo = getApplicationInfo(requestDto);
        
        setOwnerNameAndAddress(applicationInfo, customerInfo);
    }
    
    private ApplicationCustomer getApplicationCustomer(ApplicationInfoRequestLightDto requestDto) {
        return applicationCustomerService.getAppCustomerByAppIdAndType(
                requestDto.getApplicationId(),
                ApplicationCustomerType.valueOf(requestDto.getApplicationCustomerType())
        );
    }
    
    private CustomerSampleInfoDto getCustomerInfo(ApplicationCustomer applicationCustomer) {
        return customerServiceFeignClient.getAnyCustomerById(applicationCustomer.getCustomerId()).getPayload();
    }
    
    private ApplicationInfo getApplicationInfo(ApplicationInfoRequestLightDto requestDto) {
        return applicationInfoService.findById(requestDto.getApplicationId());
    }
    
    public void setOwnerNameAndAddress(ApplicationInfo applicationInfo, CustomerSampleInfoDto customerInfo) {
        applicationInfo.setOwnerNameAr(customerInfo.getNameAr());
        applicationInfo.setOwnerNameEn(customerInfo.getNameEn());
        AddressResponseDto address = customerInfo.getAddress();
        if (address != null) {
            applicationInfo.setOwnerAddressAr(address.getPlaceOfResidenceAr());
            applicationInfo.setOwnerAddressEn(address.getPlaceOfResidenceEn());
        }
        applicationInfoService.update(applicationInfo);
    }
    
    @Transactional
    public void updateApplicationNewNameAddressInfo(ApplicationInfoRequestLightDto requestDto) {
        ApplicationEditNameAddressRequest applicationCustomer = applicationEditNameAddressRequestService.findById(requestDto.getSupportServiceId());
        ApplicationInfo applicationInfo = applicationInfoService.findById(requestDto.getApplicationId());
        updateOwnerNameAndAddress(applicationInfo, applicationCustomer);
        applicationInfoService.update(applicationInfo);
    }
    
    private void updateOwnerNameAndAddress(ApplicationInfo applicationInfo, ApplicationEditNameAddressRequest applicationCustomer) {
        applicationInfo.setOwnerNameAr(applicationCustomer.getNewOwnerNameAr() != null ? applicationCustomer.getNewOwnerNameAr() : applicationCustomer.getOldOwnerNameAr());
        applicationInfo.setOwnerNameEn(applicationCustomer.getNewOwnerNameEn() != null ? applicationCustomer.getNewOwnerNameEn() : applicationCustomer.getOldOwnerNameEn());
        
        applicationInfo.setOwnerAddressAr(applicationCustomer.getNewOwnerAddressAr() != null ? applicationCustomer.getNewOwnerAddressAr() : applicationCustomer.getOldOwnerAddressAr());
        applicationInfo.setOwnerAddressEn(applicationCustomer.getNewOwnerAddressEn() != null ? applicationCustomer.getNewOwnerAddressEn() : applicationCustomer.getOldOwnerAddressEn());
        applicationInfoService.update(applicationInfo);
    }
    
    
}

