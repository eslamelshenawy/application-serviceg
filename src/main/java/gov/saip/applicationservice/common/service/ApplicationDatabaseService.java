package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.ApplicationDatabase;

import java.util.List;

public interface ApplicationDatabaseService extends BaseService<ApplicationDatabase, Long> {

    public List<ApplicationDatabase> getAllByApplicationId(Long appId);
}
