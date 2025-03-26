package gov.saip.applicationservice.common.service.impl;

import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.AGENT;
import static gov.saip.applicationservice.common.enums.ApplicationCustomerType.MAIN_OWNER;
import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_MAIN;
import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.INVENTOR;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.AWAITING_FOR_UPDATE_XML;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.AWAITING_VERIFICATION;
import static gov.saip.applicationservice.common.enums.ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY;
import static gov.saip.applicationservice.common.enums.PublicationTargetEnum.GAZETTE;
import static gov.saip.applicationservice.common.enums.PublicationTargetEnum.INTERNAL_GAZETTE;
import static gov.saip.applicationservice.common.enums.RequestTypeConfigEnum.PUBLICATION_DURATION_IND;
import static gov.saip.applicationservice.common.enums.RequestTypeConfigEnum.PUBLICATION_DURATION_TM;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.repository.ApplicationSupportServicesTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.saip.applicationservice.common.dto.ApplicationDocumentLightDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import gov.saip.applicationservice.common.dto.ApplicationRequestTypeConfig;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.ListBodyDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.PublicationProjection;
import gov.saip.applicationservice.common.dto.SortRequestDto;
import gov.saip.applicationservice.common.dto.lookup.LkStatusLightDto;
import gov.saip.applicationservice.common.dto.publication.IndustrialPublicationGazetteBatchViewDto;
import gov.saip.applicationservice.common.dto.publication.IndustrialPublicationVerificationBatchViewDto;
import gov.saip.applicationservice.common.dto.publication.PatentPublicationGazetteBatchViewDto;
import gov.saip.applicationservice.common.dto.publication.PatentPublicationVerificationBatchViewDto;
import gov.saip.applicationservice.common.dto.publication.PublicPublicationSearchParamDto;
import gov.saip.applicationservice.common.dto.publication.PublicationBatchViewDto;
import gov.saip.applicationservice.common.dto.publication.PublicationBatchViewProjection;
import gov.saip.applicationservice.common.dto.publication.TrademarkGazettePublicationListDto;
import gov.saip.applicationservice.common.dto.publication.TrademarkPublicationVerificationBatchViewDto;
import gov.saip.applicationservice.common.mapper.ApplicationInfoMapper;
import gov.saip.applicationservice.common.model.ApplicationCategoryToPublicationCountProjection;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.repository.ApplicationPublicationRepository;
import gov.saip.applicationservice.common.service.ApplicationCustomerService;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.BPMCallerService;
import gov.saip.applicationservice.common.service.CustomerServiceCaller;
import gov.saip.applicationservice.common.service.DocumentsService;
import gov.saip.applicationservice.common.service.PublicationService;
import gov.saip.applicationservice.common.service.agency.ApplicationAgentService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class PublicationServiceImpl implements PublicationService {

    private final ApplicationInfoRepository applicationInfoRepository;

    private final ApplicationInfoMapper applicationInfoMapper;

    private final DocumentsService documentsService;

    private final ApplicationInfoService applicationInfoService;
    
    private final ApplicationPublicationRepository applicationPublicationRepository;
    
    private final CustomerServiceCaller customerServiceCaller;

    private final BPMCallerService bpmCallerService;
    
    private final ApplicationAgentService applicationAgentService;
    
    private final ApplicationCustomerService applicationCustomerService;

    private final ApplicationSupportServicesTypeRepository supportServicesTypeRepository;

    @Override
    public PaginationDto getPatentPublicationsBatches(int page, int limit, String fromDate, String toDate) {

        Pageable pageable = PageRequest.of(page, limit);
        String saipCode = RequestTypeEnum.PATENT.name();
        String mainRequestType = getPublicationsBillNameBySaipCode(saipCode);
        if (mainRequestType == null)
            return new PaginationDto();
        String awaitingModificationStatus = AWAITING_FOR_UPDATE_XML.name();
        String approvedStatus = PUBLISHED_ELECTRONICALLY.name();
        String awaitingActionStatus = AWAITING_VERIFICATION.name();
        String billStatus = SupportServicePaymentStatus.PAID.name();
        Page<PublicationProjection> publicationsBatches =
                applicationInfoRepository.getPatentPublicationsBatches(saipCode, billStatus, mainRequestType,
                        awaitingActionStatus, approvedStatus, awaitingModificationStatus, fromDate, toDate, Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS, pageable);

        return PaginationDto.builder()
                .content(publicationsBatches.getContent())
                .totalPages(publicationsBatches.getTotalPages())
                .totalElements(publicationsBatches.getTotalElements())
                .build();
    }

    @Override
    public PaginationDto getTrademarkPublicationsBatches(int page, int limit, String fromDate, String toDate) {
        Pageable pageable = PageRequest.of(page, limit);

        String saipCode = RequestTypeEnum.TRADEMARK.name();
        String mainRequestType = getPublicationsBillNameBySaipCode(saipCode);
        if (mainRequestType == null)
            return new PaginationDto();
        String awaitingModificationStatus = AWAITING_FOR_UPDATE_XML.name();
        String approvedStatus = PUBLISHED_ELECTRONICALLY.name();
        String awaitingActionStatus = AWAITING_VERIFICATION.name();
        String billStatus = SupportServicePaymentStatus.PAID.name();
        Page<PublicationProjection> publicationsBatchess =
                applicationInfoRepository.getTrademarkPublicationsBatches(saipCode, billStatus, mainRequestType,
                        awaitingActionStatus, approvedStatus, awaitingModificationStatus, fromDate, toDate, Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS, pageable);
        return PaginationDto.builder()
                .content(publicationsBatchess.getContent())
                .totalPages(publicationsBatchess.getTotalPages())
                .totalElements(publicationsBatchess.getTotalElements())
                .build();

    }

    @Override
    public PaginationDto getIndustrialPublicationsBatches(int page, int limit, String fromDate, String toDate) {

        Pageable pageable = PageRequest.of(page, limit);
        String saipCode = RequestTypeEnum.INDUSTRIAL_DESIGN.name();
        String mainRequestType = getPublicationsBillNameBySaipCode(saipCode);
        if (mainRequestType == null)
            return new PaginationDto();
        String awaitingModificationStatus = AWAITING_FOR_UPDATE_XML.name();
        String approvedStatus = PUBLISHED_ELECTRONICALLY.name();
        String awaitingActionStatus = AWAITING_VERIFICATION.name();
        String billStatus = SupportServicePaymentStatus.PAID.name();
        Page<PublicationProjection> publicationsBatches =
                applicationInfoRepository.getIndustrialPublicationsBatches(saipCode, billStatus, mainRequestType,
                        awaitingActionStatus, approvedStatus, awaitingModificationStatus, fromDate, toDate, Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS, pageable);

        return PaginationDto.builder()
                .content(publicationsBatches.getContent())
                .totalPages(publicationsBatches.getTotalPages())
                .totalElements(publicationsBatches.getTotalElements())
                .build();
    }

    @Override
    public PaginationDto viewTrademarkPublications(int page, int limit, String receptionDate) {

        Page<PublicationBatchViewProjection> publicationsBatchView =
                viewPublications(RequestTypeEnum.TRADEMARK, page, limit, receptionDate);

        List<TrademarkPublicationVerificationBatchViewDto> trademarkPublicationsBatchViewDtos =
                applicationInfoMapper.mapTrademarkPublicationBatchView(publicationsBatchView.getContent());

        if (trademarkPublicationsBatchViewDtos.isEmpty())
            return new PaginationDto();

        trademarkPublicationsBatchViewDtos.forEach(dto -> {
            LkStatusLightDto applicationStatus = new LkStatusLightDto();
            applicationStatus.setCode(dto.getStatusCode());
            applicationStatus.setIpsStatusDescAr(dto.getIpStatusDescAr());
            applicationStatus.setIpsStatusDescEn(dto.getIpStatusDescEn());
            dto.setApplicationStatus(applicationStatus);
        });
        getTrademarkPublicationVerificationDocuments(trademarkPublicationsBatchViewDtos);

        return PaginationDto.builder()
                .content(trademarkPublicationsBatchViewDtos)
                .totalPages(publicationsBatchView.getTotalPages())
                .totalElements(publicationsBatchView.getTotalElements())
                .build();
    }

    private void getTrademarkPublicationVerificationDocuments(List<TrademarkPublicationVerificationBatchViewDto>
                                                                      trademarkPublicationsBatchView) {

        List<Long> appIds = trademarkPublicationsBatchView.stream().
                map(PublicationBatchViewDto::getApplicationId).toList();
        String typeName = "Trademark Image/voice";
        List<ApplicationDocumentLightDto> applicationDocumentLightDtos =
                documentsService.getDocumentsByAppIdsAndType(appIds, typeName);
        trademarkPublicationsBatchView.forEach(trademarkPublicationBatchView -> {
            Optional<ApplicationDocumentLightDto> documentLight =
                    applicationDocumentLightDtos.stream().filter(document -> trademarkPublicationBatchView.getApplicationId().equals(document.getApplicationId())).findAny();
            documentLight.ifPresent(trademarkPublicationBatchView::setDocument);
        });

    }

    @Override
    public PaginationDto viewPatentPublications(int page, int limit, String receptionDate) {


        Page<PublicationBatchViewProjection> publicationsBatchView =
                viewPublications(RequestTypeEnum.PATENT, page, limit, receptionDate);

        List<PatentPublicationVerificationBatchViewDto> patentPublicationVerificationBatchViewDtos =
                applicationInfoMapper.mapPatentPublicationBatchView(publicationsBatchView.getContent());

        patentPublicationVerificationBatchViewDtos.forEach(dto -> {
            LkStatusLightDto applicationStatus = new LkStatusLightDto();
            applicationStatus.setCode(dto.getStatusCode());
            applicationStatus.setIpsStatusDescAr(dto.getIpStatusDescAr());
            applicationStatus.setIpsStatusDescEn(dto.getIpStatusDescEn());
            dto.setApplicationStatus(applicationStatus);
        });

        return PaginationDto.builder()
                .content(patentPublicationVerificationBatchViewDtos)
                .totalPages(publicationsBatchView.getTotalPages())
                .totalElements(publicationsBatchView.getTotalElements())
                .build();
    }

    @Override
    public PaginationDto viewIndustrialPublications(int page, int limit, String receptionDate) {

        Page<PublicationBatchViewProjection> publicationsBatchView =
                viewPublications(RequestTypeEnum.INDUSTRIAL_DESIGN, page, limit, receptionDate);


        List<IndustrialPublicationVerificationBatchViewDto> industrialPublicationVerificationBatchViewDtos =
                applicationInfoMapper.mapIndustrialPublicationBatchView(publicationsBatchView.getContent());

        industrialPublicationVerificationBatchViewDtos.forEach(dto -> {
            LkStatusLightDto applicationStatus = new LkStatusLightDto();
            applicationStatus.setCode(dto.getStatusCode());
            applicationStatus.setIpsStatusDescAr(dto.getIpStatusDescAr());
            applicationStatus.setIpsStatusDescEn(dto.getIpStatusDescEn());
            dto.setApplicationStatus(applicationStatus);

        });

        return PaginationDto.builder()
                .content(industrialPublicationVerificationBatchViewDtos)
                .totalPages(publicationsBatchView.getTotalPages())
                .totalElements(publicationsBatchView.getTotalElements())
                .build();
    }

    private Page<PublicationBatchViewProjection> viewPublications(RequestTypeEnum requestType, int page, int limit, String receptionDate) {
        Pageable pageable = PageRequest.of(page, limit);
        String saipCode = requestType.name();
        String mainRequestType = getPublicationsBillNameBySaipCode(saipCode);
        String billStatus = SupportServicePaymentStatus.PAID.name();
        return applicationInfoRepository.viewPublications(saipCode, billStatus, mainRequestType, receptionDate,
                Constants.VERIFICATION_PUBLICATION_ALLOWED_STATUS, pageable);
    }

    private String getPublicationsBillNameBySaipCode(String saipCode) {

        if (RequestTypeEnum.TRADEMARK.name().equals(saipCode))
            return ApplicationPaymentMainRequestTypesEnum.PUBLICATION_PAYMENT.name();

        else if (RequestTypeEnum.INDUSTRIAL_DESIGN.name().equals(saipCode)) {

            return ApplicationPaymentMainRequestTypesEnum.FILE_NEW_APPLICATION.name();

        } else if (RequestTypeEnum.PATENT.name().equals(saipCode))
            return ApplicationPaymentMainRequestTypesEnum.FILE_NEW_APPLICATION.name();

        return null;
    }


    /**
     * List Gazette Or Publications ForTrademark, Patent, Industrial
     *
     * @param page               to determine the starting page
     * @param limit              to determine the size of page
     * @param publicationIssueId is optional param to filter the publications in gazette according to specific issue id
     * @param publicationTarget  using to determine which publications to list in
     *                           PUBLIC --> [GAZETTE الجريدة الرسميه, PUBLICATIONS النشره الفكريه]
     *                           OR
     *                           INTERNAL --> [GAZETTE الجريده الرسميه, PUBLICATIONS اصدار المنشور]
     * @param sortRequestDto
     */

    @Override
    public PaginationDto listGazetteOrPublicationsForTrademark(
            int page,
            int limit,
            Long publicationIssueId,
            String publicationTarget,
            PublicPublicationSearchParamDto publicPublicationSearchParamDto,
            SortRequestDto sortRequestDto) {

        try {
            List<Long> customersIds = customerServiceCaller.getCustomersIds(publicPublicationSearchParamDto.getAgentName());
            if (customersIds != null && customersIds.isEmpty()) {
                return new PaginationDto<>();
            }

            List<String> customersCodes = customerServiceCaller.getCustomersCodes(publicPublicationSearchParamDto.getApplicantName());
            if (customersCodes != null && customersCodes.isEmpty()) {
                return new PaginationDto<>();
            }
            
            Pageable pageable = PageRequest.of(page, limit);
            if (sortRequestDto.getSortBy() != null && sortRequestDto.getSortOrder() != null) {
                pageable = PageRequest.of(page, limit, Sort.by(sortRequestDto.getSortOrder(), sortRequestDto.getSortBy()));
            }

            Boolean isPublished = getPublicationFlag(publicationTarget);
            Long applicationId = Utilities.isLong(publicPublicationSearchParamDto.getApplicationNumber());

            DateTypeEnum dateType = publicPublicationSearchParamDto.getDateType();
            Page<PublicationBatchViewProjection> publicationsBatchView = applicationInfoRepository.
                    listGazetteOrPublicationsForTrademark(
                            RequestTypeEnum.TRADEMARK.name(), publicationIssueId,
                            Applicant_MAIN, ApplicationAgentStatus.ACTIVE,
                            publicPublicationSearchParamDto.getYear(),
                            Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromGazetteDate()),
                            Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToGazetteDate()),
                            Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromPublicationDate()),
                            Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToPublicationDate()),
                            publicPublicationSearchParamDto.getYearHijri(),
                            publicPublicationSearchParamDto.getPublicationType(),
                            publicPublicationSearchParamDto.getApplicationNumber(),
                            customersIds,
                            customersCodes,
                            Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromFilingDate()),
                            Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToFilingDate()),
                            publicPublicationSearchParamDto.getFilingYear(),
                            publicPublicationSearchParamDto.getFilingYearHijri(),
                            applicationId,
                            publicPublicationSearchParamDto.getSearchField(),
                            isPublished,
                            pageable
                    );

            if (publicationsBatchView == null || publicationsBatchView.isEmpty()) {
                return new PaginationDto();
            }

            List<TrademarkGazettePublicationListDto> trademarkGazettePublicationListDtos =
                    applicationInfoMapper.mapTrademarkGazettePublicationBatchView(publicationsBatchView.getContent());

            updateTrademarkGazettePublicationListDto(trademarkGazettePublicationListDtos, publicPublicationSearchParamDto);
            getoldOwenerforTRADEMARK_REGISTERATIONAndTransformed(trademarkGazettePublicationListDtos);
            return PaginationDto.builder()
                    .content(trademarkGazettePublicationListDtos)
                    .totalPages(publicationsBatchView.getTotalPages())
                    .totalElements(publicationsBatchView.getTotalElements())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new PaginationDto<>();
        }
    }

    /// IPRE-139
    private void getoldOwenerforTRADEMARK_REGISTERATIONAndTransformed( List<TrademarkGazettePublicationListDto> trademarkGazettePublicationListDtos ){

        for( TrademarkGazettePublicationListDto  element :   trademarkGazettePublicationListDtos){
            if(PublicationType.TRADEMARK_REGISTERATION.name().equalsIgnoreCase(element.getPublicationCode() )){
             List<Long> oldcustomerId =    supportServicesTypeRepository.getoldestowner(element.getApplicationId() ,SupportServiceRequestStatusEnum.TRANSFERRED_OWNERSHIP.name() );
         if (oldcustomerId != null && oldcustomerId.size() > 0 ){
             CustomerSampleInfoDto customerSampleInfoDto =   customerServiceCaller.getAnyCustomerDetails(oldcustomerId.get(0));

             System.out.println(customerSampleInfoDto);
             element.setOwnerNameAr(customerSampleInfoDto.getNameAr());
             element.setOwnerNameEn(customerSampleInfoDto.getNameEn());
             if(  element.getApplicationRelevantType() != null &&  element.getApplicationRelevantType().getApplicationRelevant()!= null  ) {
                 element.getApplicationRelevantType().getApplicationRelevant().setFullNameAr(customerSampleInfoDto.getNameAr());
                 element.getApplicationRelevantType().getApplicationRelevant().setFullNameEn(customerSampleInfoDto.getNameEn());
             }
         }

            }
        }


    }

    private void updateTrademarkGazettePublicationListDto(
    		List<TrademarkGazettePublicationListDto> trademarkGazettePublicationListDtos,
    		PublicPublicationSearchParamDto publicPublicationSearchParamDto) {
        try {
	        // TODO: remove since we ta got the applicant name from applicationInfo direct
	        getPublicationApplicationsTrademarkRelevants(trademarkGazettePublicationListDtos);
	        
	        List<Long> appIds = trademarkGazettePublicationListDtos
	        					.stream()
	        					.map(PublicationBatchViewDto::getApplicationId)
	        					.toList();
	        
	        getTrademarkGazettePublicationDocuments(appIds, trademarkGazettePublicationListDtos);
	        
	        if (publicPublicationSearchParamDto.getCheckRemainingTime()) {
	            getRemainingDurationForPublication(appIds, PUBLICATION_DURATION_TM.name(), trademarkGazettePublicationListDtos);
	        }
        } catch (Exception e) {
        	log.error("Error updating trademark gazette publication info", e);
        }
    }
    
    private void getRemainingDurationForPublication(
    		List<Long> appIds, 
    		String requestTypeConfig,
            List<? extends PublicationBatchViewDto> publications) {

    	try {
	        List<ApplicationRequestTypeConfig> applicationRequestTypeConfigs = bpmCallerService.getRequestTypeConfigValue(requestTypeConfig, appIds);	
	        if (applicationRequestTypeConfigs == null || applicationRequestTypeConfigs.isEmpty()) {
	            return;
	        }
	        
	        publications.forEach(publication -> {
	            Optional<ApplicationRequestTypeConfig> appRequestConfig = applicationRequestTypeConfigs
	                    .stream()
	                    .filter(applicationRequestTypeConfig ->
	                    (applicationRequestTypeConfig.getAppId().equals(publication.getApplicationId())
	                     && applicationRequestTypeConfig.getConfigValue() != null))
	                    .findFirst();
	            
	            appRequestConfig.ifPresent(applicationRequestTypeConfig -> {
	                    LocalDateTime publicationEndDate = publication.getPublicationDateTime()
	                            .plusDays(applicationRequestTypeConfig.getConfigValue());
	                    
	                    if (publicationEndDate.isAfter(LocalDateTime.now())) {
	                        publication.setHaveRemainingTime(true);
	                    }
	            });
	        });
    	} catch (Exception e) {
    		log.error("Error getting remaining duration for publications", e);
    	}

    }
    
    private Boolean getPublicationFlag(String publicationTarget) {
        if (GAZETTE.name().equals(publicationTarget) || INTERNAL_GAZETTE.name().equals(publicationTarget)) {
            return true;
        }
        return null;
    }

    private void getTrademarkGazettePublicationDocuments(
    		List<Long> appIds,
            List<TrademarkGazettePublicationListDto> trademarkPublicationsBatchView) {
    	try {
	        List<ApplicationDocumentLightDto> applicationDocumentLightDtos = documentsService.getDocumentsByAppIdsAndType(appIds, "Trademark Image/voice");
	        trademarkPublicationsBatchView.forEach(trademarkPublicationBatchView -> {
	        	try {
		            Optional<ApplicationDocumentLightDto> documentLight = applicationDocumentLightDtos
		            		.stream()
		            		.filter(document -> trademarkPublicationBatchView.getApplicationId().equals(document.getApplicationId()))
		            		.findAny();
		            documentLight.ifPresent(trademarkPublicationBatchView::setDocument);
	        	} catch (Exception e) {
	        		log.error("Error getting document for trademark " + trademarkPublicationBatchView.getApplicationNumber(), e);
	        	}
	        });
    	} catch (Exception e) {
    		log.error("Error getting trademark publication documents", e);
    	}
    }

    private void getPublicationApplicationsTrademarkRelevants(List<TrademarkGazettePublicationListDto> trademarkPublicationsBatchView) {
        try {
        	List<ApplicationRelevantTypeLightDto> applicationRelevantTypes = new ArrayList<>();
            trademarkPublicationsBatchView.forEach(trademarkGazettePublicationDto -> {
                applicationRelevantTypes.add(trademarkGazettePublicationDto.getApplicationRelevantType());
            });
            applicationInfoService.getDataOfCustomersByCode(applicationRelevantTypes);
        } catch (Exception e) {
            log.error("Error getting application relevants got trademarks", e);
        }
    }


    public PaginationDto listGazetteOrPublicationsForPatent(int page, int limit, Long publicationIssueId, String publicationTarget,
                                                            PublicPublicationSearchParamDto publicPublicationSearchParamDto, SortRequestDto sortRequestDto) {
        
        DateTypeEnum dateType = publicPublicationSearchParamDto.getDateType();
        Pageable pageable = PageRequest.of(page, limit);
        
        if (sortRequestDto.getSortBy() != null && sortRequestDto.getSortOrder() != null)
            pageable = PageRequest.of(page, limit, Sort.by(sortRequestDto.getSortOrder(), sortRequestDto.getSortBy()));
        
        Boolean isPublished = getPublicationFlag(publicationTarget);
        List<Long> customersIds = customerServiceCaller.getCustomersIds(publicPublicationSearchParamDto.getAgentName());
        List<String> customersCodes = customerServiceCaller.getCustomersCodes(publicPublicationSearchParamDto.getApplicantName());
        
        if ((customersIds != null && customersIds.isEmpty()) ||
                (customersCodes != null && customersCodes.isEmpty()))
            return new PaginationDto<>();
        
        Long applicationId = Utilities.isLong(publicPublicationSearchParamDto.getApplicationNumber());
        Page<PublicationBatchViewProjection> publicationsBatchView = applicationInfoRepository.
                listGazetteOrPublicationsForPatent(
                        RequestTypeEnum.PATENT.name(), publicationIssueId,
                        AGENT,
                        publicPublicationSearchParamDto.getYear(),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromGazetteDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToGazetteDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromPublicationDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToPublicationDate()),
                        publicPublicationSearchParamDto.getYearHijri(),
                        publicPublicationSearchParamDto.getPublicationType(),
                        publicPublicationSearchParamDto.getApplicationNumber(),
                        customersIds,
                        customersCodes,
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromFilingDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToFilingDate()),
                        applicationId,
                        publicPublicationSearchParamDto.getSearchField(),
                        isPublished,
                        pageable
                );
     
        List<PatentPublicationGazetteBatchViewDto> patentPublicationGazetteBatchView = applicationInfoMapper
                .mapPatentPublicationIssueAppBatchView(publicationsBatchView.getContent());
        
        if(patentPublicationGazetteBatchView.isEmpty())
            return new PaginationDto<>();
        
        List<Long> appIds = patentPublicationGazetteBatchView.stream().
                map(PublicationBatchViewDto::getApplicationId).toList();
        
        getApplicationRelevantMainApplicantTypes(patentPublicationGazetteBatchView);//todo get

        getApplicationsAgents(patentPublicationGazetteBatchView, appIds);
        
        return PaginationDto.builder()
                .content(patentPublicationGazetteBatchView)
                .totalPages(publicationsBatchView.getTotalPages())
                .totalElements(publicationsBatchView.getTotalElements())
                .build();
    }
    
    private void getApplicationsAgents(List<PatentPublicationGazetteBatchViewDto> patentPublications, List<Long> appIds) {
        
        Map<Long, String> applicationsCustomers = applicationCustomerService.getCustomerCodesByAppIdsAndCustomerType(appIds, ApplicationCustomerType.AGENT);
        
        List<ApplicationRelevantTypeLightDto> applicationRelevantTypes = new ArrayList<>();
        patentPublications.forEach(patentPublication -> {
            patentPublication.setCustomerCode(applicationsCustomers.get(patentPublication.getApplicationId()));
        });
        
        patentPublications.forEach(patentPublication -> {
            if(patentPublication.getCustomerCode() != null)
            {
                ApplicationRelevantTypeLightDto applicationRelevantType = ApplicationRelevantTypeLightDto.builder()
                        .appId(patentPublication.getApplicationId()).customerCode(patentPublication.getCustomerCode()).build();
                patentPublication.setAgentName(applicationRelevantType);
                applicationRelevantTypes.add(applicationRelevantType);
            }
        });
        
        applicationInfoService.getDataOfCustomersByCode(applicationRelevantTypes);
        
    }
    
    public PaginationDto listGazetteOrPublicationsForIndustrial(int page, int limit, Long publicationIssueId, String publicationTarget,
                                                                PublicPublicationSearchParamDto publicPublicationSearchParamDto, SortRequestDto sortRequestDto) {
        
        DateTypeEnum dateType = publicPublicationSearchParamDto.getDateType();
        
        Pageable pageable = PageRequest.of(page, limit);
        if (sortRequestDto.getSortBy() != null && sortRequestDto.getSortOrder() != null)
            pageable = PageRequest.of(page, limit, Sort.by(sortRequestDto.getSortOrder(), sortRequestDto.getSortBy()));

        List<Long> customersIds = customerServiceCaller.getCustomersIds(publicPublicationSearchParamDto.getAgentName());
        List<String> customersCodes = customerServiceCaller.getCustomersCodes(publicPublicationSearchParamDto.getApplicantName());
        if ((customersIds != null && customersIds.isEmpty()) ||
                (customersCodes != null && customersCodes.isEmpty()))
            return new PaginationDto<>();
        
        Boolean isPublished = getPublicationFlag(publicationTarget);
        Long applicationId = Utilities.isLong(publicPublicationSearchParamDto.getApplicationNumber());
        Page<PublicationBatchViewProjection> publicationsBatchView = applicationInfoRepository.
                listGazetteOrPublicationsForIndustrial(
                        RequestTypeEnum.INDUSTRIAL_DESIGN.name(), publicationIssueId,
                        isPublished, ApplicationAgentStatus.ACTIVE,
                        publicPublicationSearchParamDto.getYear(),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromGazetteDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToGazetteDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromPublicationDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToPublicationDate()),
                        publicPublicationSearchParamDto.getYearHijri(),
                        publicPublicationSearchParamDto.getPublicationType(),
                        publicPublicationSearchParamDto.getApplicationNumber(),
                        customersIds,
                        customersCodes,
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromFilingDate()),
                        Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToFilingDate()),
                        applicationId,
                        publicPublicationSearchParamDto.getSearchField(),
                        pageable
                );


        List<IndustrialPublicationGazetteBatchViewDto> industrialPublicationGazetteBatchViewDtos = applicationInfoMapper
                .mapIndustrialPublicationIssueAppBatchView(publicationsBatchView.getContent());
        
        List<Long> appIds = industrialPublicationGazetteBatchViewDtos.stream().
                map(PublicationBatchViewDto::getApplicationId).toList();
        List<ApplicationInfo> applicationInfos = publicationsBatchView.getContent().stream().map(PublicationBatchViewProjection::getApplicationInfo).toList();
        updatePublicationBatchViewDto(industrialPublicationGazetteBatchViewDtos, applicationInfos, List.of(AGENT, MAIN_OWNER));
        if (publicPublicationSearchParamDto.getCheckRemainingTime())
            getRemainingDurationForPublication(appIds, PUBLICATION_DURATION_IND.name(), industrialPublicationGazetteBatchViewDtos);

        return PaginationDto.builder()
                .content(industrialPublicationGazetteBatchViewDtos)
                .totalPages(publicationsBatchView.getTotalPages())
                .totalElements(publicationsBatchView.getTotalElements())
                .build();
    }
    
    public void updatePublicationBatchViewDto(List<IndustrialPublicationGazetteBatchViewDto> industrialPublicationGazetteBatchViewDtos,
                                              List<ApplicationInfo> applicationInfos, List<ApplicationCustomerType> customerTypes) {
        Map<Long, Map<Long, ApplicationCustomer>> applicationsCustomersMap = applicationCustomerService.getApplicationsCustomersByTypes(applicationInfos, customerTypes);
        List<Long> customersIds = applicationCustomerService.getCustomersIdsFromApplicationsCustomersMap(applicationsCustomersMap);
        Map<Long, CustomerSampleInfoDto> customerSampleInfoDtoMap = customerServiceCaller.getCustomersByIds(new ListBodyDto<>(customersIds));
        
        industrialPublicationGazetteBatchViewDtos.forEach(publication -> {
            updatePublicationWithCustomerInfo(publication, applicationsCustomersMap, customerSampleInfoDtoMap);
        });
    }
    
    private void updatePublicationWithCustomerInfo(IndustrialPublicationGazetteBatchViewDto publication,
                                                   Map<Long, Map<Long, ApplicationCustomer>> applicationsCustomersMap,
                                                   Map<Long, CustomerSampleInfoDto> customerSampleInfoDtoMap) {
        Long applicationId = publication.getApplicationId();
        Map<Long, ApplicationCustomer> customersMap = applicationsCustomersMap.get(applicationId);
        
        if (customersMap != null) {
            customersMap.forEach((customerId, applicationCustomer) -> {
                CustomerSampleInfoDto customerSampleInfoDto = customerSampleInfoDtoMap.get(customerId);
                
                if (customerSampleInfoDto != null) {
                    updatePublicationWithCustomerType(publication, applicationCustomer.getCustomerType(), customerSampleInfoDto);
                }
            });
        }
    }
    
    private void updatePublicationWithCustomerType(IndustrialPublicationGazetteBatchViewDto publication,
                                                   ApplicationCustomerType customerType, CustomerSampleInfoDto customerSampleInfoDto) {
        switch (customerType) {
            case MAIN_OWNER:
                publication.setDesignerNameAr(customerSampleInfoDto.getNameAr());
                publication.setDesignerNameEn(customerSampleInfoDto.getNameEn());
                break;
            case AGENT:
                publication.setAgentNameAr(customerSampleInfoDto.getNameAr());
                publication.setAgentNameEn(customerSampleInfoDto.getNameEn());
                break;
        }
    }


    @Override
    public List<ApplicationCategoryToPublicationCountProjection> countPublicationsByApplicationCategory(Clock clock) {
        return applicationPublicationRepository.countPublicationsByApplicationCategory(LocalDateTime.now(clock));
    }

    private void getApplicationRelevantMainApplicantTypes(
            List<PatentPublicationGazetteBatchViewDto> patentPublicationsGazetteBatchView) {

        List<ApplicationRelevantTypeLightDto> applicationsRelevants = new ArrayList<>();
        patentPublicationsGazetteBatchView.forEach(patentPublicationGazetteBatchView -> {
                    List<ApplicationRelevantTypeLightDto> applicationRelevantMainApplicantInventorTypes = new ArrayList<>();
                    Optional<ApplicationRelevantTypeLightDto> applicationRelevantMainApplicantType = patentPublicationGazetteBatchView
                            .getApplicationRelevantTypes().stream()
                            .filter(applicationRelevantTypeLightDto ->
                                    Applicant_MAIN.name().equals(applicationRelevantTypeLightDto.getType()))
                            .findAny();
                    if (applicationRelevantMainApplicantType.isPresent()) {
                        patentPublicationGazetteBatchView.setRelevantName(applicationRelevantMainApplicantType.get());
                        applicationRelevantMainApplicantInventorTypes.add(applicationRelevantMainApplicantType.get());
                    }

                    Optional<ApplicationRelevantTypeLightDto> applicationRelevantInventorType = patentPublicationGazetteBatchView
                            .getApplicationRelevantTypes().stream()
                            .filter(applicationRelevantTypeLightDto ->
                                    INVENTOR.name().equals(applicationRelevantTypeLightDto.getType()))
                            .findAny();
                    if (applicationRelevantInventorType.isPresent()) {
                        patentPublicationGazetteBatchView.setInventorName(applicationRelevantInventorType.get());
                        applicationRelevantMainApplicantInventorTypes.add(applicationRelevantInventorType.get());

                    }
                    applicationsRelevants.addAll(applicationRelevantMainApplicantInventorTypes);


                }

        );
        applicationInfoService.getDataOfCustomersByCode(applicationsRelevants);
    }

}
