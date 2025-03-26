package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.CustomerExtClassifyEnum;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "customer_ext_classify" , uniqueConstraints = @UniqueConstraint(columnNames = {"customer_ext_classify_type", "application_id"}))
@Setter
@Getter
public class CustomerExtClassify extends BaseEntity<Long> {

    @Column(name = "customer_ext_classify_type")
    @Enumerated(EnumType.STRING)
    private CustomerExtClassifyEnum customerExtClassifyType;

    @Column
    private Long customerId;

    @Column
    private LocalDate  duration;

    @Column
    private Integer durationDays;

    @Column
    private String  notes;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationInfo applicationInfo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerExtClassify" ,cascade = CascadeType.REMOVE)
    private List<CustomerExtClassifyComments> customerExtClassifyComments = new ArrayList<>();

}
