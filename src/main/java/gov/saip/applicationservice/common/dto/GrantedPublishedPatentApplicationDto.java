package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class GrantedPublishedPatentApplicationDto {
    private int rowNumber;
    private String titleAr;
    private String applicationNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public GrantedPublishedPatentApplicationDto(String titleAr, String applicationNumber, LocalDateTime startDate , LocalDateTime endDate) {
        this.titleAr = titleAr;
        this.applicationNumber = applicationNumber;
        this.startDate =startDate;
        this.endDate = endDate;
    }
}

