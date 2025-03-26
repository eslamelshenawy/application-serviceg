package gov.saip.applicationservice.common.dto.trademark;

import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.ApplicationStatusDto;
import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.ApplicationPublicationSummaryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApplicationTrademarkDetailSummaryDto {

    private ApplicationPublicationSummaryProjection lastPublicationSummary;
    private LocalDateTime grantDate;
    private String applicationRequestNumber;
    private Long createdByCustomerId;
    private String ownerNameAr;
    private String ownerNameEn;
    private String ownerAddressAr;
    private String ownerAddressEn;
    private LocalDateTime filingDate;
    private TradeMarkLightDto tradeMarkDetails;
    private long id;
    private String titleEn;
    private String titleAr;

    private String applicationNumber;
    private ApplicationStatusDto applicationStatus;
    private LKApplicationCategoryDto category;
    private ApplicationCustomerType createdByCustomerType;
    private String grantNumber;
    private String createdByCustomerTypeNameEn;
    private String createdByCustomerTypeNameAr;
    private List<ApplicantsDto> applicants;
    private ApplicationAgentSummaryDto agentSummary;
    private boolean validToAppeal;
}
