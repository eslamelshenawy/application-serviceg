package gov.saip.applicationservice.common.controllers.patent;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationNotesReqDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.patent.ApplicationPatentDetailsDto;
import gov.saip.applicationservice.common.dto.patent.PatentDetailDtoWithChangeLog;
import gov.saip.applicationservice.common.dto.patent.PatentDetailsRequestDto;
import gov.saip.applicationservice.common.dto.patent.PublicationDetailDto;
import gov.saip.applicationservice.common.dto.pltDurationPaymentInfo.PltDurationPaymentInfoDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.util.DownloadFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum.FILE_NEW_APPLICATION;

/**
 * The {@code PatentDetailsController} class provides RESTful web services for managing patent details.
 */
@RestController
@RequestMapping({"kc/patent","internal-calling/patent","pb/patent"})
@RequiredArgsConstructor
@Slf4j
public class PatentDetailsController {

    private final PatentDetailsService patentDetailsService;

    /**
     * Create patent details for an application ID.
     *
     * @param patentDetailsRequestDto the patent details request DTO
     * @param id                      the application ID
     * @return an {@link ApiResponse} containing the created patent details
     */
    @PostMapping("/application/{id}")
    public ApiResponse<Object> createPatentDetails(@Valid @RequestBody PatentDetailsRequestDto patentDetailsRequestDto, @PathVariable Long id) {
        return ApiResponse.created(patentDetailsService.savePatent(patentDetailsRequestDto, id));
    }

    /**
     * Get patent details by application ID.
     *
     * @param id the application ID
     *     * @return an {@link ApiResponse} containing the patent details
     */
    @GetMapping("/{id}")
    public ApiResponse<PatentDetails> findByPatentId(@PathVariable Long id) {
        PatentDetails patentDetails = patentDetailsService.findByPatentId(id);
        return ApiResponse.ok(patentDetails);

    }

    /**
     * Get patent details by application ID.
     *
     * @param id the application ID
     *     * @return an {@link ApiResponse} containing the patent details
     */
    @GetMapping("application/{id}")
    public ApiResponse<PatentDetailDtoWithChangeLog> findByApplicationId(@PathVariable Long id) {
        return ApiResponse.ok(patentDetailsService.findDtoByApplicationIdWithOnlyLatestVersionOfLogs(id));

    }

    @GetMapping("application/{id}/all")
    public ApiResponse<PatentDetailDtoWithChangeLog> findAllByApplicationId(@PathVariable Long id) {
        return ApiResponse.ok(patentDetailsService.findDtoByApplicationId(id));

    }

    /**
     * Retrieve substantive examination details by application ID.
     *
     * @param applicationId the application ID
     * @return an {@link ApiResponse} containing the substantive examination details
     */
    @GetMapping("/substantive-examination/{applicationId}")
    public ApiResponse<ApplicationPatentDetailsDto>  retrieveSubstantiveExaminationDetails(@PathVariable Long applicationId){
        return ApiResponse.ok(patentDetailsService.retrieveSubstantiveExaminationDetails(applicationId));
    }

    /**
     * Retrieves Publication details for an application.
     *
     * @param applicationId the ID of the application to retrieve publication details for
     * @return an {@link ApiResponse} containing the PublicationDetailsDto object representing the retrieved publication details
     */
    @GetMapping("/publication/{applicationId}")
    public ApiResponse<PublicationDetailDto>  getPublicationDetails(@PathVariable Long applicationId
    , @RequestParam(value = "applicationPublicationId", required = false) Long applicationPublicationId
    ,@RequestParam(value = "publicationCode",required = false) String publicationCode
    ){
        return ApiResponse.ok(patentDetailsService.getPublicationDetails(applicationId, applicationPublicationId,publicationCode));
    }

    /**
     * Download XML formatted file that contains the patent application information
     *
     * @param applicationId the ID of the application
     * @return an {@link ResponseEntity} containing the {@link ByteArrayResource} which is the XML file
     */
    @GetMapping("/application-info/{applicationId}/xml-file")
    public ResponseEntity<ByteArrayResource> getApplicationInfoXml(@PathVariable(value = "applicationId") Long applicationId) {
        ByteArrayResource file = patentDetailsService.getApplicationInfoXml(applicationId);
        return DownloadFileUtils.builder()
                .file(file)
                .downloadedFileName("Patents.xml")
                .build();
    }


    @GetMapping("/duration-paymentInfo")
    ApiResponse<PltDurationPaymentInfoDto>getApplicationDurationPaymentInfo(@RequestParam(name="appId",required = false) Long  appId){
         return ApiResponse.ok(patentDetailsService.getApplicationPaymentInfoAndDurationConfigured(appId,FILE_NEW_APPLICATION.name()));
    }
    @GetMapping("/patent-Examiner-report/{appId}")
    public ResponseEntity<ByteArrayResource> generatePatentExaminerReport(@PathVariable(name = "appId") Long appId, @RequestParam(value = "documentType") String documentType) throws IOException {
        log.info(" first application id {}  report type {}",appId,documentType);
        ByteArrayResource file=patentDetailsService.generatePatentExaminerReports(appId,documentType);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=patent-grant.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.contentLength()));
        log.info(" end application id {}  report type {}",appId,documentType);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(file);
    }

    @PostMapping("/pdf/{reportName}")
    ApiResponse<List<DocumentDto>> generateJasperPdf(@RequestBody ReportRequestDto dto ,@PathVariable(name = "reportName") String reportName ) throws IOException{
        List<DocumentDto>  documentDtos= patentDetailsService.generateJasperPdf(dto,reportName);
        return ApiResponse.ok(documentDtos);
    }


    @PostMapping("/with-out-appId/pdf/{reportName}")
    ApiResponse<List<DocumentDto>> generateJasperPdfForSupportServicesWithNoApplicationId(@RequestBody ReportRequestDto dto ,@PathVariable(name = "reportName") String reportName ) throws IOException{
        List<DocumentDto>  documentDtos= patentDetailsService.generateJasperPdfForSupportServicesWithNoApplicationId(dto , reportName);
        return ApiResponse.ok(documentDtos);
    }

    @PutMapping("/set-patent-opposition-flag/{appId}")
    ApiResponse<Void> setPatentOppositionFlag(@PathVariable(name = "appId") Long appId){
        patentDetailsService.setPatentOppositionFlag(appId);
        return ApiResponse.noContent();
    }
    @PutMapping("/set-patent-opposition-true-flag/{appId}")
    ApiResponse<Void> setPatentOppositionFlagWithTrue(@PathVariable(name = "appId") Long appId){
        patentDetailsService.setPatentOppositionFlagWithTrue(appId);
        return ApiResponse.noContent();
    }


    @PostMapping("/add-applicant-opposition")
    public ApiResponse<Long> applicantOppositionForCorrectionInvitations(@RequestBody ApplicationNotesReqDto applicationNotesReqDto) {
        return ApiResponse.ok(patentDetailsService.applicantOppositionForCorrectionInvitations(applicationNotesReqDto));
    }


    @GetMapping("/last-pt-applicant-opposition/{appId}")
    public ApiResponse<String> getPTLastApplicantOppositionForInvitationCorrection(@PathVariable("appId") Long appId,
                                                                                   @RequestParam(value = "taskKey", required = false) String taskKey) {
        return ApiResponse.ok(patentDetailsService.getPTLastApplicantOppositionForInvitationCorrection(appId,taskKey));
    }

}
