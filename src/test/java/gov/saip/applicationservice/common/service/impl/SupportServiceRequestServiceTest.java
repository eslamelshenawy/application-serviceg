package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.repository.annualfees.AnnualFeesRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;
import gov.saip.applicationservice.common.service.annualfees.impl.AnnualFeesServiceImpl;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SupportServiceRequestServiceTest {

    @Mock
    public ApplicationInfoService applicationInfoService;

    @Mock
    public LKSupportServicesService lKSupportServicesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
