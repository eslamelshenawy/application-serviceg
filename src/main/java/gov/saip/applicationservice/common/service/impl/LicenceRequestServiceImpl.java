package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.dto.supportService.license.LicenceListSortingColumn;
import gov.saip.applicationservice.common.dto.supportService.license.OppositionRevokeLicenceListSortingColumn;
import gov.saip.applicationservice.common.dto.supportService.license.RevokeLicenceListSortingColumn;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.LicenceRequestMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LicenceRequest;
import gov.saip.applicationservice.common.model.LkRegions;
import gov.saip.applicationservice.common.repository.DocumentRepository;
import gov.saip.applicationservice.common.repository.LicenceRequestRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.repository.lookup.LkRegionsRepository;
import gov.saip.applicationservice.common.repository.supportService.LkLicencePurposeRepository;
import gov.saip.applicationservice.common.repository.supportService.LkLicenceTypeRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.LicenceRequestService;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.LicenceRequestUtil;
import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LicenceRequestServiceImpl extends SupportServiceRequestServiceImpl<LicenceRequest> implements LicenceRequestService {

    private final LicenceRequestRepository licenceRequestRepository;
    private final LicenceRequestUtil licenceRequestUtil;
    private final LkRegionsRepository lkRegionsRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return licenceRequestRepository;
    }

    private final LicenceRequestMapper licenceRequestMapper;
    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationInfoService applicationInfoService;
    private final CustomerServiceCaller customerServiceCaller;
    private final TrademarkAgencyRequestService trademarkAgencyRequestService;
    private final LkLicenceTypeRepository lkLicenceTypeRepository;
    private final LkLicencePurposeRepository lkLicencePurposeRepository;

    @Override
    public LicenceRequest insert(LicenceRequest entity) {
        LicenceRequest licenceRequest;

        boolean isModification = isModificationRequest(entity.getLicenceTypeEnum());
        ApplicationPaymentMainRequestTypesEnum licenseType = determineLicenseType(isModification);

        String licenseCategory = getLicenseCategory(entity);
        if (isTrademarkCategory(licenseCategory)) {
            handleTrademarkCategory(entity);
        }

        setLicenceTypeAndPurpose(entity, isModification);
        licenceRequest = persistLicenceRequest(entity, isModification);
        processPaymentAndCallback(licenceRequest, licenseType);

        return licenceRequest;
    }

    private boolean isModificationRequest(LicenceTypeEnum licenceTypeEnum) {
        return LicenceTypeEnum.CANCEL_LICENCE.equals(licenceTypeEnum) || LicenceTypeEnum.EDIT_LICENCE.equals(licenceTypeEnum);
    }

    private ApplicationPaymentMainRequestTypesEnum determineLicenseType(boolean isModification) {
        return isModification
                ? ApplicationPaymentMainRequestTypesEnum.LICENSING_MODIFICATION
                : ApplicationPaymentMainRequestTypesEnum.LICENSING_REGISTRATION;
    }

    private String getLicenseCategory(LicenceRequest entity) {
        return applicationInfoService.getApplicationCategoryById(entity.getApplicationInfo().getId());
    }

    private boolean isTrademarkCategory(String licenseCategory) {
        return ApplicationCategoryEnum.TRADEMARK.toString().equals(licenseCategory);
    }

    private void handleTrademarkCategory(LicenceRequest entity) {
        updateEntityWithCorrectCustomerId(entity);
        licenceRequestUtil.validateLicenceRequestOwner(entity.getApplicantType(), entity.getAgencyRequestNumber(),
                entity.getCustomerId(), entity.getApplicationInfo().getId(),
                SupportServiceType.LICENSING_REGISTRATION);
        validateLicenceRequestToDateIsNotPastApplicationEndOfProtectionDate(entity);
    }

    private void setLicenceTypeAndPurpose(LicenceRequest entity, boolean isModification) {
        if (isModification) {
            entity.setLkLicenceType(lkLicenceTypeRepository.findByCode(entity.getLicenceTypeEnum().toString()).orElseThrow());
            if (LicenceTypeEnum.EDIT_LICENCE.equals(entity.getLicenceTypeEnum())) {
                entity.setLkLicencePurpose(lkLicencePurposeRepository.findByCode(entity.getLicencePurpose().toString()).orElseThrow());
            }
        }
    }

    private LicenceRequest persistLicenceRequest(LicenceRequest entity, boolean isModification) {
        if (isModification) {
            return super.insert(SupportServiceType.LICENSING_MODIFICATION, entity);
        } else {
            return super.insert(SupportServiceType.LICENSING_REGISTRATION, entity);
        }
    }

    private void processPaymentAndCallback(LicenceRequest licenceRequest, ApplicationPaymentMainRequestTypesEnum licenseType) {
        CustomerSampleInfoDto customerSampleInfoDto = customerServiceCaller.getCustomerInfoFromRequest();
        ApplicationInfoBaseDto applicationInfoBaseDto = applicationInfoService.getAppBasicInfo(licenceRequest.getApplicationInfo().getId());
        Double cost = applicationInfoService.calculateServiceCost(customerSampleInfoDto, licenseType,
                ApplicationCategoryEnum.valueOf(applicationInfoBaseDto.getApplicationCategory().getSaipCode()));

        if (cost == 0) {
            handleZeroCostInvoice(licenceRequest, licenseType);
        }
    }

    private void handleZeroCostInvoice(LicenceRequest licenceRequest, ApplicationPaymentMainRequestTypesEnum licenseType) {
        ApplicationNumberGenerationDto applicationNumberGenerationDto = new ApplicationNumberGenerationDto();
        applicationNumberGenerationDto.setApplicationPaymentMainRequestTypesEnum(licenseType);
        paymentCallBackHandler(licenceRequest.getId(), applicationNumberGenerationDto);
    }


    private void validateLicenceRequestToDateIsNotPastApplicationEndOfProtectionDate(LicenceRequest entity) {
        if(entity.getToDate() != null)
            applicationInfoService.validateGivenDateIsNotPastApplicationEndOfProtectionDate(entity.getApplicationInfo().getId(), entity.getToDate());
    }

    @Override
    public LicenceRequest update(LicenceRequest entity) {
        updateEntityWithCorrectCustomerId(entity);
        LicenceRequest licenceRequest = findById(entity.getId());
        licenceRequest.setLicenceTypeEnum(entity.getLicenceTypeEnum() != null ? entity.getLicenceTypeEnum() : licenceRequest.getLicenceTypeEnum());
        licenceRequest.setCustomerId(entity.getCustomerId() != null ? entity.getCustomerId() : licenceRequest.getCustomerId());
        licenceRequest.setLicencePurpose(entity.getLicencePurpose() != null ? entity.getLicencePurpose() : licenceRequest.getLicencePurpose());
        licenceRequest.setContractDocument(entity.getContractDocument());
        licenceRequest.setPoaDocument(entity.getPoaDocument());
        licenceRequest.setUpdatedContractDocument(entity.getUpdatedContractDocument());
        licenceRequest.setCompulsoryLicenseDocument(entity.getCompulsoryLicenseDocument());
        licenceRequest.setAgencyRequestNumber(entity.getAgencyRequestNumber());
        licenceRequest.setFromDate(entity.getFromDate());
        licenceRequest.setToDate( entity.getToDate());
        licenceRequest.setNotes(entity.getNotes());
        licenceRequest.setDocuments(entity.getDocuments() != null ? entity.getDocuments() : licenceRequest.getDocuments());
        licenceRequest.setLicenceValidityNumber(entity.getLicenceValidityNumber());
        licenceRequest.setCanceledContractDocument(entity.getCanceledContractDocument());
        licenceRequest = super.update(licenceRequest);
        completeUserTask(licenceRequest.getId() , null);
        this.updateRequestStatusByCode(licenceRequest.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        return licenceRequest;
    }

    private void completeUserTask( Long licenseId , RequestTypeEnum requestTypeEnum){
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(requestTypeEnum == null ? RequestTypeEnum.LICENSING_REGISTRATION : requestTypeEnum, licenseId).getPayload();
        Map<String, Object> approved = new LinkedHashMap();
        approved.put("value", "YES");
        Map<String, Object> processVars = new LinkedHashMap<>();
        processVars.put("approved", approved);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(processVars);
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }


    private void updateEntityWithCorrectCustomerId(LicenceRequest entity) {
        Long customerId = entity.getCustomerId();
        if(entity.getApplicantType().equals(ApplicantType.LICENSED_CUSTOMER))
            customerId = Utilities.getCustomerIdFromHeadersAsLong();
        else if(entity.getApplicantType().equals(ApplicantType.LICENSED_CUSTOMER_AGENT)) {
            String customerCode = trademarkAgencyRequestService.getAgencyCustomerCodeByRequestNumber(entity.getAgencyRequestNumber());
            customerId = customerServiceCaller.getCustomerIdByCustomerCode(customerCode);
        }
        entity.setCustomerId(customerId);
    }

    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public LicenceRequestDto getLicenceRequest(Long id) {
        LicenceRequest licenceRequest = findById(id);
        CustomerSampleInfoDto customerSampleInfoDto = null;
        LicenceRequestDto licenceRequestDto = licenceRequestMapper.map(licenceRequest);
        if (licenceRequestDto.getCustomerId() != null) {
            licenceRequestDto.setCustomerCode(customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(licenceRequestDto.getCustomerId())).getPayload());
            customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerById(licenceRequestDto.getCustomerId()).getPayload();
            licenceRequestDto.setCustomer(customerSampleInfoDto);
        } else {
            licenceRequestDto.setCustomerCode(licenceRequest.getCreatedByCustomerCode());
            customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(licenceRequestDto.getCreatedByCustomerCode()).getPayload();
            licenceRequestDto.setCustomer(customerSampleInfoDto);
        }

        RequestTasksDto task = null;
        try {
            task = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.LICENSING_REGISTRATION, id).getPayload();
        } catch (Exception e) {
        } finally {
            licenceRequestDto.setTask(task);
        }

        updateLicenceRequestDtoWithApplicantName(licenceRequestDto, licenceRequest.getCreatedByCustomerCode());

        return licenceRequestDto;
    }

    @Override
    public CustomerCodeAndApplicationIdDTO getLicenceRequestCustomerCodeAndApplicationId(Long id) {
        LicenceRequest licenceRequest = findById(id);
        String customerCode = customerServiceFeignClient.getCustomerCodeByCustomerId(String.valueOf(licenceRequest.getCustomerId())).getPayload();
        return CustomerCodeAndApplicationIdDTO.builder().applicationId(licenceRequest.getApplicationInfo().getId()).customerCode(customerCode).build();
    }

    // start process Licence registration
    private void startLicenceRegistrationProcess(Long id) {
        LicenceRequest entity = findById(id);
        CustomerSampleInfoDto clientCustomerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(entity.getCreatedByCustomerCode()).getPayload();
        ApplicationInfoBaseDto applicationInfoBaseDto = applicationInfoService.getAppBasicInfo(entity.getApplicationInfo().getId());
        // Start process
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(clientCustomerSampleInfoDto.getNameAr())
                .fullNameEn(clientCustomerSampleInfoDto.getNameEn())
                .mobile(clientCustomerSampleInfoDto.getMobile())
                .email(clientCustomerSampleInfoDto.getEmail())
                .applicationCategory(applicationInfoBaseDto.getApplicationCategory().getSaipCode())
                .processName("licensing_request_process")
                .applicationIdColumn(String.valueOf(applicationInfoBaseDto.getApplicationId()))
                .requestTypeCode("LICENSING_REGISTRATION")
                .supportServiceTypeCode(entity.getLicenceTypeEnum().name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .build();
        updateStartProcessDtoWithApplicationInfo(entity, startProcessDto);
        startSupportServiceProcess(entity, startProcessDto);
    }

    // start process Licence registration
    private void startLicenceModificationProcess(Long id) {
        LicenceRequest entity = findById(id);
        CustomerSampleInfoDto clientCustomerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(entity.getCreatedByCustomerCode()).getPayload();
        ApplicationInfoBaseDto applicationInfoBaseDto = applicationInfoService.getAppBasicInfo(entity.getApplicationInfo().getId());
        // Start process
        StartProcessDto startProcessDto = StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(clientCustomerSampleInfoDto.getNameAr())
                .fullNameEn(clientCustomerSampleInfoDto.getNameEn())
                .mobile(clientCustomerSampleInfoDto.getMobile())
                .email(clientCustomerSampleInfoDto.getEmail())
                .applicationCategory(applicationInfoBaseDto.getApplicationCategory().getSaipCode())
                .processName("licensing_request_modify_process")
                .applicationIdColumn(String.valueOf(applicationInfoBaseDto.getApplicationId()))
                .requestTypeCode("LICENSING_MODIFICATION")
                .supportServiceTypeCode(entity.getLicenceTypeEnum().name())
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(id.toString())
                .requestNumber(entity.getRequestNumber())
                .build();
        updateStartProcessDtoWithApplicationInfo(entity, startProcessDto);
        startSupportServiceProcess(entity, startProcessDto);
    }

    private void updateStartProcessDtoWithApplicationInfo(LicenceRequest licenceRequest, StartProcessDto startProcessDto) {
        startProcessDto.addVariable("APPLICATION_NUMBER", licenceRequest.getApplicationInfo().getApplicationNumber());
        startProcessDto.addVariable("APPLICATION_TITLE_AR", licenceRequest.getApplicationInfo().getTitleAr());
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        switch (applicationNumberGenerationDto.getApplicationPaymentMainRequestTypesEnum()) {
            case LICENSING_REGISTRATION:
                startLicenceRegistrationProcess(id);
                break;
            case LICENSING_MODIFICATION:
                startLicenceModificationProcess(id);
                break;
            default:
                break;
        }

        super.paymentCallBackHandler(id, applicationNumberGenerationDto);
    }

    @Override
    public String getLicensingRequestType(Long id) {

        String[] params = {id.toString()};
        return licenceRequestRepository.findLicenceTypeEnumById(id).
                orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
    }

    @Override
    public List<CustomerSampleInfoDto> getLicensedCustomersDetails(Long applicationInfoId) {
        List<Long> customerIds = licenceRequestRepository.getAllIdsOfLicensedCustomers(applicationInfoId);
        Map<Long, CustomerSampleInfoDto> customersDetails = customerServiceFeignClient.getCustomersByIds(new ListBodyDto<Long>(customerIds)).getPayload();
        List<CustomerSampleInfoDto> list = customersDetails.values().stream().map(val -> val).toList();
        return list;

    }

    @Override
    public boolean checkApplicationHaveLicence(Long applicationId) {
        return licenceRequestRepository.checkApplicationHaveLicence(applicationId);
    }
    @Override
    public boolean checkApplicationCancelLicence(Long id) {
        return licenceRequestRepository.checkApplicationCancelLicence(id);
    }

	 @Override
    public List<CustomerSampleInfoDto> getLicenseRequestAllInvolvedUsersInfo(Long id){
        LicenceRequest licenceRequest =  this.findById(id);
         List<String> codes = licenceRequestUtil.getLicenseOwnerAndAgentCodes(licenceRequest);
        return licenceRequestUtil.getLicenseRequestAllInvolvedUsersInfo(licenceRequest, codes);
    }

    @Override
    public LicenceRequestApplicationSummaryDto getApplicationSummaryByRequestLicenseId(Long id) {
        LicenceRequest licenceRequest =  this.findById(id);
        LicenceRequestDto licenceRequestDto = licenceRequestMapper.map(licenceRequest);
        updateLicenceRequestDtoWithApplicantName(licenceRequestDto, licenceRequest.getCreatedByCustomerCode());
        ApplicationInfo applicationInfo = licenceRequest.getApplicationInfo();
        ApplicationInfoSummaryDto applicationInfoSummaryDto = (ApplicationInfoSummaryDto) applicationInfoService.getApplicationSummary(applicationInfo.getId(), null);
        LicenceRequestApplicationSummaryDto licenceRequestApplicationSummaryDto= new LicenceRequestApplicationSummaryDto(applicationInfoSummaryDto,licenceRequestDto);
        return licenceRequestApplicationSummaryDto;
    }

	private void updateLicenceRequestDtoWithApplicantName(LicenceRequestDto licenceRequestDto, String applicantCode) {
        CustomerSampleInfoDto customerSampleInfoDto =  applicantCode == null ? null : customerServiceCaller.getCustomerInfoByCustomerCode(applicantCode);
        if(customerSampleInfoDto != null) {
            licenceRequestDto.setApplicantNameAr(customerSampleInfoDto.getNameAr());
            licenceRequestDto.setApplicantNameEn(customerSampleInfoDto.getNameEn());
            licenceRequestDto.setApplicant(customerSampleInfoDto);
        }
    }

    @Override
    public PaginationDto<List<LicenceRequestListDto>> getAllApprovedLicenseRequests(String query, int page, int limit, LicenceListSortingColumn sortOrder, Sort.Direction sortDirection, SupportServiceRequestStatusEnum status) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection , sortOrder.getColumnName()));
        String currentUserCustomerCode = Utilities.getCustomerCodeFromHeaders();
        Long currentUserCustomerId = Utilities.getCustomerIdFromHeadersAsLong();
        List<String> statuses = status!= null? Arrays.asList(status.name()) :  Arrays.asList(SupportServiceRequestStatusEnum.LICENSED.name());
                Page<LicenceRequestListDto> licenceRequest =   licenceRequestRepository.getAllApprovedLicenseRequests(statuses, query, pageable, currentUserCustomerCode, currentUserCustomerId);
        return PaginationDto.<List<LicenceRequestListDto>>builder()
                .content(licenceRequest.getContent())
                .totalPages(licenceRequest.getTotalPages())
                .totalElements(licenceRequest.getTotalElements())
                .build();
    }

    @Override
    public PaginationDto<List<LicenceRequestListDto>> getAllRevokedLicenseRequests(String query, int page, int limit, RevokeLicenceListSortingColumn sortOrder, Sort.Direction sortDirection, SupportServiceRequestStatusEnum status) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by( sortDirection , sortOrder.getColumnName()));
        String currentUserCustomerCode = Utilities.getCustomerCodeFromHeaders();
        Long currentUserCustomerId = Utilities.getCustomerIdFromHeadersAsLong();
        List<String> statuses= status!= null? Arrays.asList(status.name()) : Arrays.asList(SupportServiceRequestStatusEnum.COMPLETED.name(), SupportServiceRequestStatusEnum.OPPOSITION_WATING.name(),SupportServiceRequestStatusEnum.WITHDRAWAL.name());
        Page<LicenceRequestListDto> licenceRequest =   licenceRequestRepository.getAllRevokedLicenseRequests(statuses, query, pageable, currentUserCustomerCode, currentUserCustomerId);
        return PaginationDto.<List<LicenceRequestListDto>>builder()
                .content(licenceRequest.getContent())
                .totalPages(licenceRequest.getTotalPages())
                .totalElements(licenceRequest.getTotalElements())
                .build();

    }


    @Override
    public PaginationDto<List<LicenceRequestListDto>> getAllOppositionLicenseRequests(String query, int page, int limit, OppositionRevokeLicenceListSortingColumn sortOrder, Sort.Direction sortDirection, SupportServiceRequestStatusEnum status) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, sortOrder.getColumnName()));
        String currentUserCustomerCode = Utilities.getCustomerCodeFromHeaders();
        Long currentUserCustomerId = Utilities.getCustomerIdFromHeadersAsLong();
        List<String> statuses = status!= null? Arrays.asList(status.name()) : Arrays.asList(SupportServiceRequestStatusEnum.COMPLETED.name(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name(), SupportServiceRequestStatusEnum.WITHDRAWAL.name(), SupportServiceRequestStatusEnum.APPROVED.name(), SupportServiceRequestStatusEnum.REJECTED.name(), SupportServiceRequestStatusEnum.PENDING.name(), SupportServiceRequestStatusEnum.REQUEST_CORRECTION.name(), SupportServiceRequestStatusEnum.COURT_DOCUMENTS_CORRECTION.name());
        Page<LicenceRequestListDto> licenceRequest =   licenceRequestRepository.getAllOppositionLicenseRequests(statuses, query, pageable, currentUserCustomerCode, currentUserCustomerId);
        return PaginationDto.<List<LicenceRequestListDto>>builder()
                .content(licenceRequest.getContent())
                .totalPages(licenceRequest.getTotalPages())
                .totalElements(licenceRequest.getTotalElements())
                .build();
    }

    @Override
    public String getLicensedCustomerCodeByLicenseId(Long id){
        Long licensedCustomerId = licenceRequestRepository.findLicenceCustomerId(id);
        if(licensedCustomerId == null){
            LicenceRequest licenceRequest = licenceRequestRepository.findById(id).get();
            licensedCustomerId = customerServiceCaller.getCustomerIdByCustomerCode(licenceRequest.getCreatedByCustomerCode());
            return customerServiceCaller.getCustomerCodeByCustomerId(licensedCustomerId);
        }
        return customerServiceCaller.getCustomerCodeByCustomerId(licensedCustomerId);
    }

    @Override
    public List<LicenceRequestListDto> getAllApprovedLicensedRequests(Long appId,LicenceTypeEnum licenceType) {
        List<LicenceRequestListDto> allApprovedLicensedRequests = licenceRequestRepository.getAllApprovedLicensedRequests(appId);
        return allApprovedLicensedRequests;
    }

    @Override
    public void changeLicenceValidityNumber(Long applicationId, Integer licenceValidityNumber,LicenceTypeEnum licenceType, Long mainRequestId) {
         licenceRequestRepository.changeLicenceValidityNumber(applicationId,licenceValidityNumber, mainRequestId);
    }

    @Override
    public void makeCancelLicenceRequest(Long applicationId, Long mainRequestId) {
        licenceRequestRepository.makeCancelLicenceRequest(applicationId, mainRequestId);
    }
    public LicenceRequestDto updateLicenceRequest(LicenceRequestDto licenceRequestDto)  {
        long  id = licenceRequestDto.getId();
        LicenceRequest LicenceRequest =  getReferenceById(id);
        LicenceRequest.setLicenceTypeEnum(licenceRequestDto.getLicenceTypeEnum());

        LicenceRequest.setApplicantType(licenceRequestDto.getApplicantType());
        LicenceRequest.setFromDate(licenceRequestMapper.convertDateFromHijriToGregorian( licenceRequestDto.getFromDate()));
        LicenceRequest.setToDate(licenceRequestMapper.convertDateFromHijriToGregorian( licenceRequestDto.getToDate()));
        LicenceRequest.setNotes(licenceRequestDto.getNotes());

        LicenceRequest.setLicenceValidityNumber(licenceRequestDto.getLicenceValidityNumber());
        LicenceRequest.setUpdatedContractDocument(licenceRequestDto.getUpdatedContractDocument() == null ? null : new Document(licenceRequestDto.getUpdatedContractDocument().getId()) );
        LicenceRequest.setPoaDocument(licenceRequestDto.getPoaDocument() == null ? null : new Document(licenceRequestDto.getPoaDocument().getId()) );
        LicenceRequest.setContractDocument(licenceRequestDto.getSupportDocument() == null ? null : new Document(licenceRequestDto.getSupportDocument().getId()) );
        LicenceRequest.setCanceledContractDocument(licenceRequestDto.getCanceledContractDocument() == null ? null : new Document(licenceRequestDto.getCanceledContractDocument().getId()) );
        LicenceRequest.setMainLicenceRequest(licenceRequestDto.getMainLicenceRequest() == null ? null : new LicenceRequest(licenceRequestDto.getMainLicenceRequest().getId()) );

        List<LkRegions> regions = new ArrayList<>();
        if (licenceRequestDto.getRegions() != null) {
            regions = lkRegionsRepository.findAllById(
                    licenceRequestDto.getRegions().stream()
                            .map(LkRegionsDto::getId)
                            .collect(Collectors.toList())
            );
        }
        LicenceRequest.setRegions(regions);
        List<Long> documentIds = licenceRequestDto.getDocumentIds();
        List<Document> documents = (documentIds == null ?
                Collections.emptyList() :
                documentRepository.findAllById(
                        documentIds.stream()
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                )
        );
        LicenceRequest.setDocuments(documents);
        completeUserTask(id,licenceRequestDto.getRequestType() == null ? RequestTypeEnum.LICENSING_REGISTRATION :licenceRequestDto.getRequestType());
        this.updateRequestStatusByCode(LicenceRequest.getId(), SupportServiceRequestStatusEnum.UNDER_PROCEDURE);
        licenceRequestRepository.save(LicenceRequest);
        return  licenceRequestMapper.map(LicenceRequest);
    }

}
