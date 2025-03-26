package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.common.enums.ChangeOwnershipApplicantTypeEnum;
import gov.saip.applicationservice.common.enums.ChangeOwnershipTypeEnum;
import gov.saip.applicationservice.common.enums.DocumentTransferTypeEnum;
import gov.saip.applicationservice.common.enums.OwnerShipTransferTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Entity
@Table(name = "change_ownership_request")
@Setter
@Getter
public class ChangeOwnershipRequest extends ApplicationSupportServicesType {

    @OneToMany(mappedBy = "changeOwnershipRequest" ,cascade = CascadeType.ALL)
    private List<ChangeOwnershipCustomer> changeOwnershipCustomers;

    @Enumerated(EnumType.STRING)
    private ChangeOwnershipTypeEnum changeOwnerShipType;

    @Enumerated(EnumType.STRING )
    private OwnerShipTransferTypeEnum documentTransferType;

    @Enumerated(EnumType.STRING)
    private DocumentTransferTypeEnum ownershipTransferType;

    private long percentageDocPart;
    private Long customerId;

    @Column(name = "old_owner_id")
    private Long oldOwnerId;

    @NotNull
    @PositiveOrZero(message = "participants should be 0 or higher.")
    private int participantsCount;

    @ManyToOne
    @JoinColumn(name = "support_document_id")
    private Document supportDocument;

    @ManyToOne
    @JoinColumn(name = "poa_document_id")
    private Document poaDocument;

    @ManyToOne
    @JoinColumn(name = "waive_document_id")
    private Document waiveDocument;

    @ManyToOne
    @JoinColumn(name = "mm5_document_id")
    private Document mm5Document;

    private String agencyRequestNumber;

    @Enumerated(EnumType.STRING)
    private ChangeOwnershipApplicantTypeEnum applicantType;

    @ManyToMany
    @JoinTable(name = "change_ownership_request_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"change_ownership_request_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "change_ownership_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> changeOwnershipDocuments;

    @ManyToMany
    @JoinTable(name = "change_ownership_request_licenses_waive_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"change_ownership_request_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "change_ownership_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> licensesWaiveDocuments;

    @Column(columnDefinition = "TEXT")
    private String notes;

}
