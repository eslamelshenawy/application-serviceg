package gov.saip.applicationservice.common.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.patent.PctDto;
import gov.saip.applicationservice.common.enums.NotesTypeEnum;
import gov.saip.applicationservice.common.model.patent.Pct;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.impl.ReGenerateBillCallbackService;
import gov.saip.applicationservice.common.service.lookup.LkApplicationCategoryService;
import gov.saip.applicationservice.common.service.lookup.LkFastTrackExaminationTargetAreaService;
import gov.saip.applicationservice.common.service.patent.PatentDetailsService;
import gov.saip.applicationservice.common.service.patent.PctService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal-calling")
@RequiredArgsConstructor
@Slf4j
public class InternalCallingController {
    private final ApplicationPriorityService applicationPriorityService;

    private final DocumentsService documentsService;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationAcceleratedService applicationAcceleratedService;

    private final ClassificationService classificationService;

    private final LkFastTrackExaminationTargetAreaService fastTrackExaminationTargetAreaService;

    private final PatentDetailsService patentDetailsService;
    private final LkApplicationCategoryService applicationCategoryService;

    private final ApplicationNotesService applicationNotesService;
    private final PctService pctService;
    private final ReGenerateBillCallbackService reGenerateBillCallBack;



    @PostMapping(value = "/applications/{id}/application-number")
    public ApiResponse<Long> generateApplicationNumber(@Valid @RequestBody ApplicationNumberGenerationDto applicationNumberGenerationDto, @PathVariable Long id) {
        log.info("payment call back request with id {}, request type {} and payment date {} ", id, applicationNumberGenerationDto.getMainRequestType(), applicationNumberGenerationDto.getPaymentDate());
        return ApiResponse.ok(applicationInfoService.generateApplicationNumber(applicationNumberGenerationDto, id));
    }

    @PostMapping(value = "/applications/re-generate-bill")
    public ApiResponse<Long> reGenerateBillCallBack(@RequestBody ReCreateBillDto billDto) {
        return ApiResponse.ok(reGenerateBillCallBack.reGenerateBillCallBack(billDto));
    }


    @GetMapping("/classification/unitId/{unitId}")
    public ApiResponse<List<ClassificationDto>> findClassificationByUnitId(@PathVariable List<Long> unitId) {
        return ApiResponse.ok(classificationService.findByUnitIdIn(unitId));
    }

