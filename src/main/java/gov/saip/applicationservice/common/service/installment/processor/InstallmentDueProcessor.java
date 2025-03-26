package gov.saip.applicationservice.common.service.installment.processor;

import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationType;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.model.installment.InstallmentConfigType;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentRepository;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
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
public class InstallmentDueProcessor extends InstallmentProcessor {

    private final ApplicationInstallmentRepository applicationInstallmentRepository;
    private final NotificationCaller notificationCaller;
    private final InstallmentNotificationService installmentNotificationService;
    private final ApplicationInfoService applicationInfoService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationCustomerService applicationCustomerService;
    private final PaymentFeeCostFeignClient paymentFeeCostFeignClient;
    private final ApplicationInstallmentService applicationInstallmentService;

    @Override
    protected InstallmentNotificationType getNotificationType() {
        return InstallmentNotificationType.RENEWAL_DUE_PERIOD_STARTED;
    }

    @Override
    protected List<InstallmentStatus> getInstallmentStatusList() {
        return List.of(InstallmentStatus.NEW);
    }

    private LocalDateTime getLastDueDate(ApplicationInstallmentConfig applicationInstallmentConfig) {
        return  LocalDateTime.now()
                .minusYears(applicationInstallmentConfig.getPaymentIntervalYears())
                .plusMonths(applicationInstallmentConfig.getOpenRenewalDuration());
    }

    @Override
    protected ApplicationInstallmentDatesDto getDatesToFetchPageToProcess(ApplicationInstallmentConfig applicationInstallmentConfig) {
        return ApplicationInstallmentDatesDto
                .builder()
                .lastDueDate(getLastDueDate(applicationInstallmentConfig))
                .build();
    }

    @Override
    protected ApplicationInstallmentDatesDto getDatesToUpdateDates(ApplicationInstallmentProjection installmentProjection, ApplicationInstallmentConfig config) {
        return InstallmentHelper.getInstallmentHelper(config.getInstallmentType()).getDatesToUpdateDates(installmentProjection, config);
    }


    @Override
    protected void processFetchedPage(List<ApplicationInstallmentProjection> installments, InstallmentConfigType configType, ApplicationInstallmentConfig config) {
       super.createInstallmentBills(installments, configType, config);
    }

}
