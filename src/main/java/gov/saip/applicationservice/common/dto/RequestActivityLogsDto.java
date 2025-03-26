package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.common.enums.RequestActivityLogEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RequestActivityLogsDto {

    private RequestActivityLogEnum taskDefinitionKey;

    private String taskNameEn;

    private String taskNameAr;

    private LocalDateTime created;

    private String assignee;

    private String statusCode;

    private String documentIds;

    private String notes;

    private String action;

    private String oldAssignee;

    private Long applicationId;

    private Long requestId;

}
