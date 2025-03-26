package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supporting_evidence")
@Setter
@Where(clause = "is_deleted = 0")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupportingEvidence extends BaseEntity<Long> {

    @Column
    private String patentNumber;

    @Column
    private String address;

    @Column
    private String evidenceDate;

    @Column
    private String classification;

    @Column
    private String link;

    @Column(name = "is_patent")
    private Boolean patentRegisteration = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "substantive_examination_reports_id")
    private SubstantiveExaminationReport substantiveExaminationReports;

    @ManyToOne
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;


}

