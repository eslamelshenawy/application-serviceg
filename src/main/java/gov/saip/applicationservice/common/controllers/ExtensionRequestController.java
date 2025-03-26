package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ExtensionRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.ExtensionRequestMapper;
import gov.saip.applicationservice.common.model.ExtensionRequest;
import gov.saip.applicationservice.common.service.ExtensionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service/extension", "/internal-calling/support-service/extension"})
@RequiredArgsConstructor
@Slf4j
public class ExtensionRequestController extends BaseController<ExtensionRequest, ExtensionRequestDto, Long> {
    
    private final ExtensionRequestService extensionRequestService;
    private final ExtensionRequestMapper extensionRequestMapper;
    @Override
    protected BaseService<ExtensionRequest, Long> getService() {
        return  extensionRequestService;
    }

    @Override
    protected BaseMapper<ExtensionRequest, ExtensionRequestDto> getMapper() {
        return extensionRequestMapper;
    }
    
    @GetMapping("/{appId}/application")
    public ApiResponse<List<ExtensionRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(extensionRequestMapper.map(extensionRequestService.getAllByApplicationId(appId, SupportServiceType.EXTENSION)));
    }
    
    @GetMapping("/service/{id}")
    public ApiResponse<ExtensionRequestDto> getExtensionRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(extensionRequestMapper.map(extensionRequestService.findById(id)));
    }
    
}
