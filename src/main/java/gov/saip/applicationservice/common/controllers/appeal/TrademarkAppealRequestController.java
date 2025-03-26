package gov.saip.applicationservice.common.controllers.appeal;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.appeal.TrademarkAppealRequestDto;
import gov.saip.applicationservice.common.enums.support_services_enums.TrademarkAppealRequestType;
import gov.saip.applicationservice.common.mapper.appeal.TrademarkAppealRequestMapper;
import gov.saip.applicationservice.common.model.appeal.TrademarkAppealRequest;
import gov.saip.applicationservice.common.service.appeal.TrademarkAppealRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = {"/kc/tm-appeal-request", "/internal-calling/tm-appeal-request"})
@RequiredArgsConstructor
@Slf4j
public class TrademarkAppealRequestController extends BaseController<TrademarkAppealRequest, TrademarkAppealRequestDto, Long> {

    private final TrademarkAppealRequestService trademarkAppealRequestService;
    private final TrademarkAppealRequestMapper trademarkAppealRequestMapper;

    @Override
    protected BaseService<TrademarkAppealRequest, Long> getService() {
        return trademarkAppealRequestService;
    }

    @Override
    protected BaseMapper<TrademarkAppealRequest, TrademarkAppealRequestDto> getMapper() {
        return trademarkAppealRequestMapper;
    }

    @GetMapping("/service/{serviceId}")
    public ApiResponse<TrademarkAppealRequestDto> getAppealDetailsBySupportServiceId(@PathVariable(name="serviceId")Long serviceId) {
        return ApiResponse.ok(trademarkAppealRequestService.findDetailsByById(serviceId));
    }

    @PutMapping("/department-reply")
    public ApiResponse<Void> updateDepartmentReply(@RequestBody TrademarkAppealRequestDto dto) {
        trademarkAppealRequestService.updateDepartmentReply(dto.getId(), dto.getDepartmentReply());
        return ApiResponse.noContent();
    }

    @GetMapping("/appeal-type")
    public ApiResponse<TrademarkAppealRequestType> getTrademarkAppealRequestType(@RequestParam(name="id")Long applicationId) {
        TrademarkAppealRequestType trademarkAppealRequestType = trademarkAppealRequestService.getTrademarkAppealRequestType(applicationId);
        log.info("appeal type of application id {} is ==> {} ", applicationId, trademarkAppealRequestType);
        return ApiResponse.ok(trademarkAppealRequestType);
    }


    @PutMapping("/final-decision")
    public ApiResponse<Void> updateFinalDecision(@RequestBody TrademarkAppealRequestDto dto) {
        trademarkAppealRequestService.updateFinalDecision(dto.getId(), dto.getFinalDecisionNotes());
        return ApiResponse.noContent();
    }
    @PutMapping("/time-out/{id}")
    public ApiResponse<Void> handleApplicantTaskTimeout(@PathVariable(name = "id") Long id) {
        trademarkAppealRequestService.handleApplicantTaskTimeout(id);
        return ApiResponse.noContent();
    }
}
