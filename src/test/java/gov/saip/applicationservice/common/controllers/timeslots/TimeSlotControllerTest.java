package gov.saip.applicationservice.common.controllers.timeslots;

import gov.saip.applicationservice.common.controllers.trademark.TrademarkDetailController;
import gov.saip.applicationservice.common.dto.timeslots.TimeSlotDto;
import gov.saip.applicationservice.common.service.timeslots.TimeSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TimeSlotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TimeSlotService timeSlotService;

    @InjectMocks
    private TimeSlotController timeSlotController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(timeSlotController).build();
    }

    @Test
    void reserveTimeSlotById() throws Exception {
        Long id = 1L;
        doNothing().when(timeSlotService).reserveTimeSlotById(id);

        mockMvc.perform(post("/kc/time-slot/reserve-time-slot/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(timeSlotService, times(1)).reserveTimeSlotById(id);
    }

    @Test
    void rescheduleReserveTimeSlot() throws Exception {
        Long id = 1L;
        Long newId = 2L;
        doNothing().when(timeSlotService).rescheduleReserveTimeSlot(id, newId);

        mockMvc.perform(put("/kc/time-slot/reserve-time-slot/reschedule")
                        .param("id", id.toString())
                        .param("newId", newId.toString()))
                .andExpect(status().isOk());

        verify(timeSlotService, times(1)).rescheduleReserveTimeSlot(id, newId);
    }

    @Test
    void getTimeSlotsPerDay() throws Exception {
        LocalDate date = LocalDate.now();
        List<TimeSlotDto> timeSlots = Arrays.asList(new TimeSlotDto(), new TimeSlotDto());
        when(timeSlotService.getAllTimeSlotsByPerDay(date)).thenReturn(timeSlots);

        mockMvc.perform(get("/kc/time-slot/time-slots").accept(MediaType.APPLICATION_JSON)
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").isArray())
                .andExpect(jsonPath("$.payload").isNotEmpty())
                .andExpect(jsonPath("$.payload[0]").exists());

        verify(timeSlotService, times(1)).getAllTimeSlotsByPerDay(date);
    }
}
