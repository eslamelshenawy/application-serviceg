package gov.saip.applicationservice.common.service.pdf;

import gov.saip.applicationservice.common.dto.DocumentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrademarkPdfGenerationService {
    
    ResponseEntity<byte[]> generateTradeMarkPdf(Long certificateId, String documentType) throws Exception;
    
    ResponseEntity<byte[]>generateRevokedVoluntaryTradeMarkPdf(Long appId) throws Exception;
    
    
    List<DocumentDto> generateUploadSavePdfForTrademarkApplication(Long applicationId, Long certificateId, String documentType,
                                                                   String applicationNumber, String certificateNumber, Long supportServiceId);
}
