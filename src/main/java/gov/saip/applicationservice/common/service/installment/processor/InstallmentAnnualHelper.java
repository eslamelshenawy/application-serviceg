package gov.saip.applicationservice.common.service.installment.processor;

import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.installment.AnnualInstallmentCostEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Component
public class InstallmentAnnualHelper implements InstallmentHelper {


    @PostConstruct
    public void init() {
        HELPERS.put(InstallmentType.ANNUAL, this);
    }

    @Override
    public String getPaymentCostKey(ApplicationInstallmentConfig config, int index,boolean isInstallmentHasPenaltyCode) {
        ApplicationCategoryEnum applicationCategory = config.getApplicationCategory();
        switch (applicationCategory) {
            case PATENT -> {
                return AnnualInstallmentCostEnum.getYearByIndex(index).name();
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public ApplicationInstallmentDatesDto getDatesToUpdateDates(ApplicationInstallmentProjection installmentProjection, ApplicationInstallmentConfig config) {
        LocalDateTime starDueDate = installmentProjection.getLastDueDate()
                .plusYears(config.getPaymentIntervalYears())
                .minusMonths(config.getOpenRenewalDuration());

        LocalDateTime endDueDate = starDueDate.plusMonths(config.getPaymentDuration()).minusDays(1);
        LocalDateTime graceEndDate = endDueDate.plusMonths(config.getGraceDuration());

        return ApplicationInstallmentDatesDto
                .builder()
                .startDueDate(starDueDate)
                .endDueDate(endDueDate)
                .graceEndDate(graceEndDate)
                .build();
    }


    @Override
    public void setFirstInstallmentDates(ApplicationInstallment installment, ApplicationInstallmentConfig config, ApplicationInfo applicationInfo) {
        LocalDate firstDayInCurrentYear = LocalDate.of(LocalDateTime.now().getYear(), Month.JANUARY, 1);
        LocalDateTime firstTimeInCurrentYear = LocalDateTime.of(firstDayInCurrentYear, LocalDateTime.now().toLocalTime());
        installment.setLastDueDate(firstTimeInCurrentYear);
        installment.setStartDueDate(installment.getLastDueDate().plusYears(config.getPaymentIntervalYears()).minusMonths(config.getOpenRenewalDuration()));
        installment.setEndDueDate(installment.getStartDueDate().plusMonths(config.getPaymentDuration() + config.getOpenRenewalDuration()).minusDays(1));
        installment.setGraceEndDate(installment.getEndDueDate().plusMonths(config.getGraceDuration()));
    }

    @Override
    public ApplicationInstallment createNextInstallment(ApplicationInstallment lastPaidInstallment, ApplicationInstallmentConfig config) {
        AnnualInstallmentCostEnum currentYear = AnnualInstallmentCostEnum.getYearByIndex(lastPaidInstallment.getInstallmentIndex());
        if (currentYear == null || currentYear.getNextYear() == null) {
            return null;
        }

        ApplicationInstallment newInstallment = new ApplicationInstallment();

        LocalDateTime lastDueDate = lastPaidInstallment.getLastDueDate().plusYears(config.getPaymentIntervalYears());
        LocalDateTime startDueDate = lastDueDate.plusYears(config.getPaymentIntervalYears()).minusMonths(config.getOpenRenewalDuration());
        LocalDateTime endDueDate = startDueDate.plusMonths(config.getPaymentDuration()).minusDays(1);
        LocalDateTime graceEndDate = endDueDate.plusMonths(config.getGraceDuration());

        newInstallment.setLastDueDate(lastDueDate);
        newInstallment.setStartDueDate(startDueDate);
        newInstallment.setEndDueDate(endDueDate);
        newInstallment.setGraceEndDate(graceEndDate);

        newInstallment.setApplication(lastPaidInstallment.getApplication());
        newInstallment.setInstallmentStatus(InstallmentStatus.NEW);
        newInstallment.setInstallmentType(config.getInstallmentType());
        newInstallment.setInstallmentIndex(lastPaidInstallment.getInstallmentIndex() + 1);

        return newInstallment;
    }
}
