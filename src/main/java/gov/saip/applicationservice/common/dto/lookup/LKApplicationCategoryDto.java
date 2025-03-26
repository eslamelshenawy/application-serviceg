package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LKApplicationCategoryDto extends BaseDto<Long> implements Serializable {
    private String applicationCategoryDescAr;
    private String applicationCategoryDescEn;
    private String saipCode;
    private String abbreviation;
    private boolean applicationCategoryIsActive;


}
