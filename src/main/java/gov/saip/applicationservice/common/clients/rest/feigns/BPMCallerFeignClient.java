package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.CallForCorrectionInvitationType;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "bpm-caller-service", url = "${client.feign.e-filing-bpm}")
public interface BPMCallerFeignClient {
    @GetMapping(value = "/v1/verification-link/{code}")
    Long validateCode(@PathVariable(name = "code") String code, @RequestHeader("Cookie") String cookie);

    @PostMapping("/internal-calling/v1/process/start-process")
    StartProcessResponseDto startApplicationProcess(@RequestBody ProcessRequestDto dto);

    @PutMapping("/task/{key}/complete/{code}")
    void completeTaskByKey(@PathVariable(name = "key") String key, @PathVariable(name = "code") String code);

    @GetMapping("/internal-calling/v1/process/task/{rowId}")
    RequestTasksDto getTaskByRowId(@PathVariable("rowId") Long rowId,
                                   @RequestParam(value = "requestTypeCode",required = false) String requestTypeCode

    );

    @GetMapping("/internal-calling/v1/process/task/{rowId}/user")
    RequestTasksDto getTaskByRowIdAndUserName(@PathVariable("rowId") Long rowId,
                                              @RequestParam(value = "userName", required = false) String userName,
                                              @RequestParam(value = "reqTypeCode", required = false) String requestTypeCode

    );


    @GetMapping("/internal-calling/v1/task/request/{id}/task")
    ApiResponse<RequestTasksDto> getCurrentTaskByRequestId(@PathVariable("id") Long requestId);

    @GetMapping("/internal-calling/v1/process/tasks/{rowId}")
    List<RequestTasksDto> getTasksByRowId(@PathVariable("rowId") Long rowId);

    @GetMapping("/internal-calling/v1/process/tasks-by-assignee/{rowId}")
    List<RequestTasksDto> getTasksByRowIdAndAssignee(@PathVariable(name="rowId") Long rowId, @RequestParam(value = "assignee",required = false) String assignee);


    @GetMapping("/internal-calling/v1/process/task")
    List<RequestTasksDto> getTaskByRowsIds(@RequestParam("rowIds") List<Long> rowIds);

    @GetMapping("/internal-calling/v1/process/tasks/me")
    ApiResponse<PaginationDto<List<TasksUiDto>>> getUserTasks(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "fromFilingDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromFilingDate,
            @RequestParam(value = "toFilingDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toFilingDate,
            @RequestParam(value = "applicationNumber",     required = false)       String applicationNumber,
            @RequestParam(value = "expiryDate", required = false) String expiryDate
    );

    @GetMapping("/internal-calling/v1/process/task/request-type")
    ApiResponse getUserRequestTypes();

    @GetMapping("/internal-calling/v1/process/task/remaining-time")
    Map<String, Object> getTaskRemainingTimeByApplicationId(
            @RequestParam("applicationId") Long applicationId,
            @PathVariable(value = "processRequestTypeCode",required = false) String processRequestTypeCode
            );


    @PutMapping("/internal-calling/v1/process/extend-due-date")
    ApiResponse extendDueDate(@RequestBody RequestTasksDto requestTasksDto);

    @PostMapping("/pb/task/activate-payments")
    ApiResponse<String> activatePaymentsAccount(@RequestBody CompletePaymentDto completePaymentDto);

    @PostMapping("/internal-calling/v1/process/{taskId}/complete")
    public ApiResponse<String> completeUserTask(@PathVariable(name = "taskId") String taskId, @RequestBody CompleteTaskRequestDto dto);

    @PostMapping("/internal-calling/v1/process/complete/task/row-id")
    ApiResponse<Void> completeTaskByRowId(@RequestParam(name = "rowId") Long rowId, @RequestBody CompleteTaskRequestDto dto);

    @GetMapping(value = "/internal-calling/v1/process/task/{type}/{rowId}")
    ApiResponse<RequestTasksDto> getTaskByRowIdAndType(@PathVariable("type") RequestTypeEnum type, @PathVariable("rowId") Long rowId);

    @GetMapping(value = "/internal-calling/v1/process/task/history/{type}/{rowId}")
    ApiResponse<RequestTasksDto> getTaskByRowIdAndTypefromHistory(@PathVariable("type") RequestTypeEnum type, @PathVariable("rowId") Long rowId);

    @GetMapping(value = "/internal-calling/v1/process/find-task/{type}/{rowId}")
    ApiResponse<RequestTasksDto> getTaskByRowIdAndTypeIfExissts(@PathVariable("type") RequestTypeEnum type, @PathVariable("rowId") Long rowId);

    @GetMapping(value = "/internal-calling/request-type-config/value")
    String getRequestTypeConfigValue(@RequestParam(value = "requestTypeCode") String code);

    @GetMapping(value = "/internal-calling/request-type-config/process-value")
    ApiResponse<String> getRequestTypeConfigValue(@RequestParam(value = "requestConfigCode") String requestConfigCode,
                                                         @RequestParam(value = "rowId") Long rowId,
                                                  @RequestParam(required = false,value = "requestCode") String requestCode);

    @GetMapping(value = "/internal-calling/request-type-config/processes-values")
    ApiResponse<List<ApplicationRequestTypeConfig>> getRequestTypeConfigValue(@RequestParam(value = "requestTypeCode") String code,
                                                                                     @RequestParam(value = "rowsIds") List<Long> rowsIds);


    @PutMapping("/internal-calling/v1/process/suspend-process/{applicationId}/{saipCode}")
    ResponseEntity<Void> suspendApplicationProcessByType(@RequestBody ProcessInstanceDto processInstance,
                                                         @PathVariable("applicationId") Long applicationId,
                                                         @PathVariable("saipCode") String saipCode);

    @GetMapping("/internal-calling/job")
    List<JobDto> getAllJobs(@RequestParam(value = "processInstanceId",required = false)String processInstanceId,
                            @RequestParam(value = "activityId",required = false)String activityId);

    @PutMapping("/internal-calling/job/{id}/duedate")
    public void setJobDueDate(@PathVariable("id") String id, @RequestBody DueDateDto dueDateDto);

    @GetMapping("/internal-calling/v1/process/request-notes-id/{id}/{saipCode}")
    public ApiResponse<RequestIdAndNotesDto> getRequestIdAndLastNotes(@PathVariable("id") long id, @PathVariable("saipCode") String saipCode);

    @GetMapping("/internal-calling/v1/task/history")
    ApiResponse<PaginationDto<List<TaskHistoryUIDto>>> taskHistory(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) Long applicationId,
            @RequestParam(required = false) String processInstanceId,
            @RequestParam(required = false) Long serviceId
    );

