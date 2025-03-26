package gov.saip.applicationservice.common.service.agency.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.KeyValueDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.repository.agency.SupportServiceCustomerRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.TRADEMARK;
import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.AGENT;
import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.LICENSED_CUSTOMER;
import static gov.saip.applicationservice.common.enums.SupportServiceType.*;
import static gov.saip.applicationservice.util.Constants.SupportServicesApplicationListing.supportServiceCodesApplicationCategoriesStatus;


@Service
@RequiredArgsConstructor
@Transactional
public class SupportServiceCustomerImpl extends BaseServiceImpl<SupportServiceCustomer, Long> implements SupportServiceCustomerService {

    private final TrademarkAgencyRequestService trademarkAgencyRequestService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final SupportServiceCustomerRepository supportServiceCustomerRepository;
    @Lazy
    @Autowired
    private ApplicationInfoService applicationInfoService;
    @Lazy
    @Autowired
    private LicenceRequestService licenceRequestService;
    @Lazy
    @Autowired
    private RevokeLicenceRequestService revokeLicenceRequestService;
    @Autowired
    private ApplicationSupportServicesTypeService applicationSupportServicesTypeService;

    @Override
    protected BaseRepository<SupportServiceCustomer, Long> getRepository() {
        return supportServiceCustomerRepository;
    }

    @Override
    @Transactional
    public void addSupportServiceCustomer(ApplicationSupportServicesType applicationSupportServicesType, SupportServiceType SupportServiceType, Long parentServiceId) {

        String supportServiceRequestApplicantCustomerCode = Utilities.getCustomerCodeFromHeaders();
        Map<String, List<String>> supportServiceTypeMap = supportServiceCodesApplicationCategoriesStatus.get(SupportServiceType.toString());
        if (supportServiceTypeMap != null) {
            List<String> actors = supportServiceCodesApplicationCategoriesStatus.get(SupportServiceType.toString()).get(Constants.SupportServiceValidationMapKeys.ACTORS.name());
            if (applicationSupportServicesType.getApplicationInfo() == null || applicationSupportServicesType.getApplicationInfo().getId() == null || actors == null || actors.isEmpty()) {
                createSupportServiceCustomer(
                        Utilities.getCustomerIdFromHeadersAsLong(), supportServiceRequestApplicantCustomerCode, applicationSupportServicesType, null, ApplicationCustomerType.valueOf(ApplicationCustomerType.MAIN_OWNER.name()), CustomerApplicationAccessLevel.FULL_ACCESS);
                return;
            }
            if (actors.contains(SupportServiceActors.ALL.name())) {
                String mainApplicationApplicantCustomerCode = applicationRelevantTypeRepository.getMainApplicantCustomerCodeByApplicationInfoId(applicationSupportServicesType.getApplicationInfo().getId());
                handleAddingSupportServiceCustomerForApplicationMainCustomerAndTheirAgent(applicationSupportServicesType, mainApplicationApplicantCustomerCode, supportServiceRequestApplicantCustomerCode);
            } else if ((actors.contains(SupportServiceActors.LICENSED_CUSTOMER.name()) || actors.contains(SupportServiceActors.LICENSED_CUSTOMER_AGENT.name())) && parentServiceId != null) {
                handleAddingSupportServiceCustomerForLicensedCustomerAndApplicationMainCustomer(actors, applicationSupportServicesType, parentServiceId, SupportServiceType, supportServiceRequestApplicantCustomerCode);
            } else if (actors.contains(SupportServiceActors.MAIN_OWNER.name()) || actors.contains(SupportServiceActors.AGENT.name())) {

                String mainApplicationApplicantCustomerCode = applicationRelevantTypeRepository.getMainApplicantCustomerCodeByApplicationInfoId(applicationSupportServicesType.getApplicationInfo().getId());
                handleAddingSupportServiceCustomerForApplicationMainCustomerAndTheirAgent(applicationSupportServicesType, mainApplicationApplicantCustomerCode, supportServiceRequestApplicantCustomerCode);
            } else if (actors.contains(SupportServiceActors.OTHER.name())) {
                handleAddingSupportServiceCustomerForOtherCustomersAndTheirAgent(applicationSupportServicesType, supportServiceRequestApplicantCustomerCode, SupportServiceType);
            }
        }
    }

