package gov.saip.applicationservice.common.repository.installment;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.installment.InstallmentNotificationProjection;
import gov.saip.applicationservice.common.enums.installment.InstallmentNotificationStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.InstallmentNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InstallmentNotificationRepository extends BaseRepository<InstallmentNotification, Long> {

    @Modifying
    @Query("update InstallmentNotification inst set inst.isDeleted = 1 where inst.applicationInstallment.id = :id")
    void resetLastNotification(@Param("id") Long id);

    @Modifying
    @Query("update InstallmentNotification inst set inst.isDeleted = 1 where inst.applicationInstallment.id in (:ids) ")
    void resetLastNotificationList(@Param("ids") List<Long> ids);

    @Modifying
    @Query("update InstallmentNotification inst set inst.isDeleted = 1 where inst.id in (:ids) ")
    void resetLastNotificationListByIds(@Param("ids") List<Long> ids);

    @Query(value = """
        select 
            notification
        from InstallmentNotification notification 
        join notification.applicationInstallment inst 
        join inst.application app 
        where (:notificationStatus is null or notification.notificationStatus = :notificationStatus)
        and (:appNumber is null or app.applicationNumber = :appNumber OR app.grantNumber = :appNumber  OR app.idOld = :appNumber) 
        and (:applicationId is null or app.id = :applicationId) 
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
        and (:appNumber is null or app.applicationNumber = :appNumber OR app.grantNumber = :appNumber OR app.idOld = :applicationNumber) 
        and (:applicationId is null or app.id = :applicationId) 
        and (:installmentStatus is null or inst.installmentStatus = :installmentStatus)
        and notification.isDeleted = 0
    """)
    Page<InstallmentNotification> filterApplicationInstallments(@Param("notificationStatus") InstallmentNotificationStatus notificationStatus,
                                                               @Param("installmentStatus") InstallmentStatus installmentStatus,
                                                               @Param("appNumber") String applicationNumber,
                                                               @Param("applicationId") Long applicationId,
                                                               Pageable pageable);


    @Query(value = """
        select 
            notification
        from InstallmentNotification notification 
        join notification.applicationInstallment inst 
        join inst.application app 
        where (notification.notificationStatus = :notificationStatus)
        and notification.isDeleted = 0
        order by notification.id  desc 
    """, countQuery = """
        select 
            count(1) 
        from InstallmentNotification notification 
        join notification.applicationInstallment inst 
        join inst.application app 
        where (notification.notificationStatus = :notificationStatus)
        and notification.isDeleted = 0
    """)
    Page<InstallmentNotification> getFailedNotificationsToResend(@Param("notificationStatus") InstallmentNotificationStatus notificationStatus,
                                                                Pageable pageable);

    @Query(value = """
        select
          notification.id as id,
          notification.notificationTemplateCode as notificationTemplateCode,
          notification.notificationType as notificationType,
          notification.notificationStatus as notificationStatus,
          
          inst.id as installmentId,
          inst.feeCost as totalCost,
          inst.penaltyCost as penaltyCost,
          inst.taxCost as taxCost,
          inst.billNumber as billNumber,
          inst.installmentStatus as installmentStatus,
          inst.installmentType as installmentType,
          
          cat.saipCode as applicationCategory,
          
          app.id as appId,
          app.email as applicationEmail,
          app.mobileCode as applicationMobileCode,
          app.mobileNumber as applicationMobileNumber,
          app.titleAr as applicationNameAr,
          app.titleEn as applicationNameEn,
          app.applicationNumber as applicationNumber,
          
          appCst.customerId as mainApplicantId,
          appCst.customerCode as mainApplicantCustomerCode
          
        from InstallmentNotification notification
        join notification.applicationInstallment inst
        join inst.application app
        join app.category cat
        join app.applicationCustomers appCst
        left join inst.postponedReason postponedReason
        where
        notification.notificationStatus = :notificationStatus
        and notification.isDeleted = 0
        and appCst.customerType = 'MAIN_OWNER'
        and inst.postponedReason is null
    """, countQuery = """
        select
            count(1)
        from InstallmentNotification notification
        join notification.applicationInstallment inst
        join inst.application app
        join app.category cat
        join app.applicationCustomers appCst
        left join inst.postponedReason postponedReason
        where
        notification.notificationStatus = :notificationStatus
        and notification.isDeleted = 0
        and appCst.customerType = 'MAIN_OWNER'
        and inst.postponedReason is null
    """)
    Page<InstallmentNotificationProjection> getFailedProjectionPageToResend(@Param("notificationStatus") InstallmentNotificationStatus notificationStatus,
                                                                Pageable pageable);

    @Query("""
       select
          notification.id as id,
          notification.notificationTemplateCode as notificationTemplateCode,
          notification.notificationType as notificationType,
          notification.notificationStatus as notificationStatus,
          
          inst.id as installmentId,
          inst.feeCost as totalCost,
          inst.penaltyCost as penaltyCost,
          inst.taxCost as taxCost,
          inst.billNumber as billNumber,
          inst.installmentStatus as installmentStatus,
          inst.installmentType as installmentType,
          
          cat.saipCode as applicationCategory,
          
          app.id as appId,
          app.email as applicationEmail,
          app.mobileCode as applicationMobileCode,
          app.mobileNumber as applicationMobileNumber,
          app.titleAr as applicationNameAr,
          app.titleEn as applicationNameEn,
          app.applicationNumber as applicationNumber,
          
          appCst.customerId as customerId,
          appCst.customerCode as customerCode
          
        from InstallmentNotification notification
        join notification.applicationInstallment inst
        join inst.application app
        join app.category cat
        join app.applicationCustomers appCst
        where 
        notification.id = :id and 
        notification.notificationStatus = :notificationStatus
        and notification.isDeleted = 0
        and appCst.customerType = 'MAIN_OWNER'
        """)
    Optional<InstallmentNotificationProjection> findFailedNotificationByIdToResend(@Param("id") Long id, @Param("notificationStatus") InstallmentNotificationStatus installmentNotificationStatus);



    @Modifying
    @Query("update InstallmentNotification noti set noti.notificationStatus = 'FAILED' where noti.notificationStatus = 'PENDING'")
    void updatePendingToFailed();
}
