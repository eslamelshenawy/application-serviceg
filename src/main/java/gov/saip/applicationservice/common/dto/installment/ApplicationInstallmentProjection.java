package gov.saip.applicationservice.common.dto.installment;

import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ApplicationInstallmentProjection {

    Long getId();
    InstallmentStatus getInstallmentStatus();
    Long getPostponedReasonId();
    InstallmentType getInstallmentType();
    LocalDateTime getStartDueDate();
    LocalDateTime getEndDueDate();
    LocalDateTime getLastDueDate();
    String getBillNumber();
    BigDecimal getTotalCost();
    BigDecimal getPenaltyCost();
    BigDecimal getTaxCost();
    int getInstallmentIndex();
    int getInstallmentCount();


    // application attributes
    Long getApplicationId();
    Long getMainApplicantId();
    String getMainApplicantCustomerCode();
    String getApplicationEmail();
    String getApplicationMobileCode();
    String getApplicationMobileNumber();
    String getApplicationCategoryEnum();
    String getApplicationNumber();
    String getApplicationNameAr();
    String getApplicationNameEn();
    Long getProcessRequestId();


    default BigDecimal getAmount() {
        return BigDecimal.valueOf(getTotalCost().doubleValue() + getTaxCost().doubleValue() + getPenaltyCost().doubleValue());
    }

}
