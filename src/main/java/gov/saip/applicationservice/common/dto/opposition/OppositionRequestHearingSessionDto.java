package gov.saip.applicationservice.common.dto.opposition;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OppositionRequestHearingSessionDto {
    private LocalDate complainerSessionDate;
    private LocalTime complainerSessionTime;
    private Boolean ComplainerSessionIsPaid;
    private String complainerSessionResult;
    private Long complainerSessionSlotId;
}
