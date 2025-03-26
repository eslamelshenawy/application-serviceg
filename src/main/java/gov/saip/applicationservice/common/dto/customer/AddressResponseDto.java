package gov.saip.applicationservice.common.dto.customer;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AddressResponseDto {
    
    private CountryDto countryObject;
    
    private String country;
    
    private String placeOfResidenceAr;
    
    private String placeOfResidenceEn;
    
    private String unitNumber;
    
    private String buildingNumber;
    
    private String city;
    
    private String streetName;
    
    private String additionalNumber;
    
    private String district;
    
    private String postalCode;

    public AddressResponseDto(CountryDto countryObject, String placeOfResidenceAr, String placeOfResidenceEn, String city, String postalCode) {
        this.countryObject = countryObject;
        this.placeOfResidenceAr = placeOfResidenceAr;
        this.placeOfResidenceEn = placeOfResidenceEn;
        this.city = city;
        this.postalCode = postalCode;
    }
}
