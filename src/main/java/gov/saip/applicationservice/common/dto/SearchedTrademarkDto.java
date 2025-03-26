package gov.saip.applicationservice.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchedTrademarkDto {

    private Long id;
    private Long applicationInfoId;
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
    private List<String> niceClassification;  // Keep it as List<String>
}
