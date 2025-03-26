package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.EvictionRequestDto;
import gov.saip.applicationservice.common.mapper.EvictionRequestMapper;
import gov.saip.applicationservice.common.model.EvictionRequest;
import gov.saip.applicationservice.common.service.EvictionRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.EVICTION;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EvictionRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EvictionRequestService evictionRequestService;

    @InjectMocks
    private EvictionRequestController evictionRequestController;

    @Mock
    private EvictionRequestMapper evictionRequestMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(evictionRequestController).build();
    }

    @Test
    public void testGetAllReports() throws Exception {
        Long appId = 1L;

        EvictionRequestDto evictionRequestDto = new EvictionRequestDto();
        evictionRequestDto.setId(1L);

        EvictionRequest evictionRequest = new EvictionRequest();
        evictionRequest.setId(1L);

        List<EvictionRequestDto> evictionRequestDtos = List.of(evictionRequestDto);

        List<EvictionRequest> evictionRequests = List.of(evictionRequest);

        when(evictionRequestService.getAllByApplicationId(1L, EVICTION))
                .thenReturn(evictionRequests);
        when(evictionRequestMapper.map(evictionRequests)).thenReturn(evictionRequestDtos);

        mockMvc.perform(get("/kc/support-service/eviction/{appId}/application", appId))
                .andExpect(status().isOk());
    }

}