package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "petition_recovery_request")
@Setter
@Getter
public class PetitionRecoveryRequest extends ApplicationSupportServicesType {

    @OneToOne
    @JoinColumn(name = "lk_support_service_type_id")
    private LKSupportServiceType lkSupportServiceType;

    @ManyToOne
    @JoinColumn(name = "eviction_document_id")
    private Document evictionDocument;

    @ManyToOne
    @JoinColumn(name = "recovery_document_id")
    private Document recoveryDocument;

    @Column(columnDefinition = "TEXT")
    private String justification;


    @ManyToMany
    @JoinTable(name = "petition_recovery_request_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"petion_recovery_request_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "petion_recovery_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> justificationDocuments;

}
