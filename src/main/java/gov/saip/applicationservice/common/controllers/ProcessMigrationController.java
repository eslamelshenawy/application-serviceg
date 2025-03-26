package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMMigrationCallerFeignClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.migration.RestartProcessRequestDto;
import gov.saip.applicationservice.common.dto.requests.ProcessInstanceRequestDto;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.util.ApplicationHashUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pb/process-migration")
@RequiredArgsConstructor
public class ProcessMigrationController {
    
    private final BPMMigrationCallerFeignClient bpmCallerFeignClient;
    private final ApplicationInfoService applicationInfoService;
    
    @PostMapping
    ApiResponse<String> migrateProcessesRequest(@RequestBody(required = false) Object migrationPlanRequestDto,
                                                @RequestParam(name = "api-key") String apiKey,
                                                @RequestParam(name = "add-dynamic-vars", defaultValue = "false") boolean addDynamicVars
    ) {
        if (!ApplicationHashUtilities.verifyKey(apiKey, "$2a$10$pn4auMZYNlZ6WdktOn8oLO3/dIrwDaKins3S/8XOzO6FxBgAPTQOm")) {
            return ApiResponse.status(HttpStatus.UNAUTHORIZED, "Api key is not valid", null);
        }
        
        return bpmCallerFeignClient.migrateProcessesRequest(migrationPlanRequestDto, addDynamicVars);
    }
    
    @GetMapping
    ApiResponse<List<String>> migrateProcessesRequest() {
        return bpmCallerFeignClient.getMigrationReport();
    }
    
    @PostMapping("/process-definition/{id}/processes/variables/add")
    public ApiResponse<String> addVarsToProcessesByProcessDefinitionId(@RequestBody Object vars, @PathVariable(name = "id") String id) {
        bpmCallerFeignClient.addVarsToProcessesByProcessDefinitionId(vars, id);
        return ApiResponse.ok("add variables to processes of given process definition id started");
    }
    
    @PostMapping("/process-definition/{id}/activities/variables/add")
    public ApiResponse<String> addVarsToProcessesByProcessDefinitionId(@RequestBody Object vars,
                                                                       @PathVariable(name = "id") String id,
                                                                       @RequestParam(name = "activityIdIn") List<String> activityIdIn) {
        bpmCallerFeignClient.addVarsToProcessesByProcessDefinitionId(vars, id, activityIdIn);
        return ApiResponse.ok("add variables to processes of given process definition id started");
    }
    
    @GetMapping(value = "/applications/generate-tm-missing-application-number")
    public ApiResponse<String> generateTrademarkMissingApplicationNumbers() {
        applicationInfoService.generateTrademarkMissingApplicationNumbers();
        return ApiResponse.ok("Missing Trademark application numbers are generated successfully");
    }
    
    
    @PostMapping("/process-definition/restart")
    public ApiResponse<String> processInstanceRestart(@RequestBody RestartProcessRequestDto body) {
        return bpmCallerFeignClient.processInstanceRestart(body);
    }
    
    @PostMapping("/process-definition/restart/last-user-task")
    public ApiResponse<String> restartCompletedProcessInstancesFromLastUserUserTask(@RequestBody RestartProcessRequestDto body) {
        return bpmCallerFeignClient.restartCompletedProcessInstancesFromLastUserUserTask(body);
    }
    
    @GetMapping("/number-of-threads/{num}")
    public ApiResponse<Void> updateNumOfThreads(@PathVariable(name = "num") int numberOfThreads) {
        bpmCallerFeignClient.updateNumOfThreads(numberOfThreads);
        return ApiResponse.noContent();
    }
    
    @PutMapping("/change-task-assignee")
    public void changeTaskAssignee(@RequestParam(name = "oldAssigneeStartsBy") List<String> oldAssigneeStartsBy, @RequestParam(name = "newAssignee", required = false) String newAssignee) {
        bpmCallerFeignClient.changeTaskAssignee(oldAssigneeStartsBy, newAssignee);
    }
    
    @PostMapping("/internal-calling/process-migration/processes-instances-ids/bulk-delete")
    public ApiResponse<String> deleteBulkOfProcesses(@RequestBody ProcessInstanceRequestDto dto) {
        return bpmCallerFeignClient.deleteBulkOfProcesses(dto);
    }

}
