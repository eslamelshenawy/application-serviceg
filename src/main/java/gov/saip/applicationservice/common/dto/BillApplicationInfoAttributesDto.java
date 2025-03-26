package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillApplicationInfoAttributesDto {
    private String titleEn;
    private String titleAr;
    private String applicationNumber;
    private LocalDateTime filingDate;

    public BillApplicationInfoAttributesDto(String titleEn, String titleAr, String applicationNumber, LocalDateTime filingDate) {
        this.titleEn = titleEn;
        this.titleAr = titleAr;
        this.applicationNumber = applicationNumber;
        this.filingDate = filingDate;
    }
}
