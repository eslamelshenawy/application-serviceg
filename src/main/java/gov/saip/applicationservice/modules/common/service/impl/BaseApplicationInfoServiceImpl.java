package gov.saip.applicationservice.modules.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.service.ApplicationAcceleratedService;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationRelevantTypeService;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.lookup.LKApplicationServiceService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationStatusService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.common.service.BaseApplicationInfoService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.DRAFT;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.NEW;

@Service
@Slf4j
@Setter
@Transactional(readOnly = true)
public abstract class BaseApplicationInfoServiceImpl extends BaseServiceImpl<ApplicationInfo, Long> implements BaseApplicationInfoService {

    private final static Map<ApplicationCategoryEnum,  BaseApplicationInfoService> SERVICES = new LinkedMap();


    @Override
    public Map<ApplicationCategoryEnum, BaseApplicationInfoService> getServicesMap() {
        return SERVICES;
    }

    @Override
    protected BaseRepository<ApplicationInfo, Long> getRepository() {
        return getBaseApplicationInfoRepository();
    }

    protected abstract ApplicationInfoRepository getBaseApplicationInfoRepository();



    @Autowired
    protected  CustomerServiceFeignClient customerServiceFeignClient;

    @Autowired
    protected  ApplicationAgentService applicationAgentService;

    @Autowired
    protected  DocumentRepository documentRepository;

    @Autowired
    protected  ApplicationInfoMapper requestMapper;

    @Autowired
    protected ApplicationCustomerService applicationCustomerService;

    @Autowired
    protected ApplicationRelevantTypeService applicationRelevantTypeService;

    @Autowired
    private BPMCallerFeignClient bpmCallerFeignClient;

    @Autowired
    private BPMCallerServiceImpl bpmCallerService;

    @Autowired
    private LkApplicationStatusService applicationStatusService;

    @Autowired
    private LKApplicationServiceService applicationServiceService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private ApplicationAcceleratedService applicationAcceleratedService;

    @Autowired
    private ApplicationStatusChangeLogService applicationStatusChangeLogService;

    @Override
    @Transactional
    public Long saveApplication(ApplicantsRequestDto applicantsRequestDto) {
        // add a new application if there is no id
        if (isNewApplication(applicantsRequestDto)) {
            ApplicationInfo applicationInfo = insertNewApplication(applicantsRequestDto);
            return applicationInfo.getId();
        }

        return updateNewApplication(applicantsRequestDto);
    }


    @Override
    @Transactional
    public void updateApplicationWithProcessRequestId(StartProcessResponseDto startProcessResponseDto, Long id) {
        getBaseApplicationInfoRepository().updateApplicationWithProcessRequestId(id, startProcessResponseDto.getBusinessKey());
    }

    private Long updateNewApplication(ApplicantsRequestDto applicantsRequestDto) {
        // update application
        ApplicationInfo applicationInfo = updateAppRelvantInfo(applicantsRequestDto);
        setApplicantInfo(applicationInfo.getId(), applicationInfo);
        return updateApplicationCustomersAndPOA(applicantsRequestDto, applicationInfo);
    }

    protected Long updateApplicationCustomersAndPOA(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        // add customers to application
        handleApplicationCustomers(applicantsRequestDto, applicationInfo);

        handleUpdateAgentAndPOAScenarios(applicantsRequestDto, applicationInfo.getId());

        return applicationInfo.getId();
    }

    private ApplicationInfo insertNewApplication(ApplicantsRequestDto applicantsRequestDto) {
        log.info("insert a new application for a user id " + applicantsRequestDto.getCreatedByUserId());
        validateAndAddApplication(applicantsRequestDto);
        this.setCreatedByCustomerIdAndApplicantCustomerType(applicantsRequestDto);

        ApplicationInfo applicationInfo =  addApplication(applicantsRequestDto);

        handleApplicationCustomers(applicantsRequestDto, applicationInfo);
        startApplicationProcessAfterSaveFileNewApplication(applicantsRequestDto, applicationInfo);
        return applicationInfo;
    }

