package gov.saip.applicationservice.common.dto.trademark;

import gov.saip.applicationservice.common.enums.ApplicationCustomerType;

import java.time.LocalDateTime;

public interface ApplicationTradeMarkProjection {

    Long getAppId();
    String getAppTitleAr();
    String getAppTitleEn();
    String getApplicationNumber();
    String getApplicationRequestNumber();
    LocalDateTime getFilingDate();
    String getOwnerNameAr();
    String getOwnerNameEn();
    String getOwnerAddressAr();
    String getOwnerAddressEn();
    LocalDateTime getEndOfProtectionDate();
    ApplicationCustomerType getCreatedByCustomerType();
    Long getCreatedByCustomerId();
    LocalDateTime getGrantDate();
    Long getStatusId();
    String getStatusAr();
    String getStatusEn();
    String getStatusInternalAr();
    String getStatusInternalEn();
    String getStatusCode();
    Long getCategoryId();
    String getCategoryDescAr();
    String getCategoryDescEn();
    String getCategoryCode();
    Boolean getMarkClaimingColor();
    String getTypeNameAr();
    String getTypeNameEn();
    String getTagLanguageNameAr();
    String getTagLanguageNameEn();
    String getTagTypeNameAr();
    String getTagTypeNameEn();
    String getMarkDescription();
    String getExaminerGrantCondition();
    String getTmNameEn();
    String getTmNameAr();
    Long getIssueNumber();
    String getPublicationNumber();
    String getPublicationType();
    LocalDateTime getRegistrationDate();
    LocalDateTime getPublicationDate();
    String getGrantNumber();



}
