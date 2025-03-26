package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ChangeOwnershipRequestDto;
import gov.saip.applicationservice.common.dto.agency.TrademarkAgencyRequestDto;
import gov.saip.applicationservice.common.mapper.ChangeOwnershipRequestMapper;
import gov.saip.applicationservice.common.model.ChangeOwnershipRequest;
import gov.saip.applicationservice.common.service.ChangeOwnershipRequestService;
import gov.saip.applicationservice.util.SupportServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.OWNERSHIP_CHANGE;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/kc/support-service/change-ownership","/internal-calling/support-service/change-ownership"})
public class ChangeOwnershipRequestController extends BaseController<ChangeOwnershipRequest, ChangeOwnershipRequestDto, Long> {
    private final ChangeOwnershipRequestService changeOwnershipRequestService;
    private final ChangeOwnershipRequestMapper changeOwnershipRequestMapper;
    private final SupportServiceValidator supportServiceValidator;
    @Override
    protected BaseService<ChangeOwnershipRequest, Long> getService() {
        return changeOwnershipRequestService;
    }

    @Override
    protected BaseMapper<ChangeOwnershipRequest, ChangeOwnershipRequestDto> getMapper() {
        return changeOwnershipRequestMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<ChangeOwnershipRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(changeOwnershipRequestMapper.map(changeOwnershipRequestService.getAllByApplicationId(appId, OWNERSHIP_CHANGE)));
}
    @GetMapping("/service/{id}")
    public ApiResponse<ChangeOwnershipRequestDto> getOwnershipRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(changeOwnershipRequestService.getChangeOwnershipRequestByApplicationSupportServiceId(id));
    }

    @PostMapping("/process-request/{id}")
    public ApiResponse<Void> processApprovedChangeOwnershipRequest(@PathVariable(name="id")Long id) {
        changeOwnershipRequestService.processApprovedChangeOwnershipRequest(id);
        return ApiResponse.noContent();
    }

    @GetMapping("/agent-authored-client")
    public ApiResponse<TrademarkAgencyRequestDto> getTrademarkAgencyRequestByRequestNumber(@RequestParam("requestNumber") String requestNumber, @RequestParam("applicationId") Long applicationId) {
        TrademarkAgencyRequestDto trademarkAgencyRequestDto = changeOwnershipRequestService.getTrademarkAgencyRequestByRequestNumber(requestNumber, applicationId);
        return ApiResponse.ok(trademarkAgencyRequestDto);
    }

}
