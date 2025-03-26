package gov.saip.applicationservice.common.model.patent;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patent_details")
@Where(clause = "is_deleted = 0")
@Setter
@Getter
public class PatentDetails extends BaseEntity<Long> {


    @Column(unique = true)
    private Long applicationId;
    @Column(name = "ipd_summary_ar")
    private String ipdSummaryAr;
    @Column(name = "ipd_summary_en")
    private String ipdSummaryEn;
    @Column(name = "specifications_doc_id")
    private Long specificationsDocId;
    @Column(name = "collaborative_research")
    private Boolean collaborativeResearch;
    @Column(name = "patent_opposition")
    private Boolean patentOpposition = Boolean.FALSE;
    @ManyToOne
    @JoinColumn(name = "collaborative_research_id")
    private LkApplicationCollaborativeResearch collaborativeResearchId;

    @Transient
    private DocumentDto documentDto;

    @OneToMany(mappedBy = "patentDetails", cascade = {CascadeType.REMOVE})
    private List<PatentAttributeChangeLog> patentAttributeChangeLogs;

}
