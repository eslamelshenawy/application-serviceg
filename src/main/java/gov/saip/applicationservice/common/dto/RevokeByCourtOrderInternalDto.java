package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.RevokeByCourtOrderSuspensionDuration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RevokeByCourtOrderInternalDto extends BaseDto<Long> {
    private String courtNumber;
    private String courtName;
    private LocalDateTime rulingDate;
    private RevokeByCourtOrderSuspensionDuration suspensionDuration;
    private Long durationDays;
    private Long durationMonths;
    private Long durationYears;
}
