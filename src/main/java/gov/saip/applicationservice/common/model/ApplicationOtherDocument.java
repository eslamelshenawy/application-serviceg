package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_other_documents")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationOtherDocument extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
    private String documentName;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
}
