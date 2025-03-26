package gov.saip.applicationservice.common.service.impl;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.GrantedPublishedPatentApplicationDto;
import gov.saip.applicationservice.common.dto.ProcessRequestDto;
import gov.saip.applicationservice.common.dto.PublicationYearsDto;
import gov.saip.applicationservice.common.dto.PublishedPatentApplicationDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDto;
import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDto;
import gov.saip.applicationservice.common.dto.publication.PublicPublicationSearchParamDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDto;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.enums.Publication.PublicationIssueStatusEnum;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.repository.ApplicationPublicationRepository;
import gov.saip.applicationservice.common.repository.PublicationIssueApplicationRepository;
import gov.saip.applicationservice.common.repository.PublicationIssueRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.service.*;
import gov.saip.applicationservice.common.service.lookup.LkPublicationIssueStatusService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.generics.ApplicationInfoGenericService;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static gov.saip.applicationservice.common.enums.Publication.PublicationIssueStatusEnum.AWAITING_ACTION;
import static gov.saip.applicationservice.util.Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PublicationIssueServiceImpl extends BaseServiceImpl<PublicationIssue, Long> implements PublicationIssueService {
    private final PublicationIssueRepository publicationIssueRepository;
    private final LkApplicationCategoryRepository lkApplicationCategoryRepository;
    private final PublicationIssueApplicationRepository publicationIssueApplicationRepository;
    private final LkApplicationStatusRepository lkApplicationStatusRepository;
    private final ApplicationPublicationService applicationPublicationService;
    private final ApplicationPublicationRepository applicationPublicationRepository;
    private final CustomerServiceCaller customerServiceCaller;
    private final LkPublicationIssueStatusService lkPublicationIssueStatusService;
    private final BPMCallerService bpmCallerService;
    private final ApplicationInfoGenericService applicationInfoGenericService;
    private final ApplicationInfoService applicationInfoService;
    private static final Logger logger = LoggerFactory.getLogger(PublicationIssueServiceImpl.class);
    @Override
    protected BaseRepository<PublicationIssue, Long> getRepository() {
        return publicationIssueRepository;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public PublicationIssue updateLatestIssueOrCreateNewIssue(List<ApplicationInfo> applicationsReadyForPublishing,
                                                  ApplicationCategoryEnum applicationCategoryEnum,
                                                  LocalDateTime nextIssueDate,
                                                  long nextIssueNumber,
                                                  int issueCutOffInDays,
                                                  Clock clock) {
        // TODO[Fawzy] should we even create an issue if there no applications to avoid a numbered issue with no publications?
        PublicationIssue latestIssue = findLatestIssueByApplicationCategorySaipCode(applicationCategoryEnum);
        if (latestIssue != null && latestIssue.isWithinCutOffPeriod(clock, issueCutOffInDays)) {
            addApplicationsToIssue(latestIssue, applicationCategoryEnum, applicationsReadyForPublishing);
        } else {
            String nextIssueDateHijri = Utilities.convertDateFromGregorianToHijri(nextIssueDate.toLocalDate());
            PublicationIssue newIssue = createNewIssue(applicationCategoryEnum, nextIssueDate, nextIssueDateHijri,
                    nextIssueNumber);
            addApplicationsToIssue(newIssue, applicationCategoryEnum, applicationsReadyForPublishing);
            return newIssue;
        }
        return null;
    }

    @Override
    public void addApplicationsToIssue(PublicationIssue issue, ApplicationCategoryEnum applicationCategory, List<ApplicationInfo> applicationsReadyForPublishing) {
        LkApplicationStatus publishedElectronically = lkApplicationStatusRepository.findByCodeAndApplicationCategoryId(ApplicationStatusEnum.PUBLISHED_ELECTRONICALLY.name() , issue.getLkApplicationCategory().getId()).orElseThrow();
        // TODO[Fawzy] this approach can cause performance issues, we need batching https://www.baeldung.com/jpa-hibernate-batch-insert-update or native SQL
//        applicationsReadyForPublishing
//                .stream()
//                // TODO[Fawzy] Should we update this status?
//                .peek(applicationInfo -> applicationInfo.setApplicationStatus(publishedElectronically))
//                .map(application -> new PublicationIssueApplicationPublication(issue, application))
//                .forEach(publicationIssueApplicationRepository::save);
    }

    @Override
    public PublicationIssue createNewIssue(ApplicationCategoryEnum applicationCategoryEnum, LocalDateTime
            nextIssueDate, String nextIssueDateHijri, long nextIssueNumber) {
        LkApplicationCategory applicationCategory = lkApplicationCategoryRepository.findBySaipCode(applicationCategoryEnum.name()).orElseThrow();
        PublicationIssue publicationIssue = new PublicationIssue(
                nextIssueNumber,
                nextIssueDate,
                nextIssueDateHijri,
                applicationCategory
        );
        addPublicationIssueStatus(publicationIssue, AWAITING_ACTION.name());
        publicationIssue = publicationIssueRepository.save(publicationIssue);
        return publicationIssue;
    }

    private void addPublicationIssueStatus(PublicationIssue publicationIssue, String code) {
        LkPublicationIssueStatus lkPublicationIssueStatus =
                lkPublicationIssueStatusService.findByCode(code);
        publicationIssue.setLkPublicationIssueStatus(lkPublicationIssueStatus);
        update(publicationIssue);
    }
    
    @Override
    public void startPublicationIssueProcess(Long id) {
        ProcessRequestDto processRequestDto = new ProcessRequestDto();
        processRequestDto.setProcessId("application_publication_issue_process");
        Map<String, Object> vars = new HashMap<>();
        vars.put("id", id.toString());
        vars.put("identifier", id.toString());
//        vars.put("issuingDate", issuingDate);
        PublicationIssue publicationIssue = super.findById(id);
        if (publicationIssue.getLkApplicationCategory() != null){
            processRequestDto.setCategoryId(publicationIssue.getLkApplicationCategory().getId());
        }
        processRequestDto.setVariables(vars);
        bpmCallerService.startApplicationProcess(processRequestDto);
    }

    @Override
    public void startPublicationInstallmentRenewalProcess(ApplicationInstallment installment) {
        ProcessRequestDto processRequestDto = new ProcessRequestDto();
        processRequestDto.setProcessId("trademark_renewal_publication_process");
        Map<String, Object> vars = new HashMap<>();
        vars.put("id", installment.getId().toString());
        vars.put("identifier", installment.getId().toString());
        vars.put("publicationType", PublicationType.TRADEMARK_RENEWAL.toString());
        vars.put("applicationId", installment.getApplication().getId().toString());

        processRequestDto.setCategoryId(installment.getApplication().getCategory().getId());
        processRequestDto.setVariables(vars);
        bpmCallerService.startApplicationProcess(processRequestDto);
    }

    @Override
    @Nullable
    public PublicationIssue findLatestIssueByApplicationCategorySaipCode(ApplicationCategoryEnum
                                                                                 applicationCategoryEnum) {
        return publicationIssueRepository.findLatestIssueByApplicationCategorySaipCode(
                        applicationCategoryEnum.name(),
                        Pageable.ofSize(1))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ApplicationCategoryToIssueCountProjection> countPublishedIssuesByApplicationCategory(Clock clock) {
        return publicationIssueRepository.countByApplicationCategoryWithIssuingDateBefore(LocalDateTime.now(clock), Constants.GAZETTE_STATUSES);
    }

    @Override
    public PublicationYearsDto getPublicationYears(ApplicationCategoryEnum applicationCategorySaipCode) {
        List<LocalDateTime> publicationDates = publicationIssueRepository.getPublicationDates(applicationCategorySaipCode.name())
                .stream()
                .map(Timestamp::toLocalDateTime)
                .toList();
        List<Integer> publicationDatesGregorian = publicationDates
                .stream()
                .map(LocalDateTime::getYear)
                .toList();
        List<Integer> publicationDatesHijri = publicationDates
                .stream()
                .map(HijrahDate::from)
                .map(hijriDate -> hijriDate.get(ChronoField.YEAR))
                .toList();
        return new PublicationYearsDto(publicationDatesGregorian, publicationDatesHijri);
    }

    @Override
    public void setIssueDate(Long issueId, LocalDate date) {
        String[] param = {issueId.toString()};
        PublicationIssue publicationIssue = publicationIssueRepository.getReferenceById(issueId);
        if (Objects.isNull(publicationIssue)) {
            throw new BusinessException(EXCEPTION_RECORD_NOT_FOUND, HttpStatus.BAD_REQUEST, param);
        }
        LocalTime time = LocalTime.of(0, 0);
        publicationIssue.setIssuingDate(LocalDateTime.of(date, time));
        publicationIssueRepository.save(publicationIssue);
    }


    @Override
    public List<UnpublishedPublicationIssueProjection> getUnpublishedPublicationIssuesByApplicationCategory(ApplicationCategoryEnum applicationCategorySaipCode,
                                                                                                            List<PublicationIssueStatusEnum> publicationIssueStatusEnums) {
        List<String> publicationIssueStatuses = publicationIssueStatusEnums == null ? null :
                publicationIssueStatusEnums.stream().map(Enum::name).toList();
        return publicationIssueRepository.getPublicationIssuesByApplicationCategoryAndIssuingDateAfter(applicationCategorySaipCode.name(), publicationIssueStatuses);
    }
    
    @Override
    public Page<PublicationIssueProjection> getPublishedPublicationIssuesByApplicationCategory(ApplicationCategoryEnum applicationCategorySaipCode,
                                                                                               PublicPublicationSearchParamDto publicPublicationSearchParamDto,
                                                                                               List<PublicationIssueStatusEnum> publicationIssueStatusEnums,
                                                                                               String searchField, Pageable pageable, Clock clock) {
        DateTypeEnum dateType = publicPublicationSearchParamDto.getDateType();
        
        List<String> publicationIssueStatuses = publicationIssueStatusEnums == null ? null :
                publicationIssueStatusEnums.stream().map(Enum::name).toList();
        
        List<Long> customersIds = customerServiceCaller.getCustomersIds(publicPublicationSearchParamDto.getAgentName());
        List<String> customersCodes = customerServiceCaller.getCustomersCodes(publicPublicationSearchParamDto.getApplicantName());
        
        if ((customersIds != null && customersIds.isEmpty()) ||
                (customersCodes != null && customersCodes.isEmpty()))
            return Page.empty();
        
        Long applicationId = Utilities.isLong(publicPublicationSearchParamDto.getApplicationNumber());
        
        return publicationIssueRepository.getPublicationIssuesByApplicationCategoryAndIssuingDateBefore(
                applicationCategorySaipCode.name(), LocalDateTime.now(clock),
                ApplicationCustomerType.AGENT,
                publicPublicationSearchParamDto.getYear(),
                Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getFromGazetteDate()),
                Utilities.convertHijriDateToLocalDate(dateType, publicPublicationSearchParamDto.getToGazetteDate()),
                publicPublicationSearchParamDto.getYearHijri(),
                publicPublicationSearchParamDto.getPublicationType(),
                publicPublicationSearchParamDto.getApplicationNumber(),
                customersIds,
                customersCodes,
                ApplicationCustomerType.MAIN_OWNER,
                publicationIssueStatuses,
                applicationId,
                searchField,
                pageable);
    }
    
    @Override
    @Transactional
    public Long addApplicationPublicationToLatestIssueOrCreateNewIssue(ApplicationInfo application,
                                                                       ApplicationCategoryEnum applicationCategoryEnum,
                                                                       LocalDateTime nextIssueDate,
                                                                       long nextIssueNumber,
                                                                       int issueCutOffInDays,
                                                                       Clock clock) {
        Long newIssueId = null;
        PublicationIssue latestIssue = findLatestIssueByApplicationCategorySaipCode(applicationCategoryEnum);
        if (shouldAddToLatestIssue(latestIssue, clock, issueCutOffInDays)) {
            addApplicationToIssueAndUpdateGrantDate(application, latestIssue);
        } else {
            String nextIssueDateHijri = Utilities.convertDateFromGregorianToHijri(nextIssueDate.toLocalDate());
            PublicationIssue newIssue = createNewIssue(applicationCategoryEnum, nextIssueDate, nextIssueDateHijri,
                    nextIssueNumber);
            addApplicationToIssueAndUpdateGrantDate(application, newIssue);
            newIssueId = newIssue.getId();
        }
        return newIssueId;
    }
    
    private boolean shouldAddToLatestIssue(PublicationIssue latestIssue, Clock clock, int issueCutOffInDays) {
        return latestIssue != null && latestIssue.isWithinCutOffPeriod(clock, issueCutOffInDays);
    }
    
    private void addApplicationToIssueAndUpdateGrantDate(ApplicationInfo application, PublicationIssue issue) {
        ApplicationPublication applicationPublication =
                applicationPublicationService.findByApplicationId(application.getId());
        String publicationType = applicationPublication.getPublicationType().getCode();
        
        if(ApplicationCategoryEnum.PATENT.equals(ApplicationCategoryEnum.valueOf(application.getCategory().getSaipCode()))){
            application.setGrantDate(null);
            application.setGrantDateHijri(null);
        }else{
            application.setGrantDate(issue.getIssuingDate());
            application.setGrantDateHijri(issue.getIssuingDateHijri());
        }
        addApplicationToIssue(issue, application);
        applicationInfoService.update(application);
    }


    @Override
    public void addApplicationToIssue(PublicationIssue issue, ApplicationInfo application) {
               ApplicationPublication applicationPublication =
                applicationPublicationService.findByApplicationId(application.getId());
        applicationPublication.setIsPublished(true);
        applicationPublicationService.update(applicationPublication);
        PublicationIssueApplicationPublication relation = new PublicationIssueApplicationPublication(
                issue,
                applicationPublication
        );
        publicationIssueApplicationRepository.save(relation);
    }

    @Override
    public PublicationIssue changePublicationIssueStatus(Long id, String code) {
        LkPublicationIssueStatus lkPublicationIssueStatus =
                lkPublicationIssueStatusService.findByCode(code);
        PublicationIssue publicationIssue = findById(id);
        publicationIssue.setLkPublicationIssueStatus(lkPublicationIssueStatus);
        return super.update(publicationIssue);
    }

    /**
     * Validate that an issue exists
     *
     * @param issueId the issue ID to be validated
     * @return an {@link PublicationIssue} entity object for this issue ID
     * @throws BusinessException if the issue identified by issueId parameter is not found
     */
    public PublicationIssue validateIssueExistByIssueId(Long issueId) {
        return publicationIssueRepository.findById(issueId).orElseThrow(() -> {
            logger.error().message("Issue Id (" + issueId + ") not found").log();
            return new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND,
                    HttpStatus.NOT_FOUND, new String[]{issueId.toString()});
        });
    }

    /**
     * Retrieves XML formatted file that contains the trademark applications information
     *
     * @param issueId the issue ID of the applications
     * @return an {@link ByteArrayResource} object containing the XML file content
     * @throws BusinessException if a JAXB exception is thrown while generating the XML
     * @throws BusinessException if the issue identified by issueId parameter is not found
     */
    public ByteArrayResource getTrademarkApplicationsInfoXmlByIssueId(Long issueId) {
        TrademarkApplicationInfoXmlDto dto = getTrademarkApplicationsInfoXmlDtoByIssueId(issueId);
        return applicationInfoGenericService.getApplicationInfoXml(dto);
    }

    /**
     * Retrieves the trademark applications information needed for the XML file
     *
     * @param issueId the issue ID of the applications
     * @return {@link TrademarkApplicationInfoXmlDto} which contains a list of TrademarkApplicationInfoXmlDataDto objects,
     * each of which contains an application information needed for the XML file
     * @throws BusinessException if the issue identified by issueId parameter is not found
     */
    private TrademarkApplicationInfoXmlDto getTrademarkApplicationsInfoXmlDtoByIssueId(Long issueId) {
        // Validate that issueId is found
        validateIssueExistByIssueId(issueId);
        // Retrieve the trademark applications information for this issueId
        List<TrademarkApplicationInfoXmlDataDto> dtoList = publicationIssueRepository
                .getTrademarkApplicationsInfoXmlDtoByIssueId(issueId);
        return TrademarkApplicationInfoXmlDto.builder()
                .trademarkApplicationInfoXmlDataDtoList(dtoList)
                .build();
    }

    /**
     * Retrieves XML formatted file that contains the patent applications information
     *
     * @param issueId the issue ID of the applications
     * @return an {@link ByteArrayResource} object containing the XML file content
     * @throws BusinessException if a JAXB exception is thrown while generating the XML
     * @throws BusinessException if the issue identified by issueId parameter is not found
     */
    public ByteArrayResource getPatentApplicationsInfoXmlByIssueId(Long issueId) {
        PatentApplicationInfoXmlDto dto = getPatentApplicationsInfoXmlDtoByIssueId(issueId);
        return applicationInfoGenericService.getApplicationInfoXml(dto);
    }

    /**
     * Retrieves the patent applications information needed for the XML file
     *
     * @param issueId the issue ID of the applications
     * @return {@link PatentApplicationInfoXmlDto} which contains a list of PatentApplicationInfoXmlDataDto objects,
     * each of which contains an application information needed for the XML file
     * @throws BusinessException if the issue identified by issueId parameter is not found
     */
    private PatentApplicationInfoXmlDto getPatentApplicationsInfoXmlDtoByIssueId(Long issueId) {
        // Validate that issueId is found
        validateIssueExistByIssueId(issueId);
        // Retrieve the patent applications information for this issueId
        List<PatentApplicationInfoXmlDataDto> dtoList = publicationIssueRepository
                .getPatentApplicationsInfoXmlDtoByIssueId(issueId);
        return PatentApplicationInfoXmlDto.builder()
                .patentApplicationInfoXmlDataDtoList(dtoList)
                .build();
    }

    /**
     * Retrieves XML formatted file that contains the industrial design applications information
     *
     * @param issueId the issue ID of the applications
     * @return an {@link ByteArrayResource} object containing the XML file content
     * @throws BusinessException if a JAXB exception is thrown while generating the XML
     * @throws BusinessException if the issue identified by issueId parameter is not found
     */
    public ByteArrayResource getIndustrialDesignApplicationsInfoXmlByIssueId(Long issueId) {
        IndustrialDesignApplicationInfoXmlDto dto = getIndustrialDesignApplicationsInfoXmlDtoByIssueId(issueId);
        return applicationInfoGenericService.getApplicationInfoXml(dto);
    }

    /**
     * Retrieves the industrial design applications information needed for the XML file
     *
     * @param issueId the issue ID of the applications
     * @return {@link IndustrialDesignApplicationInfoXmlDto} which contains a list of
     * IndustrialDesignApplicationInfoXmlDataDto objects, each of which contains an application information needed
     * for the XML file
     * @throws BusinessException if the issue identified by issueId parameter is not found
     */
    private IndustrialDesignApplicationInfoXmlDto getIndustrialDesignApplicationsInfoXmlDtoByIssueId(Long issueId) {
        // Validate that issueId is found
        validateIssueExistByIssueId(issueId);
        // Retrieve the industrial design applications information for this issueId
        List<IndustrialDesignApplicationInfoXmlDataDto> dtoList = publicationIssueRepository
                .getIndustrialDesignApplicationsInfoXmlDtoByIssueId(issueId);
        return IndustrialDesignApplicationInfoXmlDto.builder()
                .industrialDesignApplicationInfoXmlDataDtoList(dtoList)
                .build();
    }


    @Override
    @Transactional
    public void changePublicationIssueStatusAndApplicationsStatuses(Long id, String statusCode) {

        PublicationIssue publicationIssue = changePublicationIssueStatus(id, statusCode);
        List<Long> applicationIds = getPublicationIssueApplicationIds(id);
        applicationInfoService.
                changeApplicationStatusesByApplicationIds(applicationIds, publicationIssue.getLkApplicationCategory().getId());


    }

    private List<Long> getPublicationIssueApplicationIds(Long issueId) {
        return publicationIssueRepository.findApplicationIdsByPublicationIssueId(issueId);
    }

    @Override
    public List<PublishedPatentApplicationDto> findPublicationsByCategoryAndStatus(LocalDateTime startDate, LocalDateTime endDate) {
        return publicationIssueRepository.findPublicationsByCategoryAndStatus(startDate, endDate );
    }

    @Override
    public List<GrantedPublishedPatentApplicationDto> findPublicationsByCategory(Long publicationIssueId) {
        return publicationIssueRepository.findPublicationsByCategory(publicationIssueId);
    }
    
    public PublicationIssueApplicationPublication findPublicationByAppIdAndType(Long appId, String pubType){
        return publicationIssueApplicationRepository.findByAppIdAndType(appId, pubType);
        
    }

}
