package gov.saip.applicationservice.modules.plantvarieties.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "lk_vegetarian_types")
public class LkVegetarianTypes extends BaseEntity<Long> {
    private String code;
    private String nameAr;
    private String nameEn;
    private String scientificName;
    private Long protectionPeriod;
    private Long marketingPeriodInKsa;
    private Long marketingPeriodOutKsa;
    private Boolean isActive;
    private Long codeNumber;

    public LkVegetarianTypes(Long aLong) {
        super(aLong);
    }
}
