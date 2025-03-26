package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TasksUiDto {
    private Long applicationId;
    private String requestId;
    private ApplicationAcceleratedDto applicationAccelerated;
    private Long rowId;
    LocalDateTime requestDate;
    Object requestType;
    Object requestStatus;
    ApplicationStatusDto applicationStatus;
    LKApplicationCategoryDto category;
    Object userSampleDto;
    String requestCode;
    String serviceCode;
    String taskId;
    String taskNameEn;
    String taskNameAr;
    String taskCreated;
    String processDefinitionId;
    String processInstanceId;
    String applicationNumber;
    String applicantNameAr;
    String applicantNameEn;
    String taskDefinitionKey;
    String approvedBy ;
    List<String> actions;
    String mainApplicationRequestId;
    String notes;
    List comments = new ArrayList<>();
    Boolean acceleratedFlag;
    String due;
    String titleAr;
    String titleEn;
    private String applicationSearchCategory;
    private String employeeNameAr;
    private String employeeNameEn;
    private String requestNumber;
    private String applicationRequestNumber;
    private LkSupportServiceRequestStatusDto supportServicesWithNoAppStatus;



}
