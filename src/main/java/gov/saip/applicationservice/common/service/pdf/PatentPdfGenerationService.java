package gov.saip.applicationservice.common.service.pdf;

import org.springframework.http.ResponseEntity;

public interface PatentPdfGenerationService {
    ResponseEntity<byte[]> generatePatentPdfReport(Long certificateId) throws Exception;
}
