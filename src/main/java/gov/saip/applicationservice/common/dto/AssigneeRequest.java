package gov.saip.applicationservice.common.dto;

import lombok.Data;

@Data
public class AssigneeRequest {
    private String userId;
    private Long appId;
    private String requestType;
 }
