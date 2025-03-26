package gov.saip.applicationservice.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.lookup.LkNotesDto;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationNotes;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApplicationNotesService extends BaseService<ApplicationNotes, Long> {
    List<SectionApplicationNotesResponseDto> getAllApplicationNotes(Long applicationId, Integer stepId
            , Integer attributeId, String taskDefinitionKey, NotesTypeEnum notesType) throws JsonProcessingException;

    List<ApplicationNotesDto> findAppNotes(Long applicationId, String sectionCode, String attributeCode, String taskDefinitionKey,String stageKey, NotesTypeEnum notesType);
    void updateApplicationNotesDoneStatus(Long applicationNoteId);
    Long updateAppNote(ApplicationNotesReqDto dto);
    Long addOrUpdateAppNotes(ApplicationNotesReqDto dto);
    void deleteAppNote(ApplicationNotesReqDto dto);
    List<LkNotesDto> findAppNotesByDecisionAndNotesType(SubstantiveReportDto dto, String decision, String notesType);
    ApplicationNotesResponseDto findAllNotes(Long applicationId, String sectionCode, String attributeCode, Long priorityId, String taskDefinitionKey,String stageKey);
    List<ApplicationNotesResponseDto> findAllApplicationNotes(Long applicationId,String taskDefinitionKey);
    List<ApplicationNotesReportDto> getExaminerNotes(Long applicationId, String sectionCode, String attributeCode, String stageKey, String categoryCode);
    void markAllNotesAsDoneByApplicationId(Long appId);
    List<SectionApplicationNotesResponseDto> getAllApplicationNotesWithApplicationIdAndStageKey(Long appId, String stageKey,String sectionCode);
    Boolean existsNotesByApplicationIdAndStageKey(Long applicationId, String stageKey);
    String getPvLastApplicantOppositionForInvitationCorrection(Long appId,String taskKey);
    String getPTLastApplicantOppositionForInvitationCorrection(Long appId,String taskKey);
    Boolean haveApplicantOppositionForInvitationCorrectionPAT(Long appId,String taskDefinitionKey);

}
