package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "lk_applicant_category")
@Setter
@Getter
    public class LkApplicantCategory extends BaseEntity<Long> {
    @Column
    private String applicantCategoryNameAr;

    @Column
    private String applicantCategoryNameEn;

    @Column
    private String saipCode;






}
