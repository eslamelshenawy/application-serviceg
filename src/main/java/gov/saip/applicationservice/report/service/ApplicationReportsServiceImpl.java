package gov.saip.applicationservice.report.service;


import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.AddressResponseDto;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleDrawingsResDto;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleResDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.common.dto.trademark.PublicationApplicationTrademarkDetailDto;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.mapper.trademark.TrademarkDetailMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.trademark.TrademarkDetailRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.industrial.DesignSampleService;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignDetailService;
import gov.saip.applicationservice.common.service.trademark.LkMarkTypeService;
import gov.saip.applicationservice.common.service.trademark.LkTagLanguageService;
import gov.saip.applicationservice.common.service.trademark.LkTagTypeDescService;
import gov.saip.applicationservice.common.service.trademark.TrademarkDetailService;
import gov.saip.applicationservice.common.service.trademark.impl.CustomMultipartFile;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.report.JasperReportUtils;
import gov.saip.applicationservice.report.dto.IndustrialDesignImagesDetailsDto;
import gov.saip.applicationservice.report.dto.IndustrialDesignJasperReportDto;
import gov.saip.applicationservice.report.dto.PatentLicenseJasperDto;
import gov.saip.applicationservice.report.repository.PatentLicenseRepository;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


/**
 * The {@code PatentLicenseServiceImpl} class provides an implementation of the {@link ApplicationReportsService} interface
 * that is responsible for patent license operations
 */
@Service
@RequiredArgsConstructor
public class ApplicationReportsServiceImpl implements ApplicationReportsService {

    private  ApplicationInfoService applicationInfoService;
    private final PatentLicenseRepository patentLicenseRepository;
    private final ApplicationUserService applicationUserService;
    private final ApplicationNiceClassificationService applicationNiceClassificationService;
    private final DocumentsService documentsService;
    private final DesignSampleService designSampleService;
    private final IndustrialDesignDetailService industrialDesignDetailService;
    private final TrademarkDetailMapper trademarkDetailMapper;
    private final TrademarkDetailRepository trademarkDetailRepository;
    private final LkMarkTypeService markTypeService;
    private final LkTagTypeDescService tagTypeDescService;
    private final LkTagLanguageService tagLanguageService;
    private final ApplicationSubClassificationService applicationSubClassificationService;
    private final CustomerClient customerClient;
    private final ApplicationInfoGenericService applicationInfoGenericService;
    private final TrademarkDetailService trademarkDetailService;

    @Autowired
    public void setApplicationInfoService(ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }

    /**
     * Retrieves the patent license as PDF file
     *
     * @param applicationId the ID of the patent application
     * @return an {@link ByteArrayResource} object containing the PDF file content
     * @throws BusinessException if the application identified by applicationId parameter is not found
     *                           or if error happens during reading jasper file from disk
     *                           or if jasper throws a JRException exception while generating the report
     */
    @Override
    public ByteArrayResource getPatentLicenseByApplicationId(Long applicationId) {
        PatentLicenseJasperDto dto = getPatentLicenseJasperDtoByApplicationId(applicationId);
        return JasperReportUtils.generateReport("jasper/patent-license.jrxml", Collections.singletonList(dto));
    }

    @Override
    public IndustrialDesignJasperReportDto getIndustrialDesignJasperReportDetails(Long applicationId,boolean performance ) {
        IndustrialDesignDetailDto industrialDesignDetailDto=  industrialDesignDetailService.findDtoByApplicationId(applicationId) ;


        ApplicationInfo applicationInfo = applicationInfoService.findById(applicationId);
        IndustrialDesignJasperReportDto industrialDesignJasperReportDto=IndustrialDesignJasperReportDto.builder()
                .industrialDesignDetailDto(industrialDesignDetailDto)
                .appFilingDate(applicationInfo.getFilingDate())
                .appFilingDateHigiri(applicationInfo.getFilingDateHijri())
                .granteDate(applicationInfo.getGrantDate())
                .granteDateHigiri(applicationInfo.getGrantDateHijri())
                .ApplicationNumber(applicationInfo.getApplicationNumber())
                .IndustrialDesignImagesDetailsDto(prepareData(industrialDesignDetailDto))
                .count(designSampleService.count(industrialDesignDetailDto.getId()))
                .applicationReportDetailsDto(applicationInfoService.getApplicationReportInfo(applicationId))
                .classificationDto(applicationNiceClassificationService.getLightNiceClassificationsByAppId(applicationId))
                .titleAr(applicationInfo.getTitleAr())
                .titleEn(applicationInfo.getTitleEn())
                .categoryDescAr(applicationInfo.getCategory().getApplicationCategoryDescAr())

                .build();
       if(performance) {
           industrialDesignJasperReportDto.setExaminerNames(applicationUserService.listUsersByApplicationIdAndRoleName(applicationId, null));
       }

        return industrialDesignJasperReportDto;
    }

