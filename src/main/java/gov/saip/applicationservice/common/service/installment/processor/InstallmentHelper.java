package gov.saip.applicationservice.common.service.installment.processor;

import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDatesDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



public interface InstallmentHelper {
    Map<InstallmentType, InstallmentHelper> HELPERS = new HashMap<>(2);

    void init();
    String getPaymentCostKey(ApplicationInstallmentConfig config, int index,boolean isInstallmentHasPenaltyCode);

    ApplicationInstallmentDatesDto getDatesToUpdateDates(ApplicationInstallmentProjection installmentProjection, ApplicationInstallmentConfig config);

    void setFirstInstallmentDates(ApplicationInstallment installment, ApplicationInstallmentConfig config, ApplicationInfo applicationInfo);


    ApplicationInstallment createNextInstallment(ApplicationInstallment lastPaidInstallment, ApplicationInstallmentConfig config);
    static InstallmentHelper getInstallmentHelper(InstallmentType type) {
        return HELPERS.get(type);
    }

}
