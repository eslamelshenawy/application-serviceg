package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;

import java.util.List;

public interface ApplicationSimilarDocumentService extends BaseService<ApplicationSimilarDocument, Long> {

    public List<ApplicationSimilarDocument> getAllByApplicationId(Long appId);
}