    @GetMapping("/applications/{id}")
    public ApiResponse<ApplicationInfoDto> getApplication(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.getApplication(id));
    }

    @GetMapping("/fastTrackExaminationTargetAreas")
    public List<LkFastTrackExaminationTargetAreaDto> getAllFastTrackExaminationTargetAreas() {
        return fastTrackExaminationTargetAreaService.getAllFastTrackExaminationTargetAreas();
    }

    @GetMapping("/category")
    public ApiResponse<List<LKApplicationCategoryDto>> getAllCategories() {
        return ApiResponse.ok(applicationCategoryService.getAll());
    }


    @GetMapping(value = "/{id}/payment")
    public ApiResponse<ApplicationInfoPaymentDto> getApplicationInfoPayment(@PathVariable Long id) {
        return ApiResponse.ok(applicationInfoService.getApplicationInfoPayment(id));
    }

    @PostMapping(value = "/{id}/payment")
    public ApiResponse<Long> submitApplicationInfoPayment(@PathVariable Long id, @RequestBody ApplicationInfoPaymentDto applicationInfoPaymentDto) {
        return ApiResponse.ok(applicationInfoService.submitApplicationInfoPayment(id, applicationInfoPaymentDto));
    }

    @GetMapping("/{id}/payment/current")
    public ApplicationInfoPaymentDto getApplicationPayment(@PathVariable("id") Long id) {
        return applicationInfoService.getApplicationPayment(id);
    }

    @GetMapping("/application-notes")
    public ApiResponse<List<SectionApplicationNotesResponseDto>> getAllApplicationNotes(
            @RequestParam(name = "applicationId") Long applicationId,
            @RequestParam(name = "stepId", required = false) Integer stepId,
            @RequestParam(name = "sectionId", required = false) Integer sectionId,
            @RequestParam(name = "taskDefinitionKey", required = false) String taskDefinitionKey,
            @RequestParam(name = "notesType", required = false) NotesTypeEnum notesType) throws JsonProcessingException {
        return ApiResponse.ok(applicationNotesService.getAllApplicationNotes(applicationId, stepId, sectionId, taskDefinitionKey, notesType));
    }

    @GetMapping("/application-notes/{applicationId}")
    public ApiResponse<List<ApplicationNotesDto>> findAppNotes(
            @PathVariable(name = "applicationId") Long applicationId,
            @RequestParam(name = "sectionCode", required = false) String sectionCode,
            @RequestParam(name = "attributeCode", required = false) String attributeCode,
            @RequestParam(name = "taskDefinitionKey", required = false) String taskDefinitionKey,
            @RequestParam(name = "stageKey", required = false) String stageKey,
            @RequestParam(name = "notesType", required = false) NotesTypeEnum notesType) {
        return ApiResponse.ok(applicationNotesService.findAppNotes(applicationId, sectionCode, attributeCode, taskDefinitionKey,stageKey, notesType));
    }

    @GetMapping("application/{appId}/application-notes/stageKey/{stageKey}")
    public ApiResponse<List<SectionApplicationNotesResponseDto>> getAllApplicationNotesWithApplicationIdAndStageKey(
            @PathVariable(name = "appId") Long appId,
            @PathVariable(name = "stageKey") String stageKey,
            @RequestParam(name = "sectionCode", required = false) String sectionCode) {
        return ApiResponse.ok(applicationNotesService.getAllApplicationNotesWithApplicationIdAndStageKey(appId,stageKey,sectionCode));
    }

    @PutMapping("/application-notes/done/{applicationNoteId}")
    public void updateApplicationNotesDoneStatus(
            @PathVariable(name = "applicationNoteId") Long applicationNoteId) {
        applicationNotesService.updateApplicationNotesDoneStatus(applicationNoteId);
    }

    @PutMapping("/app-notes")
    public ApiResponse<Long> addOrUpdateAppNotes(@RequestBody ApplicationNotesReqDto dto) {
        return ApiResponse.ok(applicationNotesService.addOrUpdateAppNotes(dto));
    }

    @PutMapping("/application-notes")
    public ApiResponse<Long> updateAppNote(@RequestBody ApplicationNotesReqDto dto) {
        return ApiResponse.ok(applicationNotesService.updateAppNote(dto));
    }

    @DeleteMapping("/application-notes")
    public ApiResponse<Long> deleteAppNotes(@RequestBody @Valid ApplicationNotesReqDto applicationNotesDto) {
        applicationNotesService.deleteAppNote(applicationNotesDto);
        return ApiResponse.ok();
    }

    @GetMapping("/document/applications/{id}")
    public ApiResponse<DocumentDto> findDocumentByApplicationId(@PathVariable Long id
            , @RequestParam("typeName") String typeName) {
        return ApiResponse.ok(documentsService.findDocumentByApplicationIdAndDocumentType(id, typeName));
    }

    //TODO
    @GetMapping("/latest-document/applications/{id}")
    public ApiResponse<DocumentDto> findLatestDocumentByApplicationIdAndDocumentType(@PathVariable Long id
            , @RequestParam("typeName") String typeName) {
        return ApiResponse.ok(documentsService.findLatestDocumentByApplicationIdAndDocumentType(id, typeName));
    }

    @GetMapping("/check/accelrated/{id}")
    public Boolean checkIfApplicationAccelrated(@PathVariable(name = "id") Long id) {
        return applicationAcceleratedService.checkIfApplicationAccelrated(id);
    }

    @PostMapping("/documents/application")
    public ApiResponse<List<DocumentWithCommentDto>> getApplicationDocuments(@RequestBody DocumentApplicationTypesDto documentApplicationTypesDto) {
        return ApiResponse.ok(documentsService.findDocumentByApplicationID(documentApplicationTypesDto));
    }

    @GetMapping("/priorities/application/{appId}")
    public ApiResponse<List<ApplicationPriorityListDto>> getApplicationPriorites(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationPriorityService.listApplicationPriorites(appId));
    }

    @GetMapping("/priorities/application/{appId}/provideDocLater")
    public ApiResponse<Boolean> checkApplicationPrioritiesProvideDocLater(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationPriorityService.checkApplicationPrioritiesProvideDocLater(appId));
    }

    @PutMapping("/priorities/{id}/update-priority-status-comment")
    public ApiResponse<Long> updatePriorityDocs(@RequestBody ApplicationPriorityUpdateStatusAndCommentsDto updateStatuscommentDto
            , @PathVariable Long id) {
        return ApiResponse.ok(applicationPriorityService.updatePriorityStatusAndComment(id, updateStatuscommentDto));
    }

    @GetMapping("/applications")
    public ApiResponse<List<PartialApplicationInfoDto>> getApplicationByApplicationPartialNumber(
            @RequestParam(name = "partialNumber") String partialNumber) {
        return ApiResponse.ok(applicationInfoService.getApplicationByApplicationPartialNumber(partialNumber));
    }

    @GetMapping("/application/classifications")
    public ApiResponse<List<ApplicationClassificationLightDto>> getApplicationClassificationsByApplicationIds(
            @RequestParam(name = "ids") List<Long> ids) {
        return ApiResponse.ok(applicationInfoService.getApplicationClassification(ids));
    }

    @GetMapping("/application/task-data")
    public ApiResponse<Map<Long, ApplicationInfoTaskDto>> getApplicationStatusByApplicationIds(@RequestParam(name = "ids") List<Long> ids) {
        return ApiResponse.ok(applicationInfoService.getApplicationStatusByApplicationIds(ids));
    }

    @GetMapping(value = "application/{id}/application-accelerated")
    public ApiResponse<ApplicationAcceleratedLightDto> getApplicationAccelerated(@PathVariable Long id) {
        return ApiResponse.ok(applicationAcceleratedService.getByApplicationId(id));
    }

    @PutMapping("application/{id}/application-accelerated/refused")
    public ApiResponse<?> updateApplicationAcceleratedStatus(
            @PathVariable Long id, @RequestParam(value = "refused", required = false) Boolean refused) {
        applicationAcceleratedService.updateApplicationAcceleratedStatus(id, refused);
        return ApiResponse.ok(null);
    }

    @PutMapping("application/{id}/application-accelerated/decision")
    public ApiResponse<?> updateApplicationAcceleratedDecision(
            @PathVariable Long id, @RequestParam(value = "decision", required = false) String decision) {
        applicationAcceleratedService.updateApplicationAcceleratedDecision(id, decision);
        return ApiResponse.ok(null);
    }

    @GetMapping("/application/{id}/classifications/unit-ids")
    public ApiResponse<List<Long>> getApplicationClassificationUnitIdsByAppId(
            @PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationInfoService.getApplicationClassificationUnitIdsByAppId(id));
    }

    /**
     * Get PCT application by application ID.
     *
     * @param id the application ID
     * @return an {@link ApiResponse} containing the {@link Pct} application
     */
    @GetMapping("/pct/application/{id}")
    public ApiResponse<Pct> getByApplicationId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(pctService.findByApplicationId(id));
    }

    @GetMapping("/application/{id}/status-code")
    public ApiResponse<String> getByApplicationStatus(@PathVariable Long id) {
        return ApiResponse.ok(applicationInfoService.getApplicationStatus(id));
    }

    @GetMapping("/accelerated-applications/applications")
    ApiResponse<Map<Long, ApplicationAcceleratedDto>> getAcceleratedApplications(@RequestParam(name = "ids") List<Long> appIds) {
        return ApiResponse.ok(applicationAcceleratedService.getAcceleratedApplications(appIds));
    }

    @GetMapping("/check-application/accelerated/{id}")
    public Boolean isApplicationInfoHasAcceleratedApplication(@PathVariable(name = "id") Long id) {
        return applicationAcceleratedService.isApplicationInfoHasAcceleratedApplication(id);
    }

    @GetMapping("/check-application/accelerated/decision/{id}")
    public Boolean isApplicationInfoHasAcceleratedApplicationHasDecision(@PathVariable(name = "id") Long id) {
        return applicationAcceleratedService.checkIfApplicationAcceleratedHasDecisionTakenYet(id);
    }

    @GetMapping("/retrieve-accelerated/application/{id}")
    public ApiResponse<ApplicationAcceleratedDto> getAcceleratedApplicationByApplicationId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(applicationAcceleratedService.getAcceleratedApplicationByApplicationId(id));
    }

    @GetMapping("/all-notes/{applicationId}")
    public ApiResponse<ApplicationNotesResponseDto> findAllNotes(
            @PathVariable(name = "applicationId") Long applicationId,
            @RequestParam(name = "sectionCode", required = false) String sectionCode,
            @RequestParam(name = "attributeCode", required = false) String attributeCode,
            @RequestParam(name = "priorityId", required = false) Long priorityId,
            @RequestParam(name = "taskDefinitionKey", required = false) String taskDefinitionKey,
            @RequestParam(name = "stageKey", required = false) String stageKey) {
        return ApiResponse.ok(applicationNotesService.findAllNotes(applicationId, sectionCode, attributeCode,priorityId, taskDefinitionKey, stageKey));
    }

    @GetMapping("/report-examiner-notes/{applicationId}")
    public ApiResponse<List<ApplicationNotesReportDto>> getReportExaminerNotes(
            @PathVariable(name = "applicationId") Long applicationId,
            @RequestParam(name = "sectionCode", required = false) String sectionCode,
            @RequestParam(name = "attributeCode", required = false) String attributeCode,
            @RequestParam(name = "categoryCode", required = false) String categoryCode,
            @RequestParam(name = "stageKey", required = false) String stageKey) {
        return ApiResponse.ok(applicationNotesService.getExaminerNotes(applicationId, sectionCode, attributeCode, stageKey, categoryCode));
    }

    @GetMapping("/all-application-notes/{applicationId}")
    public ApiResponse<List<ApplicationNotesResponseDto>> findAllApplicationNotes(
            @PathVariable(name = "applicationId") Long applicationId,
            @RequestParam(name = "taskDefinitionKey", required = false) String taskDefinitionKey) {
        return ApiResponse.ok(applicationNotesService.findAllApplicationNotes(applicationId, taskDefinitionKey));
    }

    @GetMapping("/pct/application/{appId}/filing-date")
    ApiResponse<LocalDateTime> retrieveApplicationFilingDateOrPCTInternationalDate(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(pctService.getFillingDateOrInternationalDate(appId));
    }

    @GetMapping("/validate-pct/{appId}")
    public ApiResponse<Boolean> validatePCT(@PathVariable Long appId) {
        return ApiResponse.ok(applicationInfoService.checkPltRegister(appId));
    }

    @GetMapping("/pct-patent/application/{id}")
    public ApiResponse<PctDto> findPCTDTOByApplicationId(@PathVariable(name="id") Long id) {
        return ApiResponse.ok(pctService.findDTOByApplicationId(id));
    }

    @GetMapping("/patent-Examiner-report/{appId}")
    public ResponseEntity<ByteArrayResource> generatePatentExaminerReport(@PathVariable(name = "appId") Long appId, @RequestParam(value = "documentType") String documentType) throws IOException {

        ByteArrayResource file=patentDetailsService.generatePatentExaminerReports(appId,documentType);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=patent-grant.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.contentLength()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(file);
    }
    @GetMapping("/generate-report-upload-nexus/{appId}")
    public ApiResponse<DocumentDto> generateReportUploadNexus(@PathVariable(name = "appId") Long appId, @RequestParam(value = "documentType") String documentType) throws IOException {
        return ApiResponse.ok(patentDetailsService.generateReportByDocumentType(appId,documentType));
    }



    @PutMapping("/application-notes/all-done/{appId}")
    public ApiResponse<Void> markAllNotesAsDoneByApplicationId(@PathVariable(name = "appId") Long appId) {
        applicationNotesService.markAllNotesAsDoneByApplicationId(appId);
        return ApiResponse.noContent();
    }

    @GetMapping("/classification-unit/id/by/app/{appId}")
    public ApiResponse<Long> getApplicationClassificationUnitIdByApplicationId(@PathVariable(name = "appId") Long appId)  {
        return ApiResponse.ok(applicationInfoService.getApplicationUnitClassificationIdByApplicationId(appId));
    }

    @GetMapping("/process-request-type-code/{appId}")
    public ApiResponse<String> getProcessRequestTypeCodeByApplicationId(@PathVariable(name = "appId") Long appId)  {
        return ApiResponse.ok(applicationInfoService.getProcessRequestTypeCodeByApplicationId(appId));
    }
    @GetMapping("/check-non-reviewed-app-priorites/{appId}")
    public Boolean doesApplicationHasPrioritiesThatNoActionTaken(@PathVariable(name="appId") Long appId) {
        return applicationPriorityService.doesApplicationHasPrioritiesThatHaveNoActionTaken(appId);
    }
    @GetMapping("/check-document-priorites/{appId}")
    public Boolean checkApplicationPrioritiesDocuments(@PathVariable(name="appId") Long appId) {
        return applicationPriorityService.checkApplicationPrioritiesDocuments(appId);
    }
    @GetMapping("/exists-notes/{appId}/stageKey/{stageKey}")
    public ApiResponse<Boolean> existsNotesByApplicationIdAndStageKey(@PathVariable(name = "appId") Long appId,@PathVariable(name = "stageKey") String stageKey) {
        return ApiResponse.ok(applicationNotesService.existsNotesByApplicationIdAndStageKey(appId,stageKey));
    }


    @GetMapping(value = "/no-priority-document/{appId}")
    public ApiResponse<List<ApplicationPriorityListDto>> getPrioritiesThatHaveNotPriorityDocument(@PathVariable("appId") Long appId) {
        return ApiResponse.ok(applicationPriorityService.getPrioritiesThatHaveNotPriorityDocument(appId));
    }
    @GetMapping("/get-accelerated/{id}")
    public ApiResponse<ApplicationAcceleratedDto> findAcceleratedByApplicationId(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationAcceleratedService.findAcceleratedByApplicationId(appId));
    }
    @GetMapping("/exists-notes-opposition/{appId}/taskDefinitionKey/{taskDefinitionKey}")
    public ApiResponse<Boolean> haveApplicantOppositionForInvitationCorrectionPAT(@PathVariable(name = "appId") Long appId,@PathVariable(name = "taskDefinitionKey") String taskDefinitionKey){
        return ApiResponse.ok(applicationNotesService.haveApplicantOppositionForInvitationCorrectionPAT(appId,taskDefinitionKey));
    }
}