package gov.saip.applicationservice.common.service.installment.processor;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.NotificationDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
import gov.saip.applicationservice.common.dto.installment.InstallmentNotificationProjection;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.payment.BillCreationDto;
import gov.saip.applicationservice.common.dto.payment.BillResponseModel;
import gov.saip.applicationservice.common.dto.payment.FeeCostDto;
import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationType;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.model.installment.InstallmentConfigType;
import gov.saip.applicationservice.common.model.installment.InstallmentNotification;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentRepository;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
import gov.saip.applicationservice.exception.AppErrorDecoderException;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.*;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION;


@Slf4j
public abstract class InstallmentProcessor {

    // cache date during processing
    protected static final Map<String, ApplicantsDto> APPLICANTS = new HashMap<>();
    protected static final Map<String, String> CUSTOMER_GROUPS = new HashMap<>();
    @Autowired
    protected TransactionTemplate transactionTemplate;

    protected abstract ApplicationInstallmentRepository getApplicationInstallmentRepository();
    protected abstract NotificationCaller getNotificationCaller();
    protected abstract InstallmentNotificationService getInstallmentNotificationService();
    protected abstract ApplicationInfoService getApplicationInfoService();
    protected abstract CustomerServiceCaller getCustomerServiceCaller();
    protected abstract PaymentFeeCostFeignClient getPaymentFeeCostFeignClient();
    protected abstract ApplicationCustomerService getApplicationCustomerService();
    protected abstract InstallmentNotificationType getNotificationType();
    protected abstract List<InstallmentStatus> getInstallmentStatusList();

    public void process(ApplicationInstallmentConfig applicationInstallmentConfig) {
        PageRequest page;
        InstallmentConfigType configType = getConfigType(applicationInstallmentConfig, getNotificationType());
        ApplicationInstallmentDatesDto dates = getDatesToFetchPageToProcess(applicationInstallmentConfig);
        Long firstIdInPrevPage = 0L;

        List<String> notInStatusList = List.of(
                WAIVED.name(),
                ABANDONED.name(),
                FORMAL_REJECTION.name(),
                OBJECTIVE_REJECTION.name()
        );

        do {
            page = PageRequest.of(0, 50);
            Page<ApplicationInstallmentProjection> installmentPage = getApplicationInstallmentRepository().getInstallmentProjectionPageToProcess(
                    applicationInstallmentConfig.getInstallmentType(),
                    getInstallmentStatusList(),
                    applicationInstallmentConfig.getApplicationCategory().name(),
                    dates.getLastDueDate(),
                    dates.getEndDueDate(),
                    dates.getGraceEndDate(),
                    notInStatusList,
                    page);
            List<ApplicationInstallmentProjection> installments = installmentPage.getContent();
            Long tmpId = firstIdInPrevPage;
            if (installments == null || installments.isEmpty() || installments.stream().anyMatch(inst -> inst.getId().equals(tmpId))) {
                break;
            }
            firstIdInPrevPage = installments.get(0).getId();
            transactionTemplate.executeWithoutResult(status -> processFetchedPage(installments, configType, applicationInstallmentConfig));

        } while (true);
    }

    protected void processFetchedPage(List<ApplicationInstallmentProjection> installments, InstallmentConfigType configType, ApplicationInstallmentConfig config) {
        updateInstallmentStatusByIds(installments, getInstallmentStatusList().get(0).getNext());
    }

    protected void updateInstallmentStatusByIds(List<ApplicationInstallmentProjection> installments, InstallmentStatus status) {
        getApplicationInstallmentRepository().updateInstallmentListByIds(installments.stream().map(ApplicationInstallmentProjection::getId).toList(), status);
    }

