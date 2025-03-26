package gov.saip.applicationservice.common.controllers.timeslots;

import gov.saip.applicationservice.base.controller.BaseClientController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotDto;
import gov.saip.applicationservice.common.service.timeslots.TimeSlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/kc/time-slot")
@RequiredArgsConstructor
@Slf4j
public class TimeSlotController extends BaseClientController<TimeSlotDto,Long> {

    private final TimeSlotService timeSlotService;



    @PostMapping("/reserve-time-slot/{id}")
    public ApiResponse<Void> reserveTimeSlotById(@PathVariable(name = "id")  Long id) {
        timeSlotService.reserveTimeSlotById(id);
        return ApiResponse.noContent();
    }

    @PutMapping("/reserve-time-slot/reschedule")
    public ApiResponse<Void> rescheduleReserveTimeSlot(@RequestParam(name = "id")  Long id  , @RequestParam(name = "newId")  Long newId) {

         timeSlotService.rescheduleReserveTimeSlot(id,newId);
        return ApiResponse.noContent();
    }

    @GetMapping("/time-slots")
    public ApiResponse<List<TimeSlotDto>> getTimeSlotsPerDay(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.ok(timeSlotService.getAllTimeSlotsByPerDay(date));
    }


}
