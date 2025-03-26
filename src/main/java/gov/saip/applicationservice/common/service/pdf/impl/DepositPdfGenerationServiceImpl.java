package gov.saip.applicationservice.common.service.pdf.impl;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.DepositReportDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkDetailDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.CertificateRequest;
import gov.saip.applicationservice.common.projection.ApplicationReportInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.pdf.DepositPdfGenerationService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.util.Utilities;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import static gov.saip.applicationservice.common.enums.RequestTypeConfigEnum.PUBLICATION_DURATION_TM;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepositPdfGenerationServiceImpl implements DepositPdfGenerationService {
    private final SpringTemplateEngine templateEngine;
    private final ApplicationInfoService applicationInfoService;
    private final TrademarkDetailService trademarkDetailService;
    private final DocumentsService documentsService;
    private final ApplicationPublicationService applicationPublicationService;
    private final BPMCallerService bpmCallerService;
    private final ApplicationInfoRepository applicationInfoRepository;
    @Lazy
    @Autowired
    private CertificateRequestService certificateRequestService;
    private FontProvider fontProvider;
    private static final int TAG_ID_TRADEMARK_IMAGE_VOICE = 1;
    private static final String PDF_ATTACHMENT_FORMAT = "attachment";

    @Value("${depositFileName}")
    private String fileName;

    @Value("${depositCertificateNiceClassificationNumber}")
    private Long depositCertificateNiceClassificationNumber;

    @Value("${depositCertificateNiceClassificationName}")
    private String depositCertificateNiceClassificationName;

    @Value("${spring.profiles.active}")
    public String activeProfile;

    private ClassPathResource[] resources;

    @PostConstruct
    public void postConstruct() {
        resources = new ClassPathResource[]{
                new ClassPathResource("/fonts/29ltbukrabold.ttf"),
                new ClassPathResource("/fonts/29ltbukraregular.ttf")
        };
    }

    public ResponseEntity<byte[]> generateDepositPdfReport(String templateName, Long aiId, Long certificateId) throws Exception {
        DepositReportDto depositReportDto = fetchDepositReportData(aiId, certificateId);
        String html = generateHtmlFromTemplate(templateName, depositReportDto);
        byte[] pdfBytes = generatePdfFromHtml(html);
        HttpHeaders headers = createPdfHeaders(aiId);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    private DepositReportDto fetchDepositReportData(Long aiId, Long certificateId) {
        DepositReportDto depositReportDto = applicationInfoService.findDepositCertificateDetailsById(aiId);
        CertificateRequest certificateRequest = certificateRequestService.findById(certificateId);
        tradeMarkDetails(aiId, depositReportDto);
        ApplicationReportDetailsDto applicationReportDetailsDto = applicationInfoService.getApplicationReportInfo(aiId);
        setApplicantInfo(depositReportDto, applicationReportDetailsDto);
        depositReportDto.setCertificateId(certificateId);
        depositReportDto.setCertificateRequestNumber(certificateRequest.getRequestNumber());
        depositReportDto.setFillingDateHigri(certificateRequest.getCreatedDate() == null ? null : Utilities.convertDateFromGregorianToHijri(certificateRequest.getCreatedDate().toLocalDate()));
        LocalDate certificateExpDate = setCertificateExpDate(aiId);
        depositReportDto.setCertificateDateExpiry(certificateExpDate == null ? null : Utilities.convertDateFromGregorianToHijri(certificateExpDate));
        niceClassificationsDetails(depositReportDto);
        DocumentDto documentDto = documentsService.findLatestDocumentByApplicationIdAndDocumentType(aiId, "Trademark Image/voice");
        depositReportDto.setFileUrl(documentDto == null ? "" : documentDto.getFileReviewUrl());
        Utilities utilities = new Utilities();
        // currently QR it will be disabled future the link should be updated
        String publicBaseUrl ="production".equalsIgnoreCase(activeProfile)?"":"";
        byte[] qrCodeImage = utilities.generateQRCodeImage(
                certificateRequest == null || certificateRequest.getApplicationInfo() == null
                        ? ""
                        : String.format("%s/pb/certificate-request/file-url?applicationNumber=%s", publicBaseUrl, certificateRequest.getApplicationInfo().getApplicationNumber())

        );
        String qrCodeImageBase64 = Base64.getEncoder().encodeToString(qrCodeImage);
        depositReportDto.setQrCodeImageBase64("data:image/png;base64," + qrCodeImageBase64);

        return depositReportDto;
    }

    private LocalDate setCertificateExpDate(Long aiId) {
        Optional<LocalDate> applicationPublicationDate = applicationPublicationService.findApplicationPublicationDateByAppId(aiId);
        LocalDate publicationDateExpiration = null;
        String processRequestTypeCode = ApplicationCategoryEnum.valueOf(applicationInfoRepository.findCategoryByApplicationId(aiId)).getProcessTypeCode();
        Long durationValue = bpmCallerService.getRequestTypeConfigValue(PUBLICATION_DURATION_TM.name(), aiId, processRequestTypeCode);
        if (durationValue != null && applicationPublicationDate.isPresent()) {
            publicationDateExpiration = applicationPublicationDate.get().plusDays(durationValue);
        }
        return publicationDateExpiration;
    }

    private String generateHtmlFromTemplate(String templateName, DepositReportDto depositReportDto) {
        Map<String, Object> data = Map.of("pdfRequest", depositReportDto);
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

    private void tradeMarkDetails(Long aiId, DepositReportDto depositReportDto) {
        TrademarkDetailDto trademarkDetailDto = trademarkDetailService.findDtoByApplicationId(aiId);
        depositReportDto.setTmNameAr(trademarkDetailDto.getNameAr());
        depositReportDto.setTmNameEn(trademarkDetailDto.getNameEn());
        depositReportDto.setMarkDescription(trademarkDetailDto.getMarkDescription());
        depositReportDto.setTradeMarkId(trademarkDetailDto.getId());
    }

    private void niceClassificationsDetails(DepositReportDto depositReportDto) {
        ApplicationReportInfo applicationReportInfo = applicationInfoService.getApplicationReportData(depositReportDto.getCertificateId());
        depositReportDto.setDepositCertificateNiceClassificationVersionName(applicationReportInfo.getVersionName());
        depositReportDto.setDepositCertificateNiceClassificationName(applicationReportInfo.getNameAr());
    }

    private void setApplicantInfo(DepositReportDto depositReportDto, ApplicationReportDetailsDto applicationReportDetailsDto) {
        for (ApplicantsDto applicant : applicationReportDetailsDto.getApplicants()) {
            if (!applicant.isInventor()) {
                depositReportDto.setTmOwner(Strings.isNullOrEmpty(applicant.getNameAr()) || applicant.getNameAr().isBlank() ? applicant.getNameEn() : applicant.getNameAr());
                if (applicant.getAddress() != null) {
                    depositReportDto.setPostalCode(applicant.getAddress().getPostalCode());
                    depositReportDto.setBuildingNumber(applicant.getAddress().getBuildingNumber());
                    depositReportDto.setCityName(applicant.getAddress().getCity());
                    depositReportDto.setStreetName(applicant.getAddress().getStreetName());
                }
            }
        }
        depositReportDto.setApplicantName(Strings.isNullOrEmpty(applicationReportDetailsDto.getMainApplicant().getNameAr()) || applicationReportDetailsDto.getMainApplicant().getNameAr().isBlank() ? applicationReportDetailsDto.getMainApplicant().getNameEn() : applicationReportDetailsDto.getMainApplicant().getNameAr());
    }
}
