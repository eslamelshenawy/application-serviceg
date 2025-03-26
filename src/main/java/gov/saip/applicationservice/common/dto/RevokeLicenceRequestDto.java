package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicantType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RevokeLicenceRequestDto  extends BaseSupportServiceDto {
    private LicenceRequestDto licenceRequest;
    private String notes;
    private ApplicantType applicantType;
    private String agencyRequestNumber;
    private List<Long> documentIds;
    private List<DocumentDto> documents;
    private String applicantNameAr;
    private String applicantNameEn;
    private boolean hasOpposition;
    private OppositionRevokeLicenceRequestDto oppositionRevokeLicenceRequestDto;
    private boolean canCurrentCustomerOppose;
}
