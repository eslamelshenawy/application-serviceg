package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.CustomerApplicationAccessLevel;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ApplicationCustomerData {
    private Long customerId;
    private ApplicationCustomerType customerType;
    private Long ApplicationId;
    private ApplicationInfo applicationInfo;
    private String customerCode;
    private Long agencyId;
    private TrademarkAgencyRequest trademarkAgencyRequest;
    private CustomerApplicationAccessLevel customerAccessLevel;
}
