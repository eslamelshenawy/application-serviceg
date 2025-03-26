package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.dto.Suspcion.TrademarkDetailsProjection;
import gov.saip.applicationservice.common.dto.publication.PublicationBatchViewProjection;
import gov.saip.applicationservice.common.dto.reports.PatentReportProjection;
import gov.saip.applicationservice.common.dto.veena.ApplicationClassificationProjection;
import gov.saip.applicationservice.common.enums.*;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.projection.ApplicationReportInfo;
import gov.saip.applicationservice.common.projection.CountApplications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
//@JaversSpringDataAuditable
public interface ApplicationInfoRepository extends BaseRepository<ApplicationInfo, Long> {

    @Modifying
    @Query("""
        update ApplicationInfo ai set ai.processRequestId = :processRequestId where ai.id = :id
    """)
    void updateApplicationWithProcessRequestId(@Param("id") Long id, @Param("processRequestId") Long processRequestId);
    @Modifying
    @Query(value = "update application.applications_info set substantive_examination = :substantiveExamination where id = :id ", nativeQuery = true)
    void updateSubstantiveExamination(@Param("substantiveExamination") Boolean substantiveExamination, @Param("id") Long id);

    @Query(value = "SELECT id FROM application.application_relevant_type where application_info_id =  :id ", nativeQuery = true)
    List<Long> getApplicationTypeId(@Param("id") Long id);

    @Modifying
    @Query(value = "update application.applications_info set accelerated = :accelerated where id = :id ", nativeQuery = true)
    void updateAccelerated(@Param("accelerated") Boolean accelerated, @Param("id") Long id);


//    @Modifying
//    @Query("delete FROM ApplicationInfo app "+
//            " where app.id=:appId and app.applicationStatus.code='DRAFT'")
//    void deleteAppById(@Param("appId")Long appId);

    @Modifying
    @Query("update  ApplicationInfo app set app.isDeleted='1'  " +
            "WHERE app.id = :appId ")
    void deleteAppById(@Param("appId") Long appId);

//    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
//    @Query(value = "SELECT  distinct a FROM ApplicationInfo a" +
//            " JOIN a.applicationRelevantTypes b" +
//            " join a.category category" +
//            " left join a.agent agent" +
//            " WHERE (" +
//            "COALESCE(:userIds) is null or a.createdByUserId IN (:userIds) " + // to get created by you
//            "or b.customerCode = :customerCode " + // to get created for you
//            "or (agent.customerId = :customerId AND agent.status  = 'ACTIVE' )" + // to get managed by you as an agent
//            ")" +
//            "AND(COALESCE(:appIds) is null or a.id in (:appIds))"+
//            " AND (COALESCE (:applicationCategories) IS NULL or a.category.applicationCategoryDescEn IN (:applicationCategories))" +  // should be change to saipCode
//            " AND (:categoryId is null or category.id =:categoryId)" +
//            " AND (:applicationNumber is null or a.applicationNumber = :applicationNumber)" +
//            "AND (COALESCE (:appStatus) IS NULL or a.applicationStatus.code IN (:appStatus))" +
//            " AND b.type = 'Applicant_MAIN'"
//    )

    @Query(value = """
            SELECT  distinct a
            FROM ApplicationInfo a
            JOIN a.category category
            JOIN a.applicationCustomers ac
            JOIN a.applicationStatus
            LEFT JOIN FETCH  ApplicationRelevantType art on art.applicationInfo.id = a.id
            WHERE (:customerId is null or ac.customerId = :customerId)
            AND (COALESCE(:appIds) is null or a.id not in (:appIds))
            AND (COALESCE (:applicationCategories) IS NULL or a.category.applicationCategoryDescEn IN (:applicationCategories))
            AND (COALESCE (:customerTypes) IS NULL or ac.customerType IN (:customerTypes))
            AND (:categoryId is null or category.id =:categoryId)
            AND (:applicationNumber is null or a.applicationNumber = :applicationNumber or a.idOld = :applicationNumber  OR a.grantNumber = :applicationNumber or a.applicationRequestNumber = :applicationNumber  )
            AND (:applicationId is null or a.id = :applicationId )
            AND (COALESCE (:appStatus) IS NULL or a.applicationStatus.code IN (:appStatus))
        """
    )
    Page<ApplicationInfo> getApplicationListByApplicationCategoryAndUserIdsForSupportServices(
            @Param("applicationCategories") List<String> applicationCategories,
            @Param("categoryId") Long categoryId,
            @Param("applicationNumber") String applicationNumber,
            @Param("appIds") List<Long> appIds,
            @Param("customerId") Long customerId,
            @Param("appStatus") List<String> appStatus,
            @Param("applicationId") Long applicationId,
            List<ApplicationCustomerType> customerTypes,
            Pageable pageable
            );


        @Query(value = """
            SELECT  distinct new gov.saip.applicationservice.common.dto.ApplicationListDto(ac.customerCode,a.id,a.applicationNumber,a.titleAr,a.titleEn,a.filingDate,
            a.applicationStatus.ipsStatusDescEnExternal,a.applicationStatus.ipsStatusDescArExternal,a.applicationStatus.code,category.saipCode,a.grantNumber,a.applicationRequestNumber
            ,a.createdDate,a.modifiedDate,a.partialApplication)
            FROM ApplicationInfo a
            JOIN a.category category 
            JOIN a.applicationCustomers ac 
            JOIN a.applicationStatus  
            WHERE (:customerId is null or ac.customerId = :customerId)
            AND (:applicationCategory IS NULL or a.category.applicationCategoryDescEn = :applicationCategory or a.category.saipCode = :applicationCategory)
        """
    )
    Page<ApplicationListDto> OptimizedApplicationFiltering(
            @Param("applicationCategory") String applicationCategory,

            @Param("customerId") Long customerId,

            Pageable pageable
            );




    @Query(value = """
            SELECT  distinct new gov.saip.applicationservice.common.dto.ApplicationListDto(ac.customerCode,a.id,a.applicationNumber,a.titleAr,a.titleEn,a.filingDate,
            a.applicationStatus.ipsStatusDescEnExternal,a.applicationStatus.ipsStatusDescArExternal,a.applicationStatus.code,category.saipCode,a.grantNumber,a.applicationRequestNumber
            ,a.createdDate,a.modifiedDate,a.partialApplication)
            FROM ApplicationInfo a
            JOIN a.category category 
            JOIN a.applicationCustomers ac 
            JOIN a.applicationStatus  
            LEFT JOIN FETCH  ApplicationRelevantType art on art.applicationInfo.id = a.id 
            LEFT JOIN FETCH  ApplicationRelevant ar on ar.id = art.applicationRelevant.id
            LEFT JOIN FETCH CertificateRequest cr on cr.applicationInfo.id = a.id
            WHERE (COALESCE(:agentIds)  IS NULL OR ac.customerId  in (:agentIds) )
            and (DATE(:startDate) is null or (  DATE(a.filingDate) >= DATE(:startDate)) )
            and (DATE(:endDate) is null or ( DATE(a.filingDate) <= DATE(:endDate)) )
            AND (COALESCE(:statusesIds)  IS NULL OR a.applicationStatus.id  in (:statusesIds) )
            AND (:query is null or a.titleAr like concat('%', :query, '%')  or UPPER(a.titleEn) like concat('%', UPPER(:query), '%') or a.applicationNumber like concat('%', :query, '%')  or cr.requestNumber like concat('%', :query, '%') or a.applicationRequestNumber like concat('%', :query, '%') OR CAST(a.id AS string) LIKE concat('%', :query, '%') )
            AND (:applicationNumber IS NULL OR a.applicationNumber = :applicationNumber or a.idOld = :applicationNumber or a.grantNumber = :applicationNumber or a.applicationRequestNumber = :applicationNumber OR CAST(a.id AS string) = :applicationNumber or cr.requestNumber = :applicationNumber )
            AND (:applicationTitle IS NULL OR UPPER(a.titleAr ) like concat('%', :applicationTitle, '%') OR  UPPER(a.titleEn ) like concat('%', :applicationTitle, '%') )
            AND (:applicantType IS NULL OR ac.customerType = :applicantType )
            AND (:status IS NULL OR a.applicationStatus.code = :status)
            AND ((:applicantName IS NULL OR UPPER(a.ownerNameAr ) like concat('%', :applicantName, '%') OR  UPPER(a.ownerNameEn ) like concat('%', :applicantName, '%') )
            OR (:applicantName IS NULL OR UPPER(ar.fullNameAr) like concat('%', :applicantName, '%') OR UPPER(ar.fullNameEn) like concat('%', :applicantName, '%') ))
            AND (:applicationCategory IS NULL or a.category.applicationCategoryDescEn = :applicationCategory or a.category.saipCode = :applicationCategory)
        """
    )
    Page<ApplicationListDto> OptimizedApplicationAdvancedFiltering(
            @Param("applicationCategory") String applicationCategory,
            @Param("query") String query,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("applicationNumber") String applicationNumber,
            @Param("status") String status,
            @Param("applicantType") ApplicationCustomerType applicantType,
            @Param("applicantName") String applicantName,
            @Param("applicationTitle") String applicationTitle,
            @Param("agentIds") List<Long> agentIds,
            @Param("statusesIds") List<Long> statusesIds,
            Pageable pageable
    );



