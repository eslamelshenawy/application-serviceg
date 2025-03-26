package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LKSupportServiceTypeDto;
import gov.saip.applicationservice.common.mapper.LKSupportServiceTypeMapper;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import gov.saip.applicationservice.common.service.LKSupportServiceTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service-type", "/internal-calling/support-service-type"})
@RequiredArgsConstructor
@Slf4j
public class LKSupportServiceTypeController extends BaseController<LKSupportServiceType, LKSupportServiceTypeDto, Long> {

    private final LKSupportServiceTypeService agentSubstitutionRequestService;
    private final LKSupportServiceTypeMapper lkSupportServiceTypeMapper;
    @Override
    protected BaseService<LKSupportServiceType, Long> getService() {
        return agentSubstitutionRequestService;
    }

    @Override
    protected BaseMapper<LKSupportServiceType, LKSupportServiceTypeDto> getMapper() {
        return lkSupportServiceTypeMapper;
    }

    @GetMapping("/{requestType}/type")
    public ApiResponse<List<LKSupportServiceTypeDto>> getAllReports(@PathVariable(name = "requestType") String requestType,
                                                                    @RequestParam(value = "id", required = false) Long id) {
        return ApiResponse.ok(lkSupportServiceTypeMapper.map(agentSubstitutionRequestService.getAllByRequestType(requestType, id)));
    }

}
