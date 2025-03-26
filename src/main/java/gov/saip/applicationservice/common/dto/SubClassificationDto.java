package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class SubClassificationDto extends BaseDto<Long> {


    private String code;
    private String nameEn;
    private String descriptionEn;
    private String descriptionAr;
    private boolean isShortcut;
    private boolean isVisible;
    private boolean enabled;
    private String nameAr;
    @NotNull
    private Long classificationId;
    private int niceVersion;
    private String serialNumberEn;
    private String serialNumberAr;
    private Long basicNumber;
    private boolean isSelected;
    private String classificationCode;
    private String classificationNameEn;
    private String classificationNameAr;
    private String classificationDescriptionEn;
    private String classificationDescriptionAr;
    private String classificationNotesEn;
    private String classificationNotesAr;
    private boolean classificationEnabled;
    private int classificationNiceVersion;

    public SubClassificationDto(String code, String nameAr, String nameEn, boolean isSelected) {
        this.code = code;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.isSelected = isSelected;
    }
}
