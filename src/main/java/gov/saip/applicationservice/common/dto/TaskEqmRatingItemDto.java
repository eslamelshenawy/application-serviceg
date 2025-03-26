package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskEqmRatingItemDto extends BaseDto<Long> {

    private LkTaskEqmItemDto taskEqmItem;
    private int value;

}
