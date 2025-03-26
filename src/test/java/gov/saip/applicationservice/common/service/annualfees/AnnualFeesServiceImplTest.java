package gov.saip.applicationservice.common.service.annualfees;

import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.repository.annualfees.AnnualFeesRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import gov.saip.applicationservice.common.service.annualfees.impl.AnnualFeesServiceImpl;
import gov.saip.applicationservice.common.service.impl.SupportServiceRequestServiceTest;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AnnualFeesServiceImplTest extends SupportServiceRequestServiceTest {

    @InjectMocks
    private AnnualFeesServiceImpl annualFeesService;

    @Mock
    private ApplicationInstallmentService applicationInstallmentService;

    @Mock
    private AnnualFeesRequestRepository annualFeesRequestRepository;

    @Mock
    LKSupportServiceRequestStatusService lKSupportServiceRequestStatusService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        annualFeesService.lKSupportServiceRequestStatusService = lKSupportServiceRequestStatusService;
    }

    @Test
    public void testInsertWithInvalidConditions() {
        Long appId = 1L;
        AnnualFeesRequest entity = new AnnualFeesRequest();
        entity.setId(1L);
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(appId);
        applicationInfo.setFilingDate(LocalDateTime.now().minusMonths(6));
        entity.setApplicationInfo(applicationInfo);
        entity.setApplicationInfo(applicationInfo);

        when(applicationInfoService.findById(appId)).thenReturn(applicationInfo);
        LKSupportServices k= new LKSupportServices();
        k.setCode(SupportServiceType.ANNUAL_FEES_PAY);
        when(lKSupportServicesService.findByCode(any())).thenReturn(k);
        when(applicationInfoService.validateSupportServicePreConditions(any(),any(),any())).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> annualFeesService.insert(entity));

    }

    @Test
    public void testInsertWithSystemError() {
        Long appId = 1L;
        AnnualFeesRequest entity = new AnnualFeesRequest();
        entity.setId(1L);
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(appId);
        applicationInfo.setId(appId);
        applicationInfo.setFilingDate(LocalDateTime.now().minusYears(1));
        entity.setApplicationInfo(applicationInfo);
        when(applicationInfoService.findById(appId)).thenReturn(applicationInfo);
        LKSupportServices k= new LKSupportServices();
        k.setCode(SupportServiceType.ANNUAL_FEES_PAY);
        when(lKSupportServicesService.findByCode(any())).thenReturn(k);
        when(applicationInfoService.validateSupportServicePreConditions(any(),any(), any())).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> annualFeesService.insert(entity));
    }

    @Test
    public void testPaymentCallBackHandler() {
        Long id = 1L;
        AnnualFeesRequest annualFeesRequest = new AnnualFeesRequest();
        annualFeesRequest.setId(id);
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();

        when(annualFeesRequestRepository.findById(id)).thenReturn(Optional.of(annualFeesRequest));

        annualFeesService.paymentCallBackHandler(id, applicationNumberGenerationDto);

        verify(applicationInstallmentService, times(1)).annualFeesPaymentCallBackHandler(annualFeesRequest, applicationNumberGenerationDto);
    }

}

