package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PatentPdfRequest {

    public void setGrantDate(LocalDateTime grantDate) {
        if(grantDate != null)
            this.grantDate = grantDate.toLocalDate();
    }

    private LocalDate grantDate;

    public void setGrantDateHijri(String grantDateHijri) {
        this.grantDateHijri = grantDateHijri + " ه ";
    }

    private String grantDateHijri;
    private String titleAr;
    private String titleEn;
    private String applicationNumber;
    private String certNumber;
    private Boolean flag = true;

    public void setFilingDate(LocalDateTime filingDate,LocalDateTime internationalPublicationDate) {
        this.filingDate = filingDate.toLocalDate();
        this.internationalPublicationDate = internationalPublicationDate.toLocalDate();
    }
    private String priorityNumber;
    private LocalDate priorityDate;
    private String  priorityCountry;

    private LocalDate filingDate;
    private String pctApplicationNo;
    private String publishNo;
    private LocalDate internationalPublicationDate;
    private Date filingDateGr;
    private String filingDateHiGry;
    private String ipcNumber;
    private String tmOwner;
    private List<String> inventor;
    private String agentName;
    private String address;
    private String nationality;
    private String descriptionSpecificationArabic;
    private String claimsArabic;
    private String summary;
    private String graphics;
    private List<String> imagesExtracted;
    private List<String> checkerNames;
    private Long patentDetailsId;
}

