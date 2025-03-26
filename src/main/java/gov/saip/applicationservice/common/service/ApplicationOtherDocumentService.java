package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;

import java.util.List;

public interface ApplicationOtherDocumentService extends BaseService<ApplicationOtherDocument, Long> {

    List<ApplicationOtherDocument> getAllAppOtherDocumentsByApplicationId(Long appId);
}
