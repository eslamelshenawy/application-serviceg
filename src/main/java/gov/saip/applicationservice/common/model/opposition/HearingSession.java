package gov.saip.applicationservice.common.model.opposition;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.ws.rs.DefaultValue;
import java.time.LocalDate;

@Embeddable
@Setter
@Getter
public class HearingSession {

    @Column(name = "session_date")
    private LocalDate date;

    @Column(name = "session_time")
    private String time;

    @Column(name = "is_session_paid")
    @DefaultValue("false")
    private Boolean isPaid;

    @Column(name="session_result", length = 500)
    private String result;

    @Column(name = "session_file_url")
    private String fileURL;

    @Column(name = "hearing_session_scheduled")
    @DefaultValue("false")
    private Boolean isHearingSessionScheduled;



}
