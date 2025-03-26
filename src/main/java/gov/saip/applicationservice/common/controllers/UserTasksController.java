package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.TasksUiDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.BPMCallerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/kc/user"})
@RequiredArgsConstructor
@Slf4j
public class UserTasksController {
private final BPMCallerService bpmCallerService;
private final ApplicationInfoRepository applicationInfoRepository;
    @GetMapping("/tasks/me")
    public ApiResponse<PaginationDto<List<TasksUiDto>>> getUserTasks(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "fromFilingDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromFilingDate,
            @RequestParam(value = "toFilingDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toFilingDate,
            @RequestParam(value = "applicationNumber",     required = false)       String applicationNumber,
            @RequestParam(value = "expiryDate", required = false) String expiryDate
    ) {
        return bpmCallerService.getAllUserTasks(page, limit,query,type,fromDate,toDate,expiryDate,fromFilingDate, toFilingDate, applicationNumber);
    }

    @GetMapping("/task/remaining-time")
    Map<String,Object> getTaskRemainingTime(@RequestParam("applicationId") Long applicationId
    ){
        String processRequestTypeCode = ApplicationCategoryEnum.valueOf(applicationInfoRepository.findCategoryByApplicationId(applicationId)).getProcessTypeCode();
        return bpmCallerService.getTaskRemainingTimeByApplicationId(applicationId,processRequestTypeCode);
    }

    @GetMapping(value = "/task/{type}/{rowId}")
    public ApiResponse<RequestTasksDto> getTaskByRowIdAndType(@PathVariable("type") RequestTypeEnum type, @PathVariable("rowId") Long rowId) {
        return bpmCallerService.getTaskByRowIdAndType(type, rowId);
    }

    @GetMapping("/request/{id}/task")
    public ApiResponse<RequestTasksDto> getTaskByRequestId(@PathVariable("id") Long requestId){
        return ApiResponse.ok(bpmCallerService.getCurrentTaskByRequestId(requestId));
    }

}
