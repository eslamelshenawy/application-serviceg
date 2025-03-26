package gov.saip.applicationservice.common.service.timeslots;

import gov.saip.applicationservice.base.service.BaseClientService;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotDto;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotPeriodDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService  extends BaseClientService<TimeSlotDto, Long> {


    void reserveTimeSlotById(Long id);
    void rescheduleReserveTimeSlot(Long id,Long newId);

    List<TimeSlotDto> getAllTimeSlotsByPerDay(LocalDate date);

}
