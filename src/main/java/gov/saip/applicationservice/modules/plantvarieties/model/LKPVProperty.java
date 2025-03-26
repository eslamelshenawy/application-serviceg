package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVExcellence;
import gov.saip.applicationservice.modules.plantvarieties.enums.PVPropertyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
@Table(name = "lk_pv_property")
public class LKPVProperty extends BaseEntity<Long> {
    private String code;
    private String nameAr;
    private String nameEn;
    private Boolean isActive;
    @Column
    @Enumerated(EnumType.STRING)
    private PVExcellence excellence;
    @Column
    @Enumerated(EnumType.STRING)
    private PVPropertyType type;
    @ManyToOne
    @JoinColumn(name = "lk_vegetarian_types_id")
    private LkVegetarianTypes lkVegetarianType;
    public LKPVProperty(Long aLong) {
        super(aLong);
    }

}

