package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SupportServicesTypeDecisionsResponse extends BaseDto<Long> {
    private Long supportServiceId;
    private LocalDateTime createdDate;
    private String decision;
    private String comment;
    private DocumentDto document;
}
