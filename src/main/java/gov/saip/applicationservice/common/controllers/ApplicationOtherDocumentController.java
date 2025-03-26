package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationOtherDocumentDto;
import gov.saip.applicationservice.common.mapper.ApplicationOtherDocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.service.ApplicationOtherDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController()
@RequestMapping(value = {"/kc/other-document", "/internal-calling/other-document"})
@RequiredArgsConstructor
public class ApplicationOtherDocumentController extends BaseController<ApplicationOtherDocument, ApplicationOtherDocumentDto, Long> {

    private final ApplicationOtherDocumentService applicationOtherDocumentService;
    private final ApplicationOtherDocumentMapper applicationOtherDocumentMapper;
    @Override
    protected BaseService<ApplicationOtherDocument, Long> getService() {
        return applicationOtherDocumentService;
    }

    @Override
    protected BaseMapper<ApplicationOtherDocument, ApplicationOtherDocumentDto> getMapper() {
        return applicationOtherDocumentMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<ApplicationOtherDocumentDto>> getAllApplicationOtherDocuments(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationOtherDocumentMapper.map(applicationOtherDocumentService.getAllAppOtherDocumentsByApplicationId(appId)));
    }

}
