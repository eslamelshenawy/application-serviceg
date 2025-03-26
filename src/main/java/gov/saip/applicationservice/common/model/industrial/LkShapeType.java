package gov.saip.applicationservice.common.model.industrial;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "lk_shapes")
public class LkShapeType extends BaseLkEntity<Long> {
}
