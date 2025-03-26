package gov.saip.applicationservice.modules.ic.controller;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.modules.ic.dto.ApplicationLegalDocumentDto;
import gov.saip.applicationservice.modules.ic.dto.LegalDocumentListDto;
import gov.saip.applicationservice.modules.ic.mapper.ApplicationLegalDocumentMapper;
import gov.saip.applicationservice.modules.ic.model.ApplicationLegalDocument;
import gov.saip.applicationservice.modules.ic.service.ApplicationLegalDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/application-legal-document", "/internal-calling/application-legal-document"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationLegalDocumentController {

    private final ApplicationLegalDocumentService applicationLegalDocumentService;
    private final ApplicationLegalDocumentMapper applicationLegalDocumentMapper;


    @PostMapping
    public ApiResponse<Long> save(@RequestBody ApplicationLegalDocumentDto dto) {
        ApplicationLegalDocument legalDocument = applicationLegalDocumentMapper.unMap(dto);
        return ApiResponse.ok(applicationLegalDocumentService.insert(legalDocument).getId());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> softDeleteDocumentById(@PathVariable Long id) {
        return ApiResponse.ok(applicationLegalDocumentService.softDeleteLegalDocumentById(id));
    }

    @GetMapping("/application/{applicationId}")
    public ApiResponse<List<LegalDocumentListDto>> findApplicationLegalDocumentsByApplicationId(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(applicationLegalDocumentService.findApplicationLegalDocumentsByApplicationId(applicationId));
    }

}
