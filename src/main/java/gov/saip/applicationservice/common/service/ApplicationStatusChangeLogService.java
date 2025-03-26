package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;

import java.util.List;

public interface ApplicationStatusChangeLogService extends BaseService<ApplicationStatusChangeLog, Long> {
    List<BaseStatusChangeLogDto> getByApplicationId(Long appId);
    BaseStatusChangeLogDto getLastLogByApplicationId(Long appId);

    boolean isExistsByAppIdTaskDefinitionKey(Long applicationId, String taskKey);
    void changeApplicationStatusAndLog(String status, String descriptionCode, Long appId);

    void changeApplicationStatusAndLog(String status, String descriptionCode, Long appId, String taskDefinitionKey, String taskId);
}
