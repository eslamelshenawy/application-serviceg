package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.CommenterType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_ext_classify_comments")
@Setter
@Getter
public class CustomerExtClassifyComments extends BaseEntity<Long> {

    @Column
    private String commenterName;

    @Column
    @Enumerated(EnumType.STRING)
    private CommenterType commenterType;

    @Column
    private LocalDateTime commentDate;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cust_ext_classify_parent_comment_id")
    private CustomerExtClassifyComments parentCustExtComment;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentCustExtComment", cascade = CascadeType.REMOVE)
    private List<CustomerExtClassifyComments> childrenCustExtComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cust_ext_classify_id")
    private CustomerExtClassify customerExtClassify;
}
