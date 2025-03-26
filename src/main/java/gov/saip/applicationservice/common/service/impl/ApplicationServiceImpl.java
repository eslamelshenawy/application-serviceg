package gov.saip.applicationservice.common.service.impl;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.PATENT;
import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.TRADEMARK;
import static gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum.FILE_NEW_APPLICATION;
import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_MAIN;
import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_SECONDARY;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.ACCEPTANCE;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.DRAFT;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.FORMAL_REJECTION;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.REJECTION;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.REVOKED_BY_COURT_ORDER;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.REVOKED_PURSUANT_TO_COURT_RULING;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.THE_TRADEMARK_IS_REGISTERED;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.UNDER_FORMAL_PROCESS;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.WAIVED;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.INDUSTRIAL_DESIGN;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.OPPOSITION_REQUEST;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.REVOKE_LICENSE_REQUEST;
import static gov.saip.applicationservice.common.enums.RequestTypeEnum.TRADEMARK_APPEAL_REQUEST;
import static gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum.COMPLETED;
import static gov.saip.applicationservice.util.Constants.MAP_CATEGORY_ID_TO_ACCEPTANCE_STATUS;
import static gov.saip.applicationservice.util.Constants.MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE;
import static gov.saip.applicationservice.util.Constants.NON_OWNER_SUPPORT_SERVICE_STRING_CODES;
import static gov.saip.applicationservice.util.Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.saip.applicationservice.common.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.AllApplicationsDto;
import gov.saip.applicationservice.common.dto.AllApplicationsDtoLight;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.AppCharacteristics;
import gov.saip.applicationservice.common.dto.AppCommunicationUpdateRequestsDto;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.ApplicantsRequestDto;
import gov.saip.applicationservice.common.dto.ApplicationAdditionalDetailsDto;
import gov.saip.applicationservice.common.dto.ApplicationCertificateDocumentDto;
import gov.saip.applicationservice.common.dto.ApplicationCheckingReportDto;
import gov.saip.applicationservice.common.dto.ApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.ApplicationClassificationLightDto;
import gov.saip.applicationservice.common.dto.ApplicationCustomerData;
import gov.saip.applicationservice.common.dto.ApplicationDataUpdateDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoListDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoPaymentDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoSummaryDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoTaskDto;
import gov.saip.applicationservice.common.dto.ApplicationListDto;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.ApplicationPriorityResponseDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantLightDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import gov.saip.applicationservice.common.dto.ApplicationRequestBodyDto;
import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationDto;
import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationRetrieveDto;
import gov.saip.applicationservice.common.dto.BillApplicationInfoAttributesDto;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.CustomerCodeListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DepositReportDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.DurationDto;
import gov.saip.applicationservice.common.dto.InventorRequestsDto;
import gov.saip.applicationservice.common.dto.LKDocumentTypeDto;
import gov.saip.applicationservice.common.dto.ListApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.PartialApplicationInfoDto;
import gov.saip.applicationservice.common.dto.PartialApplicationInfoProjection;
import gov.saip.applicationservice.common.dto.RequestActivityLogsDto;
import gov.saip.applicationservice.common.dto.RequestIdAndNotesDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.StartProcessResponseDto;
import gov.saip.applicationservice.common.dto.UpdateApplicationsStatusDto;
import gov.saip.applicationservice.common.dto.Suspcion.TrademarkDetailsProjection;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.dto.lookup.LkFeeCostDto;
import gov.saip.applicationservice.common.dto.payment.ApplicationBillLightDTO;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.dto.search.AdvancedSearchDto;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.dto.trademark.RequestTrademarkDetailsDto;
import gov.saip.applicationservice.common.dto.trademark.TradeMarkLightDto;
import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.ApplicationListSortingColumns;
import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.ApplicationServices;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.ApplicationStatusPreconditions;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.enums.CustomerApplicationAccessLevel;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.enums.RequestActivityLogEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.TrademarkType;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.enums.certificate.CertificateTypeEnum;
import gov.saip.applicationservice.common.mapper.ApplicationClassificationMapper;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.mapper.ApplicationRelevantTypeMapper;
import gov.saip.applicationservice.common.mapper.ApplicationRelvantMapper;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.model.industrial.IndustrialDesignDetail;
import gov.saip.applicationservice.common.model.patent.ApplicationCertificateDocument;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.projection.ApplicationReportInfo;
import gov.saip.applicationservice.common.projection.CountApplications;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationNiceClassificationRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.LicenceRequestRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationServiceRepository;
import gov.saip.applicationservice.common.repository.lookup.LkClassificationUnitRepository;
import gov.saip.applicationservice.common.repository.supportService.ApplicationEditTrademarkImageRequestRepository;
import gov.saip.applicationservice.common.repository.supportService.ApplicationPriorityRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationAcceleratedService;
import gov.saip.applicationservice.common.service.ApplicationCheckingReportService;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationNiceClassificationService;
import gov.saip.applicationservice.common.service.ApplicationNotesService;
import gov.saip.applicationservice.common.service.ApplicationPriorityService;
import gov.saip.applicationservice.common.service.ApplicationPublicationService;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.ApplicationUserService;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.common.service.ClassificationService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.ExtensionRequestService;
import gov.saip.applicationservice.common.service.InitialModificationRequestService;
import gov.saip.applicationservice.common.service.PaymentCallBackService;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import gov.saip.applicationservice.common.service.patent.ApplicationCertificateDocumentService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.common.validators.CustomerCodeValidator;
import gov.saip.applicationservice.config.Properties;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.common.service.impl.BaseApplicationInfoServiceImpl;
import gov.saip.applicationservice.modules.ic.service.IntegratedCircuitService;
import gov.saip.applicationservice.modules.plantvarieties.service.PlantVarietyService;
import gov.saip.applicationservice.util.ApplicationValidator;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.SupportServiceValidator;
import gov.saip.applicationservice.util.Util;
import gov.saip.applicationservice.util.Utilities;
import joptsimple.internal.Strings;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Setter
@Primary
public class ApplicationServiceImpl extends BaseApplicationInfoServiceImpl implements ApplicationInfoService {


    @Override
    @PostConstruct
    public void init() {
//        registerService(ApplicationCategoryEnum.TRADEMARK, this);
//        registerService(ApplicationCategoryEnum.PATENT, this);
//        registerService(ApplicationCategoryEnum.INDUSTRIAL_DESIGN, this);
    }

    @Override
    public ApplicationCategoryEnum getApplicationCategoryEnum() {
        return null;
    }

    @Override
    protected ApplicationInfoRepository getBaseApplicationInfoRepository() {
        return applicationInfoRepository;
    }

    private final static Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    public static final String trademarkCategory = "TRADEMARK";
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ApplicationAcceleratedService applicationAcceleratedService;
    private final PaymentCallBackService paymentService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final IndustrialDesignDetailService industrialDesignDetailService;
    private final ApplicationCheckingReportService reportService;
    private final ClassificationService classificationService;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final PatentDetailsService patentDetailsService;
    private final PaymentFeeCostFeignClient paymentFeeCostFeignClient;
    private final ApplicationAgentService applicationAgentService;
    private final DocumentRepository documentRepository;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final ApplicationInfoMapper requestMapper;
    private final DocumentsService documentsService;
    private final ApplicationUserService applicationUserService;
    private final ApplicationClassificationMapper applicationClassificationMapper;
    private final LkApplicationCategoryRepository lkApplicationCategoryRepository;
    private final CustomerServiceCaller customerServiceCaller;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationPriorityService applicationPriorityService;
    private final BPMCallerServiceImpl bpmCallerService;
    private final ApplicationNotesService applicationNotesService;
    private final LkApplicationStatusService applicationStatusService;
    private final TrademarkDetailService trademarkDetailService;
    private final PctService pctService;
    private final ApplicationValidator applicationValidator;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;

    @Autowired
    private ApplicationRelevantTypeMapper applicationRelevantTypeMapper;
    @Autowired
    private ApplicationNiceClassificationRepository applicationNiceClassificationRepository;
    @Autowired
    private LkClassificationUnitRepository lkClassificationUnitRepository;
    @Autowired
    private TrademarkAgencyRequestService trademarkAgencyRequestService;
    @Autowired
    private SupportServiceProcess supportServiceProcess;

    private ClassificationMapper classificationMapper;

    @Autowired
    private LicenceRequestRepository licenceRequestRepository;
    @Lazy
    @Autowired
    private ActivityLogService activityLogService;
    @Autowired
    @Lazy
    private  ApplicationCertificateDocumentService applicationCertificateDocumentService;
    @Autowired
    private ApplicationAgentFacadeService applicationAgentFacadeService;
    private static final List<String> EXCLUDED_DOCUMENT_TYPES_CODES = Arrays.asList("Prioirty Document", "Issue Certificate", "app_drawing", "APPLICATION_XML");
    @Lazy
    @Autowired
    private CertificateRequestService certificateRequestService;
    @Autowired
    @Lazy
    private ApplicationPublicationService applicationPublicationService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setClassificationMapper(ClassificationMapper classificationMapper) {
        this.classificationMapper = classificationMapper;
    }

    private InitialModificationRequestService initialModificationRequestService;

    private ExtensionRequestService extensionRequestService;

    @Autowired
    public void setInitialModificationRequestService(@Lazy InitialModificationRequestService initialModificationRequestService) {
        this.initialModificationRequestService = initialModificationRequestService;
    }

    @Autowired
    public void setExtensionRequestService(@Lazy ExtensionRequestService extensionRequestService) {
        this.extensionRequestService = extensionRequestService;
    }


    @Autowired
    private ApplicationCustomerService applicationCustomerService;

    @Autowired
    @Lazy
    private SupportServiceValidator supportServiceValidator;
    @Autowired
    private Util util;

    @Autowired
    private ApplicationPriorityRequestRepository applicationPriorityRequestRepository;
    @Autowired
    private IntegratedCircuitService integratedCircuitService;

    @Autowired
    private PlantVarietyService plantVarietyService;
    @Autowired
    private CustomerClient customerClient;

    public static final String PETITION_REQUEST_NATIONAL_STAGE_LOG_TASK_NAME_AR = "طلب التماس الدخول للمرحلة الوطنية";
    public static final String PETITION_REQUEST_NATIONAL_STAGE_LOG_TASK_NAME_EN = "Petition Request National Stage";
    public static final String APPLICANT_CUSTOMER_CODE = "APPLICANT_CUSTOMER_CODE";

    @Autowired
    public ApplicationServiceImpl(CustomerServiceFeignClient customerServiceFeignClient, ApplicationAcceleratedService applicationAcceleratedService,
                                  @Lazy PaymentCallBackService paymentService, @Lazy ApplicationSupportServicesTypeService applicationSupportServicesTypeService,
                                  @Lazy IndustrialDesignDetailService industrialDesignDetailService,
                                  ClassificationService classificationService, ApplicationInfoRepository applicationInfoRepository,
                                  @Lazy PatentDetailsService patentDetailsService, PaymentFeeCostFeignClient paymentFeeCostFeignClient,
                                  ApplicationRelevantRepository applicationRelevantRepository, ApplicationAgentService applicationAgentService,
                                  DocumentRepository documentRepository, ApplicationRelevantTypeRepository applicationRelevantTypeRepository,
                                  ApplicationInfoMapper requestMapper, DocumentsService documentsService, @Lazy ApplicationUserService applicationUserService, ApplicationClassificationMapper applicationClassificationMapper,
                                  ApplicationRelvantMapper applicationRelvantMapper, LkApplicationCategoryRepository lkApplicationCategoryRepository,
                                  CustomerServiceCaller customerServiceCaller, BPMCallerFeignClient bpmCallerFeignClient, @Lazy ApplicationPriorityService applicationPriorityService,
                                  LkApplicationServiceRepository applicationServiceRepository, Properties properties, CustomerCodeValidator customerCodeValidator,
                                  BPMCallerServiceImpl bpmCallerService, @Lazy ApplicationNotesService applicationNotesService,
                                  LkApplicationStatusService applicationStatusService,
                                  @Lazy TrademarkDetailService trademarkDetailService, PctService pctService, ApplicationCheckingReportService reportService, ApplicationValidator applicationValidator, ApplicationStatusChangeLogService applicationStatusChangeLogService) {
        this.customerServiceFeignClient = customerServiceFeignClient;
        this.applicationAcceleratedService = applicationAcceleratedService;
        this.paymentService = paymentService;
        this.applicationSupportServicesTypeService = applicationSupportServicesTypeService;
        this.industrialDesignDetailService = industrialDesignDetailService;
        this.classificationService = classificationService;
        this.applicationInfoRepository = applicationInfoRepository;
        this.patentDetailsService = patentDetailsService;
        this.paymentFeeCostFeignClient = paymentFeeCostFeignClient;
        this.applicationAgentService = applicationAgentService;
        this.documentRepository = documentRepository;
        this.applicationRelevantTypeRepository = applicationRelevantTypeRepository;
        this.requestMapper = requestMapper;
        this.documentsService = documentsService;
        this.applicationUserService = applicationUserService;
        this.applicationClassificationMapper = applicationClassificationMapper;
        this.lkApplicationCategoryRepository = lkApplicationCategoryRepository;
        this.customerServiceCaller = customerServiceCaller;
        this.bpmCallerFeignClient = bpmCallerFeignClient;
        this.applicationPriorityService = applicationPriorityService;
        this.bpmCallerService = bpmCallerService;
        this.applicationNotesService = applicationNotesService;
        this.applicationStatusService = applicationStatusService;
        this.trademarkDetailService = trademarkDetailService;
        this.pctService = pctService;
        this.reportService = reportService;
        this.applicationValidator = applicationValidator;
        this.applicationStatusChangeLogService = applicationStatusChangeLogService;
        this.applicationRelevantTypeMapper = applicationRelevantTypeMapper;
    }


    @Override
    public ApplicantsDto listMainApplicant(Long appId) {
        ApplicationInfo applicationInfo = applicationInfoRepository.getReferenceById(appId);
        try {
            ApplicationRelevantType applicationRelevantTypes = applicationRelevantTypeRepository.getMainApplicant(applicationInfo.getId());
            List<String> customerCodes = new LinkedList<>();
            customerCodes.add(applicationRelevantTypes.getCustomerCode());

            if (Objects.isNull(customerCodes) || customerCodes.isEmpty()) {
                logger.info().message("There is no any applicants tha").log();
            } else {
                Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = getApplicantWithCustomerCodesInfo(customerCodes);


                CustomerSampleInfoDto customerSampleInfoDto = null;

                if (customerSampleInfoDtoMap.containsKey(applicationRelevantTypes.getCustomerCode().toLowerCase())) {
                    customerSampleInfoDto = customerSampleInfoDtoMap.get(applicationRelevantTypes.getCustomerCode().toLowerCase());
                    ApplicantsDto applicantsDto = new ApplicantsDto();
                    applicantsDto.setCustomerId(customerSampleInfoDto.getId());
                    applicantsDto.setNameAr(customerSampleInfoDto.getNameAr());
                    applicantsDto.setNameEn(customerSampleInfoDto.getNameEn());
                    applicantsDto.setEmail(customerSampleInfoDto.getEmail());
                    applicantsDto.setMobile(customerSampleInfoDto.getMobile());
                    applicantsDto.setAddress(customerSampleInfoDto.getAddress());
                    applicantsDto.setIdentifierTypeEnum(IdentifierTypeEnum.CUSTOMER_CODE);
                    applicantsDto.setIdentifier(customerSampleInfoDto.getCode());
                    applicantsDto.setUsername(applicationRelevantTypes.getCreatedByUser());
                    applicantsDto.setCustomerCode(applicationRelevantTypes.getCustomerCode());
                    applicantsDto.setUserGroupCode(customerSampleInfoDto.getUserGroupCode());
                    applicantsDto.setUserGroupAr(customerSampleInfoDto.getUserGroupAr());
                    applicantsDto.setUserGroupEn(customerSampleInfoDto.getUserGroupEn());
                    return applicantsDto;
                }


            }

            return null;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }

    }

