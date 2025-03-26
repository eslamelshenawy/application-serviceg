package gov.saip.applicationservice.common.service.industrial.impl;


import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.industrial.*;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.DocumentTypeEnum;
import gov.saip.applicationservice.common.mapper.industrial.IndustrialDesignDetailMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.industrial.IndustrialDesignDetail;
import gov.saip.applicationservice.common.repository.industrial.IndustrialDesignDetailsRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.trademark.impl.CustomMultipartFile;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.TSIDUtils;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_INDUSTRIAL_DESIGN;
import static gov.saip.applicationservice.common.enums.RequestTypeConfigEnum.PUBLICATION_DURATION_IND;

@Service
@RequiredArgsConstructor
public class IndustrialDesignDetailServiceImpl extends BaseServiceImpl<IndustrialDesignDetail, Long> implements IndustrialDesignDetailService {

    private final static Logger logger = LoggerFactory.getLogger(DesignSampleServiceImpl.class);
    private final IndustrialDesignDetailMapper industrialDesignDetailMapper;
    private final IndustrialDesignDetailsRepository industrialDesignDetailRepository;
    private final DocumentsService documentsService;
    private final DesignSampleService designSampleService;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationInfoGenericService applicationInfoGenericService;
    private final ApplicationPublicationService applicationPublicationService;
    private final BPMCallerService bpmCallerService;
    private final ApplicationPriorityService applicationPriorityService;
    private final IndustrialAndIntegratedCircuitReportGenerationHelper industrialAndIntegratedCircuitReportGenerationHelper;
    @Autowired
    ApplicationInfoService applicationService;
    @Override
    protected BaseRepository<IndustrialDesignDetail, Long> getRepository() {
        return industrialDesignDetailRepository;
    }
    public Long create(IndustrialDesignDetailReqDto req, Long applicationId) {
        try {
            IndustrialDesignDetail entity = getAndMapDet(req, applicationId);
            insert(entity);
            return entity.getId();
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }


    private IndustrialDesignDetail getAndMapDet(IndustrialDesignDetailReqDto req, Long applicationId) {
        Optional<IndustrialDesignDetail> det = industrialDesignDetailRepository
                .findByApplicationId(applicationId);
        IndustrialDesignDetail entity = null;
        if (det.isPresent())
            entity = industrialDesignDetailMapper.mapDetReq(req, det.get());
        else {
            entity = industrialDesignDetailMapper.mapDetReq(req);
            entity.setId(TSIDUtils.next());
            entity.setApplicationId(applicationId);
        }

        return entity;
    }

    public IndustrialDesignDetail findByApplicationId(Long applicationId) {
        String[] params = {applicationId.toString()};
        return industrialDesignDetailRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
    }
    public IndustrialDesignDetail getByApplicationId(Long applicationId){
        Optional<IndustrialDesignDetail> industrialDesignDetail = industrialDesignDetailRepository.findByApplicationId(applicationId);
        if(!industrialDesignDetail.isPresent())
            return null;
        return industrialDesignDetail.get();
    }
    public IndustrialDesignDetailDto findDtoByApplicationId(Long applicationId) {
        IndustrialDesignDetail entity = findByApplicationId(applicationId);
        IndustrialDesignDetailDto result = industrialDesignDetailMapper.map(entity);
        removeDeletedSample(result);
        return findDocumentFromIndustrialDesignDetail(result);
    }

    public IndustrialDesignDetailDto findDtoById(Long id) {
        IndustrialDesignDetail entity = findById(id);
        IndustrialDesignDetailDto result = industrialDesignDetailMapper.map(entity);
        removeDeletedSample(result);

        if (result.getDesignSamples() != null && !result.getDesignSamples().isEmpty()) {
            for (DesignSampleResDto sample : result.getDesignSamples()) {
                List<ApplicationRelevantTypeDto> applicationRelevantTypeDtoList = sample.getApplicationRelevantTypes();
                applicationService.assignDataOfCustomersCode(applicationRelevantTypeDtoList);
                sample.setApplicationRelevantTypes(applicationRelevantTypeDtoList);
                designSampleService.processSample(sample);
            }
        }
        return findDocumentFromIndustrialDesignDetail(result);
    }


    @Override
    public Optional<IndustrialDesignDetail> findIndustrialDesignById(Long id) {
        return industrialDesignDetailRepository.findById(id);
    }

    @Override
    public IndustrialDesignDetailDto findDocumentFromIndustrialDesignDetail(IndustrialDesignDetailDto industrialDesignDetailDto) throws BusinessException {
        try {
            if (industrialDesignDetailDto == null)
                return null;

            TreeSet<DesignSampleResDto> designSamples = industrialDesignDetailDto.getDesignSamples();
            if (designSamples == null || designSamples.isEmpty()) {
                return industrialDesignDetailDto;
            }

            List<Long> docIds = new LinkedList<>();
            for (DesignSampleResDto sample : designSamples) {
                TreeSet<DesignSampleDrawingsResDto> designSampleDrawings = sample.getDesignSampleDrawings();
                if (designSampleDrawings != null && !designSampleDrawings.isEmpty()) {
                    for (DesignSampleDrawingsResDto drawing : designSampleDrawings) {
                        if (drawing.getDocId() != null) {
                            docIds.add(drawing.getDocId());
                        }
                    }
                }
            }
            Map<Long, DocumentDto> map = documentsService.findDocumentMapByIds(docIds);
            for (DesignSampleResDto sample : designSamples) {
                TreeSet<DesignSampleDrawingsResDto> designSampleDrawings = sample.getDesignSampleDrawings();
                if (designSampleDrawings != null && !designSampleDrawings.isEmpty()) {
                    for (DesignSampleDrawingsResDto drawing : designSampleDrawings) {
                        if (drawing.getDocId() != null) {
                            drawing.setDocument(map.get(drawing.getDocId()));
                        }
                    }
                }
            }
            return industrialDesignDetailDto;
        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }

    @Override
    public void removeDeletedSample(IndustrialDesignDetailDto result) {
        if (Objects.nonNull(result) && !CollectionUtils.isEmpty(result.getDesignSamples())) {
            Iterator<DesignSampleResDto> itr = result.getDesignSamples().iterator();
            while (itr.hasNext()) {
                DesignSampleResDto sample = itr.next();
                if (sample.getIsDeleted() == 1) {
                    itr.remove();
                }
            }
        }
    }
    @Override
    public ApplicationIndustrialDesignDetailDto getApplicationIndustrialDesignDetails(Long applicationId) {
        if (applicationId == null)
            throw new BusinessException(Constants.ErrorKeys.IDENTIFIER_REQUIRED, HttpStatus.BAD_REQUEST, null);

            ApplicationIndustrialDesignDetailDto dto =  industrialDesignDetailMapper.mapApplicationDet(findByApplicationId(applicationId));
            dto.setApplication(applicationInfoService.getApplicationSubstantiveExamination(applicationId));
            return dto;
    }

    @Override
    public PublicationDetailsDto getPublicationDetails(Long applicationId, Long applicationPublicationId) {
        ApplicationInfo applicationInfo = applicationInfoService.findById(applicationId);
        IndustrialDesignDetailDto industrialDesignDetailDto= findDtoByApplicationId( applicationId);
        ApplicantsDto mainApplicant =  applicationInfoService.listMainApplicant(applicationInfo.getId());
        ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(applicationId);
        CustomerSampleInfoDto applicationCurrentAgent = applicationInfoService.getCurrentAgentsInfoByApplicationId(applicationId);
        PublicationDetailsDto publicationDetailsDto = PublicationDetailsDto.builder()
        .applicationNumber(applicationInfoDto.getApplicationNumber())
        .applicationFilingDate(applicationInfoDto.getFilingDate().toLocalDate())
        .applicationStatus(applicationInfoDto.getApplicationStatus().getCode())
                .processRequestTypeCode(Objects.nonNull(ApplicationCategoryEnum.valueOf(applicationInfo.getCategory().getSaipCode()).getProcessTypeCode())?
                        ApplicationCategoryEnum.valueOf(applicationInfo.getCategory().getSaipCode()).getProcessTypeCode():null)
        .designerNameEn(mainApplicant.getNameEn())
        .designerNameAr(mainApplicant.getNameAr())
        .certOwnerNameAr(mainApplicant.getNameAr())
        .certOwnerAddress(mainApplicant.getAddress())
        .agentNameAr(applicationCurrentAgent.getNameAr())
        .agentNameEn(applicationCurrentAgent.getNameEn())
        .designSamplesCount(designSampleService.count(industrialDesignDetailDto.getId()))
        .task(applicationInfoDto.getTask())
        .applicationTitleAr(applicationInfoDto.getTitleAr())
        .applicationTitleEn(applicationInfoDto.getTitleEn())
        .build();
        
        DurationAndPercentageDto publicationRemainingTime = new DurationAndPercentageDto();
        if(applicationPublicationId != null) {
            LocalDateTime publicationDate = applicationPublicationService.
                    getPublicationDateById(applicationPublicationId);
            publicationDetailsDto.setPublicationDate(publicationDate.toLocalDate());
            Long durationValue = bpmCallerService.getRequestTypeConfigValue(PUBLICATION_DURATION_IND.name());
            if(durationValue != null) {
                LocalDateTime publicationDateWithDuration = publicationDate.plusDays(durationValue);
                Utilities.calculateDurationWithPercentage(publicationDateWithDuration, durationValue, publicationRemainingTime);
                publicationDetailsDto.setPublicationRemainingDuration(publicationRemainingTime);
            }
        }
        
        List<ApplicationPriorityLightResponseDto> applicationPriorities = applicationPriorityService.setPrioritiesDetailsIfValid(applicationInfoDto);
        publicationDetailsDto.setApplicationPriorities(applicationPriorities);

        return publicationDetailsDto;
    }
    private List<ApplicationPriorityResponseDto> listValidPriorities(List<ApplicationPriorityResponseDto> priorities)
    {
        List<ApplicationPriorityResponseDto> vaildPriorites =  new ArrayList<>();
        for (ApplicationPriorityResponseDto priority:priorities) {
            if(Objects.isNull(priority.getIsExpired())||!priority.getIsExpired()    )
                vaildPriorites.add(priority);
        }
        return vaildPriorites;
    }
    
    @Override
    @SneakyThrows
    public void generateUploadSaveXmlForApplication(Long applicationId, String documentType) {
        
        ByteArrayResource file = getApplicationInfoXml(applicationId);
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), "industrial.xml",
                        "text/xml", false, file.getByteArray().length, file);
        files.add(multipartFile);
        documentsService.addDocuments(files, documentType, IPRS_INDUSTRIAL_DESIGN.name(), applicationId);
    }


