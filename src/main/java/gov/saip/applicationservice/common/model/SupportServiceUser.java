package gov.saip.applicationservice.common.model;

import gov.saip.applicationservice.base.model.BaseEntity;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "support_service_users")
public class SupportServiceUser extends BaseEntity<Long> {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "application_support_service_type_id")
    private ApplicationSupportServicesType applicationSupportServicesType;
    private String userName;
    @Enumerated(EnumType.STRING)
    private ApplicationUserRoleEnum userRole;

}
