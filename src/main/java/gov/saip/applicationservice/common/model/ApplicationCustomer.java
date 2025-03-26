package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.dto.ApplicationCustomerData;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.CustomerApplicationAccessLevel;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"application_id", "customerId", "customerCode"})
})
@Setter
@Getter
@NoArgsConstructor
public class ApplicationCustomer  extends BaseEntity<Long>  {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @Column(name = "application_customer_type")
    @Enumerated(EnumType.STRING)
    private ApplicationCustomerType customerType;

    private Long customerId;

    private String customerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_request_id")
    private TrademarkAgencyRequest agencyRequest;

    @Column(name = "customer_access_level")
    @Enumerated(EnumType.STRING)
    private CustomerApplicationAccessLevel customerAccessLevel;

    public ApplicationCustomer(ApplicationInfo application, ApplicationCustomerType customerType, Long customerId, String customerCode) {
        this.application = application;
        this.customerType = customerType;
        this.customerId = customerId;
        this.customerCode = customerCode;
    }

    public ApplicationCustomer(ApplicationCustomerData data) {
        this.application = data.getApplicationInfo();
        this.customerType = data.getCustomerType();
        this.customerId = data.getCustomerId();
        this.customerCode = data.getCustomerCode();
        this.customerAccessLevel = data.getCustomerAccessLevel();
        this.agencyRequest = data.getTrademarkAgencyRequest();
    }
}
