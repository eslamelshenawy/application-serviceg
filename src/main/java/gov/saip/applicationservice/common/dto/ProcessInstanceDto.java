package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcessInstanceDto {
    private String id;
    private String definitionId;
    private String businessKey;
    private String caseInstanceId;
    private boolean ended;
    private boolean suspended;
    private String tenantId;
}
