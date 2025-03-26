package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.supportService.license.LkLicencePurposeDto;
import gov.saip.applicationservice.common.dto.supportService.license.LkLicenceTypeDto;
import gov.saip.applicationservice.common.enums.ApplicantType;
import gov.saip.applicationservice.common.enums.LicencePurposeEnum;
import gov.saip.applicationservice.common.enums.LicenceTypeEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class LicenceRequestDto extends BaseSupportServiceDto {
    private LicenceTypeEnum licenceTypeEnum;
    private Long customerId;
    private LicencePurposeEnum licencePurpose;
    private String customerCode;
    private DocumentLightDto supportDocument;
    private DocumentLightDto poaDocument;
    private DocumentLightDto updatedContractDocument;
    private DocumentLightDto compulsoryLicenseDocument;
    private RequestTasksDto task;
    private String notes;
    private LocalDate fromDate;
    private LocalDate toDate;
    private ApplicantType applicantType = ApplicantType.OWNER;
    private String agencyRequestNumber;
    private List<LkRegionsDto> regions;
    private List<Long> documentIds;
    private List<DocumentDto> documents;
    private CustomerSampleInfoDto customer;
    private String applicantNameAr;
    private String applicantNameEn;
    private CustomerSampleInfoDto applicant;
    private DocumentLightDto canceledContractDocument;
    private Integer licenceValidityNumber;
    private LkLicenceTypeDto lkLicenceType;
    private LkLicencePurposeDto lkLicencePurpose;
    private LicenceRequestDto mainLicenceRequest;
    @NotNull
    private RequestTypeEnum requestType;
}
