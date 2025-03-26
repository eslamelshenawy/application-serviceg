package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseSupportServiceDto extends BaseDto<Long> {
    private Long applicationId;
    private ApplicationInfoDto applicationInfo;
    private LKSupportServicesDto lkSupportServices;
    private LKSupportServiceRequestStatus requestStatus;
    private String requestNumber;
    private String processRequestId;
    private LocalDateTime createdDate;
    private String createdByUser;
    private String createdByCustomerCode;
}
