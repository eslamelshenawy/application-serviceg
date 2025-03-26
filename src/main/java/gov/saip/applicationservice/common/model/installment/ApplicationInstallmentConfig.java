package gov.saip.applicationservice.common.model.installment;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application_installments_config")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationInstallmentConfig extends BaseEntity<Long> {

    @Column(name = "application_category")
    @Enumerated(EnumType.STRING)
    private ApplicationCategoryEnum applicationCategory;

    @Column(name = "installment_type")
    @Enumerated(EnumType.STRING)
    private InstallmentType installmentType;

    @Column(name = "last_running_date")
    private LocalDateTime lastRunningDate;

    @Column(name = "payment_interval_years")
    private Integer paymentIntervalYears;

    @Column(name = "notification_duration")
    private Integer notificationDuration;

    @Column(name = "payment_duration")
    private Integer paymentDuration;

    @Column(name = "grace_duration")
    private Integer graceDuration;

    @Column(name = "open_renewal_duration")
    private Integer openRenewalDuration;

    @Column(name = "desertion_duration")
    private Integer desertionDuration;

    @OneToMany( mappedBy = "installmentConfig", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<InstallmentConfigType> installmentConfigTypes;

    @ManyToOne
    @JoinColumn(name = "application_desertion_status_id", nullable = true)
    private LkApplicationStatus applicationDesertionStatus;

    private String billRequestTypeSaipCodePenalty;
    private String billRequestTypeSaipCode;
    private String publicationBillRequestTypeSaipCode;
}
