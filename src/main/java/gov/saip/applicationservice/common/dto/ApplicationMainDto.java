package gov.saip.applicationservice.common.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApplicationMainDto implements Serializable {
    private Long id;
    private String titleAr;
    private String titleEn;
    private LocalDateTime filingDate;
    private String applicationNumber;
    private String requestId;

    public ApplicationMainDto(Long id, String titleAr, String titleEn, LocalDateTime filingDate, String applicationNumber) {
        this.id = id;
        this.titleAr = titleAr;
        this.titleEn = titleEn;
        this.filingDate = filingDate;
        this.applicationNumber = applicationNumber;
    }
}
