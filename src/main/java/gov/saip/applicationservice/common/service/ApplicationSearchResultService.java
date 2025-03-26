package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.model.ApplicationSearchResult;

import java.util.List;

public interface ApplicationSearchResultService extends BaseService<ApplicationSearchResult, Long> {

    List<ApplicationSearchResult> getAllSearchResultsByApplicationId(Long appId);
}
