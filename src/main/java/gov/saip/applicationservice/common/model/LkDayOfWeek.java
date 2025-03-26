package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lk_day_of_week")
@Setter
@Getter
@NoArgsConstructor
public class LkDayOfWeek extends BaseLkEntity<Integer> {

}
