package gov.saip.applicationservice.modules.ic.controller;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitDto;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitListDto;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitRequestDto;
import gov.saip.applicationservice.modules.ic.service.IntegratedCircuitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = {"/kc/integrated-circuit", "/internal-calling/integrated-circuit"})
@RequiredArgsConstructor
@Slf4j
public class IntegratedCircuitController {

    private final IntegratedCircuitService integratedCircuitService;

    @PostMapping
    public ApiResponse<Long> save(@Valid @RequestBody ApplicantsRequestDto applicantsRequestDto) {
        return ApiResponse.ok(integratedCircuitService.saveApplication(applicantsRequestDto));
    }

    @PutMapping("/application")
    public ApiResponse<Long> updateWithApplication(@RequestBody IntegratedCircuitDto integratedCircuitDto) {
        return ApiResponse.ok(integratedCircuitService.updateWithApplication(integratedCircuitDto));
    }

    @GetMapping
    public ApiResponse<PaginationDto<List<IntegratedCircuitListDto>>> listApplications(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        PaginationDto<List<IntegratedCircuitListDto>> applicationListByApplicationCategoryAndUserId =
                integratedCircuitService.listIntegratedCircuitsApplication(page, limit, query, status, sortDirection);
        return ApiResponse.ok(applicationListByApplicationCategoryAndUserId);
    }

    @GetMapping("/info/{applicationId}")
    public ApiResponse<IntegratedCircuitRequestDto> getIntegratedCircuitsApplicationInfo(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(integratedCircuitService.getIntegratedCircuitsApplicationInfo(applicationId));
    }

    @PutMapping("/approvedNames")
    public ApiResponse updateIntegratedCircuitApprovedNames(@RequestBody ApplicationNameDto applicationNameDto) {
        integratedCircuitService.updateIntegratedCircuitApprovedNames(applicationNameDto);
        return ApiResponse.ok(null);
    }

    @PostMapping("/pdf/{reportName}")
    ApiResponse<List<DocumentDto>> generateJasperPdf(@RequestBody ReportRequestDto dto , @PathVariable(name = "reportName") String reportName, @RequestParam(value = "documentType") DocumentTypeEnum documentType) throws IOException {
        List<DocumentDto>  documentDtos= integratedCircuitService.generateJasperPdf(dto,reportName, documentType);
        return ApiResponse.ok(documentDtos);
    }

    @GetMapping(value = "/merge-files/application/{applicationId}")
    public ApiResponse mergeFrontAndBackShapesDocumentsByApplicationId(@PathVariable(name = "applicationId") Long applicationId) {
        integratedCircuitService.mergeFrontAndBackShapesDocumentsByApplicationId(applicationId);
        return ApiResponse.ok();
    }

    @PutMapping("/application/{applicationId}/status/{statusCode}/firstAssignationDate")
    ApiResponse updateStatusAndSetFirstAssignationDateByApplicationId(@PathVariable(name = "applicationId") Long applicationId,  @PathVariable(name="statusCode") ApplicationStatusEnum statusCode) {
        integratedCircuitService.updateStatusAndSetFirstAssignationDateByApplicationId(applicationId, statusCode);
        return ApiResponse.ok();
    }

    @PutMapping("/updateDatesValues/application/{applicationId}")
    public ApiResponse deleteInternalUserDateValuesByApplicationId(@PathVariable(name = "applicationId") Long applicationId) {
        integratedCircuitService.deleteInternalUserDateValuesByApplicationId(applicationId);
        return ApiResponse.ok();
    }
}
