package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.bpm.CompleteTaskRequestDto;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.DocumentTypes;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import gov.saip.applicationservice.common.enums.RequestTypeEnum;
import gov.saip.applicationservice.common.mapper.ApplicationRelvantMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevant;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.common.repository.ApplicationRelevantRepository;
import gov.saip.applicationservice.common.repository.ApplicationRelevantTypeRepository;
import gov.saip.applicationservice.common.service.ApplicationRelevantTypeService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.validators.ApplicationRelevantTypeValidator;
import gov.saip.applicationservice.common.validators.CustomerCodeValidator;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.ic.dto.ApplicationApplicantDto;
import gov.saip.applicationservice.modules.ic.dto.InventorDto;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_MAIN;
import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_SECONDARY;


@Service
@Slf4j
@Setter
@RequiredArgsConstructor
public class ApplicationRelevantTypeServiceImpl extends BaseServiceImpl<ApplicationRelevantType, Long> implements ApplicationRelevantTypeService {

    private final ApplicationRelevantTypeRepository applicationRelevantTypeRepository;
    private final BPMCallerServiceImpl bpmCallerService;

    private final ApplicationRelevantRepository applicationRelevantRepository;
    private final ApplicationRelvantMapper applicationRelvantMapper;
    private final CustomerCodeValidator customerCodeValidator;
    private final CustomerServiceCaller customerServiceCaller;
    private final DocumentsService documentsService;
    private final ApplicationRelevantTypeValidator applicationRelevantTypeValidator;

    @Override
    protected BaseRepository<ApplicationRelevantType, Long> getRepository() {
        return applicationRelevantTypeRepository;
    }

    @Override
    @Transactional
    public void updateDocument(Long id, Long documentId) {
        ApplicationRelevantType applicationRelevantType = applicationRelevantTypeRepository.findById(id).get();
        applicationRelevantType.setWaiverDocumentId(new Document(documentId));
        applicationRelevantTypeRepository.save(applicationRelevantType);

        // check if complete
        ApplicationInfo applicationInfo = applicationRelevantType.getApplicationInfo();
        if (!hasApplicationRelavantTypeWithOutWaiverDocument(applicationInfo.getId())) {
            RequestTasksDto requestTasksDto = bpmCallerService.getTaskByRowIdAndTypeIfExists(RequestTypeEnum.valueOf(applicationInfo.getCategory().getSaipCode()), applicationInfo.getId());
            completeTask(requestTasksDto, "UPDATE");
        }
    }

    @Override
    public Boolean hasApplicationRelavantTypeWithOutWaiverDocument(Long appId) {
        return applicationRelevantTypeRepository.hasApplicationRelavantTypeWithOutWaiverDocument(appId);
    }

    private void completeTask(RequestTasksDto requestTasksDto, String approvedValue) {
        CompleteTaskRequestDto completeTaskRequestDto = buildCompleteTaskRequestDto(approvedValue);
        bpmCallerService.completeTaskToUser(requestTasksDto.getTaskId(), completeTaskRequestDto);
    }

    private static CompleteTaskRequestDto buildCompleteTaskRequestDto(String approvedValue) {
        Map<String, Object> variables = buildTaskVariableMap(approvedValue);
        CompleteTaskRequestDto completeTaskRequestDto = new CompleteTaskRequestDto();
        completeTaskRequestDto.setVariables(variables);
        return completeTaskRequestDto;
    }

    private static Map<String, Object> buildTaskVariableMap(String approvedValue) {
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> completedPetitionRecovery = new HashMap<>();
        completedPetitionRecovery.put("value", approvedValue);
        variables.put("approved", completedPetitionRecovery);
        return variables;
    }


