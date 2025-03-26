package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseLkEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lk_certificate_types")
@Setter
@Getter
@NoArgsConstructor
public class LkCertificateType extends BaseLkEntity<Integer> {

    @ManyToMany()
    @JoinTable(name = "certificate_types_application_categories",
            uniqueConstraints = @UniqueConstraint(columnNames={"lk_certificate_type_id", "lk_category_id"} ) ,
            joinColumns = @JoinColumn(name = "lk_certificate_type_id"),
            inverseJoinColumns = @JoinColumn(name = "lk_category_id"))
    private List<LkApplicationCategory> applicationCategories;

    @Column(name = "enabled")
    private Boolean enabled;
    

}
