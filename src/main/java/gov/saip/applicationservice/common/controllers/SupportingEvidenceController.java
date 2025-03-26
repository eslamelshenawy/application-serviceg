package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.mapper.SupportingEvidenceMapper;
import gov.saip.applicationservice.common.model.SupportingEvidence;
import gov.saip.applicationservice.common.service.SupportingEvidenceService;
import gov.saip.applicationservice.common.validators.SupportingEvidenceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = { "/internal-calling/supporting-evidence"})
@RequiredArgsConstructor
@Slf4j
public class SupportingEvidenceController {
    private final SupportingEvidenceService supportingEvidenceService;
    private final SupportingEvidenceMapper supportingEvidenceMapper;
    private final SupportingEvidenceValidator SupportingEvidenceValidator;


    @GetMapping("")
    public  ApiResponse<PaginationDto<List<SupportingEvidenceDto>>> getSupportingEvidenceForApplicationsInf(@RequestParam(value = "applicationId",         required = true)        Long appId,
                                      @RequestParam(value = "page",                  defaultValue = "1")     Integer page,
                                      @RequestParam(value = "limit",                 defaultValue = "10")    Integer limit) {

        return ApiResponse.ok(supportingEvidenceService.getSupportingEvidenceForApplicationsInfo(appId,page,limit));
    }
    @DeleteMapping("/delete/{supportEvId}")
    ApiResponse<String> deleteSupportingEvidence(@PathVariable(name="supportEvId")Long supportEvId){
        supportingEvidenceService.deleteBySupportEvIdId(supportEvId);
        return ApiResponse.ok("Deleted");
    }

    @PostMapping("")
    public void updateSupportingEvidence(@RequestBody SupportingEvidenceDto supportingEvidenceDto) {
        SupportingEvidenceValidator.validate(supportingEvidenceDto,null);
        SupportingEvidence entity = supportingEvidenceMapper.unMap(supportingEvidenceDto);
        supportingEvidenceService.update(entity);
    }
    @PutMapping("")
    public void saveSupportingEvidence(@RequestBody SupportingEvidenceDto supportingEvidenceDto) {
        // We need Validation in applicationId
        SupportingEvidenceValidator.validate(supportingEvidenceDto,null);
        supportingEvidenceService.updateSupportingEvidence(supportingEvidenceDto);
    }

    @PostMapping("/add-list")
    public void addSupportingEvidence(@RequestBody List<SupportingEvidenceDto> supportingEvidenceDtoList) {
        supportingEvidenceDtoList.forEach(this::updateSupportingEvidence);
    }


}
