package gov.saip.applicationservice.common.dto.industrial;

import gov.saip.applicationservice.common.dto.ApplicationPriorityLightResponseDto;
import gov.saip.applicationservice.common.dto.DurationAndPercentageDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
public class PublicationDetailsDto {
    private String applicationNumber;
    private LocalDate applicationFilingDate;
    private String priorityApplicationNumber;
    private LocalDate priorityFilingDate;
    private String priorityCountry;
    private String applicationStatus;
    private LocalDate publicationDate;
    private String designerNameAr;
    private String designerNameEn;
    private Long certNumber;
    private String certOwnerNameAr;
    private AddressResponseDto certOwnerAddress;
    private String technicalInfo;
    private String agentNameAr;
    private String agentNameEn;
    private  Long designSamplesCount;
    private RequestTasksDto task;
    private String applicationTitleAr;
    private String applicationTitleEn;
    private DurationAndPercentageDto publicationRemainingDuration;
    private LocalDate grantDate;
    private String processRequestTypeCode;
    List<ApplicationPriorityLightResponseDto> applicationPriorities;
    
}
