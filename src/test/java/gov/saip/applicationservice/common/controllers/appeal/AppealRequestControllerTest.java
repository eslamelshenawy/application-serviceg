package gov.saip.applicationservice.common.controllers.appeal;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.appeal.AppealDetailsDto;
import gov.saip.applicationservice.common.dto.appeal.AppealRequestDto;
import gov.saip.applicationservice.common.dto.appeal.AppealSupportServiceRequestDto;
import gov.saip.applicationservice.common.mapper.appeal.AppealRequestMapper;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import gov.saip.applicationservice.common.service.appeal.AppealRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

public class AppealRequestControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private AppealRequestService appealRequestService;

    @Mock
    private AppealRequestMapper appealRequestMapper;

    @InjectMocks
    private AppealRequestController appealRequestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appealRequestController).build();

    }

    @Test
    public void testAddCheckerDecision() throws Exception {
        AppealRequestDto dto = new AppealRequestDto();
        when(appealRequestService.addCheckerDecision(dto)).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/appeal-request/checker-decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateAppealRequest() throws Exception {
        AppealRequestDto appealRequestDto = new AppealRequestDto();
        String taskId = "task123";

        when(appealRequestMapper.unMap(appealRequestDto)).thenReturn(new AppealRequest());
        when(appealRequestService.updateAppealRequest(any(), eq(taskId))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/appeal-request/update-appeal-request?taskId=" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(appealRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddOfficialLetter() throws Exception {
        AppealRequestDto dto = new AppealRequestDto();
        when(appealRequestService.addOfficialLetter(dto)).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/appeal-request/official-letter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddAppealCommitteeDecision() throws Exception {
        AppealRequestDto dto = new AppealRequestDto();
        when(appealRequestService.addAppealCommitteeDecision(dto)).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/kc/appeal-request/committee-decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTradeMarkAppealDetails() throws Exception {
        String appealId = "1";
        AppealDetailsDto detailsDto = new AppealDetailsDto();
        when(appealRequestService.getTradeMarkAppealDetails(1L)).thenReturn(detailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/appeal-request/details/{appealId}", appealId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAppealDetailsBySupportServiceId() throws Exception {
        Long serviceId = 1L;
        AppealRequest appealRequest = new AppealRequest();
        AppealSupportServiceRequestDto requestDto = new AppealSupportServiceRequestDto();

        when(appealRequestService.findById(serviceId)).thenReturn(appealRequest);
        when(appealRequestMapper.mapAppealSupportServiceRequestDto(appealRequest)).thenReturn(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/appeal-request/service/{serviceId}", serviceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

