package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderInternalDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.RevokeByCourtOrderMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.RevokeByCourtOrder;
import gov.saip.applicationservice.common.repository.RevokeByCourtOrderRepository;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.common.service.impl.RevokeByCourtOrderServiceImpl;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceImpl;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceTest;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_BY_COURT_ORDER;
import static gov.saip.applicationservice.util.Constants.AppRequestHeaders.CUSTOMER_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RevokeByCourtOrderServiceImplTest extends SupportServiceRequestServiceTest {

    @Mock
    private RevokeByCourtOrderRepository revokeByCourtOrderRepository;
    @Mock
    private SupportServiceProcess supportServiceProcess;

    @Mock
    private SupportServiceRequestServiceImpl supportServiceRequestService;
    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;

    @InjectMocks
    @Spy
    private RevokeByCourtOrderServiceImpl revokeByCourtOrderService;
    Long appId;
    RevokeByCourtOrder entity;
    ApplicationInfo applicationInfo;

    @Mock
    LKSupportServiceRequestStatusService lKSupportServiceRequestStatusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public RevokeByCourtOrderServiceImplTest() {
        appId = 1L;
        entity = new RevokeByCourtOrder();
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
        k.setCode(REVOKE_BY_COURT_ORDER);
        when(lKSupportServicesService.findByCode(any())).thenReturn(k);
        when(applicationInfoService.validateSupportServicePreConditions(any(), any(),any())).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> revokeByCourtOrderService.insert(entity));
    }

    @Test
    public void givenValidApplication_whenInsert_thenCorrect() {

        RevokeByCourtOrder revokeByCourtOrder = new RevokeByCourtOrder();
        revokeByCourtOrder.setPaymentStatus(SupportServicePaymentStatus.FREE);
        doReturn(revokeByCourtOrder).when(revokeByCourtOrderService).getRevokeByCourtOrder(Mockito.any(RevokeByCourtOrder.class));
        doNothing().when(revokeByCourtOrderService).startRevokeByCourtOrderProcess(any());
        // act
        RevokeByCourtOrder result = revokeByCourtOrderService.insert(revokeByCourtOrder);
        // assert
        assertNotNull(result);
        assertEquals(result.getPaymentStatus(), revokeByCourtOrder.getPaymentStatus());
        assertSame(result,revokeByCourtOrder);
        verify(revokeByCourtOrderService, times(1)).startRevokeByCourtOrderProcess(any());

    }

    @Test
    public void givenInCorrectId_whenUpdate_thenCorrect() {
        when(revokeByCourtOrderRepository.findById(appId)).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> revokeByCourtOrderService.update(entity));
    }

    @Test
    public void givenCorrectId_whenUpdate_thenCorrect() {
        when(revokeByCourtOrderRepository.findById(appId)).thenReturn(Optional.of(entity));
        when(revokeByCourtOrderRepository.save(any())).thenReturn(entity);
        when(bpmCallerFeignClient.getTaskByRowIdAndType(any(), any())).thenReturn(ApiResponse.ok(new RequestTasksDto()));
        when(bpmCallerFeignClient.completeUserTask(any(), any())).thenReturn(ApiResponse.ok(""));
        when(lKSupportServiceRequestStatusService.findIdByCode(any())).thenReturn(any());
        assertEquals(revokeByCourtOrderService.update(entity), entity);
    }

    @Test
    public void givenInCorrectId_whenUpdateRevokeByCourtOrderRequestWithInternalData_thenCorrect() {
        RevokeByCourtOrderInternalDto revokeByCourtOrderInternalDto = new RevokeByCourtOrderInternalDto();
        revokeByCourtOrderInternalDto.setId(appId);
        when(revokeByCourtOrderRepository.findById(appId)).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> revokeByCourtOrderService.updateRevokeByCourtOrderRequestWithInternalData(revokeByCourtOrderInternalDto));
    }
}
