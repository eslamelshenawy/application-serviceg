package gov.saip.applicationservice.common.service.installment.processor;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.RequestActivityLogsDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
import gov.saip.applicationservice.common.enums.RequestActivityLogEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationType;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.model.installment.InstallmentConfigType;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentRepository;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationStatusChangeLogService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Getter
public class InstallmentDesertionProcessor extends InstallmentProcessor {

    private final ApplicationInstallmentRepository applicationInstallmentRepository;
    private final NotificationCaller notificationCaller;
    private final InstallmentNotificationService installmentNotificationService;
    private final ApplicationInfoService applicationInfoService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationCustomerService applicationCustomerService;
    private final PaymentFeeCostFeignClient paymentFeeCostFeignClient;
    private final ApplicationInstallmentService applicationInstallmentService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final ApplicationStatusChangeLogService applicationStatusChangeLogService;
    @Override
    protected InstallmentNotificationType getNotificationType() {
        return InstallmentNotificationType.RENEWAL_APPLICATION_DESERTION;
    }

    @Override
    protected List<InstallmentStatus> getInstallmentStatusList() {
        return List.of(InstallmentStatus.DUE_OVER);
    }

    private LocalDateTime getGraceEndDate(ApplicationInstallmentConfig applicationInstallmentConfig) {
        return LocalDateTime.now().minusDays(1);
    }

    @Override
    protected ApplicationInstallmentDatesDto getDatesToFetchPageToProcess(ApplicationInstallmentConfig applicationInstallmentConfig) {
        return ApplicationInstallmentDatesDto
                .builder()
                .graceEndDate(getGraceEndDate(applicationInstallmentConfig))
                .build();
    }

    @Override
    protected void processFetchedPage(List<ApplicationInstallmentProjection> installmentProjections, InstallmentConfigType configType, ApplicationInstallmentConfig config) {
        super.processFetchedPage(installmentProjections, configType, config);
        this.updateApplicationsStatus(installmentProjections, config);
        this.buildActivityLogAndAddApplicationChangeStatusLog(installmentProjections, config.getApplicationDesertionStatus());
        sendNotificationsToTheUsers(installmentProjections, configType);
    }

    private void updateApplicationsStatus(List<ApplicationInstallmentProjection> installmentProjections, ApplicationInstallmentConfig config) {
        List<Long> ids = installmentProjections.stream().map(installment -> installment.getApplicationId()).toList();
        applicationInfoService.updateApplicationsStatusByIds(ids, config.getApplicationDesertionStatus());
    }

    private void buildActivityLogAndAddApplicationChangeStatusLog(List<ApplicationInstallmentProjection> installmentProjections,
                                               LkApplicationStatus lkApplicationStatus) {
        for (ApplicationInstallmentProjection installmentProjection : installmentProjections) {
            RequestActivityLogsDto requestActivityLogsDto = RequestActivityLogsDto.builder()
                    .taskDefinitionKey(RequestActivityLogEnum.RENEWAL_APPLICATION_DESERTION)
                    .taskNameAr(lkApplicationStatus.getIpsStatusDescAr())
                    .taskNameEn(lkApplicationStatus.getIpsStatusDescEn())
                    .statusCode(lkApplicationStatus.getCode())
                    .assignee(installmentProjection.getMainApplicantCustomerCode())
                    .requestId(installmentProjection.getProcessRequestId()).build();
            String taskId = bpmCallerFeignClient.addActivityLog(requestActivityLogsDto).getPayload().toString();
            applicationStatusChangeLogService.changeApplicationStatusAndLog(lkApplicationStatus.getCode(), "CANCELLATION_FOR_NON-RENEWAL",
                    installmentProjection.getApplicationId(), RequestActivityLogEnum.RENEWAL_APPLICATION_DESERTION.name() , taskId);
        }
    }
}
