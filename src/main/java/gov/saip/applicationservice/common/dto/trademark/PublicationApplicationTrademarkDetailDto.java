package gov.saip.applicationservice.common.dto.trademark;


import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
public class PublicationApplicationTrademarkDetailDto {

    private ApplicationTrademarkDetailDto applicationTrademarkDetailDto;

    private String applicationStatus;
    
    private String dasCode;
    
    private String applicationNumber;

    private String applicationRequestNumber;
    
    private LocalDate applicationFilingDate;
    
    private String priorityApplicationNumber;
    
    private LocalDate priorityFilingDate;
    
    private String priorityCountry;
    
    private String applicantNameAr;
    
    private String applicantNameEn;
    
    private AddressResponseDto applicantAdress;
    
    private String agentNameAr;
    
    private String agentNameEn;
    
    private AddressResponseDto agentAdress;

    private List<ClassificationDto> niceClassifications;
    
    private List<SubClassificationDto> subClassifications;

    private List<SubClassificationDto> revokedSubClassifications;

    private LocalDate publicationDate;
    
    private String technicalInfo;
    
    private String termsAndCondtions;
    
    DurationAndPercentageDto publicationRemainingDuration;
    
    private RequestTasksDto task;
    
    private DocumentDto document;

    private String applicationTitleAr;

    private String applicationTitleEn;

    private LocalDateTime appFilingDate;
    private String appFilingDateHigri;
    private String grantHigiriDate;
    private LocalDateTime grantDate;
    private String addressAr;
    private String addressEn;
    private String publicationType;
     private String processRequestTypeCode;
    private List<ApplicationPriorityLightResponseDto> applicationPriorities;
    private List<LKVeenaClassificationDto> veenaClassifications;

    
    
}
