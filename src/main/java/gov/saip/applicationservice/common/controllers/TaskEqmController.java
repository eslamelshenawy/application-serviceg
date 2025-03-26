package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.TaskEqmDto;
import gov.saip.applicationservice.common.mapper.TaskEqmMapper;
import gov.saip.applicationservice.common.model.TaskEqm;
import gov.saip.applicationservice.common.service.TaskEqmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/task-eqm", "/internal-calling/task-eqm"})
@RequiredArgsConstructor
@Slf4j
public class TaskEqmController extends BaseController<TaskEqm, TaskEqmDto, Long> {

    private final TaskEqmService taskEqmService;
    private final TaskEqmMapper taskEqmMapper;

    @Override
    protected BaseService<TaskEqm, Long> getService() {
        return  taskEqmService;
    }

    @Override
    protected BaseMapper<TaskEqm, TaskEqmDto> getMapper() {
        return taskEqmMapper;
    }
    @GetMapping("/task/{taskId}")
    public ApiResponse<TaskEqmDto> findByTaskId(@PathVariable(name = "taskId") String taskId) {
        return ApiResponse.ok(taskEqmMapper.map(taskEqmService.findByTaskId(taskId)));
    }

    @GetMapping("/application/{applicationId}")
    public ApiResponse<TaskEqmDto> findByApplicationId(@PathVariable(name = "applicationId") Long applicationId) {
        return ApiResponse.ok(taskEqmMapper.map(taskEqmService.findByEqmApplicationId(applicationId)));
    }

    @GetMapping("/application/check/{applicationId}")
    public Boolean checkByEqmApplicationId(@PathVariable(name = "applicationId") Long applicationId) {
        return taskEqmService.checkByEqmApplicationId(applicationId);
    }

    @GetMapping("/application/all/{applicationId}")
    public ApiResponse<List<TaskEqmDto>> findAllByApplicationId(@PathVariable(name = "applicationId") Long applicationId) {
        return ApiResponse.ok(taskEqmMapper.map(taskEqmService.findAllByApplicationId(applicationId)));
    }

    @GetMapping("/service")
    public ApiResponse<List<TaskEqmDto>> getByServiceIdAndType(@RequestParam(name = "serviceId") Long serviceId, @RequestParam(name = "typeCode") String typeCode) {
        return ApiResponse.ok(taskEqmMapper.map(taskEqmService.getByServiceIdAndType(serviceId, typeCode)));
    }
}
