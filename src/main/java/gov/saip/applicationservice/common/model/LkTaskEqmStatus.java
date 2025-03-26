package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lk_task_eqm_status")
@Setter
@Getter
@Accessors(chain = true)
public class LkTaskEqmStatus extends BaseLkEntity<Integer> {

}