    @GetMapping("/internal-calling/v1/process/processInstance/{rowId}")
    ApiResponse<String> getProcessInstanceByRowIdIfExist(@PathVariable(name="rowId") Long rowId,
                                                         @RequestParam(required = false,name = "requestType")String requestType);


    @GetMapping("/internal-calling/v1/task/count-correction-invitation/{requestId}")
    ApiResponse<Integer> getMaxNumberForCallForCorrectionInvitation(@PathVariable(name = "requestId")Long requestId , @RequestParam("invitationType") CallForCorrectionInvitationType invitationType);

    @GetMapping("/internal-calling/v1/process/process-instance/{processInstanceId}/variables")
    ResponseEntity<Map<String, Object>> getProcessVariableMap(@PathVariable("processInstanceId") String processInstanceId);


    @GetMapping("/internal-calling/v1/process/process-instance/{rowId}/variables/{var}")
    String getProcessVarByName(@PathVariable("rowId") Long rowId, @PathVariable("var") String var,
                               @RequestParam(value = "requestTypeCode",required = false) String requestTypeCode

    );

    @GetMapping("/internal-calling/v1/process/find-request-id-by-row-id/{rowId}")
    ApiResponse<Long> findRequestIdByRowId(@PathVariable(name = "rowId") Long rowId);

    @GetMapping("/internal-calling/process/tasks-by-assignee/{rowId}")
    List<RequestTasksDto> getTasksByActualRowIdAndAssignee(@PathVariable(name="rowId") Long rowId, @RequestParam(value = "assignee",required = false) String assignee);

    @PostMapping("/internal-calling/activity-log")
    ApiResponse<UUID> addActivityLog(@RequestBody RequestActivityLogsDto requestActivityLogsDto);

    @PutMapping("/internal-calling/activity-log/update-request-id/{taskDefinitionKey}")
    ApiResponse<Void> updateActivityLogRequestIdByTaskDefinitionKey(@PathVariable(name="taskDefinitionKey") String taskDefinitionKey,
                                                                    @RequestParam(value = "businessKey") Long businessKey);

    @GetMapping("/internal-calling/v1/task/task-definition-key/task/{taskId}")
    ApiResponse<String> getTaskDefinitionKeyByTaskId(@PathVariable(name = "taskId") String taskId);

    @PostMapping("/internal-calling/v1/process/task/{taskId}/assignee")
    String assigneeTaskToUser(@PathVariable(name = "taskId") String taskId, @RequestBody AssigneeRequest request);
}