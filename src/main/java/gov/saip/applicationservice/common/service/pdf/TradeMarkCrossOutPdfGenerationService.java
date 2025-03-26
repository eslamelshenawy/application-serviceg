package gov.saip.applicationservice.common.service.pdf;

import org.springframework.http.ResponseEntity;

public interface TradeMarkCrossOutPdfGenerationService {


    ResponseEntity<byte[]> generateTradeMarkCrossOutPdf(String templateName, Long aiId) throws Exception;
}
