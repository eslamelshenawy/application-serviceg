package gov.saip.applicationservice.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.SubClassificationType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ClassificationDto extends BaseDto<Long> {

    @JsonIgnore
    private String applicationId;
    private Long classificationId;
    @NotEmpty
    private String code;
    @NotEmpty
    private String nameAr;
    private String nameEn;
    private String descriptionEn;
    @NotEmpty
    private String descriptionAr;
    private boolean enabled;
    private SubClassificationType subClassificationType;
    private int niceVersion;
    private String nameVersionEn;
    private String nameVersionAr;
    private Long unitId;
    private String nameUnitEn;
    private String nameUnitAr;
    @NotNull
    private Long categoryId;
    private String applicationCategoryDescAr;
    private String applicationCategoryDescEn;

}
