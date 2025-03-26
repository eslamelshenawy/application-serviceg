package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "lk_support_service_type")
@Setter
@Getter
public class LKSupportServiceType extends BaseEntity<Long> {

    private String descAr;
    private String descEn;

    @Enumerated(EnumType.STRING)
    private SupportServiceType type;

    @Enumerated(EnumType.STRING)
    private SupportedServiceCode code;

    @Column(name = "is_free")
    private Boolean isFree;

}
