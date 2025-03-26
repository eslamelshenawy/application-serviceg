package gov.saip.applicationservice.common.model.supportService;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_support_service_comment")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationSupportServicesTypeComment extends BaseEntity<Long> {

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_support_services_type_id")
    private ApplicationSupportServicesType applicationSupportServicesType;
}
