package gov.saip.applicationservice.common.service.protectionElementsMigration;


import java.sql.Date;
import java.time.LocalDateTime;

public class NewElement {
    String createdByUser;
    LocalDateTime createdDate;
    Long detailId;
    String arabicValue;
    String englishValue;

    public NewElement(Long detailId, String createdByUser, Date createdDate) {
        this.detailId = detailId;
        this.createdByUser = createdByUser;
        this.createdDate = createdDate.toLocalDate().atStartOfDay();
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getArabicValue() {
        return arabicValue;
    }

    public void setArabicValue(String arabicValue) {
        this.arabicValue = arabicValue;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    public void setEnglishValue(String englishValue) {
        this.englishValue = englishValue;
    }
}
