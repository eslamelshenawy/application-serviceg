package gov.saip.applicationservice.common.service.installment.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.NotificationDto;
import gov.saip.applicationservice.common.dto.installment.InstallmentNotificationProjection;
import gov.saip.applicationservice.common.dto.notifications.AppNotificationDto;
import gov.saip.applicationservice.common.dto.notifications.NotificationLanguage;
import gov.saip.applicationservice.common.dto.notifications.NotificationRequest;
import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.dto.payment.BillResponseModel;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.model.installment.InstallmentNotification;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentConfigRepository;
import gov.saip.applicationservice.common.repository.installment.InstallmentNotificationRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentDesertionProcessor;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentDueProcessor;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentOverDueProcessor;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentProcessor;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstallmentNotificationServiceImpl extends BaseServiceImpl<InstallmentNotification, Long> implements InstallmentNotificationService {
    private static final Map<InstallmentStatus, InstallmentProcessor> PROCESSOR_MAP = new HashMap<>();
    private final InstallmentNotificationRepository installmentNotificationRepository;
    private final NotificationCaller notificationCaller;
    private ApplicationInfoService applicationInfoService;
    private InstallmentDueProcessor installmentDueProcessor;
    private InstallmentOverDueProcessor installmentOverDueProcessor;
    private InstallmentDesertionProcessor installmentDesertionProcessor;
    private final ApplicationInstallmentConfigRepository installmentConfigRepository;
    protected final TransactionTemplate transactionTemplate;
    @Override
    protected BaseRepository<InstallmentNotification, Long> getRepository() {
        return installmentNotificationRepository;
    }
    @Autowired
    public void setInstallmentDueProcessor(@Lazy InstallmentDueProcessor installmentDueProcessor) {
        this.installmentDueProcessor = installmentDueProcessor;
    }

    @Autowired
    public void setInstallmentOverDueProcessor(@Lazy InstallmentOverDueProcessor installmentOverDueProcessor) {
        this.installmentOverDueProcessor = installmentOverDueProcessor;
    }

    @Autowired
    public void setInstallmentDesertionProcessor(@Lazy InstallmentDesertionProcessor installmentDesertionProcessor) {
        this.installmentDesertionProcessor = installmentDesertionProcessor;
    }

    @Autowired
    public void setApplicationInfoService(@Lazy ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }
    @PostConstruct
    public void init() {
        PROCESSOR_MAP.put(InstallmentStatus.DUE, installmentDueProcessor);
        PROCESSOR_MAP.put(InstallmentStatus.DUE_OVER, installmentOverDueProcessor);
        PROCESSOR_MAP.put(InstallmentStatus.EXPIRED, installmentDesertionProcessor);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Async
    @Transactional
    @Override
    public void resendNotification(Long id, boolean sendAll) {
        if (sendAll) {
            resendAllFailedNotifications();
        } else {
            resendNotificationToOne(id);
        }
        installmentNotificationRepository.updatePendingToFailed();
        installmentDesertionProcessor.flushCacheAfterProcessing();
    }

    public void resendNotificationToOne(Long id) {
        Optional<InstallmentNotificationProjection> optNotification = installmentNotificationRepository.findFailedNotificationByIdToResend(id, InstallmentNotificationStatus.FAILED);
        InstallmentNotificationProjection notification = optNotification.orElseThrow(() -> new BusinessException(Constants.ErrorKeys.VALIDATION_INSTALLMENT_NOT_FOUND));
        transactionTemplate.executeWithoutResult(status -> sendNotificationsToTheUsers(List.of(notification)));
    }

    public void resetLastNotification(Long id) {
        installmentNotificationRepository.resetLastNotification(id);
        log.info("End resetLastNotification ");
    }

    private void resendAllFailedNotifications() {
        Long firstIdInPrevPage =  0L;
        do {
            List<InstallmentNotificationProjection> failedPageOfNotifications = getFailedPageOfNotifications();
            if (failedPageOfNotifications.isEmpty()) break;

            Long tmpId = firstIdInPrevPage;
            if (failedPageOfNotifications == null || failedPageOfNotifications.isEmpty() || failedPageOfNotifications.stream().anyMatch(inst -> inst.getId().equals(tmpId))) {
                break;
            }
            firstIdInPrevPage = failedPageOfNotifications.get(0).getId();
            transactionTemplate.executeWithoutResult(status -> sendNotificationsToTheUsers(failedPageOfNotifications));
        } while (true);
    }

    private List<InstallmentNotificationProjection> getFailedPageOfNotifications() {
        PageRequest pageRequest = PageRequest.of(0, 50);
        Page<InstallmentNotificationProjection> applicationInstallments = installmentNotificationRepository.getFailedProjectionPageToResend(InstallmentNotificationStatus.FAILED,  pageRequest);
        return applicationInstallments.getContent();
    }

    private void sendNotificationsToTheUsers(List<InstallmentNotificationProjection> notifications) {
        List<InstallmentNotification> notificationList = new ArrayList<>();
        List<Long> notificationIdsToDelete = new ArrayList<>(notifications.size());
        for (InstallmentNotificationProjection notification : notifications) {
            ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(notification.getAppId());
            BillResponseModel bill = createBillIfNotCreated(notification);
            Optional<String> notificationMsg = sendNotification(notification, applicantsDto, bill);
            InstallmentNotification installmentNotification = createNewNotificationToBeInsertedAfterResend(notification, applicantsDto, notificationMsg);
            notificationList.add(installmentNotification);
            notificationIdsToDelete.add(notification.getId());
        }
        installmentNotificationRepository.resetLastNotificationListByIds(notificationIdsToDelete);
        installmentNotificationRepository.saveAll(notificationList);
    }

    private BillResponseModel createBillIfNotCreated(InstallmentNotificationProjection notification) {
        if(notification.getBillNumber() != null) {
            return null;
        }

        try {
            return createBillForFailedWhileBillCreating(notification);
        } catch(Exception ex) {
            return null;
        }
    }

    private static InstallmentNotification createNewNotificationToBeInsertedAfterResend(InstallmentNotificationProjection notification, ApplicantsDto applicantsDto, Optional<String> notificationMsg) {
        InstallmentNotification installmentNotification = new InstallmentNotification();
        installmentNotification.setApplicationInstallment(new ApplicationInstallment(notification.getInstallmentId()));
        installmentNotification.setNotificationType(notification.getNotificationType());
        installmentNotification.setNotificationTemplateCode(notification.getNotificationTemplateCode());
        installmentNotification.setUserName(applicantsDto.getUsername());
        installmentNotification.setNotificationStatus(notificationMsg.isEmpty() ? InstallmentNotificationStatus.SENT : InstallmentNotificationStatus.PENDING);
        installmentNotification.setEmail(notification.getApplicationEmail());
        installmentNotification.setMobile(notification.getApplicationMobileCode() + notification.getApplicationMobileNumber());
        installmentNotification.setCustomerId(applicantsDto.getCustomerId());
        installmentNotification.setExceptionMessage(notificationMsg.orElse(null));
        return installmentNotification;
    }


    private Optional<String> sendNotification(InstallmentNotificationProjection notification, ApplicantsDto applicantsDto, BillResponseModel bill) {

        try {
            String username = applicantsDto.getCustomerCode();
            Map<String, Object> vars = getNotificationParamsMap(notification, applicantsDto, bill);

            NotificationRequest notificationRequest = buildNotificationRequest(notification, notification.getNotificationTemplateCode(), username, vars);
            notificationCaller.sendSynchronousNotifications(notificationRequest);
        } catch (Exception ex) {
            log.error("failed to send {} notification to application number {} ",  notification.getNotificationTemplateCode(), notification.getApplicationNumber() );
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

    private Map<String, Object> getNotificationParamsMap(InstallmentNotificationProjection notification, ApplicantsDto applicantsDto, BillResponseModel bill) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", applicantsDto.getNameAr());
        params.put("applicationNumber", notification.getApplicationNumber());
        params.put("applicationNameAr", notification.getApplicationNameAr());
        params.put("applicationNameEn", notification.getApplicationNameEn());
        params.put("id", notification.getInstallmentId());
        params.put("applicationId", notification.getAppId());
        if(notification.getBillNumber() != null) {
            params.put("billNumber", notification.getBillNumber());
            params.put("billAmount", notification.getAmount());
        } else if(bill != null) {
            params.put("billNumber", bill.getBillNumber());
            params.put("billAmount", bill.getTotalCost() + bill.getTotalTaxCost() + bill.getTotalPenaltyCost());
        }
        return params;
    }

    private BillResponseModel createBillForFailedWhileBillCreating(InstallmentNotificationProjection notification) {
        notification.getNotificationTemplateCode();
        InstallmentProcessor installmentProcessor = PROCESSOR_MAP.get(notification.getInstallmentStatus());
        ApplicationInstallmentConfig config = installmentConfigRepository.findByApplicationCategory(ApplicationCategoryEnum.valueOf(notification.getApplicationCategory()));
        if (installmentProcessor != null) {
            return installmentProcessor.createBillForFailedWhileBillCreating(notification, config);
        }
        return null;
    }

    private static NotificationRequest buildNotificationRequest(InstallmentNotificationProjection notification, NotificationTemplateCode templateCode, String username, Map<String, Object> vars) {
        NotificationDto emailDto = NotificationDto
                .builder()
                .to(notification.getApplicationEmail())
                .messageType(Constants.MessageType.EMAIL_TYPE_MESSAGE)
                .build();

        NotificationDto smsDto = NotificationDto
                .builder()
                .to(notification.getApplicationMobileCode() + notification.getApplicationMobileNumber())
                .build();


        AppNotificationDto appDto = AppNotificationDto.builder()
                .rowId(String.valueOf(notification.getAppId()))
                .routing(notification.getApplicationCategory())
                .date(LocalDateTime.now())
                .userNames(List.of(username)).build();

        return NotificationRequest.builder()
                .lang(NotificationLanguage.AR)
                .code(templateCode)
                .templateParams(vars)
                .email(emailDto)
                .sms(smsDto)
                .app(appDto)
                .build();
    }

    @Override
    @Transactional
    public List<InstallmentNotification> saveAll(List<InstallmentNotification> entities) {
        List<Long> ids = entities.stream().map(notification -> notification.getApplicationInstallment().getId()).toList();
        installmentNotificationRepository.resetLastNotificationList(ids);
        return super.saveAll(entities);
    }
}
