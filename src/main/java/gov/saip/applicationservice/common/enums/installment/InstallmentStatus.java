package gov.saip.applicationservice.common.enums.installment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InstallmentStatus {
    DELETED_BECAUSE_OF_APP_REJECTION(null),

    PAID(null),
    POSTPONED(InstallmentStatus.PAID),

    // cron job flow
    EXPIRED(InstallmentStatus.PAID),
    DUE_OVER(InstallmentStatus.EXPIRED),
    DUE(InstallmentStatus.DUE_OVER),
    NEW(InstallmentStatus.DUE)
    ;

    InstallmentStatus next;
}
