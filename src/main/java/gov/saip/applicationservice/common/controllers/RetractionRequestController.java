package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.RetractionRequestDto;
import gov.saip.applicationservice.common.dto.RetractionTypesDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.RetractionRequestMapper;
import gov.saip.applicationservice.common.model.RetractionRequest;
import gov.saip.applicationservice.common.service.RetractionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service/retraction", "/internal-calling/support-service/retraction"})
@RequiredArgsConstructor
@Slf4j
public class RetractionRequestController extends BaseController<RetractionRequest, RetractionRequestDto, Long> {
    
    private final RetractionRequestService retractionRequestService;
    private final RetractionRequestMapper retractionRequestMapper;
    @Override
    protected BaseService<RetractionRequest, Long> getService() {
        return retractionRequestService;
    }

    @Override
    protected BaseMapper<RetractionRequest, RetractionRequestDto> getMapper() {
        return retractionRequestMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<RetractionRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(retractionRequestMapper.map(retractionRequestService.getAllByApplicationId(appId, SupportServiceType.RETRACTION)));
    }
    
    @GetMapping("/service/{id}")
    public ApiResponse<RetractionRequestDto> getRetractionRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(retractionRequestMapper.map(retractionRequestService.findById(id)));
    }
}
