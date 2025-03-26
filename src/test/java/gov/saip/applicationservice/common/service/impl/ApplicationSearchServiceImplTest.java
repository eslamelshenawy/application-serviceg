package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.ApplicationSearchMapper;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.repository.ApplicationSearchRepository;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.transaction.support.TransactionTemplate;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class ApplicationSearchServiceImplTest {

    @Mock
    private ApplicationSearchRepository applicationSearchRepository;

    @Mock
    private SupportServiceProcess supportServiceProcess;

    @Mock
    private CustomerServiceFeignClient customerServiceFeignClient;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private ApplicationSearchMapper applicationSearchMapper;

    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;

    @Mock
    LKSupportServicesService lKSupportServicesService;
    @InjectMocks
    @Spy
    private ApplicationSearchServiceImpl applicationSearchService;

    @Mock
    LKSupportServiceRequestStatusService lKSupportServiceRequestStatusService;

    @Mock
    TransactionTemplate transactionTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationSearchService.setSupportServiceProcess(supportServiceProcess);
        applicationSearchService.lKSupportServiceRequestStatusService = lKSupportServiceRequestStatusService;
        applicationSearchService.transactionTemplate = transactionTemplate;
//        applicationSearchService

    }

    @Test
    void insertShouldCallSuperInsert() {
        // Arrange
        ApplicationSearch applicationSearch = new ApplicationSearch();
        LKSupportServices lkSupportServices = new LKSupportServices();
        lkSupportServices.setCode(SupportServiceType.APPEAL_REQUEST);
        applicationSearch.setLkSupportServices(lkSupportServices);
        ApplicationSearchDto applicationSearchDto = new ApplicationSearchDto();
        // Act
        doReturn(applicationSearch).when(applicationSearchService).insert(applicationSearch);

        ApplicationSearch result = applicationSearchService.insert(applicationSearch);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void paymentCallBackHandler_Should_Call_StartProcess_When_Id_Provided() {
        Long id = 1L;
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        ApplicationSearch applicationSearch = new ApplicationSearch();
        applicationSearch.setId(id);
        applicationSearch.setCreatedByUser("testUser");
        applicationSearch.setCreatedByCustomerCode("657");
        LKSupportServices lkSupportServices = new LKSupportServices();
        lkSupportServices.setCode(SupportServiceType.APPEAL_REQUEST);
        applicationSearch.setLkSupportServices(lkSupportServices);
        doReturn(applicationSearch).when(applicationSearchService).findById(1l);
       when(customerServiceFeignClient.getAnyCustomerByCustomerCode(anyString()))
                .thenReturn(ApiResponse.ok(new CustomerSampleInfoDto()));
        doNothing().when(applicationSearchService).toStartSupportServiceProcess(any(), any());
        applicationSearchService.paymentCallBackHandler(id, applicationNumberGenerationDto);

        verify(applicationSearchService).applicationSearchStartProcess(id);

    }
        @Test
    void getPaymentRequestStatusShouldReturnCorrectStatus() {
        // Act
        SupportServiceRequestStatusEnum result = applicationSearchService.getPaymentRequestStatus();

        // Assert
        assertEquals(SupportServiceRequestStatusEnum.UNDER_PROCEDURE, result);
    }

    @Test
    void updateShouldUpdateFieldsAndCompleteUserTask() {
        // Arrange
        ApplicationSearch entity = new ApplicationSearch();
        entity.setId(1L);
        entity.setTitle("New Title");

        ApplicationSearch existingEntity = new ApplicationSearch();
        existingEntity.setId(1L);
        existingEntity.setTitle("Old Title");


        doReturn(entity).when(applicationSearchService).update(entity);

        // Act
        ApplicationSearch result = applicationSearchService.update(entity);

        // Assert
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
    }

    @Test
    void completeUserTaskShouldCallFeignClient() {
        // Arrange
        ApplicationSearch applicationSearch = new ApplicationSearch();
        applicationSearch.setId(1L);

        RequestTasksDto requestTasksDto = new RequestTasksDto();
        requestTasksDto.setTaskId("task123");

        when(bpmCallerFeignClient.getTaskByRowIdAndType(any(RequestTypeEnum.class), anyLong())).thenReturn(ApiResponse.ok(requestTasksDto));

        // Act
        applicationSearchService.completeUserTask(applicationSearch);

        // Assert
        verify(bpmCallerFeignClient, times(1)).completeUserTask(eq(requestTasksDto.getTaskId()), any(CompleteTaskRequestDto.class));
    }

    @Test
    void applicationSearchStartProcessShouldCallSupportServiceProcess() {
        // Arrange
        ApplicationSearch applicationSearch = new ApplicationSearch();
        applicationSearch.setId(1L);
        applicationSearch.setCreatedByUser("user123");
        applicationSearch.setCreatedByCustomerCode("66778");
        LKSupportServices lkSupportServices = new LKSupportServices();
        lkSupportServices.setCode(SupportServiceType.APPEAL_REQUEST);
        applicationSearch.setLkSupportServices(lkSupportServices);

        CustomerSampleInfoDto customerSampleInfoDto = new CustomerSampleInfoDto();
        customerSampleInfoDto.setCode("customer123");

        StartProcessDto expectedStartProcessDto = StartProcessDto.builder()
                .id("1")
                .fullNameAr("Full Name AR")
                .fullNameEn("Full Name EN")
                .mobile("1234567890")
                .email("email@example.com")
                .identifier("ID123")
                .applicantUserName("user123")
                .processName("application_search_process")
                .requestTypeCode("APPLICATION_SEARCH")
                .supportServiceCode(SupportServiceType.APPEAL_REQUEST.name())
                .build();
        doReturn(applicationSearch).when(applicationSearchService).findById(1l);

        when(customerServiceFeignClient.getAnyCustomerByCustomerCode(anyString())).thenReturn(ApiResponse.ok(customerSampleInfoDto));

        doNothing().when(applicationSearchService).toStartSupportServiceProcess(any(), any());
        applicationSearchService.applicationSearchStartProcess(1L);
    }
}

