package gov.saip.applicationservice.common.dto.reports;


import java.time.LocalDateTime;
import java.util.Date;

public interface PatentReportProjection {
    LocalDateTime getGrantDate();

    String getGrantDateHijri();

    String getTitleAr();

    String getTitleEn();

    String getApplicationNumber();

    Long getId();

    LocalDateTime getFilingDate();

    String getPctApplicationNo();

    String getPublishNo();

    Date getInternationalPublicationDate();

    Date getfilingDateGr();

    String getIpcNumber();
    Long getPatentDetailsId();

}

