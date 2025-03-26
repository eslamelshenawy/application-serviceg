package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "publication_issue_application_publication")
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PublicationIssueApplicationPublication extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn
    private PublicationIssue publicationIssue;

    @ManyToOne
    @JoinColumn
    private ApplicationPublication applicationPublication;
}