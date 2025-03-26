package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.ApplicationWord;

import java.util.List;

public interface ApplicationWordService extends BaseService<ApplicationWord, Long> {

    List<ApplicationWord> getAllAppWordsByApplicationId(Long appId);
}
