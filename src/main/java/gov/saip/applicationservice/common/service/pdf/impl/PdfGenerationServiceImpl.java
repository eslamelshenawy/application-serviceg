
package gov.saip.applicationservice.common.service.pdf.impl;

import com.aspose.words.*;
import com.aspose.words.shaping.harfbuzz.HarfBuzzTextShaperFactory;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gov.saip.applicationservice.client.JasperReportsClient;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.pdf.PdfData;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.certificate.PatentAttributeNames;
import gov.saip.applicationservice.common.model.ApplicationDrawing;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.industrial.DesignSample;
import gov.saip.applicationservice.common.service.ApplicationDrawingService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import gov.saip.applicationservice.common.service.patent.PatentAttributeChangeLogService;
import gov.saip.applicationservice.common.service.patent.ProtectionElementService;
import gov.saip.applicationservice.common.service.pdf.FileConverterFactory;
import gov.saip.applicationservice.common.service.pdf.PdfGenerationService;
import gov.saip.applicationservice.common.service.trademark.impl.CustomMultipartFile;
import gov.saip.applicationservice.common.util.ByteArrayMultipartFile;
import gov.saip.applicationservice.common.util.PatentDescriptiveUtil;
import gov.saip.applicationservice.report.dto.IndustrialDesignJasperReportDto;
import gov.saip.applicationservice.report.service.ApplicationReportsService;
import gov.saip.applicationservice.util.IPRMultipartFile;
import gov.saip.applicationservice.util.PdfUtil;
import gov.saip.applicationservice.util.StringUtil;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.docx4j.wml.Style;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBElement;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.*;

