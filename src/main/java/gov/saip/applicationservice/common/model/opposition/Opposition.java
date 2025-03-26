package gov.saip.applicationservice.common.model.opposition;

import gov.saip.applicationservice.common.enums.opposition.OppositionFinalDecision;
import gov.saip.applicationservice.common.enums.opposition.OppositionType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.List;

@Entity
@Table(name = "opposition")
@Setter
@Getter
@NoArgsConstructor
public class Opposition extends ApplicationSupportServicesType {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @Column(name="complainer_customer_id")
    private Long complainerCustomerId;

    @Column(name = "opposition_type")
    @Enumerated(EnumType.STRING)
    private OppositionType type;

    @Column(name = "opposition_reason", length = 500)
    private String oppositionReason;


    @ManyToMany
    @JoinTable(name = "opposition_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"opposition_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "opposition_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;


            @ManyToMany
            @JoinTable(name = "opposition_classification",
                    uniqueConstraints = @UniqueConstraint(columnNames={"opposition_id", "classification_id"} ) ,
                    joinColumns = @JoinColumn(name = "opposition_id"),
                    inverseJoinColumns = @JoinColumn(name = "classification_id"))
            private List<Classification> classifications;


            @Column(name = "final_decision")
            @Enumerated(EnumType.STRING)
            private OppositionFinalDecision finalDecision;


            @Column(name = "final_notes", length = 500)
            private String finalNotes;

            @Column(name = "applicant_examiner_notes", length = 500)
            private String applicantExaminerNotes;

            @Column(name = "head_examiner_notes_to_examiner", length = 500)
            private String headExaminerNotesToExaminer;

            @Column(name = "head_examiner_confirmed")
            @DefaultValue("false")
            private Boolean isHeadExaminerConfirmed;



    @Embedded
    private OppositionLegalRepresentative oppositionLegalRepresentative;

    @Embedded
    private HearingSession hearingSession;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "date", column = @Column(name = "applicant_session_date")),
            @AttributeOverride(name = "time", column = @Column(name = "applicant_session_time")),
            @AttributeOverride(name = "isPaid", column = @Column(name = "is_applicant_session_paid")),
            @AttributeOverride(name = "result", column = @Column(name = "applicant_session_result")),
            @AttributeOverride(name = "fileURL", column = @Column(name = "applicant_session_file_url")),
            @AttributeOverride(name = "isHearingSessionScheduled", column = @Column(name = "applicant_hearing_session_scheduled"))
    })
    private HearingSession applicantHearingSession;

}
