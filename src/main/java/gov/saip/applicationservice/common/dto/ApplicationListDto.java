package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.trademark.TradeMarkLightDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ApplicationListDto {
    private String customerCode;
    private Long id;
    private String applicationNumber;
    private String titleAr;
    private String titleEn;
    private LocalDateTime filingDate;
    private Boolean partialApplication;
    private String ipsStatusDescEn;
    private String ipsStatusDescAr;
    private Long updatedDaysAgo;
    private Long updatedHoursAgo;
    private CustomerSampleInfoDto customerSampleInfoDto;
    private RequestTasksDto task;
    private boolean validToAppeal;
    public ApplicationListDto(String customerCode, Long id, String applicationNumber, String titleAr, String titleEn, LocalDateTime filingDate, String ipsStatusDescEn, String ipsStatusDescAr, String applicationStatus, String categoryCode, String grantNumber, String applicationRequestNumber
    ,LocalDateTime createdDate,LocalDateTime modifiedDate,Boolean partialApplication
    ) {
        this.customerCode = customerCode;
        this.id = id;
        this.applicationNumber = applicationNumber;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.filingDate = filingDate;
        this.ipsStatusDescEn = ipsStatusDescEn;
        this.ipsStatusDescAr = ipsStatusDescAr;
        this.applicationStatus = applicationStatus;
        this.categoryCode = categoryCode;
        this.grantNumber = grantNumber;
        this.applicationRequestNumber = applicationRequestNumber;
        this.createdDate=createdDate;
        this.modifiedDate=modifiedDate;
        this.partialApplication = partialApplication ;
    }

    private String applicationStatus;
    private String categoryCode;
    private List<String> supportServicesCodes;
    private Long licenceRequestId;
    private Long serviceId;
    private TradeMarkLightDto tradeMarkDetails;
    private String grantNumber;
    private String applicationRequestNumber;

    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;

}
