package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "dus_testing_documents")
@Where(clause = "is_deleted = 0")
@Setter
@Getter
public class DUSTestingDocument extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;
    private Long countryId;
}
