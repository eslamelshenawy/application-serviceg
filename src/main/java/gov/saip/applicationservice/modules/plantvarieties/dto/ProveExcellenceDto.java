package gov.saip.applicationservice.modules.plantvarieties.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProveExcellenceDto extends PlantVarietyDto {
    private Long plantVarietyDetailsId;
    private String plantNameSimilarYourPlant;
    private Long lkpvPropertyId;
    private Long lkpvPropertyOptionsId;
    private Long lkpvPropertyOptionsSecondId;
    private Long lkVegetarianTypesId;
    private String attributePlantDescription;
    private String explainDifference;
    private String descriptionTraitSimilarCategory;
}










