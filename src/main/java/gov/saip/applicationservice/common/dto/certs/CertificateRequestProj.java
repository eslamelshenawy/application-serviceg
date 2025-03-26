package gov.saip.applicationservice.common.dto.certs;

import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.model.LkCertificateStatus;
import gov.saip.applicationservice.common.model.LkCertificateType;

import java.time.LocalDateTime;

public interface CertificateRequestProj {
    
    Long getApplicationId();
    
    String getApplicationNumber();
    
    String getApplicationTitleAr();
    
    String getApplicationTitleEn();

    Integer  getCertificateType();

    Integer getCertificateStatus();
    
    String getCustomerCode();
    
    ApplicationRelevantType getApplicationRelevantType();
    
    LocalDateTime getDepositDate();
    
    Long getCertificateRequestId();

    Document getDocument();

}

