package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.certs.*;
import gov.saip.applicationservice.common.dto.veena.CertificateRequestDto;
import gov.saip.applicationservice.common.facade.CertificateRequestFacade;
import gov.saip.applicationservice.common.mapper.CertificateRequestMapper;
import gov.saip.applicationservice.common.model.CertificateRequest;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.filters.JwtUtility;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/kc/certificate-request", "/internal-calling/certificate-request"})
@RequiredArgsConstructor
@Slf4j
public class CertificateRequestController extends BaseController<CertificateRequest, CertificateRequestDto, Long> {

    private final CertificateRequestService certificateRequestService;
    private final CertificateRequestMapper certificateRequestMapper;
    private final CertificateRequestFacade certificateRequestFacade;

    @Override
    protected BaseService<CertificateRequest, Long> getService() {
        return certificateRequestService;
    }

    @Override
    protected BaseMapper<CertificateRequest, CertificateRequestDto> getMapper() {
        return certificateRequestMapper;
    }

    @GetMapping("/requests")
    ApiResponse<PaginationDto> listCertificateRequests(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "certificateStatus", required = false) String certificateStatus,
            @RequestBody CertificateRequestSearchFilterDto certificateRequestSearchFilterDto,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit) {
        return ApiResponse.ok(certificateRequestService.listCertificateRequests(id, certificateStatus, certificateRequestSearchFilterDto, page, limit));
    }

    @GetMapping("/previous-certificate-requests")
    ApiResponse<PaginationDto> listPreviousCertificateRequests(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "certificateStatus", required = false) String certificateStatus,
            @ModelAttribute CertificateRequestSearchFilterDto certificateRequestSearchFilterDto,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit) {
        return ApiResponse.ok(certificateRequestService.listPreviousCertificateRequests(id, certificateRequestSearchFilterDto, certificateStatus, page, limit));
    }

    @GetMapping("/lastAction/{certRequestId}")
    ApiResponse<CertRequestDetailsDto> lastActionOnRequest(@PathVariable("certRequestId") String certRequestId) {

        return ApiResponse.ok(certificateRequestService.getCertDetailsDto(Long.valueOf(certRequestId)));
    }

    @GetMapping("/check")
    ApiResponse<CertificateRequestVerificationDto> checkCertificateRequest(
            @RequestParam(value = "applicationNumber", required = false) String applicationNumber,
            @RequestParam(value = "requestNumber", required = false) String requestNumber,
            @RequestParam(value = "certificateTypeCode", required = false) String certificateTypeCode) {
        CertificateRequestVerificationDto  certificateRequestVerificationDto =
                certificateRequestService.checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode);
        certificateRequestVerificationDto.setBillNumber(
                certificateRequestService.getBillNumberByAppId(certificateRequestVerificationDto.getApplicationId(),certificateTypeCode)
        );
        return ApiResponse.ok(certificateRequestVerificationDto);
    }

    @PutMapping(value = "/{id}/re-create")
    public ApiResponse<Long> reCreateCertificate(@PathVariable(name = "id") Long requestId) {
        return ApiResponse.ok(certificateRequestService.reCreateCertificate(requestId));
    }

    @PostMapping(value = "/application/{id}/generate-certificate-pdf")
    public ApiResponse<String> generateCertificatePdf(@PathVariable(name = "id") Long applicationId,
                                                      @RequestParam(value = "certificateType") String certificateType,
                                                      @RequestParam(value = "documentType") String documentType) {
        CertificateRequest certificateRequest = certificateRequestService.createCertificationIfNotExist(applicationId, certificateType);
        certificateRequestService.generateCertificatePdf(documentType, certificateRequest);
        return ApiResponse.ok(certificateRequest.getRequestNumber());
    }

    @PostMapping(value = "/application/{id}/generate-support-service-certificate-pdf")
    public ApiResponse<String> generateCertificatePdfForSupportServices(@PathVariable(name = "id") Long applicationId,
                                                                        @RequestParam(value = "certificateType") String certificateType,
                                                                        @RequestParam(value = "documentType") String documentType,
                                                                        @RequestParam(value = "supportServiceId") Long supportServiceId) {
        certificateRequestService.generateSupportServiceCertificatePdf(applicationId, certificateType, documentType, supportServiceId);
        return ApiResponse.ok("SUCCESS");
    }

    @PostMapping(value = "/application/certificate/{id}/generate-free-certificate-pdf")
    public ApiResponse<String> generateFreeCertificatePdf(@PathVariable(name = "id") Long certificateId,
                                                          @RequestParam(value = "certificateType") String certificateType) {
        certificateRequestService.generateFreeCertificatePdf(certificateId, certificateType);
        return ApiResponse.ok("SUCCESS");
    }

    @GetMapping("/{type}/category/{category-id}")
    ApiResponse<ApplicationInfoBaseDto> getCertificatesApplications(
            @PathVariable(name = "type") String type,
            @PathVariable(name = "category-id") Long categoryId,
            @RequestParam(value = "applicationNumber", required = false) String searchField) {

        return ApiResponse.ok(certificateRequestFacade.getApplicationsAccordingCertificatesPreConditions(type, categoryId, searchField));
    }


    @GetMapping("/request-number/{appId}")
    public ApiResponse<String> findGrantCertificateNumberByAppId(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(certificateRequestService.findGrantCertificateNumberByAppId(appId));
    }

    @PostMapping("/application/{appId}")
    public ApiResponse<String> prepareAndCreateCertificateRequest(@PathVariable(name = "appId") Long appId,
                                                                  @RequestParam(value = "certificateType") String certificateType) {
        certificateRequestService.prepareAndCreateCertificateRequest(appId, certificateType);
        return ApiResponse.ok("SUCCESS");
    }

    @PostMapping("/patent/{certReqId}")
    public ApiResponse<String> prepareAndCreateCertificateRequest(@PathVariable(name = "certReqId") Long certReqId) {
        return ApiResponse.ok(certificateRequestService.callPatCertJasperReports(certReqId, UUID.randomUUID().toString()));
    }
    @GetMapping("/certificate-application/{appId}")
    ApiResponse<List<CertificateRequestProjectionDto>> listCertificateRequestsByApplicationId(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(certificateRequestService.listCertificateByApplicationId(appId));
    }

    @GetMapping("/regenerate-deposit/{certificateRequestId}")
    public ApiResponse<?> processCertificateRequest(@PathVariable(name = "certificateRequestId")Long certificateRequestId){
        certificateRequestService.processCertificateRequest(certificateRequestId, "Deposit Certificate");
        return ApiResponse.ok("done");
    }
    @PostMapping("/industerial/{certReqId}")
    public ApiResponse<String> prepareAndCreateCertificateIndusterialRequest(@PathVariable(name = "certReqId") Long certReqId) {
        return ApiResponse.ok(certificateRequestService.prepareAndCreateCertificateIndusterialRequest(certReqId, UUID.randomUUID().toString()));
    }

    @GetMapping("/certificate/{requestNumber}")
    public ApiResponse<Long> findGrantCertificateDocumentIdByRequestNumber(@PathVariable(name = "requestNumber") String requestNumber) {
        return ApiResponse.ok(certificateRequestService.findGrantCertificateDocumentIdByRequestNumber(requestNumber));
    }
}
