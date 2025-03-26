package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.migration.RestartProcessRequestDto;
import gov.saip.applicationservice.common.dto.requests.ProcessInstanceRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bpm-migration-caller-service", url = "${client.feign.e-filing-bpm}/internal-calling/process-migration")
public interface BPMMigrationCallerFeignClient {

    @PostMapping
    ApiResponse<String> migrateProcessesRequest(@RequestBody(required = false) Object migrationPlanRequestDto,
                                                @RequestParam(name = "add-dynamic-vars", defaultValue = "false") boolean addDynamicVars
    );
    @GetMapping
    ApiResponse<List<String>> getMigrationReport();

    @PostMapping("/process-definition/{id}/processes/variables/add")
    ApiResponse<String> addVarsToProcessesByProcessDefinitionId(@RequestBody Object vars, @PathVariable(name = "id") String id);

    @PostMapping("/process-definition/{id}/activities/variables/add")
    ApiResponse<String> addVarsToProcessesByProcessDefinitionId(@RequestBody Object vars, @PathVariable(name = "id") String id,
                                                                @RequestParam(name = "activityIdIn") List<String> activityIdIn);


    @PostMapping("/process-definition/restart")
    ApiResponse<String> processInstanceRestart(@RequestBody RestartProcessRequestDto body);

    @PostMapping("/process-definition/restart/last-user-task")
    ApiResponse<String> restartCompletedProcessInstancesFromLastUserUserTask(@RequestBody RestartProcessRequestDto body);

    @GetMapping("/number-of-threads/{num}")
    ApiResponse<Void> updateNumOfThreads(@PathVariable(name = "num") int numberOfThreads);

    @PutMapping("/change-task-assignee")
    void changeTaskAssignee(@RequestParam(name = "oldAssigneeStartsBy") List<String> oldAssigneeStartsBy,@RequestParam(name = "newAssignee", required = false) String newAssignee);
    
    @PostMapping("/processes-instances-ids/bulk-delete")
    public ApiResponse<String> deleteBulkOfProcesses(@RequestBody ProcessInstanceRequestDto dto);
}