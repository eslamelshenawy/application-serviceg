package gov.saip.applicationservice.common.service.installment.impl;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentConfigRepository;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApplicationInstallmentConfigServiceImplTest {

    @Mock
    private ApplicationInstallmentConfigRepository installmentConfigRepository;

    private ApplicationInstallmentConfigService installmentConfigService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        installmentConfigService = new ApplicationInstallmentConfigServiceImpl(installmentConfigRepository);
    }

    @Test
    public void testFindByApplicationCategory() {
        ApplicationInstallmentConfig expectedConfig = new ApplicationInstallmentConfig();
        expectedConfig.setId(1L);
        expectedConfig.setApplicationCategory(ApplicationCategoryEnum.PATENT);

        when(installmentConfigRepository.findByApplicationCategory(ApplicationCategoryEnum.PATENT))
                .thenReturn(expectedConfig);

        ApplicationInstallmentConfig resultConfig = installmentConfigService.findByApplicationCategory("PATENT");

        assertEquals(expectedConfig, resultConfig);
    }
}

