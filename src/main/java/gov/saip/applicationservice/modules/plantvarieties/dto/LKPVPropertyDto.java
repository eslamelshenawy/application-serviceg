package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import gov.saip.applicationservice.modules.plantvarieties.model.LkVegetarianTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class LKPVPropertyDto extends BaseDto<Long> {
    private String code;
    private String nameAr;
    private String nameEn;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private PVExcellence excellence;
    @Enumerated(EnumType.STRING)
    private PVPropertyType type;
    private Long lkVegetarianTypeId;
    private LkVegetarianTypes lkVegetarianType;
    private List<LKPVPropertyOptionsLightDto> lkpvPropertyOptionsLightDtoList;

}
