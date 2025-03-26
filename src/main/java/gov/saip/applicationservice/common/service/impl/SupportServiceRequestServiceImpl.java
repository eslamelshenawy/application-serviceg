package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerServiceFeignClient;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.StartProcessResponseDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceRequestStatusEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.ValidationType;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.LKSupportServiceRequestStatusService;
import gov.saip.applicationservice.common.service.LKSupportServicesService;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;
import gov.saip.applicationservice.common.service.activityLog.ActivityLogService;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.bpm.SupportServiceProcess;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.SupportServiceValidator;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Primary
@Transactional
@Slf4j
public abstract class SupportServiceRequestServiceImpl<E extends ApplicationSupportServicesType> extends BaseServiceImpl<E, Long> implements SupportServiceRequestService<E> {
    public abstract SupportServiceRequestRepository getSupportServiceRequestRepository();

    @Override
    protected BaseRepository<E, Long> getRepository() {
        return getSupportServiceRequestRepository();
    }

    @Autowired
    private LKSupportServicesService lKSupportServicesService;
    @Autowired
    @Lazy
    private ApplicationInfoService applicationInfoService;
    @Lazy
    @Autowired
    private ActivityLogService activityLogService;


    @Autowired
    private SupportServiceCustomerService supportServiceCustomerService;

    @Lazy
    @Autowired
    private SupportServiceValidator supportServiceValidator;

    @Autowired
    private CustomerServiceFeignClient customerServiceFeignClient;

    @Autowired
    public LKSupportServiceRequestStatusService lKSupportServiceRequestStatusService;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    private SupportServiceProcess supportServiceProcess;

    @Autowired
    public void setSupportServiceProcess(SupportServiceProcess supportServiceProcess) {
        this.supportServiceProcess = supportServiceProcess;
    }

    public E insert(E entity) {
        return insertNewRequest(entity, null);
    }

    @Override
    public E insert(SupportServiceType supportServiceType, E applicationSupportServicesType) {
        LKSupportServices supportServices = new LKSupportServices();
        supportServices.setCode(supportServiceType);
        applicationSupportServicesType.setLkSupportServices(supportServices);
        return insertNewRequest(applicationSupportServicesType, null);
    }

    public E insert(SupportServiceType supportServiceType, E applicationSupportServicesType, Long parentServiceId) {
        LKSupportServices supportServices = new LKSupportServices();
        supportServices.setCode(supportServiceType);
        applicationSupportServicesType.setLkSupportServices(supportServices);
        return insertNewRequest(applicationSupportServicesType, parentServiceId);
    }

    private E insertNewRequest(E entity, Long parentServiceId) {
        if (entity.getApplicationInfo() != null && entity.getApplicationInfo().getId() != null) {
            applicationInfoService.validateSupportServicePreConditions(entity.getApplicationInfo().getId(), entity.getLkSupportServices().getCode().name(), parentServiceId);
        }
        String requestNumber = generateRequestNumber();
        entity.setRequestNumber(requestNumber);
        // get lookup by category and code
        entity.setLkSupportServices(getLKSupportService(entity));
        entity.setRequestStatus(getSupportServiceNewRequestStatus(entity));
        entity.setCreatedByCustomerCode(Utilities.getCustomerCodeFromHeaders());
        // to add app to bridge table
        if (entity.getApplications() != null && !entity.getApplications().isEmpty()) {
            entity.setApplications(entity.getApplications());
        }
        E insert = super.insert(entity);
        supportServiceCustomerService.addSupportServiceCustomer(insert, entity.getLkSupportServices().getCode(), parentServiceId);
        return insert;
    }

