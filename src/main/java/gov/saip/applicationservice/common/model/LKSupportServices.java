package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "lk_support_services")
@Setter
@Getter
@Where(clause = "is_deleted = 0")
@NoArgsConstructor
public class LKSupportServices extends BaseEntity<Long> {

    private String nameAr;
    private String nameEn;
    private String descAr;
    private String descEn;
    //we need to move it to new tabe to save the hostory of cost
    private int cost;

    @Enumerated(EnumType.STRING)
    private SupportServiceType code;


    @ManyToMany()
    @JoinTable(name = "support_service_application_categories",
            uniqueConstraints = @UniqueConstraint(columnNames={"support_service_id", "category_id"} ) ,
            joinColumns = @JoinColumn(name = "support_service_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<LkApplicationCategory> applicationCategories;
    
    @ManyToMany
    @JoinTable(name = "support_service_type_status",
            uniqueConstraints = @UniqueConstraint(columnNames={"lk_support_service_type_id", "lk_support_service_status_id"} ) ,
            joinColumns = @JoinColumn(name = "lk_support_service_type_id"),
            inverseJoinColumns = @JoinColumn(name = "lk_support_service_status_id"))
    private List<LKSupportServiceRequestStatus> supportServiceRequestStatuses = new ArrayList<>();
    
    
    
    public LKSupportServices(Long id) {
        super(id);
    }

}
