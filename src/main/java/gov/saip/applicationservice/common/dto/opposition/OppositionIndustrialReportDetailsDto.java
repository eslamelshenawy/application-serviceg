package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.common.dto.ClassificationDto;
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
public class OppositionIndustrialReportDetailsDto  {
    private String industrialTitleAr;
    private String industrialTitleEn;
    private String industrialNumber;
    private LocalDateTime createdDate;
    private String requestNumber;
    private String applicantNameAr;
    private String applicantNameEn;
    private AddressResponseDto applicantAddress;
    private String applicantAgentNameAr;
    private String applicantAgentNameEn;
    private AddressResponseDto agentAddress;
    private String complainerNameAr;
    private String complainerNameEn;
    private String complainerAgentNameEn;
    private AddressResponseDto complainerAddress;
    private List<ClassificationDto> industrialCode;
    private   String finalNotes;
    private String oppositionFinalDecision;
    private LKSupportServiceRequestStatus requestStatus;
}
