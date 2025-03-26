package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.ApplicationNotesReqDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.modules.common.service.BaseApplicationInfoService;
import gov.saip.applicationservice.modules.plantvarieties.dto.*;
import gov.saip.applicationservice.modules.plantvarieties.model.PlantVarietyDetails;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;
public interface PlantVarietyService extends BaseApplicationInfoService {
    Long updateSummaryWithApplication (SummaryPlantVarietyDto summaryPlantVarietyDto,Boolean nextStep);
    List<SummaryPlantVarietyDto> getSummaryWithApplicationId(Long appId);
    Long updateTechnicalSurveyWithApplication (TechnicalSurveyPlantVarietyDto technicalSurveyPlantVarietyDto,Boolean nextStep);
    List<TechnicalSurveyPlantVarietyDto> getTechnicalSurveyByApplicationId(Long appId);
    Long updateExaminationDataWithApplication (ExaminationDataPlantVarietyDto examinationDataPlantVarietyDto,Boolean nextStep);
    List<ExaminationDataPlantVarietyDto>getExaminationDataByApplicationId(Long appId);
    List<PriorityDataSectionPlantVarietyDto>getPriorityDataSectionWithApplication(Long appId);
    Long updatePriorityDataSectionWithApplication (PriorityDataSectionPlantVarietyDto priorityDataSectionPlantVarietyDto,Boolean nextStep);
    Long updateProveExcellenceVariablesWithApplication (PlantProveExcellenceVariablesDto plantProveExcellenceVariablesDto,Boolean nextStep);
    PlantVarietyRequestDto getPlantVarietiesApplicationInfo(Long appId);
    Long getPlantVarietyId(Long appId);
    List <PlantProveExcellenceVariablesDto> getProveExcellenceVariablesWithApplication(Long appId);
    Long addPlantDetailsPropertiesOptions(PlantDetailsPropertiesOptionsDto plantDetailsPropertiesOptionsDto);
    PlantDetailsPropertiesOptionsDto findPropertiesAndOptionsByPlantVarietyDetailsId(Long applicationId);
     Long updatePlantVarietyDetails(PlantDetailsPropertiesOptionsDto dto);
     PaginationDto<List<PlantVarietiesListDto>> listPlantVarietiesApplication(Integer page, Integer limit, String query, String status, Sort.Direction sortDirection);
     void deleteByAppId(Long appId);
     Long findVegetarianTypeIdByPlantDetailsId(Long applicationId);
     Long updatePlantDetailsPropertiesOptions(PlantDetailsPropertiesOptionsDto plantDetailsPropertiesOptionsDto);
     PlantVarietyDetails findPlantVarietyDetailsById(Long plantDetailsId);
     PriorityPlantVarietyDto getPriorityPlantVarietyDto(Long appId);
    void updateApplicationStatusToStartFormalProcess(Long applicationId);
    void updateStatusAndSetFirstAssignationDateByApplicationId(Long applicationId, ApplicationStatusEnum code);
    List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String reportName, DocumentTypeEnum documentType) throws IOException;

    Long applicantOppositionForCorrectionInvitations(ApplicationNotesReqDto applicationNotesReqDto);

    String getPvLastApplicantOppositionForInvitationCorrection(Long appId, String taskKey);

}