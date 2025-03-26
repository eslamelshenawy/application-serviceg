package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSimilarDocumentDto;
import gov.saip.applicationservice.common.mapper.ApplicationSimilarDocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import gov.saip.applicationservice.common.service.ApplicationSimilarDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/app-similar-docs", "/internal-calling/app-similar-docs"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationSimilarDocumentController extends BaseController<ApplicationSimilarDocument, ApplicationSimilarDocumentDto, Long> {

    private final ApplicationSimilarDocumentService applicationSimilarDocumentService;
    private final ApplicationSimilarDocumentMapper applicationSimilarDocumentMapper;
    @Override
    protected BaseService<ApplicationSimilarDocument, Long> getService() {
        return applicationSimilarDocumentService;
    }

    @Override
    protected BaseMapper<ApplicationSimilarDocument, ApplicationSimilarDocumentDto> getMapper() {
        return applicationSimilarDocumentMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<ApplicationSimilarDocumentDto>> getAllApplicationWords(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationSimilarDocumentMapper.map(applicationSimilarDocumentService.getAllByApplicationId(appId)));
    }

}
