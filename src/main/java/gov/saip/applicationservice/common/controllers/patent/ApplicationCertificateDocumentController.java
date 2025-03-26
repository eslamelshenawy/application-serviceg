package gov.saip.applicationservice.common.controllers.patent;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationCertificateDocumentDto;
import gov.saip.applicationservice.common.service.patent.ApplicationCertificateDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code ApplicationCertificateDocumentController} class provides RESTful web services for managing ApplicationCertificateDocument applications.
 */
@RestController
@RequestMapping({"/kc/application-certificate-document", "internal-calling/application-certificate-document"})
@RequiredArgsConstructor
public class ApplicationCertificateDocumentController {

    private final ApplicationCertificateDocumentService applicationCertificateDocumentService;

    /**
     * Generate and save application document.
     *
     * @param applicationId
     * @return an {@link ApiResponse} containing null
     */

    @PostMapping("/generate/{applicationId}")
    public ApiResponse<Object> generateAndSaveDocument(@PathVariable("applicationId") Long applicationId) {
        applicationCertificateDocumentService.generateAndSaveDocument(applicationId);
        return ApiResponse.created(null);
    }


    @GetMapping("/documents/{applicationId}")
    public List<ApplicationCertificateDocumentDto> findByApplicationId(@PathVariable("applicationId") Long applicationId) {
        return ApiResponse.ok(applicationCertificateDocumentService.findByApplicationId(applicationId)).getPayload();
    }

    @PostMapping("/regenerate/{applicationId}")
    public ApiResponse<Object> UpdateGeneratedDocument(@PathVariable("applicationId") Long applicationId) {
        applicationCertificateDocumentService.updateGeneratedDocument(applicationId);
        return ApiResponse.ok(null);
    }

}