import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfGenerationServiceImpl implements PdfGenerationService {

    private final ApplicationReportsService applicationReportsService;
    private final SpringTemplateEngine templateEngine;
    private final DocumentsService documentsService;
    private final ApplicationDrawingService applicationDrawingService;
    private final JasperReportsClient jasperReportsClient;
    private final PatentAttributeChangeLogService patentAttributeChangeLogService;
    private final ProtectionElementService protectionElementService;
    private final FileConverterFactory fileConverterFactory;
    private final ApplicationInfoService applicationInfoService;
    private final DesignSampleService designSampleService;


    @Value("${spring.profiles.active}")
    public String activeProfile;

    @Autowired
    @Lazy
    private CertificateRequestService certificateRequestService;

    @Value("${file.directory.certificate.path}")
    String fileDirectory = "/mnt/externalstorage01/data/files/patent/certificate/";

    private ClassPathResource[] resources;

    @PostConstruct
    public void postConstruct() {
        resources = new ClassPathResource[]{
                new ClassPathResource("/fonts/NotoSans-Regular.ttf"),
                new ClassPathResource("/fonts/NotoNaskhArabic-Regular.ttf"),
                new ClassPathResource("/fonts/29ltbukrabold.ttf"),
                new ClassPathResource("/fonts/29ltbukraregular.ttf"),
                new ClassPathResource("/fonts/Dubai-Bold.ttf"),
                new ClassPathResource("/fonts/Dubai-Regular.ttf"),
                new ClassPathResource("/fonts/Simplified Arabic Bold.ttf"),
                new ClassPathResource("/fonts/Simplified Arabic Fixed Regular.ttf"),
                new ClassPathResource("/fonts/Simplified Arabic Regular.ttf"),
        };
    }

    @Override
    public byte[] generatePdf(String templateName, Map<String, Object> data) throws Exception {
        Context context = new Context();
        context.setVariables(data);
        String html = templateEngine.process(templateName, context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream.toByteArray();
    }

    @Override
    public byte[] generatePdfArabic(String templateName, Map<String, Object> data) throws Exception {
        Context context = new Context();
        context.setVariables(data);

        String html = templateEngine.process(templateName, context);


        ConverterProperties properties = new ConverterProperties();

        FontProvider fontProvider = new DefaultFontProvider(false, false, false);
        for (Resource font : resources) {
            FontProgram fontProgram = FontProgramFactory.createFont("/fonts/" + font.getFilename());
            fontProvider.addFont(fontProgram);
        }
        properties.setFontProvider(fontProvider);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PageSize landscape = PageSize.A4.rotate();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(landscape);
        HtmlConverter.convertToPdf(html, pdfDocument, properties);
        return outputStream.toByteArray();
    }

    @Override
    public ResponseEntity<byte[]> generateIndustrialCertificateReportData(Long certificateId) throws Exception {
        Map<String, Object> data = new HashMap<>();
        Long appId = certificateRequestService.findApplicationInfoIdById(certificateId);
        Utilities utilities = new Utilities();
        IndustrialDesignJasperReportDto industrialDesignJasperReportDto = applicationReportsService.getIndustrialDesignJasperReportDetails(Long.valueOf(appId), true);
        data.put("applicantNameAr", industrialDesignJasperReportDto.getApplicationReportDetailsDto().getMainApplicant().getNameAr());
        data.put("applicantNameEn", industrialDesignJasperReportDto.getApplicationReportDetailsDto().getMainApplicant().getNameEn());
        data.put("agentNameAr", industrialDesignJasperReportDto.getApplicationReportDetailsDto().getApplicationAgent() == null ? "لايوجد" : industrialDesignJasperReportDto.getApplicationReportDetailsDto().getApplicationAgent().getNameAr());
        List<ApplicantsDto> secondaryApplicants = industrialDesignJasperReportDto.getApplicationReportDetailsDto().getSecondaryApplicant();
        data.put("list", secondaryApplicants);
        data.put("applicationNumber", industrialDesignJasperReportDto.getApplicationNumber().toString());
        data.put("applicationFilingDate", industrialDesignJasperReportDto.getAppFilingDate().toLocalDate());
        data.put("applicationFilingDateHigri", industrialDesignJasperReportDto.getAppFilingDateHigiri());
        data.put("applicationGrantDate", industrialDesignJasperReportDto.getGranteDate());
        data.put("applicationGrantDateHigri", industrialDesignJasperReportDto.getGranteDateHigiri());
        data.put("date", LocalDateTime.now().toLocalDate());
        data.put("titleAr", industrialDesignJasperReportDto.getTitleAr());
        data.put("titleEn", industrialDesignJasperReportDto.getTitleEn());
        data.put("classification", industrialDesignJasperReportDto.getClassificationDto().get(0).getNameAr());
        data.put("secondaryApplicantNameAr", industrialDesignJasperReportDto.getApplicationReportDetailsDto().getSecondaryApplicant() == null || industrialDesignJasperReportDto.getApplicationReportDetailsDto().getSecondaryApplicant().isEmpty() ? null : industrialDesignJasperReportDto.getApplicationReportDetailsDto().getSecondaryApplicant().stream().map(s -> s.getNameAr()).collect(Collectors.joining(", ")));
        AddressResponseDto address = industrialDesignJasperReportDto.getApplicationReportDetailsDto().getMainApplicant().getAddress();
        if (address != null) {
            data.put("applicantAddress", address.getStreetName() + ", " +
                    address.getUnitNumber() + ", " +
                    address.getBuildingNumber() + ", " +
                    address.getCity());
        }
        if (address.getCountryObject() != null)
            data.put("applicantNationality", address.getCountryObject().getIciNationality());
        data.put("count", industrialDesignJasperReportDto.getCount());
        if (industrialDesignJasperReportDto.getExaminerNames() != null && !industrialDesignJasperReportDto.getExaminerNames().isEmpty())
            data.put("examiner", industrialDesignJasperReportDto.getExaminerNames().get(0));
        data.put("samples", industrialDesignJasperReportDto.getIndustrialDesignImagesDetailsDto());


        byte[] pdfBytes = generatePdfArabic("industrial", data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> generateFinanceGranteePdf(Long appId) throws Exception {
        LocalDate currentDate = LocalDate.now();
        Utilities utilities = new Utilities();
        Map<String, Object> data = new HashMap<>();
        IndustrialDesignJasperReportDto industrialDesignJasperReportDto = null;
        industrialDesignJasperReportDto = applicationReportsService.getIndustrialDesignJasperReportDetails(Long.valueOf(appId), false);
        data.put("applicantNameAr", industrialDesignJasperReportDto.getApplicationReportDetailsDto().getMainApplicant().getNameAr());
        data.put("agentNameAr", industrialDesignJasperReportDto.getApplicationReportDetailsDto().getApplicationAgent().getNameAr() == null ? "لا يوجد" : industrialDesignJasperReportDto.getApplicationReportDetailsDto().getApplicationAgent().getNameAr());
        data.put("filingDate", industrialDesignJasperReportDto.getAppFilingDate());
        data.put("currentDate", utilities.convertDateFromGregorianToHijri(LocalDate.from(currentDate)));
        data.put("category", industrialDesignJasperReportDto.getCategoryDescAr());
        data.put("applicationNumber", industrialDesignJasperReportDto.getApplicationNumber());
        data.put("applicationFilingDate", industrialDesignJasperReportDto.getAppFilingDate());
        data.put("titleAr", industrialDesignJasperReportDto.getTitleAr());
        data.put("classification", industrialDesignJasperReportDto.getClassificationDto().get(0).getNameAr());

        AddressResponseDto address = industrialDesignJasperReportDto.getApplicationReportDetailsDto().getMainApplicant().getAddress();
        if (address != null) {
            data.put("applicantAddress", address.getStreetName() + ", " +
                    address.getUnitNumber() + ", " +
                    address.getBuildingNumber() + ", " +
                    address.getCity());
        }
        data.put("count", industrialDesignJasperReportDto.getCount());

        byte[] pdfBytes = generatePdfArabic("payGrantFinance", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> generateRejectionRequestPdf(Long appId) throws Exception {

        LocalDate currentDate = LocalDate.now();
        Utilities utilities = new Utilities();
        Map<String, Object> data = new HashMap<>();
        IndustrialDesignJasperReportDto industrialDesignJasperReportDto = applicationReportsService.getIndustrialDesignJasperReportDetails(Long.valueOf(appId), false);
        data.put("applicantNameAr", industrialDesignJasperReportDto.
                getApplicationReportDetailsDto().getMainApplicant().getNameAr());
        data.put("agentNameAr", industrialDesignJasperReportDto.getApplicationReportDetailsDto().
                getApplicationAgent().getNameAr() == null ? "لايوجد" : industrialDesignJasperReportDto.getApplicationReportDetailsDto().
                getApplicationAgent().getNameAr());
        data.put("filingDate", industrialDesignJasperReportDto.getAppFilingDate());
        data.put("category", industrialDesignJasperReportDto.getCategoryDescAr());
        data.put("applicationNumber", industrialDesignJasperReportDto.getApplicationNumber());
        data.put("applicationFilingDate", industrialDesignJasperReportDto.getAppFilingDate());
        data.put("titleAr", industrialDesignJasperReportDto.getTitleAr());
        data.put("classification", industrialDesignJasperReportDto.getClassificationDto().get(0).getNameAr());
        data.put("currentDate", utilities.convertDateFromGregorianToHijri(LocalDate.from(currentDate)));
        AddressResponseDto address = industrialDesignJasperReportDto.getApplicationReportDetailsDto().getMainApplicant().getAddress();
        if (address != null) {
            data.put("applicantAddress", address.getStreetName() + ", " +
                    address.getUnitNumber() + ", " +
                    address.getBuildingNumber() + ", " +
                    address.getCity());
        }
        data.put("count", industrialDesignJasperReportDto.getCount());

        byte[] pdfBytes = generatePdfArabic("RequestRejection", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> generateExaminationReportPdf(Long appId) throws Exception {
        LocalDate currentDate = LocalDate.now();
        Utilities utilities = new Utilities();
        Map<String, Object> data = new HashMap<>();
        IndustrialDesignJasperReportDto industrialDesignJasperReportDto = applicationReportsService.getIndustrialDesignJasperReportDetails(Long.valueOf(appId), false);
        data.put("applicantNameAr", industrialDesignJasperReportDto.
                getApplicationReportDetailsDto().getMainApplicant().getNameAr());
        data.put("agentNameAr", industrialDesignJasperReportDto.getApplicationReportDetailsDto().
                getApplicationAgent().getNameAr() == null ? "لايوجد" : industrialDesignJasperReportDto.getApplicationReportDetailsDto().
                getApplicationAgent().getNameAr());
        data.put("filingDate", industrialDesignJasperReportDto.getAppFilingDate());
        data.put("category", industrialDesignJasperReportDto.getCategoryDescAr());
        data.put("applicationNumber", industrialDesignJasperReportDto.getApplicationNumber());
        data.put("applicationFilingDate", industrialDesignJasperReportDto.getAppFilingDate());
        data.put("titleAr", industrialDesignJasperReportDto.getTitleAr());
        data.put("classification", industrialDesignJasperReportDto.getClassificationDto().get(0).getNameAr());
        data.put("currentDate", utilities.convertDateFromGregorianToHijri(LocalDate.from(currentDate)));
        AddressResponseDto address = industrialDesignJasperReportDto.getApplicationReportDetailsDto().getMainApplicant().getAddress();
        if (address != null) {
            data.put("applicantAddress", address.getStreetName() + ", " +
                    address.getUnitNumber() + ", " +
                    address.getBuildingNumber() + ", " +
                    address.getCity());
        }
        data.put("count", industrialDesignJasperReportDto.getCount());

        byte[] pdfBytes = generatePdfArabic("examinationReport", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @Override
    public void checkProductsOverflow(Map<String, Object> data, String text, int initialCapacity, int pageCapacity) {
        int pageCount = 0;
        boolean overFlow = text.length() > initialCapacity;
        data.put("additionalPageRequired", overFlow);

        String[] words = text.split("\\s+");
        int wordIndex = 0;
        StringBuilder currentSubstring = new StringBuilder();

        // First part based on initial capacity
        while (wordIndex < words.length && currentSubstring.length() + words[wordIndex].length() + 1 <= initialCapacity) {
            currentSubstring.append(words[wordIndex++]).append(" ");
        }
        currentSubstring.deleteCharAt(currentSubstring.length() - 1); // Remove extra space at the end
        data.put("firstPart", currentSubstring.toString());
        pageCount++;

        // Handling remaining text
        List<String> remainingTextList = new ArrayList<>();
        while (wordIndex < words.length) {
            currentSubstring.setLength(0); // Clearing the StringBuilder
            int wordsRemaining = words.length - wordIndex;
            int wordsToTake = Math.min(wordsRemaining, pageCapacity);
            int charactersRemaining = pageCapacity; // Remaining characters for the current page
            for (int i = 0; i < wordsToTake; i++) {
                String word = words[wordIndex];
                if (currentSubstring.length() + word.length() + 1 > charactersRemaining) {
                    // If adding the current word exceeds the remaining characters, start a new page
                    remainingTextList.add(currentSubstring.toString());
                    currentSubstring.setLength(0);
                    charactersRemaining = pageCapacity; // Reset remaining characters for the new page
                    pageCount++;
                }
                currentSubstring.append(word).append(" ");
                charactersRemaining -= word.length() + 1; // Update remaining characters
                wordIndex++;
            }
            remainingTextList.add(currentSubstring.toString());
            currentSubstring.setLength(0); // Clearing the StringBuilder
            pageCount++;
        }
        data.put("remainingTextList", remainingTextList);
        data.put("countPages", pageCount);
    }

    public void checkTextOverflow(Map<String, Object> data, String text, int initialCapacity, int pageCapacity) {
        int start;
        int end = initialCapacity;
        int pageCount = 0;
        boolean overFlow = text.length() > initialCapacity;
        data.put("additionalPageRequired", overFlow);

        String firstPart = text.substring(0, Math.min(text.length(), initialCapacity));
        data.put("firstPart", firstPart);
        pageCount++;

        if (overFlow) {
            List<String> remainingTextList = new ArrayList<>();
            do {
                pageCount++;
                start = end;
                end = Math.min(start + pageCapacity, text.length());
                String remainingText = text.substring(start, end);
                remainingTextList.add(remainingText);
            } while (end < text.length());

            data.put("remainingTextList", remainingTextList);
        } else {
            data.put("remainingTextList", Collections.singletonList(""));
        }

        data.put("countPages", pageCount);
    }


    public ByteArrayResource convertFileToResource(String filePath) {
        File file = new File(filePath);
        log.info("startConvertFileToResource filePath: " +filePath);
        ByteArrayResource byteArrayResource = null;
        try {
            // Create a FileInputStream object using the File object
            FileInputStream fis = new FileInputStream(file);
            // Create a byte array with the length of the file
            byte[] byteArray = new byte[(int) file.length()];
            // Use the read() method of the FileInputStream object to read the contents of the file into the byte array
            fis.read(byteArray);
            byteArrayResource = new ByteArrayResource(byteArray);
            // Close the FileInputStream object
            fis.close();
            // Use the byte array as needed
            // ...
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
        log.info("endConvertFileToResource filePath: " +byteArrayResource.contentLength());
        return byteArrayResource;
    }


    @SneakyThrows
    public List<DocumentDto> generateUploadSavePdfForPatentApplication(Long applicationId, Long certificateId, String documentType,
                                                                       String applicationNumber, String certificateNumber) {
        log.info("Start generateUploadSavePdfForPatentApplication certificateId {} documentType = {} certificateNumber = {}", certificateId, documentType, certificateNumber);
        String originFilename = applicationNumber + certificateNumber + ".pdf";
        String generationId = UUID.randomUUID().toString();
        ByteArrayResource file = null;
        ResponseEntity<ByteArrayResource> newFile = null;
        List<MultipartFile> files = null;
        file = convertFileToResource(getPatCertFilePath(certificateId, generationId, applicationId));
        log.info("After convertFileToResource  certificateId {} documentType = {} certificateNumber = {}", certificateId, documentType, certificateNumber);
        files = getCustomMultipartFiles(applicationId, file, originFilename, "application/pdf");
        log.info("After getCustomMultipartFiles  certificateId {} documentType = {} certificateNumber = {}", certificateId, documentType, certificateNumber);
        if (newFile != null) {
            files = new ArrayList<>();
            MultipartFile multipartFile =
                    new CustomMultipartFile(applicationId.toString(), documentType + ".jrxml",
                            "application/pdf", false, file.contentLength(), file);
            files.add(multipartFile);
        }
        log.info("addDocuments  certificateId {} documentType = {} certificateNumber = {}", certificateId, documentType, certificateNumber);
        List<DocumentDto> documents = documentsService.addDocuments(files, documentType, IPRS_PATENT.name(), applicationId);
        log.info("deleteFilesWithPrefixAfterComplete  certificateId {} documentType = {} certificateNumber = {}", certificateId, documentType, certificateNumber);
        deleteFilesWithPrefixAfterComplete(generationId);
        System.out.println(documents.get(0).getFileReviewUrl());
        return documents;
    }

    @SneakyThrows
    public String getPatCertFilePath(Long certificateId, String generationId, Long applicationId) {

        log.info("Start getPatCertFilePath  certificateId {} generationId = {} applicationId = {}", certificateId, generationId, applicationId);
        ApplicationInfo applicationInfo = applicationInfoService.findById(applicationId);
        String titleAr = applicationInfo.getTitleAr();
        String titleEn = applicationInfo.getTitleEn();
        Map<String, Object> jasperParams = new HashMap<>();
        jasperParams.put("CERTIFICATE_ID", Integer.parseInt(certificateId.toString()));
        jasperParams.put("APPLICATION_ID", applicationId);         //Long.parseLong(applicationId.toString()));
        ReportRequestDto dto = ReportRequestDto.builder().fileName("patent-grant").params(jasperParams).build();
        List<String> mergedFiles = new ArrayList<>();
        log.info("Start saveFromByteArrayResourcesToPdfFile  certificateId {} generationId = {} applicationId = {}", certificateId, generationId, applicationId);
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "patent_certificate_header").getBody(), "HEADER.pdf", generationId));
        log.info("Start addPatentDescriptionWithDrawingsAndGetFullDescPdfPagesNumber  certificateId {} generationId = {} applicationId = {}", certificateId, generationId, applicationId);
        addPatentDescriptionWithDrawingsAndGetFullDescPdfPagesNumber(generationId, applicationId, titleAr, titleEn, dto, mergedFiles,true);
        log.info("Start mergedFiles2  certificateId {} generationId = {} applicationId = {}", certificateId, generationId, applicationId);
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "patent_certificate_footer").getBody(), "FOOTER.pdf", generationId));
        String finalFilePath = mergePDFs(generationId, mergedFiles);
        String outPutFile = fileDirectory + generationId + "merged_patent_after_numbering.pdf";
        log.info(" filePATHXX: " +outPutFile);
        log.info("After mergePDFs  certificateId {} generationId = {} applicationId = {}", certificateId, generationId, applicationId);
        return addNumberingToPages(finalFilePath, outPutFile,String.valueOf(applicationId),3,1, applicationInfo.getApplicationStatus().getCode());
    }

    private int addPatentDescriptionWithDrawingsAndGetFullDescPdfPagesNumber(String generationId, Long applicationId, String titleAr, String titleEn, ReportRequestDto dto, List<String> mergedFiles,Boolean isCertificate) throws Exception {
        log.info("Start addPatentDescriptionWithDrawingsAndGetFullDescPdfPagesNumber   applicationId = {} titleAr = {} titleEn = {} isCertificate {}", applicationId ,titleAr ,titleEn , isCertificate);
        PdfData fullDescPdfData = getFullPatentDescriptionPdfs(generationId, "FULL_DESCRIPTION", titleAr, titleEn, applicationId,isCertificate);
        log.info("After getFullPatentDescriptionPdfs   applicationId = {} titleAr = {} titleEn = {} isCertificate {}", applicationId ,titleAr ,titleEn , isCertificate);
        mergedFiles.add(fullDescPdfData.getPath());
        log.info("After mergedFiles   applicationId = {} titleAr = {} titleEn = {} isCertificate {}", applicationId ,titleAr ,titleEn , isCertificate);
        String protectedElementsHtml = convertProtectionElementsToHtml(protectionElementService.getProtectionElementsDescByApplicationId(applicationId));
        log.info("After convertProtectionElementsToHtml   applicationId = {} titleAr = {} titleEn = {} isCertificate {}", applicationId ,titleAr ,titleEn , isCertificate);
//        mergedFiles.add(convertHtmlToPdf(generationId, protectedElementsHtml, "PROTECTED_ELEMENTS", titleAr, titleEn,isCertificate).getPath());
//        log.info("After mergedFiles02   applicationId = {} titleAr = {} titleEn = {} isCertificate {}", applicationId ,titleAr ,titleEn , isCertificate);
        if (applicationDrawingService.checkMainDrawExists(applicationId)) {
            log.info("Befor mergedFiles03   applicationId = {} titleAr = {} titleEn = {} isCertificate {}", applicationId ,titleAr ,titleEn , isCertificate);
            mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "patent_certificate_drawings").getBody(), "DRAWINGS.pdf", generationId));
        }
        log.info("Before return   applicationId = {} titleAr = {} titleEn = {} isCertificate {}", applicationId ,titleAr ,titleEn , isCertificate);
        return fullDescPdfData.getPagesNumber();
    }


    private String addNumberingToPages(String inputFile, String outPutFile,String applicationId , int firstOffsetPage,int endOffsetPage, String appStatus) {
        PdfReader reader = null;
        PdfStamper stamp = null;
        try (InputStream inputFileStream = new FileInputStream(inputFile)) {
            reader = new PdfReader(inputFileStream);
            int numberOfPages = reader.getNumberOfPages();
            stamp = new PdfStamper(reader, new FileOutputStream(outPutFile));
            PdfContentByte over;
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            for (int i = firstOffsetPage; i <= numberOfPages-endOffsetPage ; i++) {
                over = stamp.getOverContent(i);
                over.beginText();
                over.setFontAndSize(bf, 12); // Adjust font size as needed
                float x = reader.getPageSize(i).getWidth() / 2 - 10;
                float y = (i != 1) ? reader.getPageSize(i).getTop() - 65 : reader.getPageSize(i).getTop() - 55; // Adjust vertical position as needed
                over.setTextMatrix(x, y);
                over.showText("-" + (i) + "-"); // Start numbering from 2 on the third page
                over.endText();
                if(appStatus.equalsIgnoreCase(ApplicationStatusEnum.ACCEPTANCE.name())
                        || appStatus.equalsIgnoreCase(ApplicationStatusEnum.THE_TRADEMARK_IS_REGISTERED.name())){
                    over.beginText();
                    over.setFontAndSize(bf, 10); // Adjust font size as needed
                    float x2 =  15 ;
                    float y2 =  15; // Adjust vertical position as needed
                    over.setTextMatrix(x2, y2);
                    over.showText(applicationId); // Your second text
                    over.endText();
                }
            }
        } catch (Exception de) {
            de.printStackTrace();
        }finally {
            try {
                if (reader != null)
                    reader.close();
                if (stamp != null)
                    stamp.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outPutFile;
    }

    @SneakyThrows
    public String getApplicationDocumentFilePath(String generationId, Long applicationId, String titleAr, String titleEn) {
        log.info("StartgetApplicationDocumentFilePath   applicationId = {} titleAr = {} titleEn = {} generationId {}", applicationId ,titleAr ,titleEn , generationId);
        String appStatus = applicationInfoService.getApplicationStatus(applicationId);
        Map<String, Object> jasperParams = new HashMap<>();
        jasperParams.put("APPLICATION_ID", applicationId);
        ReportRequestDto dto = ReportRequestDto.builder().fileName("patent-grant").params(jasperParams).build();
        List<String> mergedFiles = new ArrayList<>();
        PdfData summaryPdfData =convertHtmlToPdf(
                generationId,
                decodeHtmlImages64AndUploadThem(  patentAttributeChangeLogService.getAttributeValueByApplicationId(applicationId, "arSummary"),null)
                , "SUMMARY", titleAr, titleEn,false);
        mergedFiles.add(summaryPdfData.getPath());
        int fullDescPdfPagesNumber = addPatentDescriptionWithDrawingsAndGetFullDescPdfPagesNumber(generationId, applicationId, titleAr, titleEn, dto, mergedFiles,false);
        String finalFilePath = mergePDFs(generationId, mergedFiles);
        String outPutFile = fileDirectory + generationId + "merged_patent_after_numbering.pdf";
        log.info(" filePATHXX: " +outPutFile);
        String pdfPath = addNumberingToPages(finalFilePath, outPutFile,String.valueOf(applicationId),1,0, appStatus);
        updateApplicationWithPDFData(applicationId, fullDescPdfPagesNumber + summaryPdfData.getPagesNumber());
        log.info("endgetApplicationDocumentFilePath   applicationId = {} pdfPath = {}  ", applicationId ,pdfPath);
        return pdfPath;
    }


    private void updateApplicationWithPDFData(Long applicationId, int totalPagesOfFllDescAndSummary) {
        applicationInfoService.updateAppTotalNumberOfPagesAndCalculateClaimPages(applicationId, Long.valueOf(totalPagesOfFllDescAndSummary));
    }
    public String convertProtectionElementsToHtml(List<String> protectionElements) {
        StringBuilder sb = new StringBuilder();
//        sb.append("<ol dir = \"rtl\" style =\"font-size:14px\">");
        protectionElements.forEach(elem -> {
//            sb.append("<li>");
            sb.append("<p style =\"font-size:14pt\" >");
            sb.append(elem);
            sb.append("</p>");
//            sb.append("<li>");
        });
//        sb.append("</ol>");
        return sb.toString();
    }

    private String saveFromByteArrayResourcesToPdfFile(ByteArrayResource pdfBytes, String outputFile, String generateId) {
        String finalDrawingsPath = generateId + "_" + outputFile;
        String fullPath = fileDirectory + finalDrawingsPath;
        log.info(" filePATHXX: " +fullPath);
        // Create the directory if it doesn't exist
        File directory = new File(fullPath);
        if (!directory.getParentFile().exists())
            directory.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            // Write the PDF data to the file
            fos.write(pdfBytes.getByteArray());
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fullPath;
    }


    @SneakyThrows
    public PdfData convertHtmlToPdf(String generationId, String html, String fileName, String titleAr, String titleEn,Boolean isCertificate) {
        StringBuilder sb = new StringBuilder();
        sb.append(" <html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Sample HTML Document</title><style> p { margin: 0; } img{object-fit: contain;} </style></head><body>");
        sb.append("<div style=\"text-align-last:justify; text-align:justify; direction: rtl; margin-left: auto; display: flex; justify-content: center; align-items: center; margin-right: auto; width: fit-content;\">");
        sb.append("<p style='font-family:Simplified Arabic; font-size:14pt;'>");
        sb.append(PatentDescriptiveUtil.removeSomeStylingFromHTML(html));
        sb.append("</p> </div> </body></html>");
        return convertWordToPdf(generationId, fileName, titleAr, titleEn, sb,isCertificate);
    }


    private PdfData convertWordToPdf(String generationId, String fileName, String titleAr, String titleEn, StringBuilder sb,Boolean isCertificate) throws Exception {
        String descriptionFileNameDoc = generationId + "_" + fileName + ".docx";
        String descriptionFileNamePdf = generationId + "_" + fileName + ".pdf";
        File descriptionFileDoc = new File(fileDirectory + descriptionFileNameDoc);
        log.info(" filePATHXX: " +descriptionFileDoc);
        if (!descriptionFileDoc.getParentFile().exists())
            descriptionFileDoc.getParentFile().mkdirs();

            try {
                com.aspose.words.FontSettings fontSettingsss = com.aspose.words.FontSettings.getDefaultInstance();
                FontSourceBase[] fontSourcesss = fontSettingsss.getFontsSources();
                for (FontSourceBase font : fontSourcesss) {
                    System.out.println("Font source: " + font);
                }
                InputStream inputStream = new ClassPathResource("./templates/Original_2.docx").getInputStream() ;
                com.aspose.words.Document originalDoc = new  com.aspose.words.Document(inputStream);
                com.aspose.words.Document newDoc = originalDoc.deepClone();
                com.aspose.words.Document tempnewDoc = originalDoc.deepClone();
                String fontsFolderPath = getClass().getClassLoader().getResource("fonts").getPath();
                FontSettings fontSettings = new FontSettings();
                fontSettings.setFontsFolder(fontsFolderPath, true);
                fontSettings.getSubstitutionSettings().getDefaultFontSubstitution().setEnabled(false);
                fontSettings.getSubstitutionSettings().getTableSubstitution().addSubstitutes("Simplified Arabic Regular" );
                newDoc.setFontSettings(fontSettings);
                tempnewDoc.setFontSettings(fontSettings);
                // Create a DocumentBuilder for the cloned document
                if (fileName.equals("SUMMARY") || fileName.equals("FULL_DESCRIPTION") || isCertificate) {
                    newDoc.getRange().replace("${HEADER_AR}", StringUtil.getTextValueOrEmptyValue(titleAr), new FindReplaceOptions());
                    newDoc.getRange().replace("${HEADER_EN}",  StringUtil.getTextValueOrEmptyValue(titleEn), new FindReplaceOptions());
                    ///tempnewDoc
                    tempnewDoc.getRange().replace("${HEADER_AR}", StringUtil.getTextValueOrEmptyValue(titleAr), new FindReplaceOptions());
                    tempnewDoc.getRange().replace("${HEADER_EN}",  StringUtil.getTextValueOrEmptyValue(titleEn), new FindReplaceOptions());
                } else {
                    newDoc.getRange().replace("${HEADER_AR}", "", new FindReplaceOptions());
                    newDoc.getRange().replace("${HEADER_EN}", "", new FindReplaceOptions());
                    ///tempnewDoc
                    tempnewDoc.getRange().replace("${HEADER_AR}", "", new FindReplaceOptions());
                    tempnewDoc.getRange().replace("${HEADER_EN}", "", new FindReplaceOptions());
                }
                newDoc.getRange().replace("${HEADING}", PatentAttributeNames.valueOf(fileName).getMainSectionAr(), new FindReplaceOptions());
                tempnewDoc.getRange().replace("${HEADING}", PatentAttributeNames.valueOf(fileName).getMainSectionAr(), new FindReplaceOptions());

                com.aspose.words.DocumentBuilder builder = new  com.aspose.words.DocumentBuilder(newDoc);
                com.aspose.words.DocumentBuilder tempbuilder = new  com.aspose.words.DocumentBuilder(tempnewDoc);
                tempbuilder.getFont().setName("Simplified Arabic");
                tempbuilder.getFont().setSize(14);


                builder.getFont().setName("Simplified Arabic");
                builder.getFont().setSize(14);


                tempbuilder.moveTo(tempnewDoc.getFirstSection().getBody().getLastParagraph());
                builder.moveTo(newDoc.getFirstSection().getBody().getLastParagraph());

                tempbuilder.insertHtml(sb.toString());


                HtmlSaveOptions saveOptions = new HtmlSaveOptions();
                saveOptions.setPrettyFormat(true); // For better readability
                saveOptions.setExportFontResources(false); // Avoid external font references
                String  imagesFolder =  fileDirectory +generationId;
                saveOptions.setImagesFolder(Paths.get(imagesFolder).toString()); // Specify the images folder
                // Convert to HTML string
                String modifiedHtml = tempnewDoc.toString(saveOptions);

              // String modifiedHtmlContent = modifiedHtml.replaceAll("font-size:\\s*\\d+(?:px|pt|em|%)?", "font-size:14pt");
                String    modifiedHtmlContent =  forceFontSizeAsLangualge(modifiedHtml);

               builder.insertHtml(modifiedHtmlContent);

                // Save the new document to the specified file
                newDoc.save(fileDirectory + descriptionFileNameDoc);
                deleteTempImagesDirectory(imagesFolder);
            } catch (Exception e) {
                e.printStackTrace();
                throw  e ;
            }

        return convertToPDF(fileDirectory + descriptionFileNameDoc, fileDirectory + descriptionFileNamePdf, fileName);
    }

    private static String   forceFontSizeAsLangualge(String htmlContent ){
        Document document = Jsoup.parse(htmlContent);
        String arabicFontFamily = "Simplified Arabic";
        String englishFontFamily = "Times New Roman";
        String arabicFontSize = "14pt";
        String englishFontSize = "12pt";
        // Select all span tags or elements with text content
        for (Element element : document.select("span")) {
            String text = element.text();
            String updatedText = processNumbersWithStyle(text, englishFontSize, englishFontFamily);

            if (containsArabic(updatedText)) {
               /// element.attr("style", String.format("font-size:%s; font-family:%s;", arabicFontSize, arabicFontFamily));
                updateFontStyles(element, arabicFontSize, arabicFontFamily);
            } else if (containsEnglish(updatedText)) {
             //   element.attr("style", String.format("font-size:%s; font-family:%s;", englishFontSize, englishFontFamily));
                updateFontStyles(element, englishFontSize, englishFontFamily);
            }
        }
        // Return the modified HTML as a string
        return document.html();
    }
    // Helper method to check if text contains Arabic characters
    private static boolean containsArabic(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (char c : text.toCharArray()) {
            if ((c >= 0x0600 && c <= 0x06FF) // Arabic Unicode block
                    || (c >= 0x0750 && c <= 0x077F) // Arabic Supplement block
                    || (c >= 0x08A0 && c <= 0x08FF)) { // Arabic Extended block
                return true;
            }
        }
        return false;
    }

    private static boolean containsEnglish(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (char c : text.toCharArray()) {
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                return true;
            }
        }
        return false;
    }

    private static String processNumbersWithStyle(String text, String fontSize, String fontFamily) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        // Regex to find numbers in the text
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher matcher = numberPattern.matcher(text);

        // Use a StringBuilder to build the new text
        StringBuilder result = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            // Append the text before the match
            result.append(text, lastEnd, matcher.start());

            // Wrap the matched number with the desired style
            String number = matcher.group();
            result.append(String.format("<span style=\"font-size:%s; font-family:%s;\">%s</span>", fontSize, fontFamily, number));

            // Update the end position
            lastEnd = matcher.end();
        }

        // Append the remaining text after the last match
        result.append(text.substring(lastEnd));

        return result.toString();
    }

    public static void updateFontStyles(Element element, String fontSize, String fontFamily) {
        // Get the current style attribute
        String currentStyle = element.attr("style");

        // Parse the existing styles into a map
        Map<String, String> styles = new HashMap<>();
        if (currentStyle != null && !currentStyle.isEmpty()) {
            for (String style : currentStyle.split(";")) {
                String[] keyValue = style.split(":");
                if (keyValue.length == 2) {
                    styles.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }

        // Update or add the font-size and font-family
        styles.put("font-size", fontSize);
        styles.put("font-family", fontFamily);

        // Reconstruct the style attribute
        StringBuilder updatedStyle = new StringBuilder();
        for (Map.Entry<String, String> entry : styles.entrySet()) {
            updatedStyle.append(entry.getKey()).append(":").append(entry.getValue()).append("; ");
        }

        // Apply the updated style
        element.attr("style", updatedStyle.toString().trim());
    }


    public PdfData getFullPatentDescriptionPdfs(String generationId, String fileName, String titleAr, String titleEn, Long applicationId,Boolean isCertificate) throws Exception {
        StringBuilder sb = getFullPatentDescriptionHtml(applicationId);

        return convertWordToPdf(generationId, fileName, titleAr, titleEn, sb,isCertificate);
    }

    private StringBuilder getFullPatentDescriptionHtml(Long applicationId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" <html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Sample HTML Document</title><style> p { margin: 0; } img{object-fit: contain;}  </style></head><body>");
        sb.append("<div style=\"text-align-last:justify; text-align:justify; direction: rtl; margin-left: auto; display: flex; justify-content: center; align-items: center; margin-right: auto; width: fit-content;\">");
        sb.append("<h2 style = \"text-align:right; text-decoration:underline; color:black; font-family:Simplified Arabic;  font-size:14pt\">خلفية الاختراع</h2>");
        System.out.println("MOASAFA--------------inventionBackgroundAr--------");
        sb.append(decodeHtmlImages64AndUploadThem( patentAttributeChangeLogService.getAttributeValueByApplicationId(applicationId, "inventionBackgroundAr"),null));
        sb.append("<h2 style = \"text-align:right; text-decoration:underline; color:black; font-family:Simplified Arabic;  font-size:14pt\">الوصف العام للاختراع</h2>");
        System.out.println("MOASAFA--------------    inventionDescriptionAr --------");
        sb.append(decodeHtmlImages64AndUploadThem( patentAttributeChangeLogService.getAttributeValueByApplicationId(applicationId, "inventionDescriptionAr"),null));
        appendGraphicsDescriptionIfContainsImage(applicationId, sb);
        sb.append("<h2 style = \"text-align:right; text-decoration:underline; color:black; font-family:Simplified Arabic;  font-size:14pt\">الوصف التفصيلي</h2>");
        sb.append(decodeHtmlImages64AndUploadThem( patentAttributeChangeLogService.getAttributeValueByApplicationId(applicationId, "detailedDescriptionAr"),null));
        sb.append("<h2 style = \"text-align:center; text-decoration:underline; color:black; page-break-before: always; font-family:Simplified Arabic;   font-size:14pt\">عناصر الحماية</h2>");
        sb.append(decodeHtmlImages64AndUploadThem( patentAttributeChangeLogService.getAttributeValueByApplicationId(applicationId, "arProtection"),null));
        sb.append("</div> </body></html>");
        return sb;
    }

    private void appendGraphicsDescriptionIfContainsImage(Long applicationId, StringBuilder sb) {
        List<ApplicationDrawing> applicationDrawings = applicationDrawingService.getAppDrawing(applicationId);
        if (applicationDrawings != null && !applicationDrawings.isEmpty()) {
            String graphicsDescriptionHtml = decodeHtmlImages64AndUploadThem(
                    patentAttributeChangeLogService.getAttributeValueByApplicationId(applicationId, "graphicsDescriptionAr"),
                    null
            );
            sb.append("<h2 style = \"text-align:right; text-decoration:underline; color:black; font-size:14pt\">شرح مختصر للرسومات</h2>");
            sb.append(graphicsDescriptionHtml);
        }
    }


    private String decodeHtmlImages64AndUploadThem(String html, Long applicationId) {
        String modifiedHtml = PatentDescriptiveUtil.removeSomeStylingFromHTML(html) ;
        Document document = Jsoup.parse(modifiedHtml);
//        Elements images = document.select("img");
//        for (Element img : images) {
//            String base64Image = img.attr("src");
//            if (base64Image.startsWith("data:image")) {
//                List<MultipartFile> files = new ArrayList<>();
//                String base64Data = base64Image.split(",")[1];
//                byte[] imageBytes = Base64.getDecoder().decode(base64Data);
//                // Create a MultipartFile
//                MultipartFile multipartFile = getCustomMultipartFile(applicationId, new ByteArrayResource(imageBytes), "image.png", "image.png");
//                files.add(multipartFile);
//                List<DocumentDto> documents = documentsService.addDocuments(files, "Issue Certificate", IPRS_PATENT.name(), null);
//                if (Objects.nonNull(documents) && !documents.isEmpty()) {
//                    img.attr("src", documents.get(0).getFileReviewUrl());
//                }
//            }
//        }
        return document.html();
    }


    private MultipartFile getCustomMultipartFile(Long applicationId, ByteArrayResource file, String originalFilename, String contentType) {

        Boolean isEmpty = file.getByteArray().length <= 0;
        MultipartFile multipartFile =
                new CustomMultipartFile("any-name", originalFilename,
                        contentType, isEmpty, file.getByteArray().length, file);
        return multipartFile;

    }
    @Override
    public List<MultipartFile> getCustomMultipartFiles(Long applicationId, ByteArrayResource file, String originalFilename, String contentType) {
        log.info("startgetCustomMultipartFiles  filePath: " +applicationId);
        List<MultipartFile> files = new ArrayList<>();
        Boolean isEmpty = file.getByteArray().length <= 0;
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), originalFilename,
                        contentType, isEmpty, file.getByteArray().length, file);
        files.add(multipartFile);
        log.info("endgetCustomMultipartFiles filePath: " +applicationId);

        return files;
    }


    private void injectTemplateHeaderVariables(String titleAr, String titleEn, WordprocessingMLPackage wordMLPackage, String attribute, Boolean isCertificate) throws Exception {
        HeaderPart headerPart = wordMLPackage.getDocumentModel().getSections().get(0).getHeaderFooterPolicy().getFirstHeader();
        if (attribute.equals("SUMMARY") || attribute.equals("FULL_DESCRIPTION") || isCertificate) {
            replaceVariablesInHeaders("${HEADER_AR}", StringUtil.getTextValueOrEmptyValue(titleAr), headerPart);
            replaceVariablesInHeaders("${HEADER_EN}", StringUtil.getTextValueOrEmptyValue(titleEn), headerPart);
        } else {
            replaceVariablesInHeaders("${HEADER_AR}", "", headerPart);
            replaceVariablesInHeaders("${HEADER_EN}", "", headerPart);
        }
        replaceVariablesInHeaders("${HEADING}", PatentAttributeNames.valueOf(attribute).getMainSectionAr(), headerPart);
    }


    private static WordprocessingMLPackage getWordProcessingMlPackage(StringBuilder sb) throws Exception {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new ClassPathResource("./templates/Original_2.docx").getInputStream());
        addHtmlToMainDocumentAltChunk(sb, wordMLPackage);
        VariablePrepare.prepare(wordMLPackage);
        return wordMLPackage;
    }

    private static void addHtmlToMainDocumentAltChunk(StringBuilder sb, WordprocessingMLPackage wordMLPackage) throws InvalidFormatException {
        AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(new PartName("/hw.html"));
        afiPart.setBinaryData(sb.toString().getBytes(StandardCharsets.UTF_8));
        afiPart.setContentType(new ContentType("text/html"));
        afiPart.getAltChunkType();


        ObjectFactory factory = new ObjectFactory();
        R run = factory.createR();
        RPr runProperties = factory.createRPr();
        RFonts rFonts = factory.createRFonts();
        rFonts.setAscii("Simplified Arabic"); // Set font for ASCII characters
        rFonts.setHAnsi("Simplified Arabic"); // Set font for other characters
        rFonts.setCs("Simplified Arabic");
        runProperties.setRFonts(rFonts);

        // Optional: Set font size
        HpsMeasure size = factory.createHpsMeasure();
        size.setVal(BigInteger.valueOf(28)); // Size in half-points (24 = 12pt)
        runProperties.setSz(size);

        run.setRPr(runProperties);

        // Create a paragraph and add the run
        P paragraph = factory.createP();
        paragraph.getContent().add(run);



        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
        mdp.getContent().add(paragraph);
        Relationship altChunkRel = wordMLPackage.getMainDocumentPart().addTargetPart(afiPart);

        CTAltChunk ac = org.docx4j.jaxb.Context.getWmlObjectFactory().createCTAltChunk();
        ac.setId(altChunkRel.getId());
        updateDefaultFont(wordMLPackage, "Simplified Arabic");

        mdp.addObject(ac);
    }


    private static void updateDefaultFont(WordprocessingMLPackage wordMLPackage, String fontName)   {
        // Get the factory and style definitions
        ObjectFactory factory = new ObjectFactory();

        // Update the default run properties
        Styles styles = wordMLPackage.getMainDocumentPart().getStyleDefinitionsPart().getJaxbElement();


        for (Style style : styles.getStyle()) {
            if ("Normal".equals(style.getStyleId())) {
                if (style.getRPr() == null) {
                    style.setRPr(factory.createRPr());
                }

                RFonts rFonts = factory.createRFonts();
                rFonts.setAscii(fontName);
                rFonts.setHAnsi(fontName);
                rFonts.setCs(fontName);

                style.getRPr().setRFonts(rFonts);

            }
        }
    }

    private static void replaceVariableInHeader(HeaderPart headerPart, String variable, String replacementText) {
        List<Object> paragraphs = headerPart.getContent();
        for (Object paragraphObj : paragraphs) {
            if (paragraphObj instanceof P) {
                P paragraph = (P) paragraphObj;

                List<Object> runs = paragraph.getContent();
                for (Object runObj : runs) {
                    if (runObj instanceof R) {
                        R run = (R) runObj;

                        List<Object> texts = run.getContent();
                        for (Object textObj : texts) {
                            if (textObj instanceof JAXBElement) {

                                JAXBElement mm = (JAXBElement) textObj;
                                Text text = (Text) mm.getValue();

                                String currentText = text.getValue();
                                if (currentText.contains(variable) && replacementText != null) {
                                    text.setValue(currentText.replace(variable, replacementText));
                                    org.docx4j.wml.BooleanDefaultTrue rtlEnable = new BooleanDefaultTrue();
                                    rtlEnable.setVal(true);
                                    run.getRPr().setRtl(rtlEnable);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void replaceVariablesInHeaders(String variable, String value, HeaderPart headerPart) throws Exception {
        replaceVariableInHeader(headerPart, variable, value);
    }



    public PdfData convertToPDF(String docPath, String pdfPath, String fileName) {

        return convertToPDFUsingAsposeWords(docPath, pdfPath, fileName);


//        if (   "local".equalsIgnoreCase(activeProfile) || "development".equalsIgnoreCase(activeProfile)) {
//            return convertToPDFUsingAsposeWords(docPath, pdfPath, fileName);
//        } else {
//            return convertToPDFUsingDocument4j(docPath, pdfPath, fileName);
//        }
    }




    @SneakyThrows
    public PdfData convertToPDFUsingDocument4j(String docPath, String pdfPath, String fileName) {
        File inputWord = new File(docPath);
        if (!inputWord.getParentFile().exists())
            inputWord.getParentFile().mkdirs();
        if (!inputWord.exists())
            inputWord.createNewFile();
        File outputFile = new File(pdfPath);
        if (!outputFile.getParentFile().exists())
            outputFile.getParentFile().mkdirs();
        if (!outputFile.exists())
            outputFile.createNewFile();

        log.info("Start To Conversion Of Word To Pdf ------>", inputWord.getParentFile());
        OutputStream outputStream = null;
        IConverter converter = null;
        try {
            InputStream docxInputStream = new FileInputStream(inputWord);
            outputStream = new FileOutputStream(outputFile);
            log.info("Building Converter---------->", inputWord.getParentFile());
            converter = fileConverterFactory.getConverter();
            log.info("Ending Converter---------->", inputWord.getParentFile());
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            log.info("path ==>> {} converted to pdf successfully and new path is ==>> {}", docPath, pdfPath);
        } catch (Exception e) {
            log.error("path failed to convert ==>> {}", docPath, e);
        } finally {
            if(outputStream != null)
                outputStream.close();
        }
        log.info("path ==>> {} converted to pdf successfully and new path is ==>> {}", docPath, pdfPath);
        return buildPdfDataDto( outputFile.getPath(), fileName);

    }


    @SneakyThrows
    public PdfData convertToPDFUsingAsposeWords(String docPath, String pdfPath, String fileName) {
        log.info(" convertToPDFUsingAsposeWords start  ");
        log.info(" doc  filePATHXX: " +docPath);
        log.info(" pdf  filePATHXX: " +pdfPath);
        File inputWord = new File(docPath);
        if (!inputWord.getParentFile().exists()) {
            inputWord.getParentFile().mkdirs();
        }
        if (!inputWord.exists()) {
            inputWord.createNewFile();
        }

        File outputFile = new File(pdfPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        log.info("Starting conversion of Word to PDF: {}", inputWord.getParentFile());
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            com.aspose.words.Document document = new com.aspose.words.Document(docPath);
            try {
                document.getLayoutOptions().setTextShaperFactory(HarfBuzzTextShaperFactory.getInstance());
            } catch (Exception e) {
                log.warn("HarfBuzzTextShaperFactory is not available. Proceeding without it.", e);
            }
            // Save the document as a PDF
            document.save(outputStream,  SaveFormat.PDF);
            log.info("Path {} converted to PDF successfully. New path: {}", docPath, pdfPath);
        } catch (Exception e) {
            log.error("Failed to convert path: {}", docPath, e);
        }
        log.info("Path {} converted to PDF successfully. New path: {}", docPath, pdfPath);
        return buildPdfDataDto(outputFile.getPath(), fileName);
    }


    private PdfData buildPdfDataDto(String pdfPath, String fileName){
        int numberOfPages = countPdfNumberOfPages(pdfPath);
        return new PdfData(pdfPath, numberOfPages, fileName);
    }
    private int countPdfNumberOfPages(String pdfPath){
        return PdfUtil.countPdfNumberOfPages(pdfPath);
    }

    public String mergePDFs(String generationId, List<String> pdfFiles) throws IOException {
        String pdfFilePath = fileDirectory + generationId + "merged_patent.pdf";
        log.info(" filePATHXX: " +pdfFilePath);

        PDDocument mergedDocument = new PDDocument();
        List<PDDocument> openDocuments = new ArrayList<>();
        try {
            for (String pdfFile : pdfFiles) {
                PDDocument document = PDDocument.load(new File(pdfFile));
                for (PDPage page : document.getPages()) {
                    mergedDocument.addPage(page);
                }
                openDocuments.add(document);
                // Do not close individual document here
            }

            mergedDocument.save(pdfFilePath);
        } finally {
            mergedDocument.close(); // Close the merged document after saving
            // Close individual documents outside the try-finally block
            for (PDDocument openDocument : openDocuments) {
                openDocument.close();
            }
        }

        return pdfFilePath;
    }

    public void deleteFilesWithPrefixAfterComplete(String filePrefix) {
        log.info("startDeleteFilesWithPrefixAfterComplete  filePrefix: " + filePrefix);
        try {
            String directoryPath = fileDirectory;
            File directory = new File(directoryPath);
            File[] files = directory.listFiles((dir, name) -> name.startsWith(filePrefix));
            log.info("secondstartDeleteFilesWithPrefixAfterComplete  filePrefix: " + filePrefix);
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                      boolean deleted = file.delete();
                        if (deleted) {
                             log.info("Deleted file: " + file.getAbsolutePath());
                        } else {
                             log.error("Failed to delete file: " + file.getAbsolutePath());
                       }
                    }
                }
            }
        }catch (Exception ex){
            log.error("endDeleteFilesWithPrefixAfterComplete  filePrefix: " , ex);
        }

        log.info("endDeleteFilesWithPrefixAfterComplete  filePrefix: " + filePrefix);
    }


    public   void  deleteTempImagesDirectory(String imagesPath) {
        File directory   = new File(imagesPath);
        try {
            if (!directory.exists()) {
                System.out.println("TempImagesDirectory  does not exist: " + directory.getAbsolutePath());
            }
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            boolean deleted = file.delete();
                            if (deleted) {
                                log.info("Deleted file: " + file.getAbsolutePath());
                            } else {
                                log.error("Failed to delete file: " + file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
            boolean deleted = directory.delete();
            if (deleted) {
                System.out.println("Deleted images temp folder : " + directory.getAbsolutePath());
            } else {
                System.err.println("Failed to delete images temp folder : " + directory.getAbsolutePath());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @SneakyThrows
    public String getPatCertFilePathIndusterial(Long certificateId, String generationId, Long applicationId) {
        String appStatus = applicationInfoService.getApplicationStatus(applicationId);
        Map<String, Object> jasperParams = new HashMap<>();
        jasperParams.put("CERTIFICATE_ID", Integer.parseInt(certificateId.toString()));
        jasperParams.put("APPLICATION_ID", Integer.parseInt(applicationId.toString()));
        ReportRequestDto dto = ReportRequestDto.builder().fileName("industerial-grant").params(jasperParams).build();
        List<String> mergedFiles = new ArrayList<>();
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "Industerial_certificate_header-1").getBody(), "HEADER-1.pdf", generationId));
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "Industerial_certificate_header-2").getBody(), "HEADER-2.pdf", generationId));
        addIndusterialDescriptionWithDrawings(generationId, applicationId,  dto, mergedFiles);
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "Industerial_certificate_footer").getBody(), "FOOTER.pdf", generationId));
        String finalFilePath = mergePDFs(generationId, mergedFiles);
        String outPutFile = fileDirectory + generationId + "merged_induterial_after_numbering.pdf";
        log.info(" filePATHXX: " +outPutFile);
        return addNumberingToPages(finalFilePath, outPutFile,String.valueOf(applicationId),3,1, appStatus);
    }
    private void addIndusterialDescriptionWithDrawings(String generationId, Long applicationId,  ReportRequestDto dto, List<String> mergedFiles) throws Exception {
        List<DesignSample> designSampleList = designSampleService.findDesignSamplesByAppId(applicationId);
        int i = 1 ;
        for(DesignSample designSample : designSampleList){
            dto.getParams().put("SAMPLE_DESIGN_ID", designSample.getId().intValue());
            if(i == 1){
                mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "Industerial_certificate_drawings-1").getBody(), "DRAWINGS" + i + ".pdf", generationId));
            }else {
                dto.getParams().put("sample_number", i);
                mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "Industerial_certificate_drawings-2").getBody(), "DRAWINGS" + i + ".pdf", generationId));
            }
            i++ ;
        }
    }

    @Override
    public List<DocumentDto> generateUploadSavePdfForIndustrialApplication(Long applicationId, Long certificateId, String documentType,
                                                                           String applicationNumber, String certificateNumber) {
        String originFilename = applicationNumber + certificateNumber + ".pdf";
        String generationId = UUID.randomUUID().toString();
        ByteArrayResource file = null;
        ResponseEntity<ByteArrayResource> newFile = null;
        List<MultipartFile> files = null;
        file = convertFileToResource(getPatCertFilePathIndusterial(certificateId, generationId, applicationId));
        files = getCustomMultipartFiles(applicationId, file, originFilename, "application/pdf");
        if (newFile != null) {
            files = new ArrayList<>();
            MultipartFile multipartFile =
                    new CustomMultipartFile(applicationId.toString(), documentType + ".jrxml",
                            "application/pdf", false, file.contentLength(), file);
            files.add(multipartFile);
        }
        List<DocumentDto> documents = documentsService.addDocuments(files, documentType, IPRS_INDUSTRIAL_DESIGN.name(), applicationId);
        deleteFilesWithPrefixAfterComplete(generationId);
        System.out.println(documents.get(0).getFileReviewUrl());
        return documents;
    }

    @Override
    public List<DocumentDto> generateUploadSavePdfForIntegratedCircuit(Long applicationId, Long certificateId, String documentType,
                                                                       String applicationNumber, String certificateNumber) {
        String originFilename = applicationNumber + certificateNumber + ".pdf";
        String generationId = UUID.randomUUID().toString();
        ByteArrayResource file = null;
        ResponseEntity<ByteArrayResource> newFile = null;
        List<MultipartFile> files = null;
        file = convertFileToResource(getPatCertFilePathIntegratedCircuit(certificateId, generationId, applicationId));
        files = getCustomMultipartFiles(applicationId, file, originFilename, "application/pdf");
        if (newFile != null) {
            files = new ArrayList<>();
            MultipartFile multipartFile =
                    new CustomMultipartFile(applicationId.toString(), documentType + ".jrxml",
                            "application/pdf", false, file.contentLength(), file);
            files.add(multipartFile);
        }
        List<DocumentDto> documents = documentsService.addDocuments(files, documentType, IPRS_INTEGRATED_CIRCUITS.name(), applicationId);
        deleteFilesWithPrefixAfterComplete(generationId);
        System.out.println(documents.get(0).getFileReviewUrl());
        return documents;
    }



    @SneakyThrows
    public String getPatCertFilePathIntegratedCircuit(Long certificateId, String generationId, Long applicationId) {
        String appStatus = applicationInfoService.getApplicationStatus(applicationId);
        Map<String, Object> jasperParams = new HashMap<>();
        jasperParams.put("CERTIFICATE_ID", Integer.parseInt(certificateId.toString()));
        jasperParams.put("APPLICATION_ID", Integer.parseInt(applicationId.toString()));
        ReportRequestDto dto = ReportRequestDto.builder().fileName("integrated_circuits-grant").params(jasperParams).build();
        List<String> mergedFiles = new ArrayList<>();
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "integrated_circuits_certificate_header").getBody(), "HEADER-1.pdf", generationId));
        addIntegratedCircuitDescriptionWithDrawings(generationId, dto, mergedFiles);
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "integrated_circuits_certificate_footer").getBody(), "FOOTER.pdf", generationId));
        return mergePDFs(generationId, mergedFiles);
    }

    private void addIntegratedCircuitDescriptionWithDrawings(String generationId, ReportRequestDto dto, List<String> mergedFiles) throws Exception {
        mergedFiles.add(saveFromByteArrayResourcesToPdfFile(jasperReportsClient.exportToPdf(dto, "integrated_circuits_certificate_drawings").getBody(), "DRAWINGS.pdf", generationId));
    }


    @Override
    public String convertWordToHtml(MultipartFile file) {
        return convertWordToHtmlNew(file);
//        if ("development".equalsIgnoreCase(activeProfile) || "local".equalsIgnoreCase(activeProfile) || "test".equalsIgnoreCase(activeProfile) || "staging".equalsIgnoreCase(activeProfile)) {
//            return convertWordToHtmlNew(file);
//        } else {
//            return convertWordToHtmlOld(file);
//        }
    }


    public String convertWordToHtmlOld(MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty";
        }
        try {
            byte[] fileBytes = file.getBytes();
            com.aspose.words.Document doc = new com.aspose.words.Document(new ByteArrayInputStream(fileBytes));
            HtmlSaveOptions saveOptions = new HtmlSaveOptions();
            saveOptions.setExportImagesAsBase64(true);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            doc.save(outputStream, saveOptions);
            String htmlContent = outputStream.toString(StandardCharsets.UTF_8.name());
            return htmlContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "Conversion failed: " + e.getMessage();
        }
    }

    public String convertWordToHtmlNew(MultipartFile file) {
        boolean convertToServer = false;
        if (file.isEmpty()) {
            return "File is empty";
        }

        try {
            InputStream inputStream = file.getInputStream();

            com.aspose.words.Document doc = new com.aspose.words.Document(inputStream);
          doc.getLayoutOptions().setTextShaperFactory(HarfBuzzTextShaperFactory.getInstance());
//            // Configure font settings
//            String fontsFolderPath = getClass().getClassLoader().getResource("fonts").getPath();
//            FontSettings fontSettings = new FontSettings();
//            fontSettings.setFontsFolder(fontsFolderPath, true);
//            doc.setFontSettings(fontSettings);
            // Configure HTML save options
            HtmlSaveOptions saveOptions = new HtmlSaveOptions();
            saveOptions.setExportImagesAsBase64(true);
            saveOptions.setPrettyFormat(false);
            saveOptions.setEncoding(StandardCharsets.UTF_8);
            saveOptions.setExportPageSetup(false);
            saveOptions.setExportHeadersFootersMode(ExportHeadersFootersMode.NONE);
            // Enable support for math and chemical equations
            saveOptions.setExportFontResources(false); // Use system fonts for equations
             saveOptions.setExportLanguageInformation(true); // Include language information for proper rendering
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            doc.save(outputStream, saveOptions);
            String htmlContent = outputStream.toString(StandardCharsets.UTF_8.name());
            // Conditionally replace base64 images with URLs if convertToServer is true
            if (convertToServer) {
                htmlContent = replaceBase64ImagesWithUrls(htmlContent);
            }

            return   htmlContent  ;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error converting file";
        }
    }

    // Method to replace base64 images with URLs if convertToServer is true
    private String replaceBase64ImagesWithUrls(String htmlContent) throws IOException, ExecutionException, InterruptedException {
        Pattern base64Pattern = Pattern.compile("src=\"data:image/\\w+;base64,([^\"]+)\"");
        Matcher matcher = base64Pattern.matcher(htmlContent);

        Map<String, MultipartFile> base64ImageFilesMap = new HashMap<>();
        Set<String> uniqueBase64Images = new HashSet<>();

        while (matcher.find()) {
            String base64Image = matcher.group(1);
            if (uniqueBase64Images.add(base64Image)) { // Ensure unique images only
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                MultipartFile multipartFile = new ByteArrayMultipartFile(imageBytes, "image.png", "image/png");
                base64ImageFilesMap.put(base64Image, multipartFile);
            }
        }

        // Asynchronous batch upload
        ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust thread count based on server resources
        List<CompletableFuture<Map.Entry<String, String>>> uploadFutures = new ArrayList<>();

        for (Map.Entry<String, MultipartFile> entry : base64ImageFilesMap.entrySet()) {
            CompletableFuture<Map.Entry<String, String>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    String imageUrl = uploadImageToServer(entry.getValue());
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }, executor);
            uploadFutures.add(future);
        }

        // Collect results
        Map<String, String> base64ToUrlMap = new HashMap<>();
        for (CompletableFuture<Map.Entry<String, String>> future : uploadFutures) {
            Map.Entry<String, String> result = future.get();
            if (result != null) {
                base64ToUrlMap.put(result.getKey(), result.getValue());
            }
        }

        executor.shutdown();

        // Replace base64 in HTML with URLs
        for (Map.Entry<String, String> entry : base64ToUrlMap.entrySet()) {
            htmlContent = htmlContent.replace("data:image/png;base64," + entry.getKey(), entry.getValue());
        }

        return htmlContent;
    }

    // Upload image to server and return the URL asynchronously
    private String uploadImageToServer(MultipartFile file) throws IOException {
        List<MultipartFile> files = Collections.singletonList(file);
        List<DocumentDto> uploadedDocuments = documentsService.addDocuments(files, "PLT Document", "IPRS_PATENT", null);

        if (uploadedDocuments != null && !uploadedDocuments.isEmpty()) {
            return uploadedDocuments.get(0).getFileReviewUrl();
        } else {
            throw new IOException("Failed to upload image and retrieve URL");
        }
    }



}

