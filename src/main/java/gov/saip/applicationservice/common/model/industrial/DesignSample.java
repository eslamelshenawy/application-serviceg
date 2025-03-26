package gov.saip.applicationservice.common.model.industrial;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.SubClassification;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "design_sample")
public class DesignSample extends BaseEntity<Long> {


    private String name;


    private Long industrialDesignId;

    @OneToMany(mappedBy = "designSampleId", fetch = FetchType.LAZY)
    private List<DesignSampleDrawings> designSampleDrawings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "design_sample_relevants",
            uniqueConstraints = @UniqueConstraint(columnNames={"design_sample_id", "application_relevant_type_id"} ) ,
            joinColumns = @JoinColumn(name = "design_sample_id"),
            inverseJoinColumns = @JoinColumn(name = "application_relevant_type_id"))
    private List<ApplicationRelevantType> applicationRelevantTypes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "design_sample_sub_classifications",
            uniqueConstraints = @UniqueConstraint(columnNames={"design_sample_id", "sub_classification_id"} ) ,
            joinColumns = @JoinColumn(name = "design_sample_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_classification_id"))
    private List<SubClassification> subClassifications;

    private String description;

    private Integer code;
}
