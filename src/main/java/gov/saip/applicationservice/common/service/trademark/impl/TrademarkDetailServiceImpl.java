package gov.saip.applicationservice.common.service.trademark.impl;


import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.BPMCallerFeignClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.Suspcion.ApplicantsSuspcionRequestDto;
import gov.saip.applicationservice.common.dto.publication.TrademarkGazettePublicationListDto;
import gov.saip.applicationservice.common.dto.trademark.*;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.mapper.ClassificationMapper;
import gov.saip.applicationservice.common.mapper.SubClassificationMapper;
import gov.saip.applicationservice.common.mapper.trademark.TrademarkDetailMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.ApplicationPublication;
import gov.saip.applicationservice.common.model.ApplicationSubClassification;
import gov.saip.applicationservice.common.model.trademark.LkTagTypeDesc;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.projection.TradeMarkInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationNiceClassificationRepository;
import gov.saip.applicationservice.common.repository.ApplicationSupportServicesTypeRepository;
import gov.saip.applicationservice.common.repository.trademark.TrademarkDetailRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.agency.SupportServiceCustomerService;
import gov.saip.applicationservice.common.service.impl.ApplicationAgentFacadeService;
import gov.saip.applicationservice.common.service.impl.BPMCallerServiceImpl;
import gov.saip.applicationservice.common.service.patent.ApplicationCertificateDocumentService;
import gov.saip.applicationservice.common.service.trademark.LkMarkTypeService;
import gov.saip.applicationservice.common.service.trademark.LkTagLanguageService;
import gov.saip.applicationservice.common.service.trademark.LkTagTypeDescService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.common.service.veena.ApplicationVeenaClassificationService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Util;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.WAIVED;
import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_TRADEMARK;
import static gov.saip.applicationservice.common.enums.PublicationType.TRADEMARK_REGISTERATION;
import static gov.saip.applicationservice.common.enums.RequestTypeConfigEnum.PUBLICATION_DURATION_TM;
import static gov.saip.applicationservice.common.enums.TrademarkType.VOICE;

