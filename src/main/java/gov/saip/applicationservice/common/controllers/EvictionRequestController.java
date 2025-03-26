package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.EvictionRequestDto;
import gov.saip.applicationservice.common.mapper.EvictionRequestMapper;
import gov.saip.applicationservice.common.model.EvictionRequest;
import gov.saip.applicationservice.common.service.EvictionRequestService;
import gov.saip.applicationservice.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.EVICTION;

@RestController
@RequestMapping(value = {"/kc/support-service/eviction", "/internal-calling/support-service/eviction"})
@RequiredArgsConstructor
@Slf4j
public class EvictionRequestController extends BaseController<EvictionRequest, EvictionRequestDto, Long> {
    
    private final EvictionRequestService evictionRequestService;
    private final EvictionRequestMapper evictionRequestMapper;
    @Override
    protected BaseService<EvictionRequest, Long> getService() {
        return evictionRequestService;
    }

    @Override
    protected BaseMapper<EvictionRequest, EvictionRequestDto> getMapper() {
        return evictionRequestMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<EvictionRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(evictionRequestMapper.map(evictionRequestService.getAllByApplicationId(appId, EVICTION)));
    }
    
    @GetMapping("/service/{id}")
    public ApiResponse<EvictionRequestDto> getEvictionRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long applicationSupportServiceId) {
        return ApiResponse.ok(evictionRequestMapper.map(evictionRequestService.findById(applicationSupportServiceId)));
    }

    @Override
    @PostMapping("")
    public ApiResponse<Long> insert(@Valid @RequestBody EvictionRequestDto dto) {
        return ApiResponse.ok(evictionRequestService.createEvictionRequest(dto).getId());
    }
}
