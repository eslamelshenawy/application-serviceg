package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LkFeeCostDto extends BaseDto<Long> implements Serializable {
    private Long applicationCategoryId;
    private Long applicantCategoryId;
    private LkRequestTypeDto requestType;
    private String saipCode;
    private Double cost;
    private Boolean payLater;
}
