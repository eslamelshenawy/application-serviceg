package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DepositReportDto {
    private Long id;
    private String titleAr;
    private Long certificateId;
    private String applicationRequestNumber;
    private String certificateRequestNumber;

    public DepositReportDto(Long id, String titleAr, String titleEn, String applicationNumber, LocalDateTime filingDate, String applicationRequestNumber) {
        this.id = id;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.applicationNumber = applicationNumber;
        this.filingDate = filingDate;
        this.applicationRequestNumber = applicationRequestNumber;
    }

    private String titleEn;
    private String applicationNumber;
    private LocalDateTime filingDate;
    private String fillingDateHigri;
    private String tmNameAr;
    private String tmNameEn;
    private String tmOwner;
    private String markDescription;
    private String applicantName;
    private String postalCode;
    private String buildingNumber;
    private String cityName;
    private String streetName;
    private String certificateDateExpiry;
    private String depositCertificateNiceClassificationVersionName;
    private String depositCertificateNiceClassificationName;
    private Long tradeMarkId;
    private String fileUrl;
    private String qrCodeImageBase64;

}
