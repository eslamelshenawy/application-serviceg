package gov.saip.applicationservice.report.controller;


import gov.saip.applicationservice.report.service.ApplicationReportsService;
import gov.saip.applicationservice.util.DownloadFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code ApplicationReportsController} class provides RESTFUL web services for applications reports
 */
@RestController
@RequestMapping({"kc/application-reports", "internal-calling/application-reports", "pb/application-reports"})
@RequiredArgsConstructor
public class ApplicationReportsController {

    private final ApplicationReportsService applicationReportsService;

    /**
     * Download the patent license as PDF file
     *
     * @param applicationId the ID of the patent application
     * @return an {@link ResponseEntity} containing the {@link ByteArrayResource} which is the license PDF file
     */
    @GetMapping("/patent-license/{applicationId}")
    public ResponseEntity<ByteArrayResource> getPatentLicenseByApplicationId(@PathVariable("applicationId") Long applicationId) {
        ByteArrayResource file = applicationReportsService.getPatentLicenseByApplicationId(applicationId);
        return DownloadFileUtils.builder()
                .file(file)
                .downloadedFileName("patent-license.pdf")
                .build();
    }

}
