package gov.saip.applicationservice.common.dto.installment;

import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;

import java.time.LocalDateTime;

public interface ApplicationInstallmentLightProjection {
    Long getId();
    InstallmentStatus getInstallmentStatus();
    LocalDateTime getStartDueDate();
    LocalDateTime getEndDueDate();
    String getBillNumber();
    String getApplicationCategoryEnum();

    boolean isPostponed();
    Integer getInstallmentIndex();

}
