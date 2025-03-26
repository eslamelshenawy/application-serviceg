package gov.saip.applicationservice.common.dto.migration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestartProcessInstructionDto {
    private String type = "startBeforeActivity";
    private String activityId;
}
