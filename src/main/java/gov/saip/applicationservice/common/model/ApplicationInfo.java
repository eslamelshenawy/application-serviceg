package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import gov.saip.applicationservice.common.model.patent.ProtectionElements;
import gov.saip.applicationservice.common.model.veena.ApplicationVeenaClassification;
import gov.saip.applicationservice.listner.ApplicationInfoEntityListener;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applications_info")
@Setter
@Where(clause = "is_deleted = 0 AND ( migration_stage = 0 or migration_stage is null )  ")
//@Where(clause = "is_deleted = 0")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(ApplicationInfoEntityListener.class)
public class ApplicationInfo extends BaseEntity<Long> {

    @Column
    private String email;

    @Column(columnDefinition = "BIGSERIAL", insertable = false)
    private Long serial;

    @JoinColumn(name = "enterprise_size_id")
    @ManyToOne
    private LKMonshaatEnterpriseSize enterpriseSize;

    @Column
    private String applicationNumber;

    @Column
    private String mobileCode;

    @Column
    private String mobileNumber;

    @Column
    private String titleEn;

    @Column
    private String titleAr;

    @Column
    @Builder.Default
    private Boolean partialApplication = Boolean.FALSE;

    @Column
    private String partialApplicationNumber;

    @Column
    private Boolean nationalSecurity = Boolean.FALSE;


    @Column
    private String ipcNumber;

    @Column(updatable = false)
    private Long createdByUserId;


    @Column
    private Boolean substantiveExamination = Boolean.FALSE;

    @Column
    private Boolean accelerated = Boolean.FALSE;
    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "application_status_id", nullable = false)
    private LkApplicationStatus applicationStatus;

    @OneToMany(mappedBy = "applicationInfo")
    @OrderBy("id ASC")
    private List<ApplicationPriority> applicationPriority;

    @OneToOne(mappedBy = "applicationInfo")
    private ApplicationAccelerated applicationAccelerated;

    @ManyToOne
    @JoinColumn(name = "lk_category_id")
    private LkApplicationCategory category;

    @OneToMany(mappedBy = "applicationInfo", fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "applicationInfo")
    private List<ApplicationRelevantType> applicationRelevantTypes;

    @Column
    private LocalDateTime filingDate;
    
    @Column
    private String filingDateHijri;
    
    @Column
    private LocalDateTime grantDate;
    
    @Column
    private String grantDateHijri;
    
    @Column
    private LocalDateTime registrationDate;
    
    @Column
    private String registrationDateHijri;

    @Column
    private boolean byHimself;

    @ManyToOne()
    @JoinColumn(name = "classification_unit_id")
    private LkClassificationUnit classificationUnit;

    @Column(name = "classification_notes", nullable = true)
    private String classificationNotes;

