package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsDto;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsResponse;
import gov.saip.applicationservice.common.enums.AssistiveSupportServiceSpecialistDecision;
import gov.saip.applicationservice.common.mapper.SupportServiceTypeDecisionsMapper;
import gov.saip.applicationservice.common.model.SupportServicesTypeDecisions;
import gov.saip.applicationservice.common.service.SupportServiceTypeDecisionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SupportServiceTypeDecisionsControllerTest {

    @Mock
    private SupportServiceTypeDecisionsService service;

    @InjectMocks
    private SupportServiceTypeDecisionsController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    SupportServiceTypeDecisionsMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    public void testGetAllDecisionsByServiceId() {
        // Arrange
        Long serviceId = 1L;
        SupportServicesTypeDecisions lastDecisions = new SupportServicesTypeDecisions();
        ApiResponse<SupportServicesTypeDecisionsResponse> expectedResponse = ApiResponse.ok(new SupportServicesTypeDecisionsResponse());

        when(service.getLastDecisionsForLoggedIn(serviceId)).thenReturn(lastDecisions);
        when(mapper.mapToSupportServicesTypeDecisionsResponse(lastDecisions)).thenReturn(expectedResponse.getPayload());

        ApiResponse<SupportServicesTypeDecisionsResponse> response = controller.getAllDecisionsByServiceId(serviceId);

        assertNotNull(response);
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getCode(), response.getCode());

        verify(service, times(1)).getLastDecisionsForLoggedIn(serviceId);
        verify(mapper, times(1)).mapToSupportServicesTypeDecisionsResponse(lastDecisions);
    }

    @Test
    public void testInsertWithNoOtherApplyingLogic() {
        // Arrange
        SupportServicesTypeDecisionsDto dto = new SupportServicesTypeDecisionsDto();
        SupportServicesTypeDecisions decisions = new SupportServicesTypeDecisions();
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);

        when(service.insertWithNoOtherApplyingLogic(dto)).thenReturn(decisions);

        // Act
        ApiResponse<Long> response = controller.insertWithNoOtherApplyingLogic(dto);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getCode(), response.getCode());

        verify(service, times(1)).insertWithNoOtherApplyingLogic(dto);
    }


}

