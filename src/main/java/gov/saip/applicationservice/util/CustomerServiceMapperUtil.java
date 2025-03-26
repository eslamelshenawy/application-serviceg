package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerServiceMapperUtil {
    private final CustomerServiceCaller customerServiceCaller;

    @Named("customerIdFromCode")
    public Long getCustomerIdByCustomerCode(String customerCode) {
        Long customerId = customerCode != null?  customerServiceCaller.getCustomerIdByCustomerCode(customerCode): null;
        if (customerId == null) {
            return null;
        }
        return customerId;
    }

    @Named("customerInfoFromCustomerId")
    public CustomerSampleInfoDto getCustomerInfoByCustomerId(Long customerId) {
        if (customerId == null) {
            return null;
        }
        return customerServiceCaller.getAnyCustomerDetails(customerId);
    }


}
