package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lk_application_category")
@Setter
@Getter
    public class LkApplicationCategory extends BaseEntity<Long> {

    @Column
    private String applicationCategoryDescAr;

    @Column
    private String applicationCategoryDescEn;
    @Column
    private boolean applicationCategoryIsActive;
    @Column
    private String saipCode;

    public LkApplicationCategory(Long aLong, String applicationCategoryDescAr, String applicationCategoryDescEn, String saipCode) {
        super(aLong);
        this.applicationCategoryDescAr = applicationCategoryDescAr;
        this.applicationCategoryDescEn = applicationCategoryDescEn;
        this.saipCode = saipCode;
    }

    public LkApplicationCategory(Long aLong) {
        super(aLong);
    }
    public LkApplicationCategory(Long aLong, String saipCode) {
        super(aLong);
        this.saipCode = saipCode;
    }

    @Column
    private String abbreviation;


    public LkApplicationCategory() {

    }
}
