package gov.saip.applicationservice.common.model.agency;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application_agents")
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApplicationAgent extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @Column(name="customer_id")
    private Long customerId;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationAgentStatus status = ApplicationAgentStatus.ACTIVE;

    private LocalDate expirationDate;



    @ManyToMany
    @JoinTable(name = "application_agent_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"application_agent_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "application_agent_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> applicationAgentDocuments = new ArrayList<>();


}
