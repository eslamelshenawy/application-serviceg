package gov.saip.applicationservice.modules.ic.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.modules.ic.dto.LegalDocumentListDto;
import gov.saip.applicationservice.modules.ic.model.ApplicationLegalDocument;

import java.util.List;

public interface ApplicationLegalDocumentService extends BaseService<ApplicationLegalDocument, Long> {

    Long softDeleteLegalDocumentById(Long id);
    List<LegalDocumentListDto> findApplicationLegalDocumentsByApplicationId(Long applicationId);
}
