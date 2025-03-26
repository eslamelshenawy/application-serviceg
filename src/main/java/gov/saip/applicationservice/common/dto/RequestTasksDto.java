package gov.saip.applicationservice.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    RequestTasksDto {


    Long rowId;
    String taskId;
    String taskNameEn;
    String taskNameAr;
    String taskCreated;
    String processDefinitionId;
    String processInstanceId;
    String taskDefinitionKey;
    String assignee ;
    String due;
    String followUp;
    String lastUpdated;
    String owner;
    Long priority;
    List<String> actions;
    Map<String, Object> endDateAt;
    Boolean isAppealAccepted;
}
