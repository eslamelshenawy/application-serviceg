package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import gov.saip.applicationservice.common.dto.DocumentLightDto;
import gov.saip.applicationservice.common.dto.LkCertificateStatusDto;
import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CertificateRequestProjectionDto {
    
    Long applicationId;
    
    String applicationNumber;
    
    String applicationTitleAr;
    
    String applicationTitleEn;
    
    LkCertificateTypeDto certificateType;
    
    LkCertificateStatusDto certificateStatus;
    
    LocalDateTime depositDate;
    
    ApplicationRelevantTypeLightDto applicationRelevantType;
    
    Long certificateRequestId;

    DocumentLightDto document;
    
}
