package gov.saip.applicationservice.modules.plantvarieties.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPVProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LKPVPropertyOptionsLightDto{
    private Long id;
    private int isDeleted;
    private String code;
    private String nameAr;
    private String nameEn;
    private String example;
    private String note;
    private Boolean isActive;

    public LKPVPropertyOptionsLightDto(Long id, int isDeleted, String code, String nameAr, String nameEn, String example, String note, Boolean isActive) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.code = code;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.example = example;
        this.note = note;
        this.isActive = isActive;
    }
}
