package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.GrantedPublishedPatentApplicationDto;
import gov.saip.applicationservice.common.dto.PublishedPatentApplicationDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationIssueRepository extends BaseRepository<PublicationIssue, Long> {
    long countByLkApplicationCategory(LkApplicationCategory lkApplicationCategory);

    @Query("""
            SELECT pi
            FROM PublicationIssue pi
            WHERE pi.lkApplicationCategory.saipCode = :applicationCategorySaipCode
            ORDER BY pi.issuingDate DESC
            """)
    Page<PublicationIssue> findLatestIssueByApplicationCategorySaipCode(@Param("applicationCategorySaipCode") String applicationCategorySaipCode,
                                                                        Pageable pageable);

    @Query("""
            SELECT MAX(pi.issueNumber)
            FROM PublicationIssue pi
            WHERE pi.lkApplicationCategory.saipCode = :applicationCategorySaipCode
            """)
    Optional<Long> findMaxIssueNumberByApplicationCategorySaipCode(@Param("applicationCategorySaipCode") String applicationCategorySaipCode);

    @Query("""
                SELECT new gov.saip.applicationservice.common.model.ApplicationCategoryToIssueCountProjection(
                    ac.id,
                    ac.saipCode,
                    ac.applicationCategoryDescEn,
                    ac.applicationCategoryDescAr,
                    (SELECT COUNT(DISTINCT pi.id)
                      FROM PublicationIssue pi
                      JOIN PublicationIssueApplicationPublication piap ON pi.id = piap.publicationIssue.id
                      JOIN piap.applicationPublication ap
                      JOIN ap.applicationInfo ai
                      JOIN ai.applicationStatus appStatus
                      WHERE ac.id = ai.category.id
                      AND appStatus.code IN (:statuses)
                      AND pi.issuingDate < :now)
                )
                FROM LkApplicationCategory ac
            """)
    List<ApplicationCategoryToIssueCountProjection> countByApplicationCategoryWithIssuingDateBefore(
            @Param("now") LocalDateTime now,
            @Param("statuses") List<String> statuses
    );

    @Query(value = """
            SELECT DISTINCT ON (date_part('year', pi.issuing_date)) pi.issuing_date
            FROM application.publication_issue pi
            JOIN application.lk_application_category ac ON pi.lk_application_category_id = ac.id
            WHERE ac.saip_code = :applicationCategorySaipCode
            ORDER BY date_part('year', pi.issuing_date)
            """, nativeQuery = true)
    List<Timestamp> getPublicationDates(@Param("applicationCategorySaipCode") String applicationCategorySaipCode);


    @Query("""
            SELECT new gov.saip.applicationservice.common.model.UnpublishedPublicationIssueProjection(
                pi.id,
                pi.issueNumber,
                COUNT(DISTINCT paip.applicationPublication.id),
                pi.issuingDate,
                lpis
            )
            FROM PublicationIssue pi
            JOIN pi.lkPublicationIssueStatus lpis
            JOIN PublicationIssueApplicationPublication paip ON paip.publicationIssue.id = pi.id
            WHERE  pi.lkApplicationCategory.saipCode = :applicationCategorySaipCode
            AND (COALESCE(:publicationIssueStatusList) IS NULL OR lpis.code in :publicationIssueStatusList)
            GROUP BY pi.id, pi.issueNumber, pi.issuingDate, lpis.id
            ORDER BY pi.issueNumber DESC
            """)
    List<UnpublishedPublicationIssueProjection> getPublicationIssuesByApplicationCategoryAndIssuingDateAfter(@Param("applicationCategorySaipCode") String applicationCategorySaipCode,
                                                                                                             @Param("publicationIssueStatusList") List<String> publicationIssueStatuses);

    @Query(value = """
            SELECT new gov.saip.applicationservice.common.model.PublicationIssueProjection(
                pi.id,
                pi.issueNumber,
                pi.issuingDate,
                COUNT(DISTINCT ap.id) as numberOfApplications
            )
            FROM PublicationIssue pi
            JOIN PublicationIssueApplicationPublication paip ON paip.publicationIssue.id = pi.id
            JOIN paip.applicationPublication ap
            JOIN ap.applicationInfo ai
            JOIN ai.applicationStatus las
            JOIN pi.lkApplicationCategory lac
            JOIN pi.lkPublicationIssueStatus lpis
            LEFT JOIN ApplicationCustomer ac ON ac.application.id = ai.id
            LEFT JOIN ap.publicationType pt
            WHERE pi.issueNumber is not null and pi.issuingDate < :now
            AND (COALESCE(:publicationIssueStatusList) IS NULL OR lpis.code in :publicationIssueStatusList)
            AND lac.saipCode = :applicationCategorySaipCode
            AND (DATE(:from_date) IS NULL OR DATE(pi.issuingDate) >= DATE(:from_date))
            AND (DATE(:to_date) IS NULL OR DATE(pi.issuingDate) <= DATE(:to_date))
            AND (:year IS NULL OR date_part('year', DATE(pi.issuingDate)) = :year)
            AND (:hijri_year IS NULL OR pi.issuingDateHijri LIKE CONCAT('%',:hijri_year, '%'))
            AND (:publication_type IS NULL OR pt.code = :publication_type)
            AND (:application_number IS NULL OR ai.applicationNumber = :application_number OR ai.grantNumber = :application_number OR ai.idOld = :application_number)
            AND (:applicationId IS NULL OR ai.id = :applicationId )
            AND (COALESCE (:customers_ids) IS NULL OR (ac.customerId in :customers_ids  AND ac.customerType = :agent_type))
            AND (COALESCE (:customers_codes) IS NULL OR ac.customerCode in :customers_codes)
            AND (ac.customerType IS NULL OR ac.customerType  = :owner_type)
            AND (:searchField IS NULL OR
                 ai.applicationNumber LIKE CONCAT('%',:searchField, '%') OR 
                 ai.grantNumber LIKE CONCAT('%',:searchField, '%') OR 
                 ai.ownerNameAr LIKE CONCAT('%',:searchField, '%') OR 
                 ai.ownerNameEn LIKE CONCAT('%',:searchField, '%') OR 
                 ai.applicationRequestNumber LIKE CONCAT('%',:searchField, '%') OR 
                 ai.titleAr LIKE CONCAT('%',:searchField, '%') OR 
                 ai.titleEn LIKE CONCAT('%',:searchField, '%')
                )
            GROUP BY pi.id, pi.issueNumber, pi.issuingDate
            HAVING (COUNT(DISTINCT ai.id) > 0)
        """)
    Page<PublicationIssueProjection> getPublicationIssuesByApplicationCategoryAndIssuingDateBefore(@Param("applicationCategorySaipCode") String applicationCategorySaipCode,
                                                                                                   @Param("now") LocalDateTime now,
                                                                                                   @Param("agent_type") ApplicationCustomerType agentType,
                                                                                                   @Param("year") Integer year,
                                                                                                   @Param("from_date") LocalDate fromDate,
                                                                                                   @Param("to_date") LocalDate toDate,
                                                                                                   @Param("hijri_year") String hijriYear,
                                                                                                   @Param("publication_type") String publicationType,
                                                                                                   @Param("application_number") String applicationNumber,
                                                                                                   @Param("customers_ids") List<Long> customersIds,
                                                                                                   @Param("customers_codes") List<String> customersCodes,
                                                                                                   @Param("owner_type") ApplicationCustomerType ownerType,
                                                                                                   @Param("publicationIssueStatusList") List<String> publicationIssueStatuses,
                                                                                                   @Param("applicationId") Long applicationId,
                                                                                                   @Param("searchField") String searchField,
                                                                                                   Pageable pageable);

    @Query("SELECT new gov.saip.applicationservice.common.dto.trademark.TrademarkApplicationInfoXmlDataDto(ai.id, ai.email, " +
            "          td.markType , td.nameAr , td.nameEn) " +
            "FROM ApplicationInfo ai " +
            "JOIN TrademarkDetail td ON ai.id = td.applicationId " +
            "JOIN ApplicationPublication ap ON ai.id = ap.applicationInfo.id " +
            "JOIN PublicationIssueApplicationPublication piap ON piap.applicationPublication.id = ap.id " +
            "JOIN PublicationIssue pi ON piap.publicationIssue.id = pi.id " +
            "WHERE pi.id = :issueId")
    List<TrademarkApplicationInfoXmlDataDto> getTrademarkApplicationsInfoXmlDtoByIssueId(@Param("issueId") Long issueId);

    @Query("SELECT new gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDataDto(ai.id, ai.email, " +
            "          pd.ipdSummaryAr , pd.ipdSummaryEn) " +
            "FROM ApplicationInfo ai " +
            "JOIN PatentDetails pd ON ai.id = pd.applicationId " +
            "JOIN ApplicationPublication ap ON ai.id = ap.applicationInfo.id " +
            "JOIN PublicationIssueApplicationPublication piap ON piap.applicationPublication.id = ap.id " +
            "JOIN PublicationIssue pi ON piap.publicationIssue.id = pi.id " +
            "WHERE pi.id = :issueId")
    List<PatentApplicationInfoXmlDataDto> getPatentApplicationsInfoXmlDtoByIssueId(@Param("issueId") Long issueId);

    @Query("SELECT new gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDataDto" +
            "          (ai.id, ai.email, idd.explanationAr , idd.explanationEn, idd.requestType) " +
            "FROM ApplicationInfo ai " +
            "JOIN IndustrialDesignDetail idd ON ai.id = idd.applicationId " +
            "JOIN ApplicationPublication ap ON ai.id = ap.applicationInfo.id " +
            "JOIN PublicationIssueApplicationPublication piap ON piap.applicationPublication.id = ap.id " +
            "JOIN PublicationIssue pi ON piap.publicationIssue.id = pi.id " +
            "WHERE pi.id = :issueId")
    List<IndustrialDesignApplicationInfoXmlDataDto> getIndustrialDesignApplicationsInfoXmlDtoByIssueId(
            @Param("issueId") Long issueId);

    @Query("""
            SELECT ai.id
            FROM PublicationIssueApplicationPublication piap
            JOIN piap.publicationIssue pi
            JOIN piap.applicationPublication ap
            JOIN ap.applicationInfo ai
            WHERE pi.id = :id
            """)
    List<Long> findApplicationIdsByPublicationIssueId(@Param("id") Long id);

    @Query("SELECT NEW gov.saip.applicationservice.common.dto.PublishedPatentApplicationDto(" +
            "ai.titleAr, ai.applicationNumber, las.code) " +
            "FROM ApplicationPublication ap " +
            "JOIN ap.applicationInfo ai " +
            "JOIN ai.category lac " +
            "JOIN ai.applicationStatus las " +
            "WHERE lac.saipCode = 'PATENT' " +
            "AND ap.publicationDate BETWEEN :startDate AND :endDate")
    List<PublishedPatentApplicationDto> findPublicationsByCategoryAndStatus(
            @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    @Query("SELECT NEW gov.saip.applicationservice.common.dto.GrantedPublishedPatentApplicationDto(" +
            "ai.titleAr, ai.applicationNumber, " +
            "(SELECT MAX(pi2.issuingDate) FROM PublicationIssue pi2 WHERE pi2.issuingDate < pi.issuingDate) as startDate, " +
            "pi.issuingDate as endDate) " +
            "FROM PublicationIssueApplicationPublication piap " +
            "JOIN PublicationIssue pi ON piap.publicationIssue = pi " +
            "JOIN ApplicationPublication ap ON piap.applicationPublication = ap " +
            "JOIN ap.applicationInfo ai " +
            "JOIN ai.category lac " +
            "WHERE lac.saipCode = 'PATENT' " +
            "AND pi.issueNumber = :publicationIssueId ")
    List<GrantedPublishedPatentApplicationDto> findPublicationsByCategory(
            @Param("publicationIssueId") Long publicationIssueId);




}
