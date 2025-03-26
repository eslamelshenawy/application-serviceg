package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.LkApplicationService;

import java.util.List;


public interface LKApplicationServiceService extends BaseService<LkApplicationService, Long> {

    List<LkApplicationService> findAll();

    LkApplicationService findByCode(String serviceCode);
}
