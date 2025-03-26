package gov.saip.applicationservice.modules.patent.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.RequestTypeConfigCode;
import gov.saip.applicationservice.common.mapper.ApplicationClassificationMapper;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.mapper.ApplicationRelvantMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationServiceRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.impl.ApplicationServiceImpl;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.common.validators.CustomerCodeValidator;
import gov.saip.applicationservice.config.Properties;
import gov.saip.applicationservice.util.ApplicationValidator;
import gov.saip.applicationservice.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PatentApplicationService extends ApplicationServiceImpl {

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
    private final ApplicationRelevantRepository applicationRelevantRepository;
    private final ApplicationAgentService applicationAgentService;
    private final DocumentRepository documentRepository;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final ApplicationInfoMapper requestMapper;
    private final DocumentsService documentsService;
    private final ApplicationUserService applicationUserService;
    private final ApplicationClassificationMapper applicationClassificationMapper;
    private final ApplicationRelvantMapper applicationRelvantMapper;
    private final LkApplicationCategoryRepository lkApplicationCategoryRepository;
    private final CustomerServiceCaller customerServiceCaller;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationPriorityService applicationPriorityService;
    private final LkApplicationServiceRepository applicationServiceRepository;
    private final CustomerCodeValidator customerCodeValidator;
    private final BPMCallerServiceImpl bpmCallerService;
    private final ApplicationNotesService applicationNotesService;
    private final LkApplicationStatusService applicationStatusService;
    private final TrademarkDetailService trademarkDetailService;
    private final PctService pctService;
    private final ApplicationValidator applicationValidator;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;


    @Autowired
    public PatentApplicationService(CustomerServiceFeignClient customerServiceFeignClient, ApplicationAcceleratedService applicationAcceleratedService, PaymentCallBackService paymentService, ApplicationSupportServicesTypeService applicationSupportServicesTypeService, IndustrialDesignDetailService industrialDesignDetailService, ClassificationService classificationService, ApplicationInfoRepository applicationInfoRepository, PatentDetailsService patentDetailsService, PaymentFeeCostFeignClient paymentFeeCostFeignClient, ApplicationRelevantRepository applicationRelevantRepository, ApplicationAgentService applicationAgentService, DocumentRepository documentRepository, ApplicationRelevantTypeRepository applicationRelevantTypeRepository, ApplicationInfoMapper requestMapper, ApplicationValidator applicationValidator, DocumentsService documentsService, ApplicationUserService applicationUserService, ApplicationClassificationMapper applicationClassificationMapper, ApplicationRelvantMapper applicationRelvantMapper, LkApplicationCategoryRepository lkApplicationCategoryRepository, CustomerServiceCaller customerServiceCaller, BPMCallerFeignClient bpmCallerFeignClient, ApplicationPriorityService applicationPriorityService, LkApplicationServiceRepository applicationServiceRepository, Properties properties, CustomerCodeValidator customerCodeValidator, BPMCallerServiceImpl bpmCallerService, ApplicationNotesService applicationNotesService, LkApplicationStatusService applicationStatusService, TrademarkDetailService trademarkDetailService, PctService pctService, ApplicationCheckingReportService reportService, ApplicationStatusChangeLogService applicationStatusChangeLogService) {
        super(customerServiceFeignClient, applicationAcceleratedService, paymentService, applicationSupportServicesTypeService, industrialDesignDetailService, classificationService, applicationInfoRepository, patentDetailsService, paymentFeeCostFeignClient, applicationRelevantRepository, applicationAgentService, documentRepository, applicationRelevantTypeRepository, requestMapper, documentsService, applicationUserService, applicationClassificationMapper, applicationRelvantMapper, lkApplicationCategoryRepository, customerServiceCaller, bpmCallerFeignClient, applicationPriorityService, applicationServiceRepository, properties, customerCodeValidator, bpmCallerService, applicationNotesService, applicationStatusService, trademarkDetailService, pctService, reportService, applicationValidator, applicationStatusChangeLogService);


        this.customerServiceFeignClient = customerServiceFeignClient;
        this.applicationValidator = applicationValidator;
        this.applicationAcceleratedService = applicationAcceleratedService;
        this.paymentService = paymentService;
        this.applicationSupportServicesTypeService = applicationSupportServicesTypeService;
        this.industrialDesignDetailService = industrialDesignDetailService;
        this.classificationService = classificationService;
        this.applicationInfoRepository = applicationInfoRepository;
        this.patentDetailsService = patentDetailsService;
        this.paymentFeeCostFeignClient = paymentFeeCostFeignClient;
        this.applicationRelevantRepository = applicationRelevantRepository;
        this.applicationAgentService = applicationAgentService;
        this.documentRepository = documentRepository;
        this.applicationRelevantTypeRepository = applicationRelevantTypeRepository;
        this.requestMapper = requestMapper;
        this.documentsService = documentsService;
        this.applicationUserService = applicationUserService;
        this.applicationClassificationMapper = applicationClassificationMapper;
        this.applicationRelvantMapper = applicationRelvantMapper;
        this.lkApplicationCategoryRepository = lkApplicationCategoryRepository;
        this.customerServiceCaller = customerServiceCaller;
        this.bpmCallerFeignClient = bpmCallerFeignClient;
        this.applicationPriorityService = applicationPriorityService;
        this.applicationServiceRepository = applicationServiceRepository;
        this.customerCodeValidator = customerCodeValidator;
        this.bpmCallerService = bpmCallerService;
        this.applicationNotesService = applicationNotesService;
        this.applicationStatusService = applicationStatusService;
        this.trademarkDetailService = trademarkDetailService;
        this.pctService = pctService;
        this.reportService = reportService;
        this.applicationStatusChangeLogService = applicationStatusChangeLogService;
    }

    @Override
    @PostConstruct
    public void init() {
        registerService(ApplicationCategoryEnum.PATENT, this);
    }    @Override
    public ApplicationCategoryEnum getApplicationCategoryEnum() {
        return ApplicationCategoryEnum.PATENT;
    }



    @Override
    protected void updateApplicationEndOfProtectionDate(ApplicationInfo applicationInfo) {
        LocalDateTime ProtectionEndDate = calculateEndOfProtectionDate(applicationInfo);
        applicationInfo.setEndOfProtectionDate(ProtectionEndDate);
    }




    protected LocalDateTime calculateEndOfProtectionDate(ApplicationInfo applicationInfo) {
        Long ProtectionYears = getProtectionYears(applicationInfo);
        LocalDateTime ProtectionEndDate = Utilities.getLocalDateTimeAfterAddingYearsToHijri(ProtectionYears, applicationInfo.getFilingDate());
        return ProtectionEndDate;
    }

    private Long getProtectionYears(ApplicationInfo applicationInfo) {
        return bpmCallerService.getRequestTypeConfigValue(RequestTypeConfigCode.PROTECTION_PERIOD_EOP.name());
    }


    @Override
    protected Map<String, Object> getStartProcessCustomVariables(ApplicationInfo applicationInfo) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("isPlt", applicationInfo.getPltRegisteration() == null ? "FALSE" : applicationInfo.getPltRegisteration().toString().toUpperCase());
        return vars;
    }

    @Override
    protected LocalDateTime getFilingDateAfterPaymentCallBack(LocalDateTime paymentDate, ApplicationInfo applicationInfo) {
        return applicationInfo.getPltFilingDate() != null ? applicationInfo.getPltFilingDate() : paymentDate;
    }

    @Override
    protected LkApplicationStatus getNewApplicationStatusAfterPaymentCallback(ApplicationInfo applicationInfo) {
        return null;
    }


    @Override
    public String getApplicationNumberWithUniqueSequence(LocalDateTime paymentDate, String serviceCode, ApplicationInfo applicationInfo) {

        String prefix = applicationInfo.getPltRegisteration() ? "10" : "11";
        String abbreviation = applicationInfo.getCategory().getAbbreviation();
        Long applicationsCount = getFilingApplicationsCountInCurrentYearByCategory(paymentDate, applicationInfo);
        String sequenceNumber = Utilities.getPadded4Number(applicationsCount + 1);
        String year = String.valueOf(paymentDate.getYear());
        String sequence = abbreviation + " " + prefix + year + sequenceNumber;
        sequence = generateUniqueSequence(sequence);
        return sequence;
    }

//    private String createSequenceForApplicationNumberPatent(LocalDateTime paymentDate, LkApplicationCategory category, boolean isPLT , Long index) {
//        String prefix = isPLT ? "10" : "11";
//        LocalDateTime startDate = Utilities.getFirstSecondOfYear(paymentDate);
//        LocalDateTime endDate = Utilities.getLastSecondOfYear(paymentDate);
//        String abbreviation = category.getAbbreviation();
//        Long applicationsCount = getBaseApplicationInfoRepository().countByFilingDateBetween(startDate, endDate, category.getId());
//        String sequenceNumber = Utilities.getPadded4Number(index != null ? index : applicationsCount + 1);
//        String year = String.valueOf(paymentDate.getYear());
//        String sequence = abbreviation + " " + prefix + year + sequenceNumber;
//        validateApplicationNumberDoesntExist(sequence);
//        return sequence;
//    }
}
