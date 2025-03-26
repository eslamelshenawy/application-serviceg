package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lk_accelerated_type")
@Setter
@Getter
public class LkAcceleratedType extends BaseEntity<Long> {
    @Column
    private String nameAr;

    @Column
    private String nameEn;

    @Column
    private Boolean show;

    @ManyToOne
    @JoinColumn(name = "application_category_id")
    private LkApplicationCategory applicationCategory;

}
