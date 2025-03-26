package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationDocumentCommentDto;
import gov.saip.applicationservice.common.mapper.ApplicationDocumentCommentMapper;
import gov.saip.applicationservice.common.model.ApplicationDocumentComment;
import gov.saip.applicationservice.common.service.ApplicationDocumentCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController()
@RequestMapping(value = {"/kc/app-document-comment", "/internal-calling/app-document-comment"})
@RequiredArgsConstructor
public class ApplicationDocumentCommentController extends BaseController<ApplicationDocumentComment, ApplicationDocumentCommentDto, Long> {

    private final ApplicationDocumentCommentService applicationDocumentCommentService;
    private final ApplicationDocumentCommentMapper applicationDocumentCommentMapper;
    @Override
    protected BaseService<ApplicationDocumentComment, Long> getService() {
        return applicationDocumentCommentService;
    }

    @Override
    protected BaseMapper<ApplicationDocumentComment, ApplicationDocumentCommentDto> getMapper() {
        return applicationDocumentCommentMapper;
    }
    @GetMapping("/document/{documentId}")
    public ApiResponse<List<ApplicationDocumentCommentDto>> getAllApplicationWords(@PathVariable(name = "documentId") Long documentId) {
        return ApiResponse.ok(applicationDocumentCommentMapper.map(applicationDocumentCommentService.findByDocumentId(documentId)));
    }
}
