package gov.saip.applicationservice.common.dto.agency;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.agency.AgentType;
import lombok.Data;

@Data
public class LkAgentTypeDto extends BaseDto<Integer> {
    private AgentType code;
    private String typeEn;
    private String typeAr;
    private Integer allowedApplicationsCountYearly;
}
