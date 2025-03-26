package gov.saip.applicationservice.modules.plantvarieties.controller;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicantsRequestDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.ApplicationNotesReqDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.modules.plantvarieties.dto.*;
import gov.saip.applicationservice.modules.plantvarieties.service.PlantVarietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = {"/kc/plant-variety-details", "/internal-calling/plant-variety-details"})
@RequiredArgsConstructor
@Slf4j
public class PlantVarietyController {

    private final PlantVarietyService plantVarietyService;

    @PostMapping("/application")
    public ApiResponse<Long> save(@Valid @RequestBody ApplicantsRequestDto applicantsRequestDto) {
        return ApiResponse.ok(plantVarietyService.saveApplication(applicantsRequestDto));
    }

    @PutMapping("/summary")
    public ApiResponse<Long> updateSummaryWithApplication(@RequestBody SummaryPlantVarietyDto summaryPlantVarietyDto,
                                                          @RequestParam(value = "nextStep", required = false) Boolean nextStep) {
        return ApiResponse.ok(plantVarietyService.updateSummaryWithApplication(summaryPlantVarietyDto,nextStep));
    }

    @GetMapping("/summary/application/{appId}")
    public ApiResponse<List<SummaryPlantVarietyDto>> getSummaryByApplicationId(@PathVariable Long appId) {
        return ApiResponse.ok(plantVarietyService.getSummaryWithApplicationId(appId));
    }

    @PutMapping("/technical-survey")
    public ApiResponse<Long> updateTechnicalSurveyWithApplication(@RequestBody TechnicalSurveyPlantVarietyDto technicalSurveyPlantVarietyDto,
                                                                  @RequestParam(value = "nextStep", required = false) Boolean nextStep) {
        return ApiResponse.ok(plantVarietyService.updateTechnicalSurveyWithApplication(technicalSurveyPlantVarietyDto,nextStep));
    }

    @GetMapping("/technical-survey/application/{appId}")
    public ApiResponse<List<TechnicalSurveyPlantVarietyDto>> getTechnicalSurveyByApplicationId(@PathVariable Long appId) {
        return ApiResponse.ok(plantVarietyService.getTechnicalSurveyByApplicationId(appId));
    }

    @PutMapping("/examination-data")
    public ApiResponse<Long> updateExaminationDataWithApplication(@RequestBody ExaminationDataPlantVarietyDto examinationDataPlantVarietyDto,
                                                                  @RequestParam(value = "nextStep", required = false) Boolean nextStep) {
        return ApiResponse.ok(plantVarietyService.updateExaminationDataWithApplication(examinationDataPlantVarietyDto,nextStep));
    }

    @GetMapping("/examination-data/application/{appId}")
    public ApiResponse<List<ExaminationDataPlantVarietyDto>> getExaminationDataByApplicationId(@PathVariable Long appId) {
        return ApiResponse.ok(plantVarietyService.getExaminationDataByApplicationId(appId));
    }

    @PutMapping("/priority-section")
    public ApiResponse<Long> updatePriorityDataSectionWithApplication(@RequestBody PriorityDataSectionPlantVarietyDto priorityDataSectionPlantVarietyDto,
                                                                      @RequestParam(value = "nextStep", required = false) Boolean nextStep) {
        return ApiResponse.ok(plantVarietyService.updatePriorityDataSectionWithApplication(priorityDataSectionPlantVarietyDto,nextStep));
    }
    @GetMapping("/priority-section/application/{appId}")
    public ApiResponse<List<PriorityDataSectionPlantVarietyDto>> getPriorityDataSectionWithApplication(@PathVariable Long appId) {
        return ApiResponse.ok(plantVarietyService.getPriorityDataSectionWithApplication(appId));
    }
    @PutMapping("/prove-excellence/variables")
    public ApiResponse<Long> updateProveExcellenceVariablesWithApplication(@RequestBody PlantProveExcellenceVariablesDto proveExcellenceVariablesDto,
                                                                           @RequestParam(value = "nextStep", required = false) Boolean nextStep) {
        return ApiResponse.ok(plantVarietyService.updateProveExcellenceVariablesWithApplication(proveExcellenceVariablesDto,nextStep));
    }
    @GetMapping("/prove-excellence/variables/{applicationId}")
    public ApiResponse<List <PlantProveExcellenceVariablesDto>> getProveExcellenceVariablesWithApplication(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(plantVarietyService.getProveExcellenceVariablesWithApplication(applicationId));
    }


    @GetMapping("/info/{applicationId}")
    public ApiResponse<PlantVarietyRequestDto> getPlantVarietiesApplicationInfo(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(plantVarietyService.getPlantVarietiesApplicationInfo(applicationId));
    }

