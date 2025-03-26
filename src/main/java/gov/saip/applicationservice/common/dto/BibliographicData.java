package gov.saip.applicationservice.common.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BibliographicData {
    @JsonProperty("BrandNameAr")
    private String brandNameAr;
    @JsonProperty("BrandNameEn")
    private String brandNameEn;
    @JsonProperty("FilingNumber")
    private String filingNumber;
    @JsonProperty("TrademarkLastStatus")
    private String trademarkLastStatus;
    @JsonProperty("FilingDate")
    private String filingDate;
}