    private LKSupportServices getLKSupportService(E entity) {
        LKSupportServices service;
        String category = null;
        if (entity.getApplicationInfo() != null && entity.getApplicationInfo().getId() != null) {
            category = applicationInfoService.getApplicationCategoryById(entity.getApplicationInfo().getId());
        }
        List<LKSupportServices> services = lKSupportServicesService.findByCodeAndCategory(entity.getLkSupportServices().getCode(), category);
        if (services == null || services.isEmpty() || services.size() > 1) {
            log.error("Exception while creating support service because of no code for this service or code match more than one service, code is {} ", entity.getLkSupportServices().getCode());
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.BAD_REQUEST);
        } else {
            service = lKSupportServicesService.findByCodeAndCategory(entity.getLkSupportServices().getCode(), category).get(0);
        }
        return service;
    }

    @Override
    public List<E> getAllByApplicationId(Long appId, SupportServiceType type) {
        return getSupportServiceRequestRepository().findByApplicationInfoIdAndLkSupportServicesCode(appId, type);
    }
    @Override
    @CheckCustomerAccess(type = ValidationType.SUPPORT_SERVICES)
    public E findById(Long id) {
        String[] params = {id.toString()};
        return getRepository().findById(id).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
    }


    private String generateRequestNumber() {
        return getSupportServiceRequestRepository().getLastRequestNumber();
    }

    private String generateRequestNumber(Long num) {
        return "SS-" + String.format("%06d", num + 1);
    }


    private LKSupportServiceRequestStatus getSupportServiceNewRequestStatus(E entity) {
        // do not override status that is sent
        if (entity.getRequestStatus() != null) {
            return entity.getRequestStatus();
        }
        // if the request is free, the process will be started automatically, so it's under procedure
        if (SupportServicePaymentStatus.FREE.equals(entity.getPaymentStatus())) {
            return new LKSupportServiceRequestStatus(lKSupportServiceRequestStatusService.findIdByCode(SupportServiceRequestStatusEnum.UNDER_PROCEDURE));
        }

        // if not sent nor free, make it draft
        return new LKSupportServiceRequestStatus(lKSupportServiceRequestStatusService.findIdByCode(SupportServiceRequestStatusEnum.DRAFT));
    }

    @Override
    public void updateRequestStatusById(Long id, Integer newStatusId) {
        getSupportServiceRequestRepository().updateRequestStatus(newStatusId, id);
    }

    @Override
    public void updateRequestStatusByCode(Long id, SupportServiceRequestStatusEnum newStatusCode) {
        Integer newStatusId = lKSupportServiceRequestStatusService.findIdByCode(newStatusCode);
        updateRequestStatusById(id, newStatusId);
    }

    @Override
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        updatePaymentStatusAndRequestStatus(SupportServicePaymentStatus.PAID, getPaymentRequestStatus(), id);
    }


    @Override
    public void paymentCallBackHandler(Long id) {
        updatePaymentStatusAndRequestStatus(SupportServicePaymentStatus.PAID, getPaymentRequestStatus(), id);
    }

    @Override
    public void updatePaymentStatusAndRequestStatus(SupportServicePaymentStatus paymentStatus, SupportServiceRequestStatusEnum requestStatus, Long id) {
        Integer requestStatusId = lKSupportServiceRequestStatusService.findIdByCode(requestStatus);
        getSupportServiceRequestRepository().updatePaymentStatusAndRequestStatus(paymentStatus, requestStatusId, id);
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.UNDER_PROCEDURE;
    }

    public StartProcessDto prepareSupportServiceProcessRequestPaymentCallback(E entity, String processName, String requestTypeCode) {
        String customerCode = entity.getCreatedByCustomerCode();
        CustomerSampleInfoDto customerInfo = getCustomerInfo(customerCode);
        return buildStartProcessDto(entity, processName, requestTypeCode, customerCode, customerInfo);
    }

    private CustomerSampleInfoDto getCustomerInfo(String customerCode) {
        return customerServiceFeignClient.getAnyCustomerByCustomerCode(customerCode).getPayload();
    }

    private StartProcessDto buildStartProcessDto(E entity, String processName, String requestTypeCode, String customerCode, CustomerSampleInfoDto customerInfo) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("TM_TITLE_AR", entity.getApplicationInfo().getTitleAr());
        vars.put("TM_TITLE_EN", entity.getApplicationInfo().getTitleEn());
        vars.put("APPLICATION_NUMBER", entity.getApplicationInfo().getApplicationNumber());

        return StartProcessDto.builder()
                .id(entity.getId().toString())
                .applicantUserName(entity.getCreatedByUser())
                .fullNameAr(customerInfo == null ? entity.getCreatedByUser() : customerInfo.getNameAr())
                .fullNameEn(customerInfo == null ? entity.getCreatedByUser() : customerInfo.getNameEn())
                .mobile(customerInfo == null ? entity.getApplicationInfo().getMobileNumber() : customerInfo.getMobile())
                .email(customerInfo == null ? entity.getApplicationInfo().getEmail() : customerInfo.getEmail())
                .applicationCategory(entity.getApplicationInfo().getCategory().getSaipCode())
                .processName(processName)
                .applicationIdColumn(entity.getApplicationInfo().getId().toString())
                .requestTypeCode(requestTypeCode)
                .supportServiceCode(entity.getLkSupportServices().getCode().name())
                .identifier(entity.getId().toString())
                .requestNumber(entity.getRequestNumber())
                .variables(vars)
                .build();
    }


    @Override
    public void startSupportServiceProcess(E entity, StartProcessDto startProcessDto) {
        if (entity.getProcessRequestId() != null) {
            return;
        }
        addApplicantsCustomerCodes(entity, startProcessDto);
        StartProcessResponseDto startProcessResponseDto = supportServiceProcess.startProcess(startProcessDto);
        transactionTemplate.executeWithoutResult(status -> updateProcessRequestId(startProcessResponseDto.getBusinessKey(), entity.getId()));
        activityLogService.insertSupportServicesActivityLogStatus(startProcessResponseDto.getTaskHistoryUIDto(), entity);
    }

    protected void addApplicantsCustomerCodes(E entity, StartProcessDto startProcessDto) {
        startProcessDto.setApplicantCustomerCode(entity.getCreatedByCustomerCode());
        if (Objects.nonNull(entity.getApplicationInfo())) {
            String code = entity.getCreatedByCustomerCode();
//            CustomerSampleInfoDto customerSampleInfoDto = customerServiceFeignClient.getAnyCustomerByCustomerCode(code).getPayload();
//            startProcessDto.setMainApplicationApplicantCustomerCode(customerServiceFeignClient.getCustomerCodeByUserId(customerSampleInfoDto.getId()).getPayload());
//            startProcessDto.setMainApplicationApplicantCustomerCode(customerServiceFeignClient.getCustomerCodeByUserId(entity.getApplicationInfo().getCreatedByUserId()).getPayload());
            startProcessDto.setMainApplicationApplicantCustomerCode(code);
        }
    }

    @Override
    @Transactional
    public void updateProcessRequestId(Long processRequestId, Long id) {
        getSupportServiceRequestRepository().updateProcessRequestId(processRequestId, id);
        log.info("trademark appeal with id {}, paid and process started with key {}", id, processRequestId);
    }

    @Override
    public LKSupportServiceRequestStatus getStatusBySupportServiceId(Long id) {
        return getSupportServiceRequestRepository().getStatusBySupportServiceId(id);
    }

    @Override
    public String getProcessRequestIdById(Long id) {
        return getSupportServiceRequestRepository().getProcessRequestIdById(id);
    }

    @Override
    public boolean isCustomerServiceApplicant(Long parentServiceId, String customerCode) {
        return getSupportServiceRequestRepository().existsByIdAndCreatedByCustomerCode(parentServiceId, customerCode);
    }
}
