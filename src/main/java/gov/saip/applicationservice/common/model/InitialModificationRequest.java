package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "initial_modification_request")
@Setter
@Getter
public class InitialModificationRequest extends ApplicationSupportServicesType {

    @OneToOne
    @JoinColumn(name = "lk_support_service_type_id")
    private LKSupportServiceType lkSupportServiceType;
}
