package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "lk_publication_issue_status")
@Setter
@Getter
@Accessors(chain = true)
public class LkPublicationIssueStatus extends BaseLkEntity<Integer> {

}


