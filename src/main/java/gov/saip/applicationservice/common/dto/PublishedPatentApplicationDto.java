package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PublishedPatentApplicationDto {
    private int rowNumber;
    private String titleAr;
    private String applicationNumber;
    private String code;

    public PublishedPatentApplicationDto(String titleAr, String applicationNumber, String code) {
        this.titleAr = titleAr;
        this.applicationNumber = applicationNumber;
        this.code = code;
    }
}

