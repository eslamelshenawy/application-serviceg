package gov.saip.applicationservice.common.dto.installment;

import gov.saip.applicationservice.common.dto.notifications.NotificationTemplateCode;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationType;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface InstallmentNotificationProjection {

    Long getId();
    InstallmentNotificationType getNotificationType();
    NotificationTemplateCode getNotificationTemplateCode();
    Long getInstallmentId();
    Long getAppId();
    String getApplicationNumber();
    BigDecimal getTotalCost();
    BigDecimal getPenaltyCost();
    BigDecimal getTaxCost();
    LocalDateTime getEndDueDate();
    InstallmentNotificationStatus getNotificationStatus();
    InstallmentStatus getInstallmentStatus ();
    String getApplicationCategory();
    Long getCustomerId();
    String getBillNumber();
    String getApplicationEmail();
    String getApplicationMobileCode();
    String getApplicationMobileNumber();
    String getApplicationNameAr();
    String getApplicationNameEn();
    String getCustomerCode();


    default BigDecimal getAmount() {
        return BigDecimal.valueOf(getDoubleValue(getTotalCost()) + getDoubleValue(getTaxCost()) + getDoubleValue(getPenaltyCost()));
    }

    private double getDoubleValue(BigDecimal totalCost) {
        return totalCost == null ? 0.0 : totalCost.doubleValue();
    }
}
