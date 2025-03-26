package gov.saip.applicationservice.common.dto.customer;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgentListDto extends BaseDto<Long> {
    private String identifier;
    private String code;
    private String email;
    private String mobileCountryCode;
    private String mobile;
    private AddressResponseDto address;
    private String nameAr;
    private String nameEn;
    private Long count;
}