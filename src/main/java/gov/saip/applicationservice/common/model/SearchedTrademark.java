package gov.saip.applicationservice.common.model;
import gov.saip.applicationservice.base.model.BaseEntity;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "searched_trademark")
public class SearchedTrademark extends BaseEntity<Long> {

    @Column(nullable = false)
    private Long applicationInfoId;

    @Column(nullable = false)
    private String requestNumber;
    private String registrationNumber;
    private String nameAr;
    private String nameEn;
    private String markImage;
    private String countryCode;
    private String publicationDate;
    private String registrationDate;
    private String filingDate;
    private String markStatus;
    private String trademarkType;
    private String description;
    private String owner;
    private String representative;
    @Column(columnDefinition = "TEXT") // Store as comma-separated values
    private String niceClassification;

    public List<String> getNiceClassification() {
        return (niceClassification != null && !niceClassification.isEmpty())
                ? Arrays.asList(niceClassification.split(","))
                : null;
    }

    public void setNiceClassification(List<String> classifications) {
        this.niceClassification = (classifications != null && !classifications.isEmpty())
                ? classifications.stream().map(String::trim).collect(Collectors.joining(","))
                : null;
    }

}
