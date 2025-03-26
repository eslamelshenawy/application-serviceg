package gov.saip.applicationservice.modules.industrial.deisgn.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.NEW;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.UNDER_FORMAL_PROCESS;

@Service
@Slf4j
public class IndustrialDesignApplicationService extends ApplicationServiceImpl {

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
    public IndustrialDesignApplicationService(CustomerServiceFeignClient customerServiceFeignClient, ApplicationAcceleratedService applicationAcceleratedService, PaymentCallBackService paymentService, ApplicationSupportServicesTypeService applicationSupportServicesTypeService, IndustrialDesignDetailService industrialDesignDetailService, ClassificationService classificationService, ApplicationInfoRepository applicationInfoRepository, PatentDetailsService patentDetailsService, PaymentFeeCostFeignClient paymentFeeCostFeignClient, ApplicationRelevantRepository applicationRelevantRepository, ApplicationAgentService applicationAgentService, DocumentRepository documentRepository, ApplicationRelevantTypeRepository applicationRelevantTypeRepository, ApplicationInfoMapper requestMapper, DocumentsService documentsService, ApplicationUserService applicationUserService, ApplicationClassificationMapper applicationClassificationMapper, ApplicationRelvantMapper applicationRelvantMapper, LkApplicationCategoryRepository lkApplicationCategoryRepository, CustomerServiceCaller customerServiceCaller, BPMCallerFeignClient bpmCallerFeignClient, ApplicationPriorityService applicationPriorityService, LkApplicationServiceRepository applicationServiceRepository, Properties properties, CustomerCodeValidator customerCodeValidator, BPMCallerServiceImpl bpmCallerService, ApplicationNotesService applicationNotesService, LkApplicationStatusService applicationStatusService, TrademarkDetailService trademarkDetailService, PctService pctService, ApplicationCheckingReportService reportService, ApplicationValidator applicationValidator, ApplicationStatusChangeLogService applicationStatusChangeLogService) {
        super(customerServiceFeignClient, applicationAcceleratedService, paymentService, applicationSupportServicesTypeService, industrialDesignDetailService, classificationService, applicationInfoRepository, patentDetailsService, paymentFeeCostFeignClient, applicationRelevantRepository, applicationAgentService, documentRepository, applicationRelevantTypeRepository, requestMapper, documentsService, applicationUserService, applicationClassificationMapper, applicationRelvantMapper, lkApplicationCategoryRepository, customerServiceCaller, bpmCallerFeignClient, applicationPriorityService, applicationServiceRepository, properties, customerCodeValidator, bpmCallerService, applicationNotesService, applicationStatusService, trademarkDetailService, pctService, reportService, applicationValidator, applicationStatusChangeLogService);


        this.customerServiceFeignClient = customerServiceFeignClient;
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
        this.applicationValidator = applicationValidator;
        this.applicationStatusChangeLogService = applicationStatusChangeLogService;
    }

    @Override
    @PostConstruct
    public void init() {
        registerService(ApplicationCategoryEnum.INDUSTRIAL_DESIGN, this);
    }

    @Override
    public ApplicationCategoryEnum getApplicationCategoryEnum() {
        return ApplicationCategoryEnum.INDUSTRIAL_DESIGN;
    }

    @Override
    protected Map<String, Object> getStartProcessCustomVariables(ApplicationInfo applicationInfo) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("INDUSTRIAL_ATTACH_FILE", bpmCallerFeignClient.getRequestTypeConfigValue("INDUSTRIAL_ATTACH_FILE"));
        return vars;
    }

    @Override
    protected LkApplicationStatus getNewApplicationStatusAfterPaymentCallback(ApplicationInfo applicationInfo) {
        return applicationStatusService.findByCodeAndApplicationCategory(NEW.name() , applicationInfo.getCategory().getId());
    }
}
