package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lk_application_services")
@Setter
@Getter
public class LkApplicationService extends BaseEntity<Long> {

    @Column
    private String code;

    @Column
    private String nameAr;

    @Column
    private String nameEn;
    @Column
    private String operationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private LkApplicationCategory category;
}