    // bills
    protected void createInstallmentBills(List<ApplicationInstallmentProjection> installments, InstallmentConfigType configType, ApplicationInstallmentConfig config) {
        List<InstallmentNotification> notificationList = new ArrayList<>();
        Long installmentId = null;
        for (ApplicationInstallmentProjection applicationInstallmentProjection : installments) {

            try {
                // delay to prevent bill duplication
                Thread.sleep(1000);

                installmentId = applicationInstallmentProjection.getId();
                // check: save it during, save the installment
                String customerGroup = getCustomerGroupCode(applicationInstallmentProjection);
                if (customerGroup == null) {
                    continue;
                }

                List<FeeCostDto> costs = createBillCostList(config, applicationInstallmentProjection, customerGroup);
                //FeeCostDto feeCostDto = createFeeCostDto(applicationInstallmentProjection, customerGroup, config);
                BillCreationDto billCreationDto = createBillCreationDto(config, applicationInstallmentProjection, costs);
                BillResponseModel bill = getPaymentFeeCostFeignClient().createBill(billCreationDto).getPayload();
                updateInstallmentWithBillInformation(applicationInstallmentProjection, bill, config);

                // send notification for the current installment
                InstallmentNotification notification = sendApplicationInstallmentNotification(configType, applicationInstallmentProjection, bill);
                notificationList.add(notification);

            } catch (Exception ex) {
                addFailedInstallmentBeforeSendNotification(configType, notificationList, applicationInstallmentProjection, ex.getMessage());
                updateInstallmentWithBillInformation(applicationInstallmentProjection, null, config);

                Map<String, Object>  logMap = new LinkedHashMap<>();
                logMap.put("Title", "Exception While Creating Bill For Installment From Job");
                logMap.put("Date",  String.valueOf(LocalDateTime.now()));
                logMap.put("Exception",  getBillCreationExceptionMessage(ex));
                logMap.put("InstallmentId", installmentId);
                getApplicationInstallmentRepository().logInstallmentExceptionMessage(JsonUtils.convertToJSONString(logMap), installmentId);

            }
        }
        if (!notificationList.isEmpty()) {
            // save batch of notifications
            getInstallmentNotificationService().saveAll(notificationList);
        }
    }

    public BillResponseModel createBillForFailedWhileBillCreating(InstallmentNotificationProjection notification, ApplicationInstallmentConfig config) {
        ApplicationInstallmentProjection applicationInstallmentProjection = getApplicationInstallmentRepository().getInstallmentProjectionPageToProcessById(notification.getInstallmentId());
        if (applicationInstallmentProjection == null) {
            return null;
        }

        try {
            // delay to prevent bill duplication
            Thread.sleep(1000);
            String customerGroup = getCustomerGroupCode(applicationInstallmentProjection);
            List<FeeCostDto> costs = createBillCostList(config, applicationInstallmentProjection, customerGroup);
            BillCreationDto billCreationDto = createBillCreationDto(config, applicationInstallmentProjection, costs);
            BillResponseModel bill = getPaymentFeeCostFeignClient().createBill(billCreationDto).getPayload();
            updateInstallmentWithBillInformation(applicationInstallmentProjection, bill, config);
            return bill;
        } catch (Exception ex) {
            updateInstallmentWithBillInformation(applicationInstallmentProjection, null, config);
            log.error("exception while creating bill while resend notification for installment with id {}", applicationInstallmentProjection.getId());
            log.error(ex.getMessage());
            Map<String, Object>  logMap = new LinkedHashMap<>();
            logMap.put("Title", "Exception While Creating Bill For Installment From Re-Send Failed Installments");
            logMap.put("Date",  String.valueOf(LocalDateTime.now()));
            logMap.put("Exception",  getBillCreationExceptionMessage(ex));
            logMap.put("InstallmentId", applicationInstallmentProjection.getId());
            getApplicationInstallmentRepository().logInstallmentExceptionMessage(JsonUtils.convertToJSONString(logMap), applicationInstallmentProjection.getId());

        }

        return null;
    }

