package gov.saip.applicationservice.common.model.annual_fees;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.annuel_fees.AnnualFeesTypes;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.appeal.AppealCommitteeOpinion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.List;

@Entity
@Table(name = "annual_fees_request")
@Setter
@Getter
@NoArgsConstructor
public class AnnualFeesRequest extends ApplicationSupportServicesType {

    @Column(name = "service_type")
    @Enumerated(EnumType.STRING)
    private AnnualFeesTypes serviceType;
    @Column(name = "cost_codes")
    private String costCodes;
    @OneToOne
    @JoinColumn(name = "annual_year_id")
    LkAnnualRequestYears annualRequestYears;
    @ManyToOne
    @JoinColumn(name = "post_request_id")
    LkPostRequestReasons postRequestReasons;

}
