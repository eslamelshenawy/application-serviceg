package gov.saip.applicationservice.common.dto.timeslots;

import gov.saip.applicationservice.common.enums.timeslot.TimeSlotPeriodType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TimeSlotPeriodDto  {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotPeriodInMinutes;
    private TimeSlotPeriodType type;
}