    protected void handleApplicationCustomers(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {
        addNewApplicationCustomers(applicantsRequestDto, applicationInfo.getId());
    }


    protected void startApplicationProcessAfterSaveFileNewApplication(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo) {

    }


    protected void addNewApplicationCustomers(ApplicantsRequestDto applicantsRequestDto, Long newAppId) {

        // delete old customers before insert the new customers
        applicationCustomerService.deleteByApplicationId(newAppId);

        // get customer code by id
        String applicantCustomerCode = Utilities.getCustomerCodeFromHeaders(); // customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(applicantsRequestDto.getCustomerId())).getPayload();
        ApplicationInfo applicationInfo = new ApplicationInfo(newAppId);

        // if customer code same as sent from front that mean the applicant is owner not agent
        if (applicantsRequestDto.getCustomerCode().equalsIgnoreCase(applicantCustomerCode)) {
            ApplicationCustomerData owner = ApplicationCustomerData.builder()
                    .applicationInfo(applicationInfo)
                    .customerType(ApplicationCustomerType.MAIN_OWNER)
                    .customerId(applicantsRequestDto.getCustomerId())
                    .customerCode(applicantCustomerCode)
                    .customerAccessLevel(CustomerApplicationAccessLevel.FULL_ACCESS)
                    .build();
            applicationCustomerService.insert(new ApplicationCustomer(owner));
            return;
        }

        // get owner id,
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
                .customerCode(applicantCustomerCode)
                .customerAccessLevel(CustomerApplicationAccessLevel.FULL_ACCESS)
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
    
    private void validateAndAddApplication(ApplicantsRequestDto applicantsRequestDto) {
        if (Objects.isNull(applicantsRequestDto.getCustomerId())) {
            log.error("error user id or mobile id or mobile code is null so an exception is thrown");
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_USER_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        }
//        else if (Objects.isNull(applicantsRequestDto.getMobileCode())) {
//            log.error("error mobile code is null so an exception is thrown");
//            throw new BusinessException(Constants.ErrorKeys.VALIDATION_MOBILE_CODE_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
//        } else if (Objects.isNull(applicantsRequestDto.getMobileNumber())) {
//            log.error("error mobile number is null so an exception is thrown");
//            throw new BusinessException(Constants.ErrorKeys.VALIDATION_MOBILE_NUMBER_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
//        }
    }


    public ApplicationInfo addApplication(ApplicantsRequestDto applicantsRequestDto) {
        ApplicationInfo applicationInfo = requestMapper.mapRequestToEntity(applicantsRequestDto);
        LkApplicationCategory lkApplicationCategory = getLkApplicationCategory(applicantsRequestDto);
        applicationInfo.setCategory(lkApplicationCategory);
        LkApplicationStatus applicationStatus = getDraftApplicationStatusBeforePaymentCallback(applicationInfo);
        applicationInfo.setApplicationStatus(applicationStatus);
        setExtraDetailsBasedOnApplicationType(applicantsRequestDto, applicationInfo, lkApplicationCategory);
        ApplicationInfo saved = getBaseApplicationInfoRepository().save(applicationInfo);
        if(!applicationStatus.getCode().equals(applicationInfo.getApplicationStatus().getCode())){
            activityLogService.insertFileNewApplicationActivityLogStatus(null, applicationInfo.getId(), applicationInfo.getApplicationStatus().getCode(),applicationStatus);
        }

        return applicationRelevantTypeService.saveApplicationRelevantAndRelevantTypes(applicantsRequestDto, saved);
    }


    protected void setExtraDetailsBasedOnApplicationType(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo applicationInfo, LkApplicationCategory lkApplicationCategory) {
       // no need to set anything in base layer
    }

    protected LkApplicationCategory getLkApplicationCategory(ApplicantsRequestDto applicantsRequestDto) {
        if (getApplicationCategoryEnum() != null) {
            return getApplicationCategoryEnum().getLkApplicationCategory();
        }
        return null;
    }

    private void setCreatedByCustomerIdAndApplicantCustomerType(ApplicantsRequestDto applicantsRequestDto){
        Long customerId = Utilities.getCustomerIdFromHeadersAsLong();
        String applicantCustomerCode = Utilities.getCustomerCodeFromHeaders();
        log.info("insert a new application for a customer Id " + customerId+ " and customer code : "+applicantCustomerCode);
        applicantsRequestDto.setCreatedByCustomerId(customerId);
        if (applicantsRequestDto.getCustomerCode().equalsIgnoreCase(applicantCustomerCode)){
            applicantsRequestDto.setCreatedByCustomerType(ApplicationCustomerType.MAIN_OWNER);
        }else{
            applicantsRequestDto.setCreatedByCustomerType(ApplicationCustomerType.AGENT);
        }
    }




    public ApplicationInfo updateAppRelvantInfo(ApplicantsRequestDto applicantsRequestDto) {
        ApplicationInfo applicationInfoExist = getBaseApplicationInfoRepository().findById(applicantsRequestDto.getAppId()).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
        updateApplicationRelevantInfo(applicantsRequestDto.getAppId(), applicantsRequestDto.getCustomerCode());
        List<String> customersCodes = new LinkedList<>();
        LkApplicationCategory lkApplicantCategory = getLkApplicationCategory(applicantsRequestDto);
        applicationInfoExist.setCategory(lkApplicantCategory);
        requestMapper.mapRequestToEntity(applicationInfoExist, applicantsRequestDto);
        applicationInfoExist = getBaseApplicationInfoRepository().save(applicationInfoExist);

        return applicationRelevantTypeService.updateApplicationRelevantAndRelevantTypes(applicantsRequestDto, customersCodes, applicationInfoExist);
    }

    private void updateApplicationRelevantInfo(Long applicationId, String customerCode) {
        applicationRelevantTypeService.updateCustomerCodeForMainApplicationRelevant(customerCode, applicationId);
    }

    private void handleUpdateAgentAndPOAScenarios(ApplicantsRequestDto applicantsRequestDto, Long newAppId) {
        // is not by him self and document is null do nothing [that mean the document does not updated]
        if (!applicantsRequestDto.isByHimself() && Objects.isNull(applicantsRequestDto.getPoaDocumentId()))
            return;

        // if by him self make sure to delete the agent if assigned
        if (applicantsRequestDto.isByHimself()) {
            deleteApplicationAgentAndPOADocument(applicantsRequestDto);
            return;
        }

        // if is not by him self and document id sent then remove old agent if exists and his document then reinsert the new one
        if (!applicantsRequestDto.isByHimself() && Objects.nonNull(applicantsRequestDto.getPoaDocumentId())) {
            deleteApplicationAgentAndPOADocument(applicantsRequestDto);
            updatePOADocumentAndAssignTheAgentToApplication(applicantsRequestDto, newAppId);
        }
    }

    private void deleteApplicationAgentAndPOADocument(ApplicantsRequestDto applicantsRequestDto) {

        // delete by app and agent
        ApplicationAgent agent = applicationAgentService.getCurrentApplicationAgentEntity(applicantsRequestDto.getAppId());
        if (Objects.nonNull(agent))
            applicationAgentService.deleteApplicationAgent(agent);

        // delete by app id and poa
        List<Document> docs = documentRepository.getApplicationDocsByApplicationIdAndTypes(applicantsRequestDto.getAppId(), List.of(DocumentTypeEnum.POA.name()));
        if (docs != null && !docs.isEmpty()) {
            for (Document doc : docs){
                if(!doc.getNexuoId().equals(applicantsRequestDto.getPoaDocumentId()))
                    documentRepository.delete(doc);
            }

        }
    }


    private void updatePOADocumentAndAssignTheAgentToApplication(ApplicantsRequestDto applicantsRequestDto, Long newAppId) {
        if (!applicantsRequestDto.isByHimself() && Objects.nonNull(applicantsRequestDto.getPoaDocumentId())) {
            // update document
            Document doc = documentRepository.getDocumentByNexuoId(applicantsRequestDto.getPoaDocumentId()).get();
            doc.setApplicationInfo(new ApplicationInfo(newAppId));

            // add agent
            ApplicationAgent applicationAgent = new ApplicationAgent();
            // get customer id by user id
            applicationAgent.setCustomerId(applicantsRequestDto.getCustomerId());
            // get customer
            applicationAgent.setApplication(new ApplicationInfo(newAppId));
            applicationAgent.setApplicationAgentDocuments(Arrays.asList(doc));

            applicationAgentService.assignCreatedApplicationToAgent(applicationAgent);
        }
    }





    protected boolean isNewApplication(ApplicantsRequestDto applicantsRequestDto) {
        return Objects.isNull(applicantsRequestDto.getAppId()) || !getBaseApplicationInfoRepository().existsById(applicantsRequestDto.getAppId());
    }


    @Override
    public StartProcessResponseDto startProcessConfig(ApplicationInfo applicationInfo) {
        LkApplicationCategory lkApplicantCategory = applicationInfo.getCategory();
        ApplicationCategoryEnum applicationCategoryEnum = ApplicationCategoryEnum.valueOf(lkApplicantCategory.getSaipCode());
        return starProcess(applicationInfo, applicationCategoryEnum.getProcessName(), applicationCategoryEnum.getProcessTypeCode());
    }

    private StartProcessResponseDto starProcess(ApplicationInfo applicationInfo, String processName, String requestType) {
        ProcessRequestDto dto = new ProcessRequestDto();
        dto.setProcessId(processName);
        CustomerSampleInfoDto customerInfo = applicationCustomerService.getApplicationActiveCustomer(applicationInfo.getId());
        Map<String, Object> vars = getStringObjectMap(applicationInfo, requestType, customerInfo);
        vars.putAll(getStartProcessCustomVariables(applicationInfo));
        dto.setVariables(vars);
        if (applicationInfo.getCategory() != null) {
            dto.setCategoryId(applicationInfo.getCategory().getId());
        }
        return bpmCallerService.startApplicationProcess(dto);
    }

    protected Map<String, Object> getStartProcessCustomVariables(ApplicationInfo applicationInfo) {
        return new HashMap<>();
    }


    private Map<String, Object> getStringObjectMap(ApplicationInfo applicationInfo, String requestType, CustomerSampleInfoDto customerInfo) {
        Map<String, Object> vars = new HashMap<>();

        vars.put("fullNameAr", customerInfo.getNameAr());
        vars.put("fullNameEn", customerInfo.getNameEn());
        vars.put("APPLICANT_USER_NAME", applicationInfo.getCreatedByUser());
        String email = !Strings.isBlank(applicationInfo.getEmail()) ? applicationInfo.getEmail() : customerInfo.getEmail();
        vars.put("email", email);
        String mobileNumber = !Strings.isBlank(applicationInfo.getMobileNumber()) ? applicationInfo.getMobileCode() + applicationInfo.getMobileNumber() : customerInfo.getMobileCountryCode() + customerInfo.getMobile();
        vars.put("mobile", mobileNumber);
        vars.put("customerCode", customerInfo.getCode());
        vars.put("id", applicationInfo.getId().toString());
        vars.put("requestTypeCode", requestType);
        vars.put("identifier", applicationInfo.getIpcNumber());
        vars.put("applicationCategory", applicationInfo.getCategory().getSaipCode());
        vars.put("applicationNumber", applicationInfo.getApplicationNumber());
        vars.put("titleAr", applicationInfo.getTitleAr());
        vars.put("titleEn", applicationInfo.getTitleEn());
        vars.put("isPlt", applicationInfo.getPltRegisteration() == null ? "FALSE" : applicationInfo.getPltRegisteration().toString().toUpperCase());
        vars.put("applicationRequestNumber", applicationInfo.getApplicationRequestNumber());
//        vars.put("INDUSTRIAL_ATTACH_FILE", bpmCallerFeignClient.getRequestTypeConfigValue("INDUSTRIAL_ATTACH_FILE"));

        boolean applicationAccelerated = applicationAcceleratedService.checkIfApplicationAccelrated(applicationInfo.getId());
        String applicantCustomerCode = customerServiceFeignClient.getCustomerCodeByUserId(applicationInfo.getCreatedByUserId()).getPayload();
        vars.put("APPLICANT_CUSTOMER_CODE", applicantCustomerCode);
        vars.put("applicationAccelerated", applicationAccelerated);

        return vars;
    }


    public void generateApplicationNumberAndUpdateApplicationInfoAfterPaymentCallback(ApplicationNumberGenerationDto applicationNumberGenerationDto,
                                                                                      ApplicationInfo applicationInfo , ApplicationInfo partialApplicationInfo) {
        // updateApplicationInfoAfterPaymentCallback(applicationNumberGenerationDto.getPaymentDate(), applicationNumberGenerationDto.getServiceCode(), applicationInfo);
        // we create first installment for patent application after filing date[this installment will be next year from filing date]
        //applicationInstallmentService.createAndSaveFirstAnnualFees(applicationInfo);

        LocalDateTime filingDate ;

        if (partialApplicationInfo != null) {
            filingDate = partialApplicationInfo.getFilingDate() ;
            applicationInfo.setFilingDate(filingDate);
            applicationInfo.setFilingDateHijri(partialApplicationInfo.getFilingDateHijri());
        }else {
            filingDate = getFilingDateAfterPaymentCallBack(applicationNumberGenerationDto.getPaymentDate(), applicationInfo);
            applicationInfo.setFilingDate(filingDate);
            if (filingDate != null) {
                applicationInfo.setFilingDateHijri(Utilities.convertDateFromGregorianToHijri(filingDate.toLocalDate()));
            }
        }

        updateApplicationEndOfProtectionDate(applicationInfo);

        LkApplicationStatus newApplicationStatus = getNewApplicationStatusAfterPaymentCallback(applicationInfo);
        if (newApplicationStatus != null) {
            applicationInfo.setApplicationStatus(newApplicationStatus);
        }

        String applicationNumber = getApplicationNumberWithUniqueSequence(filingDate, applicationNumberGenerationDto.getServiceCode(), applicationInfo);
        applicationInfo.setApplicationNumber(applicationNumber);
        getBaseApplicationInfoRepository().save(applicationInfo);
    }


    protected LocalDateTime getFilingDateAfterPaymentCallBack(LocalDateTime paymentDate, ApplicationInfo applicationInfo) {
        return paymentDate;
    };


    protected LkApplicationStatus getNewApplicationStatusAfterPaymentCallback(ApplicationInfo applicationInfo) {
        return applicationStatusService.findByCodeAndApplicationCategory(NEW.name(), applicationInfo.getCategory().getId());
    }

    protected LkApplicationStatus getDraftApplicationStatusBeforePaymentCallback(ApplicationInfo applicationInfo) {
        return applicationStatusService.findByCodeAndApplicationCategory(DRAFT.name(), applicationInfo.getCategory().getId());
    }

    protected void updateApplicationEndOfProtectionDate(ApplicationInfo applicationInfo) {}



    public String getApplicationNumberWithUniqueSequence(LocalDateTime paymentDate, String serviceCode, ApplicationInfo applicationInfo) {
        LkApplicationService lkApplicationServices = applicationServiceService.findByCode(serviceCode);
        Long applicationsCount = getFilingApplicationsCountInCurrentYearByCategory(paymentDate, applicationInfo);
        String year = Utilities.getLastTwoDigitOfYear(paymentDate);
        String baseSequence = generateApplicationNumberSequence(applicationInfo, lkApplicationServices, applicationsCount, year);
        return generateUniqueSequence(baseSequence);
    }

    protected String generateUniqueSequence(String baseSequence) {
        if (!isApplicationNumberExist(baseSequence)) {
            return baseSequence;
        }

        return generateUniqueSequenceWithSuffix(baseSequence);
    }

    private String generateUniqueSequenceWithSuffix(String baseSequence) {
        Random random = new Random();
        String suffix;

        do {
            suffix = String.format("%03X", random.nextInt(0x1000));
        } while (isApplicationNumberExist(baseSequence + suffix));

        return baseSequence + suffix;
    }

    protected Long getFilingApplicationsCountInCurrentYearByCategory(LocalDateTime paymentDate, ApplicationInfo applicationInfo) {
        LocalDateTime startDate = Utilities.getFirstSecondOfYear(paymentDate);
        LocalDateTime endDate = Utilities.getLastSecondOfYear(paymentDate);
        return getBaseApplicationInfoRepository().countByFilingDateBetween(startDate, endDate, applicationInfo.getCategory().getId());
    }

    private String generateApplicationNumberSequence(ApplicationInfo applicationInfo, LkApplicationService lkApplicationServices, Long applicationsCount, String year) {
        String sequenceNumber = Utilities.getPadded5Number(applicationsCount + 1);
        return new StringBuilder()
                .append(applicationInfo.getCategory().getAbbreviation())
                .append("-")
                .append(lkApplicationServices.getOperationNumber())
                .append("-")
                .append("00")
                .append("-")
                .append(sequenceNumber)
                .append("-")
                .append(year).toString();
    }


    protected void validateApplicationNumberDoesntExist(String sequence) {
        if (getBaseApplicationInfoRepository().ApplicationNumberExist(sequence)) {
            log.error("application number exists ==> sequence = {} ", sequence);
            throw new BusinessException("APPLICATION_NUMBER_EXISTS", HttpStatus.BAD_REQUEST, new String[]{sequence});
        }
        log.info("application number is not exists ==> sequence = {} ", sequence);
    }

    protected boolean isApplicationNumberExist(String sequence) {
        return getBaseApplicationInfoRepository().ApplicationNumberExist(sequence);
    }



    @Override
    @Transactional
    public void paymentCallBackHandler(ApplicationNumberGenerationDto applicationNumberGenerationDto, ApplicationInfo applicationInfo , ApplicationInfo partialApplicationInfo) {
        LkApplicationStatus currentStatus = applicationInfo.getApplicationStatus();
        generateApplicationNumberAndUpdateApplicationInfoAfterPaymentCallback(applicationNumberGenerationDto, applicationInfo , partialApplicationInfo);
        StartProcessResponseDto startProcessResponseDto = startProcessConfig(applicationInfo);
        activityLogService.insertFileNewApplicationActivityLogStatus(startProcessResponseDto.getTaskHistoryUIDto(), applicationInfo.getId(), ApplicationStatusEnum.NEW.name(), currentStatus);
        updateApplicationWithProcessRequestId(startProcessResponseDto, applicationInfo.getId());
    }

    @Override
    @Transactional
    public void updateApplicationStatusByIdAndStatusCode(Long applicationId, String statusCode, String categoryCode) {
        getBaseApplicationInfoRepository().updateApplicationByIdAndStatusCode(applicationId, statusCode, categoryCode);
        getBaseApplicationInfoRepository().updateLastStatusModifiedDate(applicationId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void updateApplicationsStatusByIdsAndStatusCode(List<Long> applicationIds, String statusCode, String categoryCode) {
        getBaseApplicationInfoRepository().updateApplicationsStatusByIdsAndStatusCode(applicationIds, statusCode, categoryCode);
        getBaseApplicationInfoRepository().updateApplicationsLastStatusModifiedDate(applicationIds, LocalDateTime.now());
    }

    @Override
    public void setApplicantInfo(Long applicationId, ApplicationInfo applicationInfo){
        ApplicationInfoRequestLightDto applicationInfoRequestLightDto = ApplicationInfoRequestLightDto
                .builder()
                .applicationId(applicationId)
                .applicationCustomerType("MAIN_OWNER")
                .build();
        updateApplicationNameAddressInfo(applicationInfoRequestLightDto, applicationInfo);
    }

    public void updateApplicationNameAddressInfo(ApplicationInfoRequestLightDto requestDto, ApplicationInfo applicationInfo) {
        ApplicationCustomer applicationCustomer = getApplicationCustomer(requestDto);
        CustomerSampleInfoDto customerInfo = getCustomerInfo(applicationCustomer);
        setOwnerNameAndAddress(applicationInfo, customerInfo);
    }

    private ApplicationCustomer getApplicationCustomer(ApplicationInfoRequestLightDto requestDto) {
        return applicationCustomerService.getAppCustomerByAppIdAndType(
                requestDto.getApplicationId(),
                ApplicationCustomerType.valueOf(requestDto.getApplicationCustomerType())
        );
    }
    private CustomerSampleInfoDto getCustomerInfo(ApplicationCustomer applicationCustomer) {
        return customerServiceFeignClient.getAnyCustomerById(applicationCustomer.getCustomerId()).getPayload();
    }

    public void setOwnerNameAndAddress(ApplicationInfo applicationInfo, CustomerSampleInfoDto customerInfo) {
        applicationInfo.setOwnerNameAr(customerInfo.getNameAr());
        applicationInfo.setOwnerNameEn(customerInfo.getNameEn());
        AddressResponseDto address = customerInfo.getAddress();
        if (address != null) {
            applicationInfo.setOwnerAddressAr(address.getPlaceOfResidenceAr());
            applicationInfo.setOwnerAddressEn(address.getPlaceOfResidenceEn());
        }
        getBaseApplicationInfoRepository().save(applicationInfo);
    }
}
