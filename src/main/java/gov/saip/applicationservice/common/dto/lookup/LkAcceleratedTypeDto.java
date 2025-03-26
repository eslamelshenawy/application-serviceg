package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.*;

import java.io.Serializable;
@Setter
@Getter
public class LkAcceleratedTypeDto extends BaseDto<Long> implements Serializable {

    private String nameAr;
    private String nameEn;
    private Boolean show;
    private String applicationCategoryDescAr;
    private String applicationCategoryDescEn;

}
