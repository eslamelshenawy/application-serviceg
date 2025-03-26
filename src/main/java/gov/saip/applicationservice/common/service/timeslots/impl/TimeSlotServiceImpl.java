package gov.saip.applicationservice.common.service.timeslots.impl;

import gov.saip.applicationservice.base.service.BaseClientServiceImpl;
import gov.saip.applicationservice.client.TimeSlotClient;
import gov.saip.applicationservice.common.clients.rest.feigns.UserManageClient;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotDto;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotPeriodDto;
import gov.saip.applicationservice.common.service.timeslots.TimeSlotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TimeSlotServiceImpl  extends BaseClientServiceImpl<TimeSlotDto, Long> implements TimeSlotService {
    private final TimeSlotClient timeSlotClient;



    @Override
    public void reserveTimeSlotById(Long id) {
        timeSlotClient.reserveTimeSlotById(id).getPayload();
    }


    @Override
    public void rescheduleReserveTimeSlot(Long id, Long newId) {
        timeSlotClient.rescheduleReserveTimeSlot(id,newId).getPayload();
    }

    @Override
    public List<TimeSlotDto> getAllTimeSlotsByPerDay(LocalDate date) {
        return timeSlotClient.getTimeSlotsPerDay(date).getPayload();
    }


}
