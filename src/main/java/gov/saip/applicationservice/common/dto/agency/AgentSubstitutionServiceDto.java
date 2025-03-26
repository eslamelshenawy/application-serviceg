package gov.saip.applicationservice.common.dto.agency;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.ApplicationListDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AgentSubstitutionServiceDto extends BaseDto<Long> {

    private LKSupportServiceType lkSupportServiceType;
    private DocumentLightDto delegationDocument;
    private DocumentLightDto evictionDocument;
    private Long customerId;
    private List<Long> applicationIds;
    private CustomerSampleInfoDto agent;
    private ApplicantsDto applicant;
    private List<ApplicationListDto> applications;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;

}
