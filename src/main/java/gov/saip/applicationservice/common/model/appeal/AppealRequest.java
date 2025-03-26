package gov.saip.applicationservice.common.model.appeal;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.appeal.AppealCheckerDecision;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.List;

@Entity
@Table(name = "appeal_request")
@Setter
@Getter
@NoArgsConstructor
public class AppealRequest extends ApplicationSupportServicesType {

    @Column(name = "appeal_reason", length = 500)
    private String appealReason;

    @ManyToMany
    @JoinTable(name = "appeal_request_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"appeal_request_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "appeal_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;

    @Column(name = "checker_decision")
    @Enumerated(EnumType.STRING)
    private AppealCheckerDecision checkerDecision;

    @Column(name = "checker_final_notes", length = 500)
    private String checkerFinalNotes;

    @Column(name = "appeal_committee_decision")
    @Enumerated(EnumType.STRING)
    private AppealCommitteeDecision appealCommitteeDecision;

    @Column(name = "appeal_committee_decision_comment", length = 500)
    private String appealCommitteeDecisionComment;

    @Column(name = "official_letter_comment", length = 500)
    private String officialLetterComment;

    @OneToMany( mappedBy = "appealRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppealCommitteeOpinion> opinions;

    public AppealRequest(Long id) {super(id);}
}
