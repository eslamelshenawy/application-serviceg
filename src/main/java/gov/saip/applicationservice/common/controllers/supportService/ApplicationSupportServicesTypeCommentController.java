package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.supportService.ApplicationSupportServicesTypeCommentDto;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationSupportServicesTypeCommentMapper;
import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;
import gov.saip.applicationservice.common.service.supportService.ApplicationSupportServicesTypeCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service-comment", "/internal-calling/support-service-comment"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationSupportServicesTypeCommentController extends BaseController<ApplicationSupportServicesTypeComment, ApplicationSupportServicesTypeCommentDto, Long> {

    private final ApplicationSupportServicesTypeCommentService applicationSupportServicesTypeCommentService;
    private final ApplicationSupportServicesTypeCommentMapper applicationSupportServicesTypeCommentMapper;
    @Override
    protected BaseService<ApplicationSupportServicesTypeComment, Long> getService() {
        return  applicationSupportServicesTypeCommentService;
    }

    @Override
    protected BaseMapper<ApplicationSupportServicesTypeComment, ApplicationSupportServicesTypeCommentDto> getMapper() {
        return applicationSupportServicesTypeCommentMapper;
    }

    @GetMapping("/application-service/{applicationSupportServiceId}/comment")
    public ApiResponse<List<ApplicationSupportServicesTypeCommentDto>> getAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId(@PathVariable(name = "applicationSupportServiceId") Long applicationSupportServiceId) {
        return ApiResponse.ok(applicationSupportServicesTypeCommentMapper.map(applicationSupportServicesTypeCommentService.getAllApplicationSupportServicesTypeCommentsByApplicationSupportServiceId(applicationSupportServiceId)));
    }

    @PostMapping("/comment")
    public ApiResponse<Long> insertAddNewApplicationSupportServicesTypeComment(@RequestBody ApplicationSupportServicesTypeCommentDto applicationSupportServicesTypeCommentDto) {
        ApplicationSupportServicesTypeComment applicationSupportServicesTypeComment = applicationSupportServicesTypeCommentMapper.unMap(applicationSupportServicesTypeCommentDto);
        applicationSupportServicesTypeComment = applicationSupportServicesTypeCommentService.insert(applicationSupportServicesTypeComment);
        return ApiResponse.ok(applicationSupportServicesTypeComment.getId());
    }

    @GetMapping("/application-service/{applicationSupportServiceId}/lastComment")
    public ApiResponse<ApplicationSupportServicesTypeCommentDto> getLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId(@PathVariable(name = "applicationSupportServiceId") Long applicationSupportServiceId) {
        return ApiResponse.ok(applicationSupportServicesTypeCommentMapper.map(applicationSupportServicesTypeCommentService.getLastApplicationSupportServicesTypeCommentByApplicationSupportServiceId(applicationSupportServiceId)));
    }

}