    @Override
    @Transactional
    public void updateStatusByCodeAndId(Long id, String code) {
        LkApplicationCategory lkApplicationCategory = applicationInfoRepository.findCategoryByApplicationInfoId(id);
        Long lkApplicationCategoryId = lkApplicationCategory == null ? null : lkApplicationCategory.getId();
        applicationInfoRepository.updateApplicationStatusByStatusCode(id, code , lkApplicationCategoryId);
        applicationInfoRepository.updateLastStatusModifiedDate(id,LocalDateTime.now());

    }


    @Override
    public Long getUnPaidCountApplicationRelavantType(Long appId) {
        Long unPaidCountApplicationRelavantType = applicationRelevantTypeRepository.getUnPaidCountApplicationRelavantType(appId);
        ApplicationInfo appInfo = applicationInfoRepository.findById(appId).get();
        appInfo.setUnPaidApplicationReleventTypeCount(unPaidCountApplicationRelavantType);
        applicationInfoRepository.save(appInfo);
        return unPaidCountApplicationRelavantType;
    }

    @Override
    public List<ApplicantsDto> listApplicants(Long appId) {
        try {
            String ownerCustomerCode = null;
            List<ApplicantsDto> applicants = new ArrayList<>();
            AppCharacteristics appCharacteristics = applicationInfoRepository.getApplicationCategoryByAppId(appId);
            Map<String, List<ApplicantsDto>> mapCustomerCodeApplicants = new HashMap<>();
            List<ApplicantsDto> applicationRelevantTypes = applicationRelevantTypeRepository.retrieveApplicantInfosByAppId(appId);

            List<String> customerCodes = new LinkedList<>();
            if (Objects.isNull(applicationRelevantTypes) || applicationRelevantTypes.isEmpty()) {
                logger.info().message("This App contains No Any users yet").log();
            } else {
                mapCustomerCodeApplicants = applicationRelevantTypes.stream()
                        .filter(applicant -> Objects.nonNull(applicant.getCustomerCode()))
                        .collect(Collectors.groupingBy(
                                applicant -> applicant.getCustomerCode().toLowerCase()
                        ));
                ownerCustomerCode = applicationRelevantTypes.stream().filter(applicant -> applicant.getType().equals(Applicant_MAIN)).map(ApplicantsDto::getCustomerCode).findFirst().get();
                customerCodes = mapCustomerCodeApplicants.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.toList());
            }

            if (customerCodes.isEmpty()) {
                logger.info().message("There is no any applicants").log();
            } else {
                Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = getApplicantWithCustomerCodesInfo(customerCodes);
                List<ApplicantsDto> customerApplicants = processCustomerCodes(
                        customerCodes,
                        mapCustomerCodeApplicants,
                        customerSampleInfoDtoMap,
                        ownerCustomerCode,
                        appCharacteristics
                );
                applicants.addAll(customerApplicants);
            }
            return applicants;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    private List<ApplicantsDto> processCustomerCodes(
            List<String> customerCodes,
            Map<String, List<ApplicantsDto>> mapCustomerCodeApplicants,
            Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap,
            String ownerCustomerCode,
            AppCharacteristics appCharacteristics) {

        List<ApplicantsDto> customerApplicants = new ArrayList<>();

        for (String code : customerCodes) {
            String lowerCaseCode = code.toLowerCase();
            CustomerSampleInfoDto customerSampleInfoDto = customerSampleInfoDtoMap.get(lowerCaseCode);

            if (customerSampleInfoDto != null) {
                List<ApplicantsDto> applicantsList = mapCustomerCodeApplicants.get(lowerCaseCode);
                if (applicantsList != null) {
                    for (ApplicantsDto applicantsDto : applicantsList) {
                        updateApplicantsDto(applicantsDto, customerSampleInfoDto, code);
                        customerApplicants.add(applicantsDto);

                        if (isMainOwnerCustomer(code, ownerCustomerCode, appCharacteristics)) {
                            updateMainOwnerCustomer(applicantsDto, customerSampleInfoDto);
                        }
                    }
                }
            }
        }

        return customerApplicants;
    }

    private void updateApplicantsDto(ApplicantsDto applicantsDto, CustomerSampleInfoDto customerSampleInfoDto, String code) {
        applicantsDto.setUserGroupAr(customerSampleInfoDto.getUserGroupAr());
        applicantsDto.setUserGroupEn(customerSampleInfoDto.getUserGroupEn());
        applicantsDto.setEmail(customerSampleInfoDto.getEmail());
        applicantsDto.setMobile(customerSampleInfoDto.getMobile());
        applicantsDto.setIdentifierTypeEnum(IdentifierTypeEnum.CUSTOMER_CODE);
        applicantsDto.setIdentifier(customerSampleInfoDto.getCode());
        applicantsDto.setApplicantIdentifier(customerSampleInfoDto.getIdentifier());
        applicantsDto.setUserGroupCode(customerSampleInfoDto.getUserGroupCode());
        applicantsDto.setAddress(customerSampleInfoDto.getAddress());
        applicantsDto.setCountryDto(getCountryObject(customerSampleInfoDto));
        applicantsDto.setCustomerCode(code);
        applicantsDto.setNameAr(customerSampleInfoDto.getNameAr());
        applicantsDto.setNameEn(customerSampleInfoDto.getNameEn());
    }

    private boolean isMainOwnerCustomer(String code, String ownerCustomerCode, AppCharacteristics appCharacteristics) {
        return code.equals(ownerCustomerCode) && TRADEMARK.name().equals(appCharacteristics.getCategoryCode()) &&
                appCharacteristics.getApplicationNumber() != null;
    }

    @Override
    public List<ApplicationRelevantTypeDto> listIndustrailApplicants(Long appId,Integer isAttachDocument) {
        List<ApplicationRelevantType> applicants =null;
        if(isAttachDocument == null) {
          applicants = applicationRelevantTypeRepository.retrieveApplicantInfo(appId);
        }else{
           applicants = applicationRelevantTypeRepository.retrieveApplicantWithAttachmentsInfo(appId);
        }
        List<ApplicationRelevantTypeDto> applicationRelevantTypeDtoList = applicationRelevantTypeMapper.map(applicants);
        assignDataOfCustomersCode( applicationRelevantTypeDtoList);
        return applicationRelevantTypeDtoList;

    }

    @Override
    public String getProcessRequestTypeCodeByApplicationId(Long applicationId) {
        return ApplicationCategoryEnum.valueOf(applicationInfoRepository.findCategoryByApplicationId(applicationId)).getProcessTypeCode();

    }

    private static CountryDto getCountryObject(CustomerSampleInfoDto agentsDto) {
        if (agentsDto.getNationality() != null) {
            return agentsDto.getNationality();
        }

        if (agentsDto.getAddress() != null) {
            return agentsDto.getAddress().getCountryObject();
        }

        return null;
    }

    private Map<String, CustomerSampleInfoDto> getApplicantWithCustomerCodesInfo(List<String> customerCodes) {
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(customerCodes);
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto);
        return customerSampleInfoDtoMap;
    }


    private void updateMainOwnerCustomer( ApplicantsDto applicantsDto, CustomerSampleInfoDto customerSampleInfoDto) {
        applicantsDto.setNameAr(applicantsDto.getOwnerNameAr() == null ? customerSampleInfoDto.getNameAr() : applicantsDto.getOwnerNameAr());
        applicantsDto.setNameEn(applicantsDto.getOwnerNameEn() == null ? customerSampleInfoDto.getNameAr() : applicantsDto.getOwnerNameEn());
        applicantsDto.setOwnerAddressAr(applicantsDto.getOwnerAddressAr() == null ? customerSampleInfoDto.getAddress().getPlaceOfResidenceAr() : applicantsDto.getOwnerAddressAr());
        applicantsDto.setOwnerAddressEn(applicantsDto.getOwnerAddressEn() == null ? customerSampleInfoDto.getAddress().getPlaceOfResidenceEn() :applicantsDto.getOwnerAddressEn());
    }

    @Override
    @Transactional
    public Long saveApplicationPCT(ApplicantsRequestDto applicantsRequestDto) {
        Long id = saveApplication(applicantsRequestDto);
        if(applicantsRequestDto.getPltDocument() != null && applicantsRequestDto.getPltDocument().getId() != null && id != null){
            documentsService.linkApplicationToDocument(applicantsRequestDto.getPltDocument().getId(), id);
        }
        if(((applicantsRequestDto.getPltRegisteration() == null || !applicantsRequestDto.getPltRegisteration()) && applicantsRequestDto.getAppId() == null )
                || (applicantsRequestDto.getPctRequestDto() != null && applicantsRequestDto.getPctRequestDto().getId() != null)) {
            applicantsRequestDto.getPctRequestDto().setApplicationId(id);
            pctService.createPct(applicantsRequestDto.getPctRequestDto());
        }
        return id;
    }

    private void handleTrademarkApplicationCustomers(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        deleteCurrentApplicationCustomers(applicationInfo.getId());
        if (applicantsRequestDto.isByHimself()) {
            handleCreateTrademarkByApplicant(applicantsRequestDto, applicationInfo);
        } else {
            handleCreateTrademarkByAgent(applicantsRequestDto, applicationInfo);
        }
    }

    private void handleCreateTrademarkByApplicant(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        String applicantCustomerCode = Utilities.getCustomerCodeFromHeaders();
        if (applicantsRequestDto.getCustomerCode().equalsIgnoreCase(applicantCustomerCode)) {
            ApplicationCustomerData owner = ApplicationCustomerData.builder()
                    .applicationInfo(applicationInfo)
                    .customerType(ApplicationCustomerType.MAIN_OWNER)
                    .customerId(applicantsRequestDto.getCustomerId())
                    .customerCode(applicantCustomerCode)
                    .customerAccessLevel(CustomerApplicationAccessLevel.FULL_ACCESS)
                    .build();
            applicationCustomerService.insert(new ApplicationCustomer(owner));
        }
    }

    private void handleCreateTrademarkByAgent(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        String agentCustomerCode = Utilities.getCustomerCodeFromHeaders();
        List<TrademarkAgencyRequest> activeAgenciesByAgentCode = trademarkAgencyRequestService.getActiveAgenciesByAgentAndClientCodes(agentCustomerCode, applicantsRequestDto.getCustomerCode(), TrademarkAgencyType.TRADEMARK_REGISTRATION);
        CustomerSampleInfoDto customerSampleInfoDto = getCustomerInfoByCustomerCode(applicantsRequestDto.getCustomerCode());

        ApplicationCustomerData owner = ApplicationCustomerData.builder()
                .applicationInfo(applicationInfo)
                .customerType(ApplicationCustomerType.MAIN_OWNER)
                .customerId(customerSampleInfoDto.getId())
                .customerCode(applicantsRequestDto.getCustomerCode())
                .customerAccessLevel(CustomerApplicationAccessLevel.FULL_ACCESS)
                .build();

        ApplicationCustomerData agent = ApplicationCustomerData.builder()
                .applicationInfo(applicationInfo)
                .customerType(ApplicationCustomerType.AGENT)
                .customerId(applicantsRequestDto.getCustomerId())
                .customerCode(agentCustomerCode)
                .customerAccessLevel(CustomerApplicationAccessLevel.FULL_ACCESS)
                .trademarkAgencyRequest(activeAgenciesByAgentCode.get(0))
                .build();

        List<ApplicationCustomer> ApplicationCustomers = List.of(new ApplicationCustomer(owner), new ApplicationCustomer(agent));
        applicationCustomerService.saveAll(ApplicationCustomers);
    }

    private CustomerSampleInfoDto getCustomerInfoByCustomerCode(String customerCode) {
        CustomerCodeListDto dto = new CustomerCodeListDto();
        dto.setCustomerCode(List.of(customerCode));
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getCustomerByListOfCode(dto).getPayload().get(0);
        return customerSampleInfoDto;
    }


    private void deleteCurrentApplicationCustomers(Long newAppId) {
        applicationCustomerService.deleteByApplicationId(newAppId);
    }


