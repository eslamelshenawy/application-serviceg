package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationInstallmentConfigDto extends BaseDto<Long> {

    private String applicationCategory;
    private String installmentType;
    private Integer paymentIntervalYears;
    private Integer paymentDuration;
    private Integer graceDuration;
    private Integer openRenewalDuration;
    private Integer renewalIssueWaitingDuration;
}
