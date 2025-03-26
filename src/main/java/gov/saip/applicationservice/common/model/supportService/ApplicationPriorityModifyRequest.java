package gov.saip.applicationservice.common.model.supportService;

import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "application_priority_modify_request")
@Getter
@Setter
@NoArgsConstructor

public class ApplicationPriorityModifyRequest extends ApplicationSupportServicesType {

    @OneToMany(mappedBy = "applicationPriorityModifyRequest")
    @OrderBy("id ASC")
    private List<ApplicationPriorityModifyRequestDetails> applicationPriorityModifyRequestDetails;

    @Column
    private boolean isRequestUpdated = false;
}
