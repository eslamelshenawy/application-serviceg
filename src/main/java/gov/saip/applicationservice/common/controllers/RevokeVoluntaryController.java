package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.RevokeVoluntaryRequestDto;
import gov.saip.applicationservice.common.mapper.RevokeVoluntaryRequestMapper;
import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;
import gov.saip.applicationservice.common.service.RevokeVoluntaryRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.VOLUNTARY_REVOKE;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping({"/kc/support-service/voluntary-revoke","/internal-calling/support-service/voluntry-revoke"})
public class RevokeVoluntaryController extends BaseController<RevokeVoluntaryRequest, RevokeVoluntaryRequestDto, Long> {


    private final RevokeVoluntaryRequestMapper revokeVoluntryRequestMapper;
    private final RevokeVoluntaryRequestService revokeVoluntryRequestService;

    @Override
    protected BaseService<RevokeVoluntaryRequest, Long> getService() {
        return revokeVoluntryRequestService;
    }

    @Override
    protected BaseMapper<RevokeVoluntaryRequest, RevokeVoluntaryRequestDto> getMapper() {
        return revokeVoluntryRequestMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<RevokeVoluntaryRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(revokeVoluntryRequestMapper.map(revokeVoluntryRequestService.getAllByApplicationId(appId, VOLUNTARY_REVOKE)));
    }

    @GetMapping("/service/{id}")
    public ApiResponse<RevokeVoluntaryRequestDto> getRevokeVoluntaryRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long applicationSupportServiceId) {
        return ApiResponse.ok(revokeVoluntryRequestMapper.map(revokeVoluntryRequestService.findById(applicationSupportServiceId)));
    }


}
