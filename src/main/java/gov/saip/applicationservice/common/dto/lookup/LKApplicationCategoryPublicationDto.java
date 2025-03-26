package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LKApplicationCategoryPublicationDto extends BaseDto<Long> implements Serializable {

    private String saipCode;
    private Integer publicationAutoApprovalDays;
    private Integer oppositionDays;


}
