package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.AgentSubstitutionRequestDto;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.facade.ApplicationAgentFacade;
import gov.saip.applicationservice.common.mapper.AgentSubstitutionRequestMapper;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.service.AgentSubstitutionRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class AgentSubstitutionRequestControllerTest {

    @InjectMocks
    private AgentSubstitutionRequestController agentSubstitutionRequestController;
    @Mock
    private AgentSubstitutionRequestService agentSubstitutionRequestService;
    @Mock
    private AgentSubstitutionRequestMapper agentSubstitutionRequestMapper;
    @Mock
    private ApplicationAgentFacade applicationAgentFacade;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(agentSubstitutionRequestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllReports() throws Exception {
        
        Long appId = 1l;
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1L);
        AgentSubstitutionRequestDto agentSubstitutionRequestDto = new AgentSubstitutionRequestDto();
        agentSubstitutionRequestDto.setId(1L);
        List<AgentSubstitutionRequestDto> agentSubstitutionRequests = Arrays.asList(agentSubstitutionRequestDto);
        List<AgentSubstitutionRequest> list = Arrays.asList(agentSubstitutionRequest);
        Mockito.when(agentSubstitutionRequestService.getAllByApplicationId(appId)).thenReturn(list);
        ApiResponse<List<AgentSubstitutionRequestDto>> expectedResponse = ApiResponse.ok(agentSubstitutionRequests);
        Mockito.when(agentSubstitutionRequestMapper.map(list)).thenReturn(agentSubstitutionRequests);

       
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/support-service/agent-substitution/1/application", appId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testInsertAddNewAgentRequest() throws Exception {
        AgentSubstitutionRequestDto agentSubstitutionRequestDto = new AgentSubstitutionRequestDto();
        agentSubstitutionRequestDto.setId(1L);
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1L);
        Mockito.when(agentSubstitutionRequestMapper.unMap(ArgumentMatchers.any(AgentSubstitutionRequestDto.class))).thenReturn(agentSubstitutionRequest);
        Mockito.when(applicationAgentFacade.insertAddAgentRequest(agentSubstitutionRequest)).thenReturn(agentSubstitutionRequest);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/support-service/agent-substitution/add-agent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agentSubstitutionRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testInsertSubstituteAgentRequest() throws Exception {
        AgentSubstitutionRequestDto agentSubstitutionRequestDto = new AgentSubstitutionRequestDto();
        agentSubstitutionRequestDto.setId(1L);
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1L);
        boolean substituteAll = false;
        Long userId = 1L;
        Long agentId = 2L;
        Mockito.when(agentSubstitutionRequestMapper.unMap(ArgumentMatchers.any(AgentSubstitutionRequestDto.class))).thenReturn(agentSubstitutionRequest);
        Mockito.when(applicationAgentFacade.insertSubstituteAgentRequest(agentSubstitutionRequest)).thenReturn(agentSubstitutionRequest);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/support-service/agent-substitution/substitute-agent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("substituteAll", String.valueOf(substituteAll))
                        .param("userId", String.valueOf(userId))
                        .param("agentId", String.valueOf(agentId))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agentSubstitutionRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testInsertSubstituteAgentRequestToAllAppsByAgentId() throws Exception {
        AgentSubstitutionRequestDto agentSubstitutionRequestDto = new AgentSubstitutionRequestDto();
        agentSubstitutionRequestDto.setId(1L);
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1L);
        boolean substituteAll = true;
        String customerCode = "code001";
        Long agentId = 2L;
        Mockito.doReturn(agentSubstitutionRequest).when(agentSubstitutionRequestMapper).unMap(ArgumentMatchers.any(AgentSubstitutionRequestDto.class));
        Mockito.doReturn(agentSubstitutionRequest).when(applicationAgentFacade).insertSubstituteAgentRequestToAllAppsByAgentId(agentSubstitutionRequest, customerCode, agentId);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/kc/support-service/agent-substitution/substitute-agent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("substituteAll", String.valueOf(substituteAll))
                        .param("customerCode", String.valueOf(customerCode))
                        .param("agentId", String.valueOf(agentId))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agentSubstitutionRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteAgentByAppIds() throws Exception {
        Long userId = 1L;
        List<Long> applicationIds = Arrays.asList(1L, 2L);
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1L);
        Mockito.when(applicationAgentFacade.deleteAgentsByAppIds(applicationIds)).thenReturn(agentSubstitutionRequest);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/kc/support-service/agent-substitution/delete/application-agent", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("applicationIds", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteAgentByAgentAndUserIds() throws Exception {
        String customerCode = "code001";
        Long agentId = 2L;
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1L);
        Mockito.when(applicationAgentFacade.deleteAgentsByAgentAndCustomerCode(agentId, customerCode)).thenReturn(agentSubstitutionRequest);
        ApiResponse<Long> expectedResponse = ApiResponse.ok(1L);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/kc/support-service/agent-substitution/delete/application-agent") // Set the URL of the endpoint
                .param("agentId", String.valueOf(agentId))
                .accept(MediaType.APPLICATION_JSON)
                .param("customerCode", customerCode);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}