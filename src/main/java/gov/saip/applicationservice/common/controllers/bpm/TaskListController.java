package gov.saip.applicationservice.common.controllers.bpm;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.TaskHistoryUIDto;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.enums.CallForCorrectionInvitationType;
import gov.saip.applicationservice.common.service.bpm.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kc/v1/task")
@RequiredArgsConstructor
public class TaskListController {
    
    private final TaskListService taskListService;
    
    @PostMapping("/{taskId}/complete")
    public ApiResponse<String> completeUserTask(@PathVariable(name = "taskId") String taskId
            , @RequestBody CompleteTaskRequestDto dto) {
        return  ApiResponse.ok(taskListService.completeTaskToUser(taskId, dto));
    }

    @GetMapping("/history")
    public ApiResponse<PaginationDto<List<TaskHistoryUIDto>>> taskHistory(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false) Long applicationId,
            @RequestParam(required = false) String processInstanceId,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) String requestId) {
        return ApiResponse.ok(taskListService.taskHistory(page, limit, applicationId, processInstanceId, requestId, serviceId));
    }
    @GetMapping("/history/last-action")
    public ApiResponse<TaskHistoryUIDto> getLastAction(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false) Long applicationId,
            @RequestParam(required = false) String processInstanceId,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) String requestId) {
        return ApiResponse.ok(taskListService.lastAction(page, limit, applicationId, processInstanceId, requestId, serviceId));
    }



    @GetMapping("/count-correction-invitation/{requestId}")
    ApiResponse<Integer> getMaxNumberForCallForCorrectionInvitation(@PathVariable(name = "requestId")Long requestId , @RequestParam("invitationType") CallForCorrectionInvitationType invitationType){
        return ApiResponse.ok(taskListService.remainingCountOfCallForCorrectionInvitation(requestId , invitationType.name()));
    }


}
