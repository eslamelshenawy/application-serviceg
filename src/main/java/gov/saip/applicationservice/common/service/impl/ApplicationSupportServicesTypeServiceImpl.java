package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.dto.search.SearchDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceHelperInfoDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.mapper.ApplicationPreviousRequestsMapper;
import gov.saip.applicationservice.common.mapper.lookup.LkSupportServiceRequestStatusMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.repository.ApplicationSupportServicesTypeRepository;
import gov.saip.applicationservice.common.repository.SupportServiceRequestRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.PATENT;
import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.TRADEMARK;
import static gov.saip.applicationservice.common.enums.SupportServiceType.RENEWAL_FEES_PAY;
import static gov.saip.applicationservice.util.Constants.SUPPORT_SERVICES_HAVE_TASKS;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApplicationSupportServicesTypeServiceImpl extends SupportServiceRequestServiceImpl<ApplicationSupportServicesType> implements ApplicationSupportServicesTypeService {
    private final ApplicationSupportServicesTypeRepository applicationSupportServicesTypeRepository;
    private final ApplicationPreviousRequestsMapper applicationPreviousRequestsMapper;
    private final BPMCallerService bpmCallerService;
    private final LkApplicationCategoryService lkApplicationCategoryService;
    private final LkSupportServiceRequestStatusMapper lkSupportServiceRequestStatusMapper;

    @Autowired
    @Lazy
    private PublicationIssueService publicationIssueService;


    private ApplicationInstallmentService applicationInstallmentService;
    private final CustomerServiceCaller customerServiceCaller;
    @Autowired
    @Lazy
    private ApplicationPublicationService applicationPublicationService;

    public static final Long TM_CATEGORY_ID = 5L;

    public static final Long OPPOSITION_REQUEST_ID = 7L;
    public static final Long TRADEMARK_APPEAL_REQUEST_ID = 23L;
    public static final Long REVOKE_PRODUCTS_REQUEST_ID = 18L;

    @Override
    public SupportServiceRequestRepository getSupportServiceRequestRepository() {
        return applicationSupportServicesTypeRepository;
    }

    @Autowired
    public void setApplicationInstallmentService(ApplicationInstallmentService applicationInstallmentService) {
        this.applicationInstallmentService = applicationInstallmentService;
    }

    @Override
    public List<ApplicationSupportServicesType> getAllByApplicationId(Long appId) {
        return applicationSupportServicesTypeRepository.findByApplicationInfoId(appId);
    }


    @Override
    public ApplicationSupportServicesType insert(ApplicationSupportServicesType entity) {
        return super.insert(entity);
    }


    @Override
    public List<Long> getApplicationIdsForSpecificService(SupportServiceType serviceCode,Long customerId) {
        return applicationSupportServicesTypeRepository.getApplicationIdsForSpecificSupportService(serviceCode,customerId);
    }

    @Override
    public void validateApplicationSupportServicesTypeExists(Long id){
        if(!applicationSupportServicesTypeRepository.applicationSupportServicesTypeExists(id))
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, new String[]{id.toString()});
    }

    @Override
    public List<Long> getProcessRequestIdsBySearchCriteria(String query, String requestNumber) {
        List<Long> processRequestIds = applicationSupportServicesTypeRepository.getProcessRequestIdsBySearchCriteria(query, requestNumber);
        return processRequestIds.isEmpty() ? null : processRequestIds;
    }
    @Override
    public Long getApplicationIdByServiceNumber(String serviceNumber){
        return applicationSupportServicesTypeRepository.getApplicationIdByServiceNumber(serviceNumber);
    }

    @Override
    @Transactional
    public void paymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        log.info("start  installmentPaymentCallBackHandler {} " , id);
        ApplicationInstallment installment = applicationInstallmentService.installmentPaymentCallBackHandler(id, applicationNumberGenerationDto);

        //ApplicationInstallment installment = applicationInstallmentService.findById(id);
        if (installment == null) {
            return;
        }

        log.info("ApplicationInstallment with id {} " , installment.getId());

        ApplicationInfo application = installment.getApplication();

        ApplicationSupportServicesType request = applicationSupportServicesTypeRepository.getLastServiceForApplicationByServiceCode(application.getId(), RENEWAL_FEES_PAY);
        if (ApplicationCategoryEnum.TRADEMARK.name().equals(application.getCategory().getSaipCode())
            || ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name().equals(application.getCategory().getSaipCode())) {
            if(request != null && !validateSupportServiceRequest(request)){
                log.info("change Status with requestNumber ->> {}   " , request.getRequestNumber());
                //super.paymentCallBackHandler(request.getId(), applicationNumberGenerationDto);
                super.updatePaymentStatusAndRequestStatus(SupportServicePaymentStatus.PAID, SupportServiceRequestStatusEnum.RENEWED, request.getId());
            }

            log.info("start  trademark_renewal_publication_process {} " , application.getApplicationNumber());
            publicationIssueService.startPublicationInstallmentRenewalProcess(installment);
            log.info("End Start Process  trademark_renewal_publication_process {} " , application.getApplicationNumber());
        }
    }


    protected static boolean validateSupportServiceRequest(ApplicationSupportServicesType request) {
        return SupportServicePaymentStatus.PAID.equals(request.getPaymentStatus());
    }

    public ApplicationSupportServicesType getSupportServiceTypeById(Long id) {
        return applicationSupportServicesTypeRepository.findById(id).orElseThrow(() -> new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND));
    }

    @Override
    public SupportServiceRequestStatusEnum getPaymentRequestStatus() {
        return SupportServiceRequestStatusEnum.COMPLETED;
    }
    @Override
    public List<Long> findProcessesRequestsIdsByFilters(SearchDto searchDto){
        return applicationSupportServicesTypeRepository.findProcessesRequestsIdsByFilters(searchDto.getApplicationNumber(),
                                                                                        searchDto.getStartDate(),
                                                                                        searchDto.getEndDate(),
                                                                                        searchDto.getSupportServiceStatusCode(),
                                                                                        searchDto.getSupportServiceTypeId(),
                                                                                        searchDto.getCategoryId());
    }

    // get history
    @Override
    public PaginationDto<List<ApplicationPreviousRequestsDto>> getPreviousRequestsByFilter(Integer page, Integer limit, String query, SearchDto searchDto, boolean isInternal) {

        if (searchDto.getStartDate() != null && searchDto.getEndDate() != null
                && (searchDto.getStartDate().isAfter(searchDto.getEndDate())
                || searchDto.getEndDate().isBefore(searchDto.getStartDate()))) {
            throw new BusinessException(Constants.ErrorKeys.INCORRECT_DATE, HttpStatus.BAD_REQUEST, null);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(searchDto.getSortDirection(), "createdDate"));

        /*
        Long  customerId = request.getHeader(CUSTOMER_ID.getKey()) == null ? null : Long.valueOf(request.getHeader(CUSTOMER_ID.getKey()));
        SupportServicePaymentStatus status = isInternal ? SupportServicePaymentStatus.UNPAID : null;
        String userName = (String) util.getFromBasicUserinfo("userName");
        Page<ApplicationPreviousRequests> applicationInfoPages = applicationSupportServicesTypeRepository.getPreviousApplicationRequestsList(
                query, fromDate, toDate, applicationId, supportServiceTypeId,
                customerId, status, userName, pageable); */


        Page<ApplicationPreviousRequests> applicationInfoPages = getInternalOrExternalPageOfPrevRequests(query, searchDto, isInternal, pageable);
        addApplicationCategoriesToSupportServicesWithoutAppId(applicationInfoPages);
        List<ApplicationPreviousRequestsDto> dtos = applicationPreviousRequestsMapper.map(applicationInfoPages.getContent());
        setAppealedStatus(dtos);
        PaginationDto<List<ApplicationPreviousRequestsDto>> applicationInfo = new PaginationDto<>();
        applicationInfo.setContent(dtos);
        applicationInfo.setTotalPages(applicationInfoPages.getTotalPages());
        applicationInfo.setTotalElements(applicationInfoPages.getTotalElements());
        getPreviousRequestsTasks(dtos);
        return applicationInfo;
    }

    private void setAppealedStatus(List<ApplicationPreviousRequestsDto> applicationPreviousRequestsDtos) {
        List<String> appealingStatusCodes = List.of(
                SupportServiceRequestStatusEnum.REJECTED_BY_APPEAL_COMMITTEE.name(),
                SupportServiceRequestStatusEnum.ACCEPTED_BY_COMMITTEE.name(),
                SupportServiceRequestStatusEnum.COMPLAINANT_TO_COMMITTEE.name()
        );

        applicationPreviousRequestsDtos.forEach(dto -> {
            boolean isRejected = dto.getRequestStatus().getCode().equals(SupportServiceRequestStatusEnum.REJECTED.name());
            boolean isEditImageService = dto.getSupportServices().getCode().name().equals(SupportServiceType.EDIT_TRADEMARK_IMAGE.name());

            List<String> modifiedAppealingStatusCodes = new ArrayList<>(appealingStatusCodes); // Copy the list to modify
            if (isEditImageService) {
                modifiedAppealingStatusCodes.add(SupportServiceRequestStatusEnum.REJECTED.name());
            }

            boolean isAppealed = !applicationSupportServicesTypeRepository
                    .findByApplicationInfoIdAndRequestStatus(dto.getApplicationId(), modifiedAppealingStatusCodes, isEditImageService)
                    .isEmpty();
            dto.setIsAppealed((!isRejected || (isEditImageService && isAppealed)));
        });
    }

    private void addApplicationCategoriesToSupportServicesWithoutAppId(Page<ApplicationPreviousRequests> applicationInfoPages) {
        applicationInfoPages.forEach(category -> {
         if (category.getSupportServices().getCode().name().equals(SupportServiceType.TRADEMARK_APPLICATION_SEARCH.name())) {
         category.setApplicationCategory(lkApplicationCategoryService.findBySaipCode(TRADEMARK.name()));
         } else if (category.getSupportServices().getCode().name().equals(SupportServiceType.PETITION_REQUEST_NATIONAL_STAGE.name())){
             category.setApplicationCategory(lkApplicationCategoryService.findBySaipCode(PATENT.name()));
         }
      });
    }
    private Page<ApplicationPreviousRequests> getInternalOrExternalPageOfPrevRequests(String query, SearchDto searchDto, boolean isInternal, Pageable pageable) {
        if (isTMApplicationNumber(query)
                && (searchDto.getCategoryId() != null && Objects.equals(searchDto.getCategoryId(), TM_CATEGORY_ID))
                && (searchDto.getSupportServiceTypeId() != null
                && (Objects.equals(searchDto.getSupportServiceTypeId(), OPPOSITION_REQUEST_ID) || Objects.equals(searchDto.getSupportServiceTypeId(), TRADEMARK_APPEAL_REQUEST_ID)))
                ) {
            throw new BusinessException(Constants.ErrorKeys.CANT_SEARCH_FOR_THIS_SERVICE_WITH_APPLICATION_NUMBER_USE_REQUEST_NUMBER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Page<ApplicationPreviousRequests>  applicationPreviousRequestsPage;
        if (isInternal) {
            List<Long> customerIds = null;
            if (!Strings.isNullOrEmpty(searchDto.getAgentName())){
                customerIds = customerServiceCaller.getCustomersIds(searchDto.getAgentName());
            }

            List<String> statusCodeList = null;
            if (!Strings.isNullOrEmpty(searchDto.getSupportServiceStatusCode())) {
                statusCodeList = new ArrayList<>();
                if (Objects.equals(searchDto.getSupportServiceTypeId(), REVOKE_PRODUCTS_REQUEST_ID)) {
                    switch (searchDto.getSupportServiceStatusCode()) {
                        case "PAY_APPLYING_PUBLICATION_FEES" -> statusCodeList.add("APPROVED");
                        case "ACCEPTANCE" -> statusCodeList.add("COMPLETED");
                    }
                } else if (Objects.equals(searchDto.getSupportServiceTypeId(), OPPOSITION_REQUEST_ID)) {
                    switch (searchDto.getSupportServiceStatusCode()) {
                        case "UNDER_TRADEMARK_STUDY" -> statusCodeList.add("UNDER_PROCEDURE");
                        case "OPPOSITION_REJECTED" -> statusCodeList.add("REJECTED");
                        case "OPPOSITION_ACCEPTED" -> statusCodeList.add("COMPLETED");
                    }
                } else {
                    statusCodeList.add(searchDto.getSupportServiceStatusCode());
                }
            }

            applicationPreviousRequestsPage = applicationSupportServicesTypeRepository.getInternalPreviousRequest(
                    query, searchDto.getStartDate(), searchDto.getEndDate(), searchDto.getApplicationId(), searchDto.getSupportServiceTypeId(),
                    SupportServicePaymentStatus.UNPAID, searchDto.getCategoryId(), searchDto.getApplicationNumber(), searchDto.getOwnerName(),
                    searchDto.getAgentName(), searchDto.getApplicationTitleAr(), searchDto.getApplicationTitleEn(), statusCodeList, customerIds, pageable);


        } else {
            String  currentCustomerCode = Utilities.getCustomerCodeFromHeaders();

            applicationPreviousRequestsPage = applicationSupportServicesTypeRepository.getExternalPreviousRequest(
                    query, Utilities.getFirstSecondOfDay(searchDto.getStartDate()),Utilities.getLastSecondOfDay(searchDto.getEndDate()) ,
                    searchDto.getApplicationId(), searchDto.getSupportServiceTypeId(), currentCustomerCode, searchDto.getCategoryId(), ApplicationCustomerType.MAIN_OWNER, pageable);
        }

        applicationPreviousRequestsPage.forEach(applicationPreviousRequest -> {
            if(applicationPreviousRequest.getRevokeLicenseId() != null) {
                applicationPreviousRequest.setOppositionId(applicationPreviousRequest.getId());
                applicationPreviousRequest.setId(applicationPreviousRequest.getRevokeLicenseId());
            }
        });
        return applicationPreviousRequestsPage;
    }

    static boolean isTMApplicationNumber(String applicationNumber) {
        return !Strings.isNullOrEmpty(applicationNumber) && (applicationNumber.startsWith("TM") || applicationNumber.startsWith("tm"));
    }

    private void getPreviousRequestsTasks(List<ApplicationPreviousRequestsDto> applicationPreviousRequests) {

        if (applicationPreviousRequests.isEmpty())
            return;

        List<Long> applicationSupportServicesIds = applicationPreviousRequests
                .stream()
                .filter(applicationPreviousRequest ->
                        SUPPORT_SERVICES_HAVE_TASKS.contains(applicationPreviousRequest.getSupportServices().getCode().toString()))
                .map(ApplicationPreviousRequestsDto::getId)
                .collect(Collectors.toList());

        if(applicationSupportServicesIds.isEmpty()) return;

        List<RequestTasksDto> requestTasks = bpmCallerService.getTaskByRowsIds(applicationSupportServicesIds);
        if (requestTasks.isEmpty())
            return;

        applicationPreviousRequests.forEach(applicationPreviousRequest -> {
            Optional<RequestTasksDto> task =
                    requestTasks.stream().filter(requestTask -> requestTask.getRowId().equals(applicationPreviousRequest.getId())).findFirst();
            task.ifPresent(requestTask -> applicationPreviousRequest.setTask(task.get()));
        });

    }

    @Override
    public List<ChangeOwnerShipReportDTO> getOwnerShipChangedData(LocalDateTime startDate, LocalDateTime endDate) {
        return applicationSupportServicesTypeRepository.getOwnerShipChangedData(startDate, endDate);
    }

    @Override
    public Long getLastSupportServiceByTypeAndApplicationِAndStatus(SupportServiceType type, Long appId, List<String> status) {
        return applicationSupportServicesTypeRepository.getLastSupportServiceByTypeAndApplicationِAndStatus(type, appId, status);
    }

    @Override
    @Transactional
    public void initiateExamination(Long id) {
        applicationSupportServicesTypeRepository.updatePaymentStatusAndRequestStatus(id, SupportServiceRequestStatusEnum.UNDER_PROCEDURE.name());
    }

 	@Override
    public String getSupportServicesRequestNumber(Long supportServiceId) {
        return applicationSupportServicesTypeRepository.getSupportServiceRequestNumberCodeByServiceId(supportServiceId);
    }
    @Override
    public Long insertRenewalFeesRequest(ApplicationSupportServicesType entity) {
        ApplicationSupportServicesType lastServiceForApplicationByServiceCode = applicationSupportServicesTypeRepository.getLastServiceForApplicationByServiceCode(entity.getApplicationInfo().getId(), RENEWAL_FEES_PAY);
        if (lastServiceForApplicationByServiceCode != null && !SupportServicePaymentStatus.PAID.equals(lastServiceForApplicationByServiceCode.getPaymentStatus())) {
            return lastServiceForApplicationByServiceCode.getId();
        }
        Integer statusId = lKSupportServiceRequestStatusService.findIdByCode(SupportServiceRequestStatusEnum.PENDING_RENEWAL_FEES);
        entity.setRequestStatus(new LKSupportServiceRequestStatus(statusId));
        entity.setPaymentStatus(SupportServicePaymentStatus.UNPAID);
        ApplicationSupportServicesType inserted = super.insert(RENEWAL_FEES_PAY, entity);
        applicationInstallmentService.setInstallmentSupportService(inserted, InstallmentType.RENEWAL);
        return inserted.getId();
    }

    @Override
    public Long getLastSupportServiceRequestServiceId(Long appId, SupportServiceType type) {
        return applicationSupportServicesTypeRepository.getLastSupportServiceRequestServiceId(appId, type);
    }
    @Override
    public boolean applicationSupportServicesTypeLicencedExists(Long appId){
        return applicationSupportServicesTypeRepository.applicationSupportServicesTypeLicencedExists(appId);

    }

    @Override
    public SupportServiceType getServiceTypeByServiceId(Long id) {
        return applicationSupportServicesTypeRepository.getServiceTypeByServiceId(id);
    }

    @Override
    public List<ApplicationSupportServicesType> getSupportServiceByAppIdAndStatusAndTypeAndCustomerCode(Long appId, SupportServiceRequestStatusEnum status, SupportServiceType type, String customerCode) {
        String customerId = Utilities.getCustomerIdFromHeaders();
        return applicationSupportServicesTypeRepository.findByApplicationInfoIdAndRequestStatusAndTypeAndCustomerCode(appId, status.name(), type, customerCode,Long.valueOf(customerId));
    }

    @Override
    public List<ApplicationSupportServicesType> hasLicensingModificationAndOwnerShipPermission(Long appId, SupportServiceRequestStatusEnum status, SupportServiceType type, String customerCode) {
        String customerId = Utilities.getCustomerIdFromHeaders();
        return applicationSupportServicesTypeRepository.hasLicensingModificationAndOwnerShipPermission(appId, status.name(), type, customerCode,Long.valueOf(customerId));
    }

    @Override
    public LkSupportServiceRequestStatusDto getSupportServiceStatus(Long id){
        return lkSupportServiceRequestStatusMapper.map(customFindById(id).getRequestStatus());
    }

    @org.springframework.transaction.annotation.Transactional
    public void createInstallmentApplicationPublication(ApplicationInstallment applicationInstallment, String publicationType) {

        if (applicationInstallment == null) {
            return;
        }
        ApplicationInfo application = applicationInstallment.getApplication();


        if (publicationType == null) {
            return;
        }

        applicationPublicationService.createApplicationPublication(
                application,
                application.getApplicationStatus().getCode(),
                publicationType,
                null,
                null
        );
    }

    @Override
    public String getSupportServiceStatusCode(Long serviceId) {
        return applicationSupportServicesTypeRepository.getServiceTypeStatus(serviceId);
    }

    @Override
    public SupportServiceHelperInfoDto getCreatedByCustomerCodeAndApplicationIdById(Long serviceId) {
        return getSupportServiceRequestRepository().getCreatedByCustomerCodeAndApplicationIdById(serviceId);
    }
}
