package gov.saip.applicationservice.common.model.agency;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "trademark_agency_status_change_log")
@Setter
@Getter
@NoArgsConstructor
public class TrademarkAgencyStatusChangeLog extends BaseEntity<Long>  {

    private String taskDefinitionKey;
    private String taskInstanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trademark_agency_request_id")
    private TrademarkAgencyRequest trademarkAgencyRequest;

    @ManyToOne
    @JoinColumn(name = "previous_status_id")
    private LKTrademarkAgencyRequestStatus previousStatus;

    @ManyToOne
    @JoinColumn(name = "new_status_id")
    private LKTrademarkAgencyRequestStatus newStatus;

    public void setTrademarkAgencyId(Long id) {
        trademarkAgencyRequest = new TrademarkAgencyRequest(id);
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append(" taskInstanceId: ").append(taskInstanceId)
                .append(", taskDefinitionKey: ").append(taskDefinitionKey)
                .append(", newStatus: ").append(newStatus == null ? null : newStatus.getCode())
                .append(", supportServicesType: ").append(trademarkAgencyRequest == null ? null : trademarkAgencyRequest.getId())
                .append("]")
                .toString();
    }
}
