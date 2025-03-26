package gov.saip.applicationservice.common.model;


import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "publication_issue")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationIssue extends BaseEntity<Long> {
    private Long issueNumber;

    private LocalDateTime issuingDate;
    
    private String issuingDateHijri;
    
    private String nameEn;

    private String nameAr;

    @ManyToOne
    @JoinColumn
    private LkApplicationCategory lkApplicationCategory;
    
    @ManyToOne
    @JoinColumn
    private LkPublicationIssueStatus lkPublicationIssueStatus;

    public PublicationIssue(Long issueNumber, LocalDateTime issuingDate, String issuingDateHijri, LkApplicationCategory lkApplicationCategory) {
        this.issueNumber = issueNumber;
        this.issuingDate = issuingDate;
        this.issuingDateHijri = issuingDateHijri;
        this.lkApplicationCategory = lkApplicationCategory;
        this.nameEn = "Issue " + issueNumber;
        this.nameAr = issueNumber + "العدد ";
    }

    public boolean isWithinCutOffPeriod(Clock clock, int issueCutOffInDays) {
        return PublicationTime.isIssuingDateWithinCutOffPeriod(clock, issuingDate, issueCutOffInDays);
    }
}
