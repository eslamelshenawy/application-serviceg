package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ApplicationTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lk_Nexuo_user")
@Setter
@Getter
public class LkNexuoUser extends BaseEntity<Long> {
    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationTypeEnum type;


}
