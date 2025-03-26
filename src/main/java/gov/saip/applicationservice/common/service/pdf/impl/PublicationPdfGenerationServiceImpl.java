package gov.saip.applicationservice.common.service.pdf.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.layout.font.FontProvider;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.PublicationIssueService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.common.service.pdf.PublicationPdfGenerationService;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationPdfGenerationServiceImpl implements PublicationPdfGenerationService {
    private final SpringTemplateEngine templateEngine;
    private FontProvider fontProvider;
    private List<FontProgram> fontPrograms;
    private final PublicationIssueService publicationIssueService;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationInfoService applicationInfoService;
    private  final ApplicationAgentService applicationAgentService;
    @Value("${newsLetterFileName}")
    private String fileName;

    private static final List<String> PUBLICATION_STATUSES = Arrays.asList("COMPLETED", "OBJECTOR");
    private static final List<String> REJECTION_STATUSES = Arrays.asList("FORMAL_REJECTION", "OBJECTIVE_REJECTION");


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

    @Override
    @Transactional
    public ResponseEntity<byte[]> generatePublicationPdfReport(String templateName, Long publicationIssueId) throws Exception {
        List<GrantedPublishedPatentApplicationDto> grantedPublishedPatentApplicationDtos = getGrantedPublishedPatentApplicationDtos(publicationIssueId);
        LocalDateTime startDate = grantedPublishedPatentApplicationDtos.get(0).getStartDate();
        LocalDateTime endDate = grantedPublishedPatentApplicationDtos.get(0).getEndDate();

        List<PublishedPatentApplicationDto> publicationPdfProjections = getPublishedPatentApplicationDtos(grantedPublishedPatentApplicationDtos.get(0).getStartDate(), grantedPublishedPatentApplicationDtos.get(0).getEndDate());
        String startDateHijri = Utilities.convertDateFromGregorianToHijri(startDate.toLocalDate());
        String endDateHijri = Utilities.convertDateFromGregorianToHijri(endDate.toLocalDate());

        List<ChangeOwnerShipReportDTO> changeOwnerShipReportDTOS = getChangeOwnerShipReportDTOS(startDate, endDate);

        List<PublishedPatentApplicationDto> publishedApplications = filterPublicationsByStatus(publicationPdfProjections, PUBLICATION_STATUSES);
        List<PublishedPatentApplicationDto> rejectedApplications = filterPublicationsByStatus(publicationPdfProjections, REJECTION_STATUSES);
        List<ChangeAgentReportDto> changeAgentReportDtos = getChangeAgentReportDtos();
        generateRowNumber(grantedPublishedPatentApplicationDtos, publishedApplications, rejectedApplications, changeOwnerShipReportDTOS, changeAgentReportDtos);
        PublicationPdfRequest pdfRequest = preparePublicationPdfRequest(publicationIssueId, publishedApplications, grantedPublishedPatentApplicationDtos, rejectedApplications, startDateHijri, endDateHijri, changeOwnerShipReportDTOS, changeAgentReportDtos);

        return getResponseEntity(templateName, publicationIssueId, pdfRequest);
    }

    private List<ChangeAgentReportDto> getChangeAgentReportDtos() {
        List<ChangeAgentReportDto> changeAgentReportDtos = applicationAgentService.findLatestChangedAgents();

        for (ChangeAgentReportDto changeAgentReportDto : changeAgentReportDtos) {
            Long activeApplicationAgentId = changeAgentReportDto.getId();

            ApplicationAgent activeApplicationAgent = applicationAgentService.findActiveAgentsByApplicationId(activeApplicationAgentId);
            CustomerSampleInfoDto newAgentInfo = getAnyCustomerDetails(activeApplicationAgent.getCustomerId());

            changeAgentReportDto.setNewAgentName(newAgentInfo.getNameAr());
            changeAgentReportDto.setModifiedDateHigri(Utilities.convertDateFromGregorianToHijri(activeApplicationAgent.getModifiedDate().toLocalDate()));

            CustomerSampleInfoDto oldAgentInfo = getAnyCustomerDetails(changeAgentReportDto.getCustomerId());
            changeAgentReportDto.setOldAgentName(oldAgentInfo.getNameAr());
        }

        return changeAgentReportDtos;
    }


    private List<ChangeOwnerShipReportDTO> getChangeOwnerShipReportDTOS(LocalDateTime startDate, LocalDateTime endDate) {
        return applicationSupportServicesTypeService.getOwnerShipChangedData(startDate, endDate)
                .stream()
                .peek(this::updateChangeOwnerShipReportDTO)
                .collect(Collectors.toList());
    }

    private void updateChangeOwnerShipReportDTO(ChangeOwnerShipReportDTO dto) {
        CustomerSampleInfoDto customerSampleInfoDto = getAnyCustomerDetails(dto.getCustomerId());
        dto.setNewCustomerNameAr(customerSampleInfoDto.getNameAr());
        ApplicationReportDetailsDto applicationReportDetailsDto = getApplicationReportInfo(dto.getId());
        dto.setOldCustomerNameAr(applicationReportDetailsDto.getMainApplicant().getNameAr());
        dto.setModifiedDateHigri(Utilities.convertDateFromGregorianToHijri(dto.getModifiedDate().toLocalDate()));
    }

    private CustomerSampleInfoDto getAnyCustomerDetails(Long customerId){
        return  customerServiceCaller.getAnyCustomerDetails(customerId);
    }

    private ApplicationReportDetailsDto getApplicationReportInfo(Long appId){
        return  applicationInfoService.getApplicationReportInfo(appId);
    }

    private List<GrantedPublishedPatentApplicationDto> getGrantedPublishedPatentApplicationDtos(Long publicationIssueId) {
        return publicationIssueService.findPublicationsByCategory(publicationIssueId);
    }

    private List<PublishedPatentApplicationDto> getPublishedPatentApplicationDtos(LocalDateTime startDate, LocalDateTime endDate) {
        return publicationIssueService.findPublicationsByCategoryAndStatus(startDate,endDate);
    }

    private ResponseEntity<byte[]> getResponseEntity(String templateName, Long publicationIssueId, PublicationPdfRequest pdfRequest) throws IOException {
        String html = generateHtmlFromTemplate(templateName, pdfRequest);
        ConverterProperties properties = initializePdfConverterProperties();
        byte[] pdfBytes = generatePdfFromHtml(html, properties);
        HttpHeaders headers = createPdfHeaders(publicationIssueId);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    private List<PublishedPatentApplicationDto> filterPublicationsByStatus(List<PublishedPatentApplicationDto> publications,
                                                                           List<String> statusCodes) {
        return publications.stream()
                .filter(publication -> statusCodes.contains(publication.getCode()))
                .collect(Collectors.toList());
    }


    private void generateRowNumber(List<GrantedPublishedPatentApplicationDto> grantedPublishedPatentApplicationDtos,
                                   List<PublishedPatentApplicationDto> publications, List<PublishedPatentApplicationDto> rejectedApplications, List<ChangeOwnerShipReportDTO> changeOwnerShipReportDTOS, List<ChangeAgentReportDto> changeAgentReportDtos) {
        IntStream.range(0, publications.size()).forEach(i -> publications.get(i).setRowNumber(i + 1));
        IntStream.range(0, rejectedApplications.size()).forEach(i -> rejectedApplications.get(i).setRowNumber(i + 1));
        IntStream.range(0, changeOwnerShipReportDTOS.size()).forEach(i -> changeOwnerShipReportDTOS.get(i).setRowNumber(i + 1));
        IntStream.range(0, changeAgentReportDtos.size()).forEach(i -> changeAgentReportDtos.get(i).setRowNumber(i + 1));
        IntStream.range(0, grantedPublishedPatentApplicationDtos.size()).forEach(i ->
                grantedPublishedPatentApplicationDtos.get(i).setRowNumber(i + 1));
    }

    private PublicationPdfRequest preparePublicationPdfRequest(Long publicationIssueId,
                                                               List<PublishedPatentApplicationDto> publications,
                                                               List<GrantedPublishedPatentApplicationDto> grantedApplications,
                                                               List<PublishedPatentApplicationDto> rejectedApplications , String startDate , String endDate, List<ChangeOwnerShipReportDTO> changeOwnerShipReportDTOS, List<ChangeAgentReportDto> changeAgentReportDtos) {
        PublicationPdfRequest pdfRequest = new PublicationPdfRequest();
        pdfRequest.setPublicationIssueId(publicationIssueId);
        pdfRequest.setPublicationPdfDTOS(publications);
        pdfRequest.setGrantedPublishedPatentApplicationDtos(grantedApplications);
        pdfRequest.setRejectedPublishedPatentApplicationDtos(rejectedApplications);
        pdfRequest.setStartDate(startDate);
        pdfRequest.setEndDate(endDate);
        pdfRequest.setChangeOwnerShipReportDTOS(changeOwnerShipReportDTOS);
        pdfRequest.setChangeAgentReportDtos(changeAgentReportDtos);
        return pdfRequest;
    }

    private String generateHtmlFromTemplate(String templateName, PublicationPdfRequest pdfRequest) {
        Context context = new Context();
        context.setVariable("pdfRequest", pdfRequest);
        return templateEngine.process(templateName, context);
    }

    private ConverterProperties initializePdfConverterProperties() {
        ConverterProperties properties = new ConverterProperties();
        initializeFonts(properties);
        return properties;
    }


    private HttpHeaders createPdfHeaders(Long aiId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName + aiId + ".pdf");
        return headers;
    }

}
