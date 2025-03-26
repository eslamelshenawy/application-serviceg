package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import lombok.Data;

@Data
public class CertificateRequestViewDto extends BaseCertificateRequestDto{
    
    String customerCode;
    
}
