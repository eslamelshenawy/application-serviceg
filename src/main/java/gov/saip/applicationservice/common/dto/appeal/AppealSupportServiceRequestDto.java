package gov.saip.applicationservice.common.dto.appeal;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentWithTypeDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppealSupportServiceRequestDto extends BaseDto<Long>  {
    private String appealReason;
    private Long applicationId;
    private List<DocumentWithTypeDto> documents;
    private SupportServicePaymentStatus paymentStatus;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;
    private String rejectionReason; // in case reject from committee or checker
}
