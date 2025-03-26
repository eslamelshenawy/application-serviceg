package gov.saip.applicationservice.modules.plantvarieties.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LKPVPropertyOptionsDto extends BaseDto<Long> {

    private String code;
    private String nameAr;
    private String nameEn;
    private String example;
    private String note;
    private Boolean isActive;
    private Long lkPVPropertyId;
    private LKPVProperty lKPVProperty;
}
