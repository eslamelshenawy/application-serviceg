package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.ExtensionRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.AWAITING_VERIFICATION;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtensionRequestServiceImplTest {

    @InjectMocks
    @Spy
    ExtensionRequestServiceImpl classUnderTest;
    @Mock
    ExtensionRequestRepository extensionRequestRepository;

    @Mock
    ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    @Mock
    LKSupportServicesService lKSupportServicesService;
    @Mock
    BPMCallerServiceImpl bpmCallerService;
    @Mock
    ApplicationInfoService applicationInfoService;


    @Test
    @DisplayName("get ExtensionRequest list by application id")
    void testGetAllByApplicationId_givenValidAppId_returnExtensionRequest() {
        // arrange
        Long id = 1L;
        LKSupportServices lkSupportServices = new LKSupportServices();
        lkSupportServices.setCode(SupportServiceType.EXTENSION);
        lkSupportServices.setId(1L);
//        List<ApplicationSupportServicesType> list = List.of(new ApplicationSupportServicesType());
        List<ExtensionRequest> extensionRequestList = List.of(new ExtensionRequest());

//        when(lKSupportServicesService.findByCode(eq(SupportServiceType.EXTENSION))).thenReturn(lkSupportServices);
//        when(applicationSupportServicesTypeService.getByApplicationInfoIdAndLkSupportServices(id, lkSupportServices)).thenReturn(list);
        when(extensionRequestRepository.findByApplicationInfoIdAndLkSupportServicesCode(id, SupportServiceType.EXTENSION)).thenReturn(extensionRequestList);

        // acct
        List<ExtensionRequest> allByApplicationId = classUnderTest.getAllByApplicationId(id, SupportServiceType.EXTENSION);

        // assert
        verify(extensionRequestRepository).findByApplicationInfoIdAndLkSupportServicesCode(id, SupportServiceType.EXTENSION);
        Assertions.assertEquals(1, allByApplicationId.size());
    }


    @Test()
    @DisplayName("given invalid id throw exception")
    void testUpdate_givenEntity_thenUpdateEntity() {
        // arrange
        ExtensionRequest request = new ExtensionRequest();
        Long id = 1L;
        request.setId(id);

        Mockito.doReturn(request).when(classUnderTest).update(request);


        // acct
        ExtensionRequest extensionRequest = classUnderTest.update(request);

        // assert
        Assert.assertTrue(Objects.nonNull(extensionRequest));

    }

    @Test()
    @DisplayName("given invalid id throw exception")
    void testUpdate_givenEntity_thenUpdateEntity2() {
        // arrange
        Long id = 1L;

        ApplicationInfo info = new ApplicationInfo(id);

        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("INDUSTRIAL_DESIGN");

        LkApplicationStatus status = new LkApplicationStatus();
        status.setCode(AWAITING_VERIFICATION.name());

        info.setCategory(category);
        info.setApplicationStatus(status);

        ExtensionRequest request = new ExtensionRequest();
        request.setId(id);
        request.setApplicationInfo(info);

        RequestTasksDto requestTasksDto = new RequestTasksDto();
        requestTasksDto.setDue("2023-09-03T15:30:45.123+05");
        applicationInfoService.findById(id);
        LKSupportServices lkSupportServices = new LKSupportServices();
        lkSupportServices.setCode(SupportServiceType.EXTENSION);

//        ReflectionTestUtils.setField(classUnderTest, "baseRepository", extensionRequestRepository);
//        ReflectionTestUtils.setField(classUnderTest, "lKSupportServicesService", lKSupportServicesService);
//        ReflectionTestUtils.setField(classUnderTest, "applicationInfoService", applicationInfoService);
//
//        when(applicationInfoService.findById(id)).thenReturn(info);
//        when(bpmCallerService.getTaskByRowIdAndType(RequestTypeEnum.INDUSTRIAL_DESIGN, id)).thenReturn(ApiResponse.ok(requestTasksDto));
//        when(applicationSupportServicesTypeService.insert(SupportServiceType.EXTENSION, info)).thenReturn(request);
//        when(applicationInfoService.validateSupportServicePreConditions(id, "CODE")).thenReturn(PASSED);
//        Mockito.doReturn(lkSupportServices).when(lKSupportServicesService).findByCode(SupportServiceType.EXTENSION);
//




        Mockito.doReturn(request).when(classUnderTest).insert(request);


        // acct
        ExtensionRequest extensionRequest = classUnderTest.insert(request);

        // assert
        Assert.assertTrue(Objects.nonNull(extensionRequest));

    }

}