    @Override
    public ApplicationInfo saveApplicationRelevantAndRelevantTypes(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo dbApplicationInfo) {
        ApplicationRelevantType applicationRelevantTypeInfo = new ApplicationRelevantType();
        applicationRelevantTypeInfo.setApplicationInfo(dbApplicationInfo);
        applicationRelevantTypeInfo.setType(Applicant_MAIN);
        applicationRelevantTypeInfo.setCustomerCode(applicantsRequestDto.getCustomerCode());
        applicationRelevantTypeRepository.save(applicationRelevantTypeInfo);

        List<ApplicationRelevantRequestsDto> relevants = applicantsRequestDto.getRelevants();
        if (relevants == null || relevants.isEmpty()) {
            return dbApplicationInfo;
        }

        List<String> customersCodes = new LinkedList<>();
        for (ApplicationRelevantRequestsDto request : relevants) {
            // get relvant by identifier ttype and value
            ApplicationRelevantType applicationRelevantTypePP = new ApplicationRelevantType();
            applicationRelevantTypePP.setType(Applicant_SECONDARY);
            applicationRelevantTypePP.setApplicationInfo(dbApplicationInfo);
            if (request.getIdentifierType().equals(IdentifierTypeEnum.CUSTOMER_CODE)) {
                //check if this exist
                applicationRelevantTypePP.setCustomerCode(request.getIdentifier());
                customersCodes.add(request.getIdentifier());
                applicationRelevantTypeRepository.save(applicationRelevantTypePP);
                continue;
            }
            ApplicationRelevant applicationRelevant = applicationRelvantMapper.mapRequestToEntity(request);
            applicationRelevantTypePP.setApplicationRelevant(applicationRelevantRepository.save(applicationRelevant));
            applicationRelevantTypeRepository.save(applicationRelevantTypePP);
        }

        customerCodeValidator.customerCodeValidator(customersCodes);
        return dbApplicationInfo;
    }


    @Override
    public ApplicationInfo updateApplicationRelevantAndRelevantTypes(ApplicantsRequestDto applicantsRequestDto, List<String> customersCodes, ApplicationInfo applicationInfoExist) {
        List<ApplicationRelevantRequestsDto> relevants = applicantsRequestDto.getRelevants();
        if (relevants == null || relevants.isEmpty()) {
            return applicationInfoExist;
        }
        for (ApplicationRelevantRequestsDto request : relevants) {
            if (request.getAppRelvantTypeId() != null) continue;

            ApplicationRelevantType applicationRelevantTypePP = new ApplicationRelevantType();
            applicationRelevantTypePP.setType(Applicant_SECONDARY);
            applicationRelevantTypePP.setApplicationInfo(applicationInfoExist);
            if (request.getIdentifierType().equals(IdentifierTypeEnum.CUSTOMER_CODE)) {
                applicationRelevantTypePP.setCustomerCode(request.getIdentifier());
                applicationRelevantTypeRepository.save(applicationRelevantTypePP);
                customersCodes.add(request.getIdentifier());
                continue;
            }

            ApplicationRelevant applicationRelevant = applicationRelvantMapper.mapRequestToEntity(request);
            applicationRelevantTypePP.setApplicationRelevant(applicationRelevantRepository.save(applicationRelevant));
            applicationRelevantTypeRepository.save(applicationRelevantTypePP);
        }
        customerCodeValidator.customerCodeValidator(customersCodes);

        return applicationInfoExist;
    }

    @Override
    @Transactional
    public Long addInventorPatch(InventorRequestsDto dto) {

        List<String> customersCodes = new LinkedList<>();
        List<ApplicationRelevantType> relevantTypes = applicationRelevantTypeRepository.getApplicationRelevantTypes(dto.getAppInfoId());
        setApplicantsInventors(dto.getFromApplicants(), relevantTypes);
        removeInventors(dto);
        if (Objects.isNull(dto.getRelvants()) || dto.getRelvants().isEmpty())
            return dto.getAppInfoId();
        for (ApplicationRelevantRequestsDto request : dto.getRelvants()) {
            if (request.getAppRelvantTypeId() != null) {
                continue;
            }
            ApplicationRelevantType applicationRelevantTypePP = new ApplicationRelevantType();
            applicationRelevantTypePP.setType(ApplicationRelevantEnum.INVENTOR);
            applicationRelevantTypePP.setApplicationInfo(new ApplicationInfo(dto.getAppInfoId()));
            applicationRelevantTypePP.setInventor(true);
            if (request.getWaiverDocumentId() != null) {
                applicationRelevantTypePP.setWaiverDocumentId(new Document(request.getWaiverDocumentId()));
            }

            if (request.getIdentifierType() != null && request.getIdentifierType().equals(IdentifierTypeEnum.CUSTOMER_CODE)) {
                applicationRelevantTypePP.setCustomerCode(request.getIdentifier());
                customersCodes.add(request.getIdentifier());
                applicationRelevantTypeRepository.save(applicationRelevantTypePP);
                continue;
            }
            ApplicationRelevant applicationRelevant = applicationRelvantMapper.mapRequestToEntity(request);
            ApplicationRelevant applicationRelevant1 = applicationRelevantRepository.save(applicationRelevant);
            applicationRelevantTypePP.setApplicationRelevant(applicationRelevant1);
            applicationRelevantTypeRepository.save(applicationRelevantTypePP);
        }
        customerCodeValidator.customerCodeValidator(customersCodes);
        return dto.getAppInfoId();
    }

