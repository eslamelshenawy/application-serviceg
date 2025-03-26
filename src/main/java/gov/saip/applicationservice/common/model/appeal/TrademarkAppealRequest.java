package gov.saip.applicationservice.common.model.appeal;

import gov.saip.applicationservice.common.enums.support_services_enums.TrademarkAppealRequestType;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trademark_appeal_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrademarkAppealRequest extends ApplicationSupportServicesType {
    
    @Enumerated(EnumType.STRING)
    @Column
    private TrademarkAppealRequestType appealRequestType;

    @Column(columnDefinition = "TEXT")
    private String appealReason;

    @Column(columnDefinition = "TEXT")
    private String departmentReply;

    @Column(columnDefinition = "TEXT")
    private String finalDecisionNotes;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_services_id")
    private ApplicationSupportServicesType supportServicesType;

    @ManyToMany
    @JoinTable(name = "trademark_appeal_request_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"trademark_appeal_request_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "trademark_appeal_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;
    
}
