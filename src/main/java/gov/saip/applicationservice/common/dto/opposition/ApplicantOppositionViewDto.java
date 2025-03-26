package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.base.dto.BaseDto;
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
public class ApplicantOppositionViewDto  {

    private List<DocumentDto> documents;

    private LocalDateTime createdDate;
    private String mainApplicationNumber;
    private String mainApplicationTitleAr;
    private String mainApplicationTitleEn;
    private String mainApplicationStatusAr;
    private String mainApplicationStatusEn;
    private String mainApplicantMobile;
    private String applicationStatusCode;

    private String  applicantNameAr;
    private String  applicantNameEn;
    private AddressResponseDto applicantAddress;
    private HearingSessionDto hearingSession;
    private String oppositionReason;
    private String applicantExaminerNotes;
    private String applicantAgentNameAr;
    private String applicantAgentNameEn;
    private String finalDecision;
    private   String finalNotes;
    private String requestNumber;
    private List<ClassificationDto> selectedApplicationClassifications;
    private List<ClassificationDto>oppositionApplicationClassifications;
    private LKSupportServiceRequestStatus requestStatus;




}
