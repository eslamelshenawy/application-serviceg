package gov.saip.applicationservice.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "revoke_products")
@Setter
@Getter
public class RevokeProducts  extends ApplicationSupportServicesType{
    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToMany
    @JoinTable(name = "revoke_products_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"revoke_products_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "revoke_products_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;

    @ManyToMany
    @JoinTable(name = "revoke_products_sub_classifications",
            uniqueConstraints = @UniqueConstraint(columnNames={"revoke_products_id", "sub_classifications_id"} ) ,
            joinColumns = @JoinColumn(name = "revoke_products_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_classifications_id"))
    private List<SubClassification> subClassifications;


}
