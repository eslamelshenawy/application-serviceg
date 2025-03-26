package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import gov.saip.applicationservice.common.enums.customers.UserGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApplicantsDto {

    //    appId
    private Long customerId;
    private Long applicantTypeId;
    private String nameAr;
    private String nameEn;
    private String identifier;
    private String applicantIdentifier;
    private String email;
    private String mobile;

    private String userGroupAr;

    public ApplicantsDto(Long applicantTypeId,String nameAr, String nameEn,String ownerNameAr ,String ownerNameEn, String identifier, boolean inventor, IdentifierTypeEnum identifierTypeEnum, ApplicationRelevantEnum type, String customerCode, String ownerAddressAr, String ownerAddressEn) {
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.identifier = identifier;
        this.inventor = inventor;
        this.identifierTypeEnum = identifierTypeEnum;
        this.type = type;
        this.customerCode = customerCode;
        this.ownerAddressAr = ownerAddressAr;
        this.ownerAddressEn = ownerAddressEn;
        this.applicantTypeId = applicantTypeId;
        this.ownerNameAr = ownerNameAr;
        this.ownerNameEn = ownerNameEn;
    }

    private String userGroupEn;
    private UserGroup userGroupCode;

    private boolean inventor;

    private IdentifierTypeEnum identifierTypeEnum;
    private ApplicationRelevantEnum type;
    private AddressResponseDto address;
    private String username;
    private String customerCode;
    private String ownerAddressAr;
    private String ownerAddressEn;
    private CountryDto countryDto;

    private String ownerNameAr;
    private String ownerNameEn;


}
