package gov.saip.applicationservice.common.model.opposition;


import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "opposition_request")
@Setter
@Getter
@NoArgsConstructor
public class OppositionRequest extends ApplicationSupportServicesType {

    @Column(columnDefinition = "TEXT")
    private String oppositionReason;



    @Column(columnDefinition = "TEXT")
    private String applicationOwnerReply;


    @Embedded
    private OppositionRequestHearingSession complainerHearingSession;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "complainerSessionDate", column = @Column(name = "application_owner_session_date")),
            @AttributeOverride(name = "complainerSessionTime", column = @Column(name = "application_owner_session_time")),
            @AttributeOverride(name = "ComplainerSessionIsPaid", column = @Column(name = "application_owner_is_session_paid")),
            @AttributeOverride(name = "complainerSessionResult", column = @Column(name = "application_owner_session_result")),
            @AttributeOverride(name = "complainerSessionSlotId", column = @Column(name = "application_owner_slot_id")),
    })
    private OppositionRequestHearingSession applicationOwnerHearingSession;


    @ManyToMany
    @JoinTable(name = "opposition_request_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"opposition_request_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "opposition_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> oppositionDocuments;


    @ManyToMany
    @JoinTable(name = "opposition_application_similars",
            uniqueConstraints = @UniqueConstraint(columnNames={"opposition_request_id", "application_info_id"} ) ,
            joinColumns = @JoinColumn(name = "opposition_request_id"),
            inverseJoinColumns = @JoinColumn(name = "application_info_id"))
    private List<ApplicationInfo> oppositionApplicationSimilars;

    public boolean withoutSession() {
        return Optional.ofNullable(applicationOwnerHearingSession)
                .map(session -> session.getComplainerSessionSlotId() == null &&
                        session.getComplainerSessionIsPaid() == null &&
                        session.getComplainerSessionResult() == null &&
                        session.getComplainerSessionDate() == null &&
                        session.getComplainerSessionTime() == null)
                .orElse(true);
    }


}
