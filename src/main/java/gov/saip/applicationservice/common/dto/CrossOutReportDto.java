package gov.saip.applicationservice.common.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CrossOutReportDto {
    private Long id;
    private String titleAr;

    public CrossOutReportDto(Long id, String titleAr, String titleEn, String applicationNumber, LocalDateTime filingDate) {
        this.id = id;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.applicationNumber = applicationNumber;
        this.filingDate = filingDate;
    }

    private String titleEn;
    private String applicationNumber;
    private LocalDateTime filingDate;
    private String fillingDateHigri;
    private String tmNameAr;
    private String tmNameEn;
    private String tmOwner;
    private String applicantName;
    private String certificateDateExpiry;
    private String crossOutReportClassificationNumber;
    private Long tradeMarkId;
    private String fileUrl;

    public CrossOutReportDto() {

    }
}

