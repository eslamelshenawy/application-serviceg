package gov.saip.applicationservice.common.service.installment.processor;

import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationPaymentMainRequestTypesEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class InstallmentRenewalHelper implements InstallmentHelper {

    @PostConstruct
    public void init() {
        HELPERS.put(InstallmentType.RENEWAL, this);
    }

    @Override
    public String getPaymentCostKey(ApplicationInstallmentConfig config,int index,boolean isInstallmentHasPenaltyCode) {
        if (isInstallmentHasPenaltyCode && config.getBillRequestTypeSaipCodePenalty() != null && !config.getBillRequestTypeSaipCodePenalty().isBlank()) {
            return config.getBillRequestTypeSaipCodePenalty();
        }
        return config.getBillRequestTypeSaipCode();
    }

    @Override
    public ApplicationInstallmentDatesDto getDatesToUpdateDates(ApplicationInstallmentProjection installmentProjection, ApplicationInstallmentConfig config) {
        LocalDateTime starDueDate = installmentProjection.getLastDueDate()
                .plusYears(config.getPaymentIntervalYears())
                .minusMonths(config.getOpenRenewalDuration())
                .minusDays(1);

        LocalDateTime endDueDate = starDueDate.plusMonths(config.getPaymentDuration());
        LocalDateTime graceEndDate = endDueDate.plusMonths(config.getGraceDuration());

        return ApplicationInstallmentDatesDto
                .builder()
                .startDueDate(starDueDate)
                .endDueDate(endDueDate)
                .graceEndDate(graceEndDate)
                .build();
    }


    @Override
    public void setFirstInstallmentDates(ApplicationInstallment installment, ApplicationInstallmentConfig config, ApplicationInfo application) {
        installment.setLastDueDate(LocalDateTime.now());

        if (ApplicationCategoryEnum.TRADEMARK.name().equals(application.getCategory().getSaipCode())) {
            installment.setLastDueDate(application.getFilingDate());
        }

        installment.setStartDueDate(installment.getLastDueDate().plusYears(config.getPaymentIntervalYears()).minusMonths(config.getOpenRenewalDuration()));
        installment.setEndDueDate(installment.getStartDueDate().plusMonths(config.getOpenRenewalDuration()));
        installment.setGraceEndDate(installment.getEndDueDate().plusMonths(config.getGraceDuration()));
    }


    @Override
    public ApplicationInstallment createNextInstallment(ApplicationInstallment lastPaidInstallment, ApplicationInstallmentConfig config) {
        ApplicationInstallment newInstallment = new ApplicationInstallment();

        LocalDateTime lastDueDate = lastPaidInstallment.getLastDueDate().plusYears(config.getPaymentIntervalYears());
        LocalDateTime startDueDate = lastDueDate.plusYears(config.getPaymentIntervalYears()).minusMonths(config.getOpenRenewalDuration());;
        LocalDateTime endDueDate = startDueDate.plusMonths(config.getPaymentDuration());
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