package gov.saip.applicationservice.common.repository.installment;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentLightProjection;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentProjection;
import gov.saip.applicationservice.common.dto.installment.InstallmentNotificationProjection;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ApplicationInstallmentRepository extends BaseRepository<ApplicationInstallment, Long> {

    @Query(value = """
            SELECT
                  DISTINCT
                  inst.id as id,
                  inst.installmentStatus as installmentStatus,
                  inst.installmentType as installmentType,
                  inst.startDueDate as startDueDate,
                  inst.endDueDate as endDueDate,
                  inst.lastDueDate as lastDueDate,
                  inst.billNumber as billNumber,
                  inst.feeCost as totalCost,
                  inst.penaltyCost as penaltyCost,
                  inst.taxCost as taxCost,
                  inst.installmentIndex as  installmentIndex,
                  inst.installmentCount as installmentCount,
                  app.id as applicationId,
                  app.email as applicationEmail,
                  app.mobileCode as applicationMobileCode,
                  app.mobileNumber as applicationMobileNumber,
                  app.titleAr as applicationNameAr,
                  app.titleEn as applicationNameEn,
                  app.applicationNumber as applicationNumber,
                  app.processRequestId as processRequestId,
                  cat.saipCode as applicationCategoryEnum,
                  appCst.customerId as mainApplicantId,
                  appCst.customerCode as mainApplicantCustomerCode
            from ApplicationInstallment inst
            join inst.application app
            join app.category cat
            join app.applicationCustomers appCst
            left join inst.postponedReason postponedReason
            where inst.installmentType = :installmentType
            and inst.installmentStatus in (:installmentStatus)
            and (cast(:lastDueDate as timestamp ) is null or inst.lastDueDate <= :lastDueDate)
            and (cast(:endDueDate as timestamp)  is  null or inst.endDueDate < :endDueDate)
            and (cast(:graceEndDate as timestamp)  is  null or inst.graceEndDate < :graceEndDate)
            and cat.saipCode = :saipCode
            and appCst.customerType = 'MAIN_OWNER'
            and inst.postponedReason is null
            and app.isDeleted = 0 
            and app.applicationStatus.code not in (:appStatus)
            """)
    Page<ApplicationInstallmentProjection> getInstallmentProjectionPageToProcess(
            @Param("installmentType") InstallmentType installmentType,
            @Param("installmentStatus") List<InstallmentStatus> installmentStatus,
            @Param("saipCode") String saipCode,
            @Param("lastDueDate") LocalDateTime lastDueDate,
//            @Param("startDueDate") LocalDateTime startDueDate,
            @Param("endDueDate") LocalDateTime endDueDate,
            @Param("graceEndDate") LocalDateTime graceEndDate,
            @Param("appStatus") List<String> appStatus ,
            Pageable pageable);

    //



    @Query(value = """
            SELECT
                  DISTINCT
                  inst.id as id,
                  inst.installmentStatus as installmentStatus,
                  inst.installmentType as installmentType,
                  inst.startDueDate as startDueDate,
                  inst.endDueDate as endDueDate,
                  inst.lastDueDate as lastDueDate,
                  inst.billNumber as billNumber,
                  inst.feeCost as totalCost,
                  inst.penaltyCost as penaltyCost,
                  inst.taxCost as taxCost,
                  inst.installmentIndex as  installmentIndex,
                  inst.installmentCount as installmentCount,
                  app.id as applicationId,
                  app.email as applicationEmail,
                  app.mobileCode as applicationMobileCode,
                  app.mobileNumber as applicationMobileNumber,
                  app.titleAr as applicationNameAr,
                  app.titleEn as applicationNameEn,
                  app.applicationNumber as applicationNumber,
                  cat.saipCode as applicationCategoryEnum,
                  appCst.customerId as mainApplicantId,
                  appCst.customerCode as mainApplicantCustomerCode
            from ApplicationInstallment inst
            join inst.application app
            join app.category cat
            join app.applicationCustomers appCst
            left join inst.postponedReason postponedReason
            where inst.id = :id and
            appCst.customerType = 'MAIN_OWNER' and app.isDeleted = 0
            """)
    ApplicationInstallmentProjection getInstallmentProjectionPageToProcessById(@Param("id") Long id);

    @Query("select inst from ApplicationInstallment inst where inst.application.id = :appId and inst.installmentStatus != :status ")
    Optional<ApplicationInstallment> getUnpaidInstallmentByAppId(Long appId, InstallmentStatus status);

    @Modifying
    @Transactional
    @Query("update ApplicationInstallment inst set inst.installmentStatus = :status where inst.id in (:ids) ")
    void updateInstallmentListByIds(@Param("ids") List<Long> ids, @Param("status") InstallmentStatus status);

    @Query(value = """
        select
            notification.id as id,
            inst.endDueDate as endDueDate,
            inst.feeCost as totalCost,
            inst.penaltyCost as penaltyCost,
            inst.taxCost as taxCost,
            app.applicationNumber as applicationNumber,
            notification.notificationStatus as notificationStatus,
            app.id as appId,
            app.category.saipCode as applicationCategory,
            inst.installmentStatus as installmentStatus,
            notification.customerId as customerId
        from InstallmentNotification notification
        join notification.applicationInstallment inst
        join inst.application app
        where (:notificationStatus is null or notification.notificationStatus = :notificationStatus)
        and (:appNumber is null or app.applicationNumber = :appNumber OR app.grantNumber = :appNumber OR app.idOld = :appNumber)
        and (:applicationId is null or app.id = :applicationId )
        and (:installmentStatus is null or inst.installmentStatus = :installmentStatus)
        and notification.isDeleted = 0
        order by notification.id  desc
    """, countQuery = """
        select
            count(1)
        from InstallmentNotification notification
        join notification.applicationInstallment inst
        join inst.application app
        where (:notificationStatus is null or notification.notificationStatus = :notificationStatus)
        and (:appNumber is null or app.applicationNumber = :appNumber or app.applicationNumber = :appNumber)
        and (:applicationId is null or app.id = :applicationId )
        and (:installmentStatus is null or inst.installmentStatus = :installmentStatus)
        and notification.isDeleted = 0
    """)
    Page<InstallmentNotificationProjection> filterApplicationInstallments(@Param("notificationStatus") InstallmentNotificationStatus notificationStatus,
                                                                     @Param("installmentStatus") InstallmentStatus installmentStatus,
                                                                     @Param("appNumber") String applicationNumber,
                                                                     @Param("applicationId") Long applicationId,
                                                                     Pageable pageable);

    Optional<ApplicationInstallment> getByApplicationId(@Param("id") Long id);

    @Query("Select app.startDueDate from ApplicationInstallment app where app.application.id=:appId and app.installmentStatus = :stat")
    LocalDateTime getApplicationInstallmentDueDate(@Param("appId") Long appId,@Param("stat")InstallmentStatus stat);

    @Query("""
        select
        inst.id as id,
        inst.installmentStatus as installmentStatus,
        inst.startDueDate as startDueDate,
        inst.endDueDate as endDueDate,
        inst.billNumber as billNumber,
        cat.saipCode as applicationCategoryEnum,
        (CASE WHEN inst.postponedReason is not null then true else false end) as postponed,
        inst.installmentIndex as installmentIndex
        from ApplicationInstallment inst
        join inst.application ai
        join ai.category cat
        where inst.id = (
            select max(id) from ApplicationInstallment where application.id = :applicationId
        )
    """)
    ApplicationInstallmentLightProjection getInstallmentStatusByApplicationId(@Param("applicationId") Long applicationId);

    @Modifying
    @Query("""
        update ApplicationInstallment inst
        set inst.billNumber = :billNumber,
        inst.feeCost = :totalCost,
        inst.penaltyCost = :penaltyCost,
        inst.taxCost = :taxesCost,
        inst.installmentStatus = :status,
        inst.exceptionMessage = null,
        
        inst.startDueDate = CASE WHEN (cast(:startDueDate as timestamp )) IS NULL THEN inst.startDueDate ELSE :startDueDate END,
        inst.endDueDate = CASE WHEN (cast(:endDueDate as timestamp )) IS NULL THEN inst.endDueDate ELSE :endDueDate END,
        inst.graceEndDate = CASE WHEN (cast(:graceEndDate as timestamp )) IS NULL THEN inst.graceEndDate ELSE :graceEndDate END
        where inst.id = :id
    """)
    void updateInstallmentInfoAfterBillCreation(@Param("billNumber") String billNumber,
                                                @Param("totalCost") BigDecimal totalCost,
                                                @Param("penaltyCost") BigDecimal penaltyCost,
                                                @Param("taxesCost") BigDecimal taxesCost,
                                                @Param("status") InstallmentStatus status,
                                                @Param("startDueDate") LocalDateTime startDueDate,
                                                @Param("endDueDate") LocalDateTime endDueDate,
                                                @Param("graceEndDate") LocalDateTime graceEndDate,
                                                @Param("id") Long id);



    @Modifying
    @Query("""
        update ApplicationInstallment inst
        set inst.exceptionMessage =  CONCAT(coalesce(inst.exceptionMessage, '['), :exception, ',')
        where inst.id = :id
    """)
    void logInstallmentExceptionMessage(@Param("exception") String exception, @Param("id") Long id);


    @Query(value = """
            SELECT
                  DISTINCT
                  inst.id as id,
                  inst.installmentStatus as installmentStatus,
                  inst.installmentType as installmentType,
                  inst.startDueDate as startDueDate,
                  inst.endDueDate as endDueDate,
                  inst.lastDueDate as lastDueDate,
                  inst.billNumber as billNumber,
                  inst.feeCost as totalCost,
                  inst.penaltyCost as penaltyCost,
                  inst.taxCost as taxCost,
                  inst.installmentIndex as  installmentIndex,
                  app.id as applicationId,
                  app.email as applicationEmail,
                  app.mobileCode as applicationMobileCode,
                  app.mobileNumber as applicationMobileNumber,
                  app.applicationNumber as applicationNumber,
                  cat.saipCode as applicationCategoryEnum,
                  appCst.customerId as mainApplicantId,
                  appCst.customerCode as mainApplicantCustomerCode
            from ApplicationInstallment inst
            join inst.application app
            join app.category cat
            join app.applicationCustomers appCst
            left join inst.postponedReason postponedReason
            where  inst.id = :id
            """)
    Optional<ApplicationInstallmentProjection> findInstallmentById(@Param("id") Long id);

    @Query("""
        select inst.installmentIndex from ApplicationInstallment inst
        where inst.id =
        (select max(inst2.id) from ApplicationInstallment inst2 where inst2.application.id = :appId and inst2.installmentStatus = :installmentStatus)
    """)
    Integer getLastIndexByAppAndStatus(@Param("appId") Long appId, @Param("installmentStatus") InstallmentStatus installmentStatus);


    @Query("""
            SELECT inst
            FROM ApplicationInstallment inst
            where inst.id = (
            SELECT max(inst2.id)
            FROM ApplicationInstallment inst2
            join inst2.application ai
            WHERE ai.id = :appId
            )
            
     """)
    ApplicationInstallment getMostRecentInstallmentByApplicationId(@Param("appId")Long  appId);


    @Query("""
            SELECT inst
            FROM ApplicationInstallment inst
            where inst.id = (
            SELECT max(inst2.id)
            FROM ApplicationInstallment inst2
            join inst2.application ai
            WHERE ai.id = :appId and inst2.installmentType = :installmentType
            )
            
     """)
    ApplicationInstallment getMostRecentInstallmentByApplicationIdAndType(@Param("appId")Long  appId, @Param("installmentType") InstallmentType installmentType);

    boolean existsByApplicationId(Long appId);


    @Query("""
        select inst from ApplicationInstallment inst
        where inst.id =
        (select max(inst2.id) 
        from ApplicationInstallment inst2
        join inst2.application ai
        where ai.id = :appId and
        inst2.installmentType = :installmentType and
        inst2.installmentStatus = :installmentStatus
        )
    """)
    ApplicationInstallment getLastInstallmentByAppAndTypeAndStatus(@Param("appId") Long appId, @Param("installmentType") InstallmentType installmentType, @Param("installmentStatus") InstallmentStatus installmentStatus);


    @Modifying
    @Query("""
        update ApplicationInstallment inst set inst.installmentStatus = :status
        where
        inst.id = (
            SELECT max(inst2.id)
            FROM ApplicationInstallment inst2
            join inst2.application ai
            WHERE ai.id = :appId and inst2.installmentType = :installmentType
        )
    """)
    void updateLastInstallmentStatusByApplicationId(@Param("appId") Long appId, @Param("installmentType") InstallmentType installmentType, @Param("status") InstallmentStatus status);


    @Modifying
    @Transactional
    @Query("""
        update ApplicationInstallment inst set inst.installmentStatus = :newStatus
        where inst.installmentStatus = :oldStatus and inst.application.id in (
            select ai.id from ApplicationInstallment inst2
            join inst2.application ai
            join ai.applicationStatus st
            join ai.category cat
            where inst2.installmentType = :installmentType and inst2.installmentStatus = 'PAID'
            and st.code not in :statusList
            and cat.saipCode in :categories
            group by ai.id having count(*) >= :yearsToPostponed
        )
    """)
    void postponedAnnualFeesForNotAcceptedApplicationsAndPaidThreeYears(@Param("statusList") List<String> statusList, @Param("categories") List<String> categories, @Param("installmentType") InstallmentType installmentType, @Param("yearsToPostponed") Long yearsToPostponed, @Param("oldStatus") InstallmentStatus oldStatus, @Param("newStatus") InstallmentStatus newStatus);

    @Query("select inst from ApplicationInstallment inst JOIN FETCH inst.application  where  inst.id = :id ")
    Optional<ApplicationInstallment> getInstallmentById(@Param("id") Long id);
}
