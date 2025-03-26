package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LkFeeCostDto;
import gov.saip.applicationservice.common.dto.trademark.TradeMarkLightDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.ApplicationClassificationMapper;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.mapper.ApplicationRelvantMapper;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.*;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationServiceRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import gov.saip.applicationservice.common.service.patent.ApplicationCertificateDocumentService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.common.validators.CustomerCodeValidator;
import gov.saip.applicationservice.config.Properties;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.TRADEMARK;
import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_SECONDARY;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.UNDER_OBJECTIVE_PROCESS;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.WAIVED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ApplicationServiceImplTest {

    @Mock
    private ApplicationNiceClassificationRepository applicationNiceClassificationRepository;

    @InjectMocks
    @Spy
    ApplicationServiceImpl applicationServiceImpl;

    @Mock
    CustomerServiceFeignClient customerServiceFeignClient;
    @Mock
    private PaymentCallBackService paymentService;
    @Mock
    private ApplicationInfoRepository applicationInfoRepository;

    @Mock
    private ApplicationInstallmentService applicationInstallmentService;
    @Mock
    private ClassificationMapper classificationMapper;
    @Mock
    private PaymentFeeCostFeignClient paymentFeeCostFeignClient;
    @Mock
    private ApplicationRelevantRepository applicationRelevantRepository;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    @Mock
    private ApplicationInfoMapper requestMapper;
    @Mock
    private DocumentsService documentsService;
    @Mock
    private TrademarkDetailService trademarkDetailService;
    @Mock
    private ApplicationAgentService applicationAgentService;
    @Mock
    private ApplicationClassificationMapper applicationClassificationMapper;
    @Mock
    private ApplicationRelvantMapper applicationRelvantMapper;
    @Mock
    private LkApplicationCategoryRepository lkApplicationCategoryRepository;
    @Mock
    private CustomerServiceCaller customerServiceCaller;
    @Mock
    private BPMCallerFeignClient bpmCallerFeignClient;

    @Mock
    private ApplicationPriorityService applicationPriorityService;
    @Mock
    private LkApplicationServiceRepository applicationServiceRepository;
    @Mock
    private Properties properties;

    @Mock
    private CustomerCodeValidator customerCodeValidator;
    @Mock
    private BPMCallerServiceImpl bpmCallerService;
    @Mock
    private ApplicationNotesService applicationNotesService;
    @Mock
    private LkApplicationStatusService applicationStatusService;

    @Mock
    private ApplicationSupportServicesTypeService applicationSupportServicesTypeService;

    @Mock
    ApplicationNiceClassificationService applicationNiceClassificationService;
    @Mock
    InitialModificationRequestService initialModificationRequestService;

    @Mock
    ClassificationService classificationService;

    @Mock
    ApplicationCustomerService applicationCustomerService;

    @Mock
    ApplicationCheckingReportService reportService;

    @Mock
    ApplicationCertificateDocumentService applicationCertificateDocumentService;

    @Mock
    ApplicationAgentFacadeService applicationAgentFacadeService;

    @Mock
    ApplicationPublicationService applicationPublicationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getHeader("X-Customer-Id")).thenReturn("456722");
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));
        applicationServiceImpl.setApplicationNiceClassificationService(applicationNiceClassificationService);
