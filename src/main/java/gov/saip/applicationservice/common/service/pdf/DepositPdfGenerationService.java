package gov.saip.applicationservice.common.service.pdf;

import org.springframework.http.ResponseEntity;

public interface DepositPdfGenerationService {

    ResponseEntity<byte[]> generateDepositPdfReport(String templateName, Long aiId, Long certificateId) throws Exception;
}
