//package gov.saip.applicationservice.common.service.installment.impl;
//
//import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
//import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
//import gov.saip.applicationservice.common.service.installment.InstallmentDateCalculator;
//
//import java.time.LocalDateTime;
//
//public class InstallmentDateCalculatorTestImpl implements InstallmentDateCalculator {
//
//    @Override
//    public LocalDateTime getUpcomingInstallmentDate(int years, int notificationDuration) {
//            return LocalDateTime.now().minusMinutes(years).plusMinutes(notificationDuration);
//    }
//
//    @Override
//    public LocalDateTime minusYears(LocalDateTime dateTime, int years) {
//        return dateTime.minusMinutes(years).withSecond(59);
//    }
//    @Override
//    public LocalDateTime plusMonths(LocalDateTime dateTime, int months) {
//        return dateTime.plusMinutes(months);
//    }
//
//    @Override
//    public LocalDateTime plusYears(LocalDateTime dateTime, int years) {
//        return dateTime.plusMinutes(years);
//    }
//
//    @Override
//    public LocalDateTime minusDays(LocalDateTime dateTime, int days) {
//        return dateTime.minusMinutes(days).withSecond(59);
//    }
//
//    @Override
//    public LocalDateTime minusMonths(LocalDateTime dateTime, int months) {
//        return dateTime.minusMinutes(months);
//    }
//
//    @Override
//    public void setPatentDates(ApplicationInstallment installment, ApplicationInstallmentConfig config) {
//        installment.setLastDueDate(LocalDateTime.now());
//        installment.setStartDueDate(installment.getLastDueDate().plusMinutes(config.getPaymentIntervalYears()).minusMinutes(config.getOpenRenewalDuration()));
//        installment.setEndDueDate(installment.getStartDueDate().plusMinutes(config.getPaymentDuration() + config.getOpenRenewalDuration()).minusMinutes(1));
//        installment.setGraceEndDate(installment.getEndDueDate().plusMinutes(config.getGraceDuration()).minusMinutes(1));
//    }
//
//    @Override
//    public void setRenewalDates(ApplicationInstallment installment, ApplicationInstallmentConfig config) {
//        installment.setLastDueDate(LocalDateTime.now());
//        installment.setStartDueDate(LocalDateTime.now().plusMinutes(config.getPaymentIntervalYears()).minusMinutes(config.getOpenRenewalDuration()).minusMinutes(1));
//        installment.setEndDueDate(installment.getStartDueDate().plusMinutes(config.getPaymentDuration() + config.getOpenRenewalDuration()));
//        installment.setGraceEndDate(installment.getEndDueDate().plusMinutes(config.getGraceDuration()));
//    }
//}
