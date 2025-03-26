package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class OppositionPatentReportDetailsDto  {
    private String patentTitleAr;
    private String patentTitleEn;
    private String patentNumber;
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
    private   String finalNotes;
    private String oppositionFinalDecision;


    private String patentCode;
}
