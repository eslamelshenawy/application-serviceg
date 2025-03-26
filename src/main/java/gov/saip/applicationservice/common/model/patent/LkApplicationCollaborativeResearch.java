package gov.saip.applicationservice.common.model.patent;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lk_application_collaborative_research")
@Setter
@Getter
public class LkApplicationCollaborativeResearch extends BaseEntity<Long> {
    String nameEn;
    String nameAr;

}
