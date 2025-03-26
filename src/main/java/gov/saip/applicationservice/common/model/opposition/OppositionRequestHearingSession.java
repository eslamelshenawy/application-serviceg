package gov.saip.applicationservice.common.model.opposition;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.ws.rs.DefaultValue;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@Getter
@Setter
public class OppositionRequestHearingSession {

    @Column(name = "complainer_session_date")
    private LocalDate complainerSessionDate;

    @Column(name = "complainer_session_time")
    private LocalTime complainerSessionTime;

    @Column(name = "complainer_session_is_paid")
    @DefaultValue("false")
    private Boolean ComplainerSessionIsPaid;

    @Column(name = "complainer_session_result",columnDefinition = "TEXT")
    private String complainerSessionResult;


    @Column(name = "complainer_session_slot_id")
    private Long complainerSessionSlotId;
}
