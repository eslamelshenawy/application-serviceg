//package gov.saip.applicationservice.common.service.installment.processor;
//
//import gov.saip.applicationservice.common.clients.rest.callers.NotificationCaller;
//import gov.saip.applicationservice.common.clients.rest.feigns.PaymentFeeCostFeignClient;
//import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
//import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationType;
//import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
//import gov.saip.applicationservice.common.enums.installment.InstallmentType;
//import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
//import gov.saip.applicationservice.common.model.installment.InstallmentConfigType;
//import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentRepository;
//import gov.saip.applicationservice.common.service.ApplicationCustomerService;
//import gov.saip.applicationservice.common.service.ApplicationInfoService;
//import gov.saip.applicationservice.common.service.CustomerServiceCaller;
//import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentService;
//import gov.saip.applicationservice.common.service.installment.InstallmentDateCalculator;
//import gov.saip.applicationservice.common.service.installment.InstallmentNotificationService;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//@Getter
//public class InstallmentDueSoonProcessor extends InstallmentProcessor {
//
//    private final ApplicationInstallmentRepository applicationInstallmentRepository;
//    private final NotificationCaller notificationCaller;
//    private final InstallmentNotificationService installmentNotificationService;
//    private final ApplicationInfoService applicationInfoService;
//    private final InstallmentDateCalculator installmentDateCalculator;
//    private final CustomerServiceCaller customerServiceCaller;
//    private final ApplicationCustomerService applicationCustomerService;
//    private final PaymentFeeCostFeignClient paymentFeeCostFeignClient;
//    private final ApplicationInstallmentService applicationInstallmentService;
//    @Override protected InstallmentNotificationType getNotificationType() {return InstallmentNotificationType.DUE_DATE_SOON;}
//
//    @Override
//    protected List<InstallmentStatus> getInstallmentStatusList() {
//        return List.of(InstallmentStatus.NEW);
//    }
//
//    @Override
//    protected LocalDateTime getLastDueDate(ApplicationInstallmentConfig applicationInstallmentConfig) {
//        LocalDateTime endDate = installmentDateCalculator.minusYears(LocalDateTime.now(), applicationInstallmentConfig.getPaymentIntervalYears());
//        return installmentDateCalculator.plusMonths(endDate, applicationInstallmentConfig.getNotificationDuration());
//    }
//
//    @Override
//    protected void processFetchedPage(List<ApplicationInstallmentProjection> installments, InstallmentConfigType configType, ApplicationInstallmentConfig config) {
//        if(InstallmentType.ANNUAL.equals(config.getInstallmentType())) {
//            super.processFetchedPage(installments, configType, config);
//        } else {
//            updateInstallmentType(installments, config);
//        }
//    }
//
//    private void updateInstallmentType(List<ApplicationInstallmentProjection> installments, ApplicationInstallmentConfig config) {
//        LocalDateTime startDueDate = installmentDateCalculator.plusMonths(LocalDateTime.now(), config.getNotificationDuration());
//        LocalDateTime endDueDate = installmentDateCalculator.plusMonths(startDueDate, config.getPaymentDuration());
//        super.updateInstallmentStatusAndDueDates(installments, getInstallmentStatusList().get(0).getNext(), startDueDate, endDueDate);
//    }
//
//
//}
