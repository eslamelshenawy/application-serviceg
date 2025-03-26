package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.ChangeOwnershipApplicantTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "revoke_licence_request")
@Setter
@Getter
public class RevokeLicenceRequest extends ApplicationSupportServicesType {

    @OneToOne
    @JoinColumn(name = "licence_request_id")
    private LicenceRequest licenceRequest;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    private ApplicantType applicantType;

    @Column(name = "old_owner_id")
    private Long oldOwnerId;

    private String agencyRequestNumber;

    @ManyToMany
    @JoinTable(name = "revoke_licence_request_documents ",
            uniqueConstraints = @UniqueConstraint(columnNames = {"revoke_licence_request_id", "document_id"}),
            joinColumns = @JoinColumn(name = "revoke_licence_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;

}
