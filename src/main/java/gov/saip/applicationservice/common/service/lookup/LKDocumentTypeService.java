package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.LkDocumentType;


public interface LKDocumentTypeService extends BaseService<LkDocumentType, Long> {
    public LkDocumentType getDocumentTypeByName(String code);
}
