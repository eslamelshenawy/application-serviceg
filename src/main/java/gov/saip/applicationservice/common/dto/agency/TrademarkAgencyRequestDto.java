package gov.saip.applicationservice.common.dto.agency;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.LKSupportServicesDto;
import gov.saip.applicationservice.common.enums.agency.LegalAgentType;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyStatusCode;
import gov.saip.applicationservice.common.enums.agency.TrademarkAgencyType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TrademarkAgencyRequestDto extends BaseDto<Long> {
    // attributes to save or update and view
    private Long applicationId;
    private List<Long> documentIds;
    private List<Long> supportServiceIds;
    private Integer clientTypeId;
    private String requestNumber;
    private String agencyNumber;
    private TrademarkAgencyStatusCode status;
    private TrademarkAgencyType agencyType;
    private LegalAgentType legalAgentType;
    private String clientCustomerCode;
    private String agentCustomerCode;
    private LocalDate startAgency;
    private LocalDate endAgency;
    private String organizationAddress;
    private String organizationDescription;
    private String agencyCheckerNotes;
    private Long agentTypeId;
    private LocalDate agentExpiryDate;
    private String agentIdentityNumber;
    private String checkerUsername;
    private String processRequestId;

    // attributes to view details
    private LkClientTypeDto clientType;
    private LkAgentTypeDto agentType;
    private CustomerSampleInfoDto agentInfo;
    private CustomerSampleInfoDto clientInfo;
    private List<DocumentDto> documents;
    private List<LKSupportServicesDto> supportServices;
    private ApplicationInfoListDto application;
    private LKTrademarkAgencyRequestStatusDto requestStatus;

    private String taskId;
    private String taskDefinitionKey;

    private int returnedRequestNum;


}
