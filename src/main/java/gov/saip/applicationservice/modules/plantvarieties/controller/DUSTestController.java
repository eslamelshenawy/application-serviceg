package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentListDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsListDto;
import gov.saip.applicationservice.modules.plantvarieties.mapper.DUSTestingDocumentMapper;
import gov.saip.applicationservice.modules.plantvarieties.mapper.OtherPlantVarietyDocumentsMapper;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;
import gov.saip.applicationservice.modules.plantvarieties.model.OtherPlantVarietyDocuments;
import gov.saip.applicationservice.modules.plantvarieties.service.DUSTestingDocumentPlantVarietyService;
import gov.saip.applicationservice.modules.plantvarieties.service.OtherDocumentPlantVarietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/plant-variety-details/dus-test", "/internal-calling/plant-variety-details/dus-test"})
@RequiredArgsConstructor
@Slf4j
public class DUSTestController {
    private final DUSTestingDocumentMapper dusTestingDocumentMapper;
    private final OtherPlantVarietyDocumentsMapper otherPlantVarietyDocumentsMapper;
    private final DUSTestingDocumentPlantVarietyService dusTestingDocumentPlantVarietyService;
    private final OtherDocumentPlantVarietyService otherDocumentPlantVarietyService;
    @PostMapping
    public ApiResponse<Long> save(@RequestBody DUSTestingDocumentDto dto) {
        DUSTestingDocument dusDocument = dusTestingDocumentMapper.unMap(dto);
        return ApiResponse.ok(dusTestingDocumentPlantVarietyService.insert(dusDocument).getId());
    }
    @PostMapping("/other-document")
    public ApiResponse<Long> save(@RequestBody OtherPlantVarietyDocumentsDto dto) {
        OtherPlantVarietyDocuments otherDocument = otherPlantVarietyDocumentsMapper.unMap(dto);
        return ApiResponse.ok(otherDocumentPlantVarietyService.insert(otherDocument).getId());
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Long> softDeleteDusTestDocumentById(@PathVariable Long id) {
        return ApiResponse.ok(dusTestingDocumentPlantVarietyService.softDeleteDusTestDocumentById(id));
    }
    @DeleteMapping("/other-document/{id}")
    public ApiResponse<Long> softDeleteOtherDocumentById(@PathVariable Long id) {
        return ApiResponse.ok(otherDocumentPlantVarietyService.softDeleteOtherDocumentById(id));
    }

    @GetMapping("/application/{applicationId}")
    public ApiResponse<List<DUSTestingDocumentListDto>> findApplicationDusTestDocumentsByApplicationId(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(dusTestingDocumentPlantVarietyService.findApplicationDusTestDocumentsByApplicationId(applicationId));
    }
    @GetMapping("/application/other-documents/{applicationId}")
    public ApiResponse<List<OtherPlantVarietyDocumentsListDto>> findApplicationOtherPlantVarietyDocumentsByApplicationId(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(otherDocumentPlantVarietyService.findApplicationOtherPlantVarietyDocumentsByApplicationId(applicationId));
    }
}
