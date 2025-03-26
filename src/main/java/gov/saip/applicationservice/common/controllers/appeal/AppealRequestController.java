package gov.saip.applicationservice.common.controllers.appeal;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.appeal.AppealDetailsDto;
import gov.saip.applicationservice.common.dto.appeal.AppealRequestDto;
import gov.saip.applicationservice.common.dto.appeal.AppealSupportServiceRequestDto;
import gov.saip.applicationservice.common.mapper.appeal.AppealRequestMapper;
import gov.saip.applicationservice.common.model.appeal.AppealRequest;
import gov.saip.applicationservice.common.service.appeal.AppealRequestService;
import gov.saip.applicationservice.util.SupportServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = {"/kc/appeal-request", "/internal-calling/appeal-request"})
@RequiredArgsConstructor
public class AppealRequestController extends BaseController<AppealRequest, AppealRequestDto, Long> {

    private final AppealRequestService appealRequestService;
    private final AppealRequestMapper appealRequestMapper;
    private final SupportServiceValidator supportServiceValidator;

    @Override
    protected BaseService<AppealRequest, Long> getService() {
        return appealRequestService;
    }

    @Override
    protected BaseMapper<AppealRequest, AppealRequestDto> getMapper() {
        return appealRequestMapper;
    }
    @PutMapping("/checker-decision")
    public ApiResponse<Long> addCheckerDecision(@RequestBody AppealRequestDto dto) {
        Long result = appealRequestService.addCheckerDecision(dto);
        return ApiResponse.ok(result);
    }

    @PutMapping("/update-appeal-request")
    public ApiResponse<Long> updateAppealRequest(@RequestBody AppealRequestDto appealRequestDto , @RequestParam(name="taskId" , required = true) String taskId) {
        Long result = appealRequestService.updateAppealRequest(appealRequestDto , taskId);
        return ApiResponse.ok(result);
    }

    @PutMapping("/official-letter")
    public ApiResponse<Long> addOfficialLetter(@RequestBody AppealRequestDto dto) {
        Long result = appealRequestService.addOfficialLetter(dto);
        return ApiResponse.ok(result);
    }


    @PutMapping("/committee-decision")
    public ApiResponse<Long> addAppealCommitteeDecision(@RequestBody AppealRequestDto dto) {
        Long result = appealRequestService.addAppealCommitteeDecision(dto);
        return ApiResponse.ok(result);
    }
    @GetMapping("/details/{appealId}")
    public ApiResponse<AppealDetailsDto> getTradeMarkAppealDetails(@PathVariable(name="appealId")String appealId){
        return ApiResponse.ok(appealRequestService.getTradeMarkAppealDetails(Long.valueOf(appealId)));
    }

    @GetMapping("/service/{serviceId}")
    public ApiResponse<AppealSupportServiceRequestDto> getAppealDetailsBySupportServiceId(@PathVariable(name="serviceId")Long serviceId) {
        AppealRequest appealRequest = appealRequestService.findById(serviceId);
        AppealSupportServiceRequestDto requestDto = appealRequestMapper.mapAppealSupportServiceRequestDto(appealRequest);
        return ApiResponse.ok(requestDto);
    }


}
