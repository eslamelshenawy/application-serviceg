package gov.saip.applicationservice.common.dto.installment;

import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InstallmentStatusBannerDto {

    private Long id;
    LocalDateTime startDueDate;
    LocalDateTime endDueDate;
    private int months;
    private InstallmentStatus installmentStatus;
    private String billNumber;
    boolean isPostponed;
    private Long paymentDeadlineDays;
}
