package gov.saip.applicationservice.common.model.trademark;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "lk_tag_type_desc")
public class LkTagTypeDesc extends BaseLkEntity<Integer> {

}
