package gov.saip.applicationservice.common.model.trademark;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "lk_mark_types")
@Where(clause = "is_deleted = 0")
public class LkMarkType extends BaseLkEntity<Integer> {

    private int isDeleted;
}
