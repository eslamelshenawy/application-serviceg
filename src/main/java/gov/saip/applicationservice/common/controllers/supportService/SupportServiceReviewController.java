package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.supportService.ApplicationSupportServicesTypeCommentDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceReviewDto;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationSupportServicesTypeCommentMapper;
import gov.saip.applicationservice.common.mapper.supportService.SupportServiceReviewMapper;
import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;
import gov.saip.applicationservice.common.model.supportService.SupportServiceReview;
import gov.saip.applicationservice.common.service.supportService.ApplicationSupportServicesTypeCommentService;
import gov.saip.applicationservice.common.service.supportService.SupportServiceReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service-review", "/internal-calling/support-service-review"})
@RequiredArgsConstructor
@Slf4j
public class SupportServiceReviewController extends BaseController<SupportServiceReview, SupportServiceReviewDto, Long> {

    private final SupportServiceReviewService supportServiceReviewService;
    private final SupportServiceReviewMapper supportServiceReviewMapper;

    @Override
    protected BaseService<SupportServiceReview, Long> getService() {
        return supportServiceReviewService;
    }

    @Override
    protected BaseMapper<SupportServiceReview, SupportServiceReviewDto> getMapper() {
        return supportServiceReviewMapper;
    }
    @GetMapping("/service/{serviceId}/reviews")
    public ApiResponse<List<SupportServiceReviewDto>> getReviewsByServiceId(@PathVariable(name = "serviceId") Long serviceId) {
        return ApiResponse.ok(supportServiceReviewMapper.map(supportServiceReviewService.getReviewsByServiceId(serviceId)));
    }

    @GetMapping("/my-review/service/{serviceId}")
    public ApiResponse<SupportServiceReviewDto> getEmployeeReviewByServiceId(@PathVariable(name = "serviceId") Long serviceId) {
        return ApiResponse.ok(supportServiceReviewMapper.map(supportServiceReviewService.getEmployeeReviewByServiceId(serviceId)));
    }

    @PutMapping("/updte-status")
    public ApiResponse<Void> updateReviewStatus(@RequestBody SupportServiceReviewDto dto) {
        supportServiceReviewService.updateReviewStatus(dto.getId(), dto.getReviewStatus());
        return ApiResponse.noContent();
    }


}
