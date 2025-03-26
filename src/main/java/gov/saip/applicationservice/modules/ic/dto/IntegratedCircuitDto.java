package gov.saip.applicationservice.modules.ic.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IntegratedCircuitDto extends BaseDto<Long> {

    private ApplicationInfo application;
    private String designDescription;
    private LocalDateTime designDate;
    private Boolean commercialExploited;
    private LocalDateTime commercialExploitationDate;
    private Long countryId;
    private Boolean notifyChecker;

    private String designNameAr;
    private String designNameEn;

    private String approvedNameAr;
    private String approvedNameEn;

    public IntegratedCircuitDto(String designDescription, LocalDateTime designDate, Boolean commercialExploited,
                                LocalDateTime commercialExploitationDate, Long countryId, String designNameAr, String designNameEn,
                                String approvedNameAr, String approvedNameEn) {
        this.designDescription = designDescription;
        this.designDate = designDate;
        this.commercialExploited = commercialExploited;
        this.commercialExploitationDate = commercialExploitationDate;
        this.countryId = countryId;
        this.designNameAr = designNameAr;
        this.designNameEn = designNameEn;
        this.approvedNameAr = approvedNameAr;
        this.approvedNameEn = approvedNameEn;
    }
}