    private List<FeeCostDto> createBillCostList(ApplicationInstallmentConfig config, ApplicationInstallmentProjection applicationInstallmentProjection, String customerGroup) {
        List<FeeCostDto> costs = new ArrayList<>();
        for (int i = 0; i < applicationInstallmentProjection.getInstallmentCount(); i++) {
            costs.add(createFeeCostDto(applicationInstallmentProjection, customerGroup, config, applicationInstallmentProjection.getInstallmentIndex() - i));
        }

        if (config.getPublicationBillRequestTypeSaipCode() == null || config.getPublicationBillRequestTypeSaipCode().isBlank()) {
            return costs;
        }

        costs.add(createFeeCostDto(applicationInstallmentProjection, customerGroup, config.getPublicationBillRequestTypeSaipCode(), false));

        return costs;
    }

    private void addFailedInstallmentBeforeSendNotification(InstallmentConfigType configType, List<InstallmentNotification> notificationList, ApplicationInstallmentProjection applicationInstallmentProjection, String notificationMsg) {
        ApplicantsDto applicantsDto = getApplicationApplicant(applicationInstallmentProjection);
        InstallmentNotification installmentNotification = createInstallmentNotificationToSaveInDB(configType, applicationInstallmentProjection, applicantsDto, Optional.of("Exception Before Notification ==> " +  notificationMsg));
        notificationList.add(installmentNotification);
    }

    private String getBillCreationExceptionMessage(Exception ex)  {
        if (ex instanceof AppErrorDecoderException) {
            return JsonUtils.convertToJSONString(((AppErrorDecoderException) ex).getApiResponse());
        }
        return ex.getMessage();

    }

    private String getCustomerGroupCode(ApplicationInstallmentProjection applicationInstallmentProjection) {
        String userGroupCodeByCustomerCode = CUSTOMER_GROUPS.get(applicationInstallmentProjection.getMainApplicantCustomerCode());
        if (userGroupCodeByCustomerCode == null) {
            userGroupCodeByCustomerCode = getCustomerServiceCaller().getUserGroupCodeByCustomerCode(applicationInstallmentProjection.getMainApplicantCustomerCode());
            CUSTOMER_GROUPS.put(applicationInstallmentProjection.getMainApplicantCustomerCode(), userGroupCodeByCustomerCode);
        }
        return userGroupCodeByCustomerCode;
    }

    private BillCreationDto createBillCreationDto(ApplicationInstallmentConfig config, ApplicationInstallmentProjection installmentProjection, List<FeeCostDto> costs) {
        BillCreationDto billCreationDto = new BillCreationDto();
        billCreationDto.setApplicationId(installmentProjection.getApplicationId());
        billCreationDto.setMainRequestType(ApplicationPaymentMainRequestTypesEnum.INSTALLMENT_PROTECTION_RENEWAL);
        billCreationDto.setServiceCode("installment-service-code");
        if(!isInstallmentHasPenalty()) {
            billCreationDto.setExpirePeriod(config.getPaymentDuration() * 30);
            billCreationDto.setForceCreateBill(false);
        }else{
            billCreationDto.setExpirePeriod(config.getGraceDuration() * 30);
            billCreationDto.setForceCreateBill(true);

        }
        billCreationDto.setServiceId(installmentProjection.getId());
        billCreationDto.setCost(costs);

        return billCreationDto;
    }

    private FeeCostDto createFeeCostDto(ApplicationInstallmentProjection installmentProjection, String userGroupCodeByCustomerCode, ApplicationInstallmentConfig config, int index) {
        String paymentCostKey = InstallmentHelper.getInstallmentHelper(config.getInstallmentType()).getPaymentCostKey(config, index, isInstallmentHasPenalty());
        return createFeeCostDto(installmentProjection, userGroupCodeByCustomerCode, paymentCostKey, isInstallmentHasPenalty());
    }

    private static FeeCostDto createFeeCostDto(ApplicationInstallmentProjection installmentProjection, String userGroupCodeByCustomerCode, String paymentCostKey, boolean installmentHasPenalty) {
        FeeCostDto feeCostDto = new FeeCostDto();
        feeCostDto.setApplicationCategoryKey(installmentProjection.getApplicationCategoryEnum());
        feeCostDto.setRequestKey(paymentCostKey);
        feeCostDto.setApplicantCategoryKey(userGroupCodeByCustomerCode);
        feeCostDto.setAddPenalty(installmentHasPenalty);
        return feeCostDto;
    }

