package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ApplicationSearchSimilarsDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.model.ApplicationSearchSimilars;

import java.util.List;

public interface ApplicationSearchService extends SupportServiceRequestService<ApplicationSearch>{


    List<ApplicationSearchSimilarsDto> fetchSimilarApplicationsFromIpSearch(Long applicationSearchId);


    List<ApplicationSearchSimilarsDto> getSavedApplicationSimilars(Long applicationSearchId);

}
