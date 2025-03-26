package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.mapper.supportService.SupportServiceStatusChangeLogMapper;
import gov.saip.applicationservice.common.model.supportService.SupportServiceStatusChangeLog;
import gov.saip.applicationservice.common.service.supportService.SupportServiceStatusChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/internal-calling/support-service-status-change-log"})
@RequiredArgsConstructor
@Slf4j
public class SupportServiceStatusChangeLogController extends BaseController<SupportServiceStatusChangeLog, SupportServiceStatusChangeLogDto, Long> {

    private final SupportServiceStatusChangeLogService supportServiceStatusChangeLogService;
    private final SupportServiceStatusChangeLogMapper supportServiceStatusChangeLogMapper;
    @Override
    protected BaseService<SupportServiceStatusChangeLog, Long> getService() {
        return supportServiceStatusChangeLogService;
    }

    @Override
    protected BaseMapper<SupportServiceStatusChangeLog, SupportServiceStatusChangeLogDto> getMapper() {
        return supportServiceStatusChangeLogMapper;
    }

    @GetMapping("/{serviceId}/service")
    public ApiResponse<List<BaseStatusChangeLogDto>> getByApplicationId(@PathVariable(name = "serviceId") Long serviceId) {
        return ApiResponse.ok(supportServiceStatusChangeLogService.getBySupportServiceId(serviceId));
    };

}
