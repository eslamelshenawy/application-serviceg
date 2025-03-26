package gov.saip.applicationservice.common.dto.customer;

import lombok.Data;

@Data
public class CountryDto {


    private Long ici_country_id;

    private Long id;

    private String iciCountryNameAr;

    private String iciCountryNameEn;

    private String iciNationality;
    private String iciNationalityEn;

    private String iciCountryCode;

    private String iciCountryTelcode;

    private String nationalityStr;

}
