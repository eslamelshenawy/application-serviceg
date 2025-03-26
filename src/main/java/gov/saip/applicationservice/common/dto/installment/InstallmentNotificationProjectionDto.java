package gov.saip.applicationservice.common.dto.installment;

import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class InstallmentNotificationProjectionDto {

    private Long id;
    private Long appId;
    private String applicationNumber;
    private BigDecimal amount;
    private LocalDateTime endDueDate;
    private InstallmentNotificationStatus notificationStatus;
    private InstallmentStatus installmentStatus;
    private String applicationCategory;
    private String requestId;
    private String customerCode;
    private String customerName;
    private String customerNameEn;
}
