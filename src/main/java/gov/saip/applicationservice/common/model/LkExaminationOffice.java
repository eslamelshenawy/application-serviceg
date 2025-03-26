package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lk_examination_offices")
@Setter
@Getter
@NoArgsConstructor
public class LkExaminationOffice extends BaseLkEntity<Integer> {
    

}
