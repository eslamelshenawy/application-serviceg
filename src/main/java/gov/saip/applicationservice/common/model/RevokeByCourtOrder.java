package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.common.enums.RevokeByCourtOrderSuspensionDuration;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "revoke_by_court_order")
@Getter
@Setter
public class RevokeByCourtOrder extends ApplicationSupportServicesType {
    @Column(name="notes")
    private String notes;

    @Column(name="court_number")
    private String courtNumber;

    @Column(name="court_name")
    private String courtName;

    @Column(name="ruling_date")
    private LocalDateTime rulingDate;

    @Column(name="suspension_duration")
    @Enumerated(EnumType.STRING)
    private RevokeByCourtOrderSuspensionDuration suspensionDuration;

    @Column(name="duration_days")
    private Long durationDays;

    @Column(name="duration_months")
    private Long durationMonths;

    @Column(name="duration_years")
    private Long durationYears;

    @ManyToMany
    @JoinTable(name = "revoke_by_court_order_documents ",
            uniqueConstraints = @UniqueConstraint(columnNames = {"revoke_by_court_order_id", "document_id"}),
            joinColumns = @JoinColumn(name = "revoke_by_court_order_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents;

}