    @Query("""
        select  ai.processRequestId
        from ApplicationInfo ai
        where
        (UPPER(:query) is null or ai.titleAr like concat('%', :query, '%')  or UPPER(ai.titleEn) like concat('%', UPPER(:query), '%') or ai.applicationNumber like concat('%', :query, '%') or ai.applicationRequestNumber like concat('%', :query, '%')) and 
        (cast(:fromFilingDate as date) is null or DATE(ai.filingDate) >= :fromFilingDate) and
        (cast(:toFilingDate as date) is null or DATE(ai.filingDate) <= :toFilingDate) and
        (:applicationNumber is null or ai.applicationNumber = :applicationNumber or ai.applicationRequestNumber = :applicationNumber)
""")
    List<Long> getApplicationProcessInstanceIds(
                                                  @Param("query") String query,
                                                  @Param("fromFilingDate") LocalDate fromFilingDate,
                                                  @Param("toFilingDate") LocalDate toFilingDate,
                                                  @Param("applicationNumber") String applicationNumber);

    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
    @Query(value = "SELECT  distinct a FROM ApplicationInfo a" +
            " JOIN a.applicationRelevantTypes b" +
            " join a.category category" +
            " JOIN a.applicationCustomers ac " +
            " WHERE (" +
            " :customerId is null or ac.customerId = :customerId " +
            ")" +
            "AND(COALESCE(:appIds) is null or a.id NOT in (:appIds))"+
            " AND (COALESCE (:applicationCategories) IS NULL or a.category.applicationCategoryDescEn IN (:applicationCategories))" +
            " AND (:categoryId is null or category.id =:categoryId)" +
            " AND (:applicationNumber is null or a.applicationNumber = :applicationNumber or a.idOld = :applicationNumber OR a.grantNumber = :applicationNumber OR a.applicationRequestNumber = :applicationNumber) " +
            " AND (:applicationId is null or a.id = :applicationId ) " +
            "AND (COALESCE (:appStatus) IS NULL or a.applicationStatus.code IN (:appStatus))" +

            "")
        Page<ApplicationInfo> getApplicationListByApplicationForRenewalFeesSupportServices(
            @Param("applicationCategories") List<String> applicationCategories
            , @Param("categoryId") Long categoryId,
            @Param("applicationNumber") String applicationNumber
            ,@Param("appIds") List<Long> appId,
            @Param("customerId") Long customerId,
            @Param("appStatus")List<String> appStatus,
            @Param("applicationId") Long applicationId,

            Pageable pageable);

    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
    @Query(value = "SELECT  distinct a FROM ApplicationInfo a" +
            " JOIN a.applicationRelevantTypes b" +
            " join a.category category" +
            " JOIN a.applicationCustomers ac" +
            " WHERE (" +
            ":customerId is null or ac.customerId != :customerId" +
            ")" +
            "AND(:appId is null or a.id = :appId)"+
            " AND (COALESCE (:applicationCategories) IS NULL or a.category.applicationCategoryDescEn IN (:applicationCategories))" +  // should be change to saipCode
            " AND (:categoryId is null or category.id =:categoryId)" +
            " AND (:applicationNumber is null or a.applicationNumber = :applicationNumber or a.idOld = :applicationNumber OR a.grantNumber = :applicationNumber OR a.applicationRequestNumber = :applicationNumber)" +
            " AND (:applicationId is null or a.id = :applicationId ) " +
            "AND (COALESCE (:appStatus) IS NULL or a.applicationStatus.code IN (:appStatus))"
    )
    Page<ApplicationInfo> getApplicationListByApplicationCategoryAndUserIds(
            @Param("applicationCategories") List<String> applicationCategories
            , @Param("categoryId") Long categoryId,
            @Param("applicationNumber") String applicationNumber
            ,@Param("appId") Long appId,
            @Param("customerId") Long customerId,
            @Param("appStatus")List<String> appStatus ,
            @Param("applicationId")Long applicationId ,
            Pageable pageable);


    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
    @Query(value = "SELECT a FROM ApplicationInfo a  JOIN  a.applicationRelevantTypes b  WHERE a.id = :applicationId AND b.type = 'Applicant_MAIN'")
    ApplicationInfo getApplicationInfoWithMainApplicant(@Param("applicationId") Long applicationId);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) from application.applications_info a  WHERE a.filing_date BETWEEN :startDate AND :endDate AND a.lk_category_id = :category_id AND a.application_number IS NOT NULL ")
    Long countByFilingDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("category_id") Long categoryId);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) from application.applications_info a  WHERE  a.lk_category_id = :category_id AND a.grant_number IS NOT NULL ")
    Long countByGrantNumber(@Param("category_id") Long categoryId);

    @Query("""
    select ai.id
    from ApplicationInfo ai
    where ai.applicationNumber = :applicationNumber or ai.applicationRequestNumber = :applicationNumber
""")
    Long getApplicationInfoIdByApplicationNumber(@Param("applicationNumber") String applicationNumber);

    @Query(value = "select ai.id, ai.title_ar as titleAr , ai.title_en as titleEn , ai.application_number as applicationNumber " +
            ", r.id as requestId\n" +
            "from application.applications_info ai\n" +
            "join efiling.requests r on ai.id = r.row_id\n" +
            "where ai.partial_application_number = :partialNumber and ai.partial_application = true and ai.is_deleted ='0'", nativeQuery = true)
    List<PartialApplicationInfoProjection> getApplicationByApplicationPartialNumber(@Param("partialNumber") String partialNumber);

    @Query(value = "select ai.id " +
            "from ApplicationInfo ai\n" +
            "where (ai.applicationNumber = :applicationNumber OR ai.idOld = :applicationNumber OR ai.id = :applicationId OR ai.grantNumber = :applicationNumber OR ai.applicationRequestNumber = :applicationNumber) and ai.partialApplication = false")
    Long getApplicationByApplicationNumber(@Param("applicationNumber") String applicationNumber,@Param("applicationId") Long applicationId);

    @Query("select ai from ApplicationInfo ai where lower(ai.applicationNumber) = lower(:applicationNumber) OR lower(ai.grantNumber) = lower(:applicationNumber) OR lower(ai.idOld) = lower(:applicationNumber) OR lower(ai.applicationRequestNumber) = lower(:applicationNumber) OR ai.id = :applicationId ")
    ApplicationInfo getApplicationInfoByApplicationNumber(@Param("applicationNumber") String applicationNumber,@Param("applicationId") Long applicationId);



    @Query("select  appInfo  " +
            " from ApplicationInfo appInfo" +
            " where appInfo.id in (:ids) ")
    List<ApplicationInfo> getApplicationClassification(@Param("ids") List<Long> ids);

    @Query("SELECT appInfo.applicationNumber AS applicationNumber, appInfo.nationalSecurity AS nationalSecurity, " +
            " appInfo.classificationNotes AS classificationNotes, classUnit.id  AS classificationUnitId," +
            " niceClass AS niceClassifications" +
            " FROM ApplicationInfo appInfo" +
            " LEFT JOIN appInfo.classificationUnit classUnit" +
            " LEFT JOIN appInfo.niceClassifications niceClass" +
            " where appInfo.id = :id ")
    ApplicationClassificationProjection getApplicationClassificationById(@Param("id") Long id);


    @Query("select  appInfo  " +
            " from ApplicationInfo appInfo" +
            " where appInfo.id in (:ids) ")
    List<ApplicationInfo> getApplicationStatusByApplicationIds(@Param("ids") List<Long> ids);

    @Query("select u.id from ApplicationInfo appInfo " +
            "join appInfo.niceClassifications niceClass " +
            "join niceClass.classification c " +
            "join c.unit u " +
            "where appInfo.id = :id"
    )
    List<Long> getApplicationClassificationUnitIdsByAppId(@Param("id") Long id);


    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
    @Query(value = "SELECT a FROM ApplicationInfo a " +
            "join a.applicationRelevantTypes relevant " +
            "JOIN a.agent agent " +
            "join a.category category " +
            "join a.applicationStatus appStatus " +
            "WHERE agent.customerId = :customerId " +
            "and agent.status = :status " +
            "and relevant.customerCode = :customerCode and relevant.type = 'Applicant_MAIN' " +
            "AND ( (:categoryId is null or category.id =:categoryId) " +
            "and (:applicationNumber is null or a.applicationNumber = :applicationNumber or a.idOld = :applicationNumber OR a.grantNumber = :applicationNumber) " +
            "AND (:applicationId is null or a.id = :applicationId )" +
            "and (:appStatus is null or appStatus.code = :appStatus) )" +
            "and appStatus.code not in (:notEligibleStatus)" +
            "and category.saipCode in (:cats) "
    )
    Page<ApplicationInfo> listApplicationsAgentId(@Param("categoryId") Long categoryId,
                                                  @Param("applicationNumber") String applicationNumber,
                                                  @Param("appStatus") String appStatus,
                                                  @Param("customerId") Long customerId,
                                                  @Param("notEligibleStatus") List<String> notEligibleStatus,
                                                  @Param("customerCode") String customerCode,
                                                  @Param("status") ApplicationAgentStatus status,
                                                  @Param("cats") List<String> cats,
                                                  @Param("applicationId") Long applicationId,
                                                  Pageable pageable);


    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
    @Query(value = "SELECT a FROM ApplicationInfo a " +
            "join a.applicationRelevantTypes relevant " +
            "join a.category category " +
            "join a.applicationStatus appStatus " +
            "where relevant.customerCode = :customerCode and relevant.type = 'Applicant_MAIN'  and " +
            " NOT EXISTS (" +
            "select 1 from ApplicationAgent ag where ag.application = a and ag.status = 'ACTIVE' " +
            ") " +
            "AND ( (:categoryId is null or category.id =:categoryId) " +
            "and (:applicationNumber is null or a.applicationNumber = :applicationNumber or a.idOld = :applicationNumber OR a.grantNumber = :applicationNumber) " +
            "AND (:applicationId is null or a.id = :applicationId )" +
            "and (:appStatus is null or appStatus.code = :appStatus) ) " +
            "and appStatus.code not in (:notEligibleStatus)" +
            "and a.category.saipCode in (:cats) "
    )
    Page<ApplicationInfo> getNotAssignedToAgentApplications(
            @Param("categoryId") Long categoryId,
            @Param("applicationNumber") String applicationNumber,
            @Param("appStatus") String appStatus,
            @Param("notEligibleStatus") List<String> notEligibleStatus,
            @Param("customerCode") String customerCode,
            @Param("cats") List<String> cats,
            @Param("applicationId") Long applicationId,
            Pageable pageable);



    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
    @Query(value = "SELECT a FROM ApplicationInfo a " +
            "join a.applicationRelevantTypes relevant " +
            "join a.category category " +
            "join a.applicationStatus appStatus " +
            "where relevant.customerCode = :customerCode and relevant.type = 'Applicant_MAIN' and " +
            " EXISTS (" +
            "select 1 from ApplicationAgent ag where ag.application = a and ag.status = 'ACTIVE' " +
            ") " +
            "AND ( (:categoryId is null or category.id =:categoryId) " +
            "and (:applicationNumber is null or a.applicationNumber = :applicationNumber or a.idOld = :applicationNumber OR a.grantNumber = :applicationNumber) " +
            "AND (:applicationId is null or a.id = :applicationId )" +
            "and (:appStatus is null or appStatus.code = :appStatus) ) " +
            "and appStatus.code not in (:notEligibleStatus)" +
            "and a.category.saipCode in (:cats) "
    )
    Page<ApplicationInfo> getAssignedToAgentApplications(
            @Param("categoryId") Long categoryId,
            @Param("applicationNumber") String applicationNumber,
            @Param("appStatus") String appStatus,
            @Param("notEligibleStatus") List<String> notEligibleStatus,
            @Param("customerCode") String customerCode,
            @Param("cats") List<String> cats,
            @Param("applicationId") Long applicationId,
            Pageable pageable);


    @EntityGraph(attributePaths = {"applicationRelevantTypes"})
    @Query(value = "SELECT a FROM ApplicationInfo a " +
            "join a.applicationRelevantTypes relevant " +
            "JOIN a.agent agent " +
            "where relevant.customerCode = :customerCode and  relevant.type = 'Applicant_MAIN' " +
            "and agent.customerId = :agentId and agent.status = :status "
    )
    List<ApplicationInfo> getAllApplicationsByUserIdAndAgentId(@Param("agentId") Long agentId, @Param("customerCode") String customerCode, @Param("status") ApplicationAgentStatus status);

    @Query(value = """
                SELECT
                  DATE(main.receptionDate) AS receptionDate,
                  main.numberOfPublications,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :approved_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS publicationsApprovedNumber,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :awaiting_modification_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS numberOfPublicationsSentForEditing,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :awaiting_action_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS publicationsAwaitingAction
                FROM (
                  SELECT DATE(b.payment_date) AS receptionDate, COUNT(*) AS numberOfPublications
                  FROM application.applications_info ai
                  JOIN payment.bill b ON b.application_id = ai.id
                  JOIN payment.bill_status bs ON bs.id = b.status_id
                  JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                  JOIN application.lk_application_status las ON las.id = ai.application_status_id
                  WHERE bs.code = :bill_status
                    AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                    AND lac.saip_code = :saip_code
                    AND (:from_date IS NULL OR DATE(b.payment_date) >= DATE(:from_date))
                    AND (:to_date IS NULL OR DATE(b.payment_date) <= DATE(:to_date))
                    AND las.code in (:allowedStatuses)
                    AND ai.is_deleted ='0'
                  GROUP BY DATE(b.payment_date)
                ) AS main
                ORDER BY receptionDate DESC
            """,
            countQuery = """
                        SELECT COUNT(DISTINCT DATE(b.payment_date))
                        FROM application.applications_info ai
                        JOIN payment.bill b ON b.application_id = ai.id
                        JOIN payment.bill_status bs ON bs.id = b.status_id
                        JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                        JOIN application.lk_application_status las ON las.id = ai.application_status_id
                        WHERE bs.code = :bill_status
                          AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                          AND lac.saip_code = :saip_code
                          AND (:from_date IS NULL OR DATE(b.payment_date) >= DATE(:from_date))
                          AND (:to_date IS NULL OR DATE(b.payment_date) <= DATE(:to_date))
                          AND las.code in (:allowedStatuses)
                          AND ai.is_deleted ='0'
                          """,
            nativeQuery = true)
    Page<PublicationProjection> getPatentPublicationsBatches(
            @Param("saip_code") String saipCode,
            @Param("bill_status") String billStatus,
            @Param("main_request_type") String mainRequestType,
            @Param("awaiting_action_status") String awaitingActionStatus,
            @Param("approved_status") String approvedStatus,
            @Param("awaiting_modification_status") String awaitingModificationStatus,
            @Param("from_date") String fromDate,
            @Param("to_date") String toDate,
            @Param("allowedStatuses") List<String> allowedStatuses,
            Pageable pageable);


    @Query(value = """
                SELECT
                  DATE(main.receptionDate) AS receptionDate,
                  main.numberOfPublications,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :approved_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS publicationsApprovedNumber,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :awaiting_modification_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS numberOfPublicationsSentForEditing,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :awaiting_action_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS publicationsAwaitingAction
                FROM (
                  SELECT DATE(b.payment_date) AS receptionDate, COUNT(*) AS numberOfPublications
                  FROM application.applications_info ai
                  JOIN application.lk_application_status las ON las.id = ai.application_status_id
                  JOIN payment.bill b ON b.application_id = ai.id
                  JOIN payment.bill_status bs ON bs.id = b.status_id
                  JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                  WHERE bs.code = :bill_status
                    AND b.main_request_type LIKE CONCAT('%', :main_request_type)
                    AND lac.saip_code = :saip_code
                    AND (:from_date IS NULL OR DATE(b.payment_date) >= DATE(:from_date))
                    AND (:to_date IS NULL OR DATE(b.payment_date) <= DATE(:to_date))
                    AND las.code in (:allowedStatuses)
                    AND ai.is_deleted ='0'
                  GROUP BY DATE(b.payment_date)
                ) AS main
                ORDER BY receptionDate DESC
            """,
            countQuery = """
                        SELECT COUNT(DISTINCT DATE(b.payment_date))
                        FROM application.applications_info ai
                        JOIN application.lk_application_status las ON las.id = ai.application_status_id
                        JOIN payment.bill b ON b.application_id = ai.id
                        JOIN payment.bill_status bs ON bs.id = b.status_id
                        JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                        WHERE bs.code = :bill_status
                          AND main_request_type LIKE CONCAT('%', :main_request_type)
                          AND lac.saip_code = :saip_code
                          AND (:from_date IS NULL OR DATE(b.payment_date) >= DATE(:from_date))
                          AND (:to_date IS NULL OR DATE(b.payment_date) <= DATE(:to_date))
                          AND las.code in (:allowedStatuses)
                          AND ai.is_deleted ='0'
                    """,
            nativeQuery = true)
    Page<PublicationProjection> getTrademarkPublicationsBatches(
            @Param("saip_code") String saipCode,
            @Param("bill_status") String billStatus,
            @Param("main_request_type") String mainRequestType,
            @Param("awaiting_action_status") String awaitingActionStatus,
            @Param("approved_status") String approvedStatus,
            @Param("awaiting_modification_status") String awaitingModificationStatus,
            @Param("from_date") String fromDate,
            @Param("to_date") String toDate,
            @Param("allowedStatuses") List<String> allowedStatuses,
            Pageable pageable);

    @Query(value = """
                SELECT
                  DATE(main.receptionDate) AS receptionDate,
                  main.numberOfPublications,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :approved_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS publicationsApprovedNumber,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :awaiting_modification_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS numberOfPublicationsSentForEditing,
                  (
                    SELECT COUNT(*)
                    FROM application.applications_info ai
                    JOIN payment.bill b ON b.application_id = ai.id
                    JOIN payment.bill_status bs ON bs.id = b.status_id
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    JOIN application.lk_application_status las ON las.id = ai.application_status_id
                    WHERE bs.code = :bill_status
                      AND main_request_type LIKE CONCAT('%', :main_request_type)
                      AND lac.saip_code = :saip_code
                      AND las.code = :awaiting_action_status
                      AND DATE(b.payment_date) = DATE(main.receptionDate)
                      AND ai.is_deleted ='0'
                  ) AS publicationsAwaitingAction
                FROM (
                  SELECT DATE(b.payment_date) AS receptionDate, COUNT(*) AS numberOfPublications
                  FROM application.applications_info ai
                  JOIN application.lk_application_status las ON las.id = ai.application_status_id
                  JOIN payment.bill b ON b.application_id = ai.id
                  JOIN payment.bill_status bs ON bs.id = b.status_id
                  JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                  WHERE bs.code = :bill_status
                    AND main_request_type LIKE CONCAT('%', :main_request_type)
                    AND lac.saip_code = :saip_code
                    AND (:from_date IS NULL OR DATE(b.payment_date) >= DATE(:from_date))
                    AND (:to_date IS NULL OR DATE(b.payment_date) <= DATE(:to_date))
                    AND las.code in (:allowedStatuses)
                    AND ai.is_deleted ='0'
                  GROUP BY DATE(b.payment_date)
                ) AS main
                ORDER BY receptionDate DESC
            """,
            countQuery = """
                        SELECT COUNT(DISTINCT DATE(b.payment_date))
                        FROM application.applications_info ai
                        JOIN application.lk_application_status las ON las.id = ai.application_status_id
                        JOIN payment.bill b ON b.application_id = ai.id
                        JOIN payment.bill_status bs ON bs.id = b.status_id
                        JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                        WHERE bs.code = :bill_status
                          AND main_request_type LIKE CONCAT('%', :main_request_type)
                          AND lac.saip_code = :saip_code
                          AND (:from_date IS NULL OR DATE(b.payment_date) >= DATE(:from_date))
                          AND (:to_date IS NULL OR DATE(b.payment_date) <= DATE(:to_date))
                          AND las.code in (:allowedStatuses)
                          AND ai.is_deleted ='0'
                    """,
            nativeQuery = true)
    Page<PublicationProjection> getIndustrialPublicationsBatches(
            @Param("saip_code") String saipCode,
            @Param("bill_status") String billStatus,
            @Param("main_request_type") String mainRequestType,
            @Param("awaiting_action_status") String awaitingActionStatus,
            @Param("approved_status") String approvedStatus,
            @Param("awaiting_modification_status") String awaitingModificationStatus,
            @Param("from_date") String fromDate,
            @Param("to_date") String toDate,
            @Param("allowedStatuses") List<String> allowedStatuses,
            Pageable pageable);

    @Query(value = """
            SELECT ai.id applicationId, ai.filing_date depositDate, ai.application_number applicationNumber,
            las.ips_status_desc_ar ipStatusDescAr, las.ips_status_desc_en ipStatusDescEn, las.code as statusCode,\s
            ai.title_ar AS applicationTitleAr , ai.title_en AS applicationTitleEn,\s
            lmt.name_ar tmTypeAr, lmt.name_en tmTypeEn, pi.issuing_date AS gazettePublicationDate,
            ap.publication_date AS publicationDate, lttd.code AS tmTagCode\s
            FROM application.applications_info ai\s
            JOIN application.lk_application_status las ON las.id = ai.application_status_id
            JOIN payment.bill b ON b.application_id = ai.id\s
            JOIN payment.bill_status bs ON bs.id = b.status_id\s
            JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
            LEFT JOIN application.application_publication ap ON ap.application_info_id = ai.id
            LEFT JOIN application.publication_issue_application_publication piap ON piap.application_publication_id = ap.id
            LEFT JOIN application.publication_issue pi ON pi.id = piap.publication_issue_id
            LEFT JOIN application.trademark_details tm ON tm.application_id = ai.id\s
            LEFT JOIN application.lk_mark_types lmt ON lmt.id = tm.mark_type_id
            LEFT JOIN application.lk_tag_type_desc lttd ON lttd.id = tm.tag_type_desc_id
            WHERE bs.code = :bill_status\s
            AND main_request_type LIKE CONCAT('%', :main_request_type)
            AND lac.saip_code  = :saip_code\s
            AND (:date IS NULL OR DATE(b.payment_date) = DATE(:date))
            AND las.code in (:allowedStatuses)
            AND ai.is_deleted ='0'
            """,
            countQuery = """
                    SELECT COUNT(ai.id) applicationId
                    FROM application.applications_info ai\s
                    JOIN application.lk_application_status las on ai.application_status_id = las.id
                    JOIN payment.bill b ON b.application_id = ai.id\s
                    JOIN payment.bill_status bs ON bs.id = b.status_id\s
                    JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
                    WHERE bs.code = :bill_status\s
                    AND main_request_type LIKE CONCAT('%', :main_request_type)
                    AND lac.saip_code  = :saip_code\s
                    AND (:date IS NULL OR DATE(b.payment_date) = DATE(:date))
                    AND las.code in (:allowedStatuses)
                    AND ai.is_deleted ='0'
                    """, nativeQuery = true)
    Page<PublicationBatchViewProjection> viewPublications(@Param("saip_code") String saipCode,
                                                          @Param("bill_status") String billStatus,
                                                          @Param("main_request_type") String mainRequestType,
                                                          @Param("date") String date,
                                                          @Param("allowedStatuses") List<String> allowedStatuses,
                                                          Pageable pageable);

    @Query(value = """
            SELECT DISTINCT pi.issuingDate AS gazettePublicationDate, ap.publicationDate AS publicationDate,
            ai.id AS applicationId, ai.filingDate AS depositDate, ai.applicationNumber AS applicationNumber,
            ai.titleAr AS applicationTitleAr , ai.titleEn AS applicationTitleEn,\s
            mt.nameAr AS tmTypeAr, mt.nameEn AS tmTypeEn, art.id AS relevantTypeId, ap.publicationDate AS publicationDateTime,
            art AS applicationRelevantType, lttd.code AS tmTagCode, pt.nameEn AS publicationTypeEn,
            pt.nameAr As publicationTypeAr, pt.code as publicationCode, appStatus AS applicationStatus, ap.id AS applicationPublicationId,
            ai.ownerNameAr AS ownerNameAr, ai.ownerNameEn AS ownerNameEn, ai.applicationRequestNumber as applicationRequestNumber
            FROM ApplicationPublication ap
            LEFT JOIN ap.applicationInfo ai
            LEFT JOIN ai.category AS category
            LEFT JOIN ai.applicationStatus AS appStatus
            LEFT JOIN ai.applicationRelevantTypes art 
            LEFT JOIN ApplicationAgent aa ON aa.application.id = ai.id
            LEFT JOIN ap.publicationType pt
            LEFT JOIN PublicationIssueApplicationPublication piap on piap.applicationPublication.id = ap.id
            LEFT JOIN piap.publicationIssue pi
            LEFT JOIN TrademarkDetail tm ON tm.applicationId = ai.id
            LEFT JOIN tm.markType mt
            LEFT JOIN tm.tagTypeDesc lttd
            WHERE category.saipCode = :saip_code
            AND (art.type IS NULL OR art.type  = :relevant_type)
            AND (:publication_issue_id IS NULL OR pi.id = :publication_issue_id)
            AND (DATE(:from_date) IS NULL OR DATE(pi.issuingDate) >= DATE(:from_date))
            AND (DATE(:to_date) IS NULL OR DATE(pi.issuingDate) <= DATE(:to_date))
            AND (DATE(:from_publication_date) IS NULL OR DATE(ap.publicationDate) >= DATE(:from_publication_date))
            AND (DATE(:to_publication_date) IS NULL OR DATE(ap.publicationDate) <= DATE(:to_publication_date))
            AND (:year IS NULL OR date_part('year', DATE(pi.issuingDate)) = :year)
            AND (:hijri_year IS NULL OR pi.issuingDateHijri LIKE CONCAT('%',:hijri_year, '%'))
            AND (:publication_type IS NULL OR pt.code = :publication_type)
            AND (:application_number IS NULL OR ai.applicationNumber = :application_number or ai.idOld = :application_number OR ai.grantNumber = :application_number OR ai.applicationRequestNumber = :application_number)
            AND (:applicationId is null or ai.id = :applicationId )
            AND (COALESCE (:customers_ids) IS NULL OR (aa.customerId in :customers_ids AND aa.status = :agent_status))
            AND (COALESCE (:customers_codes) IS NULL OR art.customerCode in :customers_codes)
            AND (DATE(:from_filing_date) IS NULL OR DATE(ai.filingDate) >= DATE(:from_filing_date))
            AND (DATE(:to_filing_date) IS NULL OR DATE(ai.filingDate) <= DATE(:to_filing_date))
            AND (:filing_year IS NULL OR date_part('year', DATE(ai.filingDate)) = :filing_year)
            AND (:filing_year_hijri IS NULL OR ai.filingDateHijri LIKE CONCAT('%',:filing_year_hijri, '%'))
            AND (:searchField IS NULL OR
                 ai.applicationNumber LIKE CONCAT('%',:searchField, '%') OR 
                 ai.grantNumber LIKE CONCAT('%',:searchField, '%') OR 
                 ai.ownerNameAr LIKE CONCAT('%',:searchField, '%') OR 
                 ai.ownerNameEn LIKE CONCAT('%',:searchField, '%') OR 
                 ai.applicationRequestNumber LIKE CONCAT('%',:searchField, '%') OR 
                 ai.titleAr LIKE CONCAT('%',:searchField, '%') OR 
                 ai.titleEn LIKE CONCAT('%',:searchField, '%')
                )
            AND(:is_published IS NULL OR ap.isPublished = :is_published)
            """)
    Page<PublicationBatchViewProjection> listGazetteOrPublicationsForTrademark(@Param("saip_code") String saipCode,
                                                                               @Param("publication_issue_id") Long publicationIssueId,
                                                                               @Param("relevant_type") ApplicationRelevantEnum relevantType,
                                                                               @Param("agent_status") ApplicationAgentStatus agentStatus,
                                                                               @Param("year") Integer year,
                                                                               @Param("from_date") LocalDate fromDate,
                                                                               @Param("to_date") LocalDate toDate,
                                                                               @Param("from_publication_date") LocalDate fromPublicationDate,
                                                                               @Param("to_publication_date") LocalDate toPublicationDate,
                                                                               @Param("hijri_year") String hijriYear,
                                                                               @Param("publication_type") String publicationType,
                                                                               @Param("application_number") String applicationNumber,
                                                                               @Param("customers_ids") List<Long> customersIds,
                                                                               @Param("customers_codes") List<String> customersCodes,
                                                                               @Param("from_filing_date") LocalDate fromFilingDate,
                                                                               @Param("to_filing_date") LocalDate toFilingDate,
                                                                               @Param("filing_year") Integer filingYear,
                                                                               @Param("filing_year_hijri") String filingYearHijri,
                                                                               @Param("applicationId") Long applicationId,
                                                                               @Param("searchField") String searchField,
                                                                               @Param("is_published") Boolean isPublished,
                                                                               Pageable pageable);

    @Query(value = """
            SELECT DISTINCT pi.issuingDate AS gazettePublicationDate, ap.publicationDate AS publicationDate,
            ai AS applicationInfo, pt.nameEn AS publicationTypeEn, ap.publicationDate AS publicationDateTime,
            pt.nameAr As publicationTypeAr, pt.code as publicationCode, ai.filingDate AS depositDate,
            ap.id AS applicationPublicationId, ai.applicationRequestNumber as applicationRequestNumber
            FROM ApplicationPublication ap
            LEFT JOIN PublicationIssueApplicationPublication piap on piap.applicationPublication.id = ap.id
            LEFT JOIN piap.publicationIssue pi
            JOIN ap.applicationInfo ai
            JOIN ai.category AS category
            JOIN ai.applicationStatus AS appStatus
            LEFT JOIN ai.applicationRelevantTypes art
            LEFT JOIN ApplicationCustomer ac ON ac.application.id = ai.id
            LEFT JOIN ap.publicationType pt
            WHERE category.saipCode = :saip_code
            AND (:publication_issue_id IS NULL OR pi.id = :publication_issue_id)
            AND (DATE(:from_date) IS NULL OR DATE(pi.issuingDate) >= DATE(:from_date))
            AND (DATE(:to_date) IS NULL OR DATE(pi.issuingDate) <= DATE(:to_date))
            AND (DATE(:from_publication_date) IS NULL OR DATE(ap.publicationDate) >= DATE(:from_publication_date))
            AND (DATE(:to_publication_date) IS NULL OR DATE(ap.publicationDate) <= DATE(:to_publication_date))
            AND (:year IS NULL OR date_part('year', DATE(pi.issuingDate)) = :year)
            AND (:hijri_year IS NULL OR pi.issuingDateHijri LIKE CONCAT('%',:hijri_year, '%'))
            AND (:publication_type IS NULL OR pt.code = :publication_type)
            AND (:application_number IS NULL OR ai.applicationNumber = :application_number or ai.idOld = :application_number OR ai.grantNumber = :application_number OR ai.applicationRequestNumber = :application_number)
            AND (:applicationId is null or ai.id = :applicationId )
            AND (COALESCE (:customers_ids) IS NULL OR (ac.customerId in :customers_ids AND ac.customerType = :agent_type))
            AND (COALESCE (:customers_codes) IS NULL OR ac.customerCode in :customers_codes)
            AND (DATE(:from_filing_date) IS NULL OR DATE(ai.filingDate) >= DATE(:from_filing_date))
            AND (DATE(:to_filing_date) IS NULL OR DATE(ai.filingDate) <= DATE(:to_filing_date))
            AND (:searchField IS NULL OR CONCAT('%', ai.applicationNumber, COALESCE(ai.grantNumber, ''),
             COALESCE(ai.titleAr, ''), COALESCE(ai.titleEn, ''), COALESCE(CONCAT(pi.issueNumber, ''), ''), '%')
                  LIKE (CONCAT('%', :searchField, '%')))
           AND(:is_published IS NULL OR ap.isPublished = :is_published)
            """)
    Page<PublicationBatchViewProjection> listGazetteOrPublicationsForPatent(@Param("saip_code") String saipCode,
                                                                            @Param("publication_issue_id") Long publicationIssueId,
                                                                            @Param("agent_type") ApplicationCustomerType agentType,
                                                                            @Param("year") Integer year,
                                                                            @Param("from_date") LocalDate fromDate,
                                                                            @Param("to_date") LocalDate toDate,
                                                                            @Param("from_publication_date") LocalDate fromPublicationDate,
                                                                            @Param("to_publication_date") LocalDate toPublicationDate,
                                                                            @Param("hijri_year") String hijriYear,
                                                                            @Param("publication_type") String publicationType,
                                                                            @Param("application_number") String applicationNumber,
                                                                            @Param("customers_ids") List<Long> customersIds,
                                                                            @Param("customers_codes") List<String> customersCodes,
                                                                            @Param("from_filing_date") LocalDate fromFilingDate,
                                                                            @Param("to_filing_date") LocalDate toFilingDate,
                                                                            @Param("applicationId") Long applicationId,
                                                                            @Param("searchField") String searchField,
                                                                            @Param("is_published") Boolean isPublished,
                                                                            Pageable pageable);

    @EntityGraph(attributePaths = {"applicationCustomers"})
    @Query(value = """
            SELECT DISTINCT pi.issuingDate AS gazettePublicationDate, ap.publicationDate AS publicationDate,
            ai AS applicationInfo, pt.nameEn AS publicationTypeEn,
            pt.nameAr As publicationTypeAr, pt.code as publicationCode, ai.filingDate AS depositDate
            , ap.id AS applicationPublicationId, ap.publicationDate AS publicationDateTime, ai.applicationRequestNumber as applicationRequestNumber
            FROM ApplicationPublication ap
            LEFT JOIN ap.applicationInfo ai
            LEFT JOIN ai.category AS category
            LEFT JOIN ai.applicationStatus AS appStatus
            LEFT JOIN ai.applicationRelevantTypes art
            LEFT JOIN ApplicationAgent aa ON aa.application.id = ai.id
            LEFT JOIN ap.publicationType pt
            LEFT JOIN PublicationIssueApplicationPublication piap on piap.applicationPublication.id = ap.id
            LEFT JOIN piap.publicationIssue pi
            LEFT JOIN TrademarkDetail tm ON tm.applicationId = ai.id
            LEFT JOIN tm.markType mt
            LEFT JOIN tm.tagTypeDesc lttd
            WHERE category.saipCode = :saip_code
            AND (:publication_issue_id IS NULL OR pi.id = :publication_issue_id)
            AND (DATE(:from_date) IS NULL OR DATE(pi.issuingDate) >= DATE(:from_date))
            AND (DATE(:to_date) IS NULL OR DATE(pi.issuingDate) <= DATE(:to_date))
            AND (DATE(:from_publication_date) IS NULL OR DATE(ap.publicationDate) >= DATE(:from_publication_date))
            AND (DATE(:to_publication_date) IS NULL OR DATE(ap.publicationDate) <= DATE(:to_publication_date))
            AND (:year IS NULL OR date_part('year', DATE(pi.issuingDate)) = :year)
            AND (:hijri_year IS NULL OR pi.issuingDateHijri LIKE CONCAT('%',:hijri_year, '%'))
            AND (:publication_type IS NULL OR pt.code = :publication_type)
            AND (:application_number IS NULL OR ai.applicationNumber = :application_number or ai.idOld = :application_number OR ai.grantNumber = :application_number)
            AND (:applicationId is null or ai.id = :applicationId )
            AND (COALESCE (:customers_ids) IS NULL OR (aa.customerId in :customers_ids AND aa.status = :agent_status))
            AND (COALESCE (:customers_codes) IS NULL OR art.customerCode in :customers_codes)
            AND (DATE(:from_filing_date) IS NULL OR DATE(ai.filingDate) >= DATE(:from_filing_date))
            AND (DATE(:to_filing_date) IS NULL OR DATE(ai.filingDate) <= DATE(:to_filing_date))
            AND (:searchField IS NULL OR CONCAT('%', ai.applicationNumber, COALESCE(ai.grantNumber, ''),
             COALESCE(ai.titleAr, ''), COALESCE(ai.titleEn, ''), COALESCE(CONCAT(pi.issueNumber, ''), ''), '%')
                              LIKE (CONCAT('%', :searchField, '%')))
            AND(:is_published IS NULL OR ap.isPublished = :is_published)
            """)
    Page<PublicationBatchViewProjection> listGazetteOrPublicationsForIndustrial(@Param("saip_code") String saipCode,
                                                                                @Param("publication_issue_id") Long publicationIssueId,
                                                                                @Param("is_published") Boolean isPublished,
                                                                                @Param("agent_status") ApplicationAgentStatus agentStatus,
                                                                                @Param("year") Integer year,
                                                                                @Param("from_date") LocalDate fromDate,
                                                                                @Param("to_date") LocalDate toDate,
                                                                                @Param("from_publication_date") LocalDate fromPublicationDate,
                                                                                @Param("to_publication_date") LocalDate toPublicationDate,
                                                                                @Param("hijri_year") String hijriYear,
                                                                                @Param("publication_type") String publicationType,
                                                                                @Param("application_number") String applicationNumber,
                                                                                @Param("customers_ids") List<Long> customersIds,
                                                                                @Param("customers_codes") List<String> customersCodes,
                                                                                @Param("from_filing_date") LocalDate fromFilingDate,
                                                                                @Param("to_filing_date") LocalDate toFilingDate,
                                                                                @Param("applicationId") Long applicationId,
                                                                                @Param("searchField") String searchField,
                                                                                Pageable pageable);

    @Query(value = """
            SELECT ai.id
            FROM ApplicationInfo ai
            WHERE (ai.applicationNumber = :applicationNumber OR ai.idOld = :applicationNumber OR ai.grantNumber = :applicationNumber OR ai.applicationRequestNumber = :applicationNumber)
            AND (:applicationId is null or ai.id = :applicationId )
            AND ai.category.saipCode = :category
            AND ai.partialApplication = false
            AND ai.applicationStatus.code in (:appStatus)
            """)
    Long countByApplicationNumber (@Param("applicationNumber") String applicationNumber,
                                   @Param("category") String category,
                                   @Param("appStatus") List<String> appStatus,
                                   @Param("applicationId") Long applicationId
                                   );


    //    @Query(value = """
