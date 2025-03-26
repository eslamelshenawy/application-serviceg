package gov.saip.applicationservice.common.dto.appeal;

import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppealDetailsDto implements Serializable {
    private String requestNumber;
    private LocalDateTime createdDate;
    private String reasons;
    private List<DocumentDto> documents;
    private String appStatusCode;
    private String applicationTitleAr;
    private String applicationTitleEn;
    private String applicantNameAr;
    private String applicantNameEn;
    private String applicantMobile;
    private AddressResponseDto applicantAddress;
    private String agentNameAr;
    private String agentNameEn;

    private String applicationNumber;
    private List<AppealCommitteeOpinionDto> appealCommitteeOpinionDtos;
    private String checkerDecision;
    private String checkerFinalNotes;
    private String appealCommitteeDecision;
    private String appealCommitteeDecisionComment;
    private String officialLetterComment;
    private String appStatusAr;
    private String appStatusEn;
    private LKSupportServiceRequestStatus requestStatus;
}