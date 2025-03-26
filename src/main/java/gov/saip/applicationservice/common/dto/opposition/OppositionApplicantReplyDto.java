package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OppositionApplicantReplyDto extends BaseDto<Long> {
    private List<DocumentDto> documents;
    private HearingSessionDto applicantHearingSession;
}