    private String getLicensedCustomerCode(SupportServiceType serviceType, Long parentServiceId) {
        String licensedCustomerCode = null;
        if (serviceType.equals(REVOKE_LICENSE_REQUEST))
            licensedCustomerCode = licenceRequestService.getLicensedCustomerCodeByLicenseId(parentServiceId);
        else if (serviceType.equals(OPPOSITION_REVOKE_LICENCE_REQUEST))
            licensedCustomerCode = revokeLicenceRequestService.getLicensedCustomerCodeByRevokeLicenseId(parentServiceId);
        return licensedCustomerCode;
    }

    @Override
    public SupportServiceCustomer findByApplicationSupportServicesId(Long applicationSupportServicesId , ApplicationCustomerType customerType) {
        return supportServiceCustomerRepository.findByApplicationSupportServicesId(applicationSupportServicesId , customerType);
    }

    @Override
    public List<ApplicationCustomerType> findSupportServiceCustomerTypeByServiceId(Long applicationSupportServicesId) {
        return supportServiceCustomerRepository.findSupportServiceCustomerTypeByServiceId(applicationSupportServicesId);
    }

    private void handleAddingSupportServiceCustomerForOtherCustomersAndTheirAgent(ApplicationSupportServicesType applicationSupportServicesType, String supportServiceRequestApplicantCustomerCode, SupportServiceType SupportServiceType) {
        TrademarkAgencyRequest agency = trademarkAgencyRequestService.getAgencyForServicesByAgentAndAppAndServiceType(supportServiceRequestApplicantCustomerCode, null, applicationSupportServicesType.getApplicationInfo().getId(), SupportServiceType);
        Long supportServiceRequestApplicantCustomerId = Utilities.getCustomerIdFromHeadersAsLong();
         if (agency == null) {
             createOtherSupportServiceCustomer(supportServiceRequestApplicantCustomerId, supportServiceRequestApplicantCustomerCode, applicationSupportServicesType);
         } else {
             Long clientId = customerServiceCaller.getCustomerIdByCustomerCode(agency.getClientCustomerCode()) ;
             createOtherSupportServiceCustomer(clientId, agency.getClientCustomerCode(), applicationSupportServicesType);
             createAgentSupportServiceCustomer(supportServiceRequestApplicantCustomerId ,supportServiceRequestApplicantCustomerCode, applicationSupportServicesType, agency);
         }
    }


