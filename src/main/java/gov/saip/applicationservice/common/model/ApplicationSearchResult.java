package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application_search_results")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationSearchResult extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;
    private LocalDate resultDate;
    private String relationOfProtectionElements;
    private Long countryId;
    private String resultLink;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    private String sameDocument;
    private String result;

}
