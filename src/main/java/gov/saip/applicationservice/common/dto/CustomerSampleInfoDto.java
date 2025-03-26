package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.enums.customers.UserGroup;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerSampleInfoDto {
    private Long id;
    private String nameAr;
    private String nameEn;
    private String identifier;
    private String type;
    private String typeEn;
    private String typeAr;
    private String code;
    private String mobile;
    private String email;
    private String mobileCountryCode;
    private String userGroupAr;
    private String userGroupEn;
    private UserGroup userGroupCode;
    private CountryDto nationality;
    private AddressResponseDto address;
    private String createdByUser;
    private String gender;
    private String customerStatus;
    public String getCustomerAddressAsString() {
        if (address == null ) {
            return "";
        }
        return address.getCity() + " - " + address.getDistrict() + " - " + address.getStreetName();
    }
}
