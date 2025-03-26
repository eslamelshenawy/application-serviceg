package gov.saip.applicationservice.common.service.pdf;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PublicationPdfGenerationService {
    ResponseEntity<byte[]> generatePublicationPdfReport(String templateName, Long publicationIssueId) throws Exception;

}
