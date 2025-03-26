package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApplicationSupportServicesTypeDto extends BaseDto<Long> {
    private Long applicationId;
    private ApplicationInfoListDto applicationInfo;
    private LKSupportServicesDto lkSupportServices;
    private LKSupportServiceRequestStatus requestStatus;
    private String requestNumber;
    private String processRequestId;
    private LocalDateTime createdDate;
    private String createdByUser;
    private String createdByCustomerCode;
    private SupportServicePaymentStatus paymentStatus;
}
