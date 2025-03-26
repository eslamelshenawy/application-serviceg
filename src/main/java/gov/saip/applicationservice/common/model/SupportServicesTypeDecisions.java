package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.enums.AssistiveSupportServiceSpecialistDecision;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "support_services_type_decisions")
@Getter
@Setter
public class SupportServicesTypeDecisions extends BaseEntity<Long> {


    @Column(name = "decision")
    @Enumerated(EnumType.STRING)
    private AssistiveSupportServiceSpecialistDecision decision;

    @Column(name = "comment",columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "support_services_type_id")
    private ApplicationSupportServicesType applicationSupportServicesType;
    
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    @Column(name = "to_role")
    private String role;
    @Column(name = "to_customers")
    private String customers;

    @Transient
    private boolean skipChangeStatus;
    @Transient
    private SupportServiceStatusChangeLogDto supportServiceStatusChangeLogDto;



}
