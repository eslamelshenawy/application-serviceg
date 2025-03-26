//package gov.saip.applicationservice.common.service.installment.impl;
//
//import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
//import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
//import gov.saip.applicationservice.common.service.installment.InstallmentDateCalculator;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.Month;
//
//public class InstallmentDateCalculatorProdImpl implements InstallmentDateCalculator {
//
//    @Override
//    public LocalDateTime getUpcomingInstallmentDate(int years, int notificationDuration) {
//        return LocalDateTime.now().minusYears(years).plusMonths(notificationDuration);
//    }
//
//    @Override
//    public LocalDateTime minusYears(LocalDateTime dateTime, int years) {
//        // last second of the day
//        return dateTime.minusYears(years).with(LocalTime.MAX);
//    }
//
//    @Override
//    public LocalDateTime plusMonths(LocalDateTime dateTime, int months) {
//        return dateTime.plusMonths(months);
//    }
//
//    @Override
//    public LocalDateTime plusYears(LocalDateTime dateTime, int years) {
//        return dateTime.plusYears(years);
//    }
//
//    @Override
//    public LocalDateTime minusDays(LocalDateTime dateTime, int days) {
//        return dateTime.minusDays(days).with(LocalTime.MAX);
//    }
//    @Override
//    public LocalDateTime minusMonths(LocalDateTime dateTime, int months) {
//        return dateTime.minusMonths(months).with(LocalTime.MAX);
//    }
//
//    @Override
//    public void setPatentDates(ApplicationInstallment installment, ApplicationInstallmentConfig config) {
//        LocalDate firstDayInCurrentYear = LocalDate.of(LocalDateTime.now().getYear(), Month.JANUARY, 1);
//        LocalDateTime firstTimeInCurrentYear = LocalDateTime.of(firstDayInCurrentYear, LocalDateTime.now().toLocalTime());
//        installment.setLastDueDate(firstTimeInCurrentYear);
//        installment.setStartDueDate(installment.getLastDueDate().plusYears(config.getPaymentIntervalYears()).minusMonths(config.getOpenRenewalDuration()));
//        installment.setEndDueDate(installment.getStartDueDate().plusMonths(config.getPaymentDuration() + config.getOpenRenewalDuration()).minusDays(1));
//        installment.setGraceEndDate(installment.getEndDueDate().plusMonths(config.getGraceDuration()));
//    }
//    @Override
//    public void setRenewalDates(ApplicationInstallment installment, ApplicationInstallmentConfig config) {
//        installment.setLastDueDate(LocalDateTime.now());
//        installment.setStartDueDate(LocalDateTime.now().plusYears(config.getPaymentIntervalYears()).minusMonths(config.getOpenRenewalDuration()));
//        installment.setEndDueDate(installment.getStartDueDate().plusMonths(config.getOpenRenewalDuration()));
//        installment.setGraceEndDate(installment.getEndDueDate().plusMonths(config.getGraceDuration()));
//    }
//}