//            SELECT ai.id -- We only care about the ids
//            FROM application.applications_info ai
//                     JOIN payment.bill b ON b.application_id = ai.id
//                     JOIN payment.bill_status bs ON bs.id = b.status_id
//                     JOIN application.lk_application_category lac ON lac.id = ai.lk_category_id
//                     JOIN application.lk_application_status las ON las.id = ai.application_status_id
//                     RIGHT OUTER JOIN application.publication_issue_application pia ON pia.application_info_id = ai.id
//            WHERE bs.code = 'PAID'                                               -- bill is paid
//              AND main_request_type LIKE CONCAT('%', 'TRADEMARK_FILE_NEW_APPLICATION') -- bill that should be paid
//              AND lac.saip_code = 'TRADEMARK'                                    -- is a trademark
//              AND las.code = 'ACCEPTANCE'                                        -- is approved to be published in gazzette
//              AND pia.application_info_id IS NULL; -- Is not yet in any issue
//                          """, nativeQuery = true)
    default List<ApplicationInfo> getApplicationsThatAreReadyToBeAddedToAPublicationIssue(ApplicationCategoryEnum applicationCategoryEnum) {
        // TODO[Fawzy] Implement
        // TODO[Fawzy] create the query and write integration test for it
        return List.of();
    };

    @Query("SELECT category.saipCode FROM ApplicationInfo ai" +
            " JOIN ai.category category where ai.id = :appId ")
    String findCategoryByApplicationId(@Param("appId") Long appId);

    @Query("SELECT ai FROM ApplicationInfo ai" +
            " WHERE ai.id in (:ids)")
    List<ApplicationInfo> findByIds(@Param("ids") List<Long> applicationIds);

    @Query("SELECT distinct new gov.saip.applicationservice.common.dto.ApplicationEqmDto(" +
            "ai.id, ai.applicationNumber, ai.titleAr, ai.titleEn, " +
            "ai.category, ai.applicationStatus) " +
            "FROM ApplicationInfo ai " +
            "left join ApplicationUser appUser on ai.id = appUser.applicationInfo.id " +
            "JOIN ai.applicationStatus appStatus " +
            "LEFT JOIN TaskEqm te ON te.applicationInfo.id = ai.id " +
            "LEFT JOIN te.taskEqmStatus lts " +
            "WHERE (COALESCE(:appStatuses) IS NULL OR (appStatus.code in (:appStatuses) and te.id is null)) " +
            "AND (COALESCE(:eqmStatuses) IS NULL OR lts.code in (:eqmStatuses)) " +
            "AND (:userName is null or appUser.userName = :userName) " +
            "AND(:search IS NULL " +
            "OR CONCAT(ai.applicationNumber, ai.titleAr, ai.titleEn, " +
            "              appStatus.ipsStatusDescAr, appStatus.ipsStatusDescEn, " +
            "              ai.category.applicationCategoryDescAr, ai.category.applicationCategoryDescEn) " +
            "       LIKE (CONCAT('%', :search, '%'))) " +
            "AND (DATE(:fromDate) IS NULL OR DATE(ai.filingDate) >= DATE(:fromDate)) " +
            "AND (DATE(:toDate) IS NULL OR DATE(ai.filingDate) <= DATE(:toDate))")
    Page<ApplicationEqmDto> listEqmApplicationsFor(@Param("appStatuses") List<String> appStatuses,
                                                   @Param("eqmStatuses") List<String> eqmStatuses,
                                                   @Param("search") String search,
                                                   @Param("userName") String userName,
                                                   @Param("fromDate") LocalDate fromDate,
                                                   @Param("toDate") LocalDate toDate,
                                                   Pageable pageable);


    @Query("""
            SELECT
                ai.id as id, ai.grantDate as grantDate, ai.grantDateHijri as grantDateHijri, ai.titleAr as titleAr,
                ai.titleEn as titleEn, ai.applicationNumber as applicationNumber, pd.id as patentDetailsId,
                ai.filingDate as filingDate, pct.pctApplicationNo as pctApplicationNo, pct.publishNo as publishNo,
                pct.internationalPublicationDate as internationalPublicationDate, pct.filingDateGr as filingDateGr, ai.ipcNumber as ipcNumber
            FROM ApplicationInfo ai
            INNER JOIN ai.category cat
            INNER JOIN PatentDetails pd on pd.applicationId= ai.id
            LEFT JOIN Pct pct ON pct.patentDetails.id = pd.id
            WHERE ai.id = :aiId AND cat.id = :lacId
    """)
    PatentReportProjection findDetailsByIdAndCategoryId(@Param("aiId") Long aiId, @Param("lacId") Long lacId);



    @Modifying
    @Query("update ApplicationInfo set email = :email, mobileCode = :mobileCode, mobileNumber = :mobileNumber  where id = :id  ")
    void updateAppCommunicationDetails(@Param("id")Long id, @Param("email")String email, @Param("mobileCode")String mobileCode, @Param("mobileNumber")String mobileNumber);

    @Query("SELECT appStatus.code FROM ApplicationInfo ai " +
            "JOIN ai.applicationStatus appStatus " +
            "WHERE ai.id = :appId")
    String getApplicationStatus(@Param("appId") Long applicationId);

    @Query("SELECT ai.createdByUser FROM ApplicationInfo ai " +
            "WHERE ai.id = :appId")
    String getCreatedUserById(@Param("appId") Long applicationId);




    @Query(value = "Select new gov.saip.applicationservice.common.dto.AllApplicationsDto(" +
            "appInfo.id," +
            "appInfo.applicationNumber" +
            ",appInfo.titleEn," +
            "appInfo.titleAr," +
            "appInfo.address," +
            "stat,cat,applicant.customerCode" +
            ",appInfo.filingDate,appInfo.grantNumber) " +

            " from ApplicationInfo appInfo " +
            "join appInfo.applicationStatus stat " +
            "join appInfo.category cat " +
            "JOIN appInfo.applicationRelevantTypes applicant" +
            " where " +
            "(:appStatus is null or stat.code= :appStatus)" +
            " and (:appCategory is null or :appCategory = cat.saipCode)" +
            "and (:appNumber is null or :appNumber= appInfo.applicationNumber or appInfo.idOld = :appNumber OR appInfo.grantNumber = :appNumber OR appInfo.applicationRequestNumber = :appNumber) " +
            "AND (:applicationId is null or appInfo.id = :applicationId )" +
            "AND (:querySearch IS NULL " +
            "OR UPPER(cat.applicationCategoryDescAr) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(cat.applicationCategoryDescEn) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(cat.saipCode) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(stat.ipsStatusDescAr) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(stat.ipsStatusDescEn) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(stat.code) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(appInfo.applicationNumber) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(appInfo.applicationRequestNumber) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(appInfo.grantNumber) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(appInfo.idOld) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR CAST(appInfo.id AS string) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(appInfo.address) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(appInfo.titleAr) LIKE CONCAT('%', UPPER(:querySearch), '%') " +
            "OR UPPER(appInfo.titleEn) LIKE CONCAT('%', UPPER(:querySearch), '%')) " +
            "and applicant.type = 'Applicant_MAIN'" +
            "and stat.code != 'DRAFT'")

    Page<AllApplicationsDto> getAllApplicationsWithSearchCapabilities(@Param("appStatus")String appStatus,
                                                                      @Param("appCategory") String appCategory,
                                                                      @Param("appNumber") String appNumber,
                                                                      @Param("querySearch") String querySearch,
                                                                      @Param("applicationId") Long applicationId,
                                                                      Pageable pageable);


    @Query("SELECT distinct new gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto(" +
            "ai.id, ai.applicationNumber, ai.titleAr, ai.titleEn, ai.filingDate, appStatus, ai.applicationRequestNumber, ai.category) " +
            "FROM ApplicationInfo ai " +
            "JOIN ai.applicationStatus appStatus " +
            "JOIN ai.category category " +
            "LEFT JOIN ApplicationUser appUser on ai.id = appUser.applicationInfo.id and appUser.userRole = 'EXAMINER' " +
            "WHERE (COALESCE(:appStatuses) IS NULL OR appStatus.code in (:appStatuses)) " +
            "AND (:userName is null or appUser.userName = :userName)" +
            "AND(:search IS NULL or ai.applicationNumber = :search " +
            "OR CONCAT(coalesce(ai.applicationNumber,'') , coalesce(ai.titleAr,''), coalesce(ai.titleEn,''), " +
            "coalesce(appStatus.ipsStatusDescAr,''), coalesce(appStatus.ipsStatusDescEn,''),coalesce(ai.idOld,''),ai.id,coalesce(ai.grantNumber,''), coalesce(ai.applicationRequestNumber,'')) LIKE (CONCAT('%', :search, '%'))) " +
            "AND(:categoryCode IS NULL OR category.saipCode = :categoryCode) " +
            "AND(:statusCode IS NULL OR appStatus.code = :statusCode)")
    Page<ApplicationInfoBaseDto> findApplicationsByCategoryCodeAndApplicationStatus(@Param("categoryCode") String categoryCode,
                                                                                 @Param("appStatuses") List<String> appStatuses,
                                                                                 @Param("userName") String userName,
                                                                                 @Param("search") String search,
                                                                                 @Param("statusCode") String statusCode,
                                                                                 Pageable pageable);
    @Query("SELECT distinct new gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto(" +
            "ai.id, ai.applicationNumber, ai.titleAr, ai.titleEn, ai.filingDate, appStatus, ai.applicationRequestNumber, ai.category) " +
            "FROM ApplicationInfo ai " +
            "JOIN ai.applicationStatus appStatus " +
            "JOIN ai.category category " +
            "JOIN ai.classificationUnit unit " +
            "LEFT JOIN ApplicationUser appUser on ai.id = appUser.applicationInfo.id and appUser.userRole = 'EXAMINER' " +
            "WHERE (COALESCE(:appStatuses) IS NULL OR appStatus.code in (:appStatuses)) " +
            "AND(CONCAT(coalesce(ai.applicationNumber,'') , coalesce(ai.titleAr,''), coalesce(ai.titleEn,''), " +
            "coalesce(appStatus.ipsStatusDescAr,''), coalesce(appStatus.ipsStatusDescEn,''),coalesce(ai.idOld,''),ai.id,coalesce(ai.grantNumber,''), coalesce(ai.applicationRequestNumber,'')) LIKE (CONCAT('%', '', '%'))) " +
            "AND(:categoryCode IS NULL OR category.saipCode = :categoryCode) " +
            "AND(unit.code in :unitCodes) " +
            "AND(:statusCode IS NULL OR appStatus.code = :statusCode)")
    Page<ApplicationInfoBaseDto> findApplicationsByCategoryCodeAndApplicationStatusAndUnitCode(@Param("categoryCode") String categoryCode,
                                                                                               @Param("appStatuses") List<String> appStatuses,
                                                                                               @Param("unitCodes") List<String> unitCodes,
                                                                                               @Param("statusCode") String statusCode,
                                                                                    Pageable pageable);
    @Query("select niceClassi " +
            " from ApplicationInfo appInfo " +
            " join appInfo.niceClassifications niceClassi " +
            " left join fetch niceClassi.classification classi " +
            " where appInfo.id in (:ids) ")
    List<ApplicationNiceClassification> getNiceClassification(@Param("ids") List<Long> ids);

    @Modifying
    @Query("""
        UPDATE ApplicationInfo e SET e.endOfProtectionDate = :protectionDate
        where e.id = :id
    """)
    void extendProtectionPeriodByAppId(@Param("id") Long id,@Param("protectionDate") LocalDateTime protectionDate);

    @Query("select case when (count(a) > 0)  then true else false end from ApplicationInfo a where a.applicationNumber = :applicationNumber")
    boolean ApplicationNumberExist(@Param("applicationNumber") String applicationNumber);

    @Query("select case when (count(a) > 0)  then true else false end from ApplicationInfo a where a.grantNumber = :grantNumber")
    boolean GrantNumberExist(@Param("grantNumber") String grantNumber);


    @Query("SELECT new gov.saip.applicationservice.common.dto.DepositReportDto(ai.id, ai.titleAr, ai.titleEn, ai.applicationNumber, ai.filingDate, ai.applicationRequestNumber) " +
            "FROM ApplicationInfo ai " +
            "WHERE ai.id = :aiId")
    DepositReportDto findDepositCertificateDetailsById(@Param("aiId") Long aiId);





    @Query(value =
            "select ai.applicationNumber as applicationNumber, " +
                    "lkas.ipsStatusDescAr as ipsStatusDescAr, " +
                    "ai.filingDate as filingDate, " +
                    "ai.titleAr as titleAr, " +
                    "ai.titleEn as titleEn, " +
                    "ai.filingDateHijri as filingDateHijri, " +
                    "ai.grantDateHijri as grantDateHijri, " +
                    "ai.ownerNameAr as ownerNameAr, ai.ownerNameEn as ownerNameEn," +
                    "ai.ownerAddressAr as ownerAddressAr, ai.ownerAddressEn as ownerAddressEn," +
                    "ai.grantDate as grantDate, " +
                    "cr.requestNumber as requestNumber, " +
                    "cr.createdDate as createdDate, " +
                    "cl.nameAr as nameAr, " +
                    "ai.endOfProtectionDate as endOfProtectionDate, " +
                    "ai.registrationDate as registrationDate, " +
                    "ai.registrationDateHijri as registrationDateHijri, " +
                    "ver.nameAr as versionName " +
                    "from CertificateRequest cr " +
                    "join cr.applicationInfo ai " +
                    "join ai.applicationStatus lkas " +
                    "left join ai.niceClassifications nc " +
                    "left join nc.classification cl " +
                    "left join cl.version ver " +
                    "where cr.id = :certificateId")
    List<ApplicationReportInfo> getApplicationReportData(Long certificateId);

    List<ApplicationInfo> findByCategorySaipCodeOrderById (String saipCode);




    @Query("""
        SELECT
        COALESCE(SUM(CASE WHEN a.applicationStatus.code IN :applicationsUnderStudy THEN 1 ELSE 0 END),0) AS applicationsUnderStudy,
        COALESCE(SUM(CASE WHEN a.applicationStatus.code IN :grantedApplications THEN 1 ELSE 0 END),0) AS grantedApplications,
        COALESCE(SUM(CASE WHEN a.applicationStatus.code IN :refusedApplications THEN 1 ELSE 0 END),0) AS refusedApplications,
        COALESCE(SUM(CASE WHEN a.applicationStatus.code = :newApplications THEN 1 ELSE 0 END),0) AS newApplications
        FROM ApplicationInfo a
        JOIN a.applicationRelevantTypes apt
        WHERE a.category.saipCode = :saipCode
        AND apt.customerCode = :customerCode
        """)
    CountApplications sumOfApplicationsAccordingToStatus(
            @Param("saipCode") String saipCode,
            @Param("customerCode") String customerCode,
            @Param("applicationsUnderStudy") List<String> applicationsUnderStudy,
            @Param("grantedApplications") List<String> grantedApplications,
            @Param("refusedApplications") List<String> refusedApplications,
            @Param("newApplications") String newApplications);




  	@Query("select a.endOfProtectionDate from ApplicationInfo a where a.id = :id ")
    LocalDateTime findEndOfProtectionDateById (Long id);

    @Query(value = """
            select 
            (select count(*) from ApplicationSubClassification aSubC
            join ApplicationInfo info2 on info2.id = aSubC.applicationInfo.id
            join SubClassification sc on sc.id = aSubC.subClassification.id
            where info2.id = info.id) as trademarkTotalSubClassifications,
            d.nexuoId as trademarkPhoto,
            info.applicationNumber as trademarkNumber,
            info.titleAr as trademarkNameAr,
            info.id as trademarkId,
            art.customerCode as tmoCustomerCode,
            info.titleEn as trademarkNameEn,
            user.name as lkNexuoUserName,
            clss.nameAr as classificationNameAr,
            clss.nameEn as classificationNameEn
            from ApplicationInfo info
            join ApplicationNiceClassification anc on anc.application.id=info.id
            join Classification clss on clss.id = anc.classification.id
            join ApplicationRelevantType art on art.applicationInfo.id = info.id
            join LkApplicationCategory lac on lac.id = info.category.id
            join LkApplicationStatus las on las.id = info.applicationStatus.id
            join Document d on d.applicationInfo.id = info.id
            join LkDocumentType dt on dt.id = d.documentType.id
            join TrademarkDetail td on td.applicationId = info.id
            join LkTagTypeDesc lkt on td.tagTypeDesc.id = lkt.id
            left join LkNexuoUser user on user.id = d.lkNexuoUser.id
            where info.isDeleted = 0
            and art.customerCode In :customerCodes
            and art.type = :applicantType
            and las.code = :applicationStatus
            and lkt.code in :trademarkTypesList
            and lac.saipCode = :applicationCategory
            and ( COALESCE( :trademarkNumbers ) is null or info.applicationNumber in :trademarkNumbers or info.grantNumber in :trademarkNumbers or info.idOld in :trademarkNumbers)
            and ( COALESCE( :applicationId ) is null or info.id in :applicationId)
            and (:filterTrademarkNumber is null or UPPER(info.applicationNumber) like UPPER(CAST(CONCAT('%', :filterTrademarkNumber, '%') AS string)) )
            and dt.code = :documentType""",
            countQuery = """
            select count(distinct info.id)
             from ApplicationInfo info
            join ApplicationNiceClassification anc on anc.application.id=info.id
            join TrademarkDetail td on td.applicationId = info.id
            join LkTagTypeDesc lkt on td.tagTypeDesc.id = lkt.id
            join Classification clss on clss.id = anc.classification.id
            join ApplicationRelevantType art on art.applicationInfo.id = info.id
            join LkApplicationCategory lac on lac.id = info.category.id
            join LkApplicationStatus las on las.id = info.applicationStatus.id
            join Document d on d.applicationInfo.id = info.id
            join LkDocumentType dt on dt.id = d.documentType.id
            left join LkNexuoUser user on user.id = d.lkNexuoUser.id
            where info.isDeleted = 0
            and art.customerCode In :customerCodes
            and art.type = :applicantType
            and las.code = :applicationStatus
            and lkt.code in :trademarkTypesList
            and lac.saipCode = :applicationCategory
            and ( COALESCE( :trademarkNumbers ) is null or info.applicationNumber in :trademarkNumbers or info.grantNumber in :trademarkNumbers or info.idOld in :trademarkNumbers)
            and ( COALESCE( :applicationId ) is null or info.id in :applicationId)
            and (:filterTrademarkNumber is null or UPPER(info.applicationNumber) like UPPER(CAST(CONCAT('%', :filterTrademarkNumber, '%') AS string)) or UPPER(info.grantNumber) like UPPER(CAST(CONCAT('%', :filterTrademarkNumber, '%') AS string)) or UPPER(info.idOld) like UPPER(CAST(CONCAT('%', :filterTrademarkNumber, '%') AS string)) )
            and dt.code = :documentType"""
    )

    Page<TrademarkDetailsProjection> getTrademarksByCustomerCode(@Param(value = "customerCodes") List<String> customerCode,
                                                                 @Param("trademarkNumbers") List<String> trademarkNumbers,
                                                                 @Param("filterTrademarkNumber") String filterTrademarkNumber,
                                                                 @Param("applicantType") ApplicationRelevantEnum type,
                                                                 @Param("applicationStatus") String applicationStatus,
                                                                 @Param("applicationCategory") String applicationCategory,
                                                                 @Param("documentType") String documentType,
                                                                 @Param("trademarkTypesList") List<String> trademarkTypesList,
                                                                 @Param("applicationId") List<Long> applicationId,
                                                                 Pageable pageable
    );

    @Modifying
    @Query("update ApplicationInfo a set a.endOfProtectionDate = :endOfProtectionDate where a.id = :id ")
    void updateApplicationEndOfProtectionDate(@Param("endOfProtectionDate") LocalDateTime endOfProtectionDate, @Param("id") Long id);

    @Modifying
    @Query("""
        update ApplicationInfo ai set ai.applicationStatus = :lkApplicationStatus where ai.id in (:ids)
    """)
    void updateApplicationsStatusByIds(@Param("ids") List<Long> ids, @Param("lkApplicationStatus") LkApplicationStatus lkApplicationStatus);

    @Query("select ai from ApplicationInfo ai where (lower(ai.applicationNumber) = lower(:applicationNumber) OR lower(ai.grantNumber) = lower(:applicationNumber) OR lower(ai.idOld) = lower(:applicationNumber) OR ai.id = :applicationId or lower(ai.applicationRequestNumber) = lower(:applicationNumber) ) and ai.category.saipCode = :category")
    ApplicationInfo getApplicationInfoByApplicationNumberAndCategory(@Param("applicationNumber") String applicationNumber, @Param("category") String category, @Param("applicationId") Long applicationId);

    @Query("select case when (count(ai) > 0)  then true else false end from ApplicationInfo ai where ai.id = :applicationId and :date <= ai.endOfProtectionDate")
    boolean isGivenDateNotPastApplicationEndOfProtectionDate (@Param("applicationId") Long applicationId, @Param("date") LocalDateTime date);

    @Query("SELECT ai.pltRegisteration FROM ApplicationInfo ai Where ai.id = :appId")
    Boolean checkPltRegister(@Param("appId") Long appId);
    @Query("SELECT ai.category.saipCode FROM ApplicationInfo ai Where ai.id = :appId")
    String checkApplicationCategory(@Param("appId") Long appId);

    @Query("select case when (count(ai) > 0)  then true else false end from ApplicationInfo ai where ai.id = :applicationId and ai.applicationStatus.code NOT IN (:appStatus)")
    boolean checkApplicationStatusIsNotInListOfStatus(@Param("applicationId")Long applicationId, @Param("appStatus") List<String> appStatus);

    @Modifying
    @Query("""
       update ApplicationInfo ai set ai.isPriorityConfirmed = true where ai.id = :applicationInfoId 
    """)
    void disableAddingApplicationPriority(@Param("applicationInfoId") Long applicationInfoId);

    @Query("SELECT ai.isPriorityConfirmed FROM ApplicationInfo ai Where ai.id = :applicationInfoId")
    boolean checkIsPriorityConfirmed(@Param("applicationInfoId") Long applicationInfoId);

    @Query("select ai from ApplicationInfo ai where ai.applicationNumber= :applicationNumber and ai.applicationStatus.code='THE_TRADEMARK_IS_REGISTERED'")
    ApplicationInfo getGrantedApplicationDetailsByApplicationNumber(@Param("applicationNumber") String applicationNumber);

    @Modifying
    @Query("""
        update ApplicationInfo ai set ai.applicationStatus.id =
        (select lkas.id from LkApplicationStatus lkas where lkas.code = :code and lkas.applicationCategory.id= :categoryId)
        where ai.id = :id
        """)
    void updateApplicationStatusByStatusCode(@Param("id") Long id, @Param("code") String code , @Param("categoryId") Long categoryId);




    @Query(value = """
            SELECT ai
            FROM ApplicationInfo ai
            WHERE (ai.applicationNumber = :applicationNumber OR ai.grantNumber = :applicationNumber OR ai.idOld = :applicationNumber OR ai.applicationRequestNumber = :applicationNumber)
            AND ai.partialApplication = false
            AND ai.applicationStatus.code not in (:appStatus)
            """)
    ApplicationInfo getMainApplicationInfo (@Param("applicationNumber") String applicationNumber, @Param("appStatus") List<String> appStatus);

    @Query(value = """
            SELECT ai.id as id, ai.titleAr as titleAr, ai.titleEn as titleEn , ai.applicationNumber as applicationNumber
            FROM ApplicationInfo ai
            JOIN ApplicationCustomer ac ON ai.id = ac.application.id
            WHERE ai.partialApplication = false
              AND ai.applicationNumber  = COALESCE(:applicationNumber,  ai.applicationNumber, ai.idOld, ai.grantNumber)
              AND ac.customerId         = :customerId
              AND ai.category.saipCode  = :category
              
            """)
    List<PartialApplicationInfoProjection> listCustomerMainApplications (@Param("applicationNumber") String applicationNumber, @Param("customerId") Long customerId, @Param("category") String category);

    @Query("select ai.category.saipCode from ApplicationInfo ai where ai.id = :id")
    String getApplicationCategoryById(@Param("id") Long id);
    
    @Query("select ai.nationalSecurity from ApplicationInfo ai where ai.id = :id")
    Boolean findNationalSecurityByAppId(@Param("id") Long id);
    @Query("""
        select ai.processRequestId from ApplicationInfo ai
        where ai.id = :id
    """)
    String getProcessRequestIdById(Long id);



    @Query("SELECT a.category FROM ApplicationInfo a WHERE a.id = :id")
    LkApplicationCategory findCategoryByApplicationInfoId(Long id);

    @Query("SELECT cu.id FROM Classification c  JOIN c.unit cu WHERE c.id = :classificationId")
    Long getClassificationUnitByClassificationId(Long classificationId);


    @Modifying
    @Query("update ApplicationInfo app set app.totalPagesNumber = :totalNumberOfPages" +
            ", app.claimPagesNumber = (select count(pe) from ProtectionElements pe where pe.application.id = :appId ) "+
            "  where app.id = :appId  ")
    void updateAppTotalNumberOfPagesAndCalculateClaimPages(@Param("appId")Long appId, @Param("totalNumberOfPages")Long totalNumberOfPages);

    @Query("""
    SELECT unit.id from ApplicationInfo app 
    left join LkClassificationUnit unit on app.classificationUnit.id = unit.id
    where app.id = :appId
      """)
    Long getApplicationClassificationUnitIdByApplicationId(@Param("appId")Long appId);


    @Query(value = """
            SELECT nextval('application.applications_info_request_number_seq')
        """, nativeQuery = true)
    Long getRequestNumberSequenceNextValue();

    @Query(value = """
            select ai.applicationRequestNumber from ApplicationInfo ai where ai.id = :id
        """)
    String getApplicationRequestNumber(@Param("id") Long id);

    @Query("select ai.id from ApplicationInfo ai where lower(ai.applicationNumber) = lower(:applicationNumber)")
    Long getAppIdByAppNumber(@Param("applicationNumber") String applicationNumber);

    @Query("select count(ag.id) from ApplicationAgent ag where ag.application.id = :applicationId and ag.status = 'ACTIVE' ")
    Long getApplicationAgentCountByAppId(@Param("applicationId") Long applicationId);
    
    @Query(value = """
            select ai.processRequestId
            from ApplicationInfo ai
            left join ai.applicationStatus las
            left join ai.category lac
            where (:status is null or las.code = :status)
                and (:categoryCode is null or lac.saipCode = :categoryCode)
                and (:applicationNumber is null or ai.applicationNumber = :applicationNumber or ai.applicationRequestNumber = :applicationNumber)
                and (DATE(:fromFilingDate) is null or (  DATE(ai.filingDate) >= DATE(:fromFilingDate)) )
                and (DATE(:toFilingDate) is null or ( DATE(ai.filingDate) <= DATE(:toFilingDate)))
                and ai.processRequestId is not null
               """)
    List<Long> findProcessesRequestsIdsByFilters(@Param("applicationNumber") String applicationNumber, @Param("status") String status,
                                                 @Param("categoryCode") String categoryCode, @Param("fromFilingDate") LocalDateTime fromFilingDate,
                                                 @Param("toFilingDate") LocalDateTime toFilingDate);

    @Query("""
            SELECT distinct new gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto(
            ai.id, ai.applicationNumber, ai.titleAr, ai.titleEn, ai.filingDate, appStatus, ai.applicationRequestNumber, ai.category)
            FROM ApplicationInfo ai
            JOIN ai.applicationStatus appStatus
            JOIN ai.category category
            LEFT JOIN ApplicationUser appUser on ai.id = appUser.applicationInfo.id
            WHERE (:appId IS NULL OR ai.id = :appId)
            """)
    ApplicationInfoBaseDto findApplicationBasicInfo(@Param("appId") Long appId);
    @Query("""
            SELECT distinct new gov.saip.applicationservice.common.dto.ApplicationInfoBaseDto(
            ai.id, ai.applicationNumber, ai.titleAr, ai.titleEn, ai.filingDate, appStatus, ai.applicationRequestNumber, ai.category)
            FROM ApplicationInfo ai
            left JOIN ai.applicationStatus appStatus
            left JOIN ai.category category
            LEFT JOIN ApplicationUser appUser on ai.id = appUser.applicationInfo.id
            WHERE (:appIds IS NULL OR ai.id in :appIds)
            """)
    ApplicationInfoBaseDto findApplicationBasicInfoIds(@Param("appIds") List<Long> appIds);

    @Query("""
            SELECT ai.id
            FROM ApplicationInfo ai
            WHERE ai.category.saipCode = 'PATENT'
             and ai.applicationStatus.code not in('WAIVED','COMPLETE_REQUIREMENTS', 'WAITING_FOR_APPLICATION_FEE_PAYMENT','NEW','DRAFT')
             and ai.id not in
                (select cert.application.id from ApplicationCertificateDocument cert)
            """)
    List<Long> findPatentApplicationWithNoPdfDocument();

    @Query("""
            select
             new gov.saip.applicationservice.common.dto.AppCharacteristics(category.saipCode,app.applicationNumber)
            from ApplicationInfo app
            join app.category category
            where app.id = :appId
            """)
    AppCharacteristics getApplicationCategoryByAppId(@Param("appId") Long appId);


    @Query("SELECT ai FROM ApplicationInfo ai " +
            "WHERE ai.category.saipCode = :saipCode " +
            "AND ai.filingDate IS NOT NULL AND (ai.applicationNumber IS NULL OR ai.applicationNumber = '')")
    List<ApplicationInfo> findByCategoryAndMissingApplicationNumber(@Param("saipCode") String saipCode);



    @Modifying
    @Query("""
            update ApplicationInfo ai set ai.enterpriseSize.id= :enterpriseSizeId where ai.id= :appId
            """)
    void updateApplicationInfoEnterpriseSize(@Param("enterpriseSizeId") Long enterpriseSizeId,@Param("appId") Long appId);


    @Modifying
    @Query("""
        update ApplicationInfo ai set ai.applicationStatus = (
        select s from LkApplicationStatus s where s.code = :statusCode
        and s.applicationCategory = (
            select c from LkApplicationCategory c where c.saipCode = :categoryCode
        )
        )
        where ai.processRequestId in (:ids)
    """)
    void updateApplicationBatchByProcessRequestId(@Param("ids") List<Long> ids, @Param("statusCode") String statusCode, @Param("categoryCode") String categoryCode);

    @Query("""
            SELECT distinct new gov.saip.applicationservice.common.dto.ApplicationMainDto(
            ai.id, ai.titleAr, ai.titleEn , ai.filingDate , ai.applicationNumber)
            FROM ApplicationInfo ai
            where ai.applicationNumber = :applicationNumber and ai.isDeleted = 0
            """)
    ApplicationMainDto getApplicationMainByApplicationNumber(@Param("applicationNumber") String applicationNumber);

    @Modifying
    @Transactional
    @Query(value = """
            update ApplicationInfo ap
            set ap.lastStatusModifiedDate = :lastModifiedStatusDate
            where ap.id= :appId
                    
                     """)
    void updateLastStatusModifiedDate(@Param("appId") Long appId, @Param("lastModifiedStatusDate") LocalDateTime lastModifiedStatusDate);


    boolean existsByIdAndCreatedByCustomerId(Long id, Long customerid);

    @Modifying
    @Query("""
        update ApplicationInfo ai set ai.applicationStatus = (
        select s from LkApplicationStatus s where s.code = :statusCode
        and s.applicationCategory = (
            select c from LkApplicationCategory c where c.saipCode = :categoryCode
        )
        )
        where ai.id = :applicationId
    """)
    void updateApplicationByIdAndStatusCode(@Param("applicationId") Long applicationId, @Param("statusCode") String statusCode, @Param("categoryCode") String categoryCode);

    @Modifying
    @Query("""
        update ApplicationInfo ai set ai.applicationStatus = (
        select s from LkApplicationStatus s where s.code = :statusCode
        and s.applicationCategory = (
            select c from LkApplicationCategory c where c.saipCode = :categoryCode
        )
        )
        where ai.id in (:applicationIds)
    """)
    void updateApplicationsStatusByIdsAndStatusCode(@Param("applicationIds") List<Long> applicationIds, @Param("statusCode") String statusCode, @Param("categoryCode") String categoryCode);

    @Modifying
    @Transactional
    @Query(value = """
            update ApplicationInfo ap
            set ap.lastStatusModifiedDate = :lastModifiedStatusDate
            where ap.id in (:applicationIds)
                    
                     """)
    void updateApplicationsLastStatusModifiedDate(@Param("applicationIds") List<Long> applicationIds, @Param("lastModifiedStatusDate") LocalDateTime lastModifiedStatusDate);

    @Modifying
    @Query(value = """
            update ApplicationInfo ap
            set ap.lastUserModifiedDate = null
            where ap.id = :applicationId   
            """)
    void setApplicationLastUserModifiedDateWithNull(@Param("applicationId") Long applicationId);

    @Query(value = """
            select distinct new gov.saip.applicationservice.common.dto.BillApplicationInfoAttributesDto(ai.titleEn,ai.titleAr,ai.applicationNumber,ai.filingDate) from ApplicationInfo ai where ai.id =:applicationId
            """)
    BillApplicationInfoAttributesDto getBillApplicationAttributes(@Param("applicationId") Long applicationId);

    @Query("""
        select ai.applicationStatus.code from  ApplicationInfo ai  where ai.id = :id
    """)
    String getApplicationsStatusCodeByApplicationId(@Param("id") Long id);


    @Modifying
    @Query("""
        update ApplicationInfo ai set ai.byHimself = false where (COALESCE(:appIds) is null or ai.id in :appIds)
    """)
    void updateApplicationWithHimSelfWhenAddAgent(@Param("appIds") List<Long> appIds);

    Optional<ApplicationInfo> findByApplicationNumber(String applicationNumber);


}

