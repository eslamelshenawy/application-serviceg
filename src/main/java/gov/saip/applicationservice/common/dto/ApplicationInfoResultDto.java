package gov.saip.applicationservice.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationInfoResultDto {
    
    private Long applicationId;
    
    private String saipCode;
    
    private String applicationNumber;
    
    private String certificateNumber;
    
    public ApplicationInfoResultDto(Long applicationId, String saipCode, String applicationNumber, String certificateNumber) {
        this.applicationId = applicationId;
        this.saipCode = saipCode;
        this.applicationNumber = applicationNumber;
        this.certificateNumber = certificateNumber;
    }
}


