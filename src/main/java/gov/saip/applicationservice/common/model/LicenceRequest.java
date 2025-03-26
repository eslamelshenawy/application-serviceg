package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.LicencePurposeEnum;
import gov.saip.applicationservice.common.enums.LicenceTypeEnum;
import gov.saip.applicationservice.common.model.supportService.LkLicencePurpose;
import gov.saip.applicationservice.common.model.supportService.LkLicenceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "licence_request")
@Setter
@Getter
public class LicenceRequest extends ApplicationSupportServicesType {

    @Enumerated(EnumType.STRING)
    private LicenceTypeEnum licenceTypeEnum;
    @ManyToOne
    @JoinColumn(name = "licence_type_id")
    private LkLicenceType lkLicenceType;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private LicencePurposeEnum licencePurpose;

    @ManyToOne
    @JoinColumn(name = "licence_purpose_id")
    private LkLicencePurpose lkLicencePurpose;

    @ManyToOne
    @JoinColumn(name = "support_document_id")
    private Document contractDocument;

    @ManyToOne
    @JoinColumn(name = "poa_document_id")
    private Document poaDocument;

    @ManyToOne
    @JoinColumn(name = "updated_contract_document_id")
    private Document updatedContractDocument;

    @ManyToOne
    @JoinColumn(name = "compulsory_license_document_id")
    private Document compulsoryLicenseDocument;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    @Enumerated(EnumType.STRING)
    private ApplicantType applicantType;

    private String agencyRequestNumber;

   @ManyToMany
    @JoinTable(name = "regions_license_request",
            uniqueConstraints = @UniqueConstraint(columnNames = {"licence_request_id", "region_id"}),
            joinColumns = @JoinColumn(name = "licence_request_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private List<LkRegions> regions;

    @ManyToMany
    @JoinTable(name = "licence_request_documents ",
            uniqueConstraints = @UniqueConstraint(columnNames = {"licence_request_id", "document_id"}),
            joinColumns = @JoinColumn(name = "licence_request_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;

    @ManyToOne
    @JoinColumn(name = "canceled_contract_document_id")
    private Document canceledContractDocument;

    private Integer licenceValidityNumber;

    @ManyToOne
    @JoinColumn(name = "main_licence_request_id")
    private LicenceRequest mainLicenceRequest;


    public LicenceRequest() {
    }

    public LicenceRequest(Long id) {
        super(id);
    }
}
