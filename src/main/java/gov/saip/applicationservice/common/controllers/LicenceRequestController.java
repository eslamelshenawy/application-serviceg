package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.supportService.license.LicenceListSortingColumn;
import gov.saip.applicationservice.common.dto.supportService.license.OppositionRevokeLicenceListSortingColumn;
import gov.saip.applicationservice.common.dto.supportService.license.RevokeLicenceListSortingColumn;
import gov.saip.applicationservice.common.enums.LicenceTypeEnum;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.mapper.LicenceRequestMapper;
import gov.saip.applicationservice.common.model.LicenceRequest;
import gov.saip.applicationservice.common.service.LicenceRequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.LICENSING_REGISTRATION;

@AllArgsConstructor
@RestController
@RequestMapping({"/kc/support-service/licence-request", "/internal-calling/support-service/licence-request"})
public class LicenceRequestController extends BaseController<LicenceRequest, LicenceRequestDto, Long> {

    private final LicenceRequestService licenceRequestService;
    private final LicenceRequestMapper licenceRequestMapper;
    @Override
    protected BaseService<LicenceRequest, Long> getService() {
        return licenceRequestService;
    }

    @Override
    protected BaseMapper<LicenceRequest, LicenceRequestDto> getMapper() {
        return  licenceRequestMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<LicenceRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(licenceRequestMapper.map(licenceRequestService.getAllByApplicationId(appId, LICENSING_REGISTRATION)));

    }


