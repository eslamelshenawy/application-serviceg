package gov.saip.applicationservice.common.dto.Suspcion;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ApplicantsSuspcionRequestDto {

    private String applicantAr;
    private String applicantEn;
    private String tradeMarkAr;
    private String tradeMarkEn;


}
