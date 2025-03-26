package gov.saip.applicationservice.common.model.installment;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.annual_fees.LkPostRequestReasons;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application_installments")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationInstallment  extends BaseEntity<Long> {

    public ApplicationInstallment(Long id) {
        super(id);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @Column(name = "installment_status")
    @Enumerated(EnumType.STRING)
    private InstallmentStatus installmentStatus;

    @ManyToOne
    @JoinColumn(name = "postponed_reason_id")
    LkPostRequestReasons postponedReason;

    @Column(name = "installment_type")
    @Enumerated(EnumType.STRING)
    private InstallmentType installmentType;

    @Column(name = "start_due_date")
    private LocalDateTime startDueDate;


    @Column(name = "end_due_date")
    private LocalDateTime endDueDate;

    @Column(name = "grace_end_date")
    private LocalDateTime graceEndDate;


    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "last_due_date")// to calculate from it the next payment
    private LocalDateTime lastDueDate;

    @Transient
    private BigDecimal amount;
    @Transient
    private BigDecimal penaltyAmount;


    @Column(name = "bill_number")
    private String billNumber;

    @Column(name = "fee_cost")
    private BigDecimal feeCost;
    @Column(name = "penalty_cost")
    private BigDecimal penaltyCost;
    @Column(name = "tax_cost")
    private BigDecimal taxCost;

    @Column(name = "installment_index")
    private int installmentIndex;
    @Column(name = "exception_message", columnDefinition = "TEXT")
    private String exceptionMessage;

    @Column(name = "installmentCount", columnDefinition = "INT DEFAULT 1", nullable = false)
    private int installmentCount = 1;

    @Column(name = "publish_status")
    private String publishStatus;

    @OneToOne
    @JoinColumn(name = "support_service_id")
    private ApplicationSupportServicesType supportService;

    @OneToMany( mappedBy = "applicationInstallment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstallmentNotification> notifications;

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(getFeeCost().doubleValue() + getTaxCost().doubleValue() + getPenaltyCost().doubleValue());
    }
}
