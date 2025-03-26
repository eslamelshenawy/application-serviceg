package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.mapper.ApplicationStatusChangeLogMapper;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/internal-calling/app-status-change-log"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationStatusChangeLogController extends BaseController<ApplicationStatusChangeLog, ApplicationStatusChangeLogDto, Long> {

    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    private final ApplicationStatusChangeLogMapper applicationStatusChangeLogMapper;
    @Override
    protected BaseService<ApplicationStatusChangeLog, Long> getService() {
        return applicationStatusChangeLogService;
    }

    @Override
    protected BaseMapper<ApplicationStatusChangeLog, ApplicationStatusChangeLogDto> getMapper() {
        return applicationStatusChangeLogMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<BaseStatusChangeLogDto>> getByApplicationId(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationStatusChangeLogService.getByApplicationId(appId));
    };

    @GetMapping("/last/{appId}/application")
    public ApiResponse<BaseStatusChangeLogDto> getLastLogByApplicationId(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationStatusChangeLogService.getLastLogByApplicationId(appId));
    };

    @Override
    @PostMapping("")
    public ApiResponse<Long> insert(@RequestBody ApplicationStatusChangeLogDto dto) {
        ApplicationStatusChangeLog entity = applicationStatusChangeLogMapper.unMap(dto);
        ApplicationStatusChangeLog result = applicationStatusChangeLogService.insert(entity);
        return ApiResponse.ok(result == null ? null : result.getId());
    }


}
