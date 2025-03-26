package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "agent_substitution_request")
@Setter
@Getter
@NoArgsConstructor
public class AgentSubstitutionRequest extends ApplicationSupportServicesType {


    @OneToOne
    @JoinColumn(name = "lk_support_service_type_id", updatable = false)
    private LKSupportServiceType lkSupportServiceType;

    @ManyToOne
    @JoinColumn(name = "delegation_document_id")
    private Document delegationDocument;

    @ManyToOne
    @JoinColumn(name = "eviction_document_id")
    private Document evictionDocument;

    @Column(name="customer_id")
    private Long customerId;

}
