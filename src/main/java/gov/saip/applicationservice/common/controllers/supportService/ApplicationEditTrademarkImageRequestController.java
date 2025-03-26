package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.supportService.ApplicationEditTrademarkImageRequestDto;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationEditTrademarkImageRequestMapper;
import gov.saip.applicationservice.common.model.supportService.application_edit_trademark_image_request.ApplicationEditTrademarkImageRequest;
import gov.saip.applicationservice.common.service.supportService.ApplicationEditTrademarkImageRequestService;
import gov.saip.applicationservice.util.SupportServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({"/kc/support-service/application-edit-trademark-image-request", "/internal-calling/support-service/application-edit-trademark-image-request"})
@RequiredArgsConstructor
public class ApplicationEditTrademarkImageRequestController extends BaseController<ApplicationEditTrademarkImageRequest, ApplicationEditTrademarkImageRequestDto, Long> {
    
    private final ApplicationEditTrademarkImageRequestService applicationEditTrademarkImageRequestService;
    private final ApplicationEditTrademarkImageRequestMapper applicationEditTrademarkImageRequestMapper;
    private final SupportServiceValidator supportServiceValidator;

    @Override
    protected BaseService<ApplicationEditTrademarkImageRequest, Long> getService() {
        return applicationEditTrademarkImageRequestService;
    }

    @Override
    protected BaseMapper<ApplicationEditTrademarkImageRequest, ApplicationEditTrademarkImageRequestDto> getMapper() {
        return applicationEditTrademarkImageRequestMapper;
    }

    @GetMapping("/service/{serviceId}")
    public ApiResponse<ApplicationEditTrademarkImageRequestDto> getDetailsBySupportServiceId(@PathVariable(name="serviceId")Long serviceId){
        return ApiResponse.ok(applicationEditTrademarkImageRequestService.getDetailsBySupportServiceId(serviceId));
    }

}
