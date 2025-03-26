package gov.saip.applicationservice.common.model.appeal;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.appeal.AppealCheckerDecision;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.List;

@Entity
@Table(name = "appeal_committee_opinion")
@Setter
@Getter
@NoArgsConstructor
public class AppealCommitteeOpinion extends BaseEntity<Long> {

    @OneToOne
    private Document document;

    @Column(name = "appeal_committee_opinion", length = 500)
    private String appealCommitteeOpinion;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppealRequest appealRequest;

}
