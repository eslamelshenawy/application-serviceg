package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationInfoResultDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.certs.CertRequestDetailsDto;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestProjection;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestSearchFilterDto;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestVerificationDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.certificate.CertificateStatusEnum;
import gov.saip.applicationservice.common.mapper.CertificateRequestMapper;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.CertificateRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.LkCertificateTypeService;
import gov.saip.applicationservice.common.service.lookup.LkCertificateStatusService;
import gov.saip.applicationservice.common.service.pdf.PdfGenerationService;
import gov.saip.applicationservice.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_MAIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CertificateRequestServiceImplTest {

    @InjectMocks
    @Spy
    private CertificateRequestServiceImpl certificateRequestService;

    @Mock
    ApplicationInfoService applicationInfoService;

    @Mock
    LkCertificateTypeService certificateTypeService;

    @Mock
    LkCertificateStatusService certificateStatusService;

    @Mock
    private DocumentMapper documentMapper;
    @Mock
    private CertificateRequestMapper certificateRequestMapper;
    @Mock
    private CustomerServiceCaller customerServiceCaller;
    @Mock
    private PdfGenerationService pdfGenerationService;

    @Mock
    CertificateRequestRepository certificateRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testInsert() {
        LkCertificateType lkCertificateType = new LkCertificateType();
        lkCertificateType.setCode("CERT_TYPE_CODE");
        CertificateRequest certificateRequest = new CertificateRequest();
        certificateRequest.setApplicationInfo(new ApplicationInfo());
        certificateRequest.setCertificateType(lkCertificateType);
        certificateRequest.setRequestNumber("SA0002");
        when(applicationInfoService.findById(any())).thenReturn(new ApplicationInfo());
        when(certificateRequestRepository.findByApplicationInfoAndCertificateTypeAndCertificateStatus(any(), any(), any()))
                .thenReturn(null);
        when(certificateRequestRepository.getMaxId()).thenReturn(1L);
        doReturn(certificateRequest).when(certificateRequestService).insert(certificateRequest);

        CertificateRequest result = certificateRequestService.insert(certificateRequest);

        assertNotNull(result);
        assertEquals("SA0002", result.getRequestNumber());
    }


    @Test
    public void testGetMaxId() {
        Long maxId = 1L;
        when(certificateRequestRepository.getMaxId()).thenReturn(maxId);

        Long result = certificateRequestService.getMaxId();

        assertEquals(maxId, result);
        verify(certificateRequestRepository, times(1)).getMaxId();
    }

    @Test
    public void testGenerateAppNumberWithNonNullMaxId() {
        Long maxId = 10L;
        String expectedAppNumber = "SA0011";
        String result = certificateRequestService.generateAppNumber(maxId);
        assertEquals(expectedAppNumber, result);
    }

    @Test
    public void testGenerateAppNumberWithNullMaxId() {
        Long maxId = null;
        String expectedAppNumber = "SA0001";
        String result = certificateRequestService.generateAppNumber(maxId);
        assertEquals(expectedAppNumber, result);
    }

    @Test
    public void testListCertificateRequests() {
        Long id = 1L;
        String statusCode = "STATUS";
        CertificateRequestSearchFilterDto filterDto = new CertificateRequestSearchFilterDto();
        Long userId = 2L;
        int page = 1;
        int limit = 10;

        Page<CertificateRequestProjection> projection = mock(Page.class);
        when(customerServiceCaller.getCustomersCodes(filterDto.getApplicantName())).thenReturn(new ArrayList<>());
//        when(certificateRequestRepository.listCertificateRequests(id,
//                filterDto.getSearchField(),
//                new ArrayList<>(),
//                statusCode,
//                ApplicationRelevantEnum.Applicant_MAIN,
//                userId.toString(),
//                filterDto.getRequestType(),
//                filterDto.getServiceType(),
//                filterDto.getTrademarkRegistrationNumber(),
//                filterDto.getSubmissionDate(),
//                filterDto.getApplicationNumber(),
//                PageRequest.of(page, limit)))
//                .thenReturn(projection);

        PaginationDto result = certificateRequestService.listCertificateRequests(id, statusCode, filterDto, page, limit);

        assertNotNull(result);
        verify(customerServiceCaller, times(1)).getCustomersCodes(filterDto.getApplicantName());
    }

    @Test
    public void testListPreviousCertificateRequests() {
        Long id = 1L;
        CertificateRequestSearchFilterDto filterDto = new CertificateRequestSearchFilterDto();
        String statusCode = "STATUS";
        int page = 1;
        int limit = 10;

        Page<CertificateRequestProjection> projection = mock(Page.class);
        when(customerServiceCaller.getCustomersCodes(filterDto.getApplicantName())).thenReturn(new ArrayList<>());

        when(certificateRequestRepository.listPreviousCertificateRequests(id, filterDto.getApplicationNumber(),
                filterDto.getApplicationTitle(), filterDto.getCertificateTypeName(), filterDto.getCertificateStatusName(),
                filterDto.getDepositDate(), filterDto.getCustomerCode(), new ArrayList<>(), statusCode, Applicant_MAIN, id,
                PageRequest.of(page, limit))).thenReturn(projection);

        PaginationDto result = certificateRequestService.listPreviousCertificateRequests(id, filterDto, statusCode, page, limit);

        assertNotNull(result);
        verify(customerServiceCaller, times(1)).getCustomersCodes(filterDto.getApplicantName());
    }

    @Test
    public void testGetCertDetailsDto() {
        Long reqId = 1L;
        CertificateRequest certificateRequest = new CertificateRequest();
        DocumentDto documentDto = new DocumentDto();
        certificateRequest.setDocument(new Document());
        certificateRequest.setCertificateType(new LkCertificateType());
        when(certificateRequestRepository.findById(reqId)).thenReturn(Optional.of(certificateRequest));
        when(documentMapper.mapToDto(certificateRequest.getDocument())).thenReturn(documentDto);
        doReturn(certificateRequest).when(certificateRequestService).findById(anyLong());

        CertRequestDetailsDto result = certificateRequestService.getCertDetailsDto(reqId);

        assertNotNull(result);
        verify(documentMapper, times(1)).mapToDto(certificateRequest.getDocument());
    }

    @Test
    public void testChangeCertificateRequestStatus() {
        Long certificateRequestId = 1L;
        String certificateStatusCode = "STATUS";
        LkCertificateStatus certificateStatus = new LkCertificateStatus();
        Optional<CertificateRequest> certificateRequestOptional = Optional.of(new CertificateRequest());
        when(certificateStatusService.findByCode(certificateStatusCode)).thenReturn(certificateStatus);
        when(certificateRequestRepository.findById(certificateRequestId)).thenReturn(certificateRequestOptional);
        doReturn(certificateRequestOptional.get()).when(certificateRequestService).update(certificateRequestOptional.get());

        certificateRequestService.changeCertificateRequestStatus(certificateRequestId, certificateStatusCode);

        verify(certificateStatusService, times(1)).findByCode(certificateStatusCode);
        verify(certificateRequestRepository, times(1)).findById(certificateRequestId);
    }

    @Test
    public void testChangeCertificateRequestStatusWithNotFound() {
        Long certificateRequestId = 1L;
        String certificateStatusCode = "STATUS";
        when(certificateStatusService.findByCode(certificateStatusCode)).thenReturn(null);
        when(certificateRequestRepository.findById(certificateRequestId)).thenReturn(Optional.empty());

        certificateRequestService.changeCertificateRequestStatus(certificateRequestId, certificateStatusCode);


        verify(certificateStatusService, times(1)).findByCode(certificateStatusCode);
        verify(certificateRequestRepository, never()).save(any());
    }

    @Test
    public void testCheckCertificateRequest() {
        String applicationNumber = "APP_NUM";
        String requestNumber = "REQ_NUM";
        Long id = 1L;
        String certificateTypeCode = "CERT_TYPE_CODE";
        List<CertificateRequestVerificationDto> certificates = new ArrayList<>();
        certificates.add(new CertificateRequestVerificationDto());
        when(certificateRequestRepository.checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode, CertificateStatusEnum.COMPLETED.name(), null)).thenReturn(certificates);

        CertificateRequestVerificationDto result = certificateRequestService.checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode);

        assertNotNull(result);
        verify(certificateRequestRepository, times(1)).checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode, CertificateStatusEnum.COMPLETED.name(), null);
    }

    @Test
    public void testCheckCertificateRequestWithEmptyResult() {
        String applicationNumber = "APP_NUM";
        String requestNumber = "REQ_NUM";
        String certificateTypeCode = "CERT_TYPE_CODE";
        Long id = 1L;
        when(certificateRequestRepository.checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode, CertificateStatusEnum.COMPLETED.name(), null)).thenReturn(new ArrayList<>());

        CertificateRequestVerificationDto result = certificateRequestService.checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode);

        assertNotNull(result);
        verify(certificateRequestRepository, times(1)).checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode, CertificateStatusEnum.COMPLETED.name(), null);
    }

    @Test
    public void testGetAppInfoByCertificateRequestId() {
        Long id = 1L;
        ApplicationInfoResultDto appInfoResultDto = new ApplicationInfoResultDto();
        when(certificateRequestRepository.getApplicationInfoByCertificateRequestId(id)).thenReturn(appInfoResultDto);

        ApplicationInfoResultDto result = certificateRequestService.getAppInfoByCertificateRequestId(id);

        assertNotNull(result);
        assertEquals(appInfoResultDto, result);
        verify(certificateRequestRepository, times(1)).getApplicationInfoByCertificateRequestId(id);
    }

    @Test
    public void testLinkDocumentToCertificateRequest() {
        Long id = 1L;
        DocumentDto documentDto = new DocumentDto();
        CertificateRequest certificateRequest = new CertificateRequest();
        when(certificateRequestRepository.findById(id)).thenReturn(Optional.of(certificateRequest));
        doReturn(certificateRequest).when(certificateRequestService).update(certificateRequest);
        certificateRequestService.linkDocumentToCertificateRequest(id, documentDto);

        verify(certificateRequestRepository, times(1)).findById(id);
    }

    @Test
    public void testLinkDocumentToCertificateRequestWithNotFound() {
        Long id = 1L;
        DocumentDto documentDto = new DocumentDto();
        when(certificateRequestRepository.findById(id)).thenReturn(Optional.empty());

        certificateRequestService.linkDocumentToCertificateRequest(id, documentDto);

        verify(certificateRequestRepository, times(1)).findById(id);
        verify(certificateRequestRepository, never()).save(any());
    }

    @Test
    public void testProcessCertificateRequest() {
        Long certificateId = 1L;
        String documentType = "DOCUMENT_TYPE";
        ApplicationInfoResultDto appInfoResultDto = new ApplicationInfoResultDto();
        appInfoResultDto.setSaipCode(ApplicationCategoryEnum.PATENT.name());
        when(certificateRequestRepository.getApplicationInfoByCertificateRequestId(any())).thenReturn(appInfoResultDto);
        certificateRequestService.processCertificateRequest(certificateId, documentType);

        verify(pdfGenerationService, times(1)).generateUploadSavePdfForPatentApplication(any(), eq(certificateId), eq(documentType), any(), any());
        verify(certificateRequestService, times(1)).changeCertificateRequestStatus(eq(certificateId), eq(CertificateStatusEnum.COMPLETED.name()));
    }

    @Test
    public void testPrepareAndSaveCertificateRequest() {
        Long applicationId = 1L;
        String createdByUser = "USER";
        LkCertificateType lkCertificateType = new LkCertificateType();
        lkCertificateType.setId(1);
        ApplicationInfoResultDto applicationInfoResultDto = new ApplicationInfoResultDto();
        applicationInfoResultDto.setSaipCode("PATENT");
        CertificateRequest certificateRequest = new CertificateRequest();
        when(applicationInfoService.getCreatedUserById(applicationId)).thenReturn(createdByUser);
        when(certificateRequestRepository.save(any())).thenReturn(certificateRequest);
        when(certificateTypeService.findByCode(any(String.class))).thenReturn(lkCertificateType);
        when(certificateRequestRepository.findByApplicationInfoAndCertificateTypeAndCertificateStatus(any(),any(),any())).thenReturn(certificateRequest);


        CertificateRequest result = certificateRequestService.prepareAndSaveCertificateRequest(applicationId, "ISSUE_CERTIFICATE");

        assertNotNull(result);
        verify(applicationInfoService, times(1)).getCreatedUserById(applicationId);
        verify(certificateRequestRepository, times(1)).findByApplicationInfoAndCertificateTypeAndCertificateStatus(any(),any(),any());
    }

    @Test
    public void testGenerateCertificatePdf() {
        Long applicationId = 1L;
        String certificateType = "CERT_TYPE";
        String documentType = "Issue Certificate";
        LkCertificateType lkCertificateType = new LkCertificateType();
        lkCertificateType.setId(1);
        CertificateRequest certificateRequest = new CertificateRequest();
        certificateRequest.setId(1L);
        when(certificateTypeService.findByCode(any(String.class))).thenReturn(lkCertificateType);
        when(applicationInfoService.getCreatedUserById(any())).thenReturn("test");
        when(certificateRequestRepository.findByApplicationInfoAndCertificateTypeAndCertificateStatus(any(),any(),any())).thenReturn(certificateRequest);
        certificateRequestService.generateCertificatePdf(documentType, certificateRequest);

        verify(certificateRequestService, times(1)).processCertificateRequest(any(), any());
    }

    @Test
    public void testReCreateCertificate() {
        Long id = 1L;
        CertificateRequest certificateRequest = new CertificateRequest();
        certificateRequest.setId(id);
        ApplicationInfoResultDto applicationInfoResultDto = new ApplicationInfoResultDto();
        applicationInfoResultDto.setSaipCode("PATENT");
        List<DocumentDto> documentList = new ArrayList<>();
        documentList.add(new DocumentDto());
        when(certificateRequestRepository.findById(id)).thenReturn(Optional.of(certificateRequest));
        when(certificateRequestRepository.getApplicationInfoByCertificateRequestId(id)).thenReturn(applicationInfoResultDto);
        when(pdfGenerationService.generateUploadSavePdfForPatentApplication(any(),any(),any(),any(),any())).thenReturn(documentList);
        doReturn(certificateRequest).when(certificateRequestService).update(certificateRequest);

        Long result = certificateRequestService.reCreateCertificate(id);

        assertNotNull(result);
        assertEquals(id, result);
        verify(certificateRequestRepository, times(3)).findById(id);
        verify(certificateRequestService, times(1)).processCertificateRequest(eq(id), eq("Issue Certificate"));
    }

    @Test
    public void testReCreateCertificateWithNotFound() {
        Long id = 1L;
        when(certificateRequestRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            certificateRequestService.reCreateCertificate(id);
        });

        verify(certificateRequestRepository, times(1)).findById(id);
        verify(certificateRequestService, never()).processCertificateRequest(any(), any());
    }

}