    private void handleAddingSupportServiceCustomerForApplicationMainCustomerAndTheirAgent(ApplicationSupportServicesType applicationSupportServicesType, String mainApplicationApplicantCustomerCode, String supportServiceRequestApplicantCustomerCode) {
        ApplicationInfo app = applicationInfoService.findById(applicationSupportServicesType.getApplicationInfo().getId());
        Long supportServiceRequestApplicantCustomerId = Utilities.getCustomerIdFromHeadersAsLong();
        boolean hasLicensingModificationPermission = false,hasLicensingRegistrationPermission = false;
        boolean isLicensedCustomer = applicationRelevantTypeRepository.getLicensedApplicantCustomerCodeByApplicationInfoId(app.getId()) != null;
        if (mainApplicationApplicantCustomerCode.equalsIgnoreCase(supportServiceRequestApplicantCustomerCode)) {
            createMainOwnerSupportServiceCustomer(app.getCreatedByCustomerId(), mainApplicationApplicantCustomerCode, applicationSupportServicesType);
        }
        else if (isLicensedCustomer)
            createLicensedSupportServiceCustomer(Utilities.getCustomerIdFromHeadersAsLong(), Utilities.getCustomerCodeFromHeaders(), applicationSupportServicesType);
        else {
            String appCategory = app.getCategory() == null ? applicationInfoService.findById(app.getId()).getCategory().getSaipCode() : app.getCategory().getSaipCode();
            if (applicationSupportServicesType.getLkSupportServices().getCode().equals(SupportServiceType.LICENSING_MODIFICATION))
                hasLicensingModificationPermission = hasLicensingModificationPermission(applicationSupportServicesType.getApplicationInfo().getId(), supportServiceRequestApplicantCustomerCode);
            if(applicationSupportServicesType.getLkSupportServices().getCode().equals(SupportServiceType.LICENSING_REGISTRATION)) {
                ApplicantType applicantType = ((LicenceRequest) applicationSupportServicesType).getApplicantType();
                hasLicensingRegistrationPermission = applicantType.equals(ApplicantType.LICENSED_CUSTOMER) || applicantType.equals(ApplicantType.OWNER);
            }
            TrademarkAgencyRequest agency = null;
            if (TRADEMARK.name().equals(appCategory)) {
                agency = trademarkAgencyRequestService.getAgencyForServicesByAgentAndAppAndServiceType(supportServiceRequestApplicantCustomerCode, mainApplicationApplicantCustomerCode, applicationSupportServicesType.getApplicationInfo().getId(), applicationSupportServicesType.getLkSupportServices().getCode());
                if (agency == null && !SupportServiceType.TRADEMARK_APPEAL_REQUEST.equals(applicationSupportServicesType.getLkSupportServices().getCode()) && !hasLicensingRegistrationPermission && !hasLicensingModificationPermission) {
                    throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                List<String> agents = app.getApplicationCustomers().stream()
                        .filter(applicationCustomer -> applicationCustomer.getCustomerType().equals(AGENT))
                        .map(ApplicationCustomer::getCustomerCode)
                        .collect(Collectors.toList());
                if (!agents.contains(supportServiceRequestApplicantCustomerCode) && !hasLicensingModificationPermission) {
                    throw new BusinessException(Constants.ErrorKeys.VALIDATION_NO_AGENCY_VALID_TO_REGISTER, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            Long mainApplicantId = customerServiceCaller.getCustomerIdByCustomerCode(mainApplicationApplicantCustomerCode);
            createMainOwnerSupportServiceCustomer(mainApplicantId, mainApplicationApplicantCustomerCode, applicationSupportServicesType);
            createAgentSupportServiceCustomer(supportServiceRequestApplicantCustomerId, supportServiceRequestApplicantCustomerCode, applicationSupportServicesType, agency);
        }
    }

    private boolean hasLicensingModificationPermission(Long applicationId, String applicantCustomerCode) {
        List<ApplicationSupportServicesType> applicationSupportServicesTypes = applicationSupportServicesTypeService.getSupportServiceByAppIdAndStatusAndTypeAndCustomerCode(
                applicationId,
                SupportServiceRequestStatusEnum.LICENSED,
                SupportServiceType.LICENSING_REGISTRATION,
                applicantCustomerCode
        );
        return !applicationSupportServicesTypes.isEmpty();
    }

    private void handleAddingSupportServiceCustomerForLicensedCustomerAndApplicationMainCustomer(List<String> actors, ApplicationSupportServicesType applicationSupportServicesType, Long parentServiceId, SupportServiceType serviceType, String supportServiceRequestApplicantCustomerCode) {

        String customerCode = getLicensedCustomerCode(serviceType, parentServiceId);
        Long supportServiceRequestApplicantCustomerId = Utilities.getCustomerIdFromHeadersAsLong();
        String mainApplicationApplicantCustomerCode = applicationRelevantTypeRepository.getMainApplicantCustomerCodeByApplicationInfoId(applicationSupportServicesType.getApplicationInfo().getId());
        Long mainApplicantId = customerServiceCaller.getCustomerIdByCustomerCode(mainApplicationApplicantCustomerCode);
        if (actors.contains(SupportServiceActors.LICENSED_CUSTOMER.name())) {
            Long customerId = customerServiceCaller.getCustomerIdByCustomerCode(customerCode);
            createLicensedCustomerSupportServiceCustomer(customerId, customerCode, applicationSupportServicesType);
        }

        if (actors.contains(SupportServiceActors.LICENSED_CUSTOMER_AGENT.name())) {

            TrademarkAgencyRequest licensedCustomerAgency = trademarkAgencyRequestService.getAgencyForServicesByAgentAndAppAndServiceType(supportServiceRequestApplicantCustomerCode, customerCode, applicationSupportServicesType.getApplicationInfo().getId(), applicationSupportServicesType.getLkSupportServices().getCode());
            if (licensedCustomerAgency != null) {
                createLicensedCustomerAgentSupportServiceCustomer(supportServiceRequestApplicantCustomerId, supportServiceRequestApplicantCustomerCode, applicationSupportServicesType, licensedCustomerAgency);
            }
        }
        TrademarkAgencyRequest agency = trademarkAgencyRequestService.getAgencyForServicesByAgentAndAppAndServiceType(supportServiceRequestApplicantCustomerCode, mainApplicationApplicantCustomerCode, applicationSupportServicesType.getApplicationInfo().getId(), applicationSupportServicesType.getLkSupportServices().getCode());
        if (agency != null && (supportServiceRequestApplicantCustomerCode.equals(agency.getAgentCustomerCode()))) {
            createAgentSupportServiceCustomer(supportServiceRequestApplicantCustomerId, mainApplicationApplicantCustomerCode, applicationSupportServicesType, agency);
        }
        createMainOwnerSupportServiceCustomer(mainApplicantId, mainApplicationApplicantCustomerCode, applicationSupportServicesType);
    }

    private SupportServiceCustomer createOtherSupportServiceCustomer(Long mainApplicantId, String mainApplicantCode, ApplicationSupportServicesType applicationSupportServicesType ){
        return createSupportServiceCustomer(mainApplicantId, mainApplicantCode, applicationSupportServicesType,
                null, ApplicationCustomerType.OTHER, CustomerApplicationAccessLevel.FULL_ACCESS);
    }

    private SupportServiceCustomer createMainOwnerSupportServiceCustomer(Long mainApplicantId, String mainApplicantCode, ApplicationSupportServicesType applicationSupportServicesType ){
       return createSupportServiceCustomer(mainApplicantId, mainApplicantCode, applicationSupportServicesType,
               null, ApplicationCustomerType.MAIN_OWNER, CustomerApplicationAccessLevel.FULL_ACCESS);
    }

    private SupportServiceCustomer createAgentSupportServiceCustomer(Long supportServiceRequestApplicantCustomerId, String supportServiceRequestApplicantCustomerCode, ApplicationSupportServicesType applicationSupportServicesType, TrademarkAgencyRequest agency){
        return createSupportServiceCustomer(supportServiceRequestApplicantCustomerId, supportServiceRequestApplicantCustomerCode, applicationSupportServicesType,
                agency, AGENT, CustomerApplicationAccessLevel.FULL_ACCESS);
    }

    private SupportServiceCustomer createLicensedSupportServiceCustomer(Long mainApplicantId, String mainApplicantCode, ApplicationSupportServicesType applicationSupportServicesType){
        return createSupportServiceCustomer(mainApplicantId, mainApplicantCode, applicationSupportServicesType,
                null, LICENSED_CUSTOMER, CustomerApplicationAccessLevel.FULL_ACCESS);
    }

    private SupportServiceCustomer createLicensedCustomerAgentSupportServiceCustomer(Long supportServiceRequestApplicantCustomerId, String supportServiceRequestApplicantCustomerCode, ApplicationSupportServicesType applicationSupportServicesType, TrademarkAgencyRequest agency){
        return createSupportServiceCustomer(supportServiceRequestApplicantCustomerId, supportServiceRequestApplicantCustomerCode, applicationSupportServicesType,
                agency, ApplicationCustomerType.LICENSED_CUSTOMER_AGENT, CustomerApplicationAccessLevel.FULL_ACCESS);
    }


    private SupportServiceCustomer createLicensedCustomerSupportServiceCustomer(Long supportServiceRequestApplicantCustomerId, String licensedCustomerCode, ApplicationSupportServicesType applicationSupportServicesType ){
        return createSupportServiceCustomer(supportServiceRequestApplicantCustomerId, licensedCustomerCode, applicationSupportServicesType,
                null, LICENSED_CUSTOMER, CustomerApplicationAccessLevel.FULL_ACCESS);
    }

    private SupportServiceCustomer createSupportServiceCustomer(Long mainApplicantId, String mainApplicantCode, ApplicationSupportServicesType applicationSupportServicesType, TrademarkAgencyRequest agency, ApplicationCustomerType applicationCustomerType, CustomerApplicationAccessLevel customerApplicationAccessLevel) {
        SupportServiceCustomer supportServiceCustomer = new SupportServiceCustomer();
        supportServiceCustomer.setApplicationSupportServices(applicationSupportServicesType);
        supportServiceCustomer.setCustomerId(mainApplicantId);
        supportServiceCustomer.setCustomerCode(mainApplicantCode);
        supportServiceCustomer.setCustomerType(applicationCustomerType);
        supportServiceCustomer.setCustomerApplicationAccessLevel(customerApplicationAccessLevel);
        supportServiceCustomer.setTrademarkAgency(agency);
        return super.insert(supportServiceCustomer);
    }

    private TrademarkAgencyType getTrademarkAgencyType(SupportServiceType SupportServiceType){
        switch (SupportServiceType){
            case OWNERSHIP_CHANGE:
                return TrademarkAgencyType.CHANGE_OWNERSHIP;
            default:
                return TrademarkAgencyType.SUPPORT_SERVICES;
        }
    }


    @Override
    public Map<ApplicationCustomerType, String> getServiceCustomerCodes(Long supportServicesId) {
        List<KeyValueDto<ApplicationCustomerType, String>> data = supportServiceCustomerRepository.getServiceCustomerCodes(supportServicesId);
        return  data.stream().collect(Collectors.toMap(KeyValueDto::getKey, KeyValueDto::getValue));
    }

    @Override
    public List<String> getAgentsCustomerCodeByServiceId(Long id) {
        return supportServiceCustomerRepository.getAgentsCustomerCodeByServiceId(id, AGENT);
    }

    @Override
    public List<String> getCustomerCodesByServiceId(Long id) {
        return supportServiceCustomerRepository.getCustomerCodesByServiceId(id);
    }

    @Override
    public List<Long> getAgentsCustomerIdsByServiceId(Long id) {
        return supportServiceCustomerRepository.getCustomerIdsByServiceId(id, AGENT);
    }
    
    @Override
    public CustomerSampleInfoDto getAppCustomerInfoByServiceIdAndType(Long serviceId, ApplicationCustomerType applicationCustomerType) {
        List<SupportServiceCustomer> supportServiceCustomers = supportServiceCustomerRepository.findBySupportServicesId(serviceId, applicationCustomerType);
        
        return supportServiceCustomers == null || supportServiceCustomers.isEmpty() ? new CustomerSampleInfoDto() :
                getCustomerDetails(supportServiceCustomers.get(0).getCustomerId());
    }

    @Override
    public CustomerSampleInfoDto findBySupportServiceCustomerByApplicationIdAndType(Long applicationId, ApplicationCustomerType applicationCustomerType) {
        List<SupportServiceCustomer> supportServiceCustomers = supportServiceCustomerRepository.findBySupportServiceCustomerByApplicationIdAndType(applicationId, applicationCustomerType);

        return supportServiceCustomers == null || supportServiceCustomers.isEmpty() ? new CustomerSampleInfoDto() :
                getCustomerDetails(supportServiceCustomers.get(0).getCustomerId());
    }

    private CustomerSampleInfoDto getCustomerDetails(Long customerId) {
        return customerServiceCaller.getAnyCustomerDetails(customerId);
    }


}