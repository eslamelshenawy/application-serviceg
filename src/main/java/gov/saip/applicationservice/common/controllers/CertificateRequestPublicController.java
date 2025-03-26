package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pb/certificate-request")
@RequiredArgsConstructor
public class CertificateRequestPublicController {

    private final CertificateRequestService certificateRequestService;

    @GetMapping("/file-url")
    public String getFileUrlByApplicationNumber(
            @RequestParam("applicationNumber") String applicationNumber) {
        // Fetch the ApplicationInfo using the applicationNumber
        ApplicationInfo applicationInfo = certificateRequestService.findApplicationInfoByNumber(applicationNumber);
        if (applicationInfo == null) {
            throw new BusinessException("Application not found for the given number: " + applicationNumber);
        }

        // Fetch the associated Document
        DocumentDto document = certificateRequestService.findDocumentByApplicationId(applicationInfo.getId());
        if (document == null || document.getFileReviewUrl() == null) {
            throw new BusinessException("No document associated with the application");
        }

        // Return the file URL
        return document.getFileReviewUrl();
    }
}
