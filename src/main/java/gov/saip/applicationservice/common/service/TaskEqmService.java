package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.model.TaskEqm;

import java.util.List;

public interface TaskEqmService extends BaseService<TaskEqm, Long> {

    TaskEqm findByTaskId(String taskId);

    TaskEqm findByEqmApplicationId(Long applicationId);

    Boolean checkByEqmApplicationId(Long applicationId);

    List<TaskEqm> findAllByApplicationId(Long applicationId);

    List<TaskEqm> getByServiceIdAndType(Long serviceId, String typeCode);
}
