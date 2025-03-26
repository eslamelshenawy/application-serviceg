package gov.saip.applicationservice.modules.plantvarieties.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlantDetailsTestingDifferenceExcellenceDto {

    private boolean additionalFeatureDifferentiateVariety;
    private String additionalFeatureDifferentiateVarietyNote;
    private boolean plantConditionalTesting;
    private String plantConditionalTestingNote;
    private List<ProveExcellenceLightDto> proveExcellenceLightDto;

}
