package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lk_task_eqm_types")
@Setter
@Getter
@NoArgsConstructor
public class LkTaskEqmType extends BaseLkEntity<Integer> {

    @ManyToMany(mappedBy = "types")
    private List<LkTaskEqmItem> items;

}
