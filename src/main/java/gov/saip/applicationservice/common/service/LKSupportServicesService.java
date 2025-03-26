package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.LKSupportServices;

import java.util.List;

public interface LKSupportServicesService extends BaseService<LKSupportServices, Long> {


    LKSupportServices findByCode(SupportServiceType supportServiceType);
    List<LKSupportServices> findByCodeAndCategory(SupportServiceType  code,  String saipCode);
    List<LKSupportServices> findServicesByCategoryId(Long categoryId);

    List<LKSupportServices> getServicesByIds(List<Long> ids);
    
    
    List<LkSupportServiceRequestStatusDto> getSupportServiceTypeStatuses(Long id);
}
