package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.RetractionRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.RetractionRequestMapper;
import gov.saip.applicationservice.common.model.RetractionRequest;
import gov.saip.applicationservice.common.service.RetractionRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RetractionRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RetractionRequestService retractionRequestServiceMock;

    @InjectMocks
    private RetractionRequestController retractionRequestController;

    @Mock
    private RetractionRequestMapper retractionRequestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(retractionRequestController).build();
    }

    @Test
    void testGetAllReports() throws Exception {
        Long appId = 1L;
        RetractionRequest request1 = new RetractionRequest();
        RetractionRequest request2 = new RetractionRequest();
        List<RetractionRequest> requestList = Arrays.asList(request1, request2);
        RetractionRequestDto dto1 = new RetractionRequestDto();
        RetractionRequestDto dto2 = new RetractionRequestDto();
        List<RetractionRequestDto> dtoList = Arrays.asList(dto1, dto2);
        ApiResponse<List<RetractionRequestDto>> apiResponse = ApiResponse.ok(dtoList);

        when(retractionRequestServiceMock.getAllByApplicationId(1L, SupportServiceType.RETRACTION)).thenReturn(requestList);
        when(retractionRequestMapper.map(Mockito.anyList())).thenReturn(dtoList);

        mockMvc.perform(get("/kc/support-service/retraction/" + appId + "/application"))
                .andExpect(status().isOk());
    }
}