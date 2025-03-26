package gov.saip.applicationservice.common.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnpublishedPublicationIssueProjection {
    private Long issueId;
    private Long issueNumber;
    private Long numberOfApplications;
    private String issuingDayCode;
    private LocalDate issuingDate;
    private LkPublicationIssueStatus lkPublicationIssueStatus;
    
    public UnpublishedPublicationIssueProjection(Long issueId, Long issueNumber, Long numberOfApplications, LocalDateTime issuingDateTime, LkPublicationIssueStatus lkPublicationIssueStatus) {
        this.issueId = issueId;
        this.issueNumber = issueNumber;
        this.numberOfApplications = numberOfApplications;
        this.issuingDate = issuingDateTime.toLocalDate();
        this.issuingDayCode = issuingDateTime.getDayOfWeek().name();
        this.lkPublicationIssueStatus = lkPublicationIssueStatus;
    }

}
