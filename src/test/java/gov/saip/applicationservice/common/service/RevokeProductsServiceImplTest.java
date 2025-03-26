package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.RevokeProducts;
import gov.saip.applicationservice.common.repository.RevokeProductsRepository;
import gov.saip.applicationservice.common.service.impl.RevokeProductsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.*;

public class RevokeProductsServiceImplTest {

    @Mock
    private RevokeProductsRepository revokeProductsRepository;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @InjectMocks
    @Spy
    private RevokeProductsServiceImpl revokeProductsService;

    @Mock
    LKSupportServicesService lKSupportServicesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsert() {
        RevokeProducts revokeProducts = new RevokeProducts();

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        lkApplicationCategory.setSaipCode("TRADEMARK");
        LkApplicationStatus lkApplicationStatus = new LkApplicationStatus();
        lkApplicationStatus.setCode("ACCEPTANCE");
        applicationInfo.setCategory(lkApplicationCategory);
        applicationInfo.setApplicationStatus(lkApplicationStatus);
        revokeProducts.setApplicationInfo(applicationInfo);
        when(applicationInfoService.findById(anyLong())).thenReturn(applicationInfo);
        when(revokeProductsRepository.save(revokeProducts)).thenReturn(revokeProducts);
        doReturn(revokeProducts).when(revokeProductsService).insert(revokeProducts);
        RevokeProducts result = revokeProductsService.insert(revokeProducts);
        Assertions.assertEquals(applicationInfo.getId(), result.getApplicationInfo().getId());

    }


}

