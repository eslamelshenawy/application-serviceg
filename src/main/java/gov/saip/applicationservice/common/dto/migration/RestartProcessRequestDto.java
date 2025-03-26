package gov.saip.applicationservice.common.dto.migration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestartProcessRequestDto {
    private RestartProcessDto restartProcessDto;
    private Object activitiesHistoryBody;
    private String applicationStatusCode;
    private String applicationCategoryCode;
}
