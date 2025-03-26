package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupportServiceUserDto extends BaseDto<Long> implements Serializable {
    private Long applicationSupportServicesTypeId;
    private String userName;
    private String userRole;

}
