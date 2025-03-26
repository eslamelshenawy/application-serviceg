package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.RevokeVoluntaryRequestMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.RevokeVoluntryRequestRepository;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class RevokeVoluntaryServiceImplTest extends SupportServiceRequestServiceTest{

    @Mock
    private RevokeVoluntryRequestRepository revokeVoluntryRequestRepository;
    @Mock
    private SupportServiceProcess supportServiceProcess;
    @Mock
    private RevokeVoluntaryRequestMapper revokeVoluntaryRequestMapper;
    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;

    @InjectMocks
    private RevokeVoluntaryRequestServiceImpl revokeVoluntaryRequestService;
    Long appId;
    RevokeVoluntaryRequest entity;
    ApplicationInfo applicationInfo;

    @Mock
    LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public RevokeVoluntaryServiceImplTest() {
        appId = 1L;
        entity = new RevokeVoluntaryRequest();
        entity.setId(1L);
        applicationInfo = new ApplicationInfo();
        applicationInfo.setId(appId);
        applicationInfo.setFilingDate(LocalDateTime.now().minusMonths(6));
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("TRADEMARK");
        applicationInfo.setCategory(category);
        entity.setApplicationInfo(applicationInfo);
        entity.setApplicationInfo(applicationInfo);
    }

    @Test
    public void givenInvalidApplication_whenInsert_thenCorrect() {
        when(applicationInfoService.findById(appId)).thenReturn(applicationInfo);
        LKSupportServices k = new LKSupportServices();
        k.setCode(SupportServiceType.VOLUNTARY_REVOKE);
        when(lKSupportServicesService.findByCode(any())).thenReturn(k);
        when(applicationInfoService.validateSupportServicePreConditions(any(), any(), any())).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> revokeVoluntaryRequestService.insert(entity));
    }


    @Test
    public void givenValidApplication_whenInsert_thenCorrect() {
        when(applicationInfoService.findById(appId)).thenReturn(applicationInfo);
        LKSupportServices k = new LKSupportServices();
        k.setCode(SupportServiceType.VOLUNTARY_REVOKE);
        when(lKSupportServicesService.findByCode(any())).thenReturn(k);
        when(applicationInfoService.validateSupportServicePreConditions(any(), any(), any())).thenReturn("PASSED");
        doNothing().when(supportServiceProcess).starSupportServiceProcess(any());
        when(revokeVoluntryRequestRepository.save(any())).thenReturn(entity);
        assertEquals(revokeVoluntryRequestRepository.save(entity), entity);
    }

    @Test
    public void givenInCorrectId_whenUpdate_thenCorrect() {
        when(revokeVoluntryRequestRepository.findById(appId)).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> revokeVoluntaryRequestService.update(entity));
    }

    @Test
    public void givenCorrectId_whenUpdate_thenCorrect() {
        when(revokeVoluntryRequestRepository.findById(appId)).thenReturn(Optional.of(entity));
        when(revokeVoluntryRequestRepository.save(any())).thenReturn(entity);
        when(bpmCallerFeignClient.getTaskByRowIdAndType(any(), any())).thenReturn(ApiResponse.ok(new RequestTasksDto()));
        when(bpmCallerFeignClient.completeUserTask(any(), any())).thenReturn(ApiResponse.ok(""));
        when(lkSupportServiceRequestStatusService.findIdByCode(any())).thenReturn(any());
        assertEquals(revokeVoluntaryRequestService.update(entity), entity);
    }



}
