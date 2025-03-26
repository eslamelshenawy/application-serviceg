package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.LKMonshaatEnterpriseSize;
import org.springframework.stereotype.Service;



public interface LKMonshaatEnterpriseSizeService extends BaseLkService<LKMonshaatEnterpriseSize, Long> {
    String saveApplicationForOrganizationSize(Long appId,String customerCode);


}
