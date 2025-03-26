package gov.saip.applicationservice.common.dto.annualfees;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.annuel_fees.AnnualFeesTypes;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AnnualFeesRequestDto extends BaseDto<Long> {
    private Long applicationId;
    private String requestNumber;
    private AnnualFeesTypes serviceType;
    private LkAnnualRequestYearsDto annualRequestYears;
    private LkPostRequestReasonsDto postRequestReasons;
    private LKSupportServiceRequestStatus requestStatus;
    private List<String> costCodesList;
}
