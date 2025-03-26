package gov.saip.applicationservice.common.dto.patent;


import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
public class PublicationDetailDto {


    private String applicationNumber;
    private String applicationStatus;
    private LocalDate applicationFilingDate;
    private String priorityApplicationNumber;
    private LocalDate priorityFilingDate;
    private String priorityCountry;
    private String applicantNameAr;
    private String applicantNameEn;
    private AddressResponseDto applicantAdress;
    private String agentNameAr;
    private String agentNameEn;
    private String technicalInfo;
    private String inventorNameAr;
    private LocalDate publicationDate;
    private Long publicationNumber;
    private String patentSummeryAr;
    private String patentSummeryEn;
    private String grantNumber;
    private LocalDate grantDate;
    private String pct;
    private DurationDto publicationRemainingTime;// todo remove
    private RequestTasksDto task;
    private String applicationTitleAr;
    private String applicationTitleEn;
    private DurationAndPercentageDto publicationRemainingDuration;
    private List<DocumentDto> references;
    private Map<String, List<PatentAttributeChangeLogDto>> patentSummery;
    List<ApplicationPriorityLightResponseDto> applicationPriorities;
    private String processRequestTypeCode;
    private List<DocumentDto> documents;
    private Date filingInvoicePaymentDate;
    private List<String> examinerNames;
    private List<ApplicationRelevantTypeDto> inventors;
}
