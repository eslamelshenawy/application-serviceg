package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "petition_request_national_stage")
@Setter
@Getter
@NoArgsConstructor
public class PetitionRequestNationalStage extends ApplicationSupportServicesType{

    @Column
    private String globalApplicationNumber;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @ManyToMany
    @JoinTable(name = "petition_request_national_stage_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"petition_request_national_stage_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "petition_request_national_stage_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> petitionRequestNationalStageDocuments;

}
