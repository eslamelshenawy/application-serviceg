package gov.saip.applicationservice.modules.ic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.saip.applicationservice.common.dto.DocumentDto;
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
public class InventorDto {

    private Long customerId;
    private Long applicantTypeId;
    private String nameAr;
    private String nameEn;
    private String identifier;
    private String applicantIdentifier;
    private String email;
    private String mobile;
    private String gender;
    private String userGroupAr;
    private String userGroupEn;
    private UserGroup userGroupCode;
    private boolean inventor;
    private IdentifierTypeEnum identifierTypeEnum;
    private ApplicationRelevantEnum type;
    private AddressResponseDto address;

    private DocumentDto documentDto;

    private CountryDto nationalCountry;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String customerCode;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long documentId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long nationalCountryId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long countryId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String city;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String fullAddress;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String postalCode;


    public InventorDto(Long applicantTypeId, String nameAr, String nameEn, String identifier, boolean inventor, IdentifierTypeEnum identifierTypeEnum,
                       ApplicationRelevantEnum type, String customerCode, Long documentId, Long nationalCountryId, Long countryId,
                       String fullAddress, String city, String postalCode, String gender) {
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.identifier = identifier;
        this.inventor = inventor;
        this.identifierTypeEnum = identifierTypeEnum;
        this.type = type;
        this.customerCode = customerCode;
        this.applicantTypeId = applicantTypeId;
        this.documentId = documentId;
        this.nationalCountryId = nationalCountryId;
        this.countryId = countryId;
        this.fullAddress = fullAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.gender = gender;
    }
}
