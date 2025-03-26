package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationCustomerData;
import gov.saip.applicationservice.common.dto.ApplicationNotificationData;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.model.ApplicationInfo;

import java.util.List;
import java.util.Map;

public interface ApplicationCustomerService extends BaseService<ApplicationCustomer, Long> {

    void deleteByAppAndCustomerId(Long customerId, Long appId);
    List<String> getApplicationVerifiedCustomerCodes(Long applicationId, List<String> nonOwnerSupportServiceCodes);

    void deleteByAppAndCustomerCode(String customerCode, Long appId);

    void deleteApplicationCustomersByTypeAndAppIds(ApplicationCustomerType applicationCustomerType, List<Long> appIds);
    void deleteApplicationCustomersByTypeAndAppId(ApplicationCustomerType applicationCustomerType, Long appId);
    void deleteByApplicationId(Long appId);
    void updateApplicationCustomerTypeByApplication(ApplicationCustomerType newType, ApplicationCustomerType oldType, Long appId);

    CustomerSampleInfoDto getApplicationActiveCustomer(Long applicationId);

    Map<Long, String> getCustomerCodesByAppIdsAndCustomerType(List<Long> ids, ApplicationCustomerType customerType);

    List<ApplicationCustomer> getAppCustomersByTypeOrCodeOrCustomerId(ApplicationCustomerType customerType, Long appId, Long customerId,  String customerCode);
    List<ApplicationCustomer> getAppCustomersByIdsAndType(List<Long> apps, ApplicationCustomerType applicationCustomerType);
    
    void addCustomerToApplicationIfNotExistsOrIgnore(ApplicationCustomerData applicationCustomerData);
    
    Map<Long, Map<Long, ApplicationCustomer>> getApplicationsCustomersByTypes(List<ApplicationInfo> applicationInfos, List<ApplicationCustomerType> mainOwner);
    
    List<Long> getCustomersIdsFromApplicationsCustomersMap(Map<Long, Map<Long, ApplicationCustomer>> applicationsCustomers);
    
    ApplicationCustomer getAppCustomerByAppIdAndType(Long appId, ApplicationCustomerType applicationCustomerType);
    CustomerSampleInfoDto getAppCustomerInfoByAppIdAndType(Long appId, ApplicationCustomerType applicationCustomerType);

    ApplicationNotificationData findapplicationNotificationData(Long appId);
    ApplicationNotificationData  findMainOwnerNotificationData(Long appId);

    List<Long> findAllAgentsByApplicationIdsId(Long customerId);



}
