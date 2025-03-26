package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.SupportServicesTypeDecisionsDto;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.SupportServicesTypeDecisions;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SupportServiceTypeDecisionsService extends BaseService<SupportServicesTypeDecisions , Long> {

    @Transactional
    SupportServicesTypeDecisions insertWithNoOtherApplyingLogic(SupportServicesTypeDecisionsDto supportServicesTypeDecisionsDto);

    SupportServicesTypeDecisions getLastDecisionsForLoggedIn(Long serviceId);
    void addDocumentToDecision(Long serviceId,Long documentId);
}