    @Override
    public List<InventorDto> getAllInventorsExceptApplicantsByApplication(Long applicationId) {
        List<String> customerCodes;
        List<Long> countriesIds;
        List<Long> nationalCountriesIds;
        List<InventorDto> returnedInventors = new ArrayList<>();

        List<InventorDto> inventors = applicationRelevantTypeRepository.getInventorsInfoByAppId(applicationId);
        countriesIds = inventors.stream()
                .filter(inv -> Objects.nonNull(inv.getIdentifier()))
                .map(InventorDto::getCountryId).distinct().collect(Collectors.toList());
        nationalCountriesIds = inventors.stream()
                .filter(inv -> Objects.nonNull(inv.getIdentifier()))
                .map(InventorDto::getNationalCountryId).distinct().collect(Collectors.toList());
        customerCodes = inventors.stream()
                .filter(applicant -> Objects.nonNull(applicant.getCustomerCode()))
                .map(applicant -> applicant.getCustomerCode().toLowerCase()).collect(Collectors.toList());

        Map<Long, CountryDto> countriesSampleInfoDtoMap = getInventorCountriesInfo(countriesIds);
        Map<Long, CountryDto> nationalCountriesSampleInfoDtoMap = getInventorCountriesInfo(nationalCountriesIds);
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = getApplicantWithCustomerCodesInfo(customerCodes);
        CustomerSampleInfoDto customerSampleInfoDto;
        for (InventorDto inventor : inventors) {
            if (Objects.nonNull(inventor.getIdentifier())) {
                inventor.setNationalCountry(nationalCountriesSampleInfoDtoMap.get(inventor.getNationalCountryId()));
                inventor.setAddress(new AddressResponseDto(countriesSampleInfoDtoMap.get(inventor.getCountryId()), inventor.getFullAddress(),
                        inventor.getFullAddress(), inventor.getCity(), inventor.getPostalCode()));
                if (inventor.getDocumentId() != null)
                    inventor.setDocumentDto(documentsService.findDocumentById(inventor.getDocumentId()));
                returnedInventors.add(inventor);
            } else if (Objects.nonNull(inventor.getCustomerCode())) {
                customerSampleInfoDto = customerSampleInfoDtoMap.get(inventor.getCustomerCode().toLowerCase());
                setApplicantInformation(customerSampleInfoDto, inventor, Boolean.TRUE);
                returnedInventors.add(inventor);
            }
        }

        return returnedInventors;
    }

    @Override
    public List<InventorDto> getApplicantsByApplication(Long applicationId, Boolean inventor) {
        List<String> customerCodes;
        List<Long> countriesIds;
        List<Long> nationalCountriesIds;
        List<InventorDto> returnedApplicants = new ArrayList<>();

        List<InventorDto> applicants = applicationRelevantTypeRepository.getApplicantsIfInventorsByApplicationId(applicationId, inventor);
        countriesIds = applicants.stream()
                .filter(inv -> Objects.nonNull(inv.getIdentifier()))
                .map(InventorDto::getCountryId).distinct().collect(Collectors.toList());
        nationalCountriesIds = applicants.stream()
                .filter(inv -> Objects.nonNull(inv.getIdentifier()))
                .map(InventorDto::getNationalCountryId).distinct().collect(Collectors.toList());
        customerCodes = applicants.stream()
                .filter(applicant -> Objects.nonNull(applicant.getCustomerCode()))
                .map(applicant -> applicant.getCustomerCode().toLowerCase()).collect(Collectors.toList());

        Map<Long, CountryDto> countriesSampleInfoDtoMap = getInventorCountriesInfo(countriesIds);
        Map<Long, CountryDto> nationalCountriesSampleInfoDtoMap = getInventorCountriesInfo(nationalCountriesIds);
        Map<String, CustomerSampleInfoDto> customerSampleInfoDtoMap = getApplicantWithCustomerCodesInfo(customerCodes);

        CustomerSampleInfoDto customerSampleInfoDto;
        for (InventorDto applicant : applicants) {
            if (Objects.nonNull(applicant.getIdentifier())) {
                applicant.setNationalCountry(nationalCountriesSampleInfoDtoMap.get(applicant.getNationalCountryId()));
                applicant.setAddress(new AddressResponseDto(countriesSampleInfoDtoMap.get(applicant.getCountryId()), applicant.getFullAddress(),
                        applicant.getFullAddress(), applicant.getCity(), applicant.getPostalCode()));
                returnedApplicants.add(applicant);
            } else if (Objects.nonNull(applicant.getCustomerCode())) {
                customerSampleInfoDto = customerSampleInfoDtoMap.get(applicant.getCustomerCode().toLowerCase());
                setApplicantInformation(customerSampleInfoDto, applicant, Boolean.FALSE);
                returnedApplicants.add(applicant);
            }
        }

        return returnedApplicants;
    }

