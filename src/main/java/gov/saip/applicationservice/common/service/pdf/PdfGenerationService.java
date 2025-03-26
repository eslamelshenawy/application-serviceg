package gov.saip.applicationservice.common.service.pdf;

import gov.saip.applicationservice.common.dto.DocumentDto;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface PdfGenerationService {
    byte[] generatePdf(String templateName, Map<String, Object> data) throws Exception;

    byte[] generatePdfArabic(String templateName, Map<String, Object> data) throws Exception;


    ResponseEntity<byte[]>generateIndustrialCertificateReportData(Long certificateId) throws Exception;
    ResponseEntity<byte []>generateFinanceGranteePdf(Long appId) throws Exception;
    ResponseEntity<byte[]>generateRejectionRequestPdf(Long appId) throws Exception;
    ResponseEntity<byte[]>generateExaminationReportPdf(Long appId) throws Exception;
    
    public List<DocumentDto> generateUploadSavePdfForPatentApplication(Long applicationId, Long certificateRequestId, String documentType,
                                                                       String applicationNumber, String certificateNumber);
    
    public List<DocumentDto> generateUploadSavePdfForIndustrialApplication(Long applicationId, Long certificateRequestId, String documentType, String applicationNumber, String certificateNumber);
    
    public List<MultipartFile> getCustomMultipartFiles(Long applicationId, ByteArrayResource file, String originalFilename, String contentType);
    
    public void checkTextOverflow(Map<String, Object> data, String text, int initialCapacity, int pageCapacity);

    void checkProductsOverflow(Map<String, Object> data, String text, int initialCapacity, int pageCapacity);

    String getPatCertFilePath(Long certificateId, String generationId, Long applicationId);

    String getApplicationDocumentFilePath(String generationId, Long applicationId,String titleAr,String titleEn);
    ByteArrayResource convertFileToResource(String filePath);

    void deleteFilesWithPrefixAfterComplete(String filePrefix);

    String getPatCertFilePathIndusterial(Long certificateId, String generationId, Long applicationId);

    List<DocumentDto> generateUploadSavePdfForIntegratedCircuit(Long applicationId, Long certificateId, String documentType, String applicationNumber, String certificateNumber);


    String convertWordToHtml(MultipartFile file);
}

