package gov.saip.applicationservice.common.service.installment.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ApplicationNumberGenerationDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.ListBodyDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.installment.*;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.mapper.installment.ApplicationInstallmentMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.model.annual_fees.LkAnnualRequestYears;
import gov.saip.applicationservice.common.model.annual_fees.LkPostRequestReasons;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentConfigService;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentHelper;
import gov.saip.applicationservice.common.service.patent.PctService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.JsonUtils;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationInstallmentServiceImpl extends BaseServiceImpl<ApplicationInstallment, Long> implements ApplicationInstallmentService {

    private final static int MAX_PAID_ANNUAL_FEES_THE_CUSTOMER_CAN_POSTPONED_FEES_IF_NOT_GRANTED = 3;

    private final ApplicationInstallmentRepository applicationInstallmentRepository;
    private final ApplicationInstallmentConfigService applicationInstallmentConfigService;
    private InstallmentNotificationService notificationService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationInstallmentMapper applicationInstallmentMapper;
    private ApplicationInfoService applicationInfoService;
    private final PctService pctService;
    @Autowired
    public void setApplicationInstallmentService(@Lazy ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }
    public static boolean IGNORE_AUTOMATIC_PAYMENT_CALLBACK = false;
    public static boolean IGNORE_ANNUAL_MONTHS_VALIDATION = false;
    public static Map<Long, ApplicationNumberGenerationDto> HOLDDED_CALLBACK = new HashMap<>();

    @Autowired
    public void setNotificationService(InstallmentNotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @Override
    protected BaseRepository<ApplicationInstallment, Long> getRepository() {
        return applicationInstallmentRepository;
    }

    @Override
    public PaginationDto<List<InstallmentNotificationProjectionDto>> filterApplicationInstallments(InstallmentNotificationStatus status
            , InstallmentStatus installmentStatus, String applicationNumber, Integer page, Integer limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Long applicationId = Utilities.isLong(applicationNumber);
        Page<InstallmentNotificationProjection> notificationsByStatus = applicationInstallmentRepository.filterApplicationInstallments(status, installmentStatus, applicationNumber, applicationId, pageable);
        Map<Long, CustomerSampleInfoDto> customersByIds = getCustomersDetails(notificationsByStatus);
        List<InstallmentNotificationProjectionDto> list = addCustomerDetailsToNotificationList(notificationsByStatus, customersByIds);

        return PaginationDto.<List<InstallmentNotificationProjectionDto>>builder().
                totalElements(notificationsByStatus.getTotalElements()).
                content(list).
                totalPages(notificationsByStatus.getTotalPages()).
                build();
    }


    private List<InstallmentNotificationProjectionDto> addCustomerDetailsToNotificationList(Page<InstallmentNotificationProjection> notificationsByStatus, Map<Long, CustomerSampleInfoDto> customersByIds) {
        List<InstallmentNotificationProjectionDto> list = notificationsByStatus.stream().map(not -> {
            InstallmentNotificationProjectionDto installmentNotificationProjectionDtos = applicationInstallmentMapper.mapInstallmentNotificationProjection(not);
            CustomerSampleInfoDto customerSampleInfoDto = customersByIds.get(not.getCustomerId());
            installmentNotificationProjectionDtos.setCustomerCode(customerSampleInfoDto.getCode());
            installmentNotificationProjectionDtos.setCustomerName(customerSampleInfoDto.getNameAr());
            installmentNotificationProjectionDtos.setCustomerNameEn(customerSampleInfoDto.getNameEn());
            return installmentNotificationProjectionDtos;
        }).toList();
        return list;
    }

    private Map<Long, CustomerSampleInfoDto> getCustomersDetails(Page<InstallmentNotificationProjection> notificationsByStatus) {
        List<Long> ids = notificationsByStatus.getContent().stream().map(not -> not.getCustomerId()).toList();
        Map<Long, CustomerSampleInfoDto> customersByIds = customerServiceCaller.getCustomersByIds(new ListBodyDto<Long>(ids));
        return customersByIds;
    }

//    @Transactional
//    public void protectionRenewalFeesPaymentCallBack(Long applicationInfoId, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
//        ApplicationInstallment installment = getInstallmentByAppIdAndStatus(applicationInfoId, InstallmentStatus.PAID);
//        notificationService.resetLastNotification(installment.getId());
//        ApplicationInstallmentConfig installmentConfig = getInstallmentConfig(installment);
//        handlePayInstallment(applicationNumberGenerationDto, applicationInfoId, installment, installmentConfig);
//    }

    /**
     * user can pay many annual fees at the same request
     *
     * @param annualFeesRequest
     * @param applicationNumberGenerationDto
     */
    @Override
    @Transactional
    public void annualFeesPaymentCallBackHandler(AnnualFeesRequest annualFeesRequest, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        validateFeeRequest(annualFeesRequest);
        Long applicationId = annualFeesRequest.getApplicationInfo().getId();
        ApplicationInstallment installment = getInstallmentByAppIdAndStatus(applicationId, InstallmentStatus.PAID);
        LkAnnualRequestYears annualRequestYears = annualFeesRequest.getAnnualRequestYears();
        ApplicationInstallmentConfig installmentConfig = getInstallmentConfig(installment);
        if (annualRequestYears == null) {
            handlePayInstallment(applicationNumberGenerationDto, applicationId, installment, installmentConfig);
        } else {
            handlePayMultiAnnualYears(applicationNumberGenerationDto, applicationId, installment, annualRequestYears, installmentConfig);
        }

        notificationService.resetLastNotification(installment.getId());
    }

    private void handlePayInstallment(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long applicationId, ApplicationInstallment installment, ApplicationInstallmentConfig installmentConfig) {
        makeLastInstallmentPaid(installment, applicationNumberGenerationDto);
        log.info("installmentPaymentCallBackHandler makeLastInstallmentPaid installment status {} " , installment.getInstallmentStatus());
        insertNextInstallment(installment, installmentConfig);
        log.info("installmentPaymentCallBackHandler insertNextInstallment installment Id {} " , installment.getId());
        applicationInfoService.extendProtectionPeriodByAppId(applicationId, installmentConfig.getPaymentIntervalYears());
        log.info("installmentPaymentCallBackHandler extendProtectionPeriodByAppId installment Id {} " , installmentConfig.getPaymentIntervalYears());
    }

    private void handlePayMultiAnnualYears(ApplicationNumberGenerationDto applicationNumberGenerationDto, Long applicationId, ApplicationInstallment installment, LkAnnualRequestYears annualRequestYears, ApplicationInstallmentConfig installmentConfig) {
        int yearsCount = annualRequestYears.getYearsCount();
        ApplicationInstallment tempInstallment = installment;
        for (int i = 0; i < yearsCount; i++) {
            makeLastInstallmentPaid(tempInstallment, applicationNumberGenerationDto);
            tempInstallment = insertNextInstallment(tempInstallment, installmentConfig);
        }

        applicationInfoService.extendProtectionPeriodByAppId(applicationId, installmentConfig.getPaymentIntervalYears() * yearsCount);
    }


    private static void validateFeeRequest(AnnualFeesRequest annualFeesRequest) {
        if (SupportServicePaymentStatus.PAID.equals(annualFeesRequest.getPaymentStatus())) {
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND);
        }
    }

    private ApplicationInstallment insertNextInstallment(ApplicationInstallment lastPaidInstallment, ApplicationInstallmentConfig config) {
        ApplicationInstallment newInstallment = InstallmentHelper.getInstallmentHelper(config.getInstallmentType()).createNextInstallment(lastPaidInstallment, config);
        if (newInstallment == null) {
            return null;
        }
        return applicationInstallmentRepository.save(newInstallment);
    }

    private ApplicationInstallmentConfig getInstallmentConfig(ApplicationInstallment lastPaidInstallment) {
        String saipCode = lastPaidInstallment.getApplication().getCategory().getSaipCode();
        ApplicationInstallmentConfig config = applicationInstallmentConfigService.findByApplicationCategory(saipCode);
        return config;
    }

    private void makeLastInstallmentPaid(ApplicationInstallment installment, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        installment.setPaymentDate(applicationNumberGenerationDto.getPaymentDate());
        installment.setInstallmentStatus(InstallmentStatus.PAID);
        installment.setBillNumber(applicationNumberGenerationDto.getBillNumber());
        installment.setFeeCost(BigDecimal.valueOf(applicationNumberGenerationDto.getTotalCost()));
        installment.setPenaltyAmount(BigDecimal.valueOf(applicationNumberGenerationDto.getTotalPenaltyCost()));
        installment.setTaxCost(BigDecimal.valueOf(applicationNumberGenerationDto.getTotalTaxCost()));
        applicationInstallmentRepository.save(installment);
    }

    @Override
    public void postponedInstallment(ApplicationInfo applicationInfo, LkPostRequestReasons postRequestReason) {
        ApplicationInstallment installment = applicationInstallmentRepository.getByApplicationId(applicationInfo.getId())
                .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND));

        if (installment.getInstallmentIndex() <= 3) {
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_CAN_NOT_POSTPONED);
        }

        installment.setPostponedReason(postRequestReason);
        installment.setInstallmentStatus(InstallmentStatus.POSTPONED);
        applicationInstallmentRepository.save(installment);
    }

    @Override
    public ApplicationInstallment getUnpaidInstallmentForGivenApplicationId(Long appId) {
        return getInstallmentByAppIdAndStatus(appId, InstallmentStatus.PAID);
    }

    @Override
    public LocalDateTime getLastDueDate(Long appId, InstallmentStatus installmentStatus) {
        LocalDateTime date= applicationInstallmentRepository.getApplicationInstallmentDueDate(appId,installmentStatus);
        if(date!=null)
        {
            LocalDate localDate = date.toLocalDate();
            LocalTime timeOfDay = LocalTime.of(12, 0, 0); // 12:00:00

            return localDate.atTime(timeOfDay);
        }
        return null;
    }

    private ApplicationInstallment getInstallmentByAppIdAndStatus(Long appId, InstallmentStatus status) {
        return applicationInstallmentRepository.getUnpaidInstallmentByAppId(appId, status)
                .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND));
    }

    @Override
    public InstallmentStatusBannerDto getInstallmentConfigAndStatus(Long applicationId) {
        ApplicationInstallmentLightProjection installmentPro = applicationInstallmentRepository.getInstallmentStatusByApplicationId(applicationId);

        if (installmentPro == null) {
            return new InstallmentStatusBannerDto();
        }

        int currentStatusDuration = getCurrentStatusDuration(installmentPro.getApplicationCategoryEnum(), installmentPro.getInstallmentStatus());
        if (currentStatusDuration == 0) {
            InstallmentStatusBannerDto installmentStatusBannerDto = new InstallmentStatusBannerDto();
            setInstallmentPostponedValue(installmentPro, installmentStatusBannerDto);
            return installmentStatusBannerDto;
        }
        InstallmentStatusBannerDto installmentStatusBannerDto = applicationInstallmentMapper.mapApplicationInstallmentLightProjectionToBanner(installmentPro);
        installmentStatusBannerDto.setMonths(currentStatusDuration);

        Long paymentDeadlineMonths = ChronoUnit.DAYS.between(LocalDateTime.now(), installmentStatusBannerDto.getEndDueDate());
        installmentStatusBannerDto.setPaymentDeadlineDays(paymentDeadlineMonths);

        setInstallmentPostponedValue(installmentPro, installmentStatusBannerDto);

        return installmentStatusBannerDto;
    }

    private static void setInstallmentPostponedValue(ApplicationInstallmentLightProjection installmentPro, InstallmentStatusBannerDto installmentStatusBannerDto) {
        if (installmentPro.getInstallmentIndex() != null && installmentPro.getInstallmentIndex() > MAX_PAID_ANNUAL_FEES_THE_CUSTOMER_CAN_POSTPONED_FEES_IF_NOT_GRANTED) {
            installmentStatusBannerDto.setPostponed(true);
        }
    }

    @Override
    public Integer getLastIndexByAppAndStatus(Long appId, InstallmentStatus installmentStatus) {
        return applicationInstallmentRepository.getLastIndexByAppAndStatus(appId, installmentStatus);
    }
    private int getCurrentStatusDuration(String applicationCategory, InstallmentStatus installmentStatus) {
        ApplicationInstallmentConfig config = applicationInstallmentConfigService.findByApplicationCategory(applicationCategory);

        switch (installmentStatus) {
            case DUE -> {
                return config.getPaymentDuration();
            }
            case DUE_OVER -> {
                return config.getGraceDuration();
            }
            default -> {
                return 0;
            }
        }

    }


    @Override
    @Transactional
    public void insertFirstInstallmentForGivenApplications(ApplicationInfo application) {
        ApplicationInstallment installment = createFirstInstallmentAfterApproval(application);
        if (installment != null) {
            super.insert(installment);
        }
    }

    @Override
    @Transactional
    public Long createAndSaveFirstAnnualFees(Long appId) {
        ApplicationInfo applicationInfo = applicationInfoService.findById(appId);
        if (isApplicationInValidToBeInsertedAsAnnualFee(applicationInfo) || applicationInstallmentRepository.existsByApplicationId(appId)) {
            return 0L;
        }
        ApplicationInstallmentConfig config = applicationInstallmentConfigService.findByApplicationCategory(applicationInfo.getCategory().getSaipCode());
        ApplicationInstallment annualFeeRecord = createAnnualFeeRecord(applicationInfo, config);
        ApplicationInstallment installment = applicationInstallmentRepository.save(annualFeeRecord);
        return installment.getId();
    }


    private ApplicationInstallment createFirstInstallmentAfterApproval(ApplicationInfo application) {
        // insert next installment after approval
        // patent should be inserted after filing the request, so it's excluded here
        // trademark start from filing date, now approval date
        if (isApplicationInvalidToBeInsertedAsInstallment(application) || applicationInstallmentRepository.existsByApplicationId(application.getId())) {
            log.info("application with id {} does not meet installment criteria to insert the first installment ", application.getId());
            return null;
        }

        ApplicationInstallmentConfig config = applicationInstallmentConfigService.findByApplicationCategory(application.getCategory().getSaipCode());
        return createInstallmentUsingConfiguration(application, config);
    }

    private ApplicationInstallment createInstallmentUsingConfiguration(ApplicationInfo application, ApplicationInstallmentConfig config) {
        ApplicationInstallment installment = new ApplicationInstallment();
        InstallmentHelper.getInstallmentHelper(config.getInstallmentType()).setFirstInstallmentDates(installment, config, application);
        installment.setApplication(application);
        installment.setInstallmentStatus(InstallmentStatus.NEW);
        installment.setInstallmentType(config.getInstallmentType());
        installment.setInstallmentIndex(1);
        return installment;
    }

    private static boolean isApplicationInvalidToBeInsertedAsInstallment(ApplicationInfo application) {

        String applicationStatusCode = application.getApplicationStatus().getCode();
        String applicationCategory = application.getCategory().getSaipCode();

        boolean isAcceptedOrTrademarkRegistered = ACCEPTANCE.name().equals(applicationStatusCode)
                || THE_TRADEMARK_IS_REGISTERED.name().equals(applicationStatusCode)
                || WAITING_TO_PAY_TRADEMARK_REGISTRATION_FEES.name().equals(applicationStatusCode);

        boolean isNotPatentCategory = !ApplicationCategoryEnum.PATENT.name().equals(applicationCategory);

        boolean isInstallmentValidToInsert = isAcceptedOrTrademarkRegistered && isNotPatentCategory;

        return !isInstallmentValidToInsert;
    }
    private boolean isApplicationInValidToBeInsertedAsAnnualFee(ApplicationInfo application) {
        return (! ApplicationCategoryEnum.PATENT.equals(ApplicationCategoryEnum.valueOf(application.getCategory().getSaipCode())));
    }
    private ApplicationInstallment createAnnualFeeRecord(ApplicationInfo application, ApplicationInstallmentConfig config) {
        ApplicationInstallment installment = new ApplicationInstallment();
        installment.setApplication(application);
        installment.setLastDueDate(LocalDateTime.now());
        installment.setInstallmentStatus(InstallmentStatus.NEW);
        installment.setInstallmentType(config.getInstallmentType());
        int installmentForPatent = getInstallmentCountForPatent(application);
        installment.setInstallmentIndex(installmentForPatent);
        installment.setInstallmentCount(installmentForPatent);
        InstallmentHelper.getInstallmentHelper(config.getInstallmentType()).setFirstInstallmentDates(installment, config, application);
        return installment;
    }

    private int getInstallmentCountForPatent(ApplicationInfo application) {
        /**
            PLT or Partial of PLT ==> will start to pay from filing date
            PCT or Partial of PCT ==> will start to pay from international filing date
         **/
        LocalDate filingDate;
        boolean isPartialApplication = application.getPartialApplication() != null && application.getPartialApplication();
        boolean isPltAndNotPartialApplication = application.getPltRegisteration() && !isPartialApplication;

        if (isPltAndNotPartialApplication) {
            return 1;
        } else if (isPartialApplication) {
            filingDate = pctService.getFilingDateByApplicationNumber(application.getPartialApplicationNumber());
        } else {
            filingDate = pctService.getFilingDateByApplicationNumber(application.getApplicationNumber());
        }

        // in case it is partial and main app not PCT the filing date will be null
        // PLT & partial of PLT
        if (filingDate == null) {
            return 1;
        }

        return LocalDate.now().getYear() - filingDate.getYear() + 1;
    }

    @SneakyThrows
    @Transactional
    public ApplicationInstallment installmentPaymentCallBackHandler(Long id, ApplicationNumberGenerationDto applicationNumberGenerationDto) {
        log.info("start  installmentPaymentCallBackHandler IGNORE_AUTOMATIC_PAYMENT_CALLBACK {} " , IGNORE_AUTOMATIC_PAYMENT_CALLBACK);
        if (IGNORE_AUTOMATIC_PAYMENT_CALLBACK) {
            HOLDDED_CALLBACK.put(id, applicationNumberGenerationDto);
            return null;
        }
        Optional<ApplicationInstallment> installmentOpt = applicationInstallmentRepository.getInstallmentById(id);
        ApplicationInstallment installment = installmentOpt.orElseThrow(() -> new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND));
        log.info("installmentPaymentCallBackHandler installmentId {} " , installment.getId());
        log.info("find  {} " , IGNORE_AUTOMATIC_PAYMENT_CALLBACK);
        String message = handleNotDueNorOverDueInstallments(applicationNumberGenerationDto, installment);
        log.info("installmentPaymentCallBackHandler message {} " , message);
        if (message != null) {
            applicationInstallmentRepository.logInstallmentExceptionMessage(message, installment.getId());
            return installment;
        }
        ApplicationInstallmentConfig installmentConfig = getInstallmentConfig(installment);
        log.info("installmentPaymentCallBackHandler installmentConfig Id {} " , installmentConfig.getId());
        handlePayInstallment(applicationNumberGenerationDto, installment.getApplication().getId(), installment, installmentConfig);

        notificationService.resetLastNotification(installment.getId());
        return installment;
    }

    private static String handleNotDueNorOverDueInstallments(ApplicationNumberGenerationDto applicationNumberGenerationDto, ApplicationInstallment installment) throws JsonProcessingException {
        boolean isDueOrOverDue = InstallmentStatus.DUE.equals(installment.getInstallmentStatus()) || InstallmentStatus.DUE_OVER.equals(installment.getInstallmentStatus());

        if (!isDueOrOverDue) {
         Map<String, Object>  logMap = new LinkedHashMap<>();
         logMap.put("Title", "Installment CallBack Done For Invalid Status");
         logMap.put("Date", String.valueOf(LocalDateTime.now()));
         logMap.put("Status", installment.getInstallmentStatus());
         logMap.put("CallbackDto", applicationNumberGenerationDto);
         return JsonUtils.convertToJSONString(logMap);
        }

        return null;
    }

    public void isRenewalOpenedForGivenApplication(ApplicationInfo applicationInfo) {
        ApplicationInstallment lastInstallment = applicationInstallmentRepository.getMostRecentInstallmentByApplicationId(applicationInfo.getId());
        if (lastInstallment == null || lastInstallment.getInstallmentStatus().equals(InstallmentStatus.NEW) || lastInstallment.getInstallmentStatus().equals(InstallmentStatus.PAID)) {
            throw new BusinessException(Constants.ErrorKeys.RENEWAL_FEE_NOT_DUE);
        } else if (lastInstallment.getInstallmentStatus().equals(InstallmentStatus.EXPIRED)) {
            throw new BusinessException(Constants.ErrorKeys.RENEWAL_FEE_EXPIRED);
        }
    }

    @Override
    public ApplicationInstallmentDatesDto getLastInstallmentInfoByAppId(Long applicationId) {
        ApplicationInstallment inst = applicationInstallmentRepository.getMostRecentInstallmentByApplicationId(applicationId);
        return ApplicationInstallmentDatesDto.builder()
                .lastDueDate(inst.getLastDueDate())
                .startDueDate(inst.getStartDueDate())
                .endDueDate(inst.getEndDueDate())
                .graceEndDate(inst.getGraceEndDate())
                .applicationId(applicationId)
                .build();
    }

    @Override
    @SneakyThrows
    @Transactional
    public String resumeHoledCallBack(Long applicationId) {
        ApplicationInstallment installment = applicationInstallmentRepository.getMostRecentInstallmentByApplicationId(applicationId);
        if (installment == null) {
            return "there is no installment for this application !!";
        }

        ApplicationNumberGenerationDto applicationNumberGenerationDto = HOLDDED_CALLBACK.get(installment.getId());
        if (applicationNumberGenerationDto == null) {
            return "the callback does not occurred to resume it or it is resumed before !!";
        }

        String message = handleNotDueNorOverDueInstallments(applicationNumberGenerationDto, installment);
        if (message != null) {
            applicationInstallmentRepository.save(installment);
            HOLDDED_CALLBACK.remove(installment.getId());
            return "you trying to resume paid or expired installment, you can not resume this again !!";
        }

        ApplicationInstallmentConfig installmentConfig = getInstallmentConfig(installment);
        handlePayInstallment(applicationNumberGenerationDto, installment.getApplication().getId(), installment, installmentConfig);
        notificationService.resetLastNotification(installment.getId());
        HOLDDED_CALLBACK.remove(installment.getId());
        return "callback resumed successfully, you can not resume it again";
    }

    @Override
    public String updateInstallmentDates(ApplicationInstallmentDatesDto dto) {
        ApplicationInstallment inst = applicationInstallmentRepository.getMostRecentInstallmentByApplicationId(dto.getApplicationId());
        if (inst.getInstallmentStatus().equals(InstallmentStatus.EXPIRED)) {
            return "you can not change dates because you exceeded payment and grace durations";
        }
        inst.setLastDueDate(dto.getLastDueDate());
        inst.setStartDueDate(dto.getStartDueDate());
        inst.setEndDueDate(dto.getEndDueDate());
        inst.setGraceEndDate(dto.getGraceEndDate());
        applicationInstallmentRepository.save(inst);
        return "dates has changed successfully";
    }


    @Override
    public void ignoreAutomaticPaymentCallback(boolean ignore) {
        IGNORE_AUTOMATIC_PAYMENT_CALLBACK = ignore;
    }

    @Override
    public void ignoreAnnualMonths(boolean ignore) {
        IGNORE_ANNUAL_MONTHS_VALIDATION = ignore;
    }

    @Transactional
    @Override
    public void setInstallmentSupportService(ApplicationSupportServicesType supportServicesType, InstallmentType installmentType) {
        ApplicationInstallment installment = applicationInstallmentRepository.getMostRecentInstallmentByApplicationIdAndType(supportServicesType.getApplicationInfo().getId(), installmentType);
        boolean isDueOrOverDue = installment.getInstallmentStatus() == null ? false : InstallmentStatus.DUE.equals(installment.getInstallmentStatus()) || InstallmentStatus.DUE_OVER.equals(installment.getInstallmentStatus());
        if (!isDueOrOverDue) {
            throw new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND);
        }
        installment.setSupportService(supportServicesType);
        applicationInstallmentRepository.save(installment);
    }

    @Override
    @Transactional
    public void removeAnnualFeeInstallmentsAfterApplicationRejection(Long applicationId) {
        applicationInstallmentRepository.updateLastInstallmentStatusByApplicationId(applicationId, InstallmentType.ANNUAL, InstallmentStatus.DELETED_BECAUSE_OF_APP_REJECTION);
    }

    @Override
    @Transactional
    public void cancelAnnualFeePostponement(Long applicationId) {
        ApplicationInstallment installment = applicationInstallmentRepository.getLastInstallmentByAppAndTypeAndStatus(applicationId, InstallmentType.ANNUAL, InstallmentStatus.POSTPONED);
        if (installment == null) {
            return;
        }

        int countOfPostponedYears = LocalDate.now().getYear() - installment.getLastDueDate().getYear();

        installment.setLastDueDate(installment.getLastDueDate().plusYears(countOfPostponedYears));
        installment.setStartDueDate(installment.getStartDueDate().plusYears(countOfPostponedYears));
        installment.setEndDueDate(installment.getEndDueDate().plusYears(countOfPostponedYears));
        installment.setGraceEndDate(installment.getGraceEndDate().plusYears(countOfPostponedYears));

        installment.setInstallmentIndex(installment.getInstallmentIndex() + countOfPostponedYears);
        installment.setInstallmentStatus(InstallmentStatus.NEW);

        int postponedYearsIncludingNextYear = countOfPostponedYears + 1;
        installment.setInstallmentCount(postponedYearsIncludingNextYear);

        applicationInstallmentRepository.save(installment);
    }

}
