package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "application_priority")
@Setter
@Getter
public class ApplicationPriority extends BaseEntity<Long> {

    @Column
    private String priorityApplicationNumber;

    @Column
    private Long countryId;
    @Column
    private Boolean isExpired;
    @Column
    private LocalDate filingDate;

    @Column
    private String applicationClass;

    @Column
    private Boolean provideDocLater = Boolean.FALSE;

    @OneToOne
    @JoinColumn(name = "priority_document_id", referencedColumnName = "id")
    private Document priorityDocument;

    @OneToOne
    @JoinColumn(name = "translated_document_id", referencedColumnName = "id")
    private Document translatedDocument;

    @Column
    private String dasCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;

    @ManyToOne
    @JoinColumn(name = "priority_status_id")
    private LkApplicationPriorityStatus priorityStatus;
    @Column(columnDefinition = "TEXT")
    private String comment;

}
