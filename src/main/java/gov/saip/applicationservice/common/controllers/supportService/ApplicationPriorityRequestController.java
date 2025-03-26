package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityRequestDto;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationPriorityRequestMapper;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityRequest;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({"/kc/support-service/priority-request", "/internal-calling/support-service/priority-request"})
@RequiredArgsConstructor
public class ApplicationPriorityRequestController extends BaseController<ApplicationPriorityRequest, ApplicationPriorityRequestDto, Long> {
    
    private final ApplicationPriorityRequestService applicationPriorityRequestService;
    private final ApplicationPriorityRequestMapper applicationPriorityRequestMapper;
    @Override
    protected BaseService<ApplicationPriorityRequest, Long> getService() {
        return applicationPriorityRequestService;
    }

    @Override
    protected BaseMapper<ApplicationPriorityRequest, ApplicationPriorityRequestDto> getMapper() {
        return applicationPriorityRequestMapper;
    }
    @GetMapping("/periority-edit-only/{appId}")
    public ApiResponse<Boolean> applicationSupportServicesTypePeriorityEditOnlyExists(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationPriorityRequestService.applicationSupportServicesTypePeriorityEditOnlyExists(appId));
    }
}
