package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ListSubClassificationDto extends BaseDto<Long> {
    private String code;
    private String nameAr;
    private String nameEn;
    private String serialNumberEn;
    private String serialNumberAr;
    private Long basicNumber;
    private boolean isShorten;
}