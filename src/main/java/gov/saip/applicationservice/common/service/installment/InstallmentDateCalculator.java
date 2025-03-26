//package gov.saip.applicationservice.common.service.installment;
//
//import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
//import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
//
//import java.time.LocalDateTime;
//
//public interface InstallmentDateCalculator {
//    LocalDateTime getUpcomingInstallmentDate(int years, int notificationDuration);
//
//    LocalDateTime minusYears(LocalDateTime dateTime, int years);
//    LocalDateTime plusMonths(LocalDateTime dateTime, int months);
//    LocalDateTime plusYears(LocalDateTime dateTime, int years);
//
//    LocalDateTime minusDays(LocalDateTime dateTime, int days);
//
//    LocalDateTime minusMonths(LocalDateTime dateTime, int months);
//
//    void setPatentDates(ApplicationInstallment installment, ApplicationInstallmentConfig config);
//    void setRenewalDates(ApplicationInstallment installment, ApplicationInstallmentConfig config);
//}