    private void updateInstallmentWithBillInformation(ApplicationInstallmentProjection installmentProjection, BillResponseModel bill,  ApplicationInstallmentConfig config) {
        ApplicationInstallmentDatesDto datesToUpdateDates = getDatesToUpdateDates(installmentProjection, config);
        getApplicationInstallmentRepository().updateInstallmentInfoAfterBillCreation(
                bill == null ? null : bill.getBillNumber(),
                bill == null ? null : BigDecimal.valueOf(bill.getTotalCost()),
                bill == null ? null : BigDecimal.valueOf(bill.getTotalPenaltyCost()),
                bill == null ? null : BigDecimal.valueOf(bill.getTotalTaxCost()),
                getInstallmentStatusList().get(0).getNext(),
                datesToUpdateDates.getStartDueDate(),
                datesToUpdateDates.getEndDueDate(),
                datesToUpdateDates.getGraceEndDate(),
                installmentProjection.getId()
        );
    }



    // send notification


    protected void sendNotificationsToTheUsers(List<ApplicationInstallmentProjection> content, InstallmentConfigType configType) {
        List<InstallmentNotification> notificationList = new ArrayList<>();

        for (ApplicationInstallmentProjection applicationInstallmentProjection : content) {
            InstallmentNotification notification = sendApplicationInstallmentNotification(configType, applicationInstallmentProjection, null);
            notificationList.add(notification);
        }

        getInstallmentNotificationService().saveAll(notificationList);
    }

    private InstallmentNotification sendApplicationInstallmentNotification(InstallmentConfigType configType,
                                                                           ApplicationInstallmentProjection applicationInstallmentProjection,
                                                                           BillResponseModel bill) {
        ApplicantsDto applicantsDto = getApplicationApplicant(applicationInstallmentProjection);
        Optional<String> notificationMsg = sendNotification(applicationInstallmentProjection, configType, applicantsDto, bill);
        InstallmentNotification installmentNotification = createInstallmentNotificationToSaveInDB(configType, applicationInstallmentProjection, applicantsDto, notificationMsg);
        return installmentNotification;
    }

    private ApplicantsDto getApplicationApplicant(ApplicationInstallmentProjection applicationInstallmentProjection) {
        ApplicantsDto applicantsDto = APPLICANTS.get(applicationInstallmentProjection.getMainApplicantCustomerCode());
        if (applicantsDto == null) {
            applicantsDto = getApplicationInfoService().listMainApplicant(applicationInstallmentProjection.getApplicationId());
            APPLICANTS.put(applicationInstallmentProjection.getMainApplicantCustomerCode(), applicantsDto);
        }
        return applicantsDto;
    }

    private static InstallmentNotification createInstallmentNotificationToSaveInDB(InstallmentConfigType configType, ApplicationInstallmentProjection applicationInstallmentProjection, ApplicantsDto applicantsDto, Optional<String> notificationMsg) {
        InstallmentNotification installmentNotification =
                new InstallmentNotification(new ApplicationInstallment(applicationInstallmentProjection.getId()), configType.getNotificationType(),
                        notificationMsg.isEmpty() ? InstallmentNotificationStatus.SENT : InstallmentNotificationStatus.FAILED);
        installmentNotification.setEmail(applicationInstallmentProjection.getApplicationEmail());
        installmentNotification.setMobile(applicationInstallmentProjection.getApplicationMobileCode() + applicationInstallmentProjection.getApplicationMobileNumber());
        installmentNotification.setCustomerId(applicantsDto.getCustomerId());
        installmentNotification.setExceptionMessage(notificationMsg.orElse(null));
        installmentNotification.setUserName(applicantsDto.getCustomerCode());
        installmentNotification.setNotificationTemplateCode(configType.getNotificationTemplate());
        return installmentNotification;
    }

