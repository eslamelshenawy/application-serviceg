package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import lombok.Data;

@Data
public class ApplicationRelevantRequestsDto {


    private String email;
    private String address;
    private String mobileCode;
    private String mobileNumber;
    private IdentifierTypeEnum identifierType;
    private Long appRelvantTypeId;
    private String fullNameEn;
    private String fullNameAr;
    private String identifier;
    private Long nationalCountryId;
    private String pobox;
    private Long countryId;
    //    private boolean inventor;
    private Long waiverDocumentId;
    private String city;

    private String gender;
    private Long appInfoId;
}
