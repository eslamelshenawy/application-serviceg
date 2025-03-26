package gov.saip.applicationservice.common.service.opposition;

import gov.saip.applicationservice.common.dto.DocumentWithCommentDto;
import gov.saip.applicationservice.common.dto.opposition.*;
import gov.saip.applicationservice.common.model.opposition.Opposition;
import gov.saip.applicationservice.common.service.SupportServiceRequestService;

import java.util.List;

public interface OppositionService extends SupportServiceRequestService<Opposition> {

    Long applicantReply(Opposition applicantReplyDto, String taskId);

    void processApplicantHearingSessionPayment(Long id);

    Long updateComplainerHearingSession(OppositionDto dto);

    Long updateApplicantHearingSession(OppositionDto dto);

    Long updateApplicantExaminerNotes(OppositionDto dto);

    Long examinerFinalDecision(OppositionDto dto);

    Long headExaminerNotesToExaminer(Opposition dto);

    void confirmFinalDecisionFromHeadOfExaminer(Long id);


    OppositionDetailsDto getTradeMarkOppositionDetails(Long oppositionId, boolean gate);
    OppositionDetailsDto getPatentOppositionDetails(Long oppositionId,boolean gate);
    OppositionDetailsDto getIndustrialOppositionDetails(Long oppositionId,boolean gate);

    List<DocumentWithCommentDto> getAllApplicationOppositionDocumentsWithComments(Long oppositionId);

    ApplicantOppositionViewDto getApplicationApplicantOppositionDetails(Long oppositionId);

    OppositionTradeMarkReportDetailsDto getOppositionTradeMarkApplicationReport(Long oppositionId);
    OppositionPatentReportDetailsDto getOppositionPatentApplicationReport(Long oppositionId);
    OppositionIndustrialReportDetailsDto getOppositionIndustrialApplicationReport(Long oppositionId);

}
