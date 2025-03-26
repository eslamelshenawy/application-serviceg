package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDto {
    private String id;
    private String jobDefinitionId;
    private OffsetDateTime dueDate;
    private String processInstanceId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String executionId;
    private int retries;
    private String exceptionMessage;
    private String failedActivityId;
    private boolean suspended;
    private int priority;
    private String tenantId;
    private OffsetDateTime createTime;

}
