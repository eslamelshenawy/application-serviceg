package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ReportsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_checking_reports")
public class ApplicationCheckingReport extends BaseEntity<Long> {
    @Column(name = "document_id")
    private Long documentId;
    @Enumerated(EnumType.STRING)
    private ReportsType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ApplicationSupportServicesType supportServicesType;

}
