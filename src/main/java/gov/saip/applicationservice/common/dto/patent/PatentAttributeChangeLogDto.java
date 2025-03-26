package gov.saip.applicationservice.common.dto.patent;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatentAttributeChangeLogDto {
    private Long id;
    private int isDeleted;
    private String attributeName;
    private String attributeValue;
    private String taskId;
    private String taskDefinitionKey;
    private int version = 1;
}
