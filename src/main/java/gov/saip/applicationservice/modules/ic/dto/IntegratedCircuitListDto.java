package gov.saip.applicationservice.modules.ic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class IntegratedCircuitListDto{

    private Long id;
    private String applicationNumber;
    private String applicationRequestNumber;
    private Long applicationId;
    private String titleEn;
    private String titleAr;
    private LocalDateTime filingDate;
    private String ipsStatusDescEn;
    private String ipsStatusDescAr;
    private String applicationStatus;
    private String designDescription;
    private LocalDateTime designDate;
    private Boolean commercialExploited;
    private LocalDateTime commercialExploitationDate;
    private Long countryId;
    private LocalDateTime createdDate;
    private String approvedNameAr;
    private String approvedNameEn;
}
