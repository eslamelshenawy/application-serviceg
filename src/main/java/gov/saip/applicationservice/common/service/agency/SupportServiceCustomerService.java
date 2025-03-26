package gov.saip.applicationservice.common.service.agency;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.SupportServiceCustomer;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface SupportServiceCustomerService extends BaseService<SupportServiceCustomer, Long> {
    @Transactional
    void addSupportServiceCustomer(ApplicationSupportServicesType applicationSupportServicesType, SupportServiceType SupportServiceType, Long parentServiceId);
    SupportServiceCustomer findByApplicationSupportServicesId(Long applicationSupportServicesId,ApplicationCustomerType customerType);

    List<ApplicationCustomerType> findSupportServiceCustomerTypeByServiceId(Long applicationSupportServicesId);

    Map<ApplicationCustomerType, String> getServiceCustomerCodes(Long supportServicesId);

    List<String> getAgentsCustomerCodeByServiceId(Long id);
    List<String> getCustomerCodesByServiceId(Long id);
    List<Long> getAgentsCustomerIdsByServiceId(Long id);
    
    public CustomerSampleInfoDto getAppCustomerInfoByServiceIdAndType(Long serviceId, ApplicationCustomerType applicationCustomerType);

    CustomerSampleInfoDto findBySupportServiceCustomerByApplicationIdAndType(Long applicationId, ApplicationCustomerType applicationCustomerType);
}