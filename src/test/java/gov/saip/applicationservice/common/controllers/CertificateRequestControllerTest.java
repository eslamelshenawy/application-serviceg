package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.certs.CertRequestDetailsDto;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestSearchFilterDto;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestVerificationDto;
import gov.saip.applicationservice.common.dto.veena.CertificateRequestDto;
import gov.saip.applicationservice.common.facade.impl.CertificateRequestFacadeImpl;
import gov.saip.applicationservice.common.model.CertificateRequest;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.filters.JwtUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CertificateRequestControllerTest {

    @Mock
    private CertificateRequestService certificateRequestService;

    @InjectMocks
    private CertificateRequestController certificateRequestController;

    @Mock
    private JwtUtility jwtUtility;
    
    @Mock
    private MockMvc mockMvc;
    
    @Mock
    protected HttpServletRequest request;
    
    @Mock
    CertificateRequestFacadeImpl certificateRequestFacade;

    @Mock
    private BaseMapper<CertificateRequest, CertificateRequestDto> baseMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(certificateRequestController).build();
    }

    @Test
    public void testListCertificateRequests() {
        Long id = 1L;
        String certificateStatus = "PENDING";
        CertificateRequestSearchFilterDto certificateRequestSearchFilterDto = new CertificateRequestSearchFilterDto();
        int page = 1;
        int limit = 10;
        HttpServletRequest request = mock(HttpServletRequest.class);
        String authorization = "Bearer token";
        String token = "token";
        Long userId = 1L;

        when(request.getHeader("Authorization")).thenReturn(authorization);
        when(jwtUtility.getUserIdFromToken(token)).thenReturn(userId);

        PaginationDto<List<CertificateRequestDto>> certificateRequests = new PaginationDto<>();
        when(certificateRequestService.listCertificateRequests(id, certificateStatus, certificateRequestSearchFilterDto, page, limit)).thenReturn(certificateRequests);

        ApiResponse<PaginationDto> response = certificateRequestController.listCertificateRequests(id, certificateStatus, certificateRequestSearchFilterDto, page, limit);

        assertEquals(ApiResponse.ok(certificateRequests), response);
    }

    @Test
    public void testListPreviousCertificateRequests() {
        Long id = 1L;
        String certificateStatus = "APPROVED";
        CertificateRequestSearchFilterDto certificateRequestSearchFilterDto = new CertificateRequestSearchFilterDto();
        int page = 1;
        int limit = 10;

        PaginationDto<List<CertificateRequestDto>> previousCertificateRequests = new PaginationDto<>();
        when(certificateRequestService.listPreviousCertificateRequests(id, certificateRequestSearchFilterDto, certificateStatus, page, limit)).thenReturn(previousCertificateRequests);

        ApiResponse<PaginationDto> response = certificateRequestController.listPreviousCertificateRequests(id, certificateStatus, certificateRequestSearchFilterDto, page, limit);

        assertEquals(ApiResponse.ok(previousCertificateRequests), response);
    }

    @Test
    public void testLastActionOnRequest() {
        String certRequestId = "12345";
        CertRequestDetailsDto certRequestDetailsDto = CertRequestDetailsDto.builder()
                .lastActionAr("value1")
                .lastActionAr("value2")
                .build();
        when(certificateRequestService.getCertDetailsDto(Long.valueOf(certRequestId))).thenReturn(certRequestDetailsDto);

        ApiResponse<CertRequestDetailsDto> response = certificateRequestController.lastActionOnRequest(certRequestId);

        assertEquals(ApiResponse.ok(certRequestDetailsDto), response);
    }

    @Test
    public void testCheckCertificateRequest() {
        String applicationNumber = "APP12345";
        String requestNumber = "REQ67890";
        String certificateTypeCode = "CERT001";
        CertificateRequestVerificationDto certificateRequestVerificationDto = new CertificateRequestVerificationDto();
        when(certificateRequestService.checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode)).thenReturn(certificateRequestVerificationDto);

        ApiResponse<CertificateRequestVerificationDto> response = certificateRequestController.checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode);

        assertEquals(ApiResponse.ok(certificateRequestVerificationDto), response);
    }

    @Test
    public void testReCreateCertificate() {
        Long requestId = 1L;
        when(certificateRequestService.reCreateCertificate(requestId)).thenReturn(requestId);

        ApiResponse<Long> response = certificateRequestController.reCreateCertificate(requestId);

        assertEquals(ApiResponse.ok(requestId), response);
    }
    
    @Test
    public void testGetCertificatesApplications() throws Exception {
        String type = "CERTIFICATE_REQUEST";
        Long categoryId = 1L;
        String applicationNumber = "123";
        ApplicationInfoBaseDto applicationInfoBaseDto = new ApplicationInfoBaseDto();
        when(certificateRequestFacade.getApplicationsAccordingCertificatesPreConditions(type, categoryId, applicationNumber)).thenReturn(applicationInfoBaseDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/kc/certificate-request/{type}/category/{category-id}", type, categoryId)
                        .param("applicationNumber", applicationNumber))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

