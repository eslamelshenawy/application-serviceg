package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import gov.saip.applicationservice.common.model.LkCertificateStatus;
import gov.saip.applicationservice.common.model.LkCertificateType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BaseCertificateRequestDto {
    
    Long applicationId;
    
    String applicationNumber;
    
    String applicationTitleAr;
    
    String applicationTitleEn;
    
    LkCertificateType certificateType;
    
    LkCertificateStatus certificateStatus;
    
    LocalDate depositDate;
    
    ApplicationRelevantTypeLightDto applicationRelevantType;
    
    Long certificateRequestId;
    
    
}
