package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "revoke_voluntary")
@Getter
@Setter
public class RevokeVoluntaryRequest extends ApplicationSupportServicesType {


    @Column(columnDefinition = "TEXT")
    private String notes;




    @ManyToMany
    @JoinTable(name = "revoke_voluntary_documents ",
            uniqueConstraints = @UniqueConstraint(columnNames={"revoke_voluntary_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "revoke_voluntary_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;

}
