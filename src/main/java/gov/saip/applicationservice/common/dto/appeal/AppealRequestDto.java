package gov.saip.applicationservice.common.dto.appeal;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.appeal.AppealCheckerDecision;
import gov.saip.applicationservice.common.enums.appeal.AppealCommitteeDecision;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppealRequestDto extends BaseDto<Long>  {
    private Long appealApplicantCustomerId;
    private String appealReason;
    private AppealCheckerDecision checkerDecision;
    private String checkerFinalNotes;
    private AppealCommitteeDecision appealCommitteeDecision;
    private String appealCommitteeDecisionComment;
    private String officialLetterComment;
    private List<Long> documentIds;
    private Long applicationId;
    private AppealCommitteeOpinionDto opinion;
    private String taskId;
    private String taskDefinitionKey;
}
