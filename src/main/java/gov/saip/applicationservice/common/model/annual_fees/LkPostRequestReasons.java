package gov.saip.applicationservice.common.model.annual_fees;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lk_post_request_reasons")
@Setter
@Getter
@NoArgsConstructor
public class LkPostRequestReasons extends BaseLkEntity<Long> {

}