    @GetMapping("/application/{applicationId}")
    public ApiResponse<Long> getPlantVarietyId(@PathVariable(value = "applicationId") Long applicationId) {
        return ApiResponse.ok(plantVarietyService.getPlantVarietyId(applicationId));
    }


    @PostMapping("/properties-options")
    public ApiResponse<Long> addPlantDetailsPropertiesOptions(@RequestBody PlantDetailsPropertiesOptionsDto plantDetailsPropertiesOptionsDto) {
        return ApiResponse.ok(plantVarietyService.addPlantDetailsPropertiesOptions(plantDetailsPropertiesOptionsDto));
    }

    @PutMapping("/properties-options")
    public ApiResponse<Long> updatePlantDetailsPropertiesOptions(@RequestBody PlantDetailsPropertiesOptionsDto plantDetailsPropertiesOptionsDto) {;
        return ApiResponse.ok(plantVarietyService.updatePlantDetailsPropertiesOptions(plantDetailsPropertiesOptionsDto));
    }


    @GetMapping("/properties-options/{applicationId}")
    public ApiResponse<PlantDetailsPropertiesOptionsDto> findPropertiesAndOptionsByPlantVarietyDetailsId(@PathVariable("applicationId")Long applicationId){
        return ApiResponse.ok(plantVarietyService.findPropertiesAndOptionsByPlantVarietyDetailsId(applicationId));
    }


    @GetMapping
    public ApiResponse<PaginationDto<List<PlantVarietiesListDto>>> listApplications(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "statusCode", required = false) String statusCode,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        PaginationDto<List<PlantVarietiesListDto>> applicationListByApplicationCategoryAndUserId =
                plantVarietyService.listPlantVarietiesApplication(page, limit, query, statusCode, sortDirection);
        return ApiResponse.ok(applicationListByApplicationCategoryAndUserId);
    }


    @GetMapping("/vegetarian-type/{applicationId}")
    public ApiResponse<Long> findVegetarianTypeIdByPlantDetailsId(@PathVariable("applicationId") Long applicationId){
        return ApiResponse.ok(plantVarietyService.findVegetarianTypeIdByPlantDetailsId(applicationId));
    }
    @PutMapping("/diseases-description")
    public ApiResponse<Long> updatePlantVarietyDetails(@RequestBody PlantDetailsPropertiesOptionsDto dto){
       return ApiResponse.ok(plantVarietyService.updatePlantVarietyDetails(dto));
    }


    @GetMapping("/get-priority/{appId}")
    public ApiResponse<PriorityPlantVarietyDto> getPriorityPlantVarietyDto(@PathVariable("appId") Long appId){
        return ApiResponse.ok(plantVarietyService.getPriorityPlantVarietyDto(appId));
    }

    @PutMapping("/application/{applicationId}/start-formal-checking")
    ApiResponse updateApplicationStatusToStartFormalChecking(@PathVariable(name = "applicationId") Long applicationId) {
        plantVarietyService.updateApplicationStatusToStartFormalProcess(applicationId);
        return ApiResponse.ok();
    }
    @PutMapping("/application/{applicationId}/status/{statusCode}/firstAssignationDate")
    ApiResponse updateStatusAndSetFirstAssignationDateByApplicationId(@PathVariable(name = "applicationId") Long applicationId,  @PathVariable(name="statusCode") ApplicationStatusEnum statusCode) {
        plantVarietyService.updateStatusAndSetFirstAssignationDateByApplicationId(applicationId, statusCode);
        return ApiResponse.ok();
    }
    @PostMapping("/pdf/{reportName}")
    ApiResponse<List<DocumentDto>> generateJasperPdf(@RequestBody ReportRequestDto dto , @PathVariable(name = "reportName") String reportName, @RequestParam(value = "documentType") DocumentTypeEnum documentType) throws IOException {
        List<DocumentDto>  documentDtos= plantVarietyService.generateJasperPdf(dto,reportName, documentType);
        return ApiResponse.ok(documentDtos);
    }



    @PostMapping("/add-applicant-opposition")
    public ApiResponse<Long> applicantOppositionForCorrectionInvitations(@RequestBody ApplicationNotesReqDto applicationNotesReqDto) {
        return ApiResponse.ok(plantVarietyService.applicantOppositionForCorrectionInvitations(applicationNotesReqDto));
    }


    @GetMapping("/last-pv-applicant-opposition/{appId}")
    public ApiResponse<String> getPvLastApplicantOppositionForInvitationCorrection(@PathVariable("appId") Long appId,
                                                                                   @RequestParam(value = "taskKey", required = false) String taskKey) {
        return ApiResponse.ok(plantVarietyService.getPvLastApplicantOppositionForInvitationCorrection(appId,taskKey));
    }


}


