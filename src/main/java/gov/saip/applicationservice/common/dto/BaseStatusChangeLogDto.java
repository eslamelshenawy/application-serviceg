package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseStatusChangeLogDto extends BaseDto<Long> {
    private String taskDefinitionKey;
    private String taskInstanceId;
    private String previousStatusCode;
    private String newStatusCode;
    private GenericLookupDto previousStatus;
    private GenericLookupDto newStatus;
    private LocalDateTime createdDate;
}
