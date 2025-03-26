package gov.saip.applicationservice.common.model.veena;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "application_veena_classifications")
@Where(clause = "is_deleted = 0 ")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationVeenaClassification extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @ManyToOne
    @JoinColumn(name = "veena_classification_id")
    private LKVeenaClassification veenaClassification;

    @ManyToOne
    @JoinColumn(name = "veena_department_id")
    private LKVeenaDepartment veenaDepartment;

    @ManyToOne
    @JoinColumn(name = "veena_assistant_department_id")
    private LKVeenaAssistantDepartment veenaAssistantDepartment;

}
