package gov.saip.applicationservice.common.service.appeal.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.appeal.AppealDetailsDto;
import gov.saip.applicationservice.common.dto.appeal.AppealRequestDto;
import gov.saip.applicationservice.common.enums.appeal.AppealCheckerDecision;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.mapper.appeal.AppealCommitteeOpinionMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import gov.saip.applicationservice.common.repository.appeal.AppealRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.BPMCallerService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppealRequestServiceImplTest {

    @InjectMocks
    @Spy
    private AppealRequestServiceImpl appealRequestService;

    @Mock
    private AppealRequestRepository appealRequestRepository;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private BPMCallerServiceImpl bpmCallerService;

    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;

    @Mock
    private DocumentsService documentsService;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private AppealCommitteeOpinionMapper appealCommitteeOpinionMapper;

    @Mock
    private ApplicationAgentService applicationAgentService;

    @Mock
    private BaseRepository<AppealRequest, Long> baseRepository;

    @Mock
    LKSupportServiceRequestStatusService lkSupportServiceRequestStatusService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        appealRequestService.lKSupportServiceRequestStatusService = lkSupportServiceRequestStatusService;
        when(lkSupportServiceRequestStatusService.findByCode(anyString())).thenReturn(new LKSupportServiceRequestStatus());
    }

    @Test
    public void testInsertWithInvalidDueDate() {
        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        lkApplicationCategory.setSaipCode("sayed ya sayed");
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        applicationInfo.setCategory(lkApplicationCategory);
        AppealRequest appealRequest = new AppealRequest();
        appealRequest.setApplicationInfo(applicationInfo);
        RequestTasksDto requestTasksDto = new RequestTasksDto();
        requestTasksDto.setDue(LocalDateTime.now().minusDays(10).toString());
        requestTasksDto.setTaskDefinitionKey("test");
        ApiResponse apiResponse = ApiResponse.ok(requestTasksDto);


        when(applicationInfoService.findById(anyLong())).thenReturn(applicationInfo);
        when(bpmCallerService.getTaskByRowIdAndType(any(), anyLong())).thenReturn(apiResponse);
        BusinessException exception = assertThrows(BusinessException.class, () -> appealRequestService.insert(appealRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    public void testAddCheckerDecision() {
        AppealRequestDto dto = new AppealRequestDto();
        dto.setId(1L);
        dto.setCheckerDecision(AppealCheckerDecision.REJECTED);

        Long result = appealRequestService.addCheckerDecision(dto);

        assertEquals(dto.getId(), result);
    }

    @Test
    public void testAddOfficialLetter() {
        AppealRequestDto dto = new AppealRequestDto();
        dto.setId(1L);

        Long result = appealRequestService.addOfficialLetter(dto);

        assertEquals(dto.getId(), result);
    }

    @Test
    public void testAddAppealCommitteeDecision() {
        AppealRequestDto dto = new AppealRequestDto();
        dto.setId(1L);
        dto.setAppealCommitteeDecision(AppealCommitteeDecision.ACCEPTED);

        Long result = appealRequestService.addAppealCommitteeDecision(dto);

        assertEquals(dto.getId(), result);
    }

    @Test
    public void testGetTradeMarkAppealDetails() {
        Long appealId = 1l;
        LkApplicationStatus lkApplicationStatuskApplicationStatus = new LkApplicationStatus();
        lkApplicationStatuskApplicationStatus.setCode("sayed ya sayed");
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        applicationInfo.setApplicationStatus(lkApplicationStatuskApplicationStatus);
        AppealRequest appealRequest = new AppealRequest();
        appealRequest.setApplicationInfo(applicationInfo);
        appealRequest.setId(appealId);
        when(appealRequestRepository.findById(appealId)).thenReturn(Optional.of(appealRequest));
        when(applicationInfoService.findById(anyLong())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(anyLong())).thenReturn(new ApplicantsDto());

        AppealDetailsDto result = appealRequestService.getTradeMarkAppealDetails(appealId);

        assertNotNull(result);
    }

    @Test
    public void testUpdateAppealRequest() {
        AppealRequestDto appealRequest = new AppealRequestDto();
        appealRequest.setId(1L);

        doReturn(appealRequest).when(appealRequestService).findById(anyLong());

        Long result = appealRequestService.updateAppealRequest(appealRequest, "taskId");

        assertEquals(appealRequest.getId(), result);
    }
}

