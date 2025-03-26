package gov.saip.applicationservice.common.service.pdf.impl;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.trademark.PublicationApplicationTrademarkDetailDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaAssistantDepartmentDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;
import gov.saip.applicationservice.common.model.SubstantiveExaminationReport;
import gov.saip.applicationservice.common.projection.ApplicationReportInfo;
import gov.saip.applicationservice.common.repository.SubstantiveReportRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.pdf.DepositPdfGenerationService;
import gov.saip.applicationservice.common.service.pdf.PdfGenerationService;
import gov.saip.applicationservice.common.service.pdf.TrademarkPdfGenerationService;
import gov.saip.applicationservice.common.service.veena.LKVeenaAssistantDepartmentService;
import gov.saip.applicationservice.common.service.veena.LKVeenaClassificationService;
import gov.saip.applicationservice.report.service.ApplicationReportsService;
import gov.saip.applicationservice.util.Utilities;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_TRADEMARK;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TrademarkPdfGenerationServiceImpl implements TrademarkPdfGenerationService {

    private final ApplicationReportsService applicationReportsService;
    private final DocumentsService documentsService;
    private final LKVeenaClassificationService lkVeenaClassificationService;
    private final ApplicationInfoService infoService;
    private final DepositPdfGenerationService depositPdfGenerationService;
    private final RevokeVoluntaryRequestService revokeVoluntaryRequestService;
    private final PdfGenerationService pdfGenerationService;
    @Autowired
    @Lazy
    private CertificateRequestService certificateRequestService;
    private final LicenceRequestService licenceRequestService;
    private final RevokeLicenceRequestService revokeLicenceRequestService;
    private final ApplicationCustomerService applicationCustomerService;
    private final SubstantiveReportRepository substantiveReportRepository;
    private final LKVeenaAssistantDepartmentService veenaAssistantDepartmentService;
    private final PublicationIssueService publicationIssueService;
    private final SupportServiceTypeDecisionsService decisionsService;
    private final ApplicationPublicationService applicationPublicationService;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    @Value("${spring.profiles.active}")public String activeProfile;

    @Override
    public List<DocumentDto> generateUploadSavePdfForTrademarkApplication(Long applicationId, Long certificateId, String documentType,
                                                                          String applicationNumber, String certificateNumber, Long supportServiceId) {
        byte[] bytes = getDocumentBytes(documentType, applicationId, certificateId, supportServiceId);

        ByteArrayResource file = new ByteArrayResource(bytes);
        String originFilename = applicationNumber + certificateNumber + ".pdf";
        List<MultipartFile> files = pdfGenerationService.getCustomMultipartFiles(applicationId, file, originFilename, com.google.common.net.MediaType.PDF.toString());

        return documentsService.addDocuments(files, documentType, IPRS_TRADEMARK.name(), applicationId);
    }

    @SneakyThrows
    private byte[] getDocumentBytes(String documentType, Long applicationId, Long certificateId, Long supportServiceId) {
        switch (documentType) {
            case "Revoke Voluntary Trademark Certificate":
                return generateRevokedVoluntaryTradeMarkPdf(applicationId).getBody();
            case "Deposit Certificate":
                return depositPdfGenerationService.generateDepositPdfReport("deposit", applicationId, certificateId).getBody();
            case "License Registration Certificate":
                return generateLicenseRegistrationTradeMarkPdf(applicationId, certificateId, supportServiceId).getBody();
            case "License Cancellation Certificate":
                return generateLicenseCancellationTradeMarkPdf(applicationId, certificateId, supportServiceId).getBody();
            default:
                return generateTradeMarkPdf(certificateId, documentType).getBody();
        }
    }

    @Override
    public ResponseEntity<byte[]> generateTradeMarkPdf(Long certificateId, String documentType) throws Exception {

        Map<String, Object> data = prepareTradeMarkPdfData(certificateId);

        byte[] pdfBytes = null;
        String products = data.get("products") == null ? null : data.get("products").toString();
        if ("Issue Certificate".equals(documentType)) {
            pdfGenerationService.checkProductsOverflow(data, products, 435, 3500);
            pdfBytes = pdfGenerationService.generatePdfArabic("TM", data);
        } else if ("Exact Copy".equals(documentType) || "Certified Register Copy".equals(documentType)) {
            LocalDateTime certificateDate = certificateRequestService.findById(certificateId).getCreatedDate();
            String certificateDateHijri = certificateRequestService.findById(certificateId).getCreatedDate() == null ? null :
                    Utilities.convertDateFromGregorianToHijriWithFormat(certificateDate.toLocalDate(), "yyyy/MM/dd");
            data.put("certificateDateHijri", certificateDateHijri);
            pdfGenerationService.checkProductsOverflow(data, products, 435, 3500);
            pdfBytes = pdfGenerationService.generatePdfArabic("tmExactCopy", data);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<byte[]> generateRevokedVoluntaryTradeMarkPdf(Long appId) throws Exception {
        PublicationApplicationTrademarkDetailDto tm = applicationReportsService.getTradeMarkDetailsReport(appId);
        RevokeVoluntaryRequest revokeVoluntaryRequest = revokeVoluntaryRequestService.findByAppId(appId);
        Utilities utilities = new Utilities();
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(revokeVoluntaryRequest.getCreatedByCustomerCode()).getPayload();
        Map<String, Object> data = new HashMap<>();
        data.put("revokeVoluntaryApplicantName", customerSampleInfoDto.getNameAr() == null || customerSampleInfoDto.getNameAr().isBlank() ? customerSampleInfoDto.getNameEn() : customerSampleInfoDto.getNameAr());
        data.put("revokeVoluntaryApplicationNumber", revokeVoluntaryRequest.getRequestNumber());
        data.put("tradeMarkApplicationNumber", revokeVoluntaryRequest.getApplicationInfo().getApplicationNumber());
        data.put("classificationName", tm.getSubClassifications().get(0).getClassificationNameAr());
        data.put("tradeMarkApplicantName", tm.getApplicantNameAr() == null || tm.getApplicantNameAr().isBlank() ? tm.getApplicantNameEn() : tm.getApplicantNameAr());
        data.put("tmImage", tm.getDocument() == null ? null : tm.getDocument().getFileReviewUrl());
        data.put("createdDate", utilities.convertDateFromGregorianToHijri(LocalDate.from(revokeVoluntaryRequest.getCreatedDate())));
        byte[] pdfBytes = pdfGenerationService.generatePdfArabic("revoke-voluntary", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    private String getApplicantNameFromCustomerCode(String customerCode) {
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(List.of(customerCode));
        List<CustomerSampleInfoDto> customers = customerServiceFeignClient.getCustomerByListOfCode(customerCodeListDto).getPayload();
        if (Objects.nonNull(customers) && !customers.isEmpty())
            return Strings.isNullOrEmpty(customers.get(0).getNameAr()) ? Strings.isNullOrEmpty(customers.get(0).getNameEn()) ? null : customers.get(0).getNameEn() : customers.get(0).getNameAr();
        return null;
    }


    public ResponseEntity<byte[]> generateLicenseRegistrationTradeMarkPdf(Long appId, Long certificateId, Long supportServiceId) throws Exception {
        LicenceRequestDto licenceRequestDto = licenceRequestService.getLicenceRequest(supportServiceId);
        Map<String, Object> data = prepareTradeMarkPdfData(certificateId);

        if (licenceRequestDto != null) {
            CustomerSampleInfoDto customerSampleInfoDto = licenceRequestDto.getCustomer() == null ? null : licenceRequestDto.getCustomer();
            if (customerSampleInfoDto != null) {
                data.put("licenseNameEn", customerSampleInfoDto.getNameEn() == null || customerSampleInfoDto.getNameEn().isBlank() ? customerSampleInfoDto.getNameAr() : customerSampleInfoDto.getNameEn());
                data.put("addressEn", customerSampleInfoDto.getAddress() == null ? "" : customerSampleInfoDto.getAddress().getPlaceOfResidenceEn() == null || customerSampleInfoDto.getAddress().getPlaceOfResidenceEn().isBlank() ? customerSampleInfoDto.getAddress().getPlaceOfResidenceAr() : customerSampleInfoDto.getAddress().getPlaceOfResidenceEn());
                if (data.get("nationality") != null) {
                    data.put("nationality", customerSampleInfoDto.getNationality() == null ? " " : customerSampleInfoDto.getNationality().getIciNationalityEn());
                }

                data.put("licenseNameAr", customerSampleInfoDto.getNameAr() == null || customerSampleInfoDto.getNameAr().isBlank() ? customerSampleInfoDto.getNameEn() : customerSampleInfoDto.getNameAr());
                data.put("licenseAddress", customerSampleInfoDto.getAddress() == null ? "" : customerSampleInfoDto.getAddress().getPlaceOfResidenceAr() == null || customerSampleInfoDto.getAddress().getPlaceOfResidenceAr().isBlank() ?
                        customerSampleInfoDto.getAddress().getPlaceOfResidenceEn() : customerSampleInfoDto.getAddress().getPlaceOfResidenceAr());
                data.put("licenseNationality", customerSampleInfoDto.getNationality() == null ? " " : customerSampleInfoDto.getNationality().getIciCountryNameAr() == null || customerSampleInfoDto.getNationality().getIciCountryNameAr().isBlank() ?
                        customerSampleInfoDto.getNationality().getIciCountryNameEn() : customerSampleInfoDto.getNationality().getIciCountryNameAr());
            }
            data.put("licenseNum", licenceRequestDto.getRequestNumber());
            data.put("licenseStartDate", licenceRequestDto.getFromDate() == null ? "" : Utilities.convertDateFromGregorianToHijriWithFormat(licenceRequestDto.getFromDate(), "yyyy/MM/dd"));
            data.put("licenseEndDate", licenceRequestDto.getToDate() == null ? "" : Utilities.convertDateFromGregorianToHijriWithFormat(licenceRequestDto.getToDate(), "yyyy/MM/dd"));

            data.put("licenseId", licenceRequestDto.getId());
            data.put("tmArea", licenceRequestDto.getRegions() == null ? "" : licenceRequestDto.getRegions().stream().map(BaseLkpEntityDto::getNameAr).collect(Collectors.joining(",")));
        }

        LocalDateTime certificateDate = certificateRequestService.findById(certificateId).getCreatedDate();
        String certificateDateHijri = certificateRequestService.findById(certificateId).getCreatedDate() == null ? null :
                Utilities.convertDateFromGregorianToHijriWithFormat(certificateDate.toLocalDate(), "yyyy/MM/dd");
        data.put("certificateDateHijri", certificateDateHijri);
        byte[] pdfBytes = pdfGenerationService.generatePdfArabic("license-registration", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @SneakyThrows
    private Map<String, Object> prepareTradeMarkPdfData(Long certificateId) {
        ApplicationReportInfo applicationReportInfo = infoService.getApplicationReportData(certificateId);
        Long appId = certificateRequestService.findApplicationInfoIdById(certificateId);
        PublicationApplicationTrademarkDetailDto tm = applicationReportsService.getTradeMarkDetailsReport(appId);
        List<LKVeenaAssistantDepartmentDto> veenaAssistantDepartments = veenaAssistantDepartmentService.getVeenaAssistantDepartmentCodeByApplicationId(appId);
        DocumentDto document = documentsService.findLatestDocumentByApplicationIdAndDocumentType(appId, "Trademark Image/voice");
        Map<String, Object> data = new HashMap<>();
        Utilities utilities = new Utilities();
        // currently QR it will be disabled future the link should be updated
        String publicBaseUrl ="production".equalsIgnoreCase(activeProfile)?"":"";
        byte[] qrCodeImage = utilities.generateQRCodeImage(
                applicationReportInfo == null || applicationReportInfo.getApplicationNumber() == null
                        ? ""
                        : String.format("%s/pb/certificate-request/file-url?applicationNumber=%s", publicBaseUrl, applicationReportInfo.getApplicationNumber())
        );

        String qrCodeImageBase64 = Base64.getEncoder().encodeToString(qrCodeImage);
        data.put("qrCodeImageBase64", "data:image/png;base64," + qrCodeImageBase64);

        data.put("applicationNumber", applicationReportInfo.getApplicationNumber());
        String applicantNameAr = tm.getApplicantNameAr() == null || tm.getApplicantNameAr().isBlank() ? tm.getApplicantNameEn() : tm.getApplicantNameAr();
        data.put("applicantNameAr", applicantNameAr);
        boolean isArabicApplicant = applicantNameAr.matches(".*[\\u0600-\\u06FF].*");
        data.put("isArabicApplicant", isArabicApplicant);

        data.put("classification", applicationReportInfo.getNameAr());
        data.put("filingDate", applicationReportInfo.getFilingDateHijri());
        data.put("regDate", applicationReportInfo == null ? null : applicationReportInfo.getRegistrationDateHijri());
        data.put("grantDateEndHigri", applicationReportInfo.getEndOfProtectionDate() == null ? "لايوجد" : Utilities.convertDateFromGregorianToHijri(applicationReportInfo.getEndOfProtectionDate().toLocalDate()));
        data.put("grantDateStartHigri", applicationReportInfo.getFilingDate() == null ? "لايوجد" : Utilities.convertDateFromGregorianToHijri(applicationReportInfo.getFilingDate().toLocalDate()));
        data.put("tradeMarkDesc", tm.getApplicationTrademarkDetailDto().getMarkDescription());
        data.put("tmImage", document == null ? null : document.getFileReviewUrl());
        data.put("vennaCodes", veenaAssistantDepartments.isEmpty() ? "لايوجد" :
                veenaAssistantDepartments.stream().map(BaseLkpEntityDto::getCode).collect(Collectors.joining("- ")));

        String address = applicationReportInfo.getOwnerAddressAr() == null || applicationReportInfo.getOwnerAddressAr().isBlank() ? applicationReportInfo.getOwnerAddressEn() : applicationReportInfo.getOwnerAddressAr();
        data.put("address", address);
        boolean isArabicAddress = address.matches(".*[\\u0600-\\u06FF].*");  // Regex to check for Arabic characters
        data.put("isArabicAddress", isArabicAddress);

        data.put("priorityNumber", tm.getPriorityApplicationNumber() == null ? "لا يوجد" : tm.getPriorityApplicationNumber());
        data.put("priorityCountry", tm.getPriorityCountry() == null ? "لا يوجد" : tm.getPriorityCountry());
        data.put("priorityDate", tm.getPriorityFilingDate() == null ? "لا يوجد" : tm.getPriorityFilingDate());
        String products = tm.getSubClassifications().stream().map(s -> s.getNameAr()).collect(Collectors.joining(", "));
        data.put("products", products);
        data.put("applicationId", appId);
        data.put("nationality", getCountryNameForMainOwnerByAppId(appId));
        data.put("tmAcceptConditionDesc", getTMConditionDescForGrantCondition(appId));
        data.put("nice", applicationReportInfo.getVersionName());
        return data;
    }

    private String getTMConditionDescForGrantCondition(Long appId) {
        List<SubstantiveExaminationReport> substantiveExaminationReportList = substantiveReportRepository.getAcceptWithConditionReportByApplicationId(appId, null);
        if (substantiveExaminationReportList.isEmpty())
            return "";

        return substantiveExaminationReportList.get(0).getExaminerOpinion();
    }

    private String getCountryNameForMainOwnerByAppId(Long appId) {
        CustomerSampleInfoDto customer = applicationCustomerService.getAppCustomerInfoByAppIdAndType(appId, ApplicationCustomerType.MAIN_OWNER);
        if (customer == null || customer.getNationality() == null) {
            return "";
        }
        return customer.getNationality().getIciCountryNameAr() == null || customer.getNationality().getIciCountryNameAr().isBlank() ? customer.getNationality().getIciCountryNameEn() : customer.getNationality().getIciCountryNameAr();
    }

    public ResponseEntity<byte[]> generateLicenseCancellationTradeMarkPdf(Long appId, Long certificateId, Long supportServiceId) throws Exception {
        RevokeLicenceRequestDto revokeLicenceRequest = revokeLicenceRequestService.findByServiceId(supportServiceId);
        ApplicationReportInfo applicationReportInfo = infoService.getApplicationReportData(certificateId);
        PublicationApplicationTrademarkDetailDto tm = applicationReportsService.getTradeMarkDetailsReport(appId);
        String publicationDate = applicationPublicationService.findApplicationPublicationDateByAppIdAndPublicationType(appId, supportServiceId, "LICENCE_REVOKE");
        Utilities utilities = new Utilities();
        Map<String, Object> data = new HashMap<>();
        if (revokeLicenceRequest != null && revokeLicenceRequest.getLicenceRequest() != null) {

            data.put("cancellationLicenseRequestNumber", revokeLicenceRequest.getRequestNumber());
            data.put("licenseName", revokeLicenceRequest.getLicenceRequest() == null ? null : revokeLicenceRequest.getLicenceRequest().getCustomer().getNameAr() == null || revokeLicenceRequest.getLicenceRequest().getCustomer().getNameAr().isBlank() ?
                    revokeLicenceRequest.getLicenceRequest().getCustomer().getNameEn() : revokeLicenceRequest.getLicenceRequest().getCustomer().getNameAr());
            data.put("createdDate", revokeLicenceRequest.getCreatedDate() == null ? null : utilities.convertDateFromGregorianToHijri(LocalDate.from(revokeLicenceRequest.getCreatedDate())));
            data.put("licenseCancellationDate", utilities.convertDateFromGregorianToHijri(LocalDate.from(LocalDateTime.now())));
        }
        data.put("applicantName", tm.getApplicantNameAr() == null || tm.getApplicantNameAr().isBlank() ? tm.getApplicantNameEn() : tm.getApplicantNameAr());
        data.put("applicationNumber", applicationReportInfo.getApplicationNumber());
        data.put("tmOwnerName", tm.getApplicantNameAr() == null || tm.getApplicantNameAr().isBlank() ? tm.getApplicantNameEn() : tm.getApplicantNameAr());
        data.put("publicationDate", publicationDate == null ? null : publicationDate);
        if (tm.getSubClassifications() != null && !tm.getSubClassifications().isEmpty() && tm.getSubClassifications().get(0) != null)
            data.put("classificationName", tm.getSubClassifications().get(0).getClassificationNameAr());
        data.put("tmImage", tm.getDocument() == null ? null : tm.getDocument().getFileReviewUrl());

        byte[] pdfBytes = pdfGenerationService.generatePdfArabic("license-cancellation", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


}
