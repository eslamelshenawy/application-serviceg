package gov.saip.applicationservice.modules.ic.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "integrated_circuits")
@Setter
@Getter
@Where(clause = "is_deleted = 0")
@NoArgsConstructor
public class IntegratedCircuit extends BaseEntity<Long> {
    @OneToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @Column(name = "design_description")
    private String designDescription;

    @Column(name = "design_date")
    private LocalDateTime designDate;

    @Column(name = "is_commercial_exploited")
    private boolean commercialExploited;

    @Column(name = "commercial_exploitation_date")
    private LocalDateTime commercialExploitationDate;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "notify_checker")
    private boolean notifyChecker;

    @Column(name = "approved_name_ar")
    private String approvedNameAr;

    @Column(name = "approved_name_en")
    private String approvedNameEn;

    @Column(name = "first_assignation_date")
    private LocalDateTime firstAssignationDate;
}
