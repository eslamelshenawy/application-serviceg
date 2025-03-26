package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application_similar_documents")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationSimilarDocument extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
    private Long countryId;
    private String publicationNumber;
    private String documentNumber;
    private LocalDate documentDate;
    private String documentLink;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
}
