package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitialModificationRequestDto extends BaseDto<Long> {

    private Long applicationId;
    private LKSupportServiceTypeDto lkSupportServiceType;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;

}
