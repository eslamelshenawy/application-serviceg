package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "application_sub_classifications")
public class ApplicationSubClassification extends BaseEntity<Long> {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "sub_classification_id")
    private SubClassification subClassification;


}
