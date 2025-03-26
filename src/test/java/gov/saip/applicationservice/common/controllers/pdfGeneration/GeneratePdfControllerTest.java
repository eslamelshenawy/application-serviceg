package gov.saip.applicationservice.common.controllers.pdfGeneration;

import gov.saip.applicationservice.common.service.pdf.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GeneratePdfControllerTest {

    @Mock
    private PdfGenerationService pdfGenerationService;
    
    @Mock
    private TrademarkPdfGenerationService trademarkPdfGenerationService;

    @Mock
    private PatentPdfGenerationService patentPdfGenerationService;

    @Mock
    private PublicationPdfGenerationService publicationPdfGenerationService;

    @Mock
    private DepositPdfGenerationService depositPdfGenerationService;

    @Mock
    private TradeMarkCrossOutPdfGenerationService tradeMarkCrossOutPdfGenerationService;

    @InjectMocks
    private GeneratePdfController generatePdfController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateIndustrialCertificatePdf() throws Exception {
        Long certificateId = 123L;
        byte[] pdfData = "PDF_CONTENT".getBytes();
        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok().body(pdfData);

        when(pdfGenerationService.generateIndustrialCertificateReportData(certificateId)).thenReturn(expectedResponse);

        ResponseEntity<byte[]> response = generatePdfController.generateIndustrialCertificatePdf(certificateId.toString());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response);
        verify(pdfGenerationService, times(1)).generateIndustrialCertificateReportData(certificateId);
    }

    @Test
    public void testGenerateTradeMarkDetailsPdf() throws Exception {
        Long certificateId = 456L;
        byte[] pdfData = "PDF_CONTENT".getBytes();
        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok().body(pdfData);

        when(trademarkPdfGenerationService.generateTradeMarkPdf(certificateId, "Issue Certificate")).thenReturn(expectedResponse);

        ResponseEntity<byte[]> response = generatePdfController.generateTradeMarkCertificateReport(certificateId.toString(), "Issue Certificate");

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response);
        verify(trademarkPdfGenerationService, times(1)).generateTradeMarkPdf(certificateId, "Issue Certificate");
    }

    @Test
    public void testGeneratePatentPdfReport() throws Exception {
        Long aiId = 789L;
        Long certId = 101L;
        byte[] pdfData = "PDF_CONTENT".getBytes();
        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok().body(pdfData);

        when(patentPdfGenerationService.generatePatentPdfReport(eq(certId)))
                .thenReturn(expectedResponse);

        ResponseEntity<byte[]> response = generatePdfController.generatePatentCertificateReport(certId);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response);
        verify(patentPdfGenerationService, times(1)).generatePatentPdfReport(eq(certId));
    }

    @Test
    public void testGenerateDepositPdfReport() throws Exception {
        byte[] expectedPdfContent = "Mocked PDF Content".getBytes();
        when(depositPdfGenerationService.generateDepositPdfReport("deposit", 1L, null)).thenReturn(ResponseEntity.ok(expectedPdfContent));

        ResponseEntity<byte[]> responseEntity = generatePdfController.generateDepositPdfReport(1L, null);
        assertEquals(expectedPdfContent, responseEntity.getBody());
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        verify(depositPdfGenerationService, times(1)).generateDepositPdfReport(eq("deposit"), anyLong(), any());
    }


    @Test
    public void testGenerateTradeMarkCrossOutPdf() throws Exception {
        Long aiId = 654L;
        byte[] pdfData = "PDF_CONTENT".getBytes();
        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok().body(pdfData);

        when(tradeMarkCrossOutPdfGenerationService.generateTradeMarkCrossOutPdf(eq("crossOutReport"), eq(aiId)))
                .thenReturn(expectedResponse);

        ResponseEntity<byte[]> response = generatePdfController.generateTradeMarkCrossOutPdf(aiId);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response);
        verify(tradeMarkCrossOutPdfGenerationService, times(1)).generateTradeMarkCrossOutPdf(eq("crossOutReport"), eq(aiId));
    }

    @Test
    public void testGeneratePublicationArabicPdf() throws Exception {
        Long publicationIssueId = 123L;
        byte[] pdfData = "PDF_CONTENT".getBytes();
        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok().body(pdfData);

        when(publicationPdfGenerationService.generatePublicationPdfReport(eq("newsletter"), eq(publicationIssueId)))
                .thenReturn(expectedResponse);

        ResponseEntity<byte[]> response = generatePdfController.generatePublicationArabicPdf(publicationIssueId);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response);
        verify(publicationPdfGenerationService, times(1)).generatePublicationPdfReport(eq("newsletter"), eq(publicationIssueId));
    }
}

