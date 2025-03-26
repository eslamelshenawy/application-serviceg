package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BPMCallerService {

    StartProcessResponseDto startApplicationProcess(ProcessRequestDto dto);
    RequestTasksDto getTaskByRowId(Long rowId,String processRequestTypeCode);
    RequestTasksDto getTaskByRowIdAndUserName(Long rowId, String userName,String requestTypeCode);
    List<RequestTasksDto> getTasksByRowId(Long rowId);
    List<RequestTasksDto> getTaskByRowsIds(List<Long> rowIds);

    ApiResponse<RequestTasksDto> getTaskByRowIdAndType(RequestTypeEnum type, Long rowId);
    RequestTasksDto getTaskByRowIdAndTypeIfExists(RequestTypeEnum type, Long rowId);
    ApiResponse<PaginationDto<List<TasksUiDto>>> getAllUserTasks(Integer page, Integer limit, String query, String type, String fromDate, String toDate, String expiryDate, LocalDate fromFilingDate, LocalDate
            toFilingDate, String applicationNumber);
    Map<String,Object> getTaskRemainingTimeByApplicationId(Long applicationId,String processRequestTypeCode);

    public void extendDueDate(RequestTasksDto task);
    
    public Long getRequestTypeConfigValue(String code);

    public Long getRequestTypeConfigValue(String code, Long rowId,String requestTypeCode);

    public List<ApplicationRequestTypeConfig> getRequestTypeConfigValue(String code, List<Long> rowsIds);

    public String completeTaskToUser(String taskId, CompleteTaskRequestDto dto);

//    void suspendApplicationProcessByType(ProcessInstanceDto processInstance,
//                                         Long applicationId,
//                                         String saipCode);


    RequestIdAndNotesDto getRequestIdAndLastNotes(long id, String saipCode);

    RequestTasksDto getCurrentTaskByRequestId(Long requestId);

    PaginationDto<List<TaskHistoryUIDto>> taskHistory(Integer page, Integer limit, Long applicationId, String processInstanceId, String requestId, Long serviceId);

    Integer remainingCountOfCallForCorrectionInvitation(Long requestId, String callForCorrectionInvitationType);

    List<RequestTasksDto> getTasksByRowIdAndAssignee(Long rowId);

     ApiResponse<RequestTasksDto> getTaskByRowIdAndTypefromHistory(RequestTypeEnum type, Long rowId);


//    String getProcessInstanceByRowIdIfExist(Long rowId);

//    Map<String, Object> getProcessVariableMap(String processInstanceId);


}

