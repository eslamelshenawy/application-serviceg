package gov.saip.applicationservice.common.model.patent;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "protection_elements")
@Entity
@Getter
@Setter
public class ProtectionElements extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ProtectionElements parentElement;

    @OneToMany(mappedBy = "parentElement", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<ProtectionElements> dependentElements;

    @Column(name = "description")
    private String description;

    @Column(name = "is_english")
    private Boolean isEnglish;

    @ManyToOne()
    @JoinColumn(name = "document_id")
    private Document document;
}