    @GetMapping("/service/{id}")
    public ApiResponse<LicenceRequestDto> getEvictionRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(licenceRequestService.getLicenceRequest(id));
    }

    @GetMapping("/application/{applicationInfoId}/licenced-customers")
    public ApiResponse<List<CustomerSampleInfoDto>> getLicensedCustomersDetails(@PathVariable(name = "applicationInfoId") Long applicationInfoId) {
        List<CustomerSampleInfoDto> licensedCustomersDetails = licenceRequestService.getLicensedCustomersDetails(applicationInfoId);
        return ApiResponse.ok(licensedCustomersDetails);
    }

    @GetMapping("/{id}/allInvolvedUsers")
    public ApiResponse<List<CustomerSampleInfoDto>> getLicenseRequestAllInvolvedUsersInfo(@PathVariable(name = "id") Long id) {
        List<CustomerSampleInfoDto> licenseRequestUsersDetails = licenceRequestService.getLicenseRequestAllInvolvedUsersInfo(id);
        return ApiResponse.ok(licenseRequestUsersDetails);
    }

    @GetMapping("/{id}/allApplicationSummary")
    public ApiResponse<LicenceRequestApplicationSummaryDto> getApplicationSummaryByRequestLicenseId(@PathVariable(name = "id") Long id) {
        LicenceRequestApplicationSummaryDto licenceRequestApplicationSummary = licenceRequestService.getApplicationSummaryByRequestLicenseId(id);
        return ApiResponse.ok(licenceRequestApplicationSummary);
    }
    @GetMapping("/licenced")
    public ApiResponse<PaginationDto<List<LicenceRequestListDto>>> getAllApprovedLicenseRequests(@RequestParam(name="query",required = false) String query,
                                                                                                 @RequestParam(name="page") Integer page,
                                                                                                 @RequestParam(name="limit") Integer limit,
                                                                                                 @RequestParam(name="sortOrder", defaultValue = "MODIFIED_DATE") LicenceListSortingColumn sortOrder,
                                                                                                 @RequestParam(name="sortDirection", defaultValue = "DESC") Sort.Direction sortDirection,
                                                                                                 @RequestParam(name="status", required = false) SupportServiceRequestStatusEnum status) {
        PaginationDto<List<LicenceRequestListDto>> licenceRequestDto = licenceRequestService.getAllApprovedLicenseRequests(query, page, limit, sortOrder, sortDirection, status);
        return ApiResponse.ok(licenceRequestDto);
    }

    @GetMapping("/revoked")
    public ApiResponse<PaginationDto<List<LicenceRequestListDto>>> getAllRevokedLicenseRequests(@RequestParam(name="query",required = false) String query,
                                                                                                @RequestParam(name="page") Integer page,
                                                                                                @RequestParam(name="limit") Integer limit,
                                                                                                @RequestParam(name="sortOrder", defaultValue = "MODIFIED_DATE") RevokeLicenceListSortingColumn sortOrder,
                                                                                                @RequestParam(name="sortDirection", defaultValue = "DESC") Sort.Direction sortDirection,
                                                                                                @RequestParam(name="status", required = false) SupportServiceRequestStatusEnum status){
        PaginationDto<List<LicenceRequestListDto>> revokedLicenseRequests = licenceRequestService.getAllRevokedLicenseRequests(query, page, limit, sortOrder, sortDirection, status);
        return ApiResponse.ok(revokedLicenseRequests);
    }

    @GetMapping("/opposition")
    public ApiResponse<PaginationDto<List<LicenceRequestListDto>>> getAllOppositionLicenseRequests(@RequestParam(name="query",required = false) String query,
                                                                                                   @RequestParam(name="page") Integer page,
                                                                                                   @RequestParam(name="limit") Integer limit,
                                                                                                   @RequestParam(name="sortOrder", defaultValue = "MODIFIED_DATE") OppositionRevokeLicenceListSortingColumn sortOrder,
                                                                                                   @RequestParam(name="sortDirection", defaultValue = "DESC") Sort.Direction sortDirection,
                                                                                                   @RequestParam(name="status", required = false) SupportServiceRequestStatusEnum status){
        PaginationDto<List<LicenceRequestListDto>> oppositionRevokedLicenseRequests = licenceRequestService.getAllOppositionLicenseRequests(query, page, limit,sortOrder, sortDirection, status);
        return ApiResponse.ok(oppositionRevokedLicenseRequests);
    }

    @GetMapping("/application/{applicationId}")
    public ApiResponse<Boolean> checkApplicationHaveLicence(@PathVariable("applicationId") Long applicationId){
        return ApiResponse.ok(licenceRequestService.checkApplicationHaveLicence(applicationId));
    }
    @GetMapping("/licenced/{applicationId}")
    public ApiResponse<List<LicenceRequestListDto>> getAllApprovedLicensedRequests(@PathVariable("applicationId") Long applicationId ,@RequestParam(name = "licenceType",required = false) LicenceTypeEnum licenceType){
        return ApiResponse.ok(licenceRequestService.getAllApprovedLicensedRequests(applicationId,licenceType));
    }
    @PutMapping("/licenced/{applicationId}")
    public ApiResponse<Void> changeLicenceValidityNumber(@PathVariable("applicationId") Long applicationId ,@RequestParam(name = "licenceValidityNumber",required = true) Integer licenceValidityNumber,@RequestParam(name = "licenceType",required = true) LicenceTypeEnum licenceType, @RequestParam(name = "mainRequestId") Long mainRequestId){
        licenceRequestService.changeLicenceValidityNumber(applicationId,licenceValidityNumber,licenceType, mainRequestId);
        return ApiResponse.noContent();

    }
    @PutMapping("/cancel/licence/{applicationId}")
    public ApiResponse<Void> makeCancelLicenceRequest(@PathVariable("applicationId") Long applicationId, @RequestParam("mainRequestId") Long mainRequestId){
        licenceRequestService.makeCancelLicenceRequest(applicationId, mainRequestId);
        return ApiResponse.noContent();

    }
    @Override
    @PutMapping
    public ApiResponse<LicenceRequestDto> update( @RequestBody LicenceRequestDto licenceRequestDto) {
        LicenceRequestDto updatedRequest = licenceRequestService.updateLicenceRequest(licenceRequestDto);
        return ApiResponse.ok(updatedRequest);
    }
}
