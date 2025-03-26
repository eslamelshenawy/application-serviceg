package gov.saip.applicationservice.modules.plantvarieties.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.clients.rest.feigns.UserManageClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationNotesService;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.IndustrialAndIntegratedCircuitReportGenerationHelper;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.common.service.impl.BaseApplicationInfoServiceImpl;
import gov.saip.applicationservice.modules.plantvarieties.dto.*;
import gov.saip.applicationservice.modules.plantvarieties.mapper.*;
import gov.saip.applicationservice.modules.plantvarieties.model.*;
import gov.saip.applicationservice.modules.plantvarieties.repository.LKPlantDetailsRepository;
import gov.saip.applicationservice.modules.plantvarieties.repository.LkVegetarianTypeRepository;
import gov.saip.applicationservice.modules.plantvarieties.repository.PlantDetailsPropertiesOptionsRepository;
import gov.saip.applicationservice.modules.plantvarieties.repository.PlantVarietyRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.DUSTestingDocumentPlantVarietyService;
import gov.saip.applicationservice.modules.plantvarieties.service.FillingRequestInOtherCountryPlantVarietyService;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPVPropertyOptionsService;
import gov.saip.applicationservice.modules.plantvarieties.service.PlantVarietyService;
import gov.saip.applicationservice.modules.plantvarieties.validators.*;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_PLANT_VARIETIES;
import static gov.saip.applicationservice.common.enums.DocumentTypeEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantVarietyServiceImpl extends BaseApplicationInfoServiceImpl implements PlantVarietyService {

    private final PlantVarietyRepository plantVarietyRepository;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final SummaryPlantVarietyValidator summaryPlantVarietyValidator;
    private final ExaminationDataPlantValidator examinationDataPlantValidator;
    private final TechnicalSurveyPlantVarietyValidator technicalSurveyPlantVarietyValidator;
    private final PriorityDataSectionPlantVarietyValidator priorityDataSectionPlantVarietyValidator;
    private final ProveExcellenceVariablesValidator proveExcellenceVariablesValidator;
    private final SummaryPlantVarietyMapper summaryPlantVarietyMapper;
    private final TechnicalSurveyPlantVarietyMapper technicalSurveyPlantVarietyMapper;
    private final ExaminationDataPlantVarietyMapper examinationDataPlantVarietyMapper;
    private final PriorityDataSectionPlantVarietyMapper priorityDataSectionPlantVarietyMapper;
    private final PlantProveExcellenceVariablesMapper plantProveExcellenceVariablesMapper;
    private final DocumentsService documentsService;
    private final PlantDetailsPropertiesOptionsRepository plantDetailsPropertiesOptionsRepository;
    private final LKPVPropertyOptionsService lkpvPropertyOptionsService;
    private final LKPlantDetailsRepository lkPlantDetailsRepository;
    private final LkVegetarianTypeRepository lkVegetarianTypesRepository;
    @Autowired
    @Lazy
    private ApplicationInfoService applicationInfoService;

    @Autowired
    @Lazy
    private  ApplicationNotesService applicationNotesService;
    private final BPMCallerFeignClient bpmCallerFeignClient;


    private final DUSTestingDocumentPlantVarietyService dusTestingDocumentPlantVarietyService;
    private final FillingRequestInOtherCountryPlantVarietyService fillingRequestInOtherCountryPlantVarietyService;
    private final UserManageClient userManageClient;
    private final IndustrialAndIntegratedCircuitReportGenerationHelper industrialAndIntegratedCircuitReportGenerationHelper;



    @Override
    public ApplicationInfoRepository getBaseApplicationInfoRepository() {
        return applicationInfoRepository;
    }

    @Override
    @PostConstruct
    public void init() {
        registerService(ApplicationCategoryEnum.PLANT_VARIETIES, this);
    }

    @Override
    public ApplicationCategoryEnum getApplicationCategoryEnum() {
        return ApplicationCategoryEnum.PLANT_VARIETIES;
    }

    @Override
    @Transactional
    public Long saveApplication(ApplicantsRequestDto applicantsRequestDto) {
        Long appId = super.saveApplication(applicantsRequestDto);
        if(!plantVarietyRepository.checkApplicationHasPlantVariety(appId))
            saveEmptyPlantVariety(appId);
        return appId;
    }

    private Long saveEmptyPlantVariety(Long applicationId){
        PlantVarietyDetails plantVariety = new PlantVarietyDetails();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(applicationId);
        plantVariety.setApplication(applicationInfo);
        return plantVarietyRepository.save(plantVariety).getId();
    }

    @Transactional
    @Override
    public Long updateSummaryWithApplication(SummaryPlantVarietyDto summaryPlantVarietyDto,Boolean nextStep) {
        if(nextStep ==null ||  Boolean.TRUE.equals(nextStep))
            summaryPlantVarietyValidator.validate(summaryPlantVarietyDto, null);
        PlantVarietyDetails plantVariety = getPlantVariety(summaryPlantVarietyDto);
        plantVariety = summaryPlantVarietyMapper.unMap(plantVariety, summaryPlantVarietyDto);
        return plantVarietyRepository.save(plantVariety).getId();
    }

    @Override
    public List<SummaryPlantVarietyDto> getSummaryWithApplicationId(Long appId) {
        return plantVarietyRepository.getSummaryWithApplicationId(appId);
    }

    @Transactional
    @Override
    public Long updateTechnicalSurveyWithApplication(TechnicalSurveyPlantVarietyDto technicalSurveyPlantVarietyDto,Boolean nextStep) {
        if(nextStep ==null ||  Boolean.TRUE.equals(nextStep))
            technicalSurveyPlantVarietyValidator.validate(technicalSurveyPlantVarietyDto, null);
        PlantVarietyDetails plantVariety = getPlantVariety(technicalSurveyPlantVarietyDto);
        plantVariety = technicalSurveyPlantVarietyMapper.unMap(plantVariety, technicalSurveyPlantVarietyDto);

        plantVariety.setHybridizationType(fetchLKPlantDetails(technicalSurveyPlantVarietyDto.getHybridizationTypeId(), "Hybridization type not found"));
        plantVariety.setReproductionMethod(fetchLKPlantDetails(technicalSurveyPlantVarietyDto.getReproductionMethodId(), "Reproduction method not found"));
        plantVariety.setVegetarianTypeUse(fetchLKPlantDetails(technicalSurveyPlantVarietyDto.getVegetarianTypeUseId(), "Vegetarian type use not found"));

        if (technicalSurveyPlantVarietyDto.getLkVegetarianTypesId() != null) {
            LkVegetarianTypes lkVegetarianTypes = lkVegetarianTypesRepository.findById(technicalSurveyPlantVarietyDto.getLkVegetarianTypesId())
                    .orElseThrow(() -> new EntityNotFoundException("Vegetarian type not found"));
            plantVariety.setLkVegetarianTypes(lkVegetarianTypes);
        } else {
            plantVariety.setLkVegetarianTypes(null);
        }

        if (Boolean.TRUE.equals(technicalSurveyPlantVarietyDto.getHybridizationTypeFlag())) {
            plantVariety.setItemProducedBy("hybridizationTypeFlag");
        }

        if (Boolean.TRUE.equals(technicalSurveyPlantVarietyDto.getLeapFlag())) {
            plantVariety.setItemProducedBy("leapFlag");
        }

        if (Boolean.TRUE.equals(technicalSurveyPlantVarietyDto.getDiscoveryFlag())) {
            plantVariety.setItemProducedBy("discoveryFlag");
        }

        if (Boolean.TRUE.equals(technicalSurveyPlantVarietyDto.getOtherFlag())) {
            plantVariety.setItemProducedBy("otherFlag");
        }


        updateApplicationInfoNames(technicalSurveyPlantVarietyDto);
        return plantVarietyRepository.save(plantVariety).getId();
    }
    private LKPlantDetails fetchLKPlantDetails(Integer id, String errorMessage) {
        if (id != null) {
            return lkPlantDetailsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(errorMessage));
        }
        return null;
    }


    @Override
    public List<TechnicalSurveyPlantVarietyDto> getTechnicalSurveyByApplicationId(Long appId) {
        return plantVarietyRepository.getTechnicalSurveyByApplicationId(appId);
    }

    @Transactional
    @Override
    public Long updateExaminationDataWithApplication(ExaminationDataPlantVarietyDto examinationDataPlantVarietyDto,Boolean nextStep) {
        if(nextStep ==null ||  Boolean.TRUE.equals(nextStep))
        examinationDataPlantValidator.validate(examinationDataPlantVarietyDto, null);
        PlantVarietyDetails plantVariety = getPlantVariety(examinationDataPlantVarietyDto);
        plantVariety = examinationDataPlantVarietyMapper.unMap(plantVariety, examinationDataPlantVarietyDto);
        return plantVarietyRepository.save(plantVariety).getId();
    }

    @Override
    public List<ExaminationDataPlantVarietyDto> getExaminationDataByApplicationId(Long appId) {
        return plantVarietyRepository.getExaminationDataByApplicationId(appId);
    }

    @Override
    public List<PriorityDataSectionPlantVarietyDto> getPriorityDataSectionWithApplication(Long appId) {
        return plantVarietyRepository.getPriorityDataSectionWithApplication(appId);
    }

    @Transactional
    @Override
    public Long updatePriorityDataSectionWithApplication(PriorityDataSectionPlantVarietyDto priorityDataSectionPlantVarietyDto,Boolean nextStep) {
        if(nextStep ==null ||  Boolean.TRUE.equals(nextStep))
        priorityDataSectionPlantVarietyValidator.validate(priorityDataSectionPlantVarietyDto, null);
        PlantVarietyDetails plantVariety = getPlantVariety(priorityDataSectionPlantVarietyDto);
        plantVariety = priorityDataSectionPlantVarietyMapper.unMap(plantVariety, priorityDataSectionPlantVarietyDto);
        return plantVarietyRepository.save(plantVariety).getId();
    }
    @Transactional
    @Override
    public Long updateProveExcellenceVariablesWithApplication(PlantProveExcellenceVariablesDto plantProveExcellenceVariablesDto,Boolean nextStep) {
        if(nextStep ==null ||  Boolean.TRUE.equals(nextStep))
        proveExcellenceVariablesValidator.validate(plantProveExcellenceVariablesDto,null);
        PlantVarietyDetails plantVariety = getPlantVariety(plantProveExcellenceVariablesDto);
        plantVariety = plantProveExcellenceVariablesMapper.unMap(plantVariety,plantProveExcellenceVariablesDto);
        return plantVarietyRepository.save(plantVariety).getId();
    }

    @Override
    public PlantVarietyRequestDto getPlantVarietiesApplicationInfo(Long appId) {
        PlantVarietyRequestDto plantVarietyRequestDto = plantVarietyRepository.getPlantVarietiesAppInfo(appId);
        if(plantVarietyRequestDto == null)
            return null;
        List<String> docTypes = Arrays.asList(
                DUS_DOCUMENT_TESTING.name(),
                MAIN_PHOTO_PLANT_VEGETARIAN.name(),
                VEGETARIAN_DOCUMENT_PERVIOUS_TEST.name()
        );

        List<DocumentWithTypeDto> documentsList = documentsService.getApplicationDocsByApplicationIdAndTypes(appId, docTypes);
        Map<String, List<DocumentWithTypeDto>> documentsMap = documentsList.stream().collect(Collectors.groupingBy(document -> document.getDocumentType().getCode()));
        plantVarietyRequestDto.setDocumentDtoMap(documentsMap);
        plantVarietyRequestDto.setAgentInfo(getAgentCustomerInfoIfCreatorIsAgent(appId, plantVarietyRequestDto.getCreatedByCustomerType()));
        plantVarietyRequestDto.setAuthorizationDocument(getAuthorizationDocument(appId,"POA"));
        getAssigneeDetails(appId, plantVarietyRequestDto);
        return plantVarietyRequestDto;

    }

    @Transactional(readOnly = true)
    public void getAssigneeDetails(Long appId, PlantVarietyRequestDto plantVarietyRequestDto) {
        RequestTasksDto requestTaskDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.PLANT_VARIETIES, appId).getPayload();
        plantVarietyRequestDto.setRequestTasksDto(requestTaskDto);
        //plantVarietyRequestDto.setUserLightDto(userManageClient.getUserDetails(requestTaskDto.getAssignee()).getPayload());
    }

    private DocumentDto getAuthorizationDocument(Long applicationId,String docType){
        return documentsService.findDocumentByApplicationIdAndDocumentType(applicationId,docType);

    }
    private CustomerSampleInfoDto getAgentCustomerInfoIfCreatorIsAgent(Long applicationId, ApplicationCustomerType plantVarietyCreatorCustomerType) {
        CustomerSampleInfoDto agentInfo= null;
        if(ApplicationCustomerType.AGENT.equals(plantVarietyCreatorCustomerType))
            agentInfo = applicationCustomerService.getAppCustomerInfoByAppIdAndType(applicationId, ApplicationCustomerType.AGENT);
        if(ApplicationCustomerType.MAIN_OWNER.equals(plantVarietyCreatorCustomerType))
            agentInfo = applicationCustomerService.getAppCustomerInfoByAppIdAndType(applicationId, ApplicationCustomerType.MAIN_OWNER);
        return agentInfo;
    }

    @Override
    public String getApplicationNumberWithUniqueSequence(LocalDateTime paymentDate, String serviceCode, ApplicationInfo applicationInfo) {
        Long applicationsCount = getFilingApplicationsCountInCurrentYearByCategory(paymentDate, applicationInfo);
        String sequenceNumber = Utilities.getPadded4Number(applicationsCount + 1);
        String year = String.valueOf(paymentDate.getYear());
        return new StringBuilder().append("SA-3").append(year).append(sequenceNumber).toString();
    }

    @Override
    public Long getPlantVarietyId(Long appId) {
        Optional<PlantVarietyDetails> plantVarietyByApplicationId = plantVarietyRepository.getPlantVarietyByApplicationId(appId);
        if (plantVarietyByApplicationId.isPresent())// Check if the optional contains a value
            return plantVarietyByApplicationId.get().getId();// If it does, return the plant variety id
        else
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, null);// If not, handle the case where no plant variety is found for the given appId
    }

    @Override
    public List <PlantProveExcellenceVariablesDto> getProveExcellenceVariablesWithApplication(Long appId) {
        return plantVarietyRepository.getProveExcellenceVariablesWithApplication(appId);
    }

    private void updateApplicationInfoNames(TechnicalSurveyPlantVarietyDto technicalSurveyPlantVarietyDto) {
        Optional<ApplicationInfo> applicationInfoOptional = getBaseApplicationInfoRepository().findById(technicalSurveyPlantVarietyDto.getApplication().getId());
        if (applicationInfoOptional.isEmpty())
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        ApplicationInfo updatedApplication = applicationInfoOptional.get();
        updatedApplication.setTitleAr(technicalSurveyPlantVarietyDto.getPlantVarietyNameAr());
        updatedApplication.setTitleEn(technicalSurveyPlantVarietyDto.getPlantVarietyNameEN());
        getBaseApplicationInfoRepository().save(updatedApplication);
    }

    private <T extends PlantVarietyDto> PlantVarietyDetails getPlantVariety(T dto) {
        Optional<PlantVarietyDetails> plantVariety = Optional.empty();

        if (dto.getId() != null) {
            plantVariety = plantVarietyRepository.findById(dto.getId());
        } else if (dto.getApplication().getId() != null) {
            plantVariety = plantVarietyRepository.getPlantVarietyByApplicationId(dto.getApplication().getId());
        }

        return plantVariety.orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Long addPlantDetailsPropertiesOptions(PlantDetailsPropertiesOptionsDto plantDetailsPropertiesOptionsDto) {
        Long plantVarietyId = getPlantVarietyId(plantDetailsPropertiesOptionsDto.getApplication());
        Optional<PlantVarietyDetails> plantVarietyDetails = plantVarietyRepository.findById(plantVarietyId);
        PlantDetailsPropertiesOptions newDetailsPropertiesOptionsList = createNewPropertiesOptionsList(plantDetailsPropertiesOptionsDto, plantVarietyDetails.get());
        plantDetailsPropertiesOptionsRepository.save(newDetailsPropertiesOptionsList);
        return newDetailsPropertiesOptionsList.getId();
    }


    private PlantDetailsPropertiesOptions createNewPropertiesOptionsList(PlantDetailsPropertiesOptionsDto dto, PlantVarietyDetails plantVarietyDetails) {
        LKPVPropertyOptions lkpvPropertyOptions = lkpvPropertyOptionsService.findById(dto.getPropertiesOptionsId());
        PlantDetailsPropertiesOptions newPropertiesOptions = new PlantDetailsPropertiesOptions();
        if (lkpvPropertyOptions != null){
            newPropertiesOptions.setPlantVarietyDetails(plantVarietyDetails);
            newPropertiesOptions.setLkpvProperty(lkpvPropertyOptions.getLKPVProperty());
            newPropertiesOptions.setLkpvPropertyOptions(lkpvPropertyOptions);
            newPropertiesOptions.setNote(lkpvPropertyOptions.getNote());
            newPropertiesOptions.setExample(lkpvPropertyOptions.getExample());
        }
        return newPropertiesOptions;
    }

    @Override
    @Transactional
    public Long updatePlantDetailsPropertiesOptions(PlantDetailsPropertiesOptionsDto plantDetailsPropertiesOptionsDto) {
        Optional<PlantDetailsPropertiesOptions> plantDetailsPropertiesOption = plantDetailsPropertiesOptionsRepository.findById(plantDetailsPropertiesOptionsDto.getId());
        LKPVPropertyOptions lkpvPropertyOptions = lkpvPropertyOptionsService.findById(plantDetailsPropertiesOptionsDto.getPropertiesOptionsId());
        if (lkpvPropertyOptions != null){
            plantDetailsPropertiesOption.get().setLkpvProperty(lkpvPropertyOptions.getLKPVProperty());
            plantDetailsPropertiesOption.get().setLkpvPropertyOptions(lkpvPropertyOptions);
            plantDetailsPropertiesOption.get().setNote(lkpvPropertyOptions.getNote());
            plantDetailsPropertiesOption.get().setExample(lkpvPropertyOptions.getExample());
        }
        plantDetailsPropertiesOptionsRepository.save(plantDetailsPropertiesOption.get());
        return plantDetailsPropertiesOption.get().getId();
    }

    @Override
    @Transactional
    public Long updatePlantVarietyDetails(PlantDetailsPropertiesOptionsDto dto) {
        Long plantVarietyId = getPlantVarietyId(dto.getApplication());
        Optional<PlantVarietyDetails> plantVarietyDetails = plantVarietyRepository.findById(plantVarietyId);
        if (dto.getProtectionOtherDiseases() != null) {
            plantVarietyDetails.get().setProtectionOtherDiseases(dto.getProtectionOtherDiseases());
        }
        if (dto.getOtherDescription() != null) {
            plantVarietyDetails.get().setOtherDescription(dto.getOtherDescription());
        }
        plantVarietyRepository.save(plantVarietyDetails.get());
        return plantVarietyDetails.get().getId();
    }

    @Override
    public PlantDetailsPropertiesOptionsDto findPropertiesAndOptionsByPlantVarietyDetailsId(Long applicationId) {
        Long plantVarietyDetailsId = getPlantVarietyId(applicationId);
        List<PlantDetailsPropertiesOptionsSelectionsDto> plantDetailsPropertiesOptionsSelectionsDtos = plantDetailsPropertiesOptionsRepository.findByPlantDetailsId(plantVarietyDetailsId);
        Optional<PlantVarietyDetails> plantVarietyDetails = plantVarietyRepository.findById(plantVarietyDetailsId);
        PlantDetailsPropertiesOptionsDto plantDetailsPropertiesOptionsDto = new PlantDetailsPropertiesOptionsDto();
        plantDetailsPropertiesOptionsDto.setSelectionsDtos(plantDetailsPropertiesOptionsSelectionsDtos);
        if (plantVarietyDetails.get().getProtectionOtherDiseases() != null){
            plantDetailsPropertiesOptionsDto.setProtectionOtherDiseases(plantVarietyDetails.get().getProtectionOtherDiseases());
        }
        if (plantVarietyDetails.get().getOtherDescription() != null){
            plantDetailsPropertiesOptionsDto.setOtherDescription(plantVarietyDetails.get().getOtherDescription());
        }
        return plantDetailsPropertiesOptionsDto;
    }

    @Override
    public PaginationDto<List<PlantVarietiesListDto>> listPlantVarietiesApplication(Integer page, Integer limit, String query, String status, Sort.Direction sortDirection) {
        Page<PlantVarietiesListDto> plantVarietiesList = getCustomerPlantVarietiesApplicationsPage(page, limit, query, status, sortDirection);
        if (plantVarietiesList == null || plantVarietiesList.isEmpty()) {
            return PaginationDto.<List<PlantVarietiesListDto>>builder()
                    .totalElements(0L)
                    .content(List.of())
                    .totalPages(0)
                    .build();
        }
        return PaginationDto.<List<PlantVarietiesListDto>>builder()
                .content(plantVarietiesList.getContent())
                .totalElements(plantVarietiesList.getTotalElements())
                .totalPages(plantVarietiesList.getTotalPages())
                .build();
    }
    private Page<PlantVarietiesListDto> getCustomerPlantVarietiesApplicationsPage(Integer page, Integer limit, String query, String status, Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "createdDate"));
        if (Strings.isNullOrEmpty(status))
            status = null;
        return plantVarietyRepository.getPaginatedPlantVarietiesApplicationsFiltering(query, status, Utilities.getCustomerCodeFromHeaders(), Utilities.getCustomerIdFromHeadersAsLong(), pageable);
    }

    @Override
    public void deleteByAppId(Long appId) {
      plantVarietyRepository.deleteByAppId(appId);
    }

    @Override
    public Long findVegetarianTypeIdByPlantDetailsId(Long applicationId) {
        Long plantVarietyId = getPlantVarietyId(applicationId);
        return plantVarietyRepository.findVegetarianTypeIdByPlantDetailsId(plantVarietyId);
    }
    @Override
    public PlantVarietyDetails findPlantVarietyDetailsById(Long plantDetailsId) {
        Optional<PlantVarietyDetails> plantVarietyDetails = plantVarietyRepository.findById(plantDetailsId);
        return plantVarietyDetails.get();
    }

    @Override
    public PriorityPlantVarietyDto getPriorityPlantVarietyDto(Long appId) {
        String typeName="A document related to the previous statement for vegetarian";
        DocumentDto documentByApplicationIdAndDocumentType = documentsService.findDocumentByApplicationIdAndDocumentType(appId, typeName);
        // Fetching data from different services
        List<DUSTestingDocumentListDto> dusTestingDocumentList = dusTestingDocumentPlantVarietyService.findApplicationDusTestDocumentsByApplicationId(appId);
        Optional<PlantVarietyDetails> plantVarietyDetailsOpt = plantVarietyRepository.getPlantVarietyByApplicationId(appId);
        if (!plantVarietyDetailsOpt.isPresent()) {
            throw new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        PlantVarietyDetails plantVarietyDetails = plantVarietyDetailsOpt.get();
        List<FillingRequestInOtherCountryDto> fillingRequestInOtherCountryList = fillingRequestInOtherCountryPlantVarietyService.findAllByPlantDetailsId(plantVarietyDetails.getId());
        List<PriorityDataSectionPlantVarietyDto> priorityDataSectionList = this.getPriorityDataSectionWithApplication(appId);

        // Mapping to DTO
        PriorityPlantVarietyDto priorityPlantVarietyDto = new PriorityPlantVarietyDto();
        priorityPlantVarietyDto.setId(plantVarietyDetails.getId());
        priorityPlantVarietyDto.setVegetarianDocumentPerviousTest(documentByApplicationIdAndDocumentType);
        priorityPlantVarietyDto.setDusTestingDocumentListDto(dusTestingDocumentList);
        priorityPlantVarietyDto.setFillingRequestInOtherCountryDto(fillingRequestInOtherCountryList);
        priorityPlantVarietyDto.setPriorityDataSectionPlantVarietyDto(priorityDataSectionList);

        return priorityPlantVarietyDto;
    }
    @Transactional
    @Override
    public void updateApplicationStatusToStartFormalProcess(Long applicationId) {
        super.updateApplicationStatusByIdAndStatusCode(applicationId, ApplicationStatusEnum.UNDER_FORMAL_PROCESS.name(),
                getApplicationCategoryEnum().getSaipCode());
    }
    @Transactional
    @Override
    public void updateStatusAndSetFirstAssignationDateByApplicationId(Long applicationId,ApplicationStatusEnum code) {
        applicationInfoService.changeApplicationStatusId(applicationId, code.name());
        plantVarietyRepository.updateFirstAssignationDateByApplicationId(applicationId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String reportName, DocumentTypeEnum documentType) throws IOException {
        return industrialAndIntegratedCircuitReportGenerationHelper.generateJasperPdf(dto, reportName, IPRS_PLANT_VARIETIES, documentType);
    }

    @Override
    @Transactional
    public Long applicantOppositionForCorrectionInvitations(ApplicationNotesReqDto applicationNotesReqDto) {
        RequestTasksDto requestTasksDto = bpmCallerFeignClient.getTaskByRowIdAndType(RequestTypeEnum.PLANT_VARIETIES, applicationNotesReqDto.getApplicationId()).getPayload();
        ApplicationNotesReqDto savedApplicationNotesReqDto = new ApplicationNotesReqDto();
        savedApplicationNotesReqDto.setApplicationId(applicationNotesReqDto.getApplicationId());
        savedApplicationNotesReqDto.setDescription(applicationNotesReqDto.getDescription());
        savedApplicationNotesReqDto.setTaskDefinitionKey(requestTasksDto.getTaskDefinitionKey());
        savedApplicationNotesReqDto.setSectionCode("PV_APPLICANT_OPPOSITION");
        Long savedApplicationNotesId = applicationNotesService.addOrUpdateAppNotes(savedApplicationNotesReqDto);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(new HashMap<>());
        bpmCallerFeignClient.completeUserTask(requestTasksDto.getTaskId(), completeTaskRequestDto);
        applicationInfoService.changeApplicationStatusId(applicationNotesReqDto.getApplicationId(), "REPLY_TO_FORMAL_PROCESS_WITH_OPPOSITION");
        return savedApplicationNotesId;
    }

    @Override
    public String getPvLastApplicantOppositionForInvitationCorrection(Long appId, String taskKey) {
        return applicationNotesService.getPvLastApplicantOppositionForInvitationCorrection(appId,taskKey);
    }
}