/**
 * Implementation of the {@link TrademarkDetailService} interface.
 * <p>
 * This class provides methods for managing trademark detail data.
 * The trademark detail data is represented by instances of the {@link TrademarkDetail} and {@link TrademarkDetailDto} classes.
 *
 * <p>The implementation uses several mapper and service classes to retrieve, map, and manipulate the data.
 * These classes are injected into the implementation using the {@link RequiredArgsConstructor} annotation.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * TrademarkDetailService trademarkDetailService = new TrademarkDetailServiceImpl();
 * Long id = trademarkDetailService.create(req, applicationId);
 * TrademarkDetailDto dto = trademarkDetailService.findDtoById(id);
 * }</pre>
 *
 * @see TrademarkDetailService
 * @see TrademarkDetail
 * @see TrademarkDetailDto
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TrademarkDetailServiceImpl extends BaseServiceImpl<TrademarkDetail, Long> implements TrademarkDetailService {

    private final TrademarkDetailMapper trademarkDetailMapper;
    private final TrademarkDetailRepository trademarkDetailRepository;
    private final LkMarkTypeService markTypeService;
    private final LkTagTypeDescService tagTypeDescService;
    private final LkTagLanguageService tagLanguageService;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationAgentFacadeService applicationAgentFacadeService;

    private final BPMCallerServiceImpl bpmCallerService;
    private final BPMCallerFeignClient bpmCallerFeignClient;
    private final DocumentsService documentsService;
    private final ApplicationInfoGenericService applicationInfoGenericService;
    private final ApplicationInfoRepository applicationInfoRepository;
    @Autowired
    @Lazy
    private   ApplicationCertificateDocumentService applicationCertificateDocumentService;
    private final ClassificationService classificationService;


    private static final List<String> EXCLUDED_DOCUMENT_TYPES_CODES = Arrays.asList("Prioirty Document", "Issue Certificate", "app_drawing", "APPLICATION_XML");


    private final ApplicationPublicationService applicationPublicationService;
    private final RevokeProductsService revokeProductsService;
    private final SubClassificationService subClassificationService;
    private final SubClassificationMapper subClassificationMapper;
    private final ApplicationPriorityService applicationPriorityService;
    private final ApplicationInfoMapper requestMapper;
    private final Util util;
    private final ApplicationNiceClassificationRepository applicationNiceClassificationRepository;
    private final ClassificationMapper classificationMapper;
    private final ApplicationVeenaClassificationService applicationVeenaClassificationService;
    private final SupportServiceCustomerService supportServiceCustomerService;
    private final ApplicationCheckingReportService reportService;

    private final CustomerServiceCaller customerServiceCaller;

    private final ApplicationSupportServicesTypeRepository supportServicesTypeRepository;


    @Override
    protected BaseRepository<TrademarkDetail, Long> getRepository() {
        return trademarkDetailRepository;
    }

    private final ApplicationCustomerService applicationCustomerService;
    /**
     * Creates a new trademark detail entity and saves it to the database.
     *
     * @param req           the {@link TrademarkDetailReqDto} containing the request data
     * @param applicationId the ID of the application associated with the detail
     * @return the ID of the newly created entity
     */
    public Long create(TrademarkDetailReqDto req, Long applicationId) {

        log.info("TrademarkDetailServiceImpl : create : started");
        log.info("TrademarkDetailServiceImpl : create : applicationId: {}, TrademarkDetailReqDto: {}", applicationId, req);

        TrademarkDetail entity = getAndMapDet(req, applicationId);
        log.info("TrademarkDetailServiceImpl : create : TrademarkDetailID is : {} ", entity.getId());
        prepareLookup(req, entity);
        insert(entity);
        log.info("TrademarkDetailServiceImpl : create : ended with id : {} ", entity.getId());
        return entity.getId();
    }

    public Integer update(TradeMarkLightDto request, Long applicationId) {
        return updateExaminerGrantCondition(request.getExaminerGrantCondition(), applicationId);
    }
    /**
     * update examinerGrantCondition field in trademark detail entity by application id .
     *
     * @param examinerGrantCondition the value to be updated
     * @param applicationId the ID of the application associated with the detail
     * @return the number of row effected.
     */
    private Integer updateExaminerGrantCondition(String examinerGrantCondition, Long applicationId) {
        return trademarkDetailRepository.updateExaminerGrantCondition(examinerGrantCondition, applicationId);
    }

    /**
     * Sets the lookup fields on a trademark detail entity.
     *
     * @param req    the {@link TrademarkDetailReqDto} containing the request data
     * @param entity the {@link TrademarkDetail} entity to update
     * @return the updated entity
     */
    private TrademarkDetail prepareLookup(TrademarkDetailReqDto req, TrademarkDetail entity) {
        log.info("TrademarkDetailServiceImpl : prepareLookup : started");
        log.info("TrademarkDetailServiceImpl : prepareLookup : TrademarkDetailReqDto is : {} ", req);
        entity.setMarkType(markTypeService.getReferenceById(req.getMarkTypeId()));
        entity.setTagTypeDesc(tagTypeDescService.getReferenceById(req.getTagTypeDescId()));
        if(req.getTagLanguageId() != null){
            entity.setTagLanguage(tagLanguageService.getReferenceById(req.getTagLanguageId()));
        }
        log.info("TrademarkDetailServiceImpl : prepareLookup : ended");
        return entity;
    }

    private TrademarkDetail getAndMapDet(TrademarkDetailReqDto req, Long applicationId) {
        log.info("TrademarkDetailServiceImpl : getAndMapDet : started");
        log.info("TrademarkDetailServiceImpl : getAndMapDet : applicationId is : {} ", applicationId);
        Optional<TrademarkDetail> det = trademarkDetailRepository.findByApplicationId(applicationId);
        TrademarkDetail entity = null;
        if (det.isPresent())
            entity = trademarkDetailMapper.mapDetReq(req, det.get());
        else {
            entity = trademarkDetailMapper.mapDetReq(req);
            entity.setApplicationId(applicationId);
        }

        return entity;
    }

    /**
     * Retrieves a trademark detail entity by application ID.
     *
     * @param applicationId the ID of the application to retrieve the detail for
     * @return the {@link TrademarkDetail} associated with the application ID
     * @throws BusinessException if the entity is not found
     */
    public TrademarkDetail  findByApplicationId(Long applicationId) {
        log.info("TrademarkDetailServiceImpl : findByApplicationId : started");
        log.info("TrademarkDetailServiceImpl : findByApplicationId : applicationId is : {} ", applicationId);
        String[] params = {applicationId.toString()};
        return trademarkDetailRepository.findByApplicationId(applicationId)
                .orElse(null);
    }
    public TrademarkDetail getByApplicationId(Long applicationId){
       log.info("TrademarkDetailServiceImpl : getByApplicationId : started");
       log.info("TrademarkDetailServiceImpl : getByApplicationId : applicationId is : {} ", applicationId);
       Optional <TrademarkDetail> trademarkDetail = trademarkDetailRepository.findByApplicationId(applicationId);
        if(!trademarkDetail.isPresent())
            return null;

        return trademarkDetail.get();
    }

    @Override
    public Long getTMByApplicationId(Long applicationId) {
         return  trademarkDetailRepository.findIdTMByApplicationId(applicationId);
    }


    private void getoldOwenerforTRADEMARK_REGISTERATIONAndTransformed(  Long applicationId ,    PublicationApplicationTrademarkDetailDto publicationApplicationTrademarkDetailDto ,  ApplicationPublication applicationPublication ){
            if(PublicationType.TRADEMARK_REGISTERATION.name().equalsIgnoreCase(applicationPublication.getPublicationType().getCode().toString())){
                List<Long> oldcustomerId =    supportServicesTypeRepository.getoldestowner(applicationId ,SupportServiceRequestStatusEnum.TRANSFERRED_OWNERSHIP.name() );
                if (oldcustomerId != null && oldcustomerId.size() > 0 ){
                    CustomerSampleInfoDto customerSampleInfoDto =   customerServiceCaller.getAnyCustomerDetails(oldcustomerId.get(0));
                    System.out.println(customerSampleInfoDto);
                    publicationApplicationTrademarkDetailDto.setApplicantNameAr(customerSampleInfoDto.getNameAr());
                    publicationApplicationTrademarkDetailDto.setApplicantNameEn(customerSampleInfoDto.getNameEn());
                    if( customerSampleInfoDto.getAddress() != null    ) {
                        publicationApplicationTrademarkDetailDto.setAddressAr(customerSampleInfoDto.getAddress().getPlaceOfResidenceAr());
                        publicationApplicationTrademarkDetailDto.setAddressEn(customerSampleInfoDto.getAddress().getPlaceOfResidenceEn());
                    }
                }
            }
    }

    /**
     * Retrieves a trademark detail DTO by application ID.
     *
     * @param applicationId the ID of the application to retrieve the detail for
     * @return the {@link TrademarkDetailDto} associated with the application ID
     * @throws BusinessException if the entity is not found
     */
    public TrademarkDetailDto findDtoByApplicationId(Long applicationId) {
        log.info("TrademarkDetailServiceImpl : findDtoByApplicationId : started");
        log.info("TrademarkDetailServiceImpl : findDtoByApplicationId : applicationId is : {} ", applicationId);
        TrademarkDetail entity = findByApplicationId(applicationId);
        if(Objects.nonNull(entity)) {

            TrademarkDetailDto trademarkDetailDto = trademarkDetailMapper.map(entity);
            List<DocumentDto> documents = documentsService.getDocumentsByApplicationId(entity.getApplicationId());
            trademarkDetailDto.setDocuments(documents);
            return trademarkDetailDto;
        }
        return null;
    }

    /**
     * Retrieves a trademark detail DTO by ID.
     *
     * @param id the ID of the detail to retrieve
     * @return the {@link TrademarkDetailDto} associated with the ID
     * @throws BusinessException if the entity is not found
     */
    public TrademarkDetailDto findDtoById(Long id) {
        log.info("TrademarkDetailServiceImpl : findDtoById : started");
        log.info("TrademarkDetailServiceImpl : findDtoById : id is : {} ", id);
        TrademarkDetail entity = findById(id);
        return trademarkDetailMapper.map(entity);
    }

    /**
     * Retrieves trademark applicant information for a given applicant code.
     *
     * @param applicantCode the applicant code to retrieve TradeMarkInfo
     * @return a list of {@link TradeMarkInfo} objects containing the application information
     */
    @Override
    public List<TradeMarkInfo> getTradeMarKApplicaionInfo(String applicantCode) {
        log.info("TrademarkDetailServiceImpl : getTradeMarKApplicaionInfo : started");
        log.info("TrademarkDetailServiceImpl : getTradeMarKApplicaionInfo : applicantCode is : {} ", applicantCode);
        return trademarkDetailRepository.getTradeMarkApplicationInfo(applicantCode);
    }

    @Override
    public List<TradeMarkInfo> getRegisteredTradeMarkInfoByApplicantCode(String applicantCode) {
        log.info("TrademarkDetailServiceImpl : getRegisteredTradeMarkInfoByApplicantCode : started");
        log.info("TrademarkDetailServiceImpl : getRegisteredTradeMarkInfoByApplicantCode : applicantCode is : {} ", applicantCode);
        return trademarkDetailRepository.getRegisteredTradeMarkInfoByApplicantCode(applicantCode);
    }

    /**
     * Retrieves trademark information for a given application ID.
     *
     * @param id the ID of the application to retrieve information for
     * @return a {@link TradeMarkInfo} object containing the trademark information
     */
    @Override
    public TradeMarkInfo getTradeMarkByApplicationId(Long id) {
        log.info("TrademarkDetailServiceImpl : getTradeMarkByApplicationId : started");
        log.info("TrademarkDetailServiceImpl : getTradeMarkByApplicationId : id is : {} ", id);
        return trademarkDetailRepository.getTradeMarkByApplicationId(id);
    }

    /**
     * Retrieves trademark detail and application data by application ID.
     *
     * @param applicationId the ID of the application to retrieve data for
     * @return an {@link ApplicationTrademarkDetailDto} object containing the detail and application data
     * @throws BusinessException if the entity is not found
     */
    @Override
    public ApplicationTrademarkDetailDto getApplicationTrademarkDetails(Long applicationId) {
        log.info("TrademarkDetailServiceImpl : getApplicationTrademarkDetails : started");
        log.info("TrademarkDetailServiceImpl : getApplicationTrademarkDetails : applicationId is : {} ", applicationId);
        ApplicationTrademarkDetailDto dto = trademarkDetailMapper.mapApplicationDet(findByApplicationId(applicationId));
        dto.setApplication(applicationInfoService.getApplicationSubstantiveExamination(applicationId));
        ApplicationDocumentLightDto document = documentsService.findTrademarkLatestDocumentByApplicationIdAndDocumentType(applicationId);

        if (document != null) {
            dto.setImageLink(document.getFileReviewUrl());
            dto.setFileName(document.getFileName());
        }

        return dto;
    }

    @Override
    public List<ApplicationTrademarkDetailLightDto> getApplicationTrademarkDetails(List<Long> applicationIds) {
        log.info("TrademarkDetailServiceImpl : getApplicationTrademarkDetails : started");
        log.info("TrademarkDetailServiceImpl : getApplicationTrademarkDetails : applicationIds.size() is : {} "+ applicationIds.size());
        List<TradeMarkInfo> trademarkDetails =
                trademarkDetailRepository.getTradeMarkByApplicationIds(applicationIds)
                        .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND,
                                new String[]{applicationIds.toString()}));
        ;
        if (trademarkDetails == null || trademarkDetails.isEmpty())
            return new ArrayList<>();
        return trademarkDetailMapper.mapTrademarkApplicationDetails(trademarkDetails);
    }

    @Override
    public PublicationApplicationTrademarkDetailDto getPublicationTradeMarkDetails(Long applicationId, Long applicationPublicationId) {
        log.info("TrademarkDetailServiceImpl : getPublicationTradeMarkDetails : started");
        log.info("TrademarkDetailServiceImpl : getPublicationTradeMarkDetails : applicationId: {}, applicationPublicationId: {} ", applicationId, applicationPublicationId);
        ApplicationInfo currentApplication = applicationInfoService.findById(applicationId);
        CustomerSampleInfoDto applicationCurrentAgent = applicationCustomerService.getAppCustomerInfoByAppIdAndType(applicationId, ApplicationCustomerType.AGENT);
        ApplicationInfoDto applicationInfoDto = requestMapper.mapEntityToRequest(currentApplication);

        ApplicationPublication applicationPublication = null ;
        LocalDate trademarkPublicationDate = null ;
        if(applicationPublicationId != null){
            applicationPublication= applicationPublicationService.findById(applicationPublicationId);
            trademarkPublicationDate = applicationPublication.getPublicationDate().toLocalDate();
        }else{
            trademarkPublicationDate = applicationPublicationService.findApplicationPublicationDateByAppIdAndType(applicationId, TRADEMARK_REGISTERATION.name()).get();
        }


        setClassifications(applicationId, applicationInfoDto);
        setApplicationTask(applicationInfoDto);

        List<SubClassificationDto> subClassificationDtos = getSubClassificationDtos(currentApplication);

        PublicationApplicationTrademarkDetailDto publicationApplicationTrademarkDetailDto = PublicationApplicationTrademarkDetailDto.builder()
                .applicationTrademarkDetailDto(trademarkDetailMapper.mapApplicationDet(findByApplicationId(applicationId)))
                .applicationStatus(applicationInfoDto.getApplicationStatus().getCode())
                .applicantNameEn(currentApplication.getOwnerNameEn())
                .processRequestTypeCode(Objects.nonNull(ApplicationCategoryEnum.valueOf(currentApplication.getCategory().getSaipCode()).getProcessTypeCode())
                        ?ApplicationCategoryEnum.valueOf(currentApplication.getCategory().getSaipCode()).getProcessTypeCode():null)

                .applicantNameAr(currentApplication.getOwnerNameAr())
                .applicationNumber(applicationInfoDto.getApplicationNumber())
                .applicationRequestNumber(currentApplication.getApplicationRequestNumber())
                .applicationFilingDate(applicationInfoDto.getFilingDate().toLocalDate())
                .niceClassifications(applicationInfoDto.getClassifications())
                .agentAdress(applicationCurrentAgent.getAddress())
                .agentNameAr(applicationCurrentAgent.getNameAr())
                .agentNameEn(applicationCurrentAgent.getNameEn())
                .addressAr(applicationInfoDto.getOwnerAddressAr())
                .addressEn(applicationInfoDto.getOwnerAddressEn())
                .subClassifications(subClassificationDtos)
                .task(applicationInfoDto.getTask())
                .applicationTitleAr(applicationInfoDto.getTitleAr())
                .applicationTitleEn(applicationInfoDto.getTitleEn())
                .publicationDate(trademarkPublicationDate)
                .build();
        if(applicationPublicationId != null )
        getoldOwenerforTRADEMARK_REGISTERATIONAndTransformed(applicationId , publicationApplicationTrademarkDetailDto  , applicationPublication );

        String typeName = "Trademark Image/voice";
        DocumentDto documentDto = documentsService.findLatestDocumentByApplicationIdAndDocumentType(applicationId, typeName);
        publicationApplicationTrademarkDetailDto.setDocument(documentDto);
        
        DurationAndPercentageDto publicationRemainingTime = new DurationAndPercentageDto();
        if (applicationPublicationId != null) {
            LocalDateTime publicationDate = applicationPublication.getPublicationDate();
            publicationApplicationTrademarkDetailDto.setPublicationDate(applicationPublication.getPublicationDate().toLocalDate());
            publicationApplicationTrademarkDetailDto.setPublicationType(applicationPublication.getPublicationType().getCode());
            Long durationValue = bpmCallerService.getRequestTypeConfigValue(PUBLICATION_DURATION_TM.name(), applicationId, ApplicationCategoryEnum.valueOf(currentApplication.getCategory().getSaipCode()).getProcessTypeCode());
            if (durationValue != null) {
                LocalDateTime publicationDateWithDuration = publicationDate.plusDays(durationValue);
                Utilities.calculateDurationWithPercentage(publicationDateWithDuration, durationValue, publicationRemainingTime);
                publicationApplicationTrademarkDetailDto.setPublicationRemainingDuration(publicationRemainingTime);
            }
            checkRevokedSubClassificationsByApplicationAndStatus(publicationApplicationTrademarkDetailDto, applicationPublication, applicationId);
            updateSupportServiceDetails(applicationPublication, publicationApplicationTrademarkDetailDto, applicationId);
        }
        
        
        List<ApplicationPriorityLightResponseDto> applicationPriorities = applicationPriorityService.setPrioritiesDetailsIfValid(applicationInfoDto);
        publicationApplicationTrademarkDetailDto.setApplicationPriorities(applicationPriorities);

        List<LKVeenaClassificationDto> veenaClassifications = applicationVeenaClassificationService.getVeenaClassificationsByAppId(applicationId);
        publicationApplicationTrademarkDetailDto.setVeenaClassifications(veenaClassifications);
        return publicationApplicationTrademarkDetailDto;
    }
    
    private void updateSupportServiceDetails(ApplicationPublication applicationPublication, PublicationApplicationTrademarkDetailDto publicationApplicationTrademarkDetailDto, Long applicationId) {
        Long serviceId = applicationPublication.getApplicationSupportServicesType() == null ? null : applicationPublication.getApplicationSupportServicesType().getId();
        if(serviceId == null) {
            if (PublicationType.TRADEMARK_RENEWAL.name().equals(applicationPublication.getPublicationType().getCode())) {
                CustomerSampleInfoDto customerInfo = supportServiceCustomerService.findBySupportServiceCustomerByApplicationIdAndType(applicationId, ApplicationCustomerType.AGENT); //agent ot main owner
                publicationApplicationTrademarkDetailDto.setAgentAdress(customerInfo.getAddress());
                publicationApplicationTrademarkDetailDto.setAgentNameAr(customerInfo.getNameAr());
                publicationApplicationTrademarkDetailDto.setAgentNameEn(customerInfo.getNameEn());
            }
        } else {
            CustomerSampleInfoDto customerInfo = supportServiceCustomerService.getAppCustomerInfoByServiceIdAndType(serviceId, ApplicationCustomerType.AGENT); //agent ot main owner
            publicationApplicationTrademarkDetailDto.setAgentAdress(customerInfo.getAddress());
            publicationApplicationTrademarkDetailDto.setAgentNameAr(customerInfo.getNameAr());
            publicationApplicationTrademarkDetailDto.setAgentNameEn(customerInfo.getNameEn());
        }
        
    }
    
    private void setClassifications(Long applicationId, ApplicationInfoDto applicationInfoDto) {
        List<ApplicationNiceClassification> classifications = applicationNiceClassificationRepository.getByApplicationId(applicationId);
        if (Objects.nonNull(classifications)) {
            List<ClassificationDto> classificationDto = classificationMapper.mapApplicationNiceClassificationtoClassificationDto(classifications);
            applicationInfoDto.setClassifications(classificationDto);
        }
    }

    private void setApplicationTask(ApplicationInfoDto applicationInfoDto) {
        Object userName = util.getFromBasicUserinfo("userName");
        String assignee = userName == null  ? null : (String) userName;
        log.info("the external logged in user is -> {}", assignee);
        applicationInfoDto.setTask(bpmCallerService.getTaskByRowIdAndUserName(applicationInfoDto.getId(), assignee, ApplicationCategoryEnum.valueOf(applicationInfoDto.getCategory().getSaipCode()).getProcessTypeCode()));
    }

    private List<SubClassificationDto> getSubClassificationDtos(ApplicationInfo currentApplication) {
        List<SubClassificationDto> subClassificationDtos = new ArrayList<>();
        List<ApplicationSubClassification> applicationSubClassifications = currentApplication.getApplicationSubClassifications();
        List<ApplicationSubClassification> applicationSubClassificationsFirstTen = applicationSubClassifications.size() >= 10 ? applicationSubClassifications.subList(0, 10) : applicationSubClassifications.subList(0, applicationSubClassifications.size());

        for (ApplicationSubClassification applicationSubClassification : applicationSubClassificationsFirstTen) {
            subClassificationDtos.add(subClassificationMapper.map(applicationSubClassification.getSubClassification()));
        }
        return subClassificationDtos;
    }

    private void checkRevokedSubClassificationsByApplicationAndStatus(PublicationApplicationTrademarkDetailDto publicationApplicationTrademarkDetailDto, ApplicationPublication applicationPublication, Long applicationId) {
        switch (applicationPublication.getPublicationType().getCode()) {
            case "PRODUCTS_LIMIT", "PATENT_PRODUCTS_LIMIT", "INDUSTRIAL_DESIGN_PRODUCTS_LIMIT" -> {
                Long supportServiceId = applicationPublication.getApplicationSupportServicesType() == null ? null : applicationPublication.getApplicationSupportServicesType().getId();
                if (supportServiceId != null) {
                    List<Long> revokedSubClassIds = revokeProductsService.getRevokedSubClassificationsIdByApplicationIdAndSupportServiceId(applicationId, supportServiceId);
                    List<SubClassificationDto> revokedSubClassifications = subClassificationMapper.map(subClassificationService.findByIdIn(revokedSubClassIds));
                    publicationApplicationTrademarkDetailDto.setRevokedSubClassifications(revokedSubClassifications);
                }
            }
        }
    }

    @Override
    public Object findSuspciondetails(String applicationNumber) {
        log.info("TrademarkDetailServiceImpl : findSuspciondetails : started");
        log.info("TrademarkDetailServiceImpl : findSuspciondetails : applicationNumber is : {} ", applicationNumber);
        Long applicationId = Utilities.isLong(applicationNumber);
        TradeMarkInfo tradeMarkInfo = trademarkDetailRepository.getRegisteredTradeMarkInfoByApplicationNumber(applicationNumber,applicationId);

        if(Objects.isNull(tradeMarkInfo)) throw new BusinessException(
                        Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND,
                        HttpStatus.NOT_FOUND,
                        new String[]{applicationNumber}
                );

        ApplicationInfo applicationInfo = applicationInfoService.findById(tradeMarkInfo.getAppId());
        TrademarkDetail trademarkDetail = findByApplicationId(tradeMarkInfo.getAppId());
        ApplicantsDto applicantsDto = applicationInfoService.listMainApplicant(applicationInfo.getId());
        ApplicantsSuspcionRequestDto applicantsSuspcionRequestDto = ApplicantsSuspcionRequestDto.builder()
                .applicantAr(applicantsDto.getNameAr())
                .applicantEn(applicantsDto.getNameEn())
                .tradeMarkAr(trademarkDetail.getNameAr())
                .tradeMarkEn(trademarkDetail.getNameEn())
                .build();


        return applicantsSuspcionRequestDto;
    }

    private List<ApplicationPriorityResponseDto> listValidPriorities(List<ApplicationPriorityResponseDto> priorities) {
        log.info("TrademarkDetailServiceImpl : listValidPriorities : started");
        log.info("TrademarkDetailServiceImpl : listValidPriorities : priorities.size() is : {} ", priorities.size());
        List<ApplicationPriorityResponseDto> vaildPriorites = new ArrayList<>();
        for (ApplicationPriorityResponseDto priority : priorities) {
            if (Objects.isNull(priority.getIsExpired()) || !priority.getIsExpired())
                vaildPriorites.add(priority);
        }
        return vaildPriorites;
    }
    
    @Override
    @SneakyThrows
    public void generateUploadSaveXmlForApplication(Long applicationId, String documentType) {
        log.info("TrademarkDetailServiceImpl : generateUploadSaveXmlForApplication : started");
        log.info("TrademarkDetailServiceImpl : generateUploadSaveXmlForApplication : applicationId is : {} ", applicationId);
        log.info("TrademarkDetailServiceImpl : generateUploadSaveXmlForApplication : documentType is : {} ", documentType);
        ByteArrayResource file = getApplicationInfoXml(applicationId);
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), "trademark.xml",
                        "text/xml", false, file.getByteArray().length, file);
        files.add(multipartFile);
        documentsService.addDocuments(files, documentType, IPRS_TRADEMARK.name(), applicationId);
    }
    
    /**
     * Retrieves XML formatted file that contains the trademark application information
     *
     * @param applicationId the ID of the application
     * @return an {@link ByteArrayResource} object containing the XML file content
     * @throws BusinessException if a JAXB exception is thrown while generating the XML
     */
    public ByteArrayResource getApplicationInfoXml(Long applicationId) {
        log.info("TrademarkDetailServiceImpl : getApplicationInfoXml : started");
        log.info("TrademarkDetailServiceImpl : getApplicationInfoXml : applicationId is : {} ", applicationId);
        TrademarkApplicationInfoXmlDto applicationInfoXmlDto = getApplicationInfoXmlDto(applicationId);
        return applicationInfoGenericService.getApplicationInfoXml(applicationInfoXmlDto);
    }
    
    /**
     * Retrieves the trademark application information needed for the XML file
     *
     * @param applicationId the ID of the application
     * @return {@link TrademarkApplicationInfoXmlDto} object containing the trademark application information needed
     * for the XML file
     */
    private TrademarkApplicationInfoXmlDto getApplicationInfoXmlDto(Long applicationId) {
        log.info("TrademarkDetailServiceImpl : getApplicationInfoXmlDto : started");
        log.info("TrademarkDetailServiceImpl : getApplicationInfoXmlDto :applicationId is : {} ", applicationId);
        TrademarkApplicationInfoXmlDataDto dto = trademarkDetailRepository.getApplicationInfoXmlDataDto(applicationId)
                .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND,
                        HttpStatus.NOT_FOUND));
        return TrademarkApplicationInfoXmlDto.builder()
                .trademarkApplicationInfoXmlDataDtoList(Collections.singletonList(dto))
                .build();
    }
    
    @Override
    public TradeMarkLightDto getTradeMarkLightDetails(Long appId) {
        log.info("TrademarkDetailServiceImpl : getTradeMarkLightDetails : started");
        log.info("TrademarkDetailServiceImpl : getTradeMarkLightDetails :appId is : {} ", appId);
        TrademarkDetailDto trademarkDetailDto = findDtoByApplicationId(appId);
        if(Objects.nonNull(trademarkDetailDto)) {
            TradeMarkLightDto tradeMarkLightDto = trademarkDetailMapper.mapTrademarkLight(trademarkDetailDto);
            tradeMarkLightDto.setTrademarkImage(documentsService.findLatestDocumentByApplicationIdAndDocumentType(appId, "Trademark Image/voice"));
            return tradeMarkLightDto;
        }
        return null;
    }
    
    
    public boolean isImage(Long appId) {
        log.info("TrademarkDetailServiceImpl : isImage : started");
        log.info("TrademarkDetailServiceImpl : isImage :appId is : {} ", appId);
        TrademarkDetail trademarkDetail = findByApplicationId(appId);
        
        return trademarkDetail != null && isTagTypeImage(trademarkDetail.getTagTypeDesc());
    }
    
    private boolean isTagTypeImage(LkTagTypeDesc tmTagType) {
        log.info("TrademarkDetailServiceImpl : isTagTypeImage : started");
        log.info("TrademarkDetailServiceImpl : isTagTypeImage : tmTagType.getCode() is : {} ", tmTagType.getCode());
        return tmTagType != null && !VOICE.name().equals(tmTagType.getCode());
    }

    @Override
    public boolean isTrademarkTypeVerbal(Long applicationId){
        return trademarkDetailRepository.isTrademarkTypeVerbal(applicationId);
    }

    @CheckCustomerAccess(categoryCodeParamIndex = 1)
    public ApplicationTrademarkDetailSummaryDto getTradeMarkApplicationDetails(Long appId) {
        ApplicationTrademarkDetailSummaryDto appSummary = trademarkDetailMapper.mapSummaryProjectionToDto(trademarkDetailRepository.getAllDetailsAboutTradeMarkDetailsSummary(appId ));
       Optional<ApplicationInfo> app = applicationInfoRepository.findById(appId);
        String value = bpmCallerFeignClient.getRequestTypeConfigValue("GRACE_PERIOD_AFTER_EXAMINER_REJECTION_TM");
        Duration duration = Duration.parse(value);
        LocalDateTime now = LocalDateTime.now();
        if (app.isPresent()) {
            LocalDateTime modifiedDate = app.get().getModifiedDate();
            Duration difference = Duration.between(modifiedDate, now);
            String status = app.get().getApplicationStatus().getCode();
            if (!difference.isNegative() && difference.compareTo(duration) <= 0 && "OBJECTIVE_REJECTION".equals(status)) {
                appSummary.setValidToAppeal(true);
            } else {
                appSummary.setValidToAppeal(false);
            }
        }
        appSummary.setCreatedByCustomerTypeNameEn(Objects.nonNull(appSummary.getCreatedByCustomerType()) ?
                appSummary.getCreatedByCustomerType().nameEn : null);

        appSummary.setCreatedByCustomerTypeNameAr(Objects.nonNull(appSummary.getCreatedByCustomerType()) ?
                appSummary.getCreatedByCustomerType().nameAr : null);

        List<ApplicantsDto> applicants = applicationInfoService.listApplicants(appId);
        if (applicants != null)
            appSummary.setApplicants(applicants);

        appSummary.setLastPublicationSummary(applicationPublicationService.getPublicationSummary(appId, TRADEMARK_REGISTERATION.name()));
        appSummary.setAgentSummary(applicationAgentFacadeService.getApplicationCurrentAgentSummary(appId));
        return appSummary;
    }
    @CheckCustomerAccess(categoryCodeParamIndex = 1)
    public TradeMarkThirdPartyDto getThirdPartyIntegrationResults(Long appId,String route) { //  CAMUNDA and NEXUS(docs)
        TradeMarkThirdPartyDto result =new TradeMarkThirdPartyDto();

        ApplicationTMAttributesDto appAttributes = trademarkDetailRepository.findNationalSecurityByAppId(appId);

        routeTasksAndDocuments(appId, route, result, appAttributes);
        return result;
    }

    private void routeTasksAndDocuments(Long appId, String route, TradeMarkThirdPartyDto result, ApplicationTMAttributesDto appAttributes) {
        switch (ThirdPartyRoute.valueOf(route)) {
            case TASKS -> getCamundaTasks(appId, result, appAttributes);
            case BOTH -> {
                getApplicationTradeMArkRelatedDocuments(appId, result, appAttributes);
                getCamundaTasks(appId, result, appAttributes);
            }
            case DOCUMENTS -> getApplicationTradeMArkRelatedDocuments(appId, result, appAttributes);

        }
    }

    private void getApplicationTradeMArkRelatedDocuments(Long appId, TradeMarkThirdPartyDto result, ApplicationTMAttributesDto appAttributes) {
        DocumentDto poaDocument = documentsService.findDocumentByApplicationIdAndDocumentType(appId, DocumentTypeEnum.POA.toString());
        if (poaDocument != null)
            result.setPoaDocument(poaDocument);
        String tradeMarkImage = "Trademark Image/voice";

        DocumentDto tmImage = documentsService.findLatestDocumentByApplicationIdAndDocumentType(appId, tradeMarkImage);
        if (tmImage != null)
            result.setTmImage(tmImage);

        if (appAttributes.getStatusCode().equals(WAIVED.name())) { // TODO 3rdParty Api
            ApplicationCheckingReportDto legalDocumentsDto = reportService.getLastReportByReportType(appId, ReportsType.DroppedRequestReport);
            if (Objects.nonNull(legalDocumentsDto)) {
                DocumentDto legalDocuments = documentsService.findDocumentById(legalDocumentsDto.getDocumentId());
            result.setLegalDocuments(legalDocuments);
            }
        }
        List<DocumentDto> documents = documentsService.getDocumentsByApplicationId(appId);
        result.setApplicationDocuments(filterReturnedDocuments(documents));

        List<ApplicationCertificateDocumentDto> applicationCertificateDocumentDtos = applicationCertificateDocumentService.findByApplicationId(appId);
        if (applicationCertificateDocumentDtos != null || !applicationCertificateDocumentDtos.isEmpty()){
            result.setApplicationCertificateDocumentDtos(applicationCertificateDocumentDtos);
        }
    }

    private void getCamundaTasks(Long appId, TradeMarkThirdPartyDto result, ApplicationTMAttributesDto appAttributes) {
        Object userName = util.getFromBasicUserinfo("userName");
        String assignee = userName == null  ? null : (String) userName;
        log.info("the external logged in user is -> {}", assignee);
        String customerCode = appAttributes.getMainApplicantCustomerCode();
        result.setTask(bpmCallerService.getTaskByRowIdAndUserName(appId, customerCode, ApplicationCategoryEnum.valueOf(appAttributes.getCategoryCode()).getProcessTypeCode()));
        result.setAllTasks(bpmCallerFeignClient.getTasksByRowIdAndAssignee(appId, assignee));
        if (appAttributes.getApplicationNumber() != null) {
            RequestIdAndNotesDto requestIdAndNotesDto = bpmCallerService.getRequestIdAndLastNotes(appId, appAttributes.getCategoryCode());
            result.setRequestNotes(requestIdAndNotesDto.getNotes());
            if (requestIdAndNotesDto.getId() != null)
                result.setRequestId(requestIdAndNotesDto.getId().toString());
        }
    }

    public List<DocumentDto> filterReturnedDocuments(List<DocumentDto> documents) {
        List<DocumentDto> filteredDocuments = new ArrayList<>();

        for (DocumentDto document : documents) {
            if (!isExcludedDocumentType(document)) {
                filteredDocuments.add(document);
            }
        }
        return filteredDocuments;
    }

    private boolean isExcludedDocumentType(DocumentDto document) {
        LKDocumentTypeDto documentType = document.getDocumentType();
        String code = (documentType != null) ? documentType.getCode() : null;

        return EXCLUDED_DOCUMENT_TYPES_CODES.contains(code);
    }
    @CheckCustomerAccess(categoryCodeParamIndex = 1)
    public ApplicationTradeMarkListClassificationsDto getApplicantsAndClassifications(Long appId){
        ApplicationTradeMarkListClassificationsDto result = new ApplicationTradeMarkListClassificationsDto();
        List<ListApplicationClassificationDto> listApplicationClassifications = classificationService.listApplicationClassification(appId);
       if(Objects.nonNull(listApplicationClassifications))
           result.setClassifications(listApplicationClassifications);

        return result;
    }








}
