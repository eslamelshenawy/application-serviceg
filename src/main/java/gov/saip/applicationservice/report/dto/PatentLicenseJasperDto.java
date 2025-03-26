package gov.saip.applicationservice.report.dto;

import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PatentLicenseJasperDto {

    private Long applicationId;
    private String applicationNumber;
    private String titleAr;
    private String titleEn;
    private String ownerNameAr;
    private String ownerNameEn;
    private String ownerAddress;
    private String ownerNationality;
    private String inventorName;
    private String agentName;
    private String createdDateGregorian;
    private String createdDateHijri;
    private String filingDateGregorian;
    private String filingDateHijri;
    private String grantDateGregorian;
    private String grantDateHijri;
    private String pctApplicationNo;
    private String filingDateGr;
    private String publishNo;
    private String internationalPublicationDate;
    private String internationalClassification;
    private String summaryNotes;
    private String fullSummaryNotes;


    public PatentLicenseJasperDto(Long applicationId, String applicationNumber, String titleEn, String titleAr,
                                  LocalDateTime createdDateGregorian, String summaryNotes, String fullSummaryNotes,
                                  String pctApplicationNo, LocalDate filingDateGr, String publishNo,
                                  LocalDate internationalPublicationDate, String internationalClassification,
                                  LocalDateTime filingDateGregorian) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.applicationId = applicationId;
        this.applicationNumber = applicationNumber;
        this.titleEn = titleEn;
        this.titleAr = titleAr;
        this.createdDateGregorian = createdDateGregorian == null ? null : createdDateGregorian.format(dateTimeFormatter);
        this.createdDateHijri = createdDateGregorian == null ? null : Utilities.convertDateFromGregorianToHijri(createdDateGregorian.toLocalDate());
        this.summaryNotes = summaryNotes;
        this.fullSummaryNotes = fullSummaryNotes;
        this.pctApplicationNo = pctApplicationNo;
        this.filingDateGr = filingDateGr == null ? null : filingDateGr.format(dateTimeFormatter);
        this.publishNo = publishNo;
        this.internationalPublicationDate = internationalPublicationDate == null ? null : internationalPublicationDate.format(dateTimeFormatter);
        this.pctApplicationNo = pctApplicationNo;
        this.internationalClassification = internationalClassification;
        this.filingDateGregorian = filingDateGregorian == null ? null : filingDateGregorian.format(dateTimeFormatter);
        this.filingDateHijri = filingDateGregorian == null ? null : Utilities.convertDateFromGregorianToHijri(filingDateGregorian.toLocalDate());
        this.grantDateGregorian = this.filingDateGregorian;
        this.grantDateHijri = this.filingDateHijri;
        appendDateMarks();
    }

    private void appendDateMarks() {
        final String gregorianMark = " م";
        final String hijriMark = " هـ";
        this.createdDateGregorian += gregorianMark;
        this.createdDateHijri += hijriMark;
        this.filingDateGregorian += gregorianMark;
        this.filingDateHijri += hijriMark;
        this.grantDateGregorian += gregorianMark;
        this.grantDateHijri += hijriMark;
    }

    public void appendInternationalClassification(String internationalClassification) {
        if (this.internationalClassification == null)
            this.internationalClassification = "";
        this.internationalClassification += ", " + internationalClassification;
    }

}
