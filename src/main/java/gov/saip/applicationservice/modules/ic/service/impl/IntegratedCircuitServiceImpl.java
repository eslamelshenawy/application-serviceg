package gov.saip.applicationservice.modules.ic.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.FileServiceFienClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.modules.common.service.impl.BaseApplicationInfoServiceImpl;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitDto;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitListDto;
import gov.saip.applicationservice.modules.ic.dto.IntegratedCircuitRequestDto;
import gov.saip.applicationservice.modules.ic.mapper.IntegratedCircuitMapper;
import gov.saip.applicationservice.modules.ic.model.IntegratedCircuit;
import gov.saip.applicationservice.modules.ic.repository.IntegratedCircuitRepository;
import gov.saip.applicationservice.modules.ic.service.IntegratedCircuitService;
import gov.saip.applicationservice.modules.ic.validators.IntegratedCircuitsValidator;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.PdfUtil;
import gov.saip.applicationservice.util.Utilities;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static gov.saip.applicationservice.common.enums.ApplicationTypeEnum.IPRS_INTEGRATED_CIRCUITS;
import static gov.saip.applicationservice.common.enums.DocumentTypeEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntegratedCircuitServiceImpl extends BaseApplicationInfoServiceImpl implements IntegratedCircuitService {

    private final IntegratedCircuitRepository integratedCircuitRepository;
    private final IntegratedCircuitsValidator integratedCircuitsValidator;
    private final IntegratedCircuitMapper integratedCircuitMapper;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final DocumentsService documentsService;
    private final FileServiceFienClient fileServiceFienClient;
    private final PdfUtil pdfUtil;
    private final IndustrialAndIntegratedCircuitReportGenerationHelper industrialAndIntegratedCircuitReportGenerationHelper;
    @Autowired
    @Lazy
    private ApplicationInfoService applicationInfoService;
    private final CustomerServiceCaller customerServiceCaller;
    private final ApplicationCustomerService applicationCustomerService;

    @Override
    public ApplicationInfoRepository getBaseApplicationInfoRepository() {
        return applicationInfoRepository;
    }

    @Override
    @PostConstruct
    public void init() {
        registerService(ApplicationCategoryEnum.INTEGRATED_CIRCUITS, this);
    }

    @Override
    public ApplicationCategoryEnum getApplicationCategoryEnum() {
        return ApplicationCategoryEnum.INTEGRATED_CIRCUITS;
    }

    @Override
    @Transactional
    public Long saveApplication(ApplicantsRequestDto applicantsRequestDto) {
       Long appId = super.saveApplication(applicantsRequestDto);
       if(!integratedCircuitRepository.checkApplicationHasIntegratedCircuit(appId))
           saveEmptyIntegratedCircuit(appId);
       return appId;
    }

    private Long saveEmptyIntegratedCircuit(Long applicationId){
        IntegratedCircuit integratedCircuit = new IntegratedCircuit();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(applicationId);
        integratedCircuit.setApplication(applicationInfo);
        return integratedCircuitRepository.save(integratedCircuit).getId();
    }

    @Transactional
    @Override
    public Long updateWithApplication(IntegratedCircuitDto integratedCircuitDto) {
        integratedCircuitsValidator.validate(integratedCircuitDto, null);
        IntegratedCircuit integratedCircuit = getIntegratedCircuit(integratedCircuitDto);
        boolean wasCommercialExploited = integratedCircuit.isCommercialExploited();
        integratedCircuit = integratedCircuitMapper.unMap(integratedCircuit, integratedCircuitDto);
        deleteCommercialExploitedDataIfIsNotCommercialExploitedAnymore(integratedCircuitDto.getApplication().getId(), wasCommercialExploited, integratedCircuit);
        updateApplicationInfoNames(integratedCircuitDto);
        return integratedCircuitRepository.save(integratedCircuit).getId();
    }

    private void deleteCommercialExploitedDataIfIsNotCommercialExploitedAnymore(Long applicationId, boolean wasCommercialExploited, IntegratedCircuit integratedCircuit) {
        boolean isCommercialExploitedNow = integratedCircuit.isCommercialExploited();
        if( wasCommercialExploited && !isCommercialExploitedNow) {
            documentsService.deleteDocumentByApplicationIdAndDocType(applicationId, DocumentTypeEnum.INTEGRATED_CIRCUITS_COMMERCIAL_EXPLOITATION_DOC.name());
        }
    }

    private IntegratedCircuit getIntegratedCircuit(IntegratedCircuitDto integratedCircuitDto) {
        Optional<IntegratedCircuit> integratedCircuit = Optional.empty();
        if (integratedCircuitDto.getId() != null) {
            integratedCircuit = integratedCircuitRepository.findById(integratedCircuitDto.getId());
        } else if (integratedCircuitDto.getApplication().getId() != null) {
            integratedCircuit = integratedCircuitRepository.getIntegratedCircuitByApplicationId(integratedCircuitDto.getApplication().getId());
        }
        return integratedCircuit.orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public IntegratedCircuitRequestDto getIntegratedCircuitsApplicationInfo(Long applicationId) {
        IntegratedCircuitRequestDto integratedCircuitRequestDto = integratedCircuitRepository.getIntegratedCircuitAppInfo(applicationId);
        if(integratedCircuitRequestDto == null)
            return null;
        List<String> docTypes = Arrays.asList(
                INTEGRATED_CIRCUITS_FRONT_SHAPE_DOCUMENT.name(),
                INTEGRATED_CIRCUITS_BACK_SHAPE_DOCUMENT.name(),
                INTEGRATED_CIRCUITS_COMMERCIAL_EXPLOITATION_DOC.name(),
                INTEGRATED_CIRCUITS_MERGED_DOCUMENTS.name(),
                POA.name()
        );

        List<DocumentWithTypeDto> documentsList = documentsService.getApplicationDocsByApplicationIdAndTypes(applicationId, docTypes);
        Map<String, List<DocumentWithTypeDto>> documentsMap = documentsList.stream().collect(Collectors.groupingBy(document -> document.getDocumentType().getCode()));
        integratedCircuitRequestDto.setDocumentDtoMap(documentsMap);

        Map<Long, CountryDto> countriesSampleInfoDtoMap = getCommercialExploitedCountryInfo(Arrays.asList(integratedCircuitRequestDto.getCountryId()));
        integratedCircuitRequestDto.setCommercialExploitedCountry(countriesSampleInfoDtoMap.get(integratedCircuitRequestDto.getCountryId()));

        integratedCircuitRequestDto.setAgentInfo(getAgentCustomerInfoIfCreatorIsAgent(applicationId, integratedCircuitRequestDto.getCreatedByCustomerType()));

        return integratedCircuitRequestDto;
    }

    private CustomerSampleInfoDto getAgentCustomerInfoIfCreatorIsAgent(Long applicationId, ApplicationCustomerType integratedCircuitCreatorCustomerType) {
        CustomerSampleInfoDto agentInfo= null;
        if(ApplicationCustomerType.AGENT.equals(integratedCircuitCreatorCustomerType))
            agentInfo = applicationCustomerService.getAppCustomerInfoByAppIdAndType(applicationId, ApplicationCustomerType.AGENT);
        return agentInfo;
    }

    private Map<Long, CountryDto> getCommercialExploitedCountryInfo(List<Long> countriesInfo) {
        return customerServiceCaller.getCountriesMapByListOfCode(countriesInfo);
    }


    private void updateApplicationInfoNames(IntegratedCircuitDto integratedCircuitDto) {
        Optional<ApplicationInfo> applicationInfoOptional = getBaseApplicationInfoRepository().findById(integratedCircuitDto.getApplication().getId());
        if (applicationInfoOptional.isEmpty())
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_ID_NOT_FOUND, HttpStatus.BAD_REQUEST, null);

        ApplicationInfo updatedApplication = applicationInfoOptional.get();
        updatedApplication.setTitleAr(integratedCircuitDto.getDesignNameAr());
        updatedApplication.setTitleEn(integratedCircuitDto.getDesignNameEn());

        getBaseApplicationInfoRepository().save(updatedApplication);
    }


    @Override
    public PaginationDto<List<IntegratedCircuitListDto>> listIntegratedCircuitsApplication(Integer page, Integer limit, String query, String status, Sort.Direction sortDirection) {
        Page<IntegratedCircuitListDto> applications = getCustomerIntegratedCircuitsApplicationsPage(page, limit, query, status, sortDirection);

        if (applications == null || applications.isEmpty()) {
            return PaginationDto.<List<IntegratedCircuitListDto>>builder()
                    .totalElements(0L)
                    .content(List.of())
                    .totalPages(0)
                    .build();
        }

        return PaginationDto.<List<IntegratedCircuitListDto>>builder()
                .totalElements(applications.getTotalElements())
                .content(applications.getContent())
                .totalPages(applications.getTotalPages())
                .build();
    }

    private Page<IntegratedCircuitListDto> getCustomerIntegratedCircuitsApplicationsPage(Integer page, Integer limit, String query, String status, Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "createdDate"));
        if (Strings.isNullOrEmpty(status))
            status = null;
        return integratedCircuitRepository.getIntegratedCircuitsApplicationsFiltering(query, status, Utilities.getCustomerCodeFromHeaders(), Utilities.getCustomerIdFromHeadersAsLong(), pageable);
    }

    @Override
    public String getApplicationNumberWithUniqueSequence(LocalDateTime paymentDate, String serviceCode, ApplicationInfo applicationInfo) {
        Long applicationsCount = getFilingApplicationsCountInCurrentYearByCategory(paymentDate, applicationInfo);
        String hijriYear = Utilities.getLastTwoDigitOfYearHijri(paymentDate);
        String sequenceNumber = Utilities.getPadded4Number(applicationsCount + 1);
        String year = Utilities.getLastTwoDigitOfYear(paymentDate);
        return new StringBuilder().append("2").append(year).append(hijriYear).append(sequenceNumber).toString();
    }

    @Override
    @Transactional
    public void updateIntegratedCircuitApprovedNames(ApplicationNameDto applicationNameDto) {
        integratedCircuitRepository.updateIntegratedCircuitApprovedNames(applicationNameDto.getApplicationId(), applicationNameDto.getApplicationNameAr(), applicationNameDto.getApplicationNameEn());
    }

    @Override
    public void deleteByAppId(Long appId) {
        integratedCircuitRepository.deleteByAppId(appId);
    }

    @Override
    @Transactional
    public List<DocumentDto> generateJasperPdf(ReportRequestDto dto, String reportName, DocumentTypeEnum documentType) throws IOException {
        return industrialAndIntegratedCircuitReportGenerationHelper.generateJasperPdf(dto, reportName, IPRS_INTEGRATED_CIRCUITS, documentType);
    }

    @Transactional
    @Override
    public void mergeFrontAndBackShapesDocumentsByApplicationId(Long applicationId) {
        try {
            documentsService.deleteDocumentByApplicationIdAndDocType(applicationId, INTEGRATED_CIRCUITS_MERGED_DOCUMENTS.name());
            String fileName = applicationId + "_merged_ic.pdf";
            List<ByteArrayResource> byteArrayResourceList = new ArrayList<>();
            List<String> docTypes = Arrays.asList(
                    INTEGRATED_CIRCUITS_FRONT_SHAPE_DOCUMENT.name(),
                    INTEGRATED_CIRCUITS_BACK_SHAPE_DOCUMENT.name()
            );
            List<String> docsNexuoIds = documentsService.getNexuoIdsByApplicationIdAndTypes(applicationId, docTypes);
            for (String nexuoId : docsNexuoIds) {
                ByteArrayResource returnedImage = fileServiceFienClient.getFile(nexuoId);
                byteArrayResourceList.add(returnedImage);
            }
            MultipartFile multipartFile = pdfUtil.createPdfWithImages(fileName, byteArrayResourceList);
            List<MultipartFile> files = Arrays.asList(multipartFile);
            documentsService.addDocuments(files, INTEGRATED_CIRCUITS_MERGED_DOCUMENTS.name(), ApplicationTypeEnum.IPRS_INTEGRATED_CIRCUITS.name(), applicationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void updateStatusAndSetFirstAssignationDateByApplicationId(Long applicationId, ApplicationStatusEnum code) {
        applicationInfoService.changeApplicationStatusId(applicationId, code.name());
        integratedCircuitRepository.updateFirstAssignationDateByApplicationId(applicationId, LocalDateTime.now());
    }

    @Transactional
    @Override
    public void updateFirstAssignationDateByApplicationId(Long applicationId) {
        integratedCircuitRepository.updateFirstAssignationDateByApplicationId(applicationId, LocalDateTime.now());
    }

    @Transactional
    @Override
    public void deleteInternalUserDateValuesByApplicationId(Long applicationId) {
        integratedCircuitRepository.deleteFirstAssignationDateValueByApplicationId(applicationId);
        applicationInfoService.deleteLastUserModifiedDateValueByApplicationId(applicationId);
    }
}
