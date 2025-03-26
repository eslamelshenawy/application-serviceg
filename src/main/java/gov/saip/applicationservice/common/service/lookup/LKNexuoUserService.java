package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.LkNexuoUser;


public interface LKNexuoUserService extends BaseService<LkNexuoUser, Long> {
    public LkNexuoUser getNexuoUserTypeByType(String type);
}
