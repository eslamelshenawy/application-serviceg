package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.model.*;

import java.time.LocalDateTime;

public interface CertificateRequestProjection {
    
    Long getApplicationId();
    
    String getApplicationNumber();
    
    String getApplicationTitleAr();
    
    String getApplicationTitleEn();
    
    LkCertificateType getCertificateType();
    
    LkCertificateStatus getCertificateStatus();
    
    String getCustomerCode();
    
    ApplicationRelevantType getApplicationRelevantType();
    
    LocalDateTime getDepositDate();
    
    Long getCertificateRequestId();

    Document getDocument();

}

