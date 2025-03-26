package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderInternalDto;
import gov.saip.applicationservice.common.dto.RevokeByCourtOrderRequestDto;
import gov.saip.applicationservice.common.mapper.RevokeByCourtOrderMapper;
import gov.saip.applicationservice.common.model.RevokeByCourtOrder;
import gov.saip.applicationservice.common.service.RevokeByCourtOrderService;
import gov.saip.applicationservice.util.SupportServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_BY_COURT_ORDER;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping({"/kc/support-service/revoke-by-court-order", "/internal-calling/support-service/revoke-by-court-order"})
public class RevokeByCourtOrderController extends BaseController<RevokeByCourtOrder, RevokeByCourtOrderRequestDto, Long> {


    private final RevokeByCourtOrderMapper revokeByCourtOrderMapper;
    private final RevokeByCourtOrderService revokeByCourtOrderService;
    private final SupportServiceValidator supportServiceValidator;
    @Override
    protected BaseService<RevokeByCourtOrder, Long> getService() {
        return revokeByCourtOrderService;
    }

    @Override
    protected BaseMapper<RevokeByCourtOrder, RevokeByCourtOrderRequestDto> getMapper() {
        return revokeByCourtOrderMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<RevokeByCourtOrderRequestDto>> getAllRevokeByCourtOrderRequests(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(revokeByCourtOrderMapper.map(revokeByCourtOrderService.getAllByApplicationId(appId, REVOKE_BY_COURT_ORDER)));
    }

    @GetMapping("/service/{id}")
    public ApiResponse<RevokeByCourtOrderRequestDto> getRevokeByCourtOrderRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long applicationSupportServiceId) {
        return ApiResponse.ok(revokeByCourtOrderService.findByServiceId(applicationSupportServiceId));
    }

    @PutMapping("/internalData")
    public ApiResponse updateRevokeByCourtOrderRequestWithInternalData(@RequestBody RevokeByCourtOrderInternalDto revokeByCourtOrderInternalDto) {
        revokeByCourtOrderService.updateRevokeByCourtOrderRequestWithInternalData(revokeByCourtOrderInternalDto);
        return ApiResponse.ok();
    }


}