//    @ManyToOne
//    @JoinColumn(name = "step_id")
//    private LkStep step;


    @OneToMany(mappedBy = "applicationInfo")
    private List<ApplicationSubClassification> applicationSubClassifications;

    @Column(name = "pages_number", nullable = true)
    private Integer normalPagesNumber; //Michael get application info payment

    @Column(name = "claim_pages_number", nullable = true)
    private Integer claimPagesNumber; //Michael get application info payment

    @Column(name = "shapes_number", nullable = true)
    private Integer shapesNumber; //Michael get application info payment

    @Column(name = "total_checking_fee", nullable = true)
    private Long totalCheckingFee; //Michael get application info payment

    @Column(name = "problem",columnDefinition = "TEXT")
    private String problem;
    @Column(name = "problem_solution",columnDefinition = "TEXT")
    private String problemSolution;
    @Column(name = "name_approved")
    private Boolean nameApproved = Boolean.TRUE;
    @Column(name = "name_notes")
    private String nameNotes;
    @Column(name = "summery_approved")
    private Boolean summeryApproved = Boolean.TRUE;
    @Column(name = "summery_notes")
    private String summeryNotes;
    @Column(name = "full_summery_approved")
    private Boolean fullSummeryApproved = Boolean.TRUE;
    @Column(name = "full_summery_notes")
    private String fullSummeryNotes;
    @Column(name = "protection_elements_approved")
    private Boolean protectionElementsApproved = Boolean.TRUE;
    @Column(name = "protection_elements_notes")
    private String protectionElementsNotes;
    @Column(name = "geda_approved")
    private Boolean gedaApproved = Boolean.TRUE;
    @Column(name = "geda_notes")
    private String gedaNotes;
    @Column(name = "illustrations_approved")
    private Boolean illustrationsApproved = Boolean.TRUE;
    @Column(name = "illustrations_notes")
    private String illustrationsNotes;
    @Column(name = "innovative_step_approved")
    private Boolean innovativeStepApproved = Boolean.TRUE;
    @Column(name = "innovative_step_notes")
    private String innovativeStepNotes;
    @Column(name = "industrial_applicable_approved")
    private Boolean industrialApplicableApproved = Boolean.TRUE;
    @Column(name = "industrial_applicable_notes")
    private String industrialApplicableNotes;

    @OneToMany(mappedBy = "application")
    private List<ApplicationAgent> agent;

    @OneToMany(mappedBy = "application")
    private List<ProtectionElements> protectionElements;

    @OneToMany(mappedBy = "application",cascade = CascadeType.ALL)
    private List<ApplicationNiceClassification> niceClassifications = new ArrayList<>();

    @OneToMany(mappedBy = "application",cascade = CascadeType.ALL)
    private List<ApplicationVeenaClassification> veenaClassifications = new ArrayList<>();

    private LocalDateTime publicationDate;

    @OneToMany(mappedBy = "application")
    private List<ApplicationCustomer> applicationCustomers;

    @OneToMany(mappedBy = "application")
    private List<ExaminerConsultation> examinerConsultations;
    @OneToMany(mappedBy = "application")
    private List<ApplicationSnapShot> applicationSnapShots;

    @OneToMany(mappedBy ="application" )
    private List<TrademarkApplicationModification> trademarkApplicationModification;


    @Column(name = "end_of_protection_date")
    private LocalDateTime endOfProtectionDate;

    @Column(name = "is_plt")
    private Boolean pltRegisteration = Boolean.FALSE;

    @ManyToOne()
    @JoinColumn(name = "plt_document")
    private Document pltDocument;

    @Column(name = "plt_description", nullable = true)
    private String pltDescription;

    @Column(name = "id_old")
    private String idOld;

    @Column(name = "owner_name_ar" )
    private String ownerNameAr;

    @Column(name = "owner_name_en")
    private String ownerNameEn;

    @Column(name = "owner_address_ar")
    private String ownerAddressAr;

    @Column(name = "owner_address_en")
    private String ownerAddressEn;

    @Column(name = "is_priority_confirmed")
    private Boolean isPriorityConfirmed = Boolean.FALSE;

    @Column
    private LocalDateTime pltFilingDate;

    @Column
    private String grantNumber;
    @Column (name ="parent_elements_count")
    private Long parentElementsCount;
    @Column (name ="children_elements_count")
    private Long childrenElementsCount;
    @Column (name ="application_relevent_type_count")
    private Long unPaidApplicationReleventTypeCount;
    @Column (name ="total_pages_number")
    private Long totalPagesNumber;

    @OneToMany(mappedBy = "applicationInfo")
    private List<ApplicationCheckingReport> checkingReports;

    @Column(name = "process_request_id")
    private Long processRequestId;

    @Column(name = "application_request_number")
    private String applicationRequestNumber;
    @Column(name = "last_internal_user_name")
    private String lastInternalUserName;
    @Column(name = "last_status_modified_date")
    private LocalDateTime lastStatusModifiedDate;
    @Column(name = "last_user_modified_date")
    private LocalDateTime lastUserModifiedDate;

    @Column(updatable = false)
    private Long createdByCustomerId;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationCustomerType createdByCustomerType;

    @Column(name = "migration_stage")
    private Integer migrationStage;


    public void addSubClassification(ApplicationSubClassification t) {
        if (applicationSubClassifications == null) {
            applicationSubClassifications = new ArrayList<>();
        }
        t.setApplicationInfo(this);
        applicationSubClassifications.add(t);
    }

    public ApplicationInfo(Long id) {
        super(id);
    }

}
