package gov.saip.applicationservice.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class TaskEqmDto extends BaseDto<Long> {
    private Long applicationId;
    private String taskId;
    private String taskKey;
    private double average;
    @JsonProperty("isEnough")
    private boolean isEnough;
    private String comments;
    private List<TaskEqmRatingItemDto> taskEqmRatingItems;
    private String taskEqmTypeCode;
    private Long serviceId;
}
