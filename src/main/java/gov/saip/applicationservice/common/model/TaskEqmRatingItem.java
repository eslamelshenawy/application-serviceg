package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "task_eqm_rating_items")
@Setter
@Getter
public class TaskEqmRatingItem extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_eqm_id")
    private TaskEqm taskEqm;
    @ManyToOne()
    @JoinColumn(name = "task_eqm_item_id")
    private LkTaskEqmItem taskEqmItem;
    private int value;

}
