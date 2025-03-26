package gov.saip.applicationservice.modules.plantvarieties.dto;


import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantDetailsPropertiesOptionsSelectionsDto {

    private Long id;
    private Long pvPropertyId;
    private String pvPropertyNameAr;
    private String pvPropertyNameEn;
    private Long pvPropertyOptionId;
    private String pvPropertyOptionNameAr;
    private String pvPropertyOptionNameEn;
    private String note;
    private String example;
    private PVPropertyType type;

    public PlantDetailsPropertiesOptionsSelectionsDto(Long id,Long pvPropertyId, String pvPropertyNameAr, String pvPropertyNameEn, Long pvPropertyOptionId, String pvPropertyOptionNameAr, String pvPropertyOptionNameEn, String note, String example,PVPropertyType type) {
        this.id = id;
        this.pvPropertyId = pvPropertyId;
        this.pvPropertyNameAr = pvPropertyNameAr;
        this.pvPropertyNameEn = pvPropertyNameEn;
        this.pvPropertyOptionId = pvPropertyOptionId;
        this.pvPropertyOptionNameAr = pvPropertyOptionNameAr;
        this.pvPropertyOptionNameEn = pvPropertyOptionNameEn;
        this.note = note;
        this.example = example;
        this.type = type;
    }
}
