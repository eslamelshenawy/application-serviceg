package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.GrantedPublishedPatentApplicationDto;
import gov.saip.applicationservice.common.dto.PublishedPatentApplicationDto;
import gov.saip.applicationservice.common.dto.PublicationYearsDto;
import gov.saip.applicationservice.common.dto.publication.PublicPublicationSearchParamDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.Publication.PublicationIssueStatusEnum;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicationIssueService extends BaseService<PublicationIssue, Long> {
    @Transactional(propagation = Propagation.MANDATORY)
    PublicationIssue updateLatestIssueOrCreateNewIssue(List<ApplicationInfo> applicationsReadyForPublishing,
                                           ApplicationCategoryEnum applicationCategoryEnum,
                                           LocalDateTime nextIssueDate,
                                           long nextIssueNumber,
                                           int issueCutOffInDays,
                                           Clock clock);

    void addApplicationsToIssue(PublicationIssue issue, ApplicationCategoryEnum applicationCategory, List<ApplicationInfo> applicationsReadyForPublishing);

    PublicationIssue createNewIssue(ApplicationCategoryEnum applicationCategoryEnum, LocalDateTime
            nextIssueDate, String nextIssueDateHijri, long nextIssueNumber);

    @Nullable
    PublicationIssue findLatestIssueByApplicationCategorySaipCode(ApplicationCategoryEnum
                                                                          applicationCategoryEnum);

    List<ApplicationCategoryToIssueCountProjection> countPublishedIssuesByApplicationCategory(Clock clock);

    PublicationYearsDto getPublicationYears(ApplicationCategoryEnum applicationCategorySaipCode);

    void setIssueDate(Long issueId, LocalDate date);

    List<UnpublishedPublicationIssueProjection> getUnpublishedPublicationIssuesByApplicationCategory(ApplicationCategoryEnum applicationCategorySaipCode,
                                                                                                     List<PublicationIssueStatusEnum> publicationIssueStatusEnums
    );

    Page<PublicationIssueProjection> getPublishedPublicationIssuesByApplicationCategory(ApplicationCategoryEnum applicationCategorySaipCode,
                                                                                        PublicPublicationSearchParamDto publicPublicationSearchParamDto,
                                                                                        List<PublicationIssueStatusEnum> publicationIssueStatusEnums,
                                                                                        String searchField,
                                                                                        Pageable pageable,
                                                                                        Clock clock);

    Long addApplicationPublicationToLatestIssueOrCreateNewIssue(ApplicationInfo application,
                                                                ApplicationCategoryEnum applicationCategoryEnum,
                                                                LocalDateTime nextIssueDate,
                                                                long nextIssueNumber,
                                                                int issueCutOffInDays,
                                                                Clock clock);

    void addApplicationToIssue(PublicationIssue issue, ApplicationInfo application);

    PublicationIssue changePublicationIssueStatus(Long id, String code);

    PublicationIssue validateIssueExistByIssueId(Long issueId);

    ByteArrayResource getTrademarkApplicationsInfoXmlByIssueId(Long issueId);

    ByteArrayResource getPatentApplicationsInfoXmlByIssueId(Long issueId);

    ByteArrayResource getIndustrialDesignApplicationsInfoXmlByIssueId(Long issueId);

    void changePublicationIssueStatusAndApplicationsStatuses(Long id, String code);

    List<PublishedPatentApplicationDto> findPublicationsByCategoryAndStatus(LocalDateTime startDate, LocalDateTime endDate);

    List<GrantedPublishedPatentApplicationDto> findPublicationsByCategory(Long publicationIssueId);
    
    public void startPublicationIssueProcess(Long id);

    public void startPublicationInstallmentRenewalProcess(ApplicationInstallment installment);
    
    public PublicationIssueApplicationPublication findPublicationByAppIdAndType(Long appId, String pubType);


}
