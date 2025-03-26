package gov.saip.applicationservice.common.dto.timeslots;

import gov.saip.applicationservice.base.dto.BaseGenericDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
public class TimeSlotDto extends BaseGenericDto<Long> {
    private String slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isReserved;
}
