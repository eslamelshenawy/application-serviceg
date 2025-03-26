package gov.saip.applicationservice.common.dto.industrial;

import gov.saip.applicationservice.common.model.industrial.RequestTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class IndustrialDesignApplicationInfoXmlDataDto {

    private Long applicationId;
    private String email;
    private String explanationAr;
    private String explanationEn;
    private RequestTypeEnum requestType;

    public IndustrialDesignApplicationInfoXmlDataDto(Long applicationId, String email, String explanationAr, String explanationEn,
                                                     RequestTypeEnum requestType) {
        this.applicationId = applicationId;
        this.email = email;
        this.explanationAr = explanationAr;
        this.explanationEn = explanationEn;
        this.requestType = requestType;
    }

}
