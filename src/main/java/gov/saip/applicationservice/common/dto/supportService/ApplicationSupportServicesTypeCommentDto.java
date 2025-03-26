package gov.saip.applicationservice.common.dto.supportService;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Data;

@Data
public class ApplicationSupportServicesTypeCommentDto extends BaseDto<Long> {
    private Long applicationSupportServicesTypeId;
    private String comment;
}
