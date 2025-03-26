package gov.saip.applicationservice.common.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "extension_request")
@Setter
@Getter
public class ExtensionRequest extends ApplicationSupportServicesType {

    @OneToOne
    @JoinColumn(name = "lk_support_service_type_id")
    private LKSupportServiceType lkSupportServiceType;

    @Column(name = "extension_phase")
    private String extensionPhase;

}
