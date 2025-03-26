package gov.saip.applicationservice.common.dto.opposition;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.enums.opposition.OppositionFinalDecision;
import gov.saip.applicationservice.common.enums.opposition.OppositionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OppositionDto extends BaseDto<Long> {
    private ApplicationInfoDto application;
    private Long complainerCustomerId;
    private OppositionType type;
    private String oppositionReason;
    private List<DocumentDto> documents;
    private List<ClassificationDto> classifications;
    private OppositionFinalDecision finalDecision;
    private String finalNotes;
    private String applicantExaminerNotes;
    private String headExaminerNotesToExaminer;
    private OppositionLegalRepresentativeDto oppositionLegalRepresentative;
    private HearingSessionDto hearingSession;
    private HearingSessionDto applicantHearingSession;
    private Boolean isHeadExaminerConfirmed;


    // we receive ids directly to simplify frontend work
    private List<Long> documentIds;
    private List<Long> classificationIds;
    private Long applicationId;
    private boolean accepted;
}
