package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.RevokeLicenceRequestApplicationSummaryDto;
import gov.saip.applicationservice.common.dto.RevokeLicenceRequestDto;
import gov.saip.applicationservice.common.mapper.RevokeLicenceRequestMapper;
import gov.saip.applicationservice.common.model.RevokeLicenceRequest;
import gov.saip.applicationservice.common.service.RevokeLicenceRequestService;
import gov.saip.applicationservice.util.SupportServiceValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_LICENSE_REQUEST;

@AllArgsConstructor
@RestController
@RequestMapping({"/kc/support-service/revoke-licence-request", "/internal-calling/support-service/revoke-licence-request"})
public class RevokeLicenceRequestController extends BaseController<RevokeLicenceRequest, RevokeLicenceRequestDto, Long> {

    private final RevokeLicenceRequestService revokeLicenceRequestService;
    private final RevokeLicenceRequestMapper revokeLicenceRequestMapper;
    private final SupportServiceValidator supportServiceValidator;
    @Override
    protected BaseService<RevokeLicenceRequest, Long> getService() {
        return revokeLicenceRequestService;
    }

    @Override
    protected BaseMapper<RevokeLicenceRequest, RevokeLicenceRequestDto> getMapper() {
        return revokeLicenceRequestMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<RevokeLicenceRequestDto>> getAllByApplicationID(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(revokeLicenceRequestMapper.map(revokeLicenceRequestService.getAllByApplicationId(appId, REVOKE_LICENSE_REQUEST)));

    }

    @GetMapping("/service/{id}")
    public ApiResponse<RevokeLicenceRequestDto> getByID(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(revokeLicenceRequestService.findByServiceId(id));
    }

    @GetMapping("/{id}/allInvolvedUsers")
    public ApiResponse<List<CustomerSampleInfoDto>> getLicenseRequestAllInvolvedUsersInfo(@PathVariable(name = "id") Long id) {
        List<CustomerSampleInfoDto> licenseRequestUsersDetails = revokeLicenceRequestService.getLicenseRequestAllInvolvedUsersInfo(id);
        return ApiResponse.ok(licenseRequestUsersDetails);
    }

    @GetMapping("/{id}/allApplicationSummary")
    public ApiResponse<RevokeLicenceRequestApplicationSummaryDto> getApplicationSummaryByRequestLicenseId(@PathVariable(name = "id") Long id) {
        RevokeLicenceRequestApplicationSummaryDto revokeLicenceRequestApplicationSummaryDto = revokeLicenceRequestService.getApplicationSummaryByRevokeLicenseRequestId(id);
        return ApiResponse.ok(revokeLicenceRequestApplicationSummaryDto);
    }

    @GetMapping("/{id}/license/id")
    public ApiResponse<Long> getLicenseRequestIdByRevokeLicenseId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(revokeLicenceRequestService.getLicenseRequestIdByRevokeLicenseId(id));
    }

}
