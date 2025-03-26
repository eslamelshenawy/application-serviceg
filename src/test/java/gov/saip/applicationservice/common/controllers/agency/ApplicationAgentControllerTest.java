package gov.saip.applicationservice.common.controllers.agency;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.agency.ApplicationAgentDto;
import gov.saip.applicationservice.common.mapper.agency.ApplicationAgentMapper;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ApplicationAgentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationAgentService applicationAgentService;

    @InjectMocks
    private ApplicationAgentController applicationAgentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicationAgentController).build();
    }

    @Test
    void testGetCurrentApplicationAgent() throws Exception {
        Long appId = 1L;
        Long agentId = 2L;

        when(applicationAgentService.getCurrentApplicationAgent(eq(appId))).thenReturn(agentId);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/application-agent/application/" + appId + "/agent")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ApiResponse.ok(agentId))));
    }

    @Test
    void testGetAllApplicationAgents() throws Exception {
        Long appId = 1L;
        List<Long> agentIds = Arrays.asList(1L, 2L, 3L);

        when(applicationAgentService.getAllApplicationAgents(eq(appId))).thenReturn(agentIds);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/application-agent/application/" + appId + "/agents")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ApiResponse.ok(agentIds))));
    }

    @Test
    void testGetAllAgentsByUserId() throws Exception {
        String customerCode = "code-01";
        List<Long> agentIds = Arrays.asList(1L, 2L, 3L);

        when(applicationAgentService.getAllCustomerAgentsByCustomerCode(eq(customerCode))).thenReturn(agentIds);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/application-agent/customer/" + customerCode + "/agents")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ApiResponse.ok(agentIds))));
    }

    @Test
    void testGetCustomerAgentsAndCounts() throws Exception {
        String customerCode = "code001";
        Map<Long, Long> agentsAndCounts = new HashMap<>();
        agentsAndCounts.put(1L, 5L);
        agentsAndCounts.put(2L, 2L);

        when(applicationAgentService.getCustomerAgentsAndCounts(eq(customerCode))).thenReturn(agentsAndCounts);

        mockMvc.perform(MockMvcRequestBuilders.get("/kc/application-agent/customer/" + customerCode + "/agent-app-count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ApiResponse.ok(agentsAndCounts))));
    }
}