package gov.saip.applicationservice.modules.plantvarieties.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryPlantVarietyDto extends PlantVarietyDto {
    private Long id;
    private String descriptionAr;
    private String descriptionEn;
    private String otherDetails;

    public SummaryPlantVarietyDto(Long id,String descriptionAr,String descriptionEn,String otherDetails){
        this.id=id;
        this.descriptionAr=descriptionAr;
        this.descriptionEn=descriptionEn;
        this.otherDetails=otherDetails;
    }
}
