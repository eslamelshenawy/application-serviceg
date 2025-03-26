package gov.saip.applicationservice.common.dto.patent;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PatentApplicationInfoXmlDataDto {

    private Long applicationId;
    private String email;
    private String ipdSummaryAr;
    private String ipdSummaryEn;

    public PatentApplicationInfoXmlDataDto(Long applicationId, String email, String ipdSummaryAr, String ipdSummaryEn) {
        this.applicationId = applicationId;
        this.email = email;
        this.ipdSummaryAr = ipdSummaryAr;
        this.ipdSummaryEn = ipdSummaryEn;
    }

}
