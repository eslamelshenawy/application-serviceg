package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationInfoResultDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.certs.*;
import gov.saip.applicationservice.common.enums.certificate.CertificateTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.CertificateRequest;

import java.util.List;

public interface CertificateRequestService extends BaseService<CertificateRequest, Long> {

     PaginationDto listCertificateRequests(Long id, String statusCode, CertificateRequestSearchFilterDto certificateRequestSearchFilterDto, int page, int limit);
    
    PaginationDto listPreviousCertificateRequests(Long id, CertificateRequestSearchFilterDto certificateRequestSearchFilterDto, String statusCode, int page, int limit);
    
    
    CertRequestDetailsDto getCertDetailsDto(Long reqId);
    
    void changeCertificateRequestStatus(Long applicationId, String certificateStatusCode);
    
    CertificateRequestVerificationDto checkCertificateRequest(String applicationNumber, String certificateNumber, String certificateTypeCode);
    
    ApplicationInfoResultDto getAppInfoByCertificateRequestId(Long id);
    
    void linkDocumentToCertificateRequest(Long id, DocumentDto documentDto);
    
    public void processCertificateRequest(Long certificateRequestId, String documentType);
    
    CertificateRequest prepareCertificateRequestEntity(Long applicationId, String certificateType, String createdByUser);
    
    Long reCreateCertificate(Long requestId);
    Long findApplicationInfoIdById(Long certificateId);

    CertificateRequest prepareAndSaveCertificateRequest(Long appId, String certificateType);
    
    void generateCertificatePdf(String documentType, CertificateRequest certificateRequest);

    CertificateRequest createCertificationIfNotExist(Long applicationId, String certificateType);

    public void generateSupportServiceCertificatePdf(Long applicationId, String certificateType, String documentType, Long supportServiceId);
    
    void generateFreeCertificatePdf(Long id, String certificateType);


    String findGrantCertificateNumberByAppId( Long appId);


    void prepareAndCreateCertificateRequest(Long appId, String certificateType);

    String callPatCertJasperReports(Long certificateId, String generationId);

    List<CertificateRequestProjectionDto> listCertificateByApplicationId(Long appId);
     String getBillNumberByAppId(Long appId,String certificateTypeCode);

     void deleteOldCertificateForOldOwner(Long applicationId, String certificateType);
     String getLastCertificateNumber(Long applicationId, CertificateTypeEnum certificateType);

    String prepareAndCreateCertificateIndusterialRequest(Long certificateId, String generationId);

    Long findGrantCertificateDocumentIdByRequestNumber(String requestNumber);

    ApplicationInfo findApplicationInfoByNumber(String applicationNumber);

    DocumentDto findDocumentByApplicationId(Long applicationId);

}
