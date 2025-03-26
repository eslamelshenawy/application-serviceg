package gov.saip.applicationservice.common.service.pdf.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.ApplicationPriorityListDto;
import gov.saip.applicationservice.common.dto.PatentPdfRequest;
import gov.saip.applicationservice.common.dto.patent.PatentAttributeChangeLogDto;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.dto.reports.PatentReportProjection;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.mapper.PdfRequestMapper;
import gov.saip.applicationservice.common.projection.ApplicationReportInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.patent.PatentAttributeChangeLogService;
import gov.saip.applicationservice.common.service.pdf.PatentPdfGenerationService;
import gov.saip.applicationservice.util.Constants.PatenChangeLogAttributeNames;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum.HEAD_OF_EXAMINER;
import static gov.saip.applicationservice.common.enums.customers.UserGroup.*;
import static gov.saip.applicationservice.util.Constants.PATENT_CATEGORY_ID;
import static gov.saip.applicationservice.util.Constants.PATENT_CERTIFICATE_TEMPLATE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PatentPdfGenerationServiceImpl implements PatentPdfGenerationService {
    private final SpringTemplateEngine templateEngine;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final PdfRequestMapper pdfRequestMapper;
    private final DocumentsService documentsService;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationUserService applicationUserService;
    private final ApplicationInfoService infoService;
    private final CustomerClient customerClient;
    @Autowired
    @Lazy
    private CertificateRequestService certificateRequestService;
    private final ApplicationPriorityService applicationPriorityService;


    private static final Long DESCRIPTION_SPECIFICATION_ARABIC_DOC_ID = 4L;
    private static final Long CLAIM_PDF_CONTENT_DOC_ID = 6L;
    private static final Long SUMMARY_PDF_CONTENT_DOC_ID = 2L;
    private static final Long IMAGES_EXTRACTED_DOC_ID = 8L;

    private FontProvider fontProvider;
    private List<FontProgram> fontPrograms;

    @Value("${fileName}")
    private String fileName;

    private ClassPathResource[] resources;
    @PostConstruct
    public void postConstruct() {
        resources = new ClassPathResource[]{
                new ClassPathResource("/fonts/29ltbukrabold.ttf"),
                new ClassPathResource("/fonts/29ltbukraregular.ttf")
        };
    }
    private void initializeFonts(ConverterProperties properties) {
        properties.setFontProvider(fontProvider);
    }

    private byte[] generatePdfFromHtml(String html, ConverterProperties properties) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(html, outputStream, properties);
            return outputStream.toByteArray();
        }
    }

    public ResponseEntity<byte[]> generatePatentPdfReport(Long certificateId) throws Exception {
        String templateName = PATENT_CERTIFICATE_TEMPLATE;
        ApplicationReportInfo applicationReportInfo = infoService.getApplicationReportData(certificateId);
        Long aiId = certificateRequestService.findApplicationInfoIdById(certificateId);
        PatentReportProjection patentReportProjection = applicationInfoRepository.findDetailsByIdAndCategoryId(aiId, PATENT_CATEGORY_ID);
        ApplicationReportDetailsDto applicationReportDetailsDto = applicationInfoService.getApplicationReportInfo(patentReportProjection.getId());
        List<String> checkerNames = applicationUserService. listUsernamesByAppAndRoles(aiId, List.of(HEAD_OF_EXAMINER));
        PatentPdfRequest pdfRequest = pdfRequestMapper.mapToPdfRequest(patentReportProjection);
        setData(applicationReportInfo, checkerNames, pdfRequest);
        setApplicantInfo(pdfRequest, applicationReportDetailsDto);
        setLatestVersionAttributes(pdfRequest);
        isOrganization(applicationReportDetailsDto, pdfRequest);
        addPriorities(aiId, pdfRequest);
        return processPdf(templateName, pdfRequest);
    }

    private static void setData(ApplicationReportInfo applicationReportInfo, List<String> checkerNames, PatentPdfRequest pdfRequest) {
        Utilities utilities = new Utilities();
        pdfRequest.setFilingDateHiGry(utilities.convertDateFromGregorianToHijri(LocalDate.from(pdfRequest.getFilingDate())));
        pdfRequest.setCheckerNames(checkerNames);
        pdfRequest.setCertNumber(applicationReportInfo.getRequestNumber());
    }

    private ResponseEntity<byte[]> processPdf(String templateName, PatentPdfRequest pdfRequest) {
        Map<String, Object> data = Map.of("pdfRequest", pdfRequest);
        Context context = new Context();
        context.setVariables(data);
        ConverterProperties properties = new ConverterProperties();

        String html = templateEngine.process(templateName, context);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PageSize pageSize = PageSize.A4;

        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(pageSize);

        Document document = new Document(pdfDocument, pageSize, false);
        document.setMargins(0, 0, 0, 0);

        HtmlConverter.convertToPdf(html, pdfDocument.getWriter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        byte[] pdfBytes = outputStream.toByteArray();

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    private static void isOrganization(ApplicationReportDetailsDto applicationReportDetailsDto, PatentPdfRequest pdfRequest) {
        if(applicationReportDetailsDto.getMainApplicant().getUserGroupCode().equals(AGENT)
        || applicationReportDetailsDto.getMainApplicant().getUserGroupCode().equals(GOVERNMENT_AGENCY)
        || applicationReportDetailsDto.getMainApplicant().getUserGroupCode().equals(FOREIGN_CORPORATION)
        ||   applicationReportDetailsDto.getMainApplicant().getUserGroupCode().equals(LEGAL_REPRESENTATIVE)
        ){
            pdfRequest.setFlag(false);
        }
    }

    private void addPriorities(Long aiId, PatentPdfRequest pdfRequest) {
        List<ApplicationPriorityListDto> appPriorities= applicationPriorityService.listApplicationPriorites(aiId);
        if(!appPriorities.isEmpty()){
            Long countryId =appPriorities.get(0).getCountryId()==null?null:appPriorities.get(0).getCountryId();
            if(Objects.nonNull(countryId)){
                String country =customerClient.getCountriesByIds(
                        List.of(
                                appPriorities.get(0).getCountryId()                  )

                ).getPayload().get(0).getIciCountryNameAr();
                pdfRequest.setPriorityCountry(country);

            }


            pdfRequest.setPriorityNumber(appPriorities.get(0).getPriorityApplicationNumber()==null?"لا يوجد":appPriorities.get(0).getPriorityApplicationNumber());
            pdfRequest.setPriorityDate(appPriorities.get(0).getFilingDate()==null?null:appPriorities.get(0).getFilingDate());
        }
    }

    private void setApplicantInfo(PatentPdfRequest pdfRequest, ApplicationReportDetailsDto applicationReportDetailsDto) {
        long startTime = System.currentTimeMillis();
        List <String> InventorNames = new ArrayList<>();
        for (ApplicantsDto applicationReportDetails : applicationReportDetailsDto.getApplicants()) {
            if (applicationReportDetails.isInventor()) {
                InventorNames.add(applicationReportDetails.getNameAr());
            } if(applicationReportDetails.getType().equals(ApplicationRelevantEnum.Applicant_MAIN))
                pdfRequest.setTmOwner(applicationReportDetails.getNameAr());
            if(applicationReportDetails.getAddress() != null) {
                pdfRequest.setAddress(applicationReportDetails.getAddress().getCountry() + " " + applicationReportDetails.getAddress().getCity());
                pdfRequest.setNationality(applicationReportDetails.getAddress().getCountryObject() == null ? null :
                        applicationReportDetails.getAddress().getCountryObject().getIciNationality());
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("setApplicantInfo took: {} ms", (endTime - startTime));
        pdfRequest.setAgentName(applicationReportDetailsDto.getApplicationAgent().getNameAr());
        pdfRequest.setInventor(InventorNames);
    }

    private void setDocumentUrls(PatentPdfRequest pdfRequest, Map<Long, String> fileUrls) throws IOException {
        String descriptionSpecificationArabic = downloadPDFContent(fileUrls.getOrDefault(DESCRIPTION_SPECIFICATION_ARABIC_DOC_ID, ""));
        String claimPdfContent = downloadPDFContent(fileUrls.getOrDefault(CLAIM_PDF_CONTENT_DOC_ID, ""));
        String summaryPdfContent = downloadPDFContent(fileUrls.getOrDefault(SUMMARY_PDF_CONTENT_DOC_ID, ""));
        List<String> imagesExtracted = extractImagesFromPDF(fileUrls.getOrDefault(IMAGES_EXTRACTED_DOC_ID, ""));
        pdfRequest.setClaimsArabic(claimPdfContent);
        pdfRequest.setDescriptionSpecificationArabic(descriptionSpecificationArabic);
        pdfRequest.setSummary(summaryPdfContent);
        pdfRequest.setImagesExtracted(imagesExtracted);


    }

    private final PatentAttributeChangeLogService patentAttributeChangeLogService;

    public void setLatestVersionAttributes(PatentPdfRequest pdfRequest) {
        Map<String, List<PatentAttributeChangeLogDto>> changeLogData = patentAttributeChangeLogService.getPatentAttributeLatestChangeLogByPatentId(pdfRequest.getPatentDetailsId());
        pdfRequest.setClaimsArabic(getPatentAttributeData(changeLogData, PatenChangeLogAttributeNames.DESCRIPTION.getNameAr()));
        pdfRequest.setDescriptionSpecificationArabic(getPatentAttributeData(changeLogData, PatenChangeLogAttributeNames.PROTECTION.getNameAr()));
        pdfRequest.setSummary(getPatentAttributeData(changeLogData, PatenChangeLogAttributeNames.SUMMARY.getNameAr()));
        pdfRequest.setGraphics(getPatentAttributeData(changeLogData, PatenChangeLogAttributeNames.GRAPHICS.getNameAr()));
    }

    private String getPatentAttributeData(Map<String, List<PatentAttributeChangeLogDto>> attributes, String attributeName) {
        List<PatentAttributeChangeLogDto> attributeData = attributes.getOrDefault(attributeName, null);
        if (attributeData == null)
            return "";
        return attributeData.get(0).getAttributeValue();
    }


    public static String downloadPDFContent(String pdfUrl) throws IOException {
        long startTime = System.currentTimeMillis();
        URL url = new URL(pdfUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to download the PDF file: " + httpURLConnection.getResponseCode());
        }
        long openConnection = System.currentTimeMillis();
        log.info("connectionTime took: {} ms", (openConnection - startTime));
        try (InputStream inputStream = httpURLConnection.getInputStream()) {
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfContent = stripper.getText(document);
            document.close();
            return pdfContent;

        }
        finally {
            long endTime = System.currentTimeMillis();
            log.info("downloadPDFContent took: {} ms", (endTime - startTime));
        }

    }

    public List<String> extractImagesFromPDF(String pdfUrl) throws IOException {
        if (pdfUrl == null || pdfUrl.isBlank()) {
            return Collections.emptyList();
        }
        long startTime = System.currentTimeMillis();
        URL url = new URL(pdfUrl);
        InputStream in = url.openStream();

        PdfReader reader = new PdfReader(in);
        PdfDocument pdfDoc = new PdfDocument(reader);
        List<String> base64Images = new ArrayList<>();
        PdfCanvasProcessor parser = new PdfCanvasProcessor(new IEventListener() {
            @Override
            public void eventOccurred(IEventData data, EventType type) {
                if (type == EventType.RENDER_IMAGE) {
                    ImageRenderInfo renderInfo = (ImageRenderInfo) data;
                    byte[] imageBytes = renderInfo.getImage().getImageBytes(true);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    base64Images.add(base64Image);
                }
            }

            @Override
            public Set<EventType> getSupportedEvents() {
                return Collections.singleton(EventType.RENDER_IMAGE);
            }
        });

        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            parser.processPageContent(pdfDoc.getPage(i));
        }

        reader.close();
        in.close();

        long endTime = System.currentTimeMillis();
        log.info("extractImagesFromPDF took: {} ms", (endTime - startTime));

        return base64Images;

    }


    private HttpHeaders createPdfHeaders(Long aiId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName + aiId+".pdf");
        return headers;
    }

}
