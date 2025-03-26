package gov.saip.applicationservice.modules.plantvarieties.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantProveExcellenceVariablesDto extends PlantVarietyDto {
    private Long id;
    private Boolean additionalFeatureDifferentiateVariety;
    private String additionalFeatureDifferentiateVarietyNote;
    private Boolean plantConditionalTesting;
    private String plantConditionalTestingNote;
    public PlantProveExcellenceVariablesDto(Long id, Boolean additionalFeatureDifferentiateVariety, String additionalFeatureDifferentiateVarietyNote, Boolean plantConditionalTesting, String plantConditionalTestingNote) {
        this.id = id;
        this.additionalFeatureDifferentiateVariety = additionalFeatureDifferentiateVariety;
        this.additionalFeatureDifferentiateVarietyNote = additionalFeatureDifferentiateVarietyNote;
        this.plantConditionalTesting = plantConditionalTesting;
        this.plantConditionalTestingNote = plantConditionalTestingNote;
    }
}