    private void setApplicantInformation(CustomerSampleInfoDto customerSampleInfoDto, InventorDto applicant, Boolean isInventor) {
        applicant.setCustomerId(customerSampleInfoDto.getId());
        applicant.setUserGroupAr(customerSampleInfoDto.getUserGroupAr());
        applicant.setUserGroupEn(customerSampleInfoDto.getUserGroupEn());
        applicant.setEmail(customerSampleInfoDto.getEmail());
        applicant.setMobile(customerSampleInfoDto.getMobile());
        applicant.setIdentifierTypeEnum(IdentifierTypeEnum.CUSTOMER_CODE);
        applicant.setIdentifier(customerSampleInfoDto.getCode());
        applicant.setApplicantIdentifier(customerSampleInfoDto.getIdentifier());
        applicant.setUserGroupCode(customerSampleInfoDto.getUserGroupCode());
        applicant.setAddress(customerSampleInfoDto.getAddress());
        applicant.setNationalCountry(getCountryObject(customerSampleInfoDto));
        applicant.setCountryId(customerSampleInfoDto.getAddress() == null || customerSampleInfoDto.getAddress().getCountryObject() == null ?
                null : customerSampleInfoDto.getAddress().getCountryObject().getId());
        applicant.setNationalCountryId(customerSampleInfoDto.getNationality() == null ? null : customerSampleInfoDto.getNationality().getId());
        applicant.setNameAr(customerSampleInfoDto.getNameAr());
        applicant.setNameEn(customerSampleInfoDto.getNameEn());
        applicant.setGender(customerSampleInfoDto.getGender());
        if (isInventor.equals(Boolean.TRUE) && applicant.getDocumentId() != null)
            applicant.setDocumentDto(documentsService.findDocumentById(applicant.getDocumentId()));
    }

    @Transactional
    @Override
    public Long softDeleteById(Long id) {
       applicationRelevantTypeRepository.softDeleteAppById(id);
       return id;
    }

    @Override
    public Long updateApplicantInventors(ApplicantInventorDto applicantInventorDto) {
        List<ApplicationRelevantType> relevantTypes = applicationRelevantTypeRepository.getApplicationRelevantTypes(applicantInventorDto.getAppInfoId());
        checkAndUpdateICApplicantInventorInfo(applicantInventorDto.getApplicants(), relevantTypes, applicantInventorDto.getIsApplicantInventor());
        return applicantInventorDto.getAppInfoId();
    }

    @Override
    public Long addInventor(ApplicationRelevantRequestsDto dto) {
        return addApplicationRelevantTypes(dto, ApplicationRelevantEnum.INVENTOR);
    }

    @Override
    public Long addSecondaryApplicant(ApplicationRelevantRequestsDto dto) {
        return addApplicationRelevantTypes(dto, Applicant_SECONDARY);
    }

