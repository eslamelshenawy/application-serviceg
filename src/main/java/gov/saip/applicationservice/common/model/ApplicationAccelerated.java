package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_accelerated")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAccelerated extends BaseEntity<Long> {

    @Column
    private Boolean acceleratedExamination;

    @Column
    private Boolean fastTrackExamination; //FTE

    @Column
    private Boolean pphExamination; // PPH
//    @Column(name = "pph_pct_examination")
//    private boolean pphPctExamination; // PPH-PCT
//    @Column(name="fast_track_type")
//    private String fastTrackType; // FTE or PPH or PPH-PCT or ...etc
//    @Column(name="pct_number")
//    private String pctNumber; // PCT-Number
//    @Column(name="matching_explanation_protection_elements")
//    private boolean matchingExplanationProtectionElements; // only for PPH or PPH-PCT
//    @Column(name="all_last_protection_elements_similar_office")
//    private boolean allLastProtectionElementsSimilarOffice; // only for PPH or PPH-PCT
//    @Column(name="demand_protection_elements")
//    private String demandProtectionElements; // only for PPH or PPH-PCT
//    @Column(name="last_demand_protection_elements")
//    private String lastDemandProtectionElements; // only for PPH or PPH-PCT
//    @Column(name="matching_explanation")
//    private String matchingExplanation; // only for PPH or PPH-PCT


    @Column
    private Boolean refused;

    @OneToOne
    @JoinColumn(name = "latest_patentable_claims_file_id")
    private Document latestPatentableClaimsFile;

    @OneToOne
    @JoinColumn(name = "closest_prior_art_documents_related_to_cited_references_file_id")
    private Document closestPriorArtDocumentsRelatedToCitedReferencesFile;

    @OneToOne
    @JoinColumn(name = "comparative_id")
    private Document comparative;

    @OneToOne
    @JoinColumn(name = "application_info_id", unique = true)
    private ApplicationInfo applicationInfo;

    @ManyToOne
    @JoinColumn(name = "fast_track_examination_target_area_id")
    private LkFastTrackExaminationTargetArea fastTrackExaminationTargetArea;

    private String decision;

    public ApplicationAccelerated(Long id, LocalDateTime createdDate) {
            super(id, createdDate);
    }
}
