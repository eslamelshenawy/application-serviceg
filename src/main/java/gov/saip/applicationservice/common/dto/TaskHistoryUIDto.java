package gov.saip.applicationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistoryUIDto {
    String id;
    String taskNameEn;
    String taskNameAr;
    String startTime;
    String endTime;
    long duration;
    String assignee;
    String created;
    String due;
    Long priority;
    String processInstanceId;
    String taskDefinitionKey;
    String action;
    String notes;
    String comments;
    String rejectReason;
    ApplicationAcceleratedLightDto applicationAccelerated;
    String requestTypeCode;
    String nameEn;
    String nameAr;
    String statusCode;
    private GenericLookupDto previousStatus;
    private GenericLookupDto newStatus;
    private List<DocumentDto> documents;
    private String createdByCustomerCode;
    private Map<String,Object> additionalData;
    private boolean showReportButton;
    private boolean serviceLazyLoaded;
    private LocalDateTime actionDate;
    private boolean lastAction;
    private LocalDateTime createdDate;
    private String requestNumber;
}
