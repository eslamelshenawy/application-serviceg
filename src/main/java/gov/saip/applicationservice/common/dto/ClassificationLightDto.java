package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseLightDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClassificationLightDto extends BaseLightDto<Long> {
    private String descriptionAr;
    private String descriptionEn;
    private String nameAr;
    private String code;
    private List<SubClassificationDto> subClassificationDtos;
}