    private Long addApplicationRelevantTypes(ApplicationRelevantRequestsDto dto, ApplicationRelevantEnum relevantEnum) {
        List<String> customersCodes = new LinkedList<>();
        List<ApplicationRelevantType> applicantsToBeUpdated = new ArrayList<>();
        Long returnedAppRelevantId;
        applicationRelevantTypeValidator.validate(dto, null);
        ApplicationRelevantType applicationRelevantType = new ApplicationRelevantType();
        applicationRelevantType.setType(relevantEnum);
        if (relevantEnum.equals(ApplicationRelevantEnum.INVENTOR)) {
            applicationRelevantType.setInventor(true);
            if (dto.getWaiverDocumentId() != null) {
                applicationRelevantType.setWaiverDocumentId(new Document(dto.getWaiverDocumentId()));
            }
        }
        applicationRelevantType.setApplicationInfo(new ApplicationInfo(dto.getAppInfoId()));
        List<ApplicationRelevantType> mainSecondaryApplicants = applicationRelevantTypeRepository.getApplicationRelevantTypesMainAndSecondaryApplicantInventors(dto.getAppInfoId());
        if (!mainSecondaryApplicants.isEmpty()) {
            for (ApplicationRelevantType relevantType : mainSecondaryApplicants) {
                relevantType.setInventor(false);
                applicantsToBeUpdated.add(relevantType);
            }
        }
        if (!applicantsToBeUpdated.isEmpty())
            applicationRelevantTypeRepository.saveAll(applicantsToBeUpdated);

        if (dto.getIdentifierType() != null && dto.getIdentifierType().equals(IdentifierTypeEnum.CUSTOMER_CODE)) {
            applicationRelevantType.setCustomerCode(dto.getIdentifier());
            customersCodes.add(dto.getIdentifier());
            returnedAppRelevantId = applicationRelevantTypeRepository.save(applicationRelevantType).getId();
            return returnedAppRelevantId;
        }
        ApplicationRelevant mappedApplicationRelevant = applicationRelvantMapper.mapRequestToEntity(dto);
        applicationRelevantType.setApplicationRelevant(applicationRelevantRepository.save(mappedApplicationRelevant));
        returnedAppRelevantId = applicationRelevantTypeRepository.save(applicationRelevantType).getId();

        customerCodeValidator.customerCodeValidator(customersCodes);
        return returnedAppRelevantId;
    }

    private void setApplicantsInventors(Set<Long> applicants, List<ApplicationRelevantType> applicationRelevantTypes) {
        List<Long> applicantsTobeDeleted = new ArrayList<>();
        List<ApplicationRelevantType> applicantsToBeUpdated = new ArrayList<>();
        for (ApplicationRelevantType applicationRelevantType : applicationRelevantTypes) {
            if (applicants != null && applicants.contains(applicationRelevantType.getId())) {
                applicationRelevantType.setInventor(true);
                applicantsToBeUpdated.add(applicationRelevantType);
            } else {
                boolean isMainApplicantOrSecondary = applicationRelevantType.getType().toString().equals(Applicant_MAIN.toString())
                        || applicationRelevantType.getType().toString().equals(Applicant_SECONDARY.toString());
                if (isMainApplicantOrSecondary && applicationRelevantType.isInventor()) {
                    applicationRelevantType.setInventor(false);
                    applicantsTobeDeleted.add(applicationRelevantType.getId());
                    applicantsToBeUpdated.add(applicationRelevantType);
                }
            }
        }
        applicationRelevantTypeRepository.saveAll(applicantsToBeUpdated);
        if (!applicantsTobeDeleted.isEmpty()) {
            applicationRelevantTypeRepository.deleteByInventorIsFalse(applicantsTobeDeleted);
        }
    }

    private void checkAndUpdateICApplicantInventorInfo(Set<Long> applicants, List<ApplicationRelevantType> applicationRelevantTypes, Boolean isApplicantInventor) {
        List<ApplicationRelevantType> applicantsToBeUpdated = new ArrayList<>();
        if (isApplicantInventor.equals(Boolean.TRUE) && applicants.isEmpty())
            throw new BusinessException(Constants.ErrorKeys.NULL_APPLICANT_INVENTOR, HttpStatus.BAD_REQUEST);
        for (ApplicationRelevantType applicationRelevantType : applicationRelevantTypes) {
            if (applicants.contains(applicationRelevantType.getId())) {
                applicationRelevantType.setInventor(true);
                applicantsToBeUpdated.add(applicationRelevantType);
            } else {
                boolean isMainApplicantOrSecondary = applicationRelevantType.getType().toString().equals(Applicant_MAIN.toString())
                        || applicationRelevantType.getType().toString().equals(Applicant_SECONDARY.toString());
                if (isMainApplicantOrSecondary && applicationRelevantType.isInventor()) {
                    applicationRelevantType.setInventor(false);
                    applicantsToBeUpdated.add(applicationRelevantType);
                }
            }
        }
        applicationRelevantTypeRepository.saveAll(applicantsToBeUpdated);
    }

