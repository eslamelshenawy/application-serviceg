package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "application_status_change_log")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationStatusChangeLog extends BaseEntity<Long>  {

    private String taskDefinitionKey;
    private String taskInstanceId;
    private String descriptionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private ApplicationInfo application;

    @ManyToOne
    @JoinColumn(name = "previous_status_id")
    private LkApplicationStatus previousStatus;

    @ManyToOne
    @JoinColumn(name = "new_status_id")
    private LkApplicationStatus newStatus;

    public void setNewStatusByCode(String code) {
        newStatus = new LkApplicationStatus(code);
    }

    public void setApplicationById(Long id) {
        application = new ApplicationInfo(id);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append(" taskInstanceId: ").append(taskInstanceId)
                .append(", taskDefinitionKey: ").append(taskDefinitionKey)
                .append(", descriptionCode: ").append(descriptionCode)
                .append(", newStatus: ").append(newStatus == null ? null : newStatus.getCode())
                .append(", supportServicesType: ").append(application == null ? null : application.getId())
                .append("]")
                .toString();
    }
}
