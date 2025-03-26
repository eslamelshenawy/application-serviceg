package gov.saip.applicationservice.common.model.supportService;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application_priority_modify_request_details")
@Getter
@Setter
@NoArgsConstructor

public class ApplicationPriorityModifyRequestDetails extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_modify_request_id")
    private ApplicationPriorityModifyRequest applicationPriorityModifyRequest;

    @Column
    private Long countryId;

    @Column
    private String priorityApplicationNumber;

    @Column
    private LocalDate filingDate;

    @Column
    private String applicationClass;

    @OneToOne
    @JoinColumn(name = "priority_document_id", referencedColumnName = "id")
    private Document priorityDocument;

    @OneToOne
    @JoinColumn(name = "translated_document_id", referencedColumnName = "id")
    private Document translatedDocument;

    @Column
    private String dasCode;

    @Column
    private boolean isExpired;

    @Column
    private boolean provideDocLater = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "priority_status_id")
    private LkApplicationPriorityStatus priorityStatus;

    @Column
    private String comment;
}
