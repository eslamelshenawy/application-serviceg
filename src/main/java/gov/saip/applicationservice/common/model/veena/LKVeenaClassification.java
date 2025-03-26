package gov.saip.applicationservice.common.model.veena;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.base.model.BaseLkEntity;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lk_veena_classification")
@Setter
@Getter
public class LKVeenaClassification extends BaseLkEntity<Long> {

}
