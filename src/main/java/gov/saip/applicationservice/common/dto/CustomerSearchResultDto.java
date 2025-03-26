package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.enums.customers.UserGroup;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerSearchResultDto {
    private String customerStatus;
    private String customerCode;
    private String nameEn;
    private String nameAr;
    private String customerTypeCode;
    private String customerTypeEn;
    private int customerId;
    private String customerTypeAr;
    private String gender;
}
