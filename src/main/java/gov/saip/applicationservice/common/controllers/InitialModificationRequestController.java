package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.InitialModificationRequestDto;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.mapper.InitialModificationRequestMapper;
import gov.saip.applicationservice.common.model.InitialModificationRequest;
import gov.saip.applicationservice.common.service.InitialModificationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service/initial-modification", "/internal-calling/support-service/initial-modification"})
@RequiredArgsConstructor
@Slf4j
public class InitialModificationRequestController extends BaseController<InitialModificationRequest, InitialModificationRequestDto, Long> {

    private final InitialModificationRequestService initialModificationRequestService;
    private final InitialModificationRequestMapper initialModificationRequestMapper;
    @Override
    protected BaseService<InitialModificationRequest, Long> getService() {
        return initialModificationRequestService;
    }

    @Override
    protected BaseMapper<InitialModificationRequest, InitialModificationRequestDto> getMapper() {
        return initialModificationRequestMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<InitialModificationRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(initialModificationRequestMapper.map(initialModificationRequestService.getAllByApplicationId(appId, SupportServiceType.INITIAL_MODIFICATION)));
    }

    @GetMapping("/service/{id}")
    public ApiResponse<InitialModificationRequestDto> getInitialModificationRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(initialModificationRequestMapper.map(initialModificationRequestService.findById(id)));
    }

    @PutMapping("/{appId}/finish")
    public ApiResponse finishInitialModificationRequestByApplicationId(@PathVariable(name = "appId")Long appId) {
        initialModificationRequestService.finishInitialModificationRequestByApplicationId(appId);
        return ApiResponse.ok();
    }

}
