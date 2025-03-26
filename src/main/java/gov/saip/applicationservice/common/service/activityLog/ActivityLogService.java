package gov.saip.applicationservice.common.service.activityLog;

import gov.saip.applicationservice.common.dto.TaskHistoryUIDto;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LkApplicationStatus;

public interface ActivityLogService {
    void insertSupportServicesActivityLogStatus(TaskHistoryUIDto taskHistoryUIDto, ApplicationSupportServicesType supportServicesType);

    void insertTrademarkAgencyActivityLogStatus(TaskHistoryUIDto taskHistoryUIDto, Long agencyId);

    void insertFileNewApplicationActivityLogStatus(TaskHistoryUIDto taskHistoryUIDto, Long applicationId, String codeStatus, LkApplicationStatus previousStatus);

    void insertSupportServicesActivityLogStatus(String taskId, String taskDefinitionKey, Long id, String newStatus);
}
