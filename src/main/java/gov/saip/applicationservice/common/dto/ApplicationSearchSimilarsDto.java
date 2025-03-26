package gov.saip.applicationservice.common.dto;


import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationSearchSimilarsDto extends BaseDto<Long> {

    private String brandNameAr;
    private String brandNameEn;
    private String filingNumber;
    private String status;
    private LocalDateTime filingDate;

    public ApplicationSearchSimilarsDto(String brandNameAr, String brandNameEn, String filingNumber, String status, LocalDateTime filingDate) {
        this.brandNameAr = brandNameAr;
        this.brandNameEn = brandNameEn;
        this.filingNumber = filingNumber;
        this.status = status;
        this.filingDate = filingDate;
    }
}
