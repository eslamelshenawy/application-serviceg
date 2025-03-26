package gov.saip.applicationservice.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplicationInfoRequestLightDto {
    
    private Long applicationId;
    
    private Long supportServiceId;
    
    private String applicationCustomerType;
    
}
