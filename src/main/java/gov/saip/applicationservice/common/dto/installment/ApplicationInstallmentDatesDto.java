package gov.saip.applicationservice.common.dto.installment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApplicationInstallmentDatesDto {
    private LocalDateTime lastDueDate;
    private LocalDateTime startDueDate;
    private LocalDateTime endDueDate;
    private LocalDateTime graceEndDate;
    Long applicationId;
}
