package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppCommunicationUpdateRequestsDto extends BaseDto<Long> {
    private String email;
    private String address;
    private String mobileCode;
    private String mobileNumber;
}
