package gov.saip.applicationservice.common.facade.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import gov.saip.applicationservice.common.service.AgentSubstitutionRequestService;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LKSupportServiceTypeService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationAgentFacadeImplTest {

    @InjectMocks
    @Spy
    private ApplicationAgentFacadeImpl applicationAgentFacadeImpl;

    @Mock
    private ApplicationAgentService applicationAgentService;

    @Mock
    private SupportServiceProcess supportServiceProcess;
    @Mock
    private AgentSubstitutionRequestService agentSubstitutionRequestService;

    @Mock
    private LKSupportServiceTypeService lkSupportServiceTypeService;
    @Mock
    private ApplicationInfoService applicationInfoService;
    @Mock
    private CustomerServiceFeignClient customerServiceFeignClient;

    @Mock
    private ApplicationCustomerService applicationCustomerService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testInsertAddAgentRequest() {
        AgentSubstitutionRequest request = new AgentSubstitutionRequest();
        request.setApplications(List.of(new ApplicationInfo(1L)));
        LKSupportServiceType lkSupportServiceType = new LKSupportServiceType();
        lkSupportServiceType.setIsFree(true);
        when(lkSupportServiceTypeService.findByCode(any())).thenReturn(lkSupportServiceType);
        doNothing().when(applicationAgentFacadeImpl).startSupportServiceProcess(any(), any(), any());

        applicationAgentFacadeImpl.insertAddAgentRequest(request);

        verify(lkSupportServiceTypeService).findByCode(any(SupportedServiceCode.class));
    }

    @Test
    void testInsertSubstituteAgentRequest() {
        AgentSubstitutionRequest request = new AgentSubstitutionRequest();
        request.setApplications(new ArrayList<>());

       Assertions.assertThrows(BusinessException.class,
                () -> applicationAgentFacadeImpl.insertSubstituteAgentRequest(request));

    }

    @Test
    void testInsertSubstituteAgentRequestToAllAppsByAgentId() {

        AgentSubstitutionRequest request = new AgentSubstitutionRequest();
        List<ApplicationInfo> allAgentApplications = Arrays.asList(new ApplicationInfo(), new ApplicationInfo());


        when(applicationInfoService.getAllApplicationsByCustomerCodeAndAgentId(any(String.class), any(Long.class))).thenReturn(allAgentApplications);
        LKSupportServiceType lkSupportServiceType = new LKSupportServiceType();
        lkSupportServiceType.setIsFree(true);
        when(lkSupportServiceTypeService.findByCode(SupportedServiceCode.SUBSTITUTE_AGENT)).thenReturn(lkSupportServiceType);
        when(agentSubstitutionRequestService.insert(SupportServiceType.AGENT_SUBSTITUTION,request)).thenReturn(new AgentSubstitutionRequest());
        doNothing().when(applicationAgentFacadeImpl).startSupportServiceProcess(any(), any(), any());

        AgentSubstitutionRequest result = applicationAgentFacadeImpl.insertSubstituteAgentRequestToAllAppsByAgentId(request, "code001", 2L);


        verify(applicationInfoService).getAllApplicationsByCustomerCodeAndAgentId(any(String.class), any(Long.class));
        Assertions.assertNotNull(result.getApplications());
    }

    @Test
    void testSubstituteApplicationAgentByAgentSubstitutionRequest() {
        Long agentId = 1L;
        AgentSubstitutionRequest request = new AgentSubstitutionRequest();
        request.setId(2L);
        request.setApplications(List.of(new ApplicationInfo(1L)));
        Optional<AgentSubstitutionRequest> optional = Optional.of(request);
        LKSupportServiceType lkSupportServiceType = new LKSupportServiceType();
        lkSupportServiceType.setIsFree(true);
        when(lkSupportServiceTypeService.findByCode(SupportedServiceCode.SUBSTITUTE_AGENT)).thenReturn(lkSupportServiceType);
        when(agentSubstitutionRequestService.insert(SupportServiceType.AGENT_SUBSTITUTION, request)).thenReturn(new AgentSubstitutionRequest());
        doNothing().when(applicationAgentFacadeImpl).startSupportServiceProcess(any(), any(), any());


        AgentSubstitutionRequest result = applicationAgentFacadeImpl.substituteApplicationAgentByAgentSubstitutionRequest(request);

        Assertions.assertEquals(request.getId(), result.getId());
    }

    @Test
    void testDeleteAgentsByAgentAndUserIds() {
        Long agentId = 1L;
        String customerCode = "code001";
        LKSupportServiceType lkSupportServiceType = new LKSupportServiceType();
        lkSupportServiceType.setCode(SupportedServiceCode.DELETE_AGENT);
        lkSupportServiceType.setIsFree(true);
        when(lkSupportServiceTypeService.findByCode(SupportedServiceCode.DELETE_AGENT)).thenReturn(lkSupportServiceType);
        List<Long> appIds = Arrays.asList(3L, 4L);
        when(applicationAgentService.getAllAgentApplications(agentId, customerCode)).thenReturn(appIds);
        List<ApplicationInfo> applications = appIds.stream().map(id -> new ApplicationInfo(id)).collect(Collectors.toList());
        doNothing().when(applicationAgentFacadeImpl).startSupportServiceProcess(any(), any(), any());

        AgentSubstitutionRequest result = applicationAgentFacadeImpl.deleteAgentsByAgentAndCustomerCode(agentId, customerCode);

        verify(lkSupportServiceTypeService).findByCode(SupportedServiceCode.DELETE_AGENT);
        verify(applicationAgentService).getAllAgentApplications(agentId, customerCode);

    }

    @Test
    void testDeleteAgentsByAppIds() {
        List<Long> applications = Arrays.asList(1L, 2L);
        LKSupportServiceType lkSupportServiceType = new LKSupportServiceType();
        lkSupportServiceType.setCode(SupportedServiceCode.DELETE_AGENT);
        lkSupportServiceType.setIsFree(true);
        when(lkSupportServiceTypeService.findByCode(SupportedServiceCode.DELETE_AGENT)).thenReturn(lkSupportServiceType);
        Long agentId = 3L;
        when(applicationAgentService.getCurrentApplicationAgent(applications.get(0))).thenReturn(agentId);
        doNothing().when(applicationAgentFacadeImpl).startSupportServiceProcess(any(), any(), any());

        AgentSubstitutionRequest result = applicationAgentFacadeImpl.deleteAgentsByAppIds(applications);

        verify(lkSupportServiceTypeService).findByCode(SupportedServiceCode.DELETE_AGENT);
        verify(applicationAgentService).getCurrentApplicationAgent(applications.get(0));
        Assertions.assertNotNull(result);
    }

    @Test
    void testProcessSubsituteAgentRequest() {
        
        Long requestId = 1L;
        AgentSubstitutionRequest agentRequest = new AgentSubstitutionRequest();
        agentRequest.setId(1L);
        agentRequest.setPaymentStatus(SupportServicePaymentStatus.UNPAID);
        when(agentSubstitutionRequestService.getById(requestId)).thenReturn(Optional.of(agentRequest));
        ApiResponse api = new ApiResponse<>();
        api.setPayload("code");
        when(customerServiceFeignClient.getCustomerCodeByCustomerId(any())).thenReturn(api);

        applicationAgentFacadeImpl.processSubsituteAgentRequest(requestId);

        
        verify(agentSubstitutionRequestService).getById(requestId);
    }

    @Test
    void testProcessAddAgentRequest() {
        
        Long requestId = 1L;
        AgentSubstitutionRequest agentRequest = new AgentSubstitutionRequest();
        agentRequest.setId(1L);
        agentRequest.setPaymentStatus(SupportServicePaymentStatus.UNPAID);
        when(agentSubstitutionRequestService.getById(requestId)).thenReturn(Optional.of(agentRequest));
        ApiResponse api = new ApiResponse<>();
        api.setPayload("code");
        when(customerServiceFeignClient.getCustomerCodeByCustomerId(any())).thenReturn(api);
        
        applicationAgentFacadeImpl.processAddAgentRequest(requestId);

        
        verify(agentSubstitutionRequestService).getById(requestId);
    }

    @Test
    void testProcessDeleteAgentRequest() {
        Long requestId = 1L;
        AgentSubstitutionRequest agentRequest = new AgentSubstitutionRequest();
        agentRequest.setId(1L);
        agentRequest.setPaymentStatus(SupportServicePaymentStatus.UNPAID);
        ApplicationInfo app1 = new ApplicationInfo();
        app1.setId(1L);
        ApplicationInfo app2 = new ApplicationInfo();
        app2.setId(2L);
        List<ApplicationInfo> apps = Arrays.asList(app1, app2);
        agentRequest.setApplications(apps);
        when(agentSubstitutionRequestService.getById(requestId)).thenReturn(Optional.of(agentRequest));

        applicationAgentFacadeImpl.processDeleteAgentRequest(requestId);

        verify(agentSubstitutionRequestService).getById(requestId);
        verify(applicationAgentService).deleteAgentsByAppIds(Arrays.asList(1L, 2L));
    }
}