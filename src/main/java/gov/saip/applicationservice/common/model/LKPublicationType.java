package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseLkEntity;

import javax.persistence.*;


@Entity
@Table(name="lk_publication_type")
public class LKPublicationType extends BaseLkEntity<Integer> {



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="application_category_id")
    private LkApplicationCategory applicationCategory;

}
