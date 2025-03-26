package gov.saip.applicationservice.common.service.installment.impl;

import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentConfigRepository;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ApplicationInstallmentJobsTest {

    @Mock
    private ApplicationInstallmentRepository applicationInstallmentRepository;

    @Mock
    private InstallmentNotificationService installmentNotificationService;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private ApplicationInstallmentConfigRepository installmentConfigRepository;

    @InjectMocks
    private ApplicationInstallmentJobs installmentJobs;

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        installmentJobs.setApplicationInfoService(applicationInfoService,installmentJobs);
//    }
//
//    @Test
//    public void testProcessUpcomingInstallmentsForNextXMonths() {
//        ApplicationInstallmentConfig config = new ApplicationInstallmentConfig();
//        config.setPaymentIntervalYears(1);
//        config.setNotificationDuration(3);
//        List<ApplicationInstallment> installmentList = new ArrayList<>();
//        Page<ApplicationInstallment> page = new PageImpl<>(installmentList);
//        when(installmentConfigRepository.findAll()).thenReturn(List.of(config));
//        when(applicationInstallmentRepository.getUpcomingDueInstallments(
//                any(),
//                any(),
//                any(),
//                any(),
//                any()
//        )).thenReturn(page);
//        installmentJobs.processApplicationCategoryInstallment();
//        verify(installmentConfigRepository, times(1)).findAll();
//        verify(installmentConfigRepository, times(1)).updateLastRunningDate(any(),any());
//
//    }



}

