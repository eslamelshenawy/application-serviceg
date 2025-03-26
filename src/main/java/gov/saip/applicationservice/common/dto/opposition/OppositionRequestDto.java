package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.BaseSupportServiceDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OppositionRequestDto extends BaseSupportServiceDto {
    private List<Long> applicationIds;
    private List<Long> documentIds;
    private List<DocumentDto> oppositionDocuments;
    private String oppositionReason;
    private String applicationOwnerReply;
    private OppositionRequestHearingSessionDto complainerHearingSession;
    private OppositionRequestHearingSessionDto applicationOwnerHearingSession;
    private String complainerNameAr;
    private String complainerNameEn;
    private String applicationOwnerNameAr;
    private String applicationOwnerNameEn;
    private List<ApplicationInfoDto> oppositionApplicationSimilars;
    private String createdByCustomerCode;
    private boolean isComplainerHearingSessionScheduled;
    private boolean isApplicantHearingSessionScheduled;
    private CustomerSampleInfoDto applicantDetails;

}
