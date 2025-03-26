package gov.saip.applicationservice.modules.ic.dto;

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
public class ApplicationApplicantDto {
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
    private IdentifierTypeEnum identifierTypeEnum;
    private ApplicationRelevantEnum type;
    private AddressResponseDto address;
    private String customerCode;
    private CountryDto countryDto;

    private Long documentId;
    private DocumentDto documentDto;

    private Long countryId;

    private  boolean byHimself;

    private String customerTypeAr;
    private String customerTypeEn;
    private String customerTypeCode;
    private String customerStatus;

    public ApplicationApplicantDto(Long applicantTypeId, String nameAr, String nameEn, String identifier, boolean byHimself, IdentifierTypeEnum identifierTypeEnum,
                       ApplicationRelevantEnum type, String customerCode, Long documentId, Long countryId) {
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.identifier = identifier;
        this.byHimself = byHimself;
        this.identifierTypeEnum = identifierTypeEnum;
        this.type = type;
        this.customerCode = customerCode;
        this.applicantTypeId = applicantTypeId;
        this.documentId = documentId;
        this.countryId = countryId;
    }
}
