package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationStatusChangeLogDto extends BaseStatusChangeLogDto {
    private String descriptionCode;
    private Long applicationId;
}
