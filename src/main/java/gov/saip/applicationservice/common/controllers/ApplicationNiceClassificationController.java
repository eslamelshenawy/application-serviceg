package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationOtherDocumentDto;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.trademark.ApplicationTradeMarkListClassificationsDto;
import gov.saip.applicationservice.common.mapper.ApplicationOtherDocumentMapper;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.service.ApplicationNiceClassificationService;
import gov.saip.applicationservice.common.service.ApplicationOtherDocumentService;
import gov.saip.applicationservice.common.service.ClassificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping(value = {"/kc/application-nice-classification", "/internal-calling/application-nice-classification"})
@RequiredArgsConstructor
public class ApplicationNiceClassificationController extends BaseController<ApplicationOtherDocument, ApplicationOtherDocumentDto, Long> {

    private final ApplicationNiceClassificationService applicationNiceClassificationService;
    private final ApplicationOtherDocumentService applicationOtherDocumentService;
    private final ApplicationOtherDocumentMapper applicationCheckingReportMapper;

    @Override
    protected BaseService<ApplicationOtherDocument, Long> getService() {
        return applicationOtherDocumentService;
    }

    @Override
    protected BaseMapper<ApplicationOtherDocument, ApplicationOtherDocumentDto> getMapper() {
        return applicationCheckingReportMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<ClassificationDto>> getAllApplicationOtherDocuments(@PathVariable(name = "appId") Long appId) {
        List<ClassificationDto> classificationDtos = applicationNiceClassificationService.listSelectedApplicationNiceClassifications(appId);
        return ApiResponse.ok(classificationDtos);
    }

    @GetMapping("/light/classifications")
    public ApiResponse<List<ClassificationLightDto>> getAppNiceClassification(@RequestParam(name = "id") Long id){
        return ApiResponse.ok(applicationNiceClassificationService.getLightNiceClassificationsByAppId(id));
    }

    @GetMapping("/trade-mark/{appId}")
    @Operation(description = "**This for retrieve application classifications**")
    ApiResponse<ApplicationTradeMarkListClassificationsDto> getApplicantsAndClassifications(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationNiceClassificationService.getApplicantsAndClassifications(appId));
    }

}
