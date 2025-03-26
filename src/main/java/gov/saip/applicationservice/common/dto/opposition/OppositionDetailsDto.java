package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class OppositionDetailsDto  {
    private String oppositionUserFullNameAr;
    private String oppositionUserFullNameEn;
    private String oppositionUserCustomerCode;
    private List<DocumentDto> documents;
    private String requestNumber;
    private LocalDateTime createdDate;
    private String mainApplicationNumber;
    private String mainApplicationTitleAr;
    private String mainApplicationTitleEn;
    private String mainApplicationStatusAr;
    private String statusCode;

    private String mainApplicationStatusEn;
    private String mainApplicantMobile;
    private   String finalNotes;

    private String  applicantNameAr;
    private String  applicantNameEn;
    private AddressResponseDto applicantAddress;
    private HearingSessionDto hearingSession;
    private HearingSessionDto applicantHearingSession;
    private String oppositionReason;
    private String applicantExaminerNotes;
    private String applicantAgentNameAr;
    private String applicantAgentNameEn;
    private String headExaminerNotesToExaminer;
    private List<ClassificationDto> selectedApplicationClassifications;
    private List<ClassificationDto>oppositionApplicationClassifications;
    private String complainerLegalRepresentativeName;
    private String oppositionFinalDecision;
    private LKSupportServiceRequestStatus requestStatus;
}
