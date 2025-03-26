package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.facade.ApplicationAgentFacade;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.validators.InventorsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;

import static org.mockito.Mockito.*;

public class PaymentCallBackServiceImplTest {

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private BPMCallerServiceImpl bpmCallerService;

    @InjectMocks
    private PaymentCallBackServiceImpl paymentCallBackService;

    @Mock
    private ApplicationInfoRepository applicationInfoRepository;

    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;


    @Mock
    private ApplicationAgentFacade applicationAgentFacade;

    @Mock
    ExtensionRequestService extensionRequestService;

    @Mock
    InitialModificationRequestService initialModificationRequestService;

    @Mock
    RetractionRequestService retractionRequestService;

    @Mock
    PetitionRecoveryRequestService petitionRecoveryRequestService;

    @Mock
    EvictionRequestService evictionRequestService;

    @Mock
    ApplicationCustomerServiceImpl applicationCustomerService;

    @Mock
    ApplicationAcceleratedService applicationAcceleratedService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    CustomerServiceFeignClient customerServiceFeignClient;

    @Mock
    ActivityLogService activityLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFileNewApplication() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();

        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.FILE_NEW_APPLICATION.name());
        Long id = 1L;

        ApplicationInfo value = new ApplicationInfo();
        value.setId(1L);
        value.setEmail("ahmsalah.elsayed@gmail.com");
        LkApplicationCategory category = new LkApplicationCategory();
        category.setSaipCode("111");
        value.setCategory(category);

        StartProcessResponseDto startProcessResponseDto = new StartProcessResponseDto();
        startProcessResponseDto.setId("id");
        startProcessResponseDto.setBusinessKey(123L);

        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(value);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
        when(bpmCallerService.startApplicationProcess(Mockito.any(ProcessRequestDto.class))).thenReturn(startProcessResponseDto);
        when(applicationCustomerService.getApplicationActiveCustomer(any())).thenReturn(new CustomerSampleInfoDto());
        when(applicationAcceleratedService.checkIfApplicationAccelrated(value.getId())).thenReturn(true);
        when(customerServiceFeignClient.getCustomerCodeByUserId(anyLong()).getPayload()).thenReturn("");
        doNothing().when(activityLogService).insertFileNewApplicationActivityLogStatus(any(), anyLong(), anyString(), any());
        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);

        verify(bpmCallerService, times(1)).startApplicationProcess(any());
    }

    @Test
    void testFormalityUpdate1() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.FORMALITY_UPDATE_1.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());


        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
    }


    @Test
    void testFormalityUpdate2() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.FORMALITY_UPDATE_2.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());


        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
        verify(bpmCallerFeignClient, times(1)).activatePaymentsAccount(Mockito.any());
    }

    @Test
    void testSubsUpdat1() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.SUBS_UPDATE_1.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());


        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
        verify(bpmCallerFeignClient, times(1)).activatePaymentsAccount(Mockito.any());
    }

    @Test
    void testSubsUpdat2() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.SUBS_UPDATE_2.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());


        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
    }

    @Test
    void test_SUBS_EXAM_PAYMENT() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.SUBS_EXAM_PAYMENT.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());


        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
    }


    @Test
    void test_PUBLICATION_PAYMENT() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.PUBLICATION_PAYMENT.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());


        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
    }


    @Test
    void test_WITHDRAW_APPLICATION() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.WITHDRAW_APPLICATION.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
        doNothing().when(retractionRequestService).paymentCallBackHandler(id, applicationNumberGenerationDto);


        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
        verify(retractionRequestService).paymentCallBackHandler(eq(id), eq(applicationNumberGenerationDto));
    }

    @Test
    void test_CHANGE_AGENT_REQUEST() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.CHANGE_AGENT_REQUEST.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
        doNothing().when(applicationAgentFacade).startSupportServiceProcess(id, SupportServicePaymentStatus.PAID, null);

        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);

        verify(applicationAgentFacade, times(1)).startSupportServiceProcess(any(), any(),any());
    }

    @Test
    void test_ADD_AGENT_REQUEST() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.ADD_AGENT_REQUEST.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
//        doNothing().when(applicationAgentFacade).processAddAgentRequest(Mockito.anyLong());
        doNothing().when(applicationAgentFacade).startSupportServiceProcess(id, SupportServicePaymentStatus.PAID, null);

        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);

        verify(applicationAgentFacade, times(1)).startSupportServiceProcess(any(),any(),any());
    }

    @Test
    void test_DELETE_AGENT_REQUEST() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.DELETE_AGENT_REQUEST.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
//        doNothing().when(applicationAgentFacade).processDeleteAgentRequest(Mockito.anyLong());
        doNothing().when(applicationAgentFacade).startSupportServiceProcess(id, SupportServicePaymentStatus.PAID, null);

        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);

        verify(applicationAgentFacade, times(1)).startSupportServiceProcess(any(),any(),any());
    }


    @Test
    void test_PETITION_FOR_RECOVERY() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.PETITION_FOR_RECOVERY.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
        doNothing().when(petitionRecoveryRequestService).paymentCallBackHandler(id, applicationNumberGenerationDto);

        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
        verify(petitionRecoveryRequestService, times(1)).paymentCallBackHandler(id, applicationNumberGenerationDto);

    }


    @Test
    void test_RELINQUISHMENT() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.RELINQUISHMENT.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());

        doNothing().when(evictionRequestService).paymentCallBackHandler(id, applicationNumberGenerationDto);
        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
        verify(evictionRequestService, times(1)).paymentCallBackHandler(Mockito.any(), Mockito.any());
    }

    @Test
    void test_EXTEND_PERIOD_FORMAL_EXAMINATION() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.EXTEND_PERIOD_FORMAL_EXAMINATION.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
    }




    @Test
    void test_INITIAL_UPDATE() {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.INITIAL_UPDATE.name());
        Long id = 1L;

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        when(applicationInfoRepository.getApplicationInfoWithMainApplicant(Mockito.any())).thenReturn(applicationInfo);
        when(applicationInfoService.listMainApplicant(Mockito.any(Long.class))).thenReturn(new ApplicantsDto());
        when(applicationInfoService.listMainApplicant(any())).thenReturn(new ApplicantsDto());
        when(bpmCallerFeignClient.activatePaymentsAccount(Mockito.any())).thenReturn(new ApiResponse<>());
        doNothing().when(initialModificationRequestService).paymentCallBackHandler(id, applicationNumberGenerationDto);

        paymentCallBackService.paymentCallBack(applicationNumberGenerationDto, id);
        verify(initialModificationRequestService, times(1)).paymentCallBackHandler(id, applicationNumberGenerationDto);

    }

}