package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import lombok.Data;

@Data
public class ApplicationRelevantDto {


    private long id;
    
    private String fullNameAr;


    private String fullNameEn;

    private IdentifierTypeEnum identifierType;


    private String identifier;


    private String gender;


    private Long nationalCountryId;


    private String address;


    private Long countryId;


    private String city;


    private String pobox;

    private CountryDto country;
    
    private String typeAr;
    
    private String typeEn;




}
