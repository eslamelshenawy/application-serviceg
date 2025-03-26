package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "change_ownership_customers")
@Setter
@Getter
public class ChangeOwnershipCustomer extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "change_ownership_request_id" )
    private ChangeOwnershipRequest changeOwnershipRequest;
    private Long customerId;
    private int ownershipPercentage;
}
