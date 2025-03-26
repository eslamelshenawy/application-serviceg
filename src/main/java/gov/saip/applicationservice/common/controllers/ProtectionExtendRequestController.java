package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ProtectionExtendRequestDto;
import gov.saip.applicationservice.common.mapper.ProtectionExtendRequestMapper;
import gov.saip.applicationservice.common.model.ProtectionExtendRequest;
import gov.saip.applicationservice.common.service.ProtectionExtendRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.PROTECTION_PERIOD_EXTENSION_REQUEST;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping({"/kc/support-service/protection-extend","/internal-calling/support-service/protection-extend"})
public class ProtectionExtendRequestController extends BaseController<ProtectionExtendRequest, ProtectionExtendRequestDto, Long> {
    private final ProtectionExtendRequestService protectionExtendRequestService;
    private final ProtectionExtendRequestMapper protectionExtendRequestMapper;
    @Override
    protected BaseService<ProtectionExtendRequest, Long> getService() {
        return protectionExtendRequestService;
    }

    @Override
    protected BaseMapper<ProtectionExtendRequest, ProtectionExtendRequestDto> getMapper() {
        return protectionExtendRequestMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<ProtectionExtendRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(protectionExtendRequestMapper.map(protectionExtendRequestService.getAllByApplicationId(appId, PROTECTION_PERIOD_EXTENSION_REQUEST)));
    }

    @GetMapping("/service/{id}")
    public ApiResponse<ProtectionExtendRequestDto> getProtectionExtendRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long applicationSupportServiceId) {
        return ApiResponse.ok(protectionExtendRequestMapper.map(protectionExtendRequestService.findById(applicationSupportServiceId)));
    }
}
