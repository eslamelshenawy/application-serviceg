package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.patent.PctDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class    ApplicationInfoDto {
    private long id;
    private Long serial;
    private String email;
    private String applicationNumber;
    private String mobileCode;
    private String mobileNumber;
    private String titleEn;
    private String titleAr;
    private Boolean partialApplication;
    private String partialApplicationNumber;
    private Boolean nationalSecurity;
    private String ipcNumber;
    private Long createdByUserId;
    private Long categoryId;
    private Boolean substantiveExamination;
    private Boolean accelerated;
    private String address;
    private ApplicationStatusDto applicationStatus;
    private List<ApplicationPriorityResponseDto> applicationPriority;
    private ApplicationAcceleratedResponseDto applicationAccelerated;
    private ApplicationAcceleratedResponseDto applicationAcceleratedDetails;
    private LKApplicationCategoryDto category;
    private List<DocumentDto> documents;
    private List<ApplicationRelevantTypeDto> applicationRelevantTypes;
    private List<ApplicationRelevantTypeDto> inventors;
    private List<ClassificationDto> classifications;
    private boolean byHimself;
    private DurationDto validDurationToUpdate;
    private DurationDto validDurationToCompleteData;
    private LocalDateTime filingDate;
    private RequestTasksDto task;
    private List<RequestTasksDto> allTasks;
    private Integer normalPagesNumber;
    private Integer claimPagesNumber;
    private Integer shapesNumber;
    private Long totalCheckingFee;
    private String requestNotes;
    private ClassificationDto classification;
    private String requestId;
    private LocalDateTime grantDate;
    private DocumentDto pltDocument;
    private String pltDescription;
    private Boolean pltRegisteration;
    private LocalDateTime endOfProtectionDate;
//    private String endOfProtectionDateHijri;
    private String ownerNameAr;
    private String ownerNameEn;
    private String ownerAddressAr;
    private String ownerAddressEn;
    private Boolean isPriorityConfirmed;
    private Boolean canAddPriority = Boolean.TRUE;
    private LocalDateTime pltFilingDate;
    private String grantNumber;
    private PctDto pctDto;
    private DocumentDto legalDocuments;
    private String applicationRequestNumber;
    private Long createdByCustomerId;
    @Enumerated(EnumType.STRING)
    private ApplicationCustomerType createdByCustomerType;
    private String createdByCustomerTypeNameEn;
    private String createdByCustomerTypeNameAr;
    private List<PartialApplicationInfoDto> applicationPartialList;
    private ApplicationMainDto applicationMainDto;
}
