package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "terms_and_conditions")
@Setter
@Getter
public class TermsAndConditions extends BaseEntity<Long> {
    @Column
    private String titleAr;

    @Column
    private String titleEn;

    @Column
    private String bodyAr;

    @Column
    private String bodyEn;

    @Column
    private String link;

    @Column
    private Long sorting;

    @ManyToOne
    @JoinColumn(name = "application_category_id")
    private LkApplicationCategory applicationCategory;

    private Long requestTypeId;
}
