package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.supportService.SupportServiceReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application_support_services_type")
@Setter
@Getter
//@Where(clause = "is_deleted = 0 AND ( migration_stage = 0 or migration_stage is null )  ")
@Inheritance(strategy=InheritanceType.JOINED)
@NoArgsConstructor
public class ApplicationSupportServicesType extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "application_info_id")
    private ApplicationInfo applicationInfo;

    @ManyToOne
    @JoinColumn(name = "lk_support_service_type_id", updatable = false)
    private LKSupportServices lkSupportServices;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private SupportServicePaymentStatus paymentStatus = SupportServicePaymentStatus.UNPAID;

    @OneToMany(mappedBy = "supportServicesType")
    private List<SupportServiceReview> supportServiceReviews;

    @ManyToMany
    @JoinTable(name = "support_services_type_applications",
            uniqueConstraints = @UniqueConstraint(columnNames={"application_support_services_type_id", "application_id"} ) ,
            joinColumns = @JoinColumn(name = "application_support_services_type_id"),
            inverseJoinColumns = @JoinColumn(name = "application_id"))
    private List<ApplicationInfo> applications = new ArrayList<>();

    @Column(name = "requestNumber", unique = true, updatable = false)
    private String requestNumber;

    @ManyToOne
    @JoinColumn(name = "request_status")
    private LKSupportServiceRequestStatus  requestStatus;

    private String createdByCustomerCode;

    @Column(name = "process_request_id")
    private Long processRequestId;

    @Column(name = "migration_stage")
    private Integer migrationStage;

    public ApplicationSupportServicesType(Long id) {super(id);}
}