    /**
     * Retrieves XML formatted file that contains the industrial design applications information
     *
     * @param applicationId the ID of the application
     * @return an {@link ByteArrayResource} object containing the XML file content
     * @throws BusinessException if a JAXB exception is thrown while generating the XML
     */
    public ByteArrayResource getApplicationInfoXml(Long applicationId) {
        IndustrialDesignApplicationInfoXmlDto dto = getApplicationInfoXmlDto(applicationId);
        return applicationInfoGenericService.getApplicationInfoXml(dto);
    }

    /**
     * Retrieves the industrial design application information needed for the XML file
     *
     * @param applicationId the ID of the application
     * @return {@link IndustrialDesignApplicationInfoXmlDto} object containing the industrial design application
     * information needed for the XML file
     */
    private IndustrialDesignApplicationInfoXmlDto getApplicationInfoXmlDto(Long applicationId) {
        IndustrialDesignApplicationInfoXmlDataDto dto = industrialDesignDetailRepository
                .getApplicationInfoXmlDataDto(applicationId)
                .orElseThrow(() -> new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND,
                        HttpStatus.NOT_FOUND));
        return IndustrialDesignApplicationInfoXmlDto.builder()
                .industrialDesignApplicationInfoXmlDataDtoList(Collections.singletonList(dto))
                .build();
    }

    @Override
    public List<DocumentDto> generateJasperPdf(ReportRequestDto dto,String reportName, DocumentTypeEnum documentType) throws IOException {
        return industrialAndIntegratedCircuitReportGenerationHelper.generateJasperPdf(dto, reportName, IPRS_INDUSTRIAL_DESIGN, documentType);
    }

}
