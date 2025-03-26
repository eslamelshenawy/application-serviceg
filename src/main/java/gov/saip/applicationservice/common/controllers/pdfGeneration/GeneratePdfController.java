package gov.saip.applicationservice.common.controllers.pdfGeneration;
import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.SaveFormat;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.service.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;



@RestController
@RequestMapping(value = {"/kc/generate-pdf", "/internal-calling/generate-pdf", "/pb/generate-pdf"})

@RequiredArgsConstructor
public class GeneratePdfController {
    private final PdfGenerationService pdfGenerationService;
    private final PatentPdfGenerationService patentPdfGenerationService;
    private final PublicationPdfGenerationService publicationPdfGenerationService;
    private final DepositPdfGenerationService depositPdfGenerationService;
    private final TradeMarkCrossOutPdfGenerationService tradeMarkCrossOutPdfGenerationService;
    private final TrademarkPdfGenerationService trademarkPdfGenerationService;

    @Value("${file.directory.certificate.path}")
    String fileDirectory = "/mnt/externalstorage01/data/files/patent/certificate/";


    @GetMapping("/industrial-cert-app/{certificateId}")
    public ResponseEntity<byte[]> generateIndustrialCertificatePdf(@PathVariable(name = "certificateId") String certificateId) throws Exception {

        return pdfGenerationService.generateIndustrialCertificateReportData(Long.valueOf(certificateId));

    }

    @GetMapping("/industrial-Finance-Grantee-app/{appId}")
    public ResponseEntity<byte[]> generateFinanceGranteePdf(@PathVariable(name = "appId") String appId) throws Exception {

        return pdfGenerationService.generateFinanceGranteePdf(Long.valueOf(appId));

    }

    @GetMapping("/trade-mark-app/{certificateId}")
    public ResponseEntity<byte[]> generateTradeMarkCertificateReport(@PathVariable(name = "certificateId") String certificateId,
                                                                     @RequestParam(value = "documentType", defaultValue = "Issue Certificate", required = false) String documentType) throws Exception {

        return trademarkPdfGenerationService.generateTradeMarkPdf( Long.valueOf(certificateId), documentType);

    }


    @GetMapping("/patent-pdf-report/{certId}")
    public ResponseEntity<byte[]> generatePatentCertificateReport(@PathVariable(name = "certId") Long certId) throws Exception {
        return patentPdfGenerationService.generatePatentPdfReport(certId);
    }

    @GetMapping("/deposit-pdf-report/{aiId}")
    public ResponseEntity<byte[]> generateDepositPdfReport(@PathVariable Long aiId, @RequestParam(required = false) Long certificateId) throws Exception {
        return depositPdfGenerationService.generateDepositPdfReport("deposit", aiId, certificateId);
    }

    @GetMapping("/trade-mark-cross-out-pdf-report/{aiId}")
    public ResponseEntity<byte[]> generateTradeMarkCrossOutPdf(@PathVariable Long aiId) throws Exception {
        return tradeMarkCrossOutPdfGenerationService.generateTradeMarkCrossOutPdf("crossOutReport", aiId);
    }

    @GetMapping("/publication-pdf-report/{publicationIssueId}")
    public ResponseEntity<byte[]> generatePublicationArabicPdf(
            @PathVariable Long publicationIssueId) throws Exception {
        return publicationPdfGenerationService.generatePublicationPdfReport("newsletter", publicationIssueId);
    }


    @GetMapping("/rejection-request-pdf/{appId}")
    public ResponseEntity<byte[]> generateRejectionRequestPdf(@PathVariable(name = "appId") String appId) throws Exception {
        return pdfGenerationService.generateRejectionRequestPdf(Long.valueOf(appId));
    }

    @GetMapping("/examination-report/{appId}")
    public ResponseEntity<byte[]> generateExaminationReport(@PathVariable(name = "appId") String appId) throws Exception {
        return pdfGenerationService.generateExaminationReportPdf(Long.valueOf(appId));
    }

    @GetMapping("/revoke-voluntary-report/{appId}")
    public ResponseEntity<byte[]> generateRevokeVoluntaryReport(@PathVariable(name = "appId") String appId) throws Exception{
        return trademarkPdfGenerationService.generateRevokedVoluntaryTradeMarkPdf(Long.valueOf(appId));
    }
    @PostMapping("/word-to-html")
    public ApiResponse<String> convertWordToHtml(@RequestParam("file") MultipartFile file) {
        return ApiResponse.ok(pdfGenerationService.convertWordToHtml(file));
    }




    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam String filePath) {
        try {
            File file = new File(fileDirectory + filePath);
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            FileSystemResource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @DeleteMapping("/delete-files")
    public ResponseEntity<String> deleteAllFilesInPath(@RequestParam String directoryPath) {
        try {
            File directory = new File(fileDirectory);
            if (!directory.exists() || !directory.isDirectory()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid directory path or directory does not exist.");
            }
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        boolean deleted = file.delete();
                        if (!deleted) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Failed to delete some files.");
                        }
                    }
                }
            }
            return ResponseEntity.ok("All files in the directory have been deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting files.");
        }
    }



    @PostMapping("/word-to-pdf")
    public ResponseEntity<byte[]> convertWordToPdf(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty() || !file.getOriginalFilename().endsWith(".docx")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(("Invalid file. Please upload a .docx file.").getBytes());
            }
            InputStream inputStream = file.getInputStream();
            Document doc = new Document(inputStream);
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            doc.save(pdfOutputStream, SaveFormat.PDF);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            return new ResponseEntity<>(pdfOutputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error occurred while converting the file: " + e.getMessage()).getBytes());
        }
    }







}
