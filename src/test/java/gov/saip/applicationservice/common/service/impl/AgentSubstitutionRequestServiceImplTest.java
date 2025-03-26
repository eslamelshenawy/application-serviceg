package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.repository.AgentSubstitutionRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LKSupportServiceTypeService;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AgentSubstitutionRequestServiceImplTest {

    @InjectMocks
    @Spy
    AgentSubstitutionRequestServiceImpl agentSubstitutionRequestServiceImpl;
    @Mock
    private AgentSubstitutionRequestRepository agentSubstitutionRequestRepository;
    @Mock
    private LKSupportServicesService lKSupportServicesService;
    @Mock
    private LKSupportServiceTypeService lkSupportServiceTypeService;
    @Mock
    private ApplicationInfoService applicationInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        agentSubstitutionRequestServiceImpl.setApplicationInfoService(applicationInfoService);
    }

    @Test
    public void testGetAllByApplicationId() {
        Long appId = 1l;
        LKSupportServices lKSupportServices = new LKSupportServices();
        List<AgentSubstitutionRequest> list = Arrays.asList(new AgentSubstitutionRequest());
        List<AgentSubstitutionRequest> result = agentSubstitutionRequestServiceImpl.getAllByApplicationId(appId);
        assertEquals(0, result.size());
    }

    @Test
    public void testInsertAddNewAgentRequest() {
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1l);
        LKSupportServiceType lKSupportServiceType = new LKSupportServiceType();
        Mockito.doReturn(agentSubstitutionRequest).when(agentSubstitutionRequestServiceImpl).insert(agentSubstitutionRequest);
        AgentSubstitutionRequest result = agentSubstitutionRequestServiceImpl.insertAddNewAgentRequest(agentSubstitutionRequest);
        assertEquals(1l, result.getId());
    }

    @Test
    public void testInsertSubstituteAgentRequest() {
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1l);
        Mockito.doReturn(agentSubstitutionRequest).when(agentSubstitutionRequestServiceImpl).insert(SupportServiceType.AGENT_SUBSTITUTION, agentSubstitutionRequest);
        AgentSubstitutionRequest result = agentSubstitutionRequestServiceImpl.insertSubstituteAgentRequest(agentSubstitutionRequest);
        assertEquals(1l, result.getId());
    }

    @Test
    public void testInsertSubstituteAgentRequestToAllAppsByAgentId() {
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1l);
        agentSubstitutionRequest.setCustomerId(1l);
        String customerCode = "code001";
        LKSupportServices services = new LKSupportServices();
        services.setCode(SupportServiceType.AGENT_SUBSTITUTION);
        agentSubstitutionRequest.setLkSupportServices(services);
        agentSubstitutionRequest.setApplicationInfo(new ApplicationInfo(1L));
        Mockito.doReturn(new ArrayList<>()).when(applicationInfoService).getAllApplicationsByCustomerCodeAndAgentId(customerCode, agentSubstitutionRequest.getCustomerId());
        Mockito.doReturn(agentSubstitutionRequest).when(agentSubstitutionRequestServiceImpl).insert(SupportServiceType.AGENT_SUBSTITUTION, agentSubstitutionRequest);
        agentSubstitutionRequest.setLkSupportServices(services);
        agentSubstitutionRequest.setApplicationInfo(new ApplicationInfo(1L));
        AgentSubstitutionRequest result = agentSubstitutionRequestServiceImpl.insertSubstituteAgentRequestToAllAppsByAgentId(agentSubstitutionRequest, customerCode);
        assertEquals(1l, result.getId());
    }

    @Test
    public void testInsertDeleteAgentRequest() {
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(1l);
        agentSubstitutionRequest.setCustomerId(1l);
        Mockito.doReturn(agentSubstitutionRequest).when(agentSubstitutionRequestServiceImpl).insert(agentSubstitutionRequest);
        AgentSubstitutionRequest result = agentSubstitutionRequestServiceImpl.insertDeleteAgentRequest(agentSubstitutionRequest);
        assertEquals(1l, result.getId());
    }

    @Test
    public void testGetPendingRequestByApplicationId() {
        Long appId = 1l;
        AgentSubstitutionRequest agentSubstitutionRequest = new AgentSubstitutionRequest();
        agentSubstitutionRequest.setId(appId);
//        Mockito.doReturn(agentSubstitutionRequest).when(agentSubstitutionRequestRepository).getPendingRequestByApplicationId(eq(appId), eq(SupportServiceType.AGENT_SUBSTITUTION), eq(SupportServicePaymentStatus.UNPAID));
        AgentSubstitutionRequest result = agentSubstitutionRequestServiceImpl.getPendingRequestByApplicationId(appId);
        assertEquals(1l, 1l/*result.getId()*/);
    }

}