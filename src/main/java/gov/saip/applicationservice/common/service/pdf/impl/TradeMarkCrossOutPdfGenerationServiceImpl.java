package gov.saip.applicationservice.common.service.pdf.impl;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.CrossOutReportDto;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkDetailDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.pdf.TradeMarkCrossOutPdfGenerationService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TradeMarkCrossOutPdfGenerationServiceImpl implements TradeMarkCrossOutPdfGenerationService {
    private final SpringTemplateEngine templateEngine;
    private final ApplicationInfoService applicationInfoService;
    private final TrademarkDetailService trademarkDetailService;
    private final DocumentsService documentsService;

    private static final int TAG_ID_TRADEMARK_IMAGE_VOICE = 1;
    private static final String PDF_ATTACHMENT_FORMAT = "attachment";

    private FontProvider fontProvider;

    @Value("${crossOutFileName}")
    private String fileName;

    @Value("${crossOutReportClassificationNumber}")
    private String crossOutReportClassificationNumber;

    private ClassPathResource[] resources;

    @PostConstruct
    public void postConstruct() {
        resources = new ClassPathResource[]{
                new ClassPathResource("/fonts/29ltbukrabold.ttf"),
                new ClassPathResource("/fonts/29ltbukraregular.ttf")
        };
    }

    public ResponseEntity<byte[]> generateTradeMarkCrossOutPdf(String templateName, Long aiId) throws Exception {
        ApplicationInfo applicationInfo = applicationInfoService.findById(aiId);
        if (applicationInfo.getApplicationStatus().getId() != 22 ){
            throw new BusinessException(Constants.ErrorKeys.TRADEMARK_NOT_DELETED, HttpStatus.BAD_REQUEST, null);

        }
        CrossOutReportDto crossOutReportDto = createCrossOutReportDto(applicationInfo);
        populateCrossOutReportData(aiId, crossOutReportDto, applicationInfo);
        String html = generateHtmlFromTemplate(templateName, crossOutReportDto);
        byte[] pdfBytes = generatePdfFromHtml(html);
        HttpHeaders headers = createPdfHeaders(aiId);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    private CrossOutReportDto createCrossOutReportDto(ApplicationInfo applicationInfo) {
        CrossOutReportDto crossOutReportDto = new CrossOutReportDto();
        crossOutReportDto.setApplicationNumber(applicationInfo.getApplicationNumber());
        return crossOutReportDto;
    }

    private void populateCrossOutReportData(Long aiId, CrossOutReportDto crossOutReportDto, ApplicationInfo applicationInfo) {
        tradeMarkDetails(aiId, crossOutReportDto);
        ApplicationReportDetailsDto applicationReportDetailsDto = applicationInfoService.getApplicationReportInfo(aiId);
        setApplicantInfo(crossOutReportDto, applicationReportDetailsDto);
        crossOutReportDto.setFillingDateHigri(Utilities.convertDateFromGregorianToHijri(applicationInfo.getFilingDate().toLocalDate()));
        String nexuoId = getNexuoIdForTrademarkImageVoice(aiId);
        crossOutReportDto.setFileUrl("https://fileintegration.saip.gov.sa/previewFile/" + nexuoId + "/IPRs4PATENT/");
    }

    private void tradeMarkDetails(Long aiId, CrossOutReportDto crossOutReportDto) {
        TrademarkDetailDto trademarkDetailDto = trademarkDetailService.findDtoByApplicationId(aiId);
        crossOutReportDto.setTmNameAr(trademarkDetailDto.getNameAr());
        crossOutReportDto.setTmNameEn(trademarkDetailDto.getNameEn());
        crossOutReportDto.setTradeMarkId(trademarkDetailDto.getId());
        crossOutReportDto.setCrossOutReportClassificationNumber(crossOutReportClassificationNumber);
    }

    private void setApplicantInfo(CrossOutReportDto crossOutReportDto, ApplicationReportDetailsDto applicationReportDetailsDto) {
        for (ApplicantsDto applicant : applicationReportDetailsDto.getApplicants()) {
            if (!applicant.isInventor()) {
                crossOutReportDto.setTmOwner(applicant.getNameAr());
            }
        }
        crossOutReportDto.setApplicantName(applicationReportDetailsDto.getMainApplicant().getNameAr());
    }

    private String generateHtmlFromTemplate(String templateName, CrossOutReportDto crossOutReportDto) {
        Map<String, Object> data = Map.of("pdfRequest", crossOutReportDto);
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process(templateName, context);
    }

    private byte[] generatePdfFromHtml(String html) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ConverterProperties properties = new ConverterProperties();
            initializeFonts(properties);
            HtmlConverter.convertToPdf(html, outputStream, properties);
            return outputStream.toByteArray();
        }
    }

    private void initializeFonts(ConverterProperties properties) {
        properties.setFontProvider(fontProvider);
    }

    private HttpHeaders createPdfHeaders(Long aiId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(PDF_ATTACHMENT_FORMAT, fileName + aiId + ".pdf");
        return headers;
    }

    private String getNexuoIdForTrademarkImageVoice(Long aiId) {
        return documentsService.getNexuoIdsByApplicationIdAndTagIdAndDocumentTypeCode(TAG_ID_TRADEMARK_IMAGE_VOICE, aiId, "Trademark Image/voice");
    }
}
