package gov.saip.applicationservice.common.service.agency.impl;

import gov.saip.applicationservice.common.dto.KeyValueDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
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

import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.repository.agency.ApplicationAgentRepository;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ApplicationAgentServiceImplTest {

    @Mock
    private ApplicationAgentRepository applicationAgentRepository;

    @Mock
    private ApplicationSupportServicesTypeService applicationSupportServicesTypeService;

    @InjectMocks
    @Spy
    private ApplicationAgentServiceImpl applicationAgentService;

    private ApplicationAgent applicationAgent;

    @BeforeEach
    void setUp() {
        applicationAgent = new ApplicationAgent();
        applicationAgent.setId(1L);
        applicationAgent.setCustomerId(1L);
        applicationAgent.setStatus(ApplicationAgentStatus.ACTIVE);
        ApplicationInfo application = new ApplicationInfo();
        application.setId(1L);
        applicationAgent.setApplication(application);
    }

    @Test
    void testInsert() {
        
        Mockito.doReturn(new ApplicationAgent()).when(applicationAgentService).insert(any());
        
        ApplicationAgent actualApplicationAgent = applicationAgentService.insert(applicationAgent);

        
        Assertions.assertNotNull(actualApplicationAgent);
    }

    @Test
    void testInsert_BusinessException() {
        
        Mockito.when(applicationAgentRepository.existsByApplicationIdAndCustomerId(applicationAgent.getApplication().getId(), applicationAgent.getCustomerId())).thenReturn(true);

        
        Assertions.assertThrows(BusinessException.class, () -> applicationAgentService.insert(applicationAgent));
    }

    @Test
    void testGetAllApplicationAgents() {
        
        Long appId = 1L;
        List<Long> agentIds = new ArrayList<>();
        agentIds.add(1L);

        Mockito.when(applicationAgentRepository.getAllApplicationAgentsByAppId(appId)).thenReturn(agentIds);

        
        List<Long> actualAgentIds = applicationAgentService.getAllApplicationAgents(appId);

        
        Assertions.assertEquals(agentIds, actualAgentIds);
    }

    @Test
    void testGetAllAgentsByUserId() {
        
        String customerCode = "code001";
        List<Long> agentIds = new ArrayList<>();
        agentIds.add(1L);

        Mockito.when(applicationAgentRepository.getAllCustomerAgentsByCustomerCode(customerCode)).thenReturn(agentIds);

        
        List<Long> actualAgentIds = applicationAgentService.getAllCustomerAgentsByCustomerCode(customerCode);

        
        Assertions.assertEquals(agentIds, actualAgentIds);
    }

    @Test
    void testGetAllAgentApplications() {
        List<Long> applicationIds = new ArrayList<>();
        applicationIds.add(1L);

        Mockito.when(applicationAgentRepository.getApplicationIdsByAgentAndUserIds(1L, "code001", ApplicationAgentStatus.ACTIVE)).thenReturn(applicationIds);

        List<Long> actualApplicationIds = applicationAgentService.getAllAgentApplications(1L, "code001");

        Assertions.assertEquals(applicationIds, actualApplicationIds);
    }

    @Test
    void testDeActivateApplicationAgentsByAgentAndApps() {
        List<Long> appIds = new ArrayList<>();
        appIds.add(1L);

        List<Long> newApplicationAgentIds = new ArrayList<>();
        newApplicationAgentIds.add(2L);

        Mockito.doNothing().when(applicationAgentRepository).deActivateApplicationAgentsByAgentAndApps(appIds, newApplicationAgentIds, ApplicationAgentStatus.CHANGED, ApplicationAgentStatus.ACTIVE);

        applicationAgentService.deActivateApplicationAgentsByAgentAndApps(appIds, newApplicationAgentIds);

        Mockito.verify(applicationAgentRepository, Mockito.times(1)).deActivateApplicationAgentsByAgentAndApps(appIds, newApplicationAgentIds, ApplicationAgentStatus.CHANGED, ApplicationAgentStatus.ACTIVE);
    }



    @Test
    void testGetCustomerAgentsAndCounts() {
        List<KeyValueDto<Long, Long>> data = new ArrayList<>();

        Mockito.when(applicationAgentRepository.getCustomerAgentsAndCounts(anyString(), any(ApplicationAgentStatus.class), Mockito.anyList())).thenReturn(data);

        Map<Long, Long> actualData = applicationAgentService.getCustomerAgentsAndCounts("code001");

        Assertions.assertNotNull(actualData);
    }

    @Test
    void testDeleteAgentsByAgentAndUserIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);

        Mockito.when(applicationAgentRepository.getApplicationAgentIdsByAgentAndUserIdAndStatus(1L, "code001", ApplicationAgentStatus.ACTIVE)).thenReturn(ids);
        Mockito.doNothing().when(applicationAgentRepository).delteAgentsByIds(ids, ApplicationAgentStatus.DELETED, ApplicationAgentStatus.ACTIVE);

        applicationAgentService.deleteAgentsByAgentAndUserIds(1L, "code001");

        Mockito.verify(applicationAgentRepository, Mockito.times(1)).delteAgentsByIds(ids, ApplicationAgentStatus.DELETED, ApplicationAgentStatus.ACTIVE);
    }

    @Test
    void testDeleteAgentsByAppIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);

        Mockito.doNothing().when(applicationAgentRepository).deleteAgentsByAppIds(ids, ApplicationAgentStatus.DELETED, ApplicationAgentStatus.ACTIVE);

        applicationAgentService.deleteAgentsByAppIds(ids);

        Mockito.verify(applicationAgentRepository, Mockito.times(1)).deleteAgentsByAppIds(ids, ApplicationAgentStatus.DELETED, ApplicationAgentStatus.ACTIVE);
    }

    @Test
    public void testGetCurrentApplicationAgent() {

        Mockito.when(applicationAgentRepository.getAgentIdByAppIdAndStatus(Mockito.anyLong(), any()))
                        .thenReturn(1L);
        Long actualData = applicationAgentService.getCurrentApplicationAgent(1L);

        Assertions.assertNotNull(actualData);
    }
}