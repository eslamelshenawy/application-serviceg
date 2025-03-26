package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicantInventorDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantRequestsDto;
import gov.saip.applicationservice.common.dto.InventorRequestsDto;
import gov.saip.applicationservice.common.service.ApplicationRelevantTypeService;
import gov.saip.applicationservice.modules.ic.dto.ApplicationApplicantDto;
import gov.saip.applicationservice.modules.ic.dto.InventorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = {"/kc/application-relevant", "/internal-calling/application-relevant"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationRelevantTypeController {

    public final ApplicationRelevantTypeService applicationRelevantTypeService;

    @PutMapping(value = "/update-document")
        public void updateDocument(@RequestParam(value = "id") Long id , @RequestParam(value = "documentId") Long documentId ) {
        applicationRelevantTypeService.updateDocument(id , documentId);
    }

    @PostMapping("/inventors")
    public ApiResponse<Long> addInventorPatch(@RequestBody InventorRequestsDto inventorRequestsDto) {
        return ApiResponse.ok(applicationRelevantTypeService.addInventorPatch(inventorRequestsDto));
    }

    @GetMapping("/application/{applicationId}/inventors")
    public ApiResponse<List<InventorDto>> getAllInventorsExceptApplicantsByApplication(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(applicationRelevantTypeService.getAllInventorsExceptApplicantsByApplication(applicationId));
    }

    @GetMapping("/application/{applicationId}/applicants")
    public ApiResponse<List<InventorDto>> getApplicantsByApplication(@PathVariable(value = "applicationId") Long applicationId,
                                                                                @RequestParam(value = "inventor", required = false) Boolean inventor) {
        return ApiResponse.ok(applicationRelevantTypeService.getApplicantsByApplication(applicationId, inventor));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> deleteById(@PathVariable(value = "id") Long id) {
        return ApiResponse.ok(applicationRelevantTypeService.softDeleteById(id));
    }

    @PostMapping("/inventor")
    public ApiResponse<Long> addInventor(@RequestBody ApplicationRelevantRequestsDto requestDto) {
        return ApiResponse.ok(applicationRelevantTypeService.addInventor(requestDto));
    }

    @PutMapping("/applicants-inventor")
    public ApiResponse<Long> updateApplicantInventors(@RequestBody ApplicantInventorDto applicantInventorDto) {
        return ApiResponse.ok(applicationRelevantTypeService.updateApplicantInventors(applicantInventorDto));
    }

    @PostMapping("/secondary-applicant")
    public ApiResponse<Long> addSecondaryApplicant(@RequestBody ApplicationRelevantRequestsDto requestDto) {
        return ApiResponse.ok(applicationRelevantTypeService.addSecondaryApplicant(requestDto));
    }

    @GetMapping("/application/{applicationId}/mainApplicant")
    public ApiResponse<ApplicationApplicantDto> getMainApplicantInfoByApplicationId(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(applicationRelevantTypeService.getMainApplicantInfoByApplicationId(applicationId));
    }

}

