package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.OppositionRevokeLicenceRequestMapper;
import gov.saip.applicationservice.common.model.OppositionRevokeLicenceRequest;
import gov.saip.applicationservice.common.service.OppositionRevokeLicenceRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.REVOKE_LICENSE_REQUEST;

@AllArgsConstructor
@RestController
@RequestMapping({"/kc/support-service/opposition-revoke-licence-request", "/internal-calling/support-service/opposition-revoke-licence-request"})
public class OppositionRevokeLicenceRequestController extends BaseController<OppositionRevokeLicenceRequest, OppositionRevokeLicenceRequestDto, Long> {
    private final OppositionRevokeLicenceRequestMapper oppositionRevokeLicenceRequestMapper;
    private final OppositionRevokeLicenceRequestService oppositionRevokeLicenceRequestService;
    @Override
    protected BaseService<OppositionRevokeLicenceRequest, Long> getService() {
        return oppositionRevokeLicenceRequestService;
    }

    @Override
    protected BaseMapper<OppositionRevokeLicenceRequest, OppositionRevokeLicenceRequestDto> getMapper() {
        return oppositionRevokeLicenceRequestMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<OppositionRevokeLicenceRequestDto>> getAllByApplicationID(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(oppositionRevokeLicenceRequestMapper.map(oppositionRevokeLicenceRequestService.getAllByApplicationId(appId, REVOKE_LICENSE_REQUEST)));

    }

    @GetMapping("/service/{id}")
    public ApiResponse<OppositionRevokeLicenceRequestDto> getByID(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(oppositionRevokeLicenceRequestService.findByServiceId(id));
    }

    @GetMapping("/{id}/allApplicationSummary")
    public ApiResponse<OppositionRevokeLicenceRequestApplicationSummaryDto> getApplicationSummaryByOppositionRequestLicenseId(@PathVariable(name = "id") Long id) {
        OppositionRevokeLicenceRequestApplicationSummaryDto oppositionRevokeLicenceRequestApplicationSummaryDto = oppositionRevokeLicenceRequestService.getApplicationSummaryByOppositionRequestLicenseId(id);
        return ApiResponse.ok(oppositionRevokeLicenceRequestApplicationSummaryDto);
    }

    @GetMapping("/OppositionIdByRevokeLicenceRequest/revoke-licence-request/{revokeLicenceRequestId}")
    public ApiResponse<Long> getUnderProcedureOppositionIdByRevokeLicenceRequest(@PathVariable(name = "revokeLicenceRequestId") Long revokeLicenceRequestId) {
        return ApiResponse.ok(oppositionRevokeLicenceRequestService.getUnderProcedureOppositionIdByRevokeLicenceRequest(revokeLicenceRequestId));
    }

    @PutMapping("/{id}/withdraw")
    public ApiResponse withdrawOppositionRevokeLicenseRequest(@PathVariable(name = "id") Long id) {
        oppositionRevokeLicenceRequestService.withdrawOppositionRevokeLicenseRequest(id);
        return ApiResponse.ok();
    }

    @PutMapping("/courtDocuments")
    public ApiResponse updateOppositionRevokeLicenseRequestWithCourtDocuments(@RequestBody OppositionRevokeLicenceCourtDocumentsDto oppositionRevokeLicenceCourtDocumentsDto) {
        oppositionRevokeLicenceRequestService.updateOppositionRevokeLicenseRequestWithCourtDocuments(oppositionRevokeLicenceCourtDocumentsDto);
        return ApiResponse.ok();
    }

    @GetMapping("/request-number-by/revoke-licence-request/{revokeLicenseId}")
    public ApiResponse<String> getUnderProcedureOppositionRevokeLicenceRequestNumberByRevokeLicenseId(@PathVariable(name = "revokeLicenseId") Long revokeLicenseId) {
        return ApiResponse.ok(oppositionRevokeLicenceRequestService.getUnderProcedureOppositionRevokeLicenceRequestNumberByRevokeLicenseId(revokeLicenseId));
    }
}