//        applicationServiceImpl.setInitialModificationRequestService(initialModificationRequestService);
        ApplicationListDto listDto = mock(ApplicationListDto.class);
        listDto.setCustomerCode("test");
        applicationServiceImpl.setInitialModificationRequestService(initialModificationRequestService);
        applicationServiceImpl.setClassificationMapper(classificationMapper);
        applicationServiceImpl.setApplicationCustomerService(applicationCustomerService);
        applicationServiceImpl.setApplicationCertificateDocumentService(applicationCertificateDocumentService);
        applicationServiceImpl.setApplicationAgentFacadeService(applicationAgentFacadeService);
        applicationServiceImpl.setApplicationPublicationService(applicationPublicationService);
    }


    @Test
    public void testGetApplicationClassificationUnitIdsByAppId() {
        Long appId = 1L;

        List<Long> expectedUnitIds = Arrays.asList(2L, 3L, 4L);
        when(applicationInfoRepository.getApplicationClassificationUnitIdsByAppId(anyLong())).thenReturn(expectedUnitIds);

        List<Long> actualUnitIds = applicationServiceImpl.getApplicationClassificationUnitIdsByAppId(appId);

        assertEquals(expectedUnitIds, actualUnitIds);
    }

    @Test
    public void testGetApplicationClassificationUnitIdsByAppIdEmptyResult() {
        Long appId = 2L;
        List<Long> expectedUnitIds = new ArrayList<>();
        when(applicationInfoRepository.getApplicationClassificationUnitIdsByAppId(anyLong())).thenReturn(expectedUnitIds);
        List<Long> actualUnitIds = applicationServiceImpl.getApplicationClassificationUnitIdsByAppId(appId);
        assertEquals(expectedUnitIds, actualUnitIds);
    }

    @Test
    public void testGetApplicationStatusByApplicationIds() {
        List<Long> applicationIds = Arrays.asList(1L, 2L, 3L);
        List<ApplicationInfo> expectedApplicationInfo = new ArrayList<>();
        when(applicationInfoRepository.getApplicationStatusByApplicationIds(anyList())).thenReturn(expectedApplicationInfo);
        List<ApplicationStatusDto> expectedApplicationStatusDtos = new ArrayList<>();
        when(requestMapper.mapStatus(expectedApplicationInfo)).thenReturn(expectedApplicationStatusDtos);
        Map<Long, ApplicationInfoTaskDto> actualApplicationStatusDtos = applicationServiceImpl.getApplicationStatusByApplicationIds(applicationIds);
        assertTrue(actualApplicationStatusDtos.isEmpty());
    }

    @Test
    public void testChangeApplicationStatusId() {
        Long applicationId = 1L;
        ApplicationInfo application = new ApplicationInfo();
        application.setId(applicationId);
        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        lkApplicationCategory.setId(1L);
        lkApplicationCategory.setSaipCode("TRADEMARK");
        application.setCategory(lkApplicationCategory);
        String status = "APPROVED";
        Long categoryId = 1L;
        LkApplicationStatus expectedApplicationStatus = new LkApplicationStatus();
        when(applicationStatusService.findByCodeAndApplicationCategory(status , categoryId)).thenReturn(expectedApplicationStatus);
        ApplicationInfo expectedApplicationInfo = new ApplicationInfo();
        when(applicationInfoRepository.findById(anyLong())).thenReturn(Optional.of(expectedApplicationInfo));
        when(applicationInfoRepository.findById(anyLong())).thenReturn(Optional.of(application));
        doReturn(expectedApplicationInfo).when(applicationServiceImpl).findById(applicationId);
        doReturn(expectedApplicationInfo).when(applicationServiceImpl).update(expectedApplicationInfo);
        applicationServiceImpl.changeApplicationStatusId(applicationId, status);
        assertTrue(true);
    }


    @Test
    public void testGetSupportServiceCost() {
        String code = "SUPPORT_SERVICE_1";
        String applicantCategorySaipCode = "CATEGORY_1";
        String applicationCategorySaipCode = "APPLICATION_CATEGORY_1";
        List<LkFeeCostDto> expectedLkFeeCosts = new ArrayList<>();
        LkFeeCostDto expectedLkFeeCost = new LkFeeCostDto();
        expectedLkFeeCost.setCost(Double.valueOf(100));
        expectedLkFeeCosts.add(expectedLkFeeCost);
        when(paymentFeeCostFeignClient.findCost(anyString(), anyString(), anyString())).thenReturn(expectedLkFeeCosts);

        Double actualCost = applicationServiceImpl.getSupportServiceCost(code, applicantCategorySaipCode, applicationCategorySaipCode);

        assertEquals(Double.valueOf(100), actualCost);
    }

    @Test
    public void testGetSupportServiceCost_emptyResult() {
        String code = "SUPPORT_SERVICE_2";
        String applicantCategorySaipCode = "CATEGORY_2";
        String applicationCategorySaipCode = "APPLICATION_CATEGORY_2";

        List<LkFeeCostDto> expectedLkFeeCosts = new ArrayList<>();
        when(paymentFeeCostFeignClient.findCost(anyString(), anyString(), anyString())).thenReturn(expectedLkFeeCosts);

        Double actualCost = applicationServiceImpl.getSupportServiceCost(code, applicantCategorySaipCode, applicationCategorySaipCode);

        assertEquals(Double.valueOf(0), actualCost);
    }

    @Test
    public void testGetApplicationSummary() {
        Long applicationId = 1L;

        ApplicationInfoDto applicationInfoDto = new ApplicationInfoDto();
        applicationInfoDto.setId(1l);
        LKApplicationCategoryDto lKApplicationCategoryDto = new LKApplicationCategoryDto();
        lKApplicationCategoryDto.setSaipCode("");
        applicationInfoDto.setCategory(lKApplicationCategoryDto);
        doReturn(applicationInfoDto).when(applicationServiceImpl).getApplication(applicationId);
        ApplicationInfoSummaryDto applicationInfoSummaryDto = new ApplicationInfoSummaryDto();
        applicationInfoSummaryDto.setId(1l);
        ApplicationStatusDto applicationStatusDto = new ApplicationStatusDto();
        applicationStatusDto.setCode(WAIVED.name());
        applicationInfoSummaryDto.setApplicationStatus(applicationStatusDto);
        doReturn(applicationInfoSummaryDto).when(requestMapper).mapApplicationTSummary(applicationInfoDto);
        List<ApplicantsDto> applicants = new ArrayList<>();
        doReturn(applicants).when(applicationServiceImpl).listApplicants(applicationId);
//        doReturn(null).when(applicationAgentFacadeService).getApplicationCurrentAgentSummary(applicationId);
        doReturn(new DocumentDto()).when(documentsService).findDocumentByApplicationIdAndDocumentType(anyLong(), anyString());
        doReturn(new ApplicationAgentSummaryDto()).when(applicationAgentService).getCurrentApplicationAgentSummary(applicationId);
        doReturn(SupportedServiceCode.APPEAL_REQUEST).when(initialModificationRequestService).getApplicationSupportedServiceType(applicationId);
        doReturn(null).when(applicationAgentFacadeService).getApplicationCurrentAgentSummary(anyLong());
        when(applicationAgentFacadeService.getApplicationCurrentAgentSummary(anyLong())).thenReturn(null);
        Object actualResult = applicationServiceImpl.getApplicationSummary(applicationId, null);

        assertNotNull(actualResult);
        assertEquals(ApplicationInfoSummaryDto.class, actualResult.getClass());

    }

    @Test
    public void testGetApplicationSummarySetApplicants() {
        Long applicationId = 1L;

        ApplicationInfoDto applicationInfoDto = new ApplicationInfoDto();
        applicationInfoDto.setId(1L);
        LKApplicationCategoryDto lKApplicationCategoryDto = new LKApplicationCategoryDto();
        lKApplicationCategoryDto.setSaipCode(TRADEMARK.name());
        applicationInfoDto.setCategory(lKApplicationCategoryDto);
        doReturn(applicationInfoDto).when(applicationServiceImpl).getApplication(applicationId);
        ApplicationInfoSummaryDto applicationInfoSummaryDto = new ApplicationInfoSummaryDto();
        applicationInfoSummaryDto.setId(1L);
        applicationInfoSummaryDto.setApplicants(Arrays.asList(new ApplicantsDto(), new ApplicantsDto()));
        ApplicationStatusDto applicationStatusDto = new ApplicationStatusDto();
        applicationStatusDto.setCode("test");
        applicationInfoSummaryDto.setApplicationStatus(applicationStatusDto);
        doReturn(applicationInfoSummaryDto).when(requestMapper).mapApplicationTSummary(applicationInfoDto);
        List<ApplicantsDto> applicants = new ArrayList<>();
        doReturn(applicants).when(applicationServiceImpl).listApplicants(applicationId);
        doReturn(new DocumentDto()).when(documentsService).findDocumentByApplicationIdAndDocumentType(anyLong(), anyString());
        doReturn(new ApplicationAgentSummaryDto()).when(applicationAgentService).getCurrentApplicationAgentSummary(applicationId);
        doReturn(new TradeMarkLightDto()).when(trademarkDetailService).getTradeMarkLightDetails(applicationId);
        doReturn(null).when(classificationService).listApplicationClassification(applicationId);
//        doReturn(null).when(applicationAgentFacadeService).getApplicationCurrentAgentSummary(anyLong());
        when(applicationAgentFacadeService.getApplicationCurrentAgentSummary(anyLong())).thenReturn(null);
        when(applicationPublicationService.getPublicationSummary(anyLong(), anyString())).thenReturn(null);
        Object actualResult = applicationServiceImpl.getApplicationSummary(applicationId, null);

        assertNotNull(actualResult);
        assertEquals(ApplicationInfoSummaryDto.class, actualResult.getClass());

    }

    @Test
    public void testGetApplicationSummaryException() {
        Long applicationId = 1L;
        doReturn(null).when(applicationServiceImpl).getApplication(applicationId);
        assertThrows(BusinessException.class, ()->{applicationServiceImpl.getApplicationSummary(applicationId, null);});
    }

    @Test
    public void testGetUserRequestTypes() {
        ApiResponse expectedApiResponse = new ApiResponse();
        when(bpmCallerFeignClient.getUserRequestTypes()).thenReturn(expectedApiResponse);
        ApiResponse actualApiResponse = applicationServiceImpl.getUserRequestTypes();
        verify(bpmCallerFeignClient).getUserRequestTypes();
        assertEquals(expectedApiResponse, actualApiResponse);
    }

    @Test
    public void testGetApplicationByApplicationPartialNumber() {
        List<PartialApplicationInfoProjection> partialApplicationInfoProjections = new ArrayList<>();
        when(applicationInfoRepository.getApplicationByApplicationPartialNumber("123")).thenReturn(partialApplicationInfoProjections);
        List<PartialApplicationInfoDto> actualPartialApplicationInfoDtos = applicationServiceImpl.getApplicationByApplicationPartialNumber("123");
        verify(applicationInfoRepository).getApplicationByApplicationPartialNumber("123");
        assertNull(actualPartialApplicationInfoDtos);
    }

    @Test
    public void testGetApplicationByApplicationPartialNumberValue() {
        List<PartialApplicationInfoProjection> partialApplicationInfoProjections = new ArrayList<>();
        partialApplicationInfoProjections.add(getProjection());
        when(applicationInfoRepository.getApplicationByApplicationPartialNumber("123")).thenReturn(partialApplicationInfoProjections);
        List<PartialApplicationInfoDto> actualPartialApplicationInfoDtos = applicationServiceImpl.getApplicationByApplicationPartialNumber("123");
        assertNotNull(actualPartialApplicationInfoDtos);
    }



    @Test
    public void testGetApplicationSubstantiveExamination() {
        Long id = 1l;
        Optional<ApplicationInfo> applicationPriority = Optional.of(new ApplicationInfo());
        doReturn(applicationPriority).when(applicationInfoRepository).findById(id);
        doReturn(new ApplicationSubstantiveExaminationRetrieveDto()).when(requestMapper).mapEntityToSubstantiveExaminationRequest(applicationPriority.get());
        doReturn(new ArrayList<>()).when(applicationServiceImpl).listApplicants(id);

        ApplicationSubstantiveExaminationRetrieveDto result = applicationServiceImpl.getApplicationSubstantiveExamination(id);
        assertNotNull(result);
    }

    @Test
    public void testGetApplicationSubstantiveExaminationNullException() {
        Long id = null;
        assertThrows(BusinessException.class, ()->{applicationServiceImpl.getApplicationSubstantiveExamination(id);});
    }

    @Test
    public void testGetApplicationSubstantiveExaminationNotPresentException() {
        Long id = 1l;
        doReturn(Optional.empty()).when(applicationInfoRepository).findById(id);
        assertThrows(BusinessException.class, ()->{applicationServiceImpl.getApplicationSubstantiveExamination(id);});
    }

    @Test
    public void testUpdateExamination() {
        ApplicationSubstantiveExaminationDto dto = new ApplicationSubstantiveExaminationDto();
        dto.setApplicationNotes(Arrays.asList(new ApplicationNotesReqDto(), new ApplicationNotesReqDto()));
        ApplicationInfo entity = new ApplicationInfo();
        ApplicationInfo examination = new ApplicationInfo();
        doReturn(examination).when(requestMapper).mapToExamination(entity, dto);
        doReturn(entity).when(applicationServiceImpl).getReferenceById(1L);
        doReturn(examination).when(applicationServiceImpl).update(examination);
        doReturn(1l).when(applicationNotesService).updateAppNote(any(ApplicationNotesReqDto.class));
        applicationServiceImpl.updateExamination(dto, 1L);
        assertTrue(true);
    }

    @Test
    public void testUpdateApplicationClassification() {
        Long appId = 1L;
        ApplicationClassificationDto applicationClassificationDto = new ApplicationClassificationDto();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(appId);
        LkApplicationCategory applicationCategory = new LkApplicationCategory();
        applicationCategory.setSaipCode("");
        applicationInfo.setCategory(applicationCategory);
        Optional<ApplicationInfo> entity = Optional.of(applicationInfo);
        doReturn(entity).when(applicationInfoRepository).findById(applicationClassificationDto.getId());
        doReturn(entity.get()).when(applicationClassificationMapper).unMap(applicationClassificationDto);
        doReturn(entity.get()).when(applicationInfoRepository).save(entity.get());
        doReturn(new ApplicationClassificationDto()).when(applicationClassificationMapper).map(entity.get());
        ApplicationClassificationDto result = applicationServiceImpl.updateApplicationClassification(applicationClassificationDto);
        assertNotNull(result);
    }

    @Test
    public void testSubmitApplicationInfoPayment() {
        Long id = 1l;
        ApplicationInfoPaymentDto applicationInfoPaymentDto = new ApplicationInfoPaymentDto();
        applicationInfoPaymentDto.setShapesNumber(1);
        ApplicationInfo applicationInfo1 = new ApplicationInfo();
        applicationInfo1.setId(1l);
        Optional<ApplicationInfo> applicationInfo = Optional.of(applicationInfo1);
        doReturn(applicationInfo).when(applicationInfoRepository).findById(id);
        doReturn(applicationInfo.get()).when(applicationInfoRepository).save(applicationInfo.get());
        Long result = applicationServiceImpl.submitApplicationInfoPayment(id, applicationInfoPaymentDto);
        assertEquals(1l, result);
    }

    @Test
    public void testGetApplicationClassificationByIdSuccess() {
        Long id = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(id);
        ApplicationClassificationDto expected = new ApplicationClassificationDto();
        expected.setId(id);
        when(applicationInfoRepository.findById(id)).thenReturn(Optional.of(applicationInfo));
        when(applicationClassificationMapper.map(applicationInfo)).thenReturn(expected);
        ApplicationClassificationDto result = applicationServiceImpl.getApplicationClassificationById(id);
        assertEquals(expected, result);
        verify(applicationInfoRepository, times(1)).findById(id);
        verify(applicationClassificationMapper, times(1)).map(applicationInfo);
    }

    @Test
    public void testGetApplicationClassificationByIdApplicationNotFound() {
        Long id = 1L;
        when(applicationInfoRepository.findById(id)).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            applicationServiceImpl.getApplicationClassificationById(id);
        });
    }

    @Test
    public void testGetApplicationPaymentSuccess() {
        Long applicationId = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(applicationId);
        applicationInfo.setNormalPagesNumber(10);
        applicationInfo.setClaimPagesNumber(5);
        applicationInfo.setShapesNumber(3);
        applicationInfo.setTotalCheckingFee(100l);
        doReturn(applicationInfo).when(applicationServiceImpl).findById(applicationId);
        ApplicationInfoPaymentDto expected = new ApplicationInfoPaymentDto();
        expected.setNormalPagesNumber(10);
        expected.setClaimPagesNumber(5);
        expected.setShapesNumber(3);
        expected.setTotalCheckingFee(100l);
        ApplicationInfoPaymentDto result = applicationServiceImpl.getApplicationPayment(applicationId);
        assertEquals(expected.getClaimPagesNumber(), result.getClaimPagesNumber());
    }

    @Test
    public void testGetApplicationPaymentApplicationNotFound() {
        Long applicationId = 1L;
        when(applicationInfoRepository.findById(applicationId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            applicationServiceImpl.getApplicationPayment(applicationId);
        });
    }

    @Test
    public void testGetAllApplicationsByUserIdAndAgentIdSuccess() {
        Long agentId = 1L;
        String customerCode = "code001";
        List<ApplicationInfo> expected = Arrays.asList(new ApplicationInfo(), new ApplicationInfo(), new ApplicationInfo());
        when(applicationInfoRepository.getAllApplicationsByUserIdAndAgentId(agentId, customerCode, ApplicationAgentStatus.ACTIVE)).thenReturn(expected);
        List<ApplicationInfo> result = applicationServiceImpl.getAllApplicationsByCustomerCodeAndAgentId(customerCode, agentId);
        assertEquals(expected, result);
    }

    @Test
    public void testGetAssignedToAgentApplicationsSuccess() {
        Long categoryId = 1L;
        String applicationNumber = "12345";
        String appStatus = "ACTIVE";
        String customerCode = "code001";
        Integer page = 1;
        Integer limit = 10;
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        List<ApplicationInfo> appInfo = Arrays.asList(
                new ApplicationInfo(),
                new ApplicationInfo(),
                new ApplicationInfo()
        );
        Page<ApplicationInfo> applicationInfoPages = new PageImpl<>(appInfo, pageable, appInfo.size());
        List<ApplicationInfoListDto> appListDto = Arrays.asList(
                new ApplicationInfoListDto(),
                new ApplicationInfoListDto(),
                new ApplicationInfoListDto()
        );


        doReturn(applicationInfoPages).when(applicationInfoRepository).getAssignedToAgentApplications(anyLong(), anyString(), anyString(),
                anyList(),  anyString(), anyList(), anyLong(), any(Pageable.class));

        when(requestMapper.mapAppInfoToAppInfoListDto(appInfo)).thenReturn(appListDto);
        PaginationDto expected = PaginationDto.builder()
                .totalElements(applicationInfoPages.getTotalElements())
                .content(appListDto)
                .totalPages(applicationInfoPages.getTotalPages())
                .build();
        PaginationDto result = applicationServiceImpl.getAssignedToAgentApplications(categoryId, applicationNumber, appStatus, customerCode, page, limit);
        assertEquals(expected, result);
        verify(requestMapper, times(1)).mapAppInfoToAppInfoListDto(appInfo);
    }

    @Test
    public void testGetNotAssignedToAgentApplicationsSuccess() {
        String customerCode = "code001";
        String applicationNumber = "SA12345";
        String appStatus = "ACTIVE";
        Long categoryId = 2L;
        Integer page = 1;
        Integer limit = 10;
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        List<ApplicationInfo> appInfo = Arrays.asList(
                new ApplicationInfo(),
                new ApplicationInfo(),
                new ApplicationInfo()
        );
        Page<ApplicationInfo> applicationInfoPages = new PageImpl<>(appInfo, pageable, appInfo.size());
        List<ApplicationInfoListDto> appListDto = Arrays.asList(
                new ApplicationInfoListDto(),
                new ApplicationInfoListDto(),
                new ApplicationInfoListDto()
        );

        doReturn(applicationInfoPages).when(applicationInfoRepository).getNotAssignedToAgentApplications(anyLong(), anyString(), anyString(), anyList(), anyString(), anyList(), anyLong(),any(Pageable.class));

        when(requestMapper.mapAppInfoToAppInfoListDto(appInfo)).thenReturn(appListDto);

        PaginationDto expected = PaginationDto.builder()
                .totalElements(applicationInfoPages.getTotalElements())
                .content(appListDto)
                .totalPages(applicationInfoPages.getTotalPages())
                .build();

        PaginationDto result = applicationServiceImpl.getNotAssignedToAgentApplications(categoryId, applicationNumber, appStatus, customerCode, page, limit);
        assertEquals(expected, result);
        verify(requestMapper, times(1)).mapAppInfoToAppInfoListDto(appInfo);
    }

    @Test
    public void testThrowGetNotAssignedToAgentApplicationsAPPLICATION_NUMBER_NOT_FOUND_FOR_THIS_CATEGORY() {
        String customerCode = "code001";
        String applicationNumber = "12345";
        String appStatus = "ACTIVE";
        Long categoryId = 2L;
        Integer page = 1;
        Integer limit = 10;
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        List<ApplicationInfo> appInfo = Arrays.asList(
                new ApplicationInfo(),
                new ApplicationInfo(),
                new ApplicationInfo()
        );
        Page<ApplicationInfo> applicationInfoPages = new PageImpl<>(appInfo, pageable, appInfo.size());
        List<ApplicationInfoListDto> appListDto = Arrays.asList(
                new ApplicationInfoListDto(),
                new ApplicationInfoListDto(),
                new ApplicationInfoListDto()
        );

        doReturn(applicationInfoPages).when(applicationInfoRepository).getNotAssignedToAgentApplications(anyLong(), anyString(), anyString(), anyList(), anyString(), anyList(), anyLong(),any(Pageable.class));
        when(requestMapper.mapAppInfoToAppInfoListDto(appInfo)).thenReturn(appListDto);
        assertThrows(BusinessException.class, () -> {
            applicationServiceImpl.getNotAssignedToAgentApplications(categoryId, applicationNumber, appStatus, customerCode, page, limit);
        });
    }

    @Test
    public void testGetApplicationPaymentPreparationInfoSuccess() {
        Long id = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        ApplicationInfoPaymentDto expected = new ApplicationInfoPaymentDto();
        doReturn(applicationInfo).when(applicationServiceImpl).findById(id);
        when(requestMapper.mapApplicationInfoPayment(applicationInfo)).thenReturn(expected);
        ApplicationInfoPaymentDto result = applicationServiceImpl.getApplicationPaymentPreparationInfo(id);
        assertEquals(expected, result);
        verify(requestMapper, times(1)).mapApplicationInfoPayment(applicationInfo);
    }

    @Test
    public void testGenerateApplicationNumberSuccess() {
//        ReflectionTestUtils.setField(applicationServiceImpl, "portalLink", "https://example.com");
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setServiceCode("SERVICE_CODE");
        applicationNumberGenerationDto.setPaymentDate(LocalDateTime.now());
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        LkApplicationCategory lkApplicationCategory = new LkApplicationCategory();
        lkApplicationCategory.setSaipCode(ApplicationCategoryEnum.PATENT.name());
        applicationInfo.setCategory(lkApplicationCategory);
        applicationInfo.getCategory().setSaipCode(TRADEMARK.name());
        ApplicationRelevantType applicationRelevantType = new ApplicationRelevantType();
        applicationRelevantType.setCustomerCode("code");
        applicationInfo.setApplicationRelevantTypes(Arrays.asList(applicationRelevantType));
        LkApplicationService lkApplicationService = new LkApplicationService();
        lkApplicationService.setOperationNumber("OPERATION_NUMBER");
        when(applicationServiceRepository.findByCode(applicationNumberGenerationDto.getServiceCode())).thenReturn(lkApplicationService);
        when(applicationInfoRepository.countByFilingDateBetween(any(LocalDateTime.class), any(LocalDateTime.class), anyLong()))
                .thenReturn(0L);
        when(requestMapper.mapApplicationInfoPayment(any(ApplicationInfo.class))).thenReturn(new ApplicationInfoPaymentDto());
        when(applicationPriorityService.getPriorityDocumentsAllowanceDays()).thenReturn(10l);
        when(customerServiceCaller.getCustomerMapByListOfCode(any(CustomerCodeListDto.class)))
                .thenReturn(Collections.singletonMap("code", new CustomerSampleInfoDto()));
      //  when(emailSenderFeignClient.sendEmail(any(NotificationDto.class))).thenReturn("done");
      ///  when(smsSenderFeignClient.sendSms(any(NotificationDto.class))).thenReturn("done");

        applicationServiceImpl.generateApplicationNumberAndUpdateApplicationInfoAfterPaymentCallback(applicationNumberGenerationDto, applicationInfo,null);
    }

    @Test
    public void testGetApplicationInfoPaymentSuccess() {
        Long id = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(id);
        applicationInfo.setClaimPagesNumber(5);
        applicationInfo.setShapesNumber(10);
        applicationInfo.setNormalPagesNumber(15);
        when(applicationInfoRepository.findById(id)).thenReturn(Optional.of(applicationInfo));
        when(applicationInfoRepository.save(applicationInfo)).thenReturn(applicationInfo);
        when(documentRepository.calculateSumOfDocumentPages(eq(id), eq("claims"))).thenReturn(3);
        when(documentRepository.calculateSumOfDocumentPages(eq(id), eq("shape"))).thenReturn(7);
        when(documentRepository.calculateSumOfNormalDocumentPages(eq(id))).thenReturn(12);
        ApplicationInfoPaymentDto result = applicationServiceImpl.getApplicationInfoPayment(id);
        assertNotNull(result);
    }

    @Test
    public void testGetApplicationInfoPaymentNullValuesSuccess() {
        Long id = 1L;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(id);
        applicationInfo.setClaimPagesNumber(1);
        applicationInfo.setNormalPagesNumber(1);
        applicationInfo.setShapesNumber(1);
        when(applicationInfoRepository.findById(id)).thenReturn(Optional.of(applicationInfo));
        when(applicationInfoRepository.save(applicationInfo)).thenReturn(applicationInfo);
        when(documentRepository.calculateSumOfDocumentPages(eq(id), eq("claims"))).thenReturn(null);
        when(documentRepository.calculateSumOfDocumentPages(eq(id), eq("shape"))).thenReturn(null);
        when(documentRepository.calculateSumOfNormalDocumentPages(eq(id))).thenReturn(null);
        ApplicationInfoPaymentDto result = applicationServiceImpl.getApplicationInfoPayment(id);
        assertNotNull(result);
    }

    @Test
    public void testGetApplicationInfoPaymentNullApplicationInfoExceptionThrown() {
        Long id = 1L;
        when(applicationInfoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            applicationServiceImpl.getApplicationInfoPayment(id);
        });
    }

    @Test
    public void testAssignDataOfCustomersCodeSuccess() {
        List<ApplicationRelevantTypeDto> applicationRelevantTypes = new ArrayList<>();
        ApplicationRelevantTypeDto applicationRelevantTypeDto1 = new ApplicationRelevantTypeDto();
        applicationRelevantTypeDto1.setCustomerCode("ABC");
        ApplicationRelevantTypeDto applicationRelevantTypeDto2 = new ApplicationRelevantTypeDto();
        applicationRelevantTypeDto2.setCustomerCode("DEF");
        applicationRelevantTypes.add(applicationRelevantTypeDto1);
        applicationRelevantTypes.add(applicationRelevantTypeDto2);
        CustomerSampleInfoDto customerSampleInfoDto1 = new CustomerSampleInfoDto();
        customerSampleInfoDto1.setNameAr("Customer 1 AR");
        customerSampleInfoDto1.setNameEn("Customer 1 EN");
        customerSampleInfoDto1.setNationality(new CountryDto());
        CustomerSampleInfoDto customerSampleInfoDto2 = new CustomerSampleInfoDto();
        customerSampleInfoDto2.setNameAr("Customer 2 AR");
        customerSampleInfoDto2.setNameEn("Customer 2 EN");
        customerSampleInfoDto2.setNationality(new CountryDto());
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = new HashMap<>();
        customerSampleInfoDtoMap.put("abc", customerSampleInfoDto1);
        customerSampleInfoDtoMap.put("def", customerSampleInfoDto2);
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        LinkedList<String> customerCodeList = new LinkedList<>();
        customerCodeList.add("ABC");
        customerCodeList.add("DEF");
        customerCodeListDto.setCustomerCode(customerCodeList);
        when(customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto)).thenReturn(customerSampleInfoDtoMap);
        applicationServiceImpl.assignDataOfCustomersCode(applicationRelevantTypes);
        assertEquals(2, applicationRelevantTypes.size());
    }

    @Test
    public void testAssignDataOfCustomersCodeNullListSuccess() {
        List<ApplicationRelevantTypeDto> applicationRelevantTypes = null;
        applicationServiceImpl.assignDataOfCustomersCode(applicationRelevantTypes);
        assertNull(applicationRelevantTypes);
        verifyNoInteractions(customerServiceCaller);
    }

    @Test
    public void testAssignDataOfCustomersCodeEmptyListSuccess() {
        List<ApplicationRelevantTypeDto> applicationRelevantTypes = new ArrayList<>();
        applicationServiceImpl.assignDataOfCustomersCode(applicationRelevantTypes);
        assertTrue(applicationRelevantTypes.isEmpty());
        verifyNoInteractions(customerServiceCaller);
    }

    @Test
    public void testAssignDataOfCustomersCodeNullCustomerCodeSuccess() {
        List<ApplicationRelevantTypeDto> applicationRelevantTypes = new ArrayList<>();
        ApplicationRelevantTypeDto applicationRelevantTypeDto1 = new ApplicationRelevantTypeDto();
        applicationRelevantTypeDto1.setCustomerCode(null);
        ApplicationRelevantTypeDto applicationRelevantTypeDto2 = new ApplicationRelevantTypeDto();
        applicationRelevantTypeDto2.setCustomerCode(null);
        applicationRelevantTypes.add(applicationRelevantTypeDto1);
        applicationRelevantTypes.add(applicationRelevantTypeDto2);
        applicationServiceImpl.assignDataOfCustomersCode(applicationRelevantTypes);
        assertEquals(2,applicationRelevantTypes.size());
        assertNull(applicationRelevantTypeDto1.getApplicationRelevant());
        assertNull(applicationRelevantTypeDto2.getApplicationRelevant());
        verifyNoInteractions(customerServiceCaller);
    }

    @Test
    public void testGetApplicationListByApplicationCategoryAndUserIdSuccess() {
        String applicationCategory = "Patent";
        Long categoryId = 1L;
        String applicationNumber = "123";
        Long appId = 2L;
        Long userId = 3L;
        Integer page = 1;
        Integer limit = 10;
        List<ApplicationInfo> applicationInfos = new ArrayList<>();
        ApplicationInfo applicationInfo1 = new ApplicationInfo();
        applicationInfo1.setId(1L);
        applicationInfo1.setCategory(new LkApplicationCategory());
        applicationInfo1.setApplicationStatus(new LkApplicationStatus());

        ApplicationRelevantType applicationRelevantTypeDto1 = new ApplicationRelevantType();
        applicationRelevantTypeDto1.setCustomerCode("ABC"); // Ensure customerCode is not null
        List<ApplicationRelevantType> applicationRelevantTypes1 = new ArrayList<>();
        applicationRelevantTypes1.add(applicationRelevantTypeDto1);
        applicationInfo1.setApplicationRelevantTypes(applicationRelevantTypes1);
        applicationInfo1.setApplicationNumber("123");
        applicationInfo1.setTitleEn("Title EN");
        applicationInfo1.setTitleAr("Title AR");
        applicationInfo1.setFilingDate(LocalDateTime.now());
        applicationInfo1.setPartialApplication(false);
        LkApplicationStatus applicationStatus1 = new LkApplicationStatus();
        applicationStatus1.setCode("code");
        applicationStatus1.setIpsStatusDescEn("IPS EN");
        applicationStatus1.setIpsStatusDescAr("IPS AR");
        applicationInfo1.setApplicationStatus(applicationStatus1);
        applicationInfo1.setModifiedDate(LocalDateTime.now().minusDays(1));
        applicationInfos.add(applicationInfo1);
        Page<ApplicationInfo> applicationInfoPages = new PageImpl<>(applicationInfos);
        List<Long> userIds = new ArrayList<>();
        userIds.add(userId);
        when(customerServiceCaller.getUserColleagues(userId)).thenReturn(ApiResponse.ok(userIds, null));
        when(applicationInfoRepository.getApplicationListByApplicationCategoryAndUserIds(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), anyLong(), Mockito.any())).thenReturn(applicationInfoPages);

        when(bpmCallerService.getTaskByRowId(Mockito.anyLong(),anyString())).thenReturn(new RequestTasksDto());
        CustomerSampleInfoDto customerSampleInfoDto = new CustomerSampleInfoDto();
        customerSampleInfoDto.setNameAr("Customer AR");
        customerSampleInfoDto.setNameEn("Customer EN");
        customerSampleInfoDto.setNationality(new CountryDto());
        Map<String, CustomerSampleInfoDto> customerMap = new HashMap<>();
        customerMap.put("abc", customerSampleInfoDto);
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        LinkedList<String> customerCodes = new LinkedList<>();
        customerCodes.add("ABC"); // Ensure a non-null customerCode in the list
        customerCodeListDto.setCustomerCode(customerCodes);
        when(customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto)).thenReturn(customerMap);
        PaginationDto result = applicationServiceImpl.getApplicationListByApplicationCategoryAndUserId(applicationCategory, userId,page, limit, ApplicationListSortingColumns.id, Sort.Direction.DESC);
        assertNotNull(result);
    }


    @Test
    public void testGetApplicationListByApplicationCategoryAndUserIdSuccess_2() {
        String applicationCategory = "INDUSTRIAL_DESIGN";
        Long categoryId = 1L;
        String applicationNumber = "123";
        Long appId = 2L;
        Long userId = 3L;
        Integer page = 1;
        Integer limit = 10;
        List<ApplicationInfo> applicationInfos = new ArrayList<>();
        ApplicationInfo applicationInfo1 = new ApplicationInfo();
        applicationInfo1.setId(1L);
        applicationInfo1.setCategory(new LkApplicationCategory());
        applicationInfo1.setApplicationStatus(new LkApplicationStatus());
        ApplicationRelevantType applicationRelevantTypeDto1 = new ApplicationRelevantType();
        applicationRelevantTypeDto1.setCustomerCode("ABC");
        List<ApplicationRelevantType> applicationRelevantTypes1 = new ArrayList<>();
        applicationRelevantTypes1.add(applicationRelevantTypeDto1);
        applicationInfo1.setApplicationRelevantTypes(applicationRelevantTypes1);
        applicationInfo1.setApplicationNumber("123");
        applicationInfo1.setTitleEn("Title EN");
        applicationInfo1.setTitleAr("Title AR");
        applicationInfo1.setFilingDate(LocalDateTime.now());
        applicationInfo1.setPartialApplication(false);
        LkApplicationStatus applicationStatus1 = new LkApplicationStatus();
        applicationStatus1.setCode("code");
        applicationStatus1.setIpsStatusDescEn("IPS EN");
        applicationStatus1.setIpsStatusDescAr("IPS AR");
        applicationInfo1.setApplicationStatus(applicationStatus1);
        applicationInfo1.setModifiedDate(LocalDateTime.now().minusDays(1));
        applicationInfos.add(applicationInfo1);
        Page<ApplicationInfo> applicationInfoPages = new PageImpl<>(applicationInfos);
        List<Long> userIds = new ArrayList<>();
        userIds.add(userId);
        List<String> applicationCategories = new ArrayList<>();
        Long customerId = 3L;
        List<String> appStatus = new ArrayList<>();

        when(customerServiceCaller.getUserColleagues(userId)).thenReturn(ApiResponse.ok(userIds, null));
        when(applicationInfoRepository.getApplicationListByApplicationCategoryAndUserIds(
                applicationCategories, categoryId, applicationNumber, appId, customerId, appStatus, customerId, Pageable.unpaged()))
                .thenReturn(applicationInfoPages);
        when(applicationInfoRepository.getApplicationListByApplicationCategoryAndUserIds(Mockito.any(),Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(),Mockito.any(), anyLong(), Mockito.any())).thenReturn(applicationInfoPages);

        when(applicationInfoRepository.getApplicationListByApplicationCategoryAndUserIdsForSupportServices(Mockito.any(),Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(),Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(applicationInfoPages);

        CustomerSampleInfoDto customerSampleInfoDto = new CustomerSampleInfoDto();
        customerSampleInfoDto.setNameAr("Customer AR");
        customerSampleInfoDto.setNameEn("Customer EN");
        customerSampleInfoDto.setNationality(new CountryDto());
        Map<String, CustomerSampleInfoDto> customerMap = new HashMap<>();
        ApplicationListDto applicationListDto = new ApplicationListDto();
        applicationListDto.setCustomerCode("test");
        customerMap.put(applicationListDto.getCustomerCode(), customerSampleInfoDto);
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        LinkedList<String> customerCodes = new LinkedList<>();
        customerCodes.add("ABC");
        customerCodeListDto.setCustomerCode(customerCodes);

        // Set the "CUSTOM-HEADER" header to an unexpected value
        when(customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto)).thenReturn(customerMap);
        PaginationDto result = applicationServiceImpl.getApplicationListByApplicationCategoryAndUserId(applicationCategory, userId, page, limit, ApplicationListSortingColumns.id, Sort.Direction.DESC);
        assertNotNull(result);


    }

    @Test
    public void testGetApplicationListByApplicationCategoryAndUserIdSuccess_3() {
        String applicationCategory = "Industrial Design";
        Long categoryId = 1L;
        String applicationNumber = "123";
        Long appId = 2L;
        Long userId = 3L;
        Integer page = 1;
        Integer limit = 10;
        List<ApplicationInfo> applicationInfos = new ArrayList<>();
        ApplicationInfo applicationInfo1 = new ApplicationInfo();
        applicationInfo1.setId(1L);
        applicationInfo1.setCategory(new LkApplicationCategory());
        applicationInfo1.setApplicationStatus(new LkApplicationStatus());
        ApplicationRelevantType applicationRelevantTypeDto1 = new ApplicationRelevantType();
        applicationRelevantTypeDto1.setCustomerCode("ABC");
        List<ApplicationRelevantType> applicationRelevantTypes1 = new ArrayList<>();
        applicationRelevantTypes1.add(applicationRelevantTypeDto1);
        applicationInfo1.setApplicationRelevantTypes(applicationRelevantTypes1);
        applicationInfo1.setApplicationNumber("123");
        applicationInfo1.setTitleEn("Title EN");
        applicationInfo1.setTitleAr("Title AR");
        applicationInfo1.setFilingDate(LocalDateTime.now());
        applicationInfo1.setPartialApplication(false);
        LkApplicationStatus applicationStatus1 = new LkApplicationStatus();
        applicationStatus1.setCode("code");
        applicationStatus1.setIpsStatusDescEn("IPS EN");
        applicationStatus1.setIpsStatusDescAr("IPS AR");
        applicationInfo1.setApplicationStatus(applicationStatus1);
        applicationInfo1.setModifiedDate(LocalDateTime.now().minusDays(1));
        applicationInfos.add(applicationInfo1);
        Page<ApplicationInfo> applicationInfoPages = new PageImpl<>(applicationInfos);
        List<Long> userIds = new ArrayList<>();
        userIds.add(userId);
        when(customerServiceCaller.getUserColleagues(userId)).thenReturn(ApiResponse.ok(userIds, null));
        when(applicationInfoRepository.getApplicationListByApplicationCategoryAndUserIds(Mockito.any(),Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(),Mockito.any(),anyLong(), Mockito.any())).thenReturn(applicationInfoPages);

        CustomerSampleInfoDto customerSampleInfoDto = new CustomerSampleInfoDto();
        customerSampleInfoDto.setNameAr("Customer AR");
        customerSampleInfoDto.setNameEn("Customer EN");
        customerSampleInfoDto.setNationality(new CountryDto());
        Map<String, CustomerSampleInfoDto> customerMap = new HashMap<>();
        customerMap.put("abc", customerSampleInfoDto);
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        LinkedList<String> customerCodes = new LinkedList<>();
        customerCodes.add("ABC");
        customerCodeListDto.setCustomerCode(customerCodes);
        when(customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto)).thenReturn(customerMap);
        PaginationDto<List<ApplicationListDto>> result = applicationServiceImpl.getApplicationListByApplicationCategoryAndUserId(
                applicationCategory, userId, page, limit, ApplicationListSortingColumns.id, Sort.Direction.DESC);

        assertNotNull(result);


    }







    @Test
    public void testGetApplicationListByApplicationCategoryAndUserIdBusinessException() {
        Long userId = 1L;
        when(customerServiceCaller.getUserColleagues(userId)).thenThrow(new BusinessException("Error"));
        assertThrows(BusinessException.class, () -> {
            applicationServiceImpl.getApplicationListByApplicationCategoryAndUserId("category", 1L,1, 10, ApplicationListSortingColumns.id, Sort.Direction.DESC);
        });
    }

    @Test
    public void testGetApplicationListByApplicationCategoryAndUserIdException() {
        Long userId = 1L;
        when(customerServiceCaller.getUserColleagues(userId)).thenThrow(new RuntimeException("Error"));
        assertThrows(RuntimeException.class, () -> {
            applicationServiceImpl.getApplicationListByApplicationCategoryAndUserId("category", 1L,1, 10, ApplicationListSortingColumns.id, Sort.Direction.DESC);
        });
    }

    @Test
    public void testAddApplicationSuccess() {
        ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
        Long appId = 1L;
        String customerCode = "ABC";
        String categoryKey = "key";
        LkApplicationCategory lkApplicantCategory = new LkApplicationCategory();
        lkApplicantCategory.setId(1L);
        lkApplicantCategory.setSaipCode("test");
        applicantsRequestDto.setCustomerCode(customerCode);
        applicantsRequestDto.setAppId(1l);
        applicantsRequestDto.setCategoryKey(categoryKey);
        List<ApplicationRelevantRequestsDto> relevants = new ArrayList<>();
        ApplicationRelevantRequestsDto relevant = new ApplicationRelevantRequestsDto();
        relevant.setIdentifierType(IdentifierTypeEnum.CUSTOMER_CODE);
        relevant.setIdentifier("email@example.com");
        ApplicationRelevantRequestsDto relevant2 = new ApplicationRelevantRequestsDto();
        relevant2.setIdentifierType(IdentifierTypeEnum.IQAMA_NUMBER);
        relevant2.setIdentifier("email@example.com");
        relevants.add(relevant);
        relevants.add(relevant2);
        applicantsRequestDto.setRelevants(relevants);
        when(requestMapper.mapRequestToEntity(applicantsRequestDto)).thenReturn(new ApplicationInfo());
        when(lkApplicationCategoryRepository.findByApplicationCategoryDescEn(categoryKey)).thenReturn(Optional.of(lkApplicantCategory));
        ApplicationInfo saved = new ApplicationInfo();
        saved.setId(appId);
        when(applicationInfoRepository.save(any(ApplicationInfo.class))).thenReturn(saved);
        ApplicationRelevantType applicationRelevantTypeInfo = new ApplicationRelevantType();
        applicationRelevantTypeInfo.setApplicationInfo(saved);
        applicationRelevantTypeInfo.setType(ApplicationRelevantEnum.Applicant_MAIN);
        applicationRelevantTypeInfo.setCustomerCode(customerCode);
        when(applicationRelevantTypeRepository.save(any(ApplicationRelevantType.class))).thenReturn(applicationRelevantTypeInfo);
        ApplicationRelevant applicationRelevant = new ApplicationRelevant();
        when(applicationRelvantMapper.mapRequestToEntity(any(ApplicationRelevantRequestsDto.class))).thenReturn(applicationRelevant);
        ApplicationRelevantType applicationRelevantTypePP = new ApplicationRelevantType();
        applicationRelevantTypePP.setType(Applicant_SECONDARY);
        applicationRelevantTypePP.setApplicationInfo(saved);
        when(applicationRelevantTypeRepository.save(any(ApplicationRelevantType.class))).thenReturn(applicationRelevantTypePP);
        ApplicationRelevant value = new ApplicationRelevant();
        when(applicationRelevantRepository.findByIdentifierIgnoreCaseAndIdentifierType(Mockito.any(),Mockito.any())).thenReturn(Optional.of(value)).thenReturn(Optional.empty());
        when(lkApplicationCategoryRepository.findByApplicationCategoryDescEn(anyString())).thenReturn(Optional.of(lkApplicantCategory));

        ApplicationInfo result = applicationServiceImpl.addApplication(applicantsRequestDto);
        assertEquals(appId, result.getId());
    }



    @Test
    public void testAddApplicationNoCategory() throws Exception {
        String categoryKey = "key";
        ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
        String customerCode = "ABC";
        LkApplicationCategory lkApplicantCategory = new LkApplicationCategory();
        lkApplicantCategory.setId(1L);
        applicantsRequestDto.setCustomerCode(customerCode);
        applicantsRequestDto.setAppId(1l);
        applicantsRequestDto.setCategoryKey(categoryKey);
        applicantsRequestDto.setRelevants(new ArrayList<>());
        when(requestMapper.mapRequestToEntity(applicantsRequestDto)).thenThrow(new BusinessException("test"));
        when(lkApplicationCategoryRepository.findByApplicationCategoryDescEn(categoryKey)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> {
            applicationServiceImpl.addApplication(applicantsRequestDto);
        });
    }

    @Test
    public void testAddApplicationDatabaseException() throws Exception {
        String categoryKey = "key";
        ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
        String customerCode = "ABC";
        LkApplicationCategory lkApplicantCategory = new LkApplicationCategory();
        lkApplicantCategory.setId(1L);
        applicantsRequestDto.setCustomerCode(customerCode);
        applicantsRequestDto.setAppId(1l);
        applicantsRequestDto.setCategoryKey(categoryKey);
        applicantsRequestDto.setRelevants(new ArrayList<>());
        DataAccessException dataAccessException = new DataAccessException("DB error") {
        };
        when(requestMapper.mapRequestToEntity(applicantsRequestDto)).thenThrow(new RuntimeException());
        when(lkApplicationCategoryRepository.findByApplicationCategoryDescEn(categoryKey)).thenReturn(Optional.of(new LkApplicationCategory()));
        when(applicationInfoRepository.save(any(ApplicationInfo.class))).thenThrow(dataAccessException);

        assertThrows(RuntimeException.class, () -> {
            applicationServiceImpl.addApplication(applicantsRequestDto);
        });
    }

    @Test
    public void testUpdateAppRelvantInfoSuccess() {
        ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
        Long appId = 1L;
        String customerCode = "ABC";
        String categoryKey = "key";
        LkApplicationCategory lkApplicantCategory = new LkApplicationCategory();
        lkApplicantCategory.setId(1L);
        applicantsRequestDto.setCustomerCode(customerCode);
        applicantsRequestDto.setAppId(1l);
        applicantsRequestDto.setCategoryKey(categoryKey);
        List<ApplicationRelevantRequestsDto> relevants = new ArrayList<>();
        ApplicationRelevantRequestsDto relevant = new ApplicationRelevantRequestsDto();
        relevant.setIdentifierType(IdentifierTypeEnum.CUSTOMER_CODE);
        relevant.setIdentifier("email@example.com");
        ApplicationRelevantRequestsDto relevant2 = new ApplicationRelevantRequestsDto();
        relevant2.setIdentifierType(IdentifierTypeEnum.IQAMA_NUMBER);
        relevant2.setIdentifier("email@example.com");
        relevants.add(relevant);
        relevants.add(relevant2);
        applicantsRequestDto.setRelevants(relevants);
        when(lkApplicationCategoryRepository.findByApplicationCategoryDescEn(categoryKey)).thenReturn(Optional.of(lkApplicantCategory));
        ApplicationInfo applicationInfoExist = new ApplicationInfo();
        applicationInfoExist.setId(appId);
        when(applicationInfoRepository.findById(appId)).thenReturn(Optional.of(applicationInfoExist));
        ApplicationInfo saved = new ApplicationInfo();
        saved.setId(appId);
        when(applicationInfoRepository.save(any(ApplicationInfo.class))).thenReturn(saved);
        ApplicationRelevantType applicationRelevantTypePP = new ApplicationRelevantType();
        applicationRelevantTypePP.setType(Applicant_SECONDARY);
        applicationRelevantTypePP.setApplicationInfo(applicationInfoExist);
        when(applicationRelevantTypeRepository.save(any(ApplicationRelevantType.class))).thenReturn(applicationRelevantTypePP);
        ApplicationRelevant applicationRelevant = new ApplicationRelevant();
        when(applicationRelvantMapper.mapRequestToEntity(any(ApplicationRelevantRequestsDto.class))).thenReturn(applicationRelevant);
        ApplicationRelevant updatedApplicationRelevant = new ApplicationRelevant();
        when(applicationRelvantMapper.mapRequestToEntity(eq(applicationRelevant), any(ApplicationRelevant.class))).thenReturn(updatedApplicationRelevant);
        ApplicationRelevant savedApplicationRelevant = new ApplicationRelevant();
        savedApplicationRelevant.setId(1L);
        when(applicationRelevantRepository.save(any(ApplicationRelevant.class))).thenReturn(savedApplicationRelevant);
        ApplicationRelevant value = new ApplicationRelevant();
        when(applicationRelevantRepository.findByIdentifierIgnoreCaseAndIdentifierType(Mockito.any(),Mockito.any())).thenReturn(Optional.of(value)).thenReturn(Optional.empty());


        ApplicationInfo result = applicationServiceImpl.updateAppRelvantInfo(applicantsRequestDto);
        assertEquals(appId, result.getId());

    }
        @Test
        public void testUpdateAppRelvantInfoInvalidAppId() {
            ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
            Long appId = 1L;
            String customerCode = "ABC";
            String categoryKey = "key";
            LkApplicationCategory lkApplicantCategory = new LkApplicationCategory();
            lkApplicantCategory.setId(1L);
            applicantsRequestDto.setCustomerCode(customerCode);
            applicantsRequestDto.setAppId(1l);
            applicantsRequestDto.setCategoryKey(categoryKey);
            List<ApplicationRelevantRequestsDto> relevants = new ArrayList<>();
            ApplicationRelevantRequestsDto relevant = new ApplicationRelevantRequestsDto();
            relevant.setIdentifierType(IdentifierTypeEnum.CUSTOMER_CODE);
            relevant.setIdentifier("email@example.com");
            relevants.add(relevant);
            applicantsRequestDto.setRelevants(relevants);
            when(applicationInfoRepository.findById(appId)).thenThrow(new BusinessException("test"));
            when(applicationInfoRepository.save(any(ApplicationInfo.class))).thenThrow(BusinessException.class);
            assertThrows(BusinessException.class, () -> applicationServiceImpl.updateAppRelvantInfo(applicantsRequestDto));
        }

        @Test
        public void testUpdateAppRelvantInfoInvalidCategory() {
            ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
            Long appId = 1L;
            String customerCode = "ABC";
            String categoryKey = "key";
            LkApplicationCategory lkApplicantCategory = new LkApplicationCategory();
            lkApplicantCategory.setId(1L);
            applicantsRequestDto.setCustomerCode(customerCode);
            applicantsRequestDto.setAppId(1l);
            applicantsRequestDto.setCategoryKey(categoryKey);
            List<ApplicationRelevantRequestsDto> relevants = new ArrayList<>();
            ApplicationRelevantRequestsDto relevant = new ApplicationRelevantRequestsDto();
            relevant.setIdentifierType(IdentifierTypeEnum.CUSTOMER_CODE);
            relevant.setIdentifier("email@example.com");
            relevants.add(relevant);
            applicantsRequestDto.setRelevants(relevants);
            when(lkApplicationCategoryRepository.findByApplicationCategoryDescEn(categoryKey)).thenReturn(Optional.empty());
            ApplicationInfo applicationInfoExist = new ApplicationInfo();
            applicationInfoExist.setId(appId);
            when(applicationInfoRepository.findById(appId)).thenThrow(new BusinessException("test"));
            when(applicationInfoRepository.save(any(ApplicationInfo.class))).thenThrow(BusinessException.class);
            assertThrows(BusinessException.class, () -> applicationServiceImpl.updateAppRelvantInfo(applicantsRequestDto));
        }

        @Test
        public void testUpdateAppRelvantInfoDatabaseError() {
            ApplicantsRequestDto applicantsRequestDto = new ApplicantsRequestDto();
            Long appId = 1L;
            String customerCode = "ABC";
            String categoryKey = "key";
            LkApplicationCategory lkApplicantCategory = new LkApplicationCategory();
            lkApplicantCategory.setId(1L);
            applicantsRequestDto.setCustomerCode(customerCode);
            applicantsRequestDto.setAppId(1l);
            applicantsRequestDto.setCategoryKey(categoryKey);
            List<ApplicationRelevantRequestsDto> relevants = new ArrayList<>();
            ApplicationRelevantRequestsDto relevant = new ApplicationRelevantRequestsDto();
            relevant.setIdentifierType(IdentifierTypeEnum.CUSTOMER_CODE);
            relevant.setIdentifier("email@example.com");
            relevants.add(relevant);
            applicantsRequestDto.setRelevants(relevants);
            when(lkApplicationCategoryRepository.findByApplicationCategoryDescEn(categoryKey)).thenReturn(Optional.of(lkApplicantCategory));
            ApplicationInfo applicationInfoExist = new ApplicationInfo();
            applicationInfoExist.setId(appId);
            when(applicationInfoRepository.findById(appId)).thenReturn(Optional.of(applicationInfoExist));
            when(applicationInfoRepository.save(any(ApplicationInfo.class))).thenThrow(RuntimeException.class);
            assertThrows(RuntimeException.class, () -> applicationServiceImpl.updateAppRelvantInfo(applicantsRequestDto));
        }

    private static PartialApplicationInfoProjection getProjection() {
        return new PartialApplicationInfoProjection() {
            @Override
            public Long getId() {
                return null;
            }

            @Override
            public String getTitleAr() {
                return null;
            }

            @Override
            public String getTitleEn() {
                return null;
            }

            @Override
            public String getApplicationNumber() {
                return null;
            }

            @Override
            public String getRequestId() {
                return null;
            }
        };
    }

    @Test
    public void checkIfApplicationNumberExistsTest() throws Exception {

        //Arrange
        String appNumber = "123";
        Long partialAppId = 1L;
        List<ApplicationCustomer> appCustomersByIdsAndType =  new ArrayList<>();
        ApplicationCustomer applicationCustomer = new ApplicationCustomer();
        applicationCustomer.setCustomerCode("5674");
        ApplicationCustomer applicationCustomer2 = new ApplicationCustomer();
        applicationCustomer2.setCustomerCode("5674");
        appCustomersByIdsAndType.add(applicationCustomer);
        appCustomersByIdsAndType.add(applicationCustomer2);
        when(applicationInfoRepository.countByApplicationNumber(appNumber, "PATENT", List.of(
                "IN_COMMITTEE",
                "INVITATION_FOR_OBJECTIVE_CORRECTION",
                "PAYMENT_OF_MODIFICATION_FEES_IS_PENDING",
                "RETURNED_TO_THE_APPLICANT",
                "INVITATION_FOR_FORMAL_CORRECTION",
                "UNDER_FORMAL_PROCESS",
                "UNDER_OBJECTIVE_PROCESS"
        ), 1L)).thenReturn(1L);

        when(applicationCustomerService.getAppCustomersByIdsAndType(any(),any())).thenReturn(appCustomersByIdsAndType);
        //Act
        when(applicationInfoRepository.getApplicationStatus(anyLong())).thenReturn(UNDER_OBJECTIVE_PROCESS.name());
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1l);
        when(applicationInfoRepository.getMainApplicationInfo(any(),any())).thenReturn(applicationInfo);
        ApplicationInfo res = applicationServiceImpl.checkMainApplicationExists(appNumber, partialAppId);

        //Assert
        //assertTrue(res);

    }
}