    private Optional<String> sendNotification(ApplicationInstallmentProjection installmentProjection, InstallmentConfigType configType,
                                              ApplicantsDto applicantsDto, BillResponseModel bill) {

        try {
            Map<String, Object> params = getNotificationParamsMap(installmentProjection, applicantsDto, bill);
            NotificationRequest notificationRequest = buildNotificationRequest(installmentProjection, configType, applicantsDto, params);
            getNotificationCaller().sendSynchronousNotifications(notificationRequest);
        } catch (Exception ex) {
            log.error("failed to send {} notification to application number {} ",  configType.getNotificationTemplate(), installmentProjection.getApplicationNumber() );
            String value = ex.getMessage();
            if (ex.getMessage() == null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                value = sw.toString();
            }
            return Optional.of(value);
        }

        return Optional.empty();
    }

    private static Map<String, Object> getNotificationParamsMap(ApplicationInstallmentProjection installmentProjection, ApplicantsDto applicantsDto, BillResponseModel bill) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", applicantsDto.getNameAr());
        params.put("applicationNumber", installmentProjection.getApplicationNumber());
        params.put("applicationNameAr", installmentProjection.getApplicationNameAr());
        params.put("applicationNameEn", installmentProjection.getApplicationNameEn());
        params.put("id", installmentProjection.getId());
        params.put("applicationId", installmentProjection.getApplicationId());

        if(bill != null){
            params.put("billNumber", bill.getBillNumber());
            params.put("billAmount", bill.getTotalCost() + bill.getTotalTaxCost() + bill.getTotalPenaltyCost());
        }
        return params;
    }

    private static NotificationRequest buildNotificationRequest(ApplicationInstallmentProjection installmentProjection,
                                                                InstallmentConfigType configType,
                                                                ApplicantsDto applicantsDto,
                                                                Map<String, Object> vars) {
        String notificationCodeDuePeriod = configType.getNotificationTemplate().toString();
        String notificationCodeGracePeriod = configType.getNotificationTemplate().toString();
        if (notificationCodeDuePeriod.equals("TRADEMARK_RENEWAL_DUE_PERIOD_STARTED") ||notificationCodeGracePeriod.equals("TRADEMARK_RENEWAL_GRACE_PERIOD_STARTED") ) {
            return null;
        }
        NotificationDto emailDto = NotificationDto
                .builder()
                .to(installmentProjection.getApplicationEmail())
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();

        NotificationDto smsDto = NotificationDto
                .builder()
                .to(installmentProjection.getApplicationMobileCode() + installmentProjection.getApplicationMobileNumber())
                .build();


        AppNotificationDto appDto = AppNotificationDto.builder()
                .rowId(String.valueOf(installmentProjection.getApplicationId()))
                .routing(installmentProjection.getApplicationCategoryEnum())
                .date(LocalDateTime.now())
                .userNames(List.of(applicantsDto.getCustomerCode())).build();

        return NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(configType.getNotificationTemplate())
                .templateParams(vars)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();
    }

    private InstallmentConfigType getConfigType(ApplicationInstallmentConfig config, InstallmentNotificationType notificationType) {
        return config.getInstallmentConfigTypes().stream().filter(con -> con.getNotificationType().equals(notificationType)).findFirst().orElseThrow();
    }

    protected abstract ApplicationInstallmentDatesDto getDatesToFetchPageToProcess(ApplicationInstallmentConfig applicationInstallmentConfig);

    protected ApplicationInstallmentDatesDto getDatesToUpdateDates(ApplicationInstallmentProjection installmentProjection, ApplicationInstallmentConfig config) {return ApplicationInstallmentDatesDto.builder().build();}
    protected boolean isInstallmentHasPenalty() {
        return false;
    }

    public void flushCacheAfterProcessing() {
       APPLICANTS.clear();
       CUSTOMER_GROUPS.clear();
    }
}
