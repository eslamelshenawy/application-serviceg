package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class FillingRequestInOtherCountryDto extends BaseDto<Long> {
    private Long application;
    private Long plantVarietyDetailsId;
    private Long countryId;
    private String registrationRequestOtherCountryNumber;
    private LocalDateTime fillingDateRequest;
    private String classification;
    private CountryDto countryDto;
}
