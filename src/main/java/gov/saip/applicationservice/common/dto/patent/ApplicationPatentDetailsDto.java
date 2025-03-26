package gov.saip.applicationservice.common.dto.patent;

import gov.saip.applicationservice.common.dto.ApplicationSubstantiveExaminationRetrieveDto;
import lombok.Data;

@Data
public class ApplicationPatentDetailsDto {


    private ApplicationSubstantiveExaminationRetrieveDto application;

    private Long applicationId;
    private String ipdSummaryAr;
    private String ipdSummaryEn;
    private Long specificationsDocId;
    private Boolean collaborativeResearch;
    private Boolean patentOpposition=Boolean.FALSE;
}
