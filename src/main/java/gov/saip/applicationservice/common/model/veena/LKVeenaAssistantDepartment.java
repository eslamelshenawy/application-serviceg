package gov.saip.applicationservice.common.model.veena;


import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lk_veena_assistant_department")
@Setter
@Getter
public class LKVeenaAssistantDepartment extends BaseLkEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "veena_department_id")
    private LKVeenaDepartment veenaDepartment;

}
