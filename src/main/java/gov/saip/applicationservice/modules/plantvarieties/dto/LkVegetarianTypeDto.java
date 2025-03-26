package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LkVegetarianTypeDto extends BaseDto<Long> {
    private String code;
    private String nameAr;
    private String nameEn;
    private String scientificName;
    private Long protectionPeriod;
    private Long marketingPeriodInKsa;
    private Long marketingPeriodOutKsa;
    private Boolean isActive;
    private Long codeNumber;
}
