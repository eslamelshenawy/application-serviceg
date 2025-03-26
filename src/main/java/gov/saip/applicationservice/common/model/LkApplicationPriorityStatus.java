package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lk_application_priority_status")
@Setter
@Getter
public class LkApplicationPriorityStatus extends BaseEntity<Long> {
    @Column
    private String ipsStatusDescEn;

    @Column
    private String ipsStatusDescAr;
    @Column
    private String code;

}
