package gov.saip.applicationservice.common.dto.publication;

import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.LkApplicationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicationBatchViewProjection {
    
    Long getApplicationId();
    
    String getApplicationNumber();
    
    LocalDateTime getDepositDate();
    
    String getIpStatusDescAr();
    
    String getIpStatusDescEn();
    
    String getApplicationTitleAr();
    
    String getApplicationTitleEn();
    
    String getTmTypeAr();
    
    String getTmTypeEn();
    
    Long getRelevantTypeId();
    
    String getCustomerCode();
    
    LocalDateTime getGazettePublicationDate();
    
    String getTmTagCode();
    
    String getPublicationTypeAr();
    
    String getPublicationTypeEn();
    
    String getPublicationCode();
    
    List<ApplicationRelevantType> getApplicationRelevantTypes();
    
    ApplicationRelevantType getApplicationRelevantType();
    
    LocalDateTime getPublicationDate();
    
    ApplicationInfo getApplicationInfo();
    
    String getStatusCode();
    
    LkApplicationStatus getApplicationStatus();
    
    Long getApplicationPublicationId();
    
    LocalDateTime getPublicationDateTime();
    
    Long getCustomerId();
    
    String getOwnerNameAr();
    
    String getOwnerNameEn();
    
    String getApplicationRequestNumber();
    
    
}
