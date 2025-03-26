package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.common.enums.ApplicantType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "opposition_revoke_licence_request")
@Setter
@Getter
public class OppositionRevokeLicenceRequest extends ApplicationSupportServicesType {

    @OneToOne
    @JoinColumn(name = "revoke_licence_request_id")
    private RevokeLicenceRequest revokeLicenceRequest;

    @Column(columnDefinition = "TEXT")
    private String objectionReason ;

    @ManyToMany
    @JoinTable(name = "opposition_revoke_licence_request_documents ",
            uniqueConstraints = @UniqueConstraint(columnNames = {"opposition_revoke_licence_request_id", "document_id"}),
            joinColumns = @JoinColumn(name = "opposition_revoke_licence_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;


    @Column(columnDefinition = "TEXT")
    private String courtDocumentNotes;

    @ManyToMany
    @JoinTable(name = "opposition_revoke_licence_request_court_documents ",
            uniqueConstraints = @UniqueConstraint(columnNames = {"opposition_revoke_licence_request_id", "document_id"}),
            joinColumns = @JoinColumn(name = "opposition_revoke_licence_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> courtDocuments;


}