    private List<IndustrialDesignImagesDetailsDto> prepareData(IndustrialDesignDetailDto dto){
        List<IndustrialDesignImagesDetailsDto> res = new ArrayList<>();
        IndustrialDesignImagesDetailsDto industrialDesignImagesDetailsDto = null;
        int indx =0;
        for (DesignSampleResDto sample:dto.getDesignSamples()) {
            indx++;
            for (DesignSampleDrawingsResDto draw: sample.getDesignSampleDrawings()) {
                industrialDesignImagesDetailsDto = new IndustrialDesignImagesDetailsDto();
                industrialDesignImagesDetailsDto.setSampleName(sample.getName());
                industrialDesignImagesDetailsDto.setSampleNumber(indx);
                industrialDesignImagesDetailsDto.setShapeCount(sample.getDesignSampleDrawings().size());
                industrialDesignImagesDetailsDto.setUrlImage(documentsService.findLightDocumentByIds(Arrays.asList(draw.getDocId())).get(0).getFileReviewUrl());
                res.add(industrialDesignImagesDetailsDto);

            }

        }

        return res;
    }


    private PatentLicenseJasperDto getPatentLicenseJasperDtoByApplicationId(Long applicationId) {
        // Fill the PatentLicenseJasperDto from database
        List<PatentLicenseJasperDto> dtoList = patentLicenseRepository
                .getPatentLicenseJasperDtoByApplicationId(applicationId);
        if (dtoList.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        PatentLicenseJasperDto dto = dtoList.get(0);
        dtoList.subList(1, dtoList.size())
                .forEach(e -> dto.appendInternationalClassification(e.getInternationalClassification()));
        // Enrich the PatentLicenseJasperDto from ApplicationInfoService
        ApplicationReportDetailsDto detailsDto = applicationInfoService.getApplicationReportInfo(applicationId);
        enrichPatentLicenseJasperDtoWithAgentData(detailsDto, dto);
        enrichPatentLicenseJasperDtoWithOwnerAndInventorData(detailsDto, dto);
        return dto;
    }

    private void enrichPatentLicenseJasperDtoWithAgentData(ApplicationReportDetailsDto detailsDto,
                                                           PatentLicenseJasperDto dto) {
        if (detailsDto != null && detailsDto.getApplicationAgent() != null) {
            dto.setAgentName(detailsDto.getApplicationAgent().getNameAr());
        }
    }

    private void enrichPatentLicenseJasperDtoWithOwnerAndInventorData(ApplicationReportDetailsDto detailsDto,
                                                                      PatentLicenseJasperDto dto) {
        if (detailsDto != null && detailsDto.getApplicants() != null) {
            detailsDto.getApplicants().forEach(it -> {
                if (ApplicationRelevantEnum.Applicant_MAIN.equals(it.getType())) {
                    dto.setOwnerNameEn(it.getNameEn());
                    dto.setOwnerNameAr(it.getNameAr());
                    AddressResponseDto address = it.getAddress();
                    if (address != null) {
                        dto.setOwnerAddress(address.getStreetName() + ", " +
                                address.getUnitNumber() + ", " +
                                address.getBuildingNumber() + ", " +
                                address.getCity());
                        if (address.getCountryObject() != null) {
                            dto.setOwnerAddress(dto.getOwnerAddress() + ", "
                                    + address.getCountryObject().getIciCountryNameAr());
                            dto.setOwnerNationality(address.getCountryObject().getIciNationality());
                        }
                    }
                } else if (ApplicationRelevantEnum.INVENTOR.equals(it.getType())) {
                    dto.setInventorName(it.getNameAr());
                }
            });
        }
    }
    
    @Override
    public PublicationApplicationTrademarkDetailDto getTradeMarkDetailsReport(Long applicationId) {

       ApplicationInfoDto applicationInfoDto = applicationInfoService.getApplication(applicationId);

        CustomerSampleInfoDto applicationCurrentAgent = applicationInfoService.getCurrentAgentsInfoByApplicationId(applicationId);
        List<SubClassificationDto> subClassificationDtos = applicationSubClassificationService.listApplicationSubClassifications(applicationId);
        PublicationApplicationTrademarkDetailDto publicationApplicationTrademarkDetailDto = PublicationApplicationTrademarkDetailDto.builder()
                .applicationTrademarkDetailDto(trademarkDetailMapper.mapApplicationDet(trademarkDetailService.findByApplicationId(applicationId)))
                .applicantNameEn(applicationInfoDto.getOwnerNameEn())
                .applicantNameAr(applicationInfoDto.getOwnerNameAr())
                .agentAdress(applicationCurrentAgent.getAddress())
                .agentNameAr(applicationCurrentAgent.getNameAr())
                .agentNameEn(applicationCurrentAgent.getNameEn())
                .subClassifications(subClassificationDtos)
                .build();
                publicationApplicationTrademarkDetailDto.setAddressAr(applicationInfoDto.getOwnerAddressAr());
        
        if (!publicationApplicationTrademarkDetailDto.getApplicationTrademarkDetailDto().getTagTypeDesc().getCode().equals("VOICE")) {
            String typeName = "Trademark Image/voice";
            DocumentDto documentDto = documentsService.findLatestDocumentByApplicationIdAndDocumentType(applicationId, typeName);
            publicationApplicationTrademarkDetailDto.setDocument(documentDto);
        }
        List<ApplicationPriorityResponseDto> validPriorities = listValidPriorities(applicationInfoDto.getApplicationPriority());
        if (!validPriorities.isEmpty()) {
            publicationApplicationTrademarkDetailDto.setPriorityApplicationNumber(applicationInfoDto.getApplicationPriority().get(0).getPriorityApplicationNumber());
            publicationApplicationTrademarkDetailDto.setPriorityFilingDate(applicationInfoDto.getApplicationPriority().get(0).getFilingDate());
            publicationApplicationTrademarkDetailDto.setPriorityCountry(customerClient.getCountriesByIds(
                    List.of(
                            applicationInfoDto.getApplicationPriority().get(0).getCountryId()
                    )

            ).getPayload().get(0).getIciCountryNameAr());
            publicationApplicationTrademarkDetailDto.setDasCode(applicationInfoDto.getApplicationPriority().get(0).getDasCode());
        }
        return publicationApplicationTrademarkDetailDto;
    }





    private List<ApplicationPriorityResponseDto> listValidPriorities(List<ApplicationPriorityResponseDto> priorities) {
        List<ApplicationPriorityResponseDto> vaildPriorites = new ArrayList<>();
        for (ApplicationPriorityResponseDto priority : priorities) {
            if (Objects.isNull(priority.getIsExpired()) || !priority.getIsExpired())
                vaildPriorites.add(priority);
        }
        return vaildPriorites;
    }
    private List<MultipartFile> getCustomMultipartFiles(Long applicationId, ByteArrayResource file, String originalFilename, String contentType) {
        
        List<MultipartFile> files = new ArrayList<>();
        Boolean isEmpty = file.getByteArray().length <= 0;
        MultipartFile multipartFile =
                new CustomMultipartFile(applicationId.toString(), originalFilename,
                        contentType, isEmpty, file.getByteArray().length, file);
        files.add(multipartFile);
        return files;
    }

}
