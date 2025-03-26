package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.InitialModificationRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.InitialModificationRequestMapper;
import gov.saip.applicationservice.common.model.InitialModificationRequest;
import gov.saip.applicationservice.common.service.InitialModificationRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InitialModificationRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InitialModificationRequestService initialModificationRequestService;

    @InjectMocks
    private InitialModificationRequestController initialModificationRequestController;

    @Mock
    private InitialModificationRequestMapper initialModificationRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(initialModificationRequestController).build();
    }

    @Test
    public void testGetAllReports() throws Exception {
        // Setup
        Long appId = 1L;

        InitialModificationRequestDto initialModificationRequestDto = new InitialModificationRequestDto();
        initialModificationRequestDto.setId(1L);

        InitialModificationRequest initialModificationRequest = new InitialModificationRequest();
        initialModificationRequest.setId(1L);

        List<InitialModificationRequestDto> initialModificationRequestDtos = List.of(initialModificationRequestDto);
        List<InitialModificationRequest> initialModificationRequests = List.of(initialModificationRequest);

        when(initialModificationRequestService.getAllByApplicationId(anyLong(), Mockito.eq(SupportServiceType.INITIAL_MODIFICATION)))
                .thenReturn(initialModificationRequests);
        when(initialModificationRequestMapper.map(Mockito.anyList())).thenReturn(initialModificationRequestDtos);

        // Execute and Assert
        mockMvc.perform(get("/kc/support-service/initial-modification/{appId}/application", appId))
                .andExpect(status().isOk());
    }

}