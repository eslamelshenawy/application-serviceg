package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.model.Classification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class LkClassificationUnitDto extends BaseDto<Long> {
    @NotEmpty
    private String nameEn;
    @NotEmpty
    private String nameAr;
    @NotNull
    private Long categoryId;
    private Integer versionId;
    private String applicationCategoryDescAr;
    private String applicationCategoryDescEn;
    private List<ClassificationLightDto> classifications;
    private String classificationIds;
    private String code;

}