    @Override
    public PaginationDto getTrademarksByCustomerCode(RequestTrademarkDetailsDto requestTrademarkDetailsDto, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("modifiedDate").descending());
        List<String> trademarkTypesList = gettrademarkTypesList();
        List<Long> applicationIds = Utilities.isLong(requestTrademarkDetailsDto.getTrademarkNumbers());
        Page<TrademarkDetailsProjection> trademarkDetails = applicationInfoRepository.getTrademarksByCustomerCode(requestTrademarkDetailsDto.getTrademarkOwnersCustomerCode(),
                requestTrademarkDetailsDto.getTrademarkNumbers(), requestTrademarkDetailsDto.getFilterTrademarkNumber(), Applicant_MAIN,
                THE_TRADEMARK_IS_REGISTERED.name(), TRADEMARK.name(), Constants.DOCUMENT_TYPE, trademarkTypesList, applicationIds, pageable);
        return PaginationDto.builder()
                .content(trademarkDetails.getContent())
                .totalElements(trademarkDetails.getTotalElements())
                .totalPages(trademarkDetails.getTotalPages())
                .build();
    }

    private static List<String> gettrademarkTypesList() {
        List<String> trademarkTypesList = new ArrayList<>();
        trademarkTypesList.add(TrademarkType.VERBAL.name());
        trademarkTypesList.add(TrademarkType.VISUAL.name());
        trademarkTypesList.add(TrademarkType.VISUAL_VERBAL.name());
        return trademarkTypesList;
    }

    @CheckCustomerAccess
    public ApplicationInfoDto getApplication(Long id) {
        try {
            Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(id);
            if (!applicationInfo.isPresent()) {
                throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST, HttpStatus.BAD_REQUEST, null);
            }
            ApplicationInfoDto result = requestMapper.mapEntityToRequest(applicationInfo.get());
            result.setApplicationAcceleratedDetails(result.getApplicationAccelerated());
            ApplicationAccelerated applicationAccelerated = applicationInfo.get().getApplicationAccelerated();
            result.setAccelerated(applicationAccelerated != null && (applicationAccelerated.getRefused() == null || !applicationAccelerated.getRefused()));
            List<Long> nationalCountryIds = new ArrayList<>();
            List<ApplicationRelevantTypeDto> applicationRelevantTypes = result.getApplicationRelevantTypes();
            String applicantCustomerCode = bpmCallerFeignClient.getProcessVarByName(id, APPLICANT_CUSTOMER_CODE ,result.getCategory().getSaipCode());
            if (!applicationRelevantTypes.isEmpty()) {
                List<ApplicationRelevantTypeDto> inventors = applicationRelevantTypes.stream()
                        .filter(applicationRelevantTypeDto -> applicationRelevantTypeDto.getIsDeleted() == 0 &&
                                applicationRelevantTypeDto.isInventor())
                        .collect(Collectors.toList());
                List<ApplicationRelevantTypeDto> filteredApplicationRelevantTypes = applicationRelevantTypes.stream()
                        .filter(applicationRelevantTypeDto -> applicationRelevantTypeDto.getIsDeleted() == 0 &&
                                !applicationRelevantTypeDto.getType().equals(ApplicationRelevantEnum.INVENTOR))
                        .collect(Collectors.toList());

                result.setInventors(inventors);
                result.setApplicationRelevantTypes(filteredApplicationRelevantTypes);

                nationalCountryIds = filteredApplicationRelevantTypes.stream()
                        .map(ApplicationRelevantTypeDto::getApplicationRelevant)
                        .filter(Objects::nonNull)
                        .map(ApplicationRelevantDto::getNationalCountryId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }

            if (Objects.nonNull(result.getApplicationPriority()) && !result.getApplicationPriority().isEmpty()) {
                List<ApplicationPriorityResponseDto> filteredApplicationPriority = result.getApplicationPriority().stream()
                        .filter(applicationPriorityDto -> applicationPriorityDto.getIsDeleted() == 0)
                        .collect(Collectors.toList());
                setCountryNames(filteredApplicationPriority);
                result.setApplicationPriority(filteredApplicationPriority);
            }

            List<ApplicationNiceClassification> classifications = applicationNiceClassificationRepository.getByApplicationId(id);
            if (Objects.nonNull(classifications)) {
                List<ClassificationDto> classificationDto = classificationMapper.mapApplicationNiceClassificationtoClassificationDto(classifications);
                result.setClassifications(classificationDto);
            }


            if (Objects.nonNull(result.getDocuments()) && !result.getDocuments().isEmpty()) {
                List<DocumentDto> filteredDocuments = result.getDocuments().stream()
                        .filter(documentDto -> documentDto.getIsDeleted() == 0)
                        .collect(Collectors.toList());
                result.setDocuments(filteredDocuments);
            }

            if (Objects.nonNull(result.getFilingDate())) {
                LocalDateTime expiry = result.getFilingDate();
                expiry = expiry.plus(applicationPriorityService.getPriorityDocumentsAllowanceDays(), ChronoUnit.DAYS);
                DurationDto duration = Utilities.calculateDuration(LocalDateTime.now(), expiry);
                result.setValidDurationToUpdate(duration);


                // add validDurationToCompleteData
                if(Objects.nonNull(result.getCategory()) && result.getCategory().getSaipCode().equals("INDUSTRIAL_DESIGN")) {
                    String valueAttach = bpmCallerFeignClient.getProcessVarByName(result.getId(), "INDUSTRIAL_ATTACH_FILE",ApplicationCategoryEnum.valueOf(result.getCategory().getSaipCode()).getProcessTypeCode());
                    Duration durationAttach = Duration.parse(valueAttach);
                    LocalDateTime expirationDate = result.getFilingDate().plus(durationAttach);
                    DurationDto remainingDuration = Utilities.calculateRemainingDuration(LocalDateTime.now(), expirationDate);
                    result.setValidDurationToCompleteData(remainingDuration);
                }
            }
            /*
             * disable add priority UI button if any condition match:
             * 1- any status except under formal process
             * 2- allowance days expired
             * 3- has confirmed priority before
             * */
            if (result.getCategory().getSaipCode().equals(PATENT.name())) {
                if (shouldNotAddPriority(result))
                {
                    result.setCanAddPriority(false);
                } else if (!shouldNotAddPriority(result))
                    result.setCanAddPriority(isAssigneeMatchingCustomerCode(applicantCustomerCode));

                if(result.getPltRegisteration()== null || !result.getPltRegisteration())
                    result.setPctDto(pctService.findDTOByApplicationId(id));
            }

            assignDataOfCustomersCode(result.getApplicationRelevantTypes());
            assignDataOfCustomersCode(result.getInventors());
            getRelevantsNationalitiesByNationalCountryId(result.getApplicationRelevantTypes(), nationalCountryIds);
            getApplicationMainOwnerInfo(applicationInfo.get(), result.getApplicationRelevantTypes());
            Object userName = util.getFromBasicUserinfo("userName");
            String assignee = userName == null  ? null : (String) userName;
            log.info("the external logged in user is -> {}", assignee);
            customerServiceFeignClient.getCustomerCodeByCustomerId(result.getCreatedByCustomerId().toString()).getPayload();
            result.setTask(bpmCallerService.getTaskByRowIdAndUserName(result.getId(), Utilities.getCustomerCodeFromHeaders(), ApplicationCategoryEnum.valueOf(result.getCategory().getSaipCode()).getProcessTypeCode()));
            checkAppealAcceptanceKey(result);
            result.setAllTasks(bpmCallerFeignClient.getTasksByRowIdAndAssignee(result.getId(), assignee));
            if (result.getApplicationNumber() != null) {
                RequestIdAndNotesDto requestIdAndNotesDto = bpmCallerService.getRequestIdAndLastNotes(result.getId(), result.getCategory().getSaipCode());
                result.setRequestNotes(requestIdAndNotesDto.getNotes());
                if (requestIdAndNotesDto.getId() != null)
                    result.setRequestId(requestIdAndNotesDto.getId().toString());
            }

            // find partial App
            result.setApplicationPartialList(getApplicationByApplicationPartialNumber(result.getApplicationNumber()));

            // find Application Main
            if(result.getPartialApplication() != null && result.getPartialApplication()){
                result.setApplicationMainDto(applicationInfoRepository.getApplicationMainByApplicationNumber(result.getPartialApplicationNumber()));
                Long requestId = bpmCallerFeignClient.findRequestIdByRowId(result.getApplicationMainDto().getId()).getPayload();
                result.getApplicationMainDto().setRequestId(requestId.toString());
            }

            return result;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }

    }

    private void checkAppealAcceptanceKey(ApplicationInfoDto result) {
        if (result == null || result.getTask() == null || result.getCategory() == null) {
            return;
        }

        String processTypeCode = ApplicationCategoryEnum.valueOf(result.getCategory().getSaipCode()).getProcessTypeCode();
        String appealAcceptedVar = bpmCallerFeignClient.getProcessVarByName(result.getId(), "isAppealAccepted", processTypeCode);

        result.getTask().setIsAppealAccepted(Boolean.parseBoolean(appealAcceptedVar));
    }

    private void setCountryNames(List<ApplicationPriorityResponseDto> applicationPriorityResponseDtos) {
        for (ApplicationPriorityResponseDto dto : applicationPriorityResponseDtos) {
            setCountryName(dto);
        }
    }

    private void setCountryName(ApplicationPriorityResponseDto applicationPriorityResponseDto){
        List<CountryDto> countryDtos = customerServiceFeignClient.getCountriesByIds(List.of(applicationPriorityResponseDto.getCountryId())).getPayload();
        if (!countryDtos.isEmpty()) {
            applicationPriorityResponseDto.setCountryNameEn(countryDtos.get(0).getIciCountryNameEn());
            applicationPriorityResponseDto.setCountryNameAr(countryDtos.get(0).getIciCountryNameAr());
        }
    }


    private boolean shouldNotAddPriority(ApplicationInfoDto result) {
        return applicationUserService.checkApplicationHasSpecificUserRole(result.getId(), ApplicationUserRoleEnum.EXAMINER) ||
                isNoValidUpdateDuration(result) ||
                result.getIsPriorityConfirmed() ||
                (result.getPltRegisteration() && result.getApplicationAccelerated() != null && result.getApplicationAccelerated().getFastTrackExamination()) ||
                isApplicationNotCompleted(result);
    }




    private boolean isNoValidUpdateDuration(ApplicationInfoDto result) {
        if (result == null || result.getValidDurationToUpdate() == null) {
            return false;
        }
        return result.getValidDurationToUpdate().getDays() == 0 && result.getValidDurationToUpdate().getHours() == 0;
    }

    private boolean isApplicationNotCompleted(ApplicationInfoDto result) {
        String statusCode = result.getApplicationStatus().getCode();
        return statusCode.equalsIgnoreCase(ApplicationStatusEnum.COMPLETE_REQUIREMENTS.name())
                || statusCode.equalsIgnoreCase(ApplicationStatusEnum.WAITING_FOR_APPLICATION_FEE_PAYMENT.name());
    }

    public ApplicationInfoDto getApplicationPatent(Long id){
        ApplicationInfoDto applicationInfoDto = getApplication(id);
        if(applicationInfoDto.getPltRegisteration() == null || !applicationInfoDto.getPltRegisteration())
            applicationInfoDto.setPctDto(pctService.findDTOByApplicationId(id));
        applicationInfoDto.setClassification((applicationInfoDto.getClassifications() != null && !applicationInfoDto.getClassifications().isEmpty()) ? applicationInfoDto.getClassifications().get(0) : null);
        return applicationInfoDto;
    }

    private void getApplicationMainOwnerInfo(ApplicationInfo applicationInfo, List<ApplicationRelevantTypeDto> applicationRelevantTypes) {
        if (applicationInfo.getCategory().getSaipCode().equals(TRADEMARK.name())) {
            Optional<ApplicationRelevantTypeDto> applicationRelevantType =
                    applicationRelevantTypes.stream().filter(ar -> ar.getType().equals(Applicant_MAIN)).findFirst();
            if (applicationRelevantType.isPresent() && applicationInfo.getApplicationNumber() != null) {
                ApplicationRelevantDto applicationRelevant = applicationRelevantType.get().getApplicationRelevant();
                applicationRelevant.setFullNameAr(applicationInfo.getOwnerNameAr());
                applicationRelevant.setFullNameEn(applicationInfo.getOwnerNameEn());
            }
        }
    }

    private void getRelevantsNationalitiesByNationalCountryId(List<ApplicationRelevantTypeDto> applicationRelevantTypes,
                                                              List<Long> nationalCountryIds) {
        if (nationalCountryIds.isEmpty() || applicationRelevantTypes.isEmpty())
            return;

        List<CountryDto> nationalities = customerServiceCaller.
                getCountriesByIds(nationalCountryIds).getPayload();

        List<ApplicationRelevantDto> applicationRelevants = applicationRelevantTypes.stream()
                .map(ApplicationRelevantTypeDto::getApplicationRelevant)
                .filter(Objects::nonNull)
                .toList();

        applicationRelevants.forEach(applicationRelevantDto -> {
            Optional<CountryDto> countryDto = nationalities.stream().filter(country ->
                    country.getId().equals(applicationRelevantDto.getNationalCountryId())
            ).findAny();
            countryDto.ifPresent(applicationRelevantDto::setCountry);

        });
    }


    @Override
    public List<Long> getApplicationProcessInstanceIds(String query, LocalDate fromFilingDate, LocalDate toFilingDate, String applicationNumber) {
        List<Long> processRequestIdsApplication = applicationInfoRepository.getApplicationProcessInstanceIds(query, fromFilingDate, toFilingDate, applicationNumber);
        List<Long> processRequestIdsSupportServices = applicationSupportServicesTypeService.getProcessRequestIdsBySearchCriteria(query, applicationNumber);
        List<Long> processRequestIdsByAgencyRequestNumber =  new ArrayList<>();
        if (query == null){
            processRequestIdsByAgencyRequestNumber = trademarkAgencyRequestService.getProcessRequestIdsByAgencyRequestNumber(applicationNumber, fromFilingDate, toFilingDate);
        }
        return mergeLists(processRequestIdsApplication, processRequestIdsSupportServices, processRequestIdsByAgencyRequestNumber);
    }

    private List<Long> mergeLists(List<Long> processRequestIdsApplication, List<Long> processRequestIdsSupportServices, List<Long> processRequestIdsByAgencyRequestNumber){
        Set<Long> processRequestIds = new HashSet<>();
        if(processRequestIdsApplication != null){
            processRequestIds.addAll(processRequestIdsApplication);
        }
        if(processRequestIdsSupportServices != null){
            processRequestIds.addAll(processRequestIdsSupportServices);
        }
        if(processRequestIdsByAgencyRequestNumber != null){
            processRequestIds.addAll(processRequestIdsByAgencyRequestNumber);
        }
        return processRequestIds.isEmpty() ? null : new ArrayList<>(processRequestIds);
    }

    @Override
    @Transactional
    public Long addInventor(InventorRequestsDto dto) {
        return applicationRelevantTypeService.addInventorPatch(dto);
    }

    @Override
    @Transactional
    public void deleteAppRelvant(Long relvantTypeId) {
        try {
            applicationRelevantTypeRepository.deleteRelvant(relvantTypeId);
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Transactional
    @Override
    public void deleteByApplicationId(Long appId) {
        ApplicationInfo applicationInfo = findById(appId);
        String saipCode = applicationInfo.getCategory().getSaipCode();
        switch (saipCode) {
            case "TRADEMARK" -> {
                TrademarkDetail trademarkDetail = trademarkDetailService.getByApplicationId(appId);
                if (Objects.nonNull(trademarkDetail)) {
                    trademarkDetail.setIsDeleted(1);
                    trademarkDetailService.saveAll(List.of(trademarkDetail));
                }
            }
            case "PATENT" -> {
                PatentDetails patentDetails = patentDetailsService.findPatentDetailsByApplicationId(appId);
                if (Objects.nonNull(patentDetails)) {
                    patentDetails.setIsDeleted(1);
                    patentDetailsService.saveAll(List.of(patentDetails));
                }
            }
            case "INDUSTRIAL_DESIGN" -> {
                IndustrialDesignDetail industrialDesignDetail = industrialDesignDetailService.getByApplicationId(appId);
                if (Objects.nonNull(industrialDesignDetail)) {
                    industrialDesignDetail.setIsDeleted(1);
                    industrialDesignDetailService.saveAll(List.of(industrialDesignDetail));
                }
            }
            case "INTEGRATED_CIRCUITS" ->
                integratedCircuitService.deleteByAppId(appId);
            case "PLANT_VARIETIES" ->
                plantVarietyService.deleteByAppId(appId);

        }
        applicationInfoRepository.deleteAppById(appId);
    }


    @Transactional
    @Override
    public Long updateApplicationMainData(ApplicationDataUpdateDto dto, Long id) {
        try {
            ApplicationInfo applicationInfoExisting = findById(id);
            super.setApplicantInfo(id, applicationInfoExisting);
            applicationInfoExisting.setTitleAr(dto.getTitleAr());
            applicationInfoExisting.setTitleEn(dto.getTitleEn());
            applicationInfoExisting.setIpcNumber(dto.getIpcNumber());
            applicationInfoExisting.setPartialApplication(dto.getPartialApplication());
            applicationInfoExisting.setNationalSecurity(dto.getNationalSecurity());
            if (dto.getPartialApplication()) {
                applicationInfoExisting.setPartialApplicationNumber(dto.getPartialApplicationNumber());
            }
            return applicationInfoRepository.save(applicationInfoExisting).getId();
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }


    @Override
    @Transactional
    public Object updateSubstantiveExamination(Boolean substantiveExamination, Long id) {
        try {
            applicationInfoRepository.updateSubstantiveExamination(substantiveExamination, id);
            return id;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    @Transactional
    public Object updateAccelerated(Boolean accelerated, Long id) {
        try {
            applicationInfoRepository.updateAccelerated(accelerated, id);
            return id;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }



    @Override
    public PaginationDto<List<ApplicationListDto>> getApplicationListByApplicationCategoryAndUserId(
            String applicationCategory,
            Long userId, Integer page, Integer limit, ApplicationListSortingColumns sortingColumn, Sort.Direction sortDirection, AdvancedSearchDto advancedSearchDto) {
        try {
            Page<ApplicationListDto> applications = advancedSearchDto == null
                    ? getApplications(applicationCategory, userId, page, limit, sortingColumn, sortDirection)
                    : getApplicationsAdvanced(applicationCategory, userId, page, limit, sortingColumn, sortDirection, advancedSearchDto);
            // list of app apps with support services
            List<ApplicationListDto> applicationDtos =  Objects.nonNull(applications) ? applications.getContent() : null;
            if (Objects.isNull(applicationDtos) || applicationDtos.isEmpty()) {
                return null;
            }


            // TODO If Application Optimized we can Reduce the Complexity Class of fn(extractCustomerCode)  to O(N) instead of  O(N^2)
            // Extract customer codes
            //O(N^2)
//            for (ApplicationInfo applicationInfo : applicationInfos) {
//                String customerCode = extractCustomerCode(applicationInfo);
//                customerCodes.add(customerCode);
//            }
            List<ApplicationListDto> result = new ArrayList<>();
            List<String> customerCodes = new ArrayList<>();
            customerCodes= applicationDtos.stream().map(ApplicationListDto::getCustomerCode).collect(Collectors.toList());
            Map<String, CustomerSampleInfoDto> customerMap = getApplicantWithCustomerCodesInfo(customerCodes);
            // Build ApplicationListDto objects
            // O(N) // TODO -> Done
            String value = bpmCallerFeignClient.getRequestTypeConfigValue("GRACE_PERIOD_AFTER_EXAMINER_REJECTION_TM");
            Duration duration = Duration.parse(value);  // Parse the duration from value
            for (ApplicationListDto app : applicationDtos) {
                ApplicationListDto item = buildApplicationDto(app, customerMap);
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime modifiedDate = item.getModifiedDate();
                // Calculate the difference between now and modifiedDate
                Duration difference = Duration.between(modifiedDate, now);
                String status = item.getApplicationStatus();
                // Check if the difference is less than or equal to the specified duration
                if (!difference.isNegative() && difference.compareTo(duration) <= 0 && "OBJECTIVE_REJECTION".equals(status)) {
                    item.setValidToAppeal(true);
                } else {
                    item.setValidToAppeal(false);
                }
                result.add(item);
            }

            result = collectApplicationTasks(result); // O(N)

            return PaginationDto.<List<ApplicationListDto>>builder()
                    .totalElements(applications.getTotalElements())
                    .content(result)
                    .totalPages(applications.getTotalPages())
                    .build();
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    private String extractCustomerCode(ApplicationInfo applicationInfo) {
        return applicationInfo.getApplicationRelevantTypes()
                .stream()
                .filter(applicationRelevantType -> Applicant_MAIN.equals(applicationRelevantType.getType()))
                .map(ApplicationRelevantType::getCustomerCode)
                .findFirst()
                .orElse(null);
    }

    @Override
    public ApplicationListDto createApplicationListDtoByApplicationInfo(ApplicationInfo applicationInfo) {
        String code = extractCustomerCode(applicationInfo);
        Map<String, CustomerSampleInfoDto> customerMap = getApplicantWithCustomerCodesInfo(List.of(code));
        return buildApplicationListDto(applicationInfo, customerMap);
    }

    private ApplicationListDto buildApplicationListDto(ApplicationInfo applicationInfo,
                                                       Map<String, CustomerSampleInfoDto> customerMap) {
        String customerCode = extractCustomerCode(applicationInfo);
        ApplicationListDto item = new ApplicationListDto();
        item.setId(applicationInfo.getId());
        item.setCustomerCode(customerCode);
        item.setApplicationNumber(applicationInfo.getApplicationNumber());
        item.setTitleEn(applicationInfo.getTitleEn());
        item.setTitleAr(applicationInfo.getTitleAr());
        item.setFilingDate(applicationInfo.getFilingDate());
        item.setPartialApplication(applicationInfo.getPartialApplication());
        item.setIpsStatusDescEn(applicationInfo.getApplicationStatus().getIpsStatusDescEn());
        item.setCategoryCode(applicationInfo.getCategory().getSaipCode());
        item.setIpsStatusDescAr(applicationInfo.getApplicationStatus().getIpsStatusDescAr());
        item.setApplicationStatus(applicationInfo.getApplicationStatus().getCode());
        item.setGrantNumber(applicationInfo.getGrantNumber());
        item.setApplicationRequestNumber(applicationInfo.getApplicationRequestNumber());
        item.setCustomerSampleInfoDto(
                item.getCustomerCode() != null ? customerMap.get(item.getCustomerCode().toLowerCase()) : null
        );


        LocalDateTime applicationInfoModifiedDate = applicationInfo.getModifiedDate();
        Duration duration;

        if (applicationInfoModifiedDate != null) {
            duration = Duration.between(applicationInfoModifiedDate, LocalDateTime.now());
        } else {
            LocalDateTime applicationInfoCreatedDate = applicationInfo.getCreatedDate();
            duration = Duration.between(applicationInfoCreatedDate, LocalDateTime.now());
        }

        item.setUpdatedDaysAgo(duration.toHours() / 24);
        item.setUpdatedHoursAgo(duration.toHours() % 24);

        return item;
    }
    private ApplicationListDto buildApplicationDto(ApplicationListDto appDto,
                                                       Map<String, CustomerSampleInfoDto> customerMap) {
        appDto.setCustomerSampleInfoDto(
                appDto.getCustomerCode() != null ? customerMap.get(appDto.getCustomerCode().toLowerCase()) : null
        );

        LocalDateTime applicationInfoModifiedDate = appDto.getModifiedDate();
        Duration duration;

        if (applicationInfoModifiedDate != null) {
            duration = Duration.between(applicationInfoModifiedDate, LocalDateTime.now());
        } else {
            LocalDateTime applicationInfoCreatedDate = appDto.getCreatedDate();
            duration = Duration.between(applicationInfoCreatedDate, LocalDateTime.now());
        }

        appDto.setUpdatedDaysAgo(duration.toHours() / 24);
        appDto.setUpdatedHoursAgo(duration.toHours() % 24);

        return appDto;
    }

    private Page<ApplicationListDto> getApplications(String applicationCategory
            , Long userId, Integer page, Integer limit, ApplicationListSortingColumns sortingColumn, Sort.Direction sortDirection) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by( sortDirection , sortingColumn.name(), ApplicationListSortingColumns.id.name()));   // here the pagination
//        List<Long> userIds = new ArrayList<>();
        Long customerId = null;
        if (userId != null) {
//            userIds.add(userId);
            customerId = Utilities.getCustomerIdFromHeadersAsLong();
        }
            return applicationInfoRepository.OptimizedApplicationFiltering(applicationCategory
                    , customerId, pageable);

    }


    private Page<ApplicationListDto> getApplicationsAdvanced(String applicationCategory
            , Long userId, Integer page, Integer limit, ApplicationListSortingColumns sortingColumn, Sort.Direction sortDirection,AdvancedSearchDto advancedSearchDto) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by( sortDirection , sortingColumn.name(), ApplicationListSortingColumns.id.name()));   // here the pagination
        List<Long> userIds = new ArrayList<>();
        Long customerId = null;
        if (userId != null) {
            userIds.add(userId);
            customerId = Utilities.getCustomerIdFromHeadersAsLong();
        }
        List<Long> agentsIds = new ArrayList<>() ;
        if(advancedSearchDto.getApplicantName() != null && !advancedSearchDto.getApplicantName().trim().isEmpty()) {
            agentsIds = applicationCustomerService.findAllAgentsByApplicationIdsId(customerId);

            agentsIds = customerClient.getCustomersByIdsAndName(agentsIds, advancedSearchDto.getApplicantName().trim());
//            if(agentsIds == null || agentsIds.isEmpty())
//                return new PageImpl<>(Collections.emptyList());
        }
        agentsIds.add(customerId);

        return applicationInfoRepository.OptimizedApplicationAdvancedFiltering(applicationCategory ,
                advancedSearchDto.getQuery(),
                advancedSearchDto.getStartDate(),
                advancedSearchDto.getEndDate(),
                advancedSearchDto.getApplicationNumber(),
                advancedSearchDto.getStatus(),
                advancedSearchDto.getIsAgent() == null  ? null : advancedSearchDto.getIsAgent() ? ApplicationCustomerType.AGENT : ApplicationCustomerType.MAIN_OWNER,
                advancedSearchDto.getApplicantName(),
                advancedSearchDto.getApplicationTitle(),
                agentsIds ,
                advancedSearchDto.getStatusesIds() == null ? null :advancedSearchDto.getStatusesIds(),
                pageable);

    }





    @Override
    public PaginationDto listApplicationsAgentId(Long categoryId, String applicationNumber, String appStatus, Long customerId, String customerCode, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        List<String> cats = List.of(PATENT.name(), INDUSTRIAL_DESIGN.name());
        Long applicationId = Utilities.isLong(applicationNumber);
        Page<ApplicationInfo> applicationInfoPages = applicationInfoRepository.listApplicationsAgentId(categoryId, applicationNumber, appStatus, customerId, ApplicationStatusPreconditions.AGENT_PRE_CONDITIONS.getStatusList(),
                customerCode, ApplicationAgentStatus.ACTIVE, cats, applicationId, pageable);
        List<ApplicationInfoListDto> apps = requestMapper.mapAppInfoToAppInfoListDto(applicationInfoPages.getContent());
        return PaginationDto.builder().
                totalElements(applicationInfoPages.getTotalElements()).
                content(apps).
                totalPages(applicationInfoPages.getTotalPages()).
                build();
    }



    // O(N) -> number of tasks exists in camounda
    private List<ApplicationListDto> collectApplicationTasks(List<ApplicationListDto> results) {
        List<Long> ids = results.stream().map(app -> app.getId()).collect(Collectors.toList()); //O(N)
        List<ApplicationListDto> applicationTasks = new ArrayList<>();
        Map<Long, RequestTasksDto> tasksMap = new HashMap<>();
        List<RequestTasksDto> tasks = bpmCallerService.getTaskByRowsIds(ids);   //o(n)
        if (tasks == null || tasks.isEmpty())
            return results;

        Object userName = util.getFromBasicUserinfo("userName");
        String userNameStr = userName == null  ? null : (String) userName;
        String customerCode = Utilities.getCustomerCodeFromHeaders();
        boolean isTaskAssignedToCustomerCodeOrUserName;
        for (RequestTasksDto task : tasks) {
            if(Objects.nonNull(task.getAssignee())) {
                isTaskAssignedToCustomerCodeOrUserName = task.getAssignee().equals(customerCode) || task.getAssignee().equals(userNameStr);
                if (task.getAssignee() != null && isTaskAssignedToCustomerCodeOrUserName) {
                    tasksMap.put(task.getRowId(), task);
                }
            }
        }
        for (ApplicationListDto result : results) {       // O(M)
            result.setTask(tasksMap.get(result.getId()));
            applicationTasks.add(result);
        }


        return applicationTasks;
    }


    @Override
    @Transactional
    public Long generateApplicationNumber(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long id) {
        try {
            logger.info().field("methodName", "generateApplicationNumber")
                    .field("paymentDate", applicationNumberGenerationDto.getPaymentDate())
                    .field("serviceCode", applicationNumberGenerationDto.getServiceCode())
                    .field("mainRequestType", applicationNumberGenerationDto.getMainRequestType()).log();
            paymentService.paymentCallBack(applicationNumberGenerationDto, id);
            return id;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Async
    @Override
    public void updateApplicationNumberForAllOldData(String serviceCode, String saipCode) {
        applicationInfoRepository.findByCategorySaipCodeOrderById(saipCode).forEach(app -> {
            String applicationNumber = getServiceByStringCode(saipCode).getApplicationNumberWithUniqueSequence(app.getCreatedDate(), serviceCode, app);
            app.setApplicationNumber(applicationNumber);
            applicationInfoRepository.save(app);
        });
    }

    private void updateGrantNumberWithUniqueSequence(ApplicationInfo applicationInfo, Long index) {
        String applicationNumber = createSequenceForGrantNumber(applicationInfo.getCategory(), index);
        applicationInfo.setGrantNumber(applicationNumber);

        if (applicationInfo.getGrantDate() == null) {
            applicationInfo.setGrantDate(LocalDateTime.now());
        }

        if (applicationInfo.getGrantDateHijri() == null) {
            applicationInfo.setGrantDateHijri(Utilities.convertDateFromGregorianToHijri(LocalDate.now()));
        }

        updateCertificateDocument(applicationInfo.getId());

        applicationInfoRepository.save(applicationInfo);
    }

    public void updateCertificateDocument(Long applicationId) {
        List<ApplicationCertificateDocument> applicationCertificateDocuments =
                applicationCertificateDocumentService.getDocumentsByApplicationId(applicationId);
        if (!applicationCertificateDocuments.isEmpty()) {
            ApplicationCertificateDocument lastDocument =
                    applicationCertificateDocuments.get(applicationCertificateDocuments.size() - 1);
            applicationCertificateDocumentService.deleteById(lastDocument.getId());
            entityManager.flush();
        }

        applicationCertificateDocumentService.generateAndSaveDocument(applicationId);
    }


    private String createSequenceForGrantNumber(LkApplicationCategory category, Long index) {
        String abbreviation = category.getAbbreviation();
        Long applicationsCount = applicationInfoRepository.countByGrantNumber(category.getId());
        String sequenceNumber = Utilities.getPadded4Number(index != null ? index : applicationsCount + 1);
        String sequence = abbreviation + " " + sequenceNumber+"B";
        validateGrantNumberDoesntExist(sequence);
        return sequence;
    }


    private void validateGrantNumberDoesntExist(String sequence) {
        if (applicationInfoRepository.GrantNumberExist(sequence)) {
            log.error("application number exists ==> sequence = {} ", sequence);
            throw new BusinessException("APPLICATION_NUMBER_EXISTS", HttpStatus.BAD_REQUEST, new String[]{sequence});
        }
        log.info("application number is not exists ==> sequence = {} ", sequence);
    }

    @Override
    public Long saveApplicationInfoEntity(ApplicationInfo applicationInfo) {
        return applicationInfoRepository.save(applicationInfo).getId();
    }

    public void assignDataOfCustomersCode(List<ApplicationRelevantTypeDto> applicationRelevantTypes) {
        LinkedList<String> customerCodeList = new LinkedList<>();
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        if (applicationRelevantTypes != null)
            for (ApplicationRelevantTypeDto applicationRelevantTypeDto : applicationRelevantTypes) {
                if (Objects.nonNull(applicationRelevantTypeDto.getCustomerCode())) {
                    customerCodeList.add(applicationRelevantTypeDto.getCustomerCode());
                }
            }
        if (CollectionUtils.isEmpty(customerCodeList)) {
            return;
        }
        customerCodeListDto.setCustomerCode(customerCodeList);
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto);
        for (ApplicationRelevantTypeDto applicationRelevantTypeDto : applicationRelevantTypes) {
            if (Objects.nonNull(applicationRelevantTypeDto.getCustomerCode())) {
                ApplicationRelevantDto dto = new ApplicationRelevantDto();
                dto.setFullNameAr(customerSampleInfoDtoMap.get(applicationRelevantTypeDto.getCustomerCode().toLowerCase()).getNameAr());
                dto.setFullNameEn(customerSampleInfoDtoMap.get(applicationRelevantTypeDto.getCustomerCode().toLowerCase()).getNameEn());
                dto.setCountry(getCountryObject(customerSampleInfoDtoMap.get(applicationRelevantTypeDto.getCustomerCode().toLowerCase())));
                dto.setTypeEn(customerSampleInfoDtoMap.get(applicationRelevantTypeDto.getCustomerCode().toLowerCase()).getTypeEn());
                dto.setTypeAr(customerSampleInfoDtoMap.get(applicationRelevantTypeDto.getCustomerCode().toLowerCase()).getTypeAr());
                applicationRelevantTypeDto.setApplicationRelevant(dto);
            }
        }
    }


    public void getDataOfCustomersByCode(List<ApplicationRelevantTypeLightDto> applicationRelevantTypeLightDtos) {
        if (applicationRelevantTypeLightDtos == null || applicationRelevantTypeLightDtos.isEmpty()) {
            return;
        }

        LinkedList<String> customerCodeList = new LinkedList<>();

        for (ApplicationRelevantTypeLightDto applicationRelevantTypeDto : applicationRelevantTypeLightDtos) {
            if (Objects.nonNull(applicationRelevantTypeDto) && Objects.nonNull(applicationRelevantTypeDto.getCustomerCode())) {
                customerCodeList.add(applicationRelevantTypeDto.getCustomerCode());
            }
        }

        if (CollectionUtils.isEmpty(customerCodeList)) {
            return ;
        }
        
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(customerCodeList);
        
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = null;
        try {
        	customerSampleInfoDtoMap = customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto);
        } catch (Exception e) {
        	log.error("Error getting customers map by list of customer codes", e);
        	return;
        }
        
        for (ApplicationRelevantTypeLightDto applicationRelevantTypeDto : applicationRelevantTypeLightDtos) {
            if ( Objects.nonNull(applicationRelevantTypeDto) && Objects.nonNull(applicationRelevantTypeDto.getCustomerCode())) {
            	try {
	                ApplicationRelevantLightDto dto = new ApplicationRelevantLightDto();
	                dto.setFullNameAr(customerSampleInfoDtoMap.get(applicationRelevantTypeDto.getCustomerCode().toLowerCase()).getNameAr());
	                dto.setFullNameEn(customerSampleInfoDtoMap.get(applicationRelevantTypeDto.getCustomerCode().toLowerCase()).getNameEn());
	                applicationRelevantTypeDto.setApplicationRelevant(dto);
            	} catch (Exception e) {
            		log.error("Error getting info for customer with code " + applicationRelevantTypeDto.getCustomerCode(), e);
            	}
            }
        }
    }

    @Override
    public ApplicationReportDetailsDto getApplicationReportInfo(Long appId) {
        List<ApplicantsDto> applicants = listApplicants(appId);
        ApplicationReportDetailsDto applicationReportDetailsDto = ApplicationReportDetailsDto.builder()


                .applicants(applicants)
                .mainApplicant(getMainApplicantFromApplicants(applicants))
                .secondaryApplicant(getSecondaryApplicantsFromApplicants(applicants))
                .applicationAgent(getCurrentAgentsInfoByApplicationId(appId))
                .build();
        return applicationReportDetailsDto;
    }

    private List<ApplicantsDto> getSecondaryApplicantsFromApplicants(List<ApplicantsDto> applicants) {
        List<ApplicantsDto> secondaryApplicants = new ArrayList<>();
        for (ApplicantsDto dto : applicants) {
            if (dto.getType().equals(Applicant_SECONDARY)) {
                secondaryApplicants.add(dto);
            }
        }
        return secondaryApplicants;
    }

    private ApplicantsDto getMainApplicantFromApplicants(List<ApplicantsDto> applicants) {

        for (ApplicantsDto dto : applicants) {
            if (dto.getType().equals(Applicant_MAIN)) {
                return dto;
            }
        }
        String[] params = {};
        throw new BusinessException(APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, params);
    }


    //Michael get application info payment
    @Override
    @Transactional
    public ApplicationInfoPaymentDto getApplicationInfoPayment(Long id) {

        ApplicationInfo applicationInfo = applicationInfoRepository.findById(id).get();

        Integer claimDocumentsPages = applicationInfo.getClaimPagesNumber();
        Integer shapesNumber = applicationInfo.getShapesNumber();
        Integer normalDocumentsPages = applicationInfo.getNormalPagesNumber();

        if (claimDocumentsPages == null) {
            claimDocumentsPages = documentRepository.calculateSumOfDocumentPages(id, "claims");
        }
        if (shapesNumber == null) {
            shapesNumber = documentRepository.calculateSumOfDocumentPages(id, "shape");
        }
        if (normalDocumentsPages == null) {
            normalDocumentsPages = documentRepository.calculateSumOfNormalDocumentPages(id);
        }


        claimDocumentsPages = (claimDocumentsPages == null) ? 0 : claimDocumentsPages;
        shapesNumber = (shapesNumber == null) ? 0 : shapesNumber;
        normalDocumentsPages = (normalDocumentsPages == null) ? 0 : normalDocumentsPages;


        applicationInfo.setClaimPagesNumber(claimDocumentsPages);
        applicationInfo.setShapesNumber(shapesNumber);
        applicationInfo.setNormalPagesNumber(normalDocumentsPages);

        applicationInfo = applicationInfoRepository.save(applicationInfo);

        ApplicationInfoPaymentDto result = new ApplicationInfoPaymentDto();
        result.setClaimPagesNumber(applicationInfo.getClaimPagesNumber());
        result.setShapesNumber(applicationInfo.getShapesNumber());
        result.setNormalPagesNumber(applicationInfo.getNormalPagesNumber());
        result.setTotalCheckingFee(applicationInfo.getTotalCheckingFee());

        result.setParentElementsCount(applicationInfo.getParentElementsCount());
        result.setChildrenElementsCount(applicationInfo.getChildrenElementsCount());
        result.setTotalPagesNumber(applicationInfo.getTotalPagesNumber());
        result.setUnPaidApplicationReleventTypeCount(applicationInfo.getUnPaidApplicationReleventTypeCount());


        return result;
    }

    @Override
    public ApplicationInfoPaymentDto getApplicationPaymentPreparationInfo(Long id) {
        ApplicationInfo applicationInfo = findById(id);
        return requestMapper.mapApplicationInfoPayment(applicationInfo);
    }

    public void checkPatentAndIndustrialPattern(String applicationNumber) throws BusinessException{
        if(applicationNumber != null && !(applicationNumber.startsWith("SA") || applicationNumber.startsWith("ID"))){
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_NUMBER_NOT_FOUND_FOR_THIS_CATEGORY, HttpStatus.BAD_REQUEST, null);
        }
    }

    public Long getAppIdByAppNumber(String applicationNumber) throws BusinessException{
        Long applicationId = null;
        if(applicationNumber != null){
            applicationId = applicationInfoRepository.getAppIdByAppNumber(applicationNumber);
            if(applicationId == null){
                throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST, HttpStatus.BAD_REQUEST, null);
            }
        }
        return applicationId;
    }

    public Long getApplicationAgentCountByAppId(Long applicationId){
        return applicationInfoRepository.getApplicationAgentCountByAppId(applicationId);
    }

    public void checkIfApplicationAgentExists(Long applicationId) throws BusinessException{
        if(applicationId != null){
            Long appAgentCount = this.getApplicationAgentCountByAppId(applicationId);
            if(appAgentCount > 0){
                throw new BusinessException(Constants.ErrorKeys.APPLICATION_ALREADY_ASSIGNED_ON_AGENT, HttpStatus.BAD_REQUEST, null);
            }
        }
    }

    @Override
    public PaginationDto getNotAssignedToAgentApplications(Long categoryId, String applicationNumber, String appStatus, String customerCode, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

        this.checkPatentAndIndustrialPattern(applicationNumber);
        Long applicationId = this.getAppIdByAppNumber(applicationNumber);
        this.checkIfApplicationAgentExists(applicationId);

        List<String> cats = List.of(PATENT.name(), INDUSTRIAL_DESIGN.name());
        Page<ApplicationInfo> applicationInfoPages = applicationInfoRepository.getNotAssignedToAgentApplications(categoryId, applicationNumber, appStatus, ApplicationStatusPreconditions.AGENT_PRE_CONDITIONS.getStatusList(), customerCode, cats, applicationId, pageable);
        List<ApplicationInfoListDto> apps = requestMapper.mapAppInfoToAppInfoListDto(applicationInfoPages.getContent());
        return PaginationDto.builder().
                totalElements(applicationInfoPages.getTotalElements()).
                content(apps).
                totalPages(applicationInfoPages.getTotalPages()).
                build();
    }

    @Override
    public PaginationDto getAssignedToAgentApplications(Long categoryId, String applicationNumber, String appStatus, String customerCode, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        List<String> cats = List.of(PATENT.name(), INDUSTRIAL_DESIGN.name());
        Long applicationId = Utilities.isLong(applicationNumber);
        Page<ApplicationInfo> applicationInfoPages = applicationInfoRepository.getAssignedToAgentApplications(categoryId, applicationNumber, appStatus, ApplicationStatusPreconditions.AGENT_PRE_CONDITIONS.getStatusList(), customerCode, cats, applicationId, pageable);
        List<ApplicationInfoListDto> apps = requestMapper.mapAppInfoToAppInfoListDto(applicationInfoPages.getContent());
        filterAgentApplication(apps);
        return PaginationDto.builder().
                totalElements(applicationInfoPages.getTotalElements()).
                content(apps).
                totalPages(applicationInfoPages.getTotalPages()).
                build();
    }

    private void filterAgentApplication(List<ApplicationInfoListDto> applicationInfoListDtoList) {
        applicationInfoListDtoList.removeIf(applicationInfo ->
                applicationSupportServicesTypeService.getLastSupportServiceByTypeAndApplicationِAndStatus(
                        SupportServiceType.AGENT_SUBSTITUTION,
                        applicationInfo.getId(),
                        List.of(SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name())
                ) != null
        );
    }

    @Override
    public List<ApplicationInfo> getAllApplicationsByCustomerCodeAndAgentId(String customerCode, Long agentId) {
        return applicationInfoRepository.getAllApplicationsByUserIdAndAgentId(agentId, customerCode, ApplicationAgentStatus.ACTIVE);
    }

    @Override
    public ApplicationInfoPaymentDto getApplicationPayment(Long applicationId) {
        ApplicationInfo applicationInfo = findById(applicationId);
        return ApplicationInfoPaymentDto
                .builder()
                .normalPagesNumber(applicationInfo.getNormalPagesNumber())
                .claimPagesNumber(applicationInfo.getClaimPagesNumber())
                .shapesNumber(applicationInfo.getShapesNumber())
                .totalCheckingFee(applicationInfo.getTotalCheckingFee())
                .parentElementsCount(applicationInfo.getParentElementsCount())
                .childrenElementsCount(applicationInfo.getChildrenElementsCount())
                .totalPagesNumber(applicationInfo.getTotalPagesNumber())
                .unPaidApplicationReleventTypeCount(applicationInfo.getUnPaidApplicationReleventTypeCount())
                .build();
    }

    @Override
    @Transactional
    public Long submitApplicationInfoPayment(Long id, ApplicationInfoPaymentDto applicationInfoPaymentDto) {

        Integer claimDocumentsPages = applicationInfoPaymentDto.getClaimPagesNumber();
        Integer shapesNumber = applicationInfoPaymentDto.getShapesNumber();
        Integer normalDocumentsPages = applicationInfoPaymentDto.getNormalPagesNumber();
        Long totalCheckingFee = applicationInfoPaymentDto.getTotalCheckingFee();

        ApplicationInfo applicationInfo = applicationInfoRepository.findById(id).get();
        applicationInfo.setClaimPagesNumber(claimDocumentsPages);
        applicationInfo.setShapesNumber(shapesNumber);
        applicationInfo.setNormalPagesNumber(normalDocumentsPages);
        applicationInfo.setTotalCheckingFee(totalCheckingFee);
        applicationInfo.setParentElementsCount(applicationInfoPaymentDto.getParentElementsCount());
        applicationInfo.setChildrenElementsCount(applicationInfoPaymentDto.getChildrenElementsCount());
        applicationInfo.setUnPaidApplicationReleventTypeCount(applicationInfoPaymentDto.getUnPaidApplicationReleventTypeCount());
        applicationInfo.setTotalPagesNumber(applicationInfoPaymentDto.getTotalPagesNumber());
        applicationInfo = applicationInfoRepository.save(applicationInfo);
        return applicationInfo.getId();
    }

    @Override
    public ApplicationClassificationDto getApplicationClassificationById(Long id) {
        Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(id);
        if (!applicationInfo.isPresent()) {
            throw new BusinessException(APPLICATION_ID_NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        ApplicationClassificationDto result = applicationClassificationMapper.map(applicationInfo.get());
        return result;
    }


    private ApplicationNiceClassificationService applicationNiceClassificationService;

    @Autowired
    public void setApplicationNiceClassificationService(ApplicationNiceClassificationService applicationNiceClassificationService) {
        this.applicationNiceClassificationService = applicationNiceClassificationService;
    }

    @Transactional
    public ApplicationClassificationDto updateApplicationClassification(ApplicationClassificationDto applicationClassificationDto) {
        Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(applicationClassificationDto.getId());
        if (!applicationInfo.isPresent()) {
            throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST, HttpStatus.BAD_REQUEST, null);
        }

        ApplicationInfo dbAppInfo = applicationInfo.get();
        ApplicationInfo appInfo = applicationClassificationMapper.unMap(applicationClassificationDto);
        dbAppInfo.setClassificationNotes(applicationClassificationDto.getClassificationNotes());
        if(PATENT.name().equals(dbAppInfo.getCategory().getSaipCode())){
            Long unitId = applicationInfoRepository.getClassificationUnitByClassificationId(applicationClassificationDto.getClassificationIds().get(0));
            LkClassificationUnit classificationUnit = lkClassificationUnitRepository.findById(unitId).orElse(null);
            dbAppInfo.setClassificationUnit(classificationUnit);
        }
        else {
            dbAppInfo.setClassificationUnit(appInfo.getClassificationUnit());
        }


        if (appInfo.getNiceClassifications() != null || appInfo.getNiceClassifications().isEmpty()) {
            applicationNiceClassificationService.deleteAllByApplicationId(appInfo.getId());
            applicationNiceClassificationService.saveAll(appInfo.getNiceClassifications());
            appInfo.setNiceClassifications(null);
        }

        applicationInfoRepository.save(dbAppInfo);
        return applicationClassificationMapper.map(dbAppInfo);
    }

    @Override
    @Transactional
    public void updateExamination(ApplicationSubstantiveExaminationDto dto, Long applicationId) {
        ApplicationInfo entity = getReferenceById(applicationId);
        ApplicationInfo examination = requestMapper.mapToExamination(entity, dto);
        update(examination);
        dto.getApplicationNotes().forEach(appNote -> {
            appNote.setApplicationId(entity.getId());
            applicationNotesService.updateAppNote(appNote);
        });

    }

    @Override
    public ApplicationSubstantiveExaminationRetrieveDto getApplicationSubstantiveExamination(Long id) {
        if (id == null)
            throw new BusinessException(Constants.ErrorKeys.IDENTIFIER_REQUIRED, HttpStatus.BAD_REQUEST, null);

        Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(id);
        if (!applicationInfo.isPresent())
            throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST, HttpStatus.BAD_REQUEST, null);

        ApplicationSubstantiveExaminationRetrieveDto result = requestMapper.mapEntityToSubstantiveExaminationRequest(applicationInfo.get());
        result.setApplicants(listApplicants(id));
        return result;

    }

    public List<PartialApplicationInfoDto> getApplicationByApplicationPartialNumber(String partialNumber) {

        List<PartialApplicationInfoProjection> partialApplicationInfoProjections = applicationInfoRepository.
                getApplicationByApplicationPartialNumber(partialNumber);
        if (partialApplicationInfoProjections.isEmpty()) {
            return null;
        }
        return requestMapper.mapToPartialApplicationInfoList(partialApplicationInfoProjections);
    }

    public Long getApplicationByApplicationNumber(String partialNumber) {
        Long applicationId = Utilities.isLong(partialNumber);
        return applicationInfoRepository.getApplicationByApplicationNumber(partialNumber, applicationId);
    }

    public Long getApplicationIdByApplicationNumber(String applicationNumber) {
        return applicationInfoRepository.getApplicationByApplicationNumber(applicationNumber, null);
    }

    @Override
    public ApiResponse getUserRequestTypes() {
        return bpmCallerFeignClient.getUserRequestTypes();
    }

    @Override
    @CheckCustomerAccess(categoryCodeParamIndex = 1)
    public ApplicationInfoSummaryDto getApplicationSummary(Long appId, String categoryCode) {
        String typeName = DocumentTypeEnum.POA.toString();
        Long numberOfApplicants = 0L;
        ApplicationInfoDto applicationInfoDto = getApplication(appId);
        if (applicationInfoDto == null) {
            String[] param = {};
            throw new BusinessException(APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, param);
        }
        ApplicationInfoSummaryDto result = requestMapper.mapApplicationTSummary(applicationInfoDto);
        String applicantCustomerCode = bpmCallerFeignClient.getProcessVarByName(appId, APPLICANT_CUSTOMER_CODE ,result.getCategory().getSaipCode());

        //result.setCreatedByCustomerType(getCreatedByCutomerType(appId));
        //result.setCreatedByCustomerTypeNameEn(getCreatedByCutomerType(appId).nameEn);
        //result.setCreatedByCustomerTypeNameAr(getCreatedByCutomerType(appId).nameAr);

        List<ApplicantsDto> applicants = listApplicants(appId);

        if (applicants != null)
            result.setApplicants(applicants);
        handleMainAndPartialApplications(result);
        DocumentDto poaDocument = documentsService.findDocumentByApplicationIdAndDocumentType(appId, typeName);
        if (poaDocument != null)
            result.setPoaDocument(poaDocument);
        for (ApplicantsDto applicant : result.getApplicants()) {
            if (applicant.getType().equals(Applicant_SECONDARY))
                numberOfApplicants += 1L;

        }
        if (numberOfApplicants == 0L)
            result.setNumberOfApplicants(0L);
        else
            result.setNumberOfApplicants(numberOfApplicants);

        result.setAgentSummary(applicationAgentFacadeService.getApplicationCurrentAgentSummary(appId));

        result.setHasPriority(applicationPriorityService.checkApplicationPriorities(appId));
        result.setHasLicencedSupportedService(applicationSupportServicesTypeLicencedExists(appId));
        result.setHasSupportServicesPeriorityEditOnlyExists(applicationSupportServicesTypePeriorityEditOnlyExists(appId));

        if (TRADEMARK.name().equals(applicationInfoDto.getCategory().getSaipCode())) {
            TradeMarkLightDto tradeMarkDetailsDto = trademarkDetailService.getTradeMarkLightDetails(appId);
            if (Objects.isNull(tradeMarkDetailsDto)) {
                tradeMarkDetailsDto = new TradeMarkLightDto();
                tradeMarkDetailsDto.setTrademarkImage(getTrademarkImage(appId));
            }
            result.setTradeMarkDetails(tradeMarkDetailsDto);
            List<ListApplicationClassificationDto> listApplicationClassifications = classificationService.listApplicationClassification(appId);

            result.setClassifications(listApplicationClassifications);
        }

        if (PATENT.name().equals(applicationInfoDto.getCategory().getSaipCode())) {
            isFastAllowed(appId, applicationInfoDto, result, applicantCustomerCode);
            isExtendDurationAllowed(applicationInfoDto, result, applicantCustomerCode);
            result.setLastProtectionDocumentNumber(certificateRequestService.getLastCertificateNumber(appId, CertificateTypeEnum.PATENT_ISSUE_CERTIFICATE));
            result.setPatentOpposition(isAssigneeMatchingCustomerCode(applicantCustomerCode) ? patentDetailsService.getPatentOpposition(appId) : false);
        }
        result.setModificationType(initialModificationRequestService.getApplicationSupportedServiceType(appId));
        result.setIsPLT(applicationInfoDto.getPltRegisteration());
        //if(result.getIsPLT() == null || !result.getIsPLT())
            //result.setIsFastTrackAllowed(Boolean.FALSE);

        if(result.getApplicationStatus().getCode().equals(WAIVED.name())){
            ApplicationCheckingReportDto legalDocumentsDto = reportService.getLastReportByReportType(appId, ReportsType.DroppedRequestReport);
            if(legalDocumentsDto==null) return result;
            DocumentDto documentDto = documentsService.findDocumentById(legalDocumentsDto.getDocumentId());
            result.setLegalDocuments(documentDto);
        }
        List<ApplicationCertificateDocumentDto> applicationCertificateDocumentDtos = applicationCertificateDocumentService.findByApplicationId(appId);
        if (applicationCertificateDocumentDtos != null || !applicationCertificateDocumentDtos.isEmpty()){
            result.setApplicationCertificateDocumentDtos(applicationCertificateDocumentDtos);
        }
        List<DocumentDto> documents = documentsService.getDocumentsByApplicationId(appId);
        result.setApplicationDocuments(filterReturnedDocuments(documents));
        String publicationType = Constants.MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE.get(applicationInfoDto.getCategory().getSaipCode());
        result.setLastPublicationSummary(applicationPublicationService.getPublicationSummary(appId, publicationType));
        return result;
    }
    private DocumentDto getTrademarkImage(Long appId) {
        String typeName = "Trademark Image/voice";
        DocumentDto documentDto = documentsService.findLatestDocumentByApplicationIdAndDocumentType(appId, typeName);

        return Objects.nonNull(documentDto)?documentDto:null;
    }
    public List<DocumentDto> filterReturnedDocuments(List<DocumentDto> documents) {
        List<DocumentDto> filteredDocuments = new ArrayList<>();

        for (DocumentDto document : documents) {
            if (!isExcludedDocumentType(document)) {
                filteredDocuments.add(document);
            }
        }
        return filteredDocuments;
    }

    private boolean isExcludedDocumentType(DocumentDto document) {
        LKDocumentTypeDto documentType = document.getDocumentType();
        String code = (documentType != null) ? documentType.getCode() : null;

        return EXCLUDED_DOCUMENT_TYPES_CODES.contains(code);
    }

    private boolean isAssigneeMatchingCustomerCode(String applicantCustomerCode) {
        return Objects.nonNull(applicantCustomerCode) && !applicantCustomerCode.isEmpty() && applicantCustomerCode.equalsIgnoreCase(Utilities.getCustomerCodeFromHeaders());
    }

    private void isExtendDurationAllowed(ApplicationInfoDto applicationInfoDto, ApplicationInfoSummaryDto result, String applicantCustomerCode) {
        boolean isValidStatus = Constants.VALIDATE_SUPPORT_SERVICE_REQUEST_STATUSES
                .contains(applicationInfoDto.getApplicationStatus().getCode());
        boolean isAssigneeMatch = isAssigneeMatchingCustomerCode(applicantCustomerCode);
        boolean isSamePhase = isExistingRequestInSamePhase(applicationInfoDto);

        if (Objects.isNull(result.getTask())) {
            result.setIsExtendDurationAllowed(isValidStatus && isAssigneeMatch && !isSamePhase);
            return;
        }

        OffsetDateTime dueDate = parseDueDate(result.getTask().getDue());
        OffsetDateTime currentDateTime = OffsetDateTime.now();

        boolean isBeforeDueDate = currentDateTime.isBefore(dueDate);
        result.setIsExtendDurationAllowed(isValidStatus && isBeforeDueDate && isAssigneeMatch && !isSamePhase);
    }

    private OffsetDateTime parseDueDate(String dueDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return OffsetDateTime.parse(dueDateString, formatter);
    }


    private boolean isExistingRequestInSamePhase(ApplicationInfoDto applicationInfoDto) {
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowId(applicationInfoDto.getId(), applicationInfoDto.getCategory().getSaipCode());
        Long serviceId = applicationSupportServicesTypeService.getLastSupportServiceByTypeAndApplicationِAndStatus(
                SupportServiceType.EXTENSION,
                applicationInfoDto.getId(),
                List.of(COMPLETED.name())
        );

        if (serviceId == null) {
            return false;
        }

        ExtensionRequest existingRequest = extensionRequestService.findById(serviceId);
        return existingRequest != null
                && existingRequest.getExtensionPhase() != null
                && Objects.nonNull(requestTasksDto)
                && existingRequest.getExtensionPhase().equalsIgnoreCase(requestTasksDto.getTaskDefinitionKey());
    }

    private void isFastAllowed(Long appId, ApplicationInfoDto applicationInfoDto, ApplicationInfoSummaryDto result, String applicantCustomerCode) {
        Optional<ApplicationAccelerated> applicationAcceleratedExists = applicationAcceleratedService.getByApplicationInfo(new ApplicationInfo(appId));
        if (applicationAcceleratedExists.isPresent()){
            return;
        }
        if(
                (applicationInfoDto.getPltRegisteration() != null && applicationInfoDto.getPltRegisteration())&&
            !applicationAcceleratedService.isApplicationInfoHasAcceleratedApplication(appId) &&
            !applicationUserService.checkApplicationHasSpecificUserRole(appId, ApplicationUserRoleEnum.EXAMINER) &&
             applicationUserService.checkApplicationHasSpecificUserRole(appId, ApplicationUserRoleEnum.CHECKER)&&
                        !applicationInfoDto.getApplicationStatus().getCode().equals(WAIVED.name())&&
                        !applicationInfoDto.getApplicationStatus().getCode().equals(FORMAL_REJECTION.name())

            ){
            ApplicationBillLightDTO billDetailsDto = paymentFeeCostFeignClient.getBillDetailsByApplicationIdAndMainRequestType(appId, FILE_NEW_APPLICATION.name()).getPayload();

            if (Objects.nonNull(billDetailsDto)) {
                if (billDetailsDto.getPaymentStatus().equals("PAID") && isAssigneeMatchingCustomerCode(applicantCustomerCode)){
                    result.setIsFastTrackAllowed(true);
                }
;
            }
        }

       else if (!applicationAcceleratedService.isApplicationInfoHasAcceleratedApplication(appId) &&
                !applicationUserService.checkApplicationHasSpecificUserRole(appId, ApplicationUserRoleEnum.EXAMINER)
        &&
           applicationUserService.checkApplicationHasSpecificUserRole(appId, ApplicationUserRoleEnum.CHECKER) &&
                !applicationInfoDto.getApplicationStatus().getCode().equals(WAIVED.name())&&
                !applicationInfoDto.getApplicationStatus().getCode().equals(FORMAL_REJECTION.name())
                && isAssigneeMatchingCustomerCode(applicantCustomerCode)) {
            result.setIsFastTrackAllowed(true);
        }
    }

    private void handleMainAndPartialApplications(ApplicationInfoSummaryDto result) {

        // Here partial application and I want to get main application
        if (result.getPartialApplicationNumber() != null) {
            result.setMainApplicationId(getApplicationByApplicationNumber(result.getPartialApplicationNumber()));
        }
        // Here main application and I want to get partial applications
        else {
            result.setApplicationPartialList(getApplicationByApplicationPartialNumber(result.getApplicationNumber()));
        }
    }

    @Override
    public Double getSupportServiceCost(String code, String applicantCategorySaipCode, String applicationCategorySaipCode) {

        List<LkFeeCostDto> lkFeeCost = paymentFeeCostFeignClient.findCost(code, applicantCategorySaipCode, applicationCategorySaipCode);

        if (lkFeeCost.isEmpty())
            return Double.valueOf(0);
        return lkFeeCost.get(0).getCost();
    }

    @Override
    @Transactional
    public void changeApplicationStatusId(Long id, String code) {
        Optional<ApplicationInfo> appInfo = applicationInfoRepository.findById(id);
        if (appInfo.isEmpty()) {
            return;
        }
        ApplicationInfo applicationInfo = appInfo.get();
        LkApplicationStatus applicationStatus = applicationStatusService.findByCodeAndApplicationCategory(code , applicationInfo.getCategory().getId());
        applicationInfo.setApplicationStatus(applicationStatus);
        applicationInfoRepository.save(applicationInfo);
        applicationInfoRepository.updateLastStatusModifiedDate(id,LocalDateTime.now());

    }

    @Override
    public void changeApplicationStatusesByApplicationIds(List<Long> applicationIds, Long categoryId) {
        String statusCode= MAP_CATEGORY_ID_TO_ACCEPTANCE_STATUS.get(categoryId);
        LkApplicationStatus lkApplicationStatus =
                applicationStatusService.findByCodeAndApplicationCategory(statusCode , categoryId);
        List<ApplicationInfo> applications = applicationInfoRepository.findByIds(applicationIds);

        for (ApplicationInfo application : applications) {
        	try {
	        	ApplicationPublication ap = applicationPublicationService.findByApplicationId(application.getId());
	        	if ("PATENT".equals(application.getCategory().getSaipCode()) && "PUBLICATION_A".equals(ap.getPublicationType().getCode())) {
	        		continue;
	        	}
	            application.setApplicationStatus(lkApplicationStatus);
	            applicationInfoRepository.updateLastStatusModifiedDate(application.getId(),LocalDateTime.now());
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }

        saveAll(applications);
    }

    @Override
    public List<ApplicationClassificationLightDto> getApplicationClassification(List<Long> ids) {
        List<ApplicationInfo> applications = applicationInfoRepository.getApplicationClassification(ids);
        List<ApplicationClassificationLightDto> applicationClassificationLightDtos =  requestMapper.mapClassLight(applications);
        for(ApplicationClassificationLightDto classificationLightDto : applicationClassificationLightDtos){
            List<ClassificationLightDto> classifications = applicationNiceClassificationService.getLightNiceClassificationsByAppId(classificationLightDto.getId());
            classificationLightDto.setClassifications(classifications);
        }
        return applicationClassificationLightDtos;
    }

    public Map<Long, ApplicationInfoTaskDto> getApplicationStatusByApplicationIds(List<Long> ids) {
        List<ApplicationInfo> applicationInfo = applicationInfoRepository.getApplicationStatusByApplicationIds(ids);
        List<ApplicationInfoTaskDto> appTaskData = requestMapper.mapApplicationInfoTaskDto(applicationInfo);
        Map<Long, ApplicationInfoTaskDto> appsMap = appTaskData.stream()
                .collect(Collectors.toMap(ApplicationInfoTaskDto::getId, Function.identity()));
        return appsMap;
    }

    @Override
    public List<Long> getApplicationClassificationUnitIdsByAppId(Long id) {
        List<Long> unitIds = applicationInfoRepository.getApplicationClassificationUnitIdsByAppId(id);
        if (unitIds.isEmpty())
            return new ArrayList<>();
        return unitIds;
    }

    @Override
    public boolean checkApplicationStatusIsNotInListOfStatus(Long appId, List<String> status) {
        return applicationInfoRepository.checkApplicationStatusIsNotInListOfStatus(appId, status);
    }

    public CustomerSampleInfoDto getCurrentAgentsInfoByApplicationId(Long appId) {
        Long agentsCustomerIds = applicationAgentService.getCurrentApplicationAgent(appId);

        if (agentsCustomerIds == null)
            return new CustomerSampleInfoDto();


        return customerServiceFeignClient.getCustomerByListOfById(agentsCustomerIds).getPayload();
    }

    @Override
    public ApplicationInfo checkMainApplicationExists(String applicationNumber, Long partialAppId) {
        /*
         * main application should not in any (accepted or rejected) status
         * https://tpms.saip.gov.sa/browse/IPV-105
         * 1- get main appId where status not in (DRAFT, THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED, FORMAL_REJECTION, WAIVED, ACCEPTANCE, REJECTION,
         * REVOKED_BY_COURT_ORDER, REVOKED_PURSUANT_TO_COURT_RULING, REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION)
         * 2- if null >> exception else >> check for owner
         * */
        List<String> notInStatusList = List.of(
                DRAFT.name(),
                THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED.name(),
                FORMAL_REJECTION.name(),
                WAIVED.name(),
                ACCEPTANCE.name(),
                THE_TRADEMARK_IS_REGISTERED.name(),
                REJECTION.name(),
                REVOKED_BY_COURT_ORDER.name(),
                REVOKED_PURSUANT_TO_COURT_RULING.name(),
                REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION.name()
        );
        String saipCode = PATENT.name() ;
        Optional<ApplicationInfo> partialApp = applicationInfoRepository.findById(partialAppId);
        if(partialApp.isPresent() && partialApp.get().getCategory() != null ){
            saipCode = partialApp.get().getCategory().getSaipCode();
        }
        ApplicationInfo mainApplicationInfo = applicationInfoRepository.getMainApplicationInfo(applicationNumber, notInStatusList);
        if (mainApplicationInfo == null || mainApplicationInfo.getId() == null) {
            throw new BusinessException(Constants.ErrorKeys.MAIN_APPLICATION_STATUS_NOT_VALID, HttpStatus.BAD_REQUEST, null);
        }

        List<ApplicationCustomer> appCustomersByIdsAndType = applicationCustomerService.getAppCustomersByIdsAndType(List.of(mainApplicationInfo.getId(), partialAppId), ApplicationCustomerType.MAIN_OWNER);
        if(!isMainAndPartialAppsHaveTheSameOwner(appCustomersByIdsAndType)){
            throw new BusinessException(Constants.ErrorKeys.MAIN_APPLICATION_STATUS_NOT_VALID, HttpStatus.BAD_REQUEST, null);
        }
        return mainApplicationInfo;
    }

    private static Boolean isMainAndPartialAppsHaveTheSameOwner(List<ApplicationCustomer> appCustomersByIdsAndType) {
        if (appCustomersByIdsAndType == null || appCustomersByIdsAndType.isEmpty() || appCustomersByIdsAndType.size() != 2) {
            return false;
        }

        return appCustomersByIdsAndType.get(0).getCustomerCode().equals(appCustomersByIdsAndType.get(1).getCustomerCode());
    }


    public List<ApplicationInfo> getApplicationsThatAreReadyToBeAddedToNextPublicationIssue(ApplicationCategoryEnum applicationCategoryEnum) {
        return applicationInfoRepository.getApplicationsThatAreReadyToBeAddedToAPublicationIssue(applicationCategoryEnum);
    }


    @Override
    @Transactional
    public void updateAppCommunicationDetails(AppCommunicationUpdateRequestsDto dto) {
        applicationInfoRepository.updateAppCommunicationDetails(dto.getId(), dto.getEmail(), dto.getMobileCode(), dto.getMobileNumber());
    }

    @Override
    public String getApplicationStatus(Long applicationId) {
        return applicationInfoRepository.getApplicationStatus(applicationId);
    }

    @Override
    public String getCreatedUserById(Long applicationId) {
        return applicationInfoRepository.getCreatedUserById(applicationId);
    }

    @Override
    public PaginationDto<List<AllApplicationsDtoLight>> getAllApplications(String appNumber, String appStatus, String appCategory, String query,
                                                                           Integer firstResult, Integer maxResult) {
        PageRequest page = PageRequest.of(firstResult, maxResult, Sort.by(Sort.Direction.DESC, "id"));
        Long applicationId = Utilities.isLong(appNumber);
        Page<AllApplicationsDto> paginatedApps = applicationInfoRepository
                .getAllApplicationsWithSearchCapabilities(appStatus, appCategory, appNumber, query, applicationId, page);
        List<AllApplicationsDto> applications = paginatedApps.getContent();
        List<String> applicationCustomerCodes = applications.stream()
                .map(AllApplicationsDto::getCustomerCode)
                .collect(Collectors.toList());
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = getApplicantWithCustomerCodesInfo(applicationCustomerCodes);
        for (AllApplicationsDto app : applications) {
            if (customerSampleInfoDtoMap.containsKey(app.getCustomerCode().toLowerCase())) {
                app.setMainApplicant(customerSampleInfoDtoMap.get(app.getCustomerCode().toLowerCase()));
                app.setTask(bpmCallerService.getTaskByRowIdAndTypeIfExists(RequestTypeEnum.valueOf(app.getCategory().getSaipCode()), app.getId()));
                app.setAllTasks(bpmCallerService.getTasksByRowId(app.getId()));
            }
        }
        setTrademarkImage(applications);
        List<AllApplicationsDtoLight> allApps = requestMapper.mapListOfEntityToAllApplicationsDto(applications);
        return PaginationDto.<List<AllApplicationsDtoLight>>builder()
                .content(allApps)
                .totalPages(paginatedApps.getTotalPages())
                .totalElements(paginatedApps.getTotalElements())
                .build();
    }

    private void setTrademarkImage(List<AllApplicationsDto> applications) {
        applications.stream().filter(app -> app.getCategory().getSaipCode().equals(trademarkCategory))
                .forEach(app->{
                    app.setTrademarkImage(documentsService.findLatestDocumentByApplicationIdAndDocumentType(app.getId(), "Trademark Image/voice"));
                });
    }


    @Override
    public PaginationDto<List<ApplicationInfoBaseDto>> getExaminerApplications(ApplicationCategoryEnum categoryCode, String userName, String search, String statusCode,
                                                                               int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        List<String> examinerStatuses = Constants.EXAMINER_STATUSES;
        Page<ApplicationInfoBaseDto> applications = applicationInfoRepository.
                findApplicationsByCategoryCodeAndApplicationStatus(categoryCode.name(), examinerStatuses, userName, search, statusCode, pageable);


        if (applications.getContent().isEmpty())
            return new PaginationDto<>();

        List<Long> appIds = applications.getContent()
                .stream()
                .map(ApplicationInfoBaseDto::getApplicationId)
                .toList();

        getApplicationsClassifications(applications.getContent(), appIds);

        return PaginationDto
                .<List<ApplicationInfoBaseDto>>builder()
                .content(applications.getContent())
                .totalElements(applications.getTotalElements())
                .totalPages(applications.getTotalPages())
                .build();
    }
    @Override
    public PaginationDto<List<ApplicationInfoBaseDto>> getExaminerApplicationsByUnitCode(ApplicationCategoryEnum categoryCode, List<String> unitCodes,
                                                                               int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        List<String> examinerStatuses = Constants.EXAMINER_STATUSES;
        Page<ApplicationInfoBaseDto> applications = applicationInfoRepository.
                findApplicationsByCategoryCodeAndApplicationStatusAndUnitCode(categoryCode.name(), examinerStatuses, unitCodes,null, pageable);


        if (applications.getContent().isEmpty())
            return new PaginationDto<>();

        List<Long> appIds = applications.getContent()
                .stream()
                .map(ApplicationInfoBaseDto::getApplicationId)
                .toList();

        getApplicationsClassifications(applications.getContent(), appIds);

        return PaginationDto
                .<List<ApplicationInfoBaseDto>>builder()
                .content(applications.getContent())
                .totalElements(applications.getTotalElements())
                .totalPages(applications.getTotalPages())
                .build();
    }

    public void getApplicationsClassifications(List<ApplicationInfoBaseDto> content, List<Long> appIds) {

        List<ApplicationNiceClassification> applicationNiceClassifications =
                applicationInfoRepository.getNiceClassification(appIds);

        if (!applicationNiceClassifications.isEmpty())
            content.forEach(applicationInfoBase -> {
                List<ApplicationNiceClassification> applicationNiceClassification =
                        applicationNiceClassifications.stream().filter(applicationNiceClassification1 ->
                                applicationNiceClassification1.getApplication().getId().equals(applicationInfoBase.getApplicationId())
                        ).toList();
                if (!applicationNiceClassification.isEmpty()) {
                    applicationInfoBase.setNiceClassifications(
                            classificationMapper.mapApplicationNiceClassificationtoClassificationDto(applicationNiceClassification));
                }
            });
    }

    @Override
    public void extendProtectionPeriodByAppId(Long id, Integer years) {
        ApplicationInfo applicationInfo = applicationInfoRepository.findById(id).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST, HttpStatus.BAD_REQUEST, null));
        LocalDateTime filingDate = applicationInfo.getEndOfProtectionDate();
        if (filingDate == null) {
            throw new BusinessException(Constants.ErrorKeys.APP_ID_DOESNT_EXIST, HttpStatus.BAD_REQUEST, null);
        }
        LocalDateTime newProtectionDate = filingDate.plusYears(years);
        applicationInfoRepository.extendProtectionPeriodByAppId(id, newProtectionDate);
    }

    @Override
    public String validateSupportServicePreConditions(Long appId, String supportServiceCode, Long parentServiceId) {
        ApplicationInfo app = findById(appId);
        return supportServiceValidator.validateSupportServicePreConditions(app, supportServiceCode, parentServiceId);
    }


    @Override
    public DepositReportDto findDepositCertificateDetailsById(Long aiId) {
        return applicationInfoRepository.findDepositCertificateDetailsById(aiId);
    }

    @Override
    public ApplicationReportInfo getApplicationReportData(Long certificateId) {
        return applicationInfoRepository.getApplicationReportData(certificateId).get(0);
    }

    @Override
    public CountApplications sumOfApplicationsAccordingToStatus(String saipCode, String customerCode) {
        return applicationInfoRepository.sumOfApplicationsAccordingToStatus(
                saipCode,
                customerCode,
                Constants.APPLICATIONS_UNDER_STUDY,
                Constants.GRANTED_APPLICATIONS,
                Constants.REFUSED_APPLICATIONS,
                Constants.NEW_APPLICATIONS
        );
    }

    @Transactional
    public void updateApplicationInfoEndOfProtectionDate(long appId, Long extensionPeriodInYears) {
        LocalDateTime currentExtensionPeriod = applicationInfoRepository.findEndOfProtectionDateById(appId);
        currentExtensionPeriod = currentExtensionPeriod != null ? currentExtensionPeriod : LocalDateTime.now();
        LocalDateTime newExtensionPeriod = currentExtensionPeriod.plusYears(extensionPeriodInYears);
        applicationInfoRepository.updateApplicationEndOfProtectionDate(newExtensionPeriod, appId);
    }

    @Override
    @Transactional
    public void updateApplicationsStatusByIds(List<Long> ids, LkApplicationStatus lkApplicationStatus) {
        applicationInfoRepository.updateApplicationsStatusByIds(ids, lkApplicationStatus);
    }

    @Autowired
    private ApplicationEditTrademarkImageRequestRepository trademarkImageRequestRepository;

    @Override
    public ApplicationListDto getApplicationListDtoByApplicationNumber(String applicationNumber, String supportServiceCode, String category) {
        validateAccessibilityForCurrentCustomerCode(applicationNumber, supportServiceCode);
        ApplicationInfo applicationInfo = null;
        ApplicationSupportServicesType licenceRequest = null;
        Long parentServiceId = null;
        if (isSupportServiceRequestNumber(applicationNumber)) {
            if (supportServiceCode.equals(REVOKE_LICENSE_REQUEST.name())) {
                licenceRequest = getLicenseRequestsByRequestNumberWithValidation(applicationNumber);
                licenceRequestRepository.getLienceRequestsByRquestNumber(applicationNumber);
                    applicationInfo = licenceRequest.getApplicationInfo();
                    parentServiceId = licenceRequest.getId();
            } else if (supportServiceCode.equals(RequestTypeEnum.TRADEMARK_APPEAL_REQUEST.name())) {
                licenceRequest = trademarkImageRequestRepository.getSupportServiceByRequestNumber(applicationNumber);
                applicationInfo = licenceRequest.getApplicationInfo();
                parentServiceId = licenceRequest.getId();
            }
        } else if (!isSupportServiceRequestNumber(applicationNumber) && supportServiceCode.equals(REVOKE_LICENSE_REQUEST.name())) {
            throw new BusinessException(Constants.ErrorKeys.CANT_SEARCH_FOR_THIS_SERVICE_WITH_APPLICATION_NUMBER_USE_LICESE_NUMBER, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (supportServiceCode.equals(REVOKE_LICENSE_REQUEST.name())){
        licenceRequest = getLicenseRequestsByRequestNumberWithValidation(applicationNumber);
        applicationInfo = licenceRequest.getApplicationInfo();
        LicenceRequest licenceReq = licenceRequestRepository.getLienceRequestsByRquestNumber(applicationNumber);
        if (applicationInfo != null && licenceReq != null) {
            ApplicationCustomerType createdByCustomerType = applicationInfo.getCreatedByCustomerType();
            ApplicantType applicantType = licenceReq.getApplicantType();
            if (ApplicationCustomerType.MAIN_OWNER.equals(createdByCustomerType) &&
                    ApplicantType.OWNERS_AGENT.equals(applicantType)) {
                throw new BusinessException(Constants.ErrorKeys.REVOKE_LICENSE_WITH_MAIN_OWNER_AND_AGENT, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
      } else if (isTMApplicationNumber(applicationNumber) && category.equals(TRADEMARK.name())
                && (supportServiceCode.equals(OPPOSITION_REQUEST.name()) || supportServiceCode.equals(TRADEMARK_APPEAL_REQUEST.name()))) {
            throw new BusinessException(Constants.ErrorKeys.CANT_SEARCH_FOR_THIS_SERVICE_WITH_APPLICATION_NUMBER_USE_REQUEST_NUMBER, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            applicationInfo = getApplicationInfoByApplicationNumberAndCategory(applicationNumber, category);
        }
        supportServiceValidator.validateSupportServicePreConditions(applicationInfo, supportServiceCode, parentServiceId);
        String customerCode = extractCustomerCode(applicationInfo);
        Map<String, CustomerSampleInfoDto> customerMap = customerCode != null ? getCustomerMap(Arrays.asList(customerCode)) : new HashMap<>();
        ApplicationListDto applicationListDto = buildApplicationListDto(applicationInfo, customerMap);
        if (licenceRequest != null) {
            applicationListDto.setLicenceRequestId(licenceRequest.getId());
            applicationListDto.setServiceId(licenceRequest.getId());
        }
        if (TRADEMARK.name().equals(applicationInfo.getCategory().getSaipCode())) {
            TradeMarkLightDto tradeMarkDetailsDto = trademarkDetailService.getTradeMarkLightDetails(applicationInfo.getId());
            applicationListDto.setTradeMarkDetails(tradeMarkDetailsDto);
        }
        return applicationListDto;
    }

    private void validateAccessibilityForCurrentCustomerCode(String applicationNumber, String supportServiceCode) {
        if(isAppliedByApplicationCustomers(supportServiceCode)){
            Long applicationId = getApplicationId(applicationNumber);
            applicationValidator.checkAccessForCustomerCode(applicationId);
        }
    }
    private Long getApplicationId(String applicationNumber) {
        Long applicationId = applicationInfoRepository.getApplicationInfoIdByApplicationNumber(applicationNumber);
        if(applicationId == null){ // support service applied on another support service
            applicationId = applicationSupportServicesTypeService.getApplicationIdByServiceNumber(applicationNumber);
        }
        try {
            return applicationId == null ? Long.parseLong(applicationNumber) : applicationId;
        }
        catch (NumberFormatException ex){
            throw new BusinessException(APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        }

    }

    private static boolean isAppliedByApplicationCustomers(String supportServiceCode) {
        return !NON_OWNER_SUPPORT_SERVICE_STRING_CODES.contains(supportServiceCode);
    }

    static boolean isSupportServiceRequestNumber(String applicationNumber) {
        return applicationNumber.startsWith("SS") || applicationNumber.startsWith("ss");
    }

    static boolean isTMApplicationNumber(String applicationNumber) {
        return !Strings.isNullOrEmpty(applicationNumber) && (applicationNumber.startsWith("TM") || applicationNumber.startsWith("tm"));
    }

    private LicenceRequest getLicenseRequestsByRequestNumberWithValidation(String requestNumber) {
        LicenceRequest licenceRequest = licenceRequestRepository.getLienceRequestsByRquestNumber(requestNumber);
        if (licenceRequest == null)
            throw new BusinessException(Constants.ErrorKeys.LICENSE_NUMBER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        if(licenceRequest.getToDate()!= null && LocalDateTime.now().isAfter(licenceRequest.getToDate()))
            throw new BusinessException(Constants.ErrorKeys.LICENSE_IS_EXPIRED, HttpStatus.INTERNAL_SERVER_ERROR);
        return licenceRequest;
    }

    private ApplicationInfo getApplicationInfoByApplicationNumberAndCategory(String applicationNumber, String category) {
        Long applicationId = Utilities.isLong(applicationNumber);
        ApplicationInfo applicationInfo = applicationInfoRepository.getApplicationInfoByApplicationNumberAndCategory(applicationNumber, category, applicationId);
        if (applicationInfo == null)
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_NUMBER_NOT_FOUND_FOR_THIS_CATEGORY, HttpStatus.NOT_FOUND);
        return applicationInfo;
    }


    @Override
    public ApplicationInfo getApplicationInfoByApplicationNumber(String applicationNumber) {
        Long applicationId = Utilities.isLong(applicationNumber);
        ApplicationInfo applicationInfo = applicationInfoRepository.getApplicationInfoByApplicationNumber(applicationNumber, applicationId);
        if (applicationInfo == null)
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, new String[]{applicationNumber});
        return applicationInfo;
    }

    private Map<String, CustomerSampleInfoDto> getCustomerMap(List<String> customerCodes) {
        CustomerCodeListDto customerCodeListDto = buildCustomerCodeListDto(customerCodes);
        Map<String, CustomerSampleInfoDto> customerMap = customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto);
        return customerMap;
    }

    private CustomerCodeListDto buildCustomerCodeListDto(List<String> customerCodes) {
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(customerCodes);
        return customerCodeListDto;
    }

    public PaginationDto<List<ApplicationInfoBaseDto>> findApplicationsBaseInfoDto(Long categoryId, List<String> appStatuses, String applicationNumber,
                                                                                   Long customerId, List<ApplicationCustomerType> customerTypes, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Long applicationId = Utilities.isLong(applicationNumber);
        Page<ApplicationInfo> applications = applicationInfoRepository.
                getApplicationListByApplicationCategoryAndUserIdsForSupportServices(null, categoryId, applicationNumber, null,
                        customerId, appStatuses, applicationId, customerTypes, pageable);

        if (applications.getContent().isEmpty())
            return new PaginationDto<>();

        List<ApplicationInfoBaseDto> applicationsBaseInfo = requestMapper.mapAppInfoToBaseInfoDto(applications.getContent());

        return PaginationDto
                .<List<ApplicationInfoBaseDto>>builder()
                .content(applicationsBaseInfo)
                .totalElements(applications.getTotalElements())
                .totalPages(applications.getTotalPages())
                .build();
    }


    @Override
    public ApplicationListDto getApplicationByNumberAndAgencyType(String applicationNumber, TrademarkAgencyType trademarkAgencyType) {
        ApplicationInfo application = getApplicationInfoByApplicationNumber(applicationNumber);
        trademarkAgencyRequestService.validateAgencySearchResult(application, trademarkAgencyType);
        return createApplicationListDtoByApplicationInfo(application);
    }

    @Override
    public void validateGivenDateIsNotPastApplicationEndOfProtectionDate(Long applicationId, LocalDateTime date) {
        boolean applicationEndOfProtectionDateISNotPastThisDate = applicationInfoRepository.isGivenDateNotPastApplicationEndOfProtectionDate(applicationId, date);
        if (!applicationEndOfProtectionDateISNotPastThisDate)
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_END_OF_PROTECTION_DATE_PAST, HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getMainApplicantCustomerCodeByApplicationInfoId(Long applicationId) {
        return applicationRelevantTypeRepository.getMainApplicantCustomerCodeByApplicationInfoId(applicationId);
    }

    @Override
    public void updateStatusAndProtectionDate(Long applicationId, ApplicationStatusEnum status, LocalDateTime endOfProtection) {
        ApplicationInfo applicationInfo = findById(applicationId);
        applicationStatusChangeLogService.changeApplicationStatusAndLog(status.name(), null, applicationId);
        applicationInfo.setEndOfProtectionDate(endOfProtection);
        update(applicationInfo);
    }

    @Override
    public Boolean checkPltRegister(Long applicationId) {
        return applicationInfoRepository.checkPltRegister(applicationId);
    }
    @Override
    public String checkApplicationCategory(Long applicationId) {
        return applicationInfoRepository.checkApplicationCategory(applicationId);
    }


    public void generateApplicationNumberPatent(Long applicationInfoId) {
        ApplicationInfo applicationInfo = applicationInfoRepository.findById(applicationInfoId).get();

        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setServiceCode("PATENTS_REGISTRATION");

        getServiceByStringCode(applicationInfo.getCategory().getSaipCode())
                .generateApplicationNumberAndUpdateApplicationInfoAfterPaymentCallback(applicationNumberGenerationDto, applicationInfo,null);
    }

    @Transactional
    @Async
    public void generateGrantNumberPatent(Long applicationInfoId) {
        Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(applicationInfoId);
        if(applicationInfo.isPresent())
            updateGrantNumberWithUniqueSequence(applicationInfo.get(),null);
    }

    @Override
    public void generateTrademarkApplicationNumber(Long applicationInfoId) {
        String applicationNumber;
        ApplicationInfo applicationInfo;
        Optional<ApplicationInfo> applicationInfoOptional = applicationInfoRepository.findById(applicationInfoId);
        if (applicationInfoOptional.isPresent()) {
            applicationInfo = applicationInfoOptional.get();
            if (applicationInfo.getApplicationNumber() != null && !applicationInfo.getApplicationNumber().isEmpty()) {
                return;
            }
            applicationNumber = getServiceByStringCode(applicationInfo.getCategory().getSaipCode()).getApplicationNumberWithUniqueSequence(applicationInfo.getFilingDate(), ApplicationServices.TRADEMARK_REGISTRATION.name(),
                    applicationInfo);
            applicationInfo.setApplicationNumber(applicationNumber);
            applicationInfoRepository.save(applicationInfo);
        }
    }

    @Override
    public void disableAddingApplicationPriority(Long applicationInfoId) {
        applicationInfoRepository.disableAddingApplicationPriority(applicationInfoId);
    }

    @Override
    public boolean checkIsPriorityConfirmed(Long applicationInfoId) {
        return applicationInfoRepository.checkIsPriorityConfirmed(applicationInfoId) ;
    }

    @Override
    public ApplicationInfoDto getGrantedApplicationDetailsByApplicationNumber(String applicationNumber) {
        ApplicationInfo applicationInfo = applicationInfoRepository.getGrantedApplicationDetailsByApplicationNumber(applicationNumber);
        if (applicationInfo == null
                || !applicationInfo.getApplicationStatus().getCode().equals(THE_TRADEMARK_IS_REGISTERED.name())) {
            throw new BusinessException(Constants.ErrorKeys.SIMILAR_APPLICATION_NOT_EXISTED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return requestMapper.mapEntityToRequest(applicationInfo);
    }
    @Override
    public List<PartialApplicationInfoProjection> listCustomerMainApplications(String applicationNumber, Long customerId, String category){
        if(Utilities.getCustomerIdFromHeadersAsLong() == null ){
            throw new BusinessException(Constants.ErrorKeys.IDENTIFIER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        return applicationInfoRepository.listCustomerMainApplications(applicationNumber, Utilities.getCustomerIdFromHeadersAsLong(), PATENT.name());
    }

    @Override
    public String getApplicationCategoryById(Long id) {
        return applicationInfoRepository.getApplicationCategoryById(id);
    }

    @Override
    @Transactional
    public void initiateExamination(Long id) {
        applicationStatusChangeLogService.changeApplicationStatusAndLog(UNDER_FORMAL_PROCESS.name(), null, id);
    }

    @Override
    @Transactional
    public Long updateApplicationInfo(Long appId, ApplicationRequestBodyDto requestDto) {
        Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(appId);
        if (!applicationInfo.isPresent())
            return null;
        ApplicationInfo entity = applicationInfo.get();
        entity.setNationalSecurity(requestDto.getNationalSecurity() == null ?
                entity.getNationalSecurity() : requestDto.getNationalSecurity());
        update(entity);
        return entity.getId();

    }

    @Override
    public Boolean getNationalSecurity(Long appId) {
        return applicationInfoRepository.findNationalSecurityByAppId(appId);
    }

    @Override
    public void updateFillingDatePartialApplication(Long id){
        ApplicationInfo applicationPartial = applicationInfoRepository.findById(id).get();
        if(applicationPartial.getPartialApplication() && applicationPartial.getPartialApplicationNumber() != null && !applicationPartial.getPartialApplicationNumber().isEmpty() ){
            ApplicationInfo applicationInfo = applicationInfoRepository.getApplicationInfoByApplicationNumber(applicationPartial.getPartialApplicationNumber().trim(), null);
            if(applicationInfo != null && applicationInfo.getFilingDate() != null) {
                applicationPartial.setFilingDate(applicationInfo.getFilingDate());
                applicationInfoRepository.save(applicationPartial);
            }
        }
    }

    @Override
    public List<ApplicationRelevantType> getApplicationRelevantTypes(Long appId) {
        return applicationRelevantTypeRepository.getApplicationRelevantTypes(appId);
    }

    @Override
    public String getProcessRequestIdById(Long applicationId) {
        return applicationInfoRepository.getProcessRequestIdById(applicationId);
    }

    @Override
    @Transactional
    public void updateAppTotalNumberOfPagesAndCalculateClaimPages(Long applicationId, Long totalNumberOfPages){
        applicationInfoRepository.updateAppTotalNumberOfPagesAndCalculateClaimPages(applicationId, totalNumberOfPages);
    }

    @Override
    public ApplicationAdditionalDetailsDto getApplicationAdditionalDetailsById(Long id) {
        String category = getApplicationCategoryById(id);
        CertificateTypeEnum certificateType = PATENT.name().equals(category) ? CertificateTypeEnum.PATENT_ISSUE_CERTIFICATE : CertificateTypeEnum.ISSUE_CERTIFICATE;
        String publicationType = MAP_CATEGORY_To_REGISTRATION_PUBLICATION_TYPE.get(category);
        return ApplicationAdditionalDetailsDto
                .builder()
                .lastProtectionDocumentNumber(certificateRequestService.getLastCertificateNumber(id, certificateType))
                .lastPublicationSummary(applicationPublicationService.getPublicationSummary(id, publicationType))
                .build();
    }

@Override
    public Long getApplicationUnitClassificationIdByApplicationId(Long applicationId){
        return applicationInfoRepository.getApplicationClassificationUnitIdByApplicationId(applicationId);

    }

    @Override
    public String getApplicationRequestNumber(Long id) {
        return applicationInfoRepository.getApplicationRequestNumber(id);
    }

    @Override
    public List<Long> getProcessesRequestsIdsByFilters(SearchDto searchDto) {
        String categoryCode = searchDto.getApplicationCategoryCode() == null ? null : searchDto.getApplicationCategoryCode().toString();
        if(categoryCode != null){
            return applicationInfoRepository.findProcessesRequestsIdsByFilters(searchDto.getApplicationNumber(), searchDto.getStatus(),
                    categoryCode, searchDto.getStartDate(), searchDto.getEndDate());
        }else{
            return applicationSupportServicesTypeService.findProcessesRequestsIdsByFilters(searchDto);
        }

    }

    @Override
    public ApplicationInfoBaseDto getAppBasicInfo(Long appId) {
        return applicationInfoRepository.findApplicationBasicInfo(appId);
    }
    @Override
    public ApplicationInfoBaseDto getAppBasicInfoIds(List<Long> appIds) {
        return applicationInfoRepository.findApplicationBasicInfoIds(appIds);
    }

    @Override
    public List<Long> findPatentApplicationWithNoPdfDocument(){
        return applicationInfoRepository.findPatentApplicationWithNoPdfDocument();
    }
    @Override
    public void generateTrademarkMissingApplicationNumbers() {
        List<ApplicationInfo> applicationInfoList = applicationInfoRepository.findByCategoryAndMissingApplicationNumber(TRADEMARK.name());
        if (!applicationInfoList.isEmpty()) {
            for (ApplicationInfo appInfo : applicationInfoList) {
                String applicationNumberWithUniqueSequence = getServiceByEnumCode(TRADEMARK).getApplicationNumberWithUniqueSequence(appInfo.getFilingDate(),
                        ApplicationServices.TRADEMARK_REGISTRATION.name(), appInfo);
                appInfo.setApplicationNumber(applicationNumberWithUniqueSequence);
                applicationInfoRepository.save(appInfo);
            }
        }
    }

    public boolean applicationSupportServicesTypeLicencedExists(Long appId){
        return applicationSupportServicesTypeService.applicationSupportServicesTypeLicencedExists(appId);
    }

    public  boolean applicationSupportServicesTypePeriorityEditOnlyExists(Long appId){
        return applicationPriorityRequestRepository.applicationSupportServicesTypePeriorityEditOnlyExists(appId);
    }

    @Override
    public void updateApplicationInfoEnterpriseSize(Long enterpriseSizeId, Long appId) {
        applicationInfoRepository.updateApplicationInfoEnterpriseSize(enterpriseSizeId , appId);
    }

    @Override
    @Transactional
    public void updateApplicationBatchByProcessRequestId(UpdateApplicationsStatusDto dto) {
        if (dto.getIds() == null || dto.getCategoryCode() == null || dto.getStatusCode() == null) {
            return;
        }
        applicationInfoRepository.updateApplicationBatchByProcessRequestId(dto.getIds(), dto.getStatusCode(), dto.getCategoryCode());
    }


    @Override
    protected void startApplicationProcessAfterSaveFileNewApplication(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        if (isPatentCategory(applicationInfo)) {
            if (isPct(applicantsRequestDto) && hasValidPetitionNumber(applicantsRequestDto)) {
                logPetitionRequestNationalStage(applicationInfo);
            }
            StartProcessResponseDto startProcessResponseDto = supportServiceProcess.startProcessConfig(applicationInfo);
            if (hasValidPetitionNumber(applicantsRequestDto)) {
                bpmCallerFeignClient.updateActivityLogRequestIdByTaskDefinitionKey(RequestActivityLogEnum.PETITION_REQUEST_NATIONAL_STAGE.name(),
                        startProcessResponseDto.getBusinessKey());
            }
            updateApplicationWithProcessRequestId(startProcessResponseDto, applicationInfo.getId());
        }
    }

    private boolean isPatentCategory(ApplicationInfo applicationInfo) {
        return ApplicationCategoryEnum.PATENT.name().equals(applicationInfo.getCategory().getSaipCode());
    }

    private boolean isPct(ApplicantsRequestDto applicantsRequestDto){
        return Objects.nonNull(applicantsRequestDto.getPctRequestDto());
    }

    private boolean hasValidPetitionNumber(ApplicantsRequestDto applicantsRequestDto) {
        return  isPct(applicantsRequestDto) && Objects.nonNull(applicantsRequestDto.getPctRequestDto().getPetitionNumber()) &&
                !applicantsRequestDto.getPctRequestDto().getPetitionNumber().isEmpty();
    }

    private void logPetitionRequestNationalStage(ApplicationInfo applicationInfo) {
        RequestActivityLogsDto requestActivityLogsDto = RequestActivityLogsDto.builder()
                .taskDefinitionKey(RequestActivityLogEnum.PETITION_REQUEST_NATIONAL_STAGE)
                .taskNameAr(PETITION_REQUEST_NATIONAL_STAGE_LOG_TASK_NAME_AR)
                .taskNameEn(PETITION_REQUEST_NATIONAL_STAGE_LOG_TASK_NAME_EN)
                .statusCode(SupportServiceRequestStatusEnum.COMPLETED.name())
                .assignee(applicationInfo.getCreatedByUser()).build();
        bpmCallerFeignClient.addActivityLog(requestActivityLogsDto);
    }

    @Override
    protected void handleApplicationCustomers(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        if (TRADEMARK.name().equals(applicationInfo.getCategory().getSaipCode())) { // handle trademark type
            handleTrademarkApplicationCustomers(applicantsRequestDto, applicationInfo);
            return;
        }
        addNewApplicationCustomers(applicantsRequestDto, applicationInfo.getId());
    }


    @Override
    protected Long updateApplicationCustomersAndPOA(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        if (TRADEMARK.name().equals(applicationInfo.getCategory().getSaipCode())) {
            handleTrademarkApplicationCustomers(applicantsRequestDto, applicationInfo);
            return applicationInfo.getId();
        }
        return super.updateApplicationCustomersAndPOA(applicantsRequestDto, applicationInfo);
    }


    @Override
    protected LkApplicationCategory getLkApplicationCategory(ApplicantsRequestDto applicantsRequestDto) {
        LkApplicationCategory lkApplicationCategory = lkApplicationCategoryRepository.findByApplicationCategoryDescEn(applicantsRequestDto.getCategoryKey()).get();
        return lkApplicationCategory;
    }

    @Override
    protected void setExtraDetailsBasedOnApplicationType(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo, LkApplicationCategory lkApplicationCategory) {
        if (applicationInfo.getId() == null && lkApplicationCategory.getSaipCode().equals(ApplicationCategoryEnum.PATENT.name())) {
            applicationInfo.setPltFilingDate(LocalDateTime.now());
            applicationInfo.setFilingDate(LocalDateTime.now());
            applicationInfo.setApplicationStatus(applicationStatusService.findByCodeAndApplicationCategory(ApplicationStatusEnum.COMPLETE_REQUIREMENTS.toString() , lkApplicationCategory.getId()));
            applicationInfo.setUnPaidApplicationReleventTypeCount( applicantsRequestDto.getRelevants() !=null && applicantsRequestDto.getRelevants().isEmpty() ? Long.valueOf((applicantsRequestDto.getRelevants().size())):0L);
        }
    }

    @Override
    public List<String> retrieveInventorRelatedToDesignSample(Long applicationRelevantTypeId) {
        return applicationRelevantTypeRepository.retrieveInventorRelatedToDesignSample(applicationRelevantTypeId);
    }

    @Override
    public boolean isCustomerApplicationApplicant(Long appId, Long customerId) {
        return applicationInfoRepository.existsByIdAndCreatedByCustomerId(appId, customerId);
    }

    @Transactional
    public void generateGrantNumberDateIC(Long applicationInfoId) {
        Optional<ApplicationInfo> applicationInfo = applicationInfoRepository.findById(applicationInfoId);
        if(applicationInfo.isPresent())
            updateGrantNumberWithUniqueSequence(applicationInfo.get(),null);
    }

    @Transactional
    @Override
    public void deleteLastUserModifiedDateValueByApplicationId(Long applicationId) {
        applicationInfoRepository.setApplicationLastUserModifiedDateWithNull(applicationId);
    }

    @Override
    public BillApplicationInfoAttributesDto getBillApplicationAttributes(Long applicationId) {
        return applicationInfoRepository.getBillApplicationAttributes(applicationId);
    }

    @Override
    public Double calculateServiceCost(CustomerSampleInfoDto customerSampleInfoDto, ApplicationPaymentMainRequestTypesEnum applicationPaymentMainRequestTypesEnum, ApplicationCategoryEnum applicationCategoryEnum) {
        return getSupportServiceCost(
                applicationPaymentMainRequestTypesEnum.name(),
                customerSampleInfoDto.getUserGroupCode().getValue(),
                applicationCategoryEnum.getSaipCode()
        );
    }

    @Override
    @Transactional
    public void updateApplicationByHimSelfAfterAddingAgent(List<Long> appIds) {
        applicationInfoRepository.updateApplicationWithHimSelfWhenAddAgent(appIds);

    }

    @Override
    public Optional<ApplicationInfo> findByApplicationNumber(String applicationNumber) {
        return applicationInfoRepository.findByApplicationNumber(applicationNumber);
    }

}
