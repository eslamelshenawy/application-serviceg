package gov.saip.applicationservice.common.dto.industrial;

import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationRetrieveDto;
import lombok.Data;

@Data
public class ApplicationIndustrialDesignDetailDto {
    private ApplicationSubstantiveExaminationRetrieveDto application;
    private String explanationAr;
    private String explanationEn;
    private String usageAr;
    private String usageEn;
    private boolean secret;
    private boolean haveExhibition;
    private String  exhibitionInfo;
}
