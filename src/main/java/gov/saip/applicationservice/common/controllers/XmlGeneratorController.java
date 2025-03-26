package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping({"/internal-calling"})
@RequiredArgsConstructor
public class XmlGeneratorController {
    
    private final TrademarkDetailService trademarkDetailService;
    private final PatentDetailsService patentDetailsService;
    private final IndustrialDesignDetailService industrialDesignDetailService;
    
    @PostMapping("/application-info/{applicationId}/TRADEMARK/generate-upload-save-xml")
    public ResponseEntity<String> generateUploadSaveXmlForTrademarkApplication(
            @PathVariable Long applicationId,
            @RequestParam(value = "documentType") String documentType
    ) {
        trademarkDetailService.generateUploadSaveXmlForApplication(applicationId, documentType);
        return ResponseEntity.ok("SUCCESS");
    }
    
    
    @PostMapping("/application-info/{applicationId}/PATENT/generate-upload-save-xml")
    public ResponseEntity<String> generateUploadSaveXmlForPatentApplication(
            @PathVariable Long applicationId,
            @RequestParam(value = "documentType") String documentType
    ) {
        patentDetailsService.generateUploadSaveXmlForApplication(applicationId, documentType);
        return ResponseEntity.ok("SUCCESS");
    }
    
    @PostMapping("/application-info/{applicationId}/INDUSTRIAL_DESIGN/generate-upload-save-xml")
    public ResponseEntity<String> generateUploadSaveXmlForIndustrialApplication(
            @PathVariable Long applicationId,
            @RequestParam(value = "documentType") String documentType
    ) {
        industrialDesignDetailService.generateUploadSaveXmlForApplication(applicationId, documentType);
        return ResponseEntity.ok("SUCCESS");
    }
    
}
