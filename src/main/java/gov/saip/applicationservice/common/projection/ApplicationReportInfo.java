package gov.saip.applicationservice.common.projection;

import java.time.LocalDateTime;

public interface ApplicationReportInfo {
    String getApplicationNumber();
    String getIpsStatusDescAr();
    LocalDateTime getFilingDate();
    String getTitleAr();
    String getTitleEn();
    String getFilingDateHijri();
    String getGrantDateHijri();
    LocalDateTime getGrantDate();
    String getRequestNumber();
    LocalDateTime getCreatedDate();
    String getNameAr();
    String getOwnerNameAr();
    String getOwnerNameEn();
    String getOwnerAddressAr();
    String getOwnerAddressEn();
    LocalDateTime getEndOfProtectionDate();
    String getVersionName();
    LocalDateTime getRegistrationDate();
    String getRegistrationDateHijri();
}
