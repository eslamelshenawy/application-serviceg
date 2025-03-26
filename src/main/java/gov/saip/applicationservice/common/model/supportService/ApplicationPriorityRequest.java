package gov.saip.applicationservice.common.model.supportService;

import gov.saip.applicationservice.common.enums.support_services_enums.ModificationTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "application_priority_request")
@Getter
@Setter
@NoArgsConstructor

public class ApplicationPriorityRequest extends ApplicationSupportServicesType {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private ModificationTypeEnum modifyType;

    @NotNull
    @Column
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Column
    private boolean isRequestUpdated = false;
}

