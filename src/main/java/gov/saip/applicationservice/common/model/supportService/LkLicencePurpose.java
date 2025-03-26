package gov.saip.applicationservice.common.model.supportService;

import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "lk_licence_purposes")
public class LkLicencePurpose extends BaseLkEntity<Long> {
}
