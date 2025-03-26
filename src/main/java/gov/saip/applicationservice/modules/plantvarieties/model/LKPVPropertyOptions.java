package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "lk_pv_property_options")
@Setter
@Getter
@Where(clause = "is_deleted = 0")
public class LKPVPropertyOptions extends BaseEntity<Long> {

    @Column
    private String code;
    @Column
    private String nameAr;
    @Column
    private String nameEn;
    @Column
    private String example;
    @Column
    private String note;
    @Column
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "lk_pv_property_id")
    private LKPVProperty LKPVProperty;
}
