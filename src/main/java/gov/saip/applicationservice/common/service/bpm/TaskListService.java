package gov.saip.applicationservice.common.service.bpm;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.TaskHistoryUIDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;

import java.util.List;

public interface TaskListService {
    String completeTaskToUser(String taskId, CompleteTaskRequestDto dto);

    PaginationDto<List<TaskHistoryUIDto>> taskHistory(Integer page, Integer limit, Long applicationId, String processInstanceId, String requestId, Long serviceId);
    TaskHistoryUIDto lastAction(Integer page, Integer limit, Long applicationId, String processInstanceId, String requestId, Long serviceId);

    Integer remainingCountOfCallForCorrectionInvitation(Long requestId, String callForCorrectionInvitationType);


}
