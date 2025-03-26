package gov.saip.applicationservice.common.dto.installment;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class ApplicationInstallmentDto extends BaseDto<Long> {

    private Long applicationId;
    private InstallmentStatus installmentStatus;
    private LocalDate startDueDate;
    private LocalDate endDueDate;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private BigDecimal totalCost;
    private BigDecimal penaltyCost;
    private BigDecimal taxesCost;
}
