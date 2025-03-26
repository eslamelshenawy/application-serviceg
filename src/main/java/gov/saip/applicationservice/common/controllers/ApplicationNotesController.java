package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.service.ApplicationNotesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/application-notes"})
@Slf4j
@RequiredArgsConstructor
public class ApplicationNotesController {

    private final ApplicationNotesService applicationNotesService;

    @GetMapping
    public ApiResponse getAllApplicationNotes(
            @RequestParam(name = "applicationId") Long applicationId,
            @RequestParam(name = "stepId", required = false) Integer stepId,
            @RequestParam(name = "sectionId", required = false) Integer sectionId,
            @RequestParam(name = "taskDefinitionKey", required = false) String taskDefinitionKey,
            @RequestParam(name = "notesType", required = false) NotesTypeEnum notesType)
            throws JsonProcessingException {
        return ApiResponse.ok(applicationNotesService.getAllApplicationNotes(applicationId, stepId, sectionId, taskDefinitionKey, notesType));
    }

    @GetMapping("/{applicationId}")
    public ApiResponse<List<ApplicationNotesDto>> findAppNotes(
            @PathVariable(name = "applicationId") Long applicationId,
            @RequestParam(name = "sectionCode", required = false) String sectionCode,
            @RequestParam(name = "attributeCode", required = false) String attributeCode,
            @RequestParam(name = "taskDefinitionKey", required = false) String taskDefinitionKey,
            @RequestParam(name = "stageKey", required = false) String stageKey,
            @RequestParam(name = "notesType", required = false) NotesTypeEnum notesType) {
        return ApiResponse.ok(applicationNotesService.findAppNotes(applicationId, sectionCode, attributeCode, taskDefinitionKey,stageKey, notesType));
    }

    @PutMapping("/done/{applicationNoteId}")
    public void updateApplicationNotesDoneStatus(
            @PathVariable(name = "applicationNoteId") Long applicationNoteId) {
        applicationNotesService.updateApplicationNotesDoneStatus(applicationNoteId);
    }

    @PostMapping()
    public ApiResponse addAppNote(@RequestBody ApplicationNotesReqDto dto) {
        return ApiResponse.ok(applicationNotesService.addOrUpdateAppNotes(dto));
    }

    @PutMapping()
    public ApiResponse updateAppNote(@RequestBody ApplicationNotesReqDto dto) {
        return ApiResponse.ok(applicationNotesService.updateAppNote(dto));
    }

    @GetMapping("/application/{appId}/stageKey/{stageKey}")
    public ApiResponse<List<SectionApplicationNotesResponseDto>> getAllApplicationNotesWithApplicationIdAndStageKey(
            @PathVariable(name = "appId") Long appId,
            @PathVariable(name = "stageKey") String stageKey,
            @RequestParam(name = "sectionCode", required = false) String sectionCode) {
        return ApiResponse.ok(applicationNotesService.getAllApplicationNotesWithApplicationIdAndStageKey(appId,stageKey,sectionCode));
    }

    @GetMapping("/exists-notes/{appId}/stageKey/{stageKey}")
    public ApiResponse<Boolean> existsNotesByApplicationIdAndStageKey(@PathVariable(name = "appId") Long appId,@PathVariable(name = "stageKey") String stageKey) {
        return ApiResponse.ok(applicationNotesService.existsNotesByApplicationIdAndStageKey(appId,stageKey));
    }
    @GetMapping("/exists-notes-opposition/{appId}/taskDefinitionKey/{taskDefinitionKey}")
    public ApiResponse<Boolean> haveApplicantOppositionForInvitationCorrectionPAT(@PathVariable(name = "appId") Long appId,@PathVariable(name = "taskDefinitionKey") String taskDefinitionKey){
        return ApiResponse.ok(applicationNotesService.haveApplicantOppositionForInvitationCorrectionPAT(appId,taskDefinitionKey));
    }
}