    private void removeInventors(InventorRequestsDto dto) {
        if (Objects.nonNull(dto.getInventorsToBeDeleted()) && !dto.getInventorsToBeDeleted().isEmpty()) {
            for (Long inventorId : dto.getInventorsToBeDeleted()) {
                Optional<ApplicationRelevantType> applicationRelevantType = applicationRelevantTypeRepository.findById(inventorId);
                if (applicationRelevantType.isPresent()) {
                    ApplicationRelevantType applicationRelevantType1 = applicationRelevantType.get();
                    applicationRelevantType1.setIsDeleted(1);
                    applicationRelevantTypeRepository.save(applicationRelevantType1);
                }
            }
        }
    }

    private Map<String, CustomerSampleInfoDto> getApplicantWithCustomerCodesInfo(List<String> customerCodes) {
        CustomerCodeListDto customerCodeListDto = new CustomerCodeListDto();
        customerCodeListDto.setCustomerCode(customerCodes);
        return customerServiceCaller.getCustomerMapByListOfCode(customerCodeListDto);
    }

    private Map<Long, CountryDto> getInventorCountriesInfo(List<Long> countriesInfo) {
        return customerServiceCaller.getCountriesMapByListOfCode(countriesInfo);
    }

    private static CountryDto getCountryObject(CustomerSampleInfoDto agentsDto) {
        if (agentsDto.getNationality() != null) {
            return agentsDto.getNationality();
        }

        if (agentsDto.getAddress() != null) {
            return agentsDto.getAddress().getCountryObject();
        }

        return null;
    }

    @Override
    public ApplicationApplicantDto getMainApplicantInfoByApplicationId(Long applicationId) {
        ApplicationApplicantDto applicationApplicantDto = applicationRelevantTypeRepository.getMainApplicantInfoByApplicationId(applicationId);
        if (applicationApplicantDto != null) {
            if (!applicationApplicantDto.isByHimself())
                applicationApplicantDto.setDocumentDto(documentsService.findDocumentByApplicationIdAndDocumentType(applicationId, DocumentTypes.POA.name()));

            String customerCode= applicationApplicantDto.getCustomerCode();
            CustomerSampleInfoDto customerSampleInfoDto = customerServiceCaller.getCustomerInfoByCustomerCode(customerCode);


            applicationApplicantDto.setCustomerId(customerSampleInfoDto.getId());
            applicationApplicantDto.setUserGroupAr(customerSampleInfoDto.getUserGroupAr());
            applicationApplicantDto.setUserGroupEn(customerSampleInfoDto.getUserGroupEn());
            applicationApplicantDto.setEmail(customerSampleInfoDto.getEmail());
            applicationApplicantDto.setMobile(customerSampleInfoDto.getMobile());
            applicationApplicantDto.setIdentifierTypeEnum(IdentifierTypeEnum.CUSTOMER_CODE);
            applicationApplicantDto.setIdentifier(customerSampleInfoDto.getCode());
            applicationApplicantDto.setApplicantIdentifier(customerSampleInfoDto.getIdentifier());
            applicationApplicantDto.setUserGroupCode(customerSampleInfoDto.getUserGroupCode());
            applicationApplicantDto.setAddress(customerSampleInfoDto.getAddress());
            applicationApplicantDto.setCountryDto(getCountryObject(customerSampleInfoDto));
            applicationApplicantDto.setCustomerCode(customerCode);
            applicationApplicantDto.setNameAr(customerSampleInfoDto.getNameAr());
            applicationApplicantDto.setNameEn(customerSampleInfoDto.getNameEn());
            applicationApplicantDto.setAddress(customerSampleInfoDto.getAddress());
            applicationApplicantDto.setCustomerTypeAr(customerSampleInfoDto.getTypeAr());
            applicationApplicantDto.setCustomerTypeEn(customerSampleInfoDto.getTypeEn());
            applicationApplicantDto.setCustomerTypeCode(customerSampleInfoDto.getUserGroupCode().getValue());
            applicationApplicantDto.setCustomerStatus(customerSampleInfoDto.getCustomerStatus());
            applicationApplicantDto.setGender(customerSampleInfoDto.getGender());
        }
        return applicationApplicantDto;
    }


    @Override
    @Transactional
    public void updateCustomerCodeForMainApplicationRelevant(String customerCode, Long applicationId){
        applicationRelevantTypeRepository.updateCustomerCodeForMainApplicationRelevant(customerCode, applicationId);
    }

}
