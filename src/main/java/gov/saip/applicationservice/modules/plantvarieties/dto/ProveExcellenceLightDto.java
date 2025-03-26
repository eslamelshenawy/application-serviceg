package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProveExcellenceLightDto extends BaseDto<Long> {
    private Long id;
    private Long  plantVarietyDetailsId;
    private String plantNameSimilarYourPlant;
    private Long lkPropertyId;
    private String lkPropertyNameAr;
    private String lkPropertyNameEn;
    private Long lkpvPropertyOptionsId;
    private String optionNameAr;
    private String optionNameEn;
    private Long lkpvPropertyOptionsSecondId;
    private String optionSecondNameAr;
    private String optionSecondNameEn;
    private String attributePlantDescription;
    private String explainDifference;
    private String descriptionTraitSimilarCategory;
    private Long lkVegetarianTypesId;

    public ProveExcellenceLightDto(Long id,Long plantVarietyDetailsId, String plantNameSimilarYourPlant,
                                   Long lkPropertyId, String lkPropertyNameAr, String lkPropertyNameEn, Long lkpvPropertyOptionsId,String optionNameAr, String optionNameEn,
                                   Long lkpvPropertyOptionsSecondId,String  optionSecondNameAr,String optionSecondNameEn,
                                   String attributePlantDescription,String explainDifference,String descriptionTraitSimilarCategory,
                                   Long lkVegetarianTypesId) {
        this.id=id;
        this.plantVarietyDetailsId = plantVarietyDetailsId;
        this.plantNameSimilarYourPlant = plantNameSimilarYourPlant;
        this.lkPropertyId=lkPropertyId;
        this.lkPropertyNameAr = lkPropertyNameAr;
        this.lkPropertyNameEn = lkPropertyNameEn;
        this.lkpvPropertyOptionsId=lkpvPropertyOptionsId;
        this.optionNameAr = optionNameAr;
        this.optionNameEn = optionNameEn;
        this.lkpvPropertyOptionsSecondId=lkpvPropertyOptionsSecondId;
        this.optionSecondNameAr = optionSecondNameAr;
        this.optionSecondNameEn = optionSecondNameEn;
        this.attributePlantDescription = attributePlantDescription;
        this.explainDifference = explainDifference;
        this.descriptionTraitSimilarCategory = descriptionTraitSimilarCategory;
        this.lkVegetarianTypesId = lkVegetarianTypesId;
    }
}


