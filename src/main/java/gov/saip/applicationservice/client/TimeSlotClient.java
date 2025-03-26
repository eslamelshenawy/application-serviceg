package gov.saip.applicationservice.client;

import gov.saip.applicationservice.base.client.BaseClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "time-slot", url = "${client.feign.user.manage}/internal-calling/time-slot")
public interface TimeSlotClient extends BaseClient<TimeSlotDto, Long> {
    @PostMapping("/reserve-time-slot/{id}")
     ApiResponse<Void> reserveTimeSlotById(@PathVariable(name = "id")  Long id);
    @PutMapping("/reserve-time-slot/reschedule")
     ApiResponse<Void> rescheduleReserveTimeSlot(@RequestParam(name = "id")  Long id  , @RequestParam(name = "newId")  Long newId);


    @GetMapping("/time-slots")
    public ApiResponse<List<TimeSlotDto>> getTimeSlotsPerDay(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) ;
}
