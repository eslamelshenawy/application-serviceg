package gov.saip.applicationservice.common.model.supportService;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServiceReviewStatus;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "support_service_reviews")
@Setter
@Getter
@NoArgsConstructor
public class SupportServiceReview extends BaseEntity<Long> {

    @Column(columnDefinition = "TEXT")
    private String review;

    @Enumerated(EnumType.STRING)
    private SupportServiceReviewStatus reviewStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_support_services_type_id")
    private ApplicationSupportServicesType supportServicesType;
}
