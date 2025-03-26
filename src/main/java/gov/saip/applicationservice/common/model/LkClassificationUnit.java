package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "lk_classification_units")
@Where(clause = "is_deleted = 0")
public class LkClassificationUnit extends BaseEntity<Long> {

    private String code;
    @NotEmpty
    private String nameAr;
    @NotEmpty
    private String nameEn;
    private Long idOld;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private LkApplicationCategory category;
    @ManyToOne()
    @JoinColumn(name = "version_id")
    private LkClassificationVersion version;

    @OneToMany(mappedBy = "unit" , cascade = CascadeType.ALL)
    private List<Classification> classifications;

}
