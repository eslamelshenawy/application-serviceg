package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.CustomerApplicationAccessLevel;
import gov.saip.applicationservice.common.model.agency.TrademarkAgencyRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "support_service_customer")
@Setter
@Getter
@NoArgsConstructor
public class SupportServiceCustomer extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_service_id")
    private ApplicationSupportServicesType applicationSupportServices;

    @Column(name = "application_customer_type")
    @Enumerated(EnumType.STRING)
    private ApplicationCustomerType customerType;

    private Long customerId;

    private String customerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_request_id")
    private TrademarkAgencyRequest trademarkAgency;

    @Column(name = "customer_application_access_level")
    @Enumerated(EnumType.STRING)
    private CustomerApplicationAccessLevel customerApplicationAccessLevel;

}