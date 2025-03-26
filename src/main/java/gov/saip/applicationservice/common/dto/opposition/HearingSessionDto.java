package gov.saip.applicationservice.common.dto.opposition;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class HearingSessionDto {
    private LocalDate date;
    private String time;
    private Boolean isPaid;
    private String result;
    private String fileURL;
    private Boolean isHearingSessionScheduled;
}
