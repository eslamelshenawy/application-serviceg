package gov.saip.applicationservice.common.model.supportService;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.support_services_enums.SupportServiceChangeLogDescriptionCode;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "support_service_status_change_log")
@Setter
@Getter
@NoArgsConstructor
public class SupportServiceStatusChangeLog extends BaseEntity<Long>  {

    private String taskDefinitionKey;
    private String taskInstanceId;
    @Enumerated(EnumType.STRING)
    private SupportServiceChangeLogDescriptionCode descriptionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_service_id")
    private ApplicationSupportServicesType supportServicesType;

    @ManyToOne
    @JoinColumn(name = "previous_status_id")
    private LKSupportServiceRequestStatus previousStatus;

    @ManyToOne
    @JoinColumn(name = "new_status_id")
    private LKSupportServiceRequestStatus newStatus;

    public void setNewStatusByCode(String code) {
        newStatus = new LKSupportServiceRequestStatus(code);
    }

    public void setApplicationById(Long id) {
        supportServicesType = new ApplicationSupportServicesType(id);
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append(" taskInstanceId: ").append(taskInstanceId)
                .append(", taskDefinitionKey: ").append(taskDefinitionKey)
                .append(", descriptionCode: ").append(descriptionCode == null ? null : descriptionCode.name())
                .append(", newStatus: ").append(newStatus == null ? null : newStatus.getCode())
                .append(", supportServicesType: ").append(supportServicesType == null ? null : supportServicesType.getId())
                .append("]")
                .toString();
    }
}
