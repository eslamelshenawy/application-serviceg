package gov.saip.applicationservice.common.dto.appeal;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppealCommitteeOpinionDto extends BaseDto<Long>  {
    private String appealCommitteeOpinion;
    private Long documentId;
    private DocumentDto documentDto;
    private Long appealRequestId;
}
