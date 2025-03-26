package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationInfoResultDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.certs.*;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.certificate.CertificateStatusEnum;
import gov.saip.applicationservice.common.enums.certificate.CertificateTypeEnum;
import gov.saip.applicationservice.common.mapper.ApplicationRelevantTypeMapper;
import gov.saip.applicationservice.common.mapper.CertificateRequestMapper;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.CertificateRequestRepository;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.lookup.LkCertificateStatusRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CertificateRequestService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.LkCertificateTypeService;
import gov.saip.applicationservice.common.service.lookup.LkCertificateStatusService;
import gov.saip.applicationservice.common.service.pdf.PdfGenerationService;
import gov.saip.applicationservice.common.service.pdf.TrademarkPdfGenerationService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_MAIN;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CertificateRequestServiceImpl extends BaseServiceImpl<CertificateRequest, Long> implements CertificateRequestService {

    private final CertificateRequestRepository certificateRequestRepository;
    private final DocumentRepository documentRepository;
    private final ApplicationInfoService applicationInfoService;
    private final LkCertificateTypeService certificateTypeService;
    private final LkCertificateStatusRepository lkCertificateStatusRepository;
    private final LkCertificateStatusService certificateStatusService;
    private final DocumentMapper documentMapper;
    private final CertificateRequestMapper certificateRequestMapper;
    private final CustomerServiceCaller customerServiceCaller;
    private final PdfGenerationService pdfGenerationService;
    private final TrademarkPdfGenerationService trademarkPdfGenerationService;
    private final PaymentFeeCostFeignClient paymentFeeCostFeignClient;
    @Override
    protected BaseRepository<CertificateRequest, Long> getRepository() {
        return certificateRequestRepository;
    }

    @Override
    public CertificateRequest insert(CertificateRequest entity) {
        ApplicationInfo applicationInfo = applicationInfoService.findById(entity.getApplicationInfo().getId());
        LkCertificateType type = certificateTypeService.findByCode(entity.getCertificateType().getCode());
        LkCertificateStatus status = certificateStatusService.findByCode(CertificateStatusEnum.PENDING.name());
        CertificateRequest certificateRequest =
                certificateRequestRepository.findByApplicationInfoAndCertificateTypeAndCertificateStatus(applicationInfo, type, status);
        if(certificateRequest != null)
            return certificateRequest;
        Long maxId = getMaxId();
        String requestNumber = generateAppNumber(maxId);
        entity.setSerial(maxId == null ? 0 : maxId + 1);
        entity.setApplicationInfo(applicationInfo);
        entity.setCertificateType(type);
        entity.setCertificateStatus(status);
        entity.setDocument(null);
        entity.setRequestNumber(requestNumber);
        return super.insert(entity);
    }
    
    protected Long getMaxId() {
        return certificateRequestRepository.getMaxId();
    }
    
    protected String generateAppNumber(Long maxId) {
        
        if(maxId == null)
            maxId = 1L;
        else
            maxId = maxId + 1;
        String idString = String.format("%04d", maxId);
        return "SA" + idString;
    }

    @Override
    public PaginationDto listCertificateRequests(Long id, String statusCode,
                                                 CertificateRequestSearchFilterDto certificateRequestSearchFilterDto, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        List<String> customersCodes = customerServiceCaller.getCustomersCodes(certificateRequestSearchFilterDto.getApplicantName());
        if (customersCodes != null && customersCodes.isEmpty())
            return new PaginationDto<>();

        String serviceType = certificateRequestSearchFilterDto.getServiceType() != null
                ? String.valueOf(certificateRequestSearchFilterDto.getServiceType())
                : null;

        Page<CertificateRequestProj> projection =
                certificateRequestRepository.listCertificateRequest(pageable,certificateRequestSearchFilterDto.getApplicationNumber(),certificateRequestSearchFilterDto.getRequestType(),
                        serviceType,certificateRequestSearchFilterDto.getDepositDate(),certificateRequestSearchFilterDto.getRequestNumber());
        if (projection.getContent() == null
                || projection.getContent().isEmpty()
        )
            return new PaginationDto<>();

        ApplicationRelevantTypeMapper mapper = Mappers.getMapper(ApplicationRelevantTypeMapper.class);

        List<CertificateRequestViewDto> certificateRequestViewDto = projection.getContent()
                .stream()
                .map(projectionItem -> {
                    CertificateRequestViewDto dto = new CertificateRequestViewDto();
                    LkCertificateType certificateType = certificateTypeService.findById(projectionItem.getCertificateType());
                    dto.setCertificateType(certificateType);
//
                    dto.setCertificateRequestId(projectionItem.getCertificateRequestId());
                    dto.setApplicationId(projectionItem.getApplicationId());
                    dto.setDepositDate(projectionItem.getDepositDate().toLocalDate());
                    dto.setApplicationNumber(projectionItem.getApplicationNumber());
                    dto.setApplicationTitleAr(projectionItem.getApplicationTitleAr());
                    dto.setApplicationTitleEn(projectionItem.getApplicationTitleEn());
                    Optional<LkCertificateStatus> certificateStatus= lkCertificateStatusRepository.findById(projectionItem.getCertificateStatus());
                    dto.setCertificateStatus(certificateStatus.get());
                    dto.setCustomerCode(projectionItem.getCustomerCode());

                    return dto;
                })
                .collect(Collectors.toList());

        getCertificateRequestApplicantMainRelevant(certificateRequestViewDto);
        return PaginationDto.builder()
                .content(certificateRequestViewDto)
                .totalPages(projection.getTotalPages())
                .totalElements(projection.getTotalElements())
                .build();
    }

    @Override
    public PaginationDto listPreviousCertificateRequests(Long id, CertificateRequestSearchFilterDto certificateRequestSearchFilterDto, String statusCode, int page, int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Long applicationId = Utilities.isLong(certificateRequestSearchFilterDto.getApplicationNumber());
        List<String> customersCodes = customerServiceCaller.getCustomersCodes(certificateRequestSearchFilterDto.getApplicantName());
        if (customersCodes != null && customersCodes.isEmpty())
            return new PaginationDto<>();
        Page<CertificateRequestProjection> projection =
                certificateRequestRepository.
                        listPreviousCertificateRequests(id,
                                certificateRequestSearchFilterDto.getApplicationNumber(),
                                certificateRequestSearchFilterDto.getApplicationTitle(),
                                certificateRequestSearchFilterDto.getCertificateTypeName(),
                                certificateRequestSearchFilterDto.getCertificateStatusName(),
                                certificateRequestSearchFilterDto.getDepositDate(),
                                certificateRequestSearchFilterDto.getCustomerCode(),
                                customersCodes,
                                statusCode,
                                Applicant_MAIN,
                                applicationId,
                                pageable);
        
        if (projection.getContent() == null
                || projection.getContent().isEmpty())
            return new PaginationDto<>();
        
        List<PreviousCertificateRequestDto> previousCertificateRequestDtos =
                certificateRequestMapper.mapToCertificateRequestDto(projection.getContent());
        
        getPreviousCertificateRequestApplicantMainRelevant(previousCertificateRequestDtos);
        return PaginationDto.builder()
                .content(previousCertificateRequestDtos)
                .totalPages(projection.getTotalPages())
                .totalElements(projection.getTotalElements())
                .build();
    }
    
    private void getCertificateRequestApplicantMainRelevant(List<CertificateRequestViewDto> certificateRequests) {
        List<ApplicationRelevantTypeLightDto> applicationRelevantTypes = new ArrayList<>();
        certificateRequests.forEach(certificateRequest -> {
            applicationRelevantTypes.add(certificateRequest.getApplicationRelevantType());
        });
        
        applicationInfoService.getDataOfCustomersByCode(applicationRelevantTypes);
    }
    
    private void getPreviousCertificateRequestApplicantMainRelevant(List<PreviousCertificateRequestDto> certificateRequests) {
        List<ApplicationRelevantTypeLightDto> applicationRelevantTypes = new ArrayList<>();
        certificateRequests.forEach(certificateRequest -> {
            applicationRelevantTypes.add(certificateRequest.getApplicationRelevantType());
        });
        
        applicationInfoService.getDataOfCustomersByCode(applicationRelevantTypes);
    }

    @Override
    public CertRequestDetailsDto getCertDetailsDto(Long reqId) {
       
       CertificateRequest certificateRequest = findById(reqId);
       CertRequestDetailsDto certRequestDetailsDto=CertRequestDetailsDto.builder()
               .documents(documentMapper.mapToDto(certificateRequest.getDocument()))
               .lastActionAr(certificateRequest.getCertificateType().getNameAr())
               .lastActionEn(certificateRequest.getCertificateType().getNameEn())
               .build();
        return certRequestDetailsDto;
    }
    
    @Override
    public void changeCertificateRequestStatus(Long certificateRequestId, String certificateStatusCode) {
        LkCertificateStatus certificateStatus =
                certificateStatusService.findByCode(certificateStatusCode);
        Optional<CertificateRequest> certificateRequests =
                certificateRequestRepository.findById(certificateRequestId);
        
        if(certificateRequests.isPresent()) {
            certificateRequests.get().setCertificateStatus(certificateStatus);
            update(certificateRequests.get());
        }
    }
    
    @Override
    public CertificateRequestVerificationDto checkCertificateRequest(String applicationNumber, String requestNumber, String certificateTypeCode) {
        Long applicationId = Utilities.isLong(applicationNumber);
        List<CertificateRequestVerificationDto> certificates = certificateRequestRepository
                .checkCertificateRequest(applicationNumber, requestNumber, certificateTypeCode, CertificateStatusEnum.COMPLETED.name(),applicationId);
        if(certificates.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return certificates.get(0);
    }
    
    @Override
    public ApplicationInfoResultDto getAppInfoByCertificateRequestId(Long id) {
        return certificateRequestRepository.getApplicationInfoByCertificateRequestId(id);
    }
    
    @Override
    public void linkDocumentToCertificateRequest(Long id, DocumentDto documentDto) {

        Optional<CertificateRequest> certificateRequests =
                certificateRequestRepository.findById(id);

        if(certificateRequests.isPresent()) {
            certificateRequests.get().setDocument(new Document(documentDto.getId()));
            update(certificateRequests.get());
        }
        
    }
    
    @Transactional
    public void processCertificateRequest(Long certificateId, String documentType) {
        processCertificateRequest(certificateId, documentType, null);
    }
    
    @Transactional
    public void processCertificateRequestForSupportService(Long certificateId, String documentType, Long supportServiceId) {
        processCertificateRequest(certificateId, documentType, supportServiceId);
    }
    
    private void processCertificateRequest(Long certificateId, String documentType, Long supportServiceId) {
        try {
            log.info("start processCertificateRequest with certificateId {} , documentType {} and supportServiceId {} ", certificateId, documentType, supportServiceId);
            List<DocumentDto> documents = new ArrayList<>();
            ApplicationInfoResultDto applicationInfoResultDto = getAppInfoByCertificateRequestId(certificateId);
            String saipCode = applicationInfoResultDto.getSaipCode();
            
            if (ApplicationCategoryEnum.PATENT.name().equals(saipCode)) {
                documents = pdfGenerationService.generateUploadSavePdfForPatentApplication(
                        applicationInfoResultDto.getApplicationId(), certificateId, documentType,
                        applicationInfoResultDto.getApplicationNumber(), applicationInfoResultDto.getCertificateNumber());
            } else if (ApplicationCategoryEnum.TRADEMARK.name().equals(saipCode)) {
                documents = trademarkPdfGenerationService.generateUploadSavePdfForTrademarkApplication(
                        applicationInfoResultDto.getApplicationId(), certificateId, documentType,
                        applicationInfoResultDto.getApplicationNumber(), applicationInfoResultDto.getCertificateNumber(), supportServiceId);
            } else if (ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name().equals(saipCode)) {
                documents = pdfGenerationService.generateUploadSavePdfForIndustrialApplication(
                        applicationInfoResultDto.getApplicationId(), certificateId, documentType,
                        applicationInfoResultDto.getApplicationNumber(), applicationInfoResultDto.getCertificateNumber());
            } else if (ApplicationCategoryEnum.INTEGRATED_CIRCUITS.name().equals(saipCode)) {
                documents = pdfGenerationService.generateUploadSavePdfForIntegratedCircuit(
                        applicationInfoResultDto.getApplicationId(), certificateId, documentType,
                        applicationInfoResultDto.getApplicationNumber(), applicationInfoResultDto.getCertificateNumber());
            }
            
            changeCertificateRequestStatus(certificateId, CertificateStatusEnum.COMPLETED.name());
            log.info("processCertificateRequest done with certificateId {} , documentType {} and supportServiceId {} ", certificateId, documentType, supportServiceId);

            if (!documents.isEmpty()) {
                linkDocumentToCertificateRequest(certificateId, documents.get(0));
            }
        } catch (Exception exception) {
            changeCertificateRequestStatus(certificateId, CertificateStatusEnum.FAILED.name());
            log.error("Failed to processCertificateRequest with certificateId {} , documentType {} and supportServiceId {} exception message {}",
                    certificateId, documentType, supportServiceId, exception.getMessage());
        }
    }

    
    @Override
    public CertificateRequest prepareCertificateRequestEntity(Long applicationId,String certificateTypeCode, String createdByUser) {

        CertificateRequest entity = new CertificateRequest();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        LkCertificateType certificateType = certificateTypeService.findByCode(certificateTypeCode);
        applicationInfo.setId(applicationId);
        certificateType.setCode(certificateTypeCode);
        entity.setApplicationInfo(applicationInfo);
        entity.setCertificateType(certificateType);
        entity.setCreatedByUser(createdByUser);
        return entity;
    }
    
    @Override
    public Long reCreateCertificate(Long id) {
        String[] params = {id.toString()};
        CertificateRequest certificateRequest =
                certificateRequestRepository.findById(id).orElseThrow(() ->
                        new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
        processCertificateRequest(id, "Issue Certificate");
        return certificateRequest.getId();
    }



    @Override
    public Long findApplicationInfoIdById(Long certificateId) {
        return certificateRequestRepository.findApplicationInfoIdById(certificateId);
    }

    public CertificateRequest prepareAndSaveCertificateRequest(Long applicationId, String certificateType) {
        try{String createdByUser = applicationInfoService.getCreatedUserById(applicationId);
        CertificateRequest entity =
                prepareCertificateRequestEntity(applicationId, certificateType, createdByUser);
        return insert(entity);}
        catch (Exception ex){
            log.error("error with prepareAndSaveCertificateRequest for certificateType {} and applicationId {} exception message {}", certificateType, applicationId, ex.getMessage());
            throw new BusinessException("can't prepareAndSaveCertificateRequest " + ex.getMessage());
        }
    }
    
    @Override
    @Transactional
    public void generateCertificatePdf(String documentType, CertificateRequest certificateRequest) {
        processCertificateRequest(certificateRequest.getId(), documentType);
    }

    @Override
    @Transactional
    public CertificateRequest createCertificationIfNotExist(Long applicationId, String certificateType) {
        CertificateRequest certificateRequest = null ;
        Optional<CertificateRequest>  certificateRequestOptional = certificateRequestRepository.getLastCertificate(applicationId, certificateType);
       // CertificateRequest certificateRequest = certificateRequestRepository.findByApplicationInfoAndCertificateTypeAndStatus(applicationId, certificateType, null);
        if(certificateRequestOptional.isPresent()) {
            certificateRequest = certificateRequestOptional.get();
        }
        
        if (certificateRequest == null)
            certificateRequest = prepareAndSaveCertificateRequest(applicationId, certificateType);
        return certificateRequest;
    }

    @Override
    @Transactional
    public void generateSupportServiceCertificatePdf(Long applicationId, String certificateType, String documentType, Long supportServiceId) {
        log.info("generateSupportServiceCertificatePdf with applicationId {} , certificateType  {} , documentType {} and supportServiceId {} ",
                applicationId, certificateType, documentType, supportServiceId);
        CertificateRequest certificateRequest = prepareAndSaveCertificateRequest(applicationId, certificateType);
        Long certificateId = certificateRequest.getId();
        processCertificateRequestForSupportService(certificateId, documentType, supportServiceId);
    }
    
    @Override
    @Transactional
    public void generateFreeCertificatePdf(Long id, String certificateType) {
        CertificateRequest certificateRequest = findById(id);
        String documentType = Constants.FREE_CERTIFICATES_MAP_WITH_DOCUMENTS_TYPES.get(certificateType);
        processCertificateRequest(certificateRequest.getId(), documentType);
    }

    @Override
    public String findGrantCertificateNumberByAppId(Long appId) {
        return certificateRequestRepository.findGrantCertificateNumberByAppId(appId , CertificateTypeEnum.ISSUE_CERTIFICATE.name(),CertificateStatusEnum.COMPLETED.name());
    }

    @Override
    public void prepareAndCreateCertificateRequest(Long appId, String certificateType) {
        prepareAndSaveCertificateRequest(appId, certificateType);
    }

    @Override
    public String callPatCertJasperReports(Long certificateId, String generationId) {
        return pdfGenerationService.getPatCertFilePath(certificateId, generationId, this.findApplicationInfoIdById(certificateId));
    }
    @Override
    public List<CertificateRequestProjectionDto> listCertificateByApplicationId(Long appId) {
        List<CertificateRequestProjection> listCertificateByApplicationId =
                certificateRequestRepository.listCertificateByApplicationId(appId);

        List<CertificateRequestProjectionDto> certificateRequestViewDto =
                certificateRequestMapper.mapToBaseCertificateRequestProjectionDto(listCertificateByApplicationId);
        return certificateRequestViewDto;
    }

    @Override
    public String getBillNumberByAppId(Long appId, String certificateTypeCode) {
        try {
            return String.valueOf(paymentFeeCostFeignClient.getBillNumberByAppId(appId,certificateTypeCode).getPayload());
        }
        catch (Exception ex){
            log.info("getBillNumberByAppIdException "+ex.getMessage());
            return null;
        }
    }

    @Override
    public void deleteOldCertificateForOldOwner(Long applicationId, String certificateType) {
        Optional<CertificateRequest> certificateRequest = certificateRequestRepository.getLastCertificate(applicationId, certificateType);
        if (certificateRequest.isPresent()) {
            certificateRequest.get().setIsDeleted(1);
            certificateRequestRepository.save(certificateRequest.get());
        }
    }

    @Override
    public String getLastCertificateNumber(Long applicationId, CertificateTypeEnum certificateType) {
        Optional<CertificateRequest> certificateRequest =  certificateRequestRepository.getLastCertificate(applicationId, certificateType.name());
        return certificateRequest.isPresent() ? certificateRequest.get().getRequestNumber() : null;
    }

    @Override
    public String prepareAndCreateCertificateIndusterialRequest(Long certificateId, String generationId) {
        return pdfGenerationService.getPatCertFilePathIndusterial(certificateId, generationId, this.findApplicationInfoIdById(certificateId));
    }

    @Override
    public Long findGrantCertificateDocumentIdByRequestNumber(String requestNumber) {
        return certificateRequestRepository.findGrantCertificateDocumentIdByRequestNumber(requestNumber);
    }

    @Override
    public ApplicationInfo findApplicationInfoByNumber(String applicationNumber) {
        return applicationInfoService.findByApplicationNumber(applicationNumber)
                .orElseThrow(() -> new BusinessException("Application not found for number: " + applicationNumber));
    }

    @Override
    public DocumentDto findDocumentByApplicationId(Long applicationId) {
        // Query for the specific document type directly from the database
        List<Document> documents = documentRepository.findLastDocumentByApplicationIdAndType(applicationId, "Issue Certificate");

        // Map to DTO if found, otherwise return null
        return documents.isEmpty() ? null : documentMapper.mapToDto(documents.get(0));
    }


}
