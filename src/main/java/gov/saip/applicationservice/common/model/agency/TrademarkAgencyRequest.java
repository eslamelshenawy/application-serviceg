package gov.saip.applicationservice.common.model.agency;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.agency.LegalAgentType;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LKSupportServices;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trademark_agency_requests")
@Setter
@Getter
@NoArgsConstructor
public class TrademarkAgencyRequest extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @ManyToMany
    @JoinTable(name = "tm_agency_requests_documents",
            uniqueConstraints = @UniqueConstraint(columnNames={"agency_request_id", "document_id"} ) ,
            joinColumns = @JoinColumn(name = "agency_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tm_agency_requests_services",
            uniqueConstraints = @UniqueConstraint(columnNames={"agency_request_id", "support_service_id"} ) ,
            joinColumns = @JoinColumn(name = "agency_request_id"),
            inverseJoinColumns = @JoinColumn(name = "support_service_id"))
    private List<LKSupportServices> supportServices = new ArrayList<>();

    @Column(name = "requestNumber", unique = true, updatable = false)
    private String requestNumber;

    @Column(name = "agency_number")
    private String agencyNumber;

    @ManyToOne
    @JoinColumn(name = "request_status_id")
    private LKTrademarkAgencyRequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "client_type_id")
    private LkClientType clientType;

    @Enumerated(EnumType.STRING)
    @Column(name = "agency_type")
    private TrademarkAgencyType agencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "legal_agent_type")
    private LegalAgentType legalAgentType;

    @Column(name = "client_customer_code")
    private String clientCustomerCode;

    @Column(name = "agent_customer_code")
    private String agentCustomerCode;

    private LocalDate startAgency;
    private LocalDate endAgency;
    private String organizationAddress;
    @Column(columnDefinition = "TEXT")
    private String organizationDescription;
    @Column(columnDefinition = "TEXT")
    private String agencyCheckerNotes;
    @Column(name = "agent_type_id")
    private Long agentTypeId;
    private LocalDate agentExpiryDate;
    private String agentIdentityNumber;
    @Column(updatable = false)
    private String checkerUsername;
    @Column(name = "process_request_id")
    private Long processRequestId;
    @Column(name = "returned_request_num")
    private int returnedRequestNum;

    public TrademarkAgencyRequest(Long id) {
        super(id);
    }
}
