package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.ApplicationCustomerRepository;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.LicenceRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.MAIN_OWNER;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationCustomerServiceImpl extends BaseServiceImpl<ApplicationCustomer, Long> implements ApplicationCustomerService {

    private final ApplicationCustomerRepository applicationCustomerRepository;

    private final CustomerServiceCaller customerServiceCaller;
    @Lazy
    @Autowired
    private LicenceRequestService licenceRequestService;


    @Override
    protected BaseRepository<ApplicationCustomer, Long> getRepository() {
        return applicationCustomerRepository;
    }
    @Override
    @Transactional
    public void deleteByAppAndCustomerId(Long customerId, Long appId) {
        applicationCustomerRepository.deleteByAppAndCustomerId(customerId, appId);
    }

    @Override
    public List<String> getApplicationVerifiedCustomerCodes(Long applicationId, List<String> nonOwnerSupportServiceCodes) {
        List<String> verifiedCustomerCodes = applicationCustomerRepository.getApplicationVerifiedCustomerCodes(applicationId, nonOwnerSupportServiceCodes);
        List<String> licencedCustomers = licenceRequestService.getLicensedCustomersDetails(applicationId).stream().map(CustomerSampleInfoDto::getCode).collect(Collectors.toList());
        verifiedCustomerCodes.addAll(licencedCustomers);
        return verifiedCustomerCodes;
    }

    @Override
    @Transactional
    public void deleteByAppAndCustomerCode(String customerCode, Long appId) {
        applicationCustomerRepository.deleteByAppAndCustomerCode(customerCode, appId);
    }

    @Override
    @Transactional
    public void deleteApplicationCustomersByTypeAndAppIds(ApplicationCustomerType applicationCustomerType, List<Long> appIds) {
        applicationCustomerRepository.deleteApplicationCustomersByTypeAndAppIds(applicationCustomerType, appIds);
    }

    @Override
    @Transactional
    public void deleteApplicationCustomersByTypeAndAppId(ApplicationCustomerType applicationCustomerType, Long appId) {
        applicationCustomerRepository.deleteApplicationCustomersByTypeAndAppId(applicationCustomerType, appId);
    }

    @Override
    @Transactional
    public void deleteByApplicationId(Long appId) {
        applicationCustomerRepository.deleteByApplicationId(appId);
    }

    @Override
    @Transactional
    public void updateApplicationCustomerTypeByApplication(ApplicationCustomerType newType, ApplicationCustomerType oldType, Long appId) {
        applicationCustomerRepository.updateApplicationCustomerTypeByApplication(newType, oldType, appId);
    }

    @Override
    public CustomerSampleInfoDto getApplicationActiveCustomer(Long applicationId) {
        Long customerId =  getApplicationCustomerTypeWithHighestPriorityForApplication(applicationId);
        CustomerSampleInfoDto customerDetails = getCustomerDetails(customerId);
        return customerDetails;
    }
    @Override
    public Map<Long, String> getCustomerCodesByAppIdsAndCustomerType(List<Long> ids, ApplicationCustomerType customerType) {
        List<KeyValueDto<Long, String>> customers = applicationCustomerRepository.getCustomerCodesByAppIdsAndCustomerType(ids, customerType);
        return customers.stream().collect(Collectors.toMap(KeyValueDto::getKey, KeyValueDto::getValue));
    }

    private Long getApplicationCustomerTypeWithHighestPriorityForApplication(Long applicationId) {
        log.info("get highest customer for application ==>> {}", applicationId);
       List<ApplicationCustomerDto> applicationCustomerDtoList = applicationCustomerRepository.findAllApplicationCustomerTypeAndCustomerIdByApplicationId(applicationId);
        Long customerId =  getCustomerIdWithHighestPriorityApplicationCustomerType(applicationCustomerDtoList);
        return customerId;
    }

    private Long getCustomerIdWithHighestPriorityApplicationCustomerType(List<ApplicationCustomerDto> applicationCustomerDtoList) {
        ApplicationCustomerDto applicationCustomerDto = filterByCustomerType(applicationCustomerDtoList, ApplicationCustomerType.AGENT);
        if (applicationCustomerDto == null)
            applicationCustomerDto = filterByCustomerType(applicationCustomerDtoList, ApplicationCustomerType.APPLICANT);
        if (applicationCustomerDto == null)
            applicationCustomerDto = filterByCustomerType(applicationCustomerDtoList, MAIN_OWNER);
        return applicationCustomerDto.getCustomerId();
    }

    private ApplicationCustomerDto filterByCustomerType(List<ApplicationCustomerDto> applicationCustomerDtoList, ApplicationCustomerType applicationCustomerType) {
        Optional<ApplicationCustomerDto> applicationCustomerDto =  applicationCustomerDtoList
                .stream()
                .filter(dto -> applicationCustomerType.equals(dto.getCustomerType()))
                .findAny();
        return applicationCustomerDto.isPresent() ? applicationCustomerDto.get() : null;
    }


    private CustomerSampleInfoDto getCustomerDetails(Long customerId) {
        CustomerSampleInfoDto customerDetails = customerServiceCaller.getAnyCustomerDetails(customerId);
        return customerDetails;
    }

    @Override
    public List<ApplicationCustomer> getAppCustomersByTypeOrCodeOrCustomerId(ApplicationCustomerType customerType, Long appId, Long customerId, String customerCode) {
        return applicationCustomerRepository.getAppCustomersByTypeOrCodeOrCustomerId(customerId, customerCode, customerType, appId);
    }

    @Override
    public List<ApplicationCustomer> getAppCustomersByIdsAndType(List<Long> apps, ApplicationCustomerType applicationCustomerType) {
        return applicationCustomerRepository.getAppCustomersByIdsAndType(apps, applicationCustomerType);
    }

    @Override
    @Transactional
    public void addCustomerToApplicationIfNotExistsOrIgnore(ApplicationCustomerData applicationCustomerData) {
        List<ApplicationCustomer> customers = getExistingCustomers(applicationCustomerData);
        Optional<ApplicationCustomer> currentAgent = customers.stream().filter(c -> c.getCustomerCode().equals(applicationCustomerData.getCustomerCode())).findFirst();
        if (currentAgent.isEmpty()) {
            applicationCustomerRepository.save(new ApplicationCustomer(applicationCustomerData));
        }
    }

    private List<ApplicationCustomer> getExistingCustomers(ApplicationCustomerData applicationCustomerData) {
        return applicationCustomerRepository.getAppCustomersByTypeOrCodeOrCustomerId(null, applicationCustomerData.getCustomerCode(), applicationCustomerData.getCustomerType(), applicationCustomerData.getApplicationInfo().getId());
    }
    
    public Map<Long, Map<Long, ApplicationCustomer>> getApplicationsCustomersByTypes(List<ApplicationInfo> applicationInfos,
                                                                              List<ApplicationCustomerType> typesToFilter) {
        Map<Long, Map<Long, ApplicationCustomer>> applicationCustomersMap = new HashMap<>();
        applicationInfos.forEach(applicationInfo -> {
            Long applicationId = applicationInfo.getId();
            Map<Long, ApplicationCustomer> customerMap = buildCustomerMap(applicationInfo.getApplicationCustomers(), typesToFilter);
            applicationCustomersMap.put(applicationId, customerMap);
        });
        return applicationCustomersMap;
    }
    
    private Map<Long, ApplicationCustomer> buildCustomerMap(List<ApplicationCustomer> applicationCustomers, List<ApplicationCustomerType> typesToFilter) {
        Map<Long, ApplicationCustomer> customerMap = new HashMap<>();
        List<ApplicationCustomer> filteredCustomers = filterCustomersByTypes(applicationCustomers, typesToFilter);
        filteredCustomers.forEach(customer -> customerMap.put(customer.getCustomerId(), customer));
        return customerMap;
    }
    
    private List<ApplicationCustomer> filterCustomersByTypes(List<ApplicationCustomer> customers, List<ApplicationCustomerType> typesToFilter) {
        return customers
                .stream()
                .filter(applicationCustomer -> typesToFilter.contains(applicationCustomer.getCustomerType()))
                .collect(Collectors.toList());
    }
    
    
    
    public List<Long> getCustomersIdsFromApplicationsCustomersMap(Map<Long, Map<Long, ApplicationCustomer>> applicationCustomersMap) {
        if (applicationCustomersMap == null || applicationCustomersMap.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Long> customersIds = extractCustomerIdsFromApplicationsCustomersMap(applicationCustomersMap);
        if (customersIds.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(customersIds);
    }
    
    private Set<Long> extractCustomerIdsFromApplicationsCustomersMap(Map<Long, Map<Long, ApplicationCustomer>> applicationCustomersMap) {
        Set<Long> customersIds = new HashSet<>();
        for (Map.Entry<Long, Map<Long, ApplicationCustomer>> entry : applicationCustomersMap.entrySet()) {
            Long applicationId = entry.getKey();
            Map<Long, ApplicationCustomer> customerMap = entry.getValue();
            if (applicationId != null && customerMap != null && !customerMap.isEmpty()) {
                customersIds.addAll(customerMap.keySet());
            }
        }
        return customersIds;
    }
    
    @Override
    public ApplicationCustomer getAppCustomerByAppIdAndType(Long appId, ApplicationCustomerType applicationCustomerType) {
        return applicationCustomerRepository.getAppCustomerByAppIdAndType(appId, applicationCustomerType);
    }

    @Override
    public CustomerSampleInfoDto getAppCustomerInfoByAppIdAndType(Long appId, ApplicationCustomerType applicationCustomerType) {
        ApplicationCustomer appCustomer = applicationCustomerRepository.getAppCustomerByAppIdAndType(appId, applicationCustomerType);
        return appCustomer == null ? new CustomerSampleInfoDto() : getCustomerDetails(appCustomer.getCustomerId());
    }

    @Override
    public ApplicationNotificationData findapplicationNotificationData(Long appId) {
        List<ApplicationNotificationData> appCustomer = applicationCustomerRepository.findapplicationNotificationData(appId);
        ApplicationNotificationData applicationNotificationData = appCustomer.get(0);
        applicationNotificationData.setMobileNumber(applicationNotificationData.getMobileCode().concat(applicationNotificationData.getMobileNumber()));
        return applicationNotificationData;
    }
    public ApplicationNotificationData  findMainOwnerNotificationData(Long appId){
        ApplicationNotificationData ownerCustomer = applicationCustomerRepository.findApplicationByCustomerTypeNotificationData(appId,MAIN_OWNER);
        ownerCustomer.setMobileNumber(ownerCustomer.getMobileCode().concat(ownerCustomer.getMobileNumber()));
        return ownerCustomer;
     }

    public List<Long> findAllAgentsByApplicationIdsId(Long customerId){
        return applicationCustomerRepository.findAllAgentsByApplicationIdsId(customerId);
    }

}