package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.customer.ApplicationAgentSummaryDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.trademark.TradeMarkLightDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.ApplicationPublicationSummaryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ApplicationInfoSummaryDto {
    private long id;
    private String titleEn;
    private String titleAr;
    private Boolean partialApplication;
    private String applicationNumber;
    private String partialApplicationNumber;
    private ApplicationStatusDto applicationStatus;
    private LKApplicationCategoryDto category;
    private DocumentDto poaDocument;
    private List<ApplicantsDto> applicants;
    private LocalDateTime filingDate;
    private RequestTasksDto task;
    private List<RequestTasksDto> allTasks;
    private Long numberOfApplicants;
    private Integer normalPagesNumber;
    private Integer claimPagesNumber;
    private Integer shapesNumber;
    private Long totalCheckingFee;
    private List<PartialApplicationInfoDto> applicationPartialList;
    private ApplicationAgentSummaryDto agentSummary;
    private TradeMarkLightDto tradeMarkDetails;
    private Long mainApplicationId;
    private String requestNotes;
    private SupportedServiceCode modificationType;
    private  List<ListApplicationClassificationDto> classifications;
    private String requestId;
    private LocalDateTime endOfProtectionDate;
    private String endOfProtectionDateHijri;
    private String ownerNameAr;
    private String ownerNameEn;
    private String ownerAddressAr;
    private String ownerAddressEn;
    private Boolean isFastTrackAllowed = false;
    private Boolean isPLT ;
    private String grantNumber;
    private DocumentDto legalDocuments;
    private List<ApplicationCertificateDocumentDto> applicationCertificateDocumentDtos;
    private Boolean isExtendDurationAllowed;
    private Boolean canAddPriority = Boolean.TRUE;
    private Boolean hasPriority = Boolean.FALSE;
    private Boolean hasLicencedSupportedService = Boolean.FALSE;
    private Boolean hasSupportServicesPeriorityEditOnlyExists = Boolean.FALSE;
    private List<DocumentDto> applicationDocuments;

    private ApplicationPublicationSummaryProjection lastPublicationSummary;
    private String lastProtectionDocumentNumber;
    private LocalDateTime grantDate;
    private String applicationRequestNumber;
    private Long createdByCustomerId;
    @Enumerated(EnumType.STRING)
    private ApplicationCustomerType createdByCustomerType;
    private String createdByCustomerTypeNameEn;
    private String createdByCustomerTypeNameAr;
    private Boolean patentOpposition=Boolean.FALSE;
}
