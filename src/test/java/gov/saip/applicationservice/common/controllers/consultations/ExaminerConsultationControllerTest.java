package gov.saip.applicationservice.common.controllers.consultations;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationDto;
import gov.saip.applicationservice.common.dto.consultation.ExaminerConsultationRequestDto;
import gov.saip.applicationservice.common.service.Consultation.ConsultationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExaminerConsultationControllerTest {

    @Mock
    private ConsultationsService consultationsService;

    @InjectMocks
    private ExaminerConsultationController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getConsultationById() throws Exception {
        long consultationId = 1L;
        ExaminerConsultationDto consultationDto = new ExaminerConsultationDto();
        ApiResponse<ExaminerConsultationDto> apiResponse = ApiResponse.ok(consultationDto);

        when(consultationsService.getConsultationById(consultationId)).thenReturn(consultationDto);

        mockMvc.perform(get("/kc/examiner-consultation/{consultationId}/retrieve", consultationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists())
                .andExpect(jsonPath("$.payload").isMap())
                .andExpect(jsonPath("$.payload").isNotEmpty());

        verify(consultationsService, times(1)).getConsultationById(consultationId);
    }

    @Test
    void replayOnConsultation() throws Exception {
        ExaminerConsultationRequestDto requestDto = new ExaminerConsultationRequestDto();
        long consultationId = 1L;
        ApiResponse<Long> apiResponse = ApiResponse.ok(consultationId);

        when(consultationsService.Replay(any(ExaminerConsultationRequestDto.class))).thenReturn(consultationId);

        mockMvc.perform(post("/kc/examiner-consultation/replay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists())
                .andExpect(jsonPath("$.payload").isNumber());

        // Verify using any() matcher
        verify(consultationsService, times(1)).Replay(any(ExaminerConsultationRequestDto.class));
    }


    @Test
    void refuseConsultation() throws Exception {
        long consultationId = 1L;
        ApiResponse<String> apiResponse = ApiResponse.ok("Refused");

        when(consultationsService.refuseConsultation(consultationId)).thenReturn("Refused");

        mockMvc.perform(post("/kc/examiner-consultation/refuse/{consultationId}", consultationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists())
                .andExpect(jsonPath("$.payload").isString());

        verify(consultationsService, times(1)).refuseConsultation(consultationId);
    }

    @Test
    void listAllConsultationsByApplicationId() throws Exception {
        long applicationId = 1L;
        ExaminerConsultationDto consultationDto = new ExaminerConsultationDto();
        ApiResponse<List<ExaminerConsultationDto>> apiResponse = ApiResponse.ok(Collections.singletonList(consultationDto));

        when(consultationsService.listAllConsultationByAppId(applicationId))
                .thenReturn(Collections.singletonList(consultationDto));

        mockMvc.perform(get("/kc/examiner-consultation/application/{appId}", applicationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload").exists())
                .andExpect(jsonPath("$.payload").isArray())
                .andExpect(jsonPath("$.payload[0]").isMap());

        verify(consultationsService, times(1)).listAllConsultationByAppId(applicationId);
    }

}

