package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "lk_classification_versions")
public class LkClassificationVersion extends BaseLkEntity<Integer> {
    @ManyToOne
    @JoinColumn(name = "category_id")
    private LkApplicationCategory category;

    private int isDeleted;

    public LkClassificationVersion(Integer id) {
        this.setId(id);
    }

    public LkClassificationVersion() {

    }
}
