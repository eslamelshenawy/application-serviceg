package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvictionRequestDto extends BaseDto<Long> {

    private Long applicationId;
    private String comment;
    private DocumentLightDto descDocument;
    private DocumentLightDto evictionDocument;
    private String requestNumber;
    private LKSupportServiceRequestStatus requestStatus;

}
