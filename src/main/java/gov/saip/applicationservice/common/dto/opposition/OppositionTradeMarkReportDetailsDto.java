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
public class OppositionTradeMarkReportDetailsDto  {
private String tradeMarkTitleAr;
    private String tradeMarkTitleEn;
    private String tradeMarkNumber;
    private String requestNumber;
    private String  applicantNameAr;
    private String  applicantNameEn;
    private LocalDateTime createdDate;

    private AddressResponseDto applicantAddress;
    private String applicantAgentNameAr;
    private String applicantAgentNameEn;
    private AddressResponseDto agentAddress;


    private String complainerNameAr;
    private String complainerNameEn;
    private String oppositionFinalDecision;
    private AddressResponseDto complainerAddress;
    private   String finalNotes;


    private List<ClassificationDto> tradeMarkCode;
    private LKSupportServiceRequestStatus requestStatus;






}
