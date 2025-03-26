package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.SubClassificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_nice_classifications")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationNiceClassification extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Classification classification;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationInfo application;

    @ManyToOne(fetch = FetchType.LAZY)
    private LkClassificationVersion version;

    @Enumerated(EnumType.STRING)
    private SubClassificationType subClassificationType;

    public ApplicationNiceClassification(Long applicationInfoId, Long classificationId, SubClassificationType subClassificationType) {
        this.classification = new Classification(classificationId);
        this.application = new ApplicationInfo(applicationInfoId);
        this.subClassificationType = subClassificationType;
     //   this.application.setNiceClassifications(Arrays.asList(this));
    }
}
