package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ExtensionRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.ExtensionRequestMapper;
import gov.saip.applicationservice.common.model.ExtensionRequest;
import gov.saip.applicationservice.common.service.ExtensionRequestService;
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

public class ExtensionRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExtensionRequestService extensionRequestService;

    @InjectMocks
    private ExtensionRequestController extensionRequestController;

    @Mock
    private ExtensionRequestMapper extensionRequestMapper;

    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(extensionRequestController).build();
    }

    @Test
    public void testGetAllReports() throws Exception {
        Long appId = 1L;

        ExtensionRequestDto extensionRequestDto = new ExtensionRequestDto();
        extensionRequestDto.setId(1L);

        ExtensionRequest extensionRequest = new ExtensionRequest();
        extensionRequest.setId(1L);

        List<ExtensionRequestDto> extensionRequestDtos = List.of(extensionRequestDto);
        List<ExtensionRequest> extensionRequests = List.of(extensionRequest);

        when(extensionRequestMapper.map(extensionRequests)).thenReturn(extensionRequestDtos);
        when(extensionRequestService.getAllByApplicationId(anyLong(), Mockito.eq(SupportServiceType.EXTENSION)))
                .thenReturn(extensionRequests);

        mockMvc.perform(get("/kc/support-service/extension/{appId}/application", appId))
                .andExpect(status().isOk());
    }

}