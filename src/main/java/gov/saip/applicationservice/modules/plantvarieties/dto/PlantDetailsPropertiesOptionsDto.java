package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PlantDetailsPropertiesOptionsDto extends BaseDto<Long> {

    private Long plantVarietyDetailsId;
    private Long application;
    private Long propertiesOptionsId;
    private String protectionOtherDiseases;
    private String otherDescription;
    private List<PlantDetailsPropertiesOptionsSelectionsDto> selectionsDtos;

}
