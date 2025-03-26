package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseLkEntity;
import gov.saip.applicationservice.common.enums.eqm.EqmRatingValueType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lk_task_eqm_items")
@Setter
@Getter
@NoArgsConstructor
public class LkTaskEqmItem extends BaseLkEntity<Integer> {
    @Enumerated(EnumType.STRING)
    private EqmRatingValueType ratingValueType  = EqmRatingValueType.INTEGER;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "task_eqm_type_items",
            joinColumns = @JoinColumn(name = "lk_task_eqm_item_id"),
            inverseJoinColumns = @JoinColumn(name = "lk_task_eqm_type_id")
    )
    private List<LkTaskEqmType> types;


    @Column
    private Boolean shown;
}
