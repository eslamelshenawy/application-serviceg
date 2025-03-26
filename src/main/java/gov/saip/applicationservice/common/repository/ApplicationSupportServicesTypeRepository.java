package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.dto.ApplicationPreviousRequests;
import gov.saip.applicationservice.common.dto.ChangeOwnerShipReportDTO;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
public interface ApplicationSupportServicesTypeRepository extends SupportServiceRequestRepository<ApplicationSupportServicesType> {
    List<ApplicationSupportServicesType> findByApplicationInfoId(Long appId);

    @Query("""
        SELECT asst FROM ApplicationSupportServicesType asst
        WHERE asst.applicationInfo.id = :appId
        AND asst.requestStatus.code IN :codes
        And (
        (:isEditImageService = true And asst.appealRequestType.appealRequestType ='CHANGE_TM_IMAGE_SERVICE')
        or (:isEditImageService = false)
        )
""")
    List<ApplicationSupportServicesType> findByApplicationInfoIdAndRequestStatus(@Param("appId") Long appId, @Param("codes") List<String> codes, @Param("isEditImageService") Boolean isEditImageService);


    @Query("""
        SELECT asst FROM ApplicationSupportServicesType asst
        JOIN LicenceRequest lr ON lr.id = asst.id
        WHERE asst.applicationInfo.id = :appId
        AND asst.requestStatus.code = :status
        AND asst.lkSupportServices.code = :type
        AND ( asst.createdByCustomerCode = :customerCode OR lr.customerId = :customerId )
    """)
    List<ApplicationSupportServicesType> findByApplicationInfoIdAndRequestStatusAndTypeAndCustomerCode(@Param("appId") Long appId, @Param("status") String status,
                                                                                                       @Param("type") SupportServiceType type,
                                                                                                       @Param("customerCode") String customerCode,
                                                                                                       @Param("customerId") Long customerId);
    @Query("""
        SELECT asst FROM ApplicationSupportServicesType asst
        JOIN ChangeOwnershipRequest co ON co.id = asst.id
        WHERE asst.applicationInfo.id = :appId
        AND asst.requestStatus.code = :status
        AND asst.lkSupportServices.code = :type
        AND ( asst.createdByCustomerCode = :customerCode OR co.customerId = :customerId )
    """)
    List<ApplicationSupportServicesType> hasLicensingModificationAndOwnerShipPermission(@Param("appId") Long appId, @Param("status") String status,
                                                                                                       @Param("type") SupportServiceType type,
                                                                                                       @Param("customerCode") String customerCode,
                                                                                                       @Param("customerId") Long customerId);

    @Query(value = """
            SELECT DISTINCT new gov.saip.applicationservice.common.dto.ApplicationPreviousRequests(b.id, ai.id, b.requestNumber, ai.titleAr,ai.titleEn,b.createdDate,
            ai.category,b.lkSupportServices,b.paymentStatus, b.requestStatus)       
            FROM ApplicationSupportServicesType b
            join b.applicationInfo ai
            left join ai.applicationCustomers ac
            left join b.requestStatus reqStatus
            WHERE (:customerId is null or 
                (ac.customerId = :customerId and b.lkSupportServices.code != 'OPPOSITION') or 
                (b.createdByUser = :username and b.lkSupportServices.code = 'OPPOSITION' or b.lkSupportServices.code='LICENSING_REGISTRATION')) 
            AND(:appId is null or ai.id = :appId) 
            AND (:query is null or CONCAT( UPPER(ai.titleEn) , UPPER(ai.titleAr), b.requestNumber) LIKE CONCAT('%',UPPER(:query),'%') )
            AND ( ((cast(:fromDate as date) is null ) or (cast(:toDate as date) is null )) or ai.createdDate between :fromDate and :toDate )
            AND (b.applicationInfo.id = ai.id)
            AND (:supportServiceTypeId is null or b.lkSupportServices.id = :supportServiceTypeId)
            AND (:requestStatus is null or b.paymentStatus != :requestStatus)
            """
    )
    Page<ApplicationPreviousRequests> getPreviousApplicationRequestsList(@Param("query") String query, @Param("fromDate") LocalDateTime fromDate,
                                                                         @Param("toDate") LocalDateTime toDate, /* @Param("userIds") List<Long> userIds,*/
                                                                         @Param("appId") Long appId, @Param("supportServiceTypeId")Long supportServiceTypeId,
                                                                         @Param("customerId") Long customerId,
                                                                         @Param("requestStatus") SupportServicePaymentStatus requestStatus,
                                                                         @Param("username") String username,
                                                                         Pageable pageable);



    @Query(value = """
            SELECT DISTINCT new gov.saip.applicationservice.common.dto.ApplicationPreviousRequests(b.id, ai.id, b.requestNumber, ai.titleAr,ai.titleEn,b.createdDate,
            cat, lkser, b.paymentStatus, b.requestStatus, ssc.customerApplicationAccessLevel, rev.licenceRequest.id, op.revokeLicenceRequest.id)
            FROM ApplicationSupportServicesType b
            left join b.applicationInfo ai on b.applicationInfo.id = ai.id
            left join LkApplicationCategory cat on cat.id = ai.category.id
            left join SupportServiceCustomer ssc on b.id = ssc.applicationSupportServices.id
            left join b.requestStatus reqStatus
            left join b.lkSupportServices lkser
            left join RevokeLicenceRequest rev on b.id = rev.id
            left join OppositionRevokeLicenceRequest op on b.id = op.id
            
            WHERE
            (b.createdByCustomerCode = :currentCustomerCode or (ssc.customerType = :customerType AND ssc.customerCode = :currentCustomerCode))
            AND (:categoryId is null or cat.id = :categoryId)
            AND (:supportServiceTypeId is null or lkser.id = :supportServiceTypeId)
            AND(:appId is null or ai.id = :appId or ai is null)
            AND (:query is null or UPPER(b.requestNumber) LIKE CONCAT('%',UPPER(:query),'%') or  UPPER(ai.applicationNumber) LIKE CONCAT('%',UPPER(:query),'%') OR UPPER(ai.applicationRequestNumber) LIKE CONCAT('%',UPPER(:query),'%') or UPPER(ai.titleEn) LIKE CONCAT('%',UPPER(:query),'%') OR UPPER(ai.titleAr) LIKE CONCAT('%',UPPER(:query),'%') )
            AND ( ((cast(:fromDate as date) is null ) or (cast(:toDate as date) is null )) or b.createdDate >= :fromDate and  b.createdDate <= :toDate)
            """
    )
    Page<ApplicationPreviousRequests> getExternalPreviousRequest(@Param("query") String query, @Param("fromDate") LocalDateTime fromDate,
                                                                         @Param("toDate") LocalDateTime toDate,
                                                                         @Param("appId") Long appId, @Param("supportServiceTypeId")Long supportServiceTypeId,
                                                                         @Param("currentCustomerCode") String currentCustomerCode,
                                                                         @Param("categoryId") Long categoryId,
                                                                         @Param("customerType") ApplicationCustomerType customerType,
                                                                         Pageable pageable);


    @Query(value = """
            SELECT DISTINCT new gov.saip.applicationservice.common.dto.ApplicationPreviousRequests(b.id, ai.id, b.requestNumber, ai.titleAr,ai.titleEn,b.createdDate,
            cat, lkser,b.paymentStatus, b.requestStatus, rev.licenceRequest.id, op.revokeLicenceRequest.id)
            FROM ApplicationSupportServicesType b
            left join b.applicationInfo ai on b.applicationInfo.id = ai.id
            left join b.requestStatus lssrs
            left join b.lkSupportServices lkser
            left join lkser.applicationCategories cat on ai.category.id = cat.id
            left join RevokeLicenceRequest rev on b.id = rev.id
            left join OppositionRevokeLicenceRequest op on b.id = op.id
            left join SupportServiceCustomer ssc on b.id = ssc.applicationSupportServices
            WHERE
            (:applicationId is null or ai.id = :applicationId or ai is null)
            
            AND (:categoryId is null 
                    or (cat.id is null and lkser.code = 'TRADEMARK_APPLICATION_SEARCH' and  :categoryId = (select c.id from LkApplicationCategory c where c.saipCode = 'TRADEMARK'))
                    or (cat.id is null and lkser.code = 'PETITION_REQUEST_NATIONAL_STAGE' and :categoryId = (select c.id from LkApplicationCategory c where c.saipCode = 'PATENT'))    
                    or cat.id = :categoryId
                )
            
            
            AND (:supportServiceTypeId is null or lkser.id = :supportServiceTypeId)
            AND (:query is null or UPPER(b.requestNumber) LIKE CONCAT('%',UPPER(:query),'%') 
            OR  UPPER(ai.applicationNumber) LIKE CONCAT('%',UPPER(:query),'%') 
            OR UPPER(ai.applicationRequestNumber) LIKE CONCAT('%',UPPER(:query),'%') 
            OR UPPER(ai.titleEn) LIKE CONCAT('%',UPPER(:query),'%') OR UPPER(ai.titleAr) LIKE CONCAT('%',UPPER(:query),'%') )
            AND (:supportServiceStatusCode is null or lssrs.code in :supportServiceStatusCode)
            AND ( ((cast(:startDate as date) is null ) or (cast(:endDate as date) is null )) or b.createdDate >= :startDate and b.createdDate <= :endDate )
            AND (:requestStatus is null or b.paymentStatus != :requestStatus)
            AND (:applicationNumber is null or b.requestNumber = :applicationNumber)
            AND ( (:ownerName is null or UPPER(ai.ownerNameAr) LIKE CONCAT('%',UPPER(:ownerName),'%'))
                OR (:ownerName is null or UPPER(ai.ownerNameEn) LIKE CONCAT('%',UPPER(:ownerName),'%')) )
            AND (:applicationTitleAr is null or UPPER(ai.titleAr) LIKE CONCAT('%',UPPER(:applicationTitleAr),'%'))
            AND (:applicationTitleEn is null or UPPER(ai.titleEn) LIKE CONCAT('%',UPPER(:applicationTitleEn),'%'))
            AND (:agentName is null or (COALESCE (:customerIds) IS NULL or (ssc.customerId IN (:customerIds) and ssc.customerType = 'AGENT')))
            """

    )
    Page<ApplicationPreviousRequests> getInternalPreviousRequest(@Param("query") String query, @Param("startDate") LocalDateTime startDate,
                                                                 @Param("endDate") LocalDateTime endDate,
                                                                 @Param("applicationId") Long applicationId, @Param("supportServiceTypeId") Long supportServiceTypeId,
                                                                 @Param("requestStatus") SupportServicePaymentStatus requestStatus,
                                                                 @Param("categoryId") Long categoryId, @Param("applicationNumber") String applicationNumber,
                                                                 @Param("ownerName") String ownerName, @Param("agentName") String agentName,
                                                                 @Param("applicationTitleAr") String applicationTitleAr, @Param("applicationTitleEn") String applicationTitleEn,
                                                                 @Param("supportServiceStatusCode") List<String> supportServiceStatusCode, @Param("customerIds") List<Long> customerIds, Pageable pageable);

    @Query("""
            SELECT new gov.saip.applicationservice.common.dto.ChangeOwnerShipReportDTO(
            ai.id, co.customerId, co.modifiedDate, ai.applicationNumber)
            FROM ChangeOwnershipRequest co
            JOIN ApplicationInfo ai ON ai.id = co.applicationInfo.id
            AND co.modifiedDate BETWEEN :startDate AND :endDate
            """)
    List<ChangeOwnerShipReportDTO> getOwnerShipChangedData(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);




    @Query("""
            SELECT co.oldOwnerId 
            FROM ChangeOwnershipRequest co
            where co.applicationInfo.id = :appId
            AND co.requestStatus.code = :status
            order by co.createdDate  ASC 
            """)
    List<Long> getoldestowner(@Param("appId") Long appId ,  @Param("status") String status) ;


    @Query("select case when (count(a) > 0)  then true else false end from ApplicationSupportServicesType a where a.id = :id")
    boolean applicationSupportServicesTypeExists(@Param("id") Long id);

    @Query("""
        select asst.processRequestId
        from ApplicationSupportServicesType asst
        where 
          asst.requestNumber = :requestNumber
          or asst.requestNumber = :query
         or asst.applicationInfo.titleAr  like concat('%', :query , '%')  or upper(asst.applicationInfo.titleEn)  like concat('%', UPPER(:query) , '%') or asst.applicationInfo.applicationNumber like concat('%', :query, '%') or asst.applicationInfo.applicationRequestNumber like concat('%', :query, '%')
""")
    List<Long> getProcessRequestIdsBySearchCriteria(@Param("query") String query, @Param("requestNumber") String requestNumber);



@Query("select a.applicationInfo.id from ApplicationSupportServicesType a" +
        " join ApplicationCustomer ac  on ac.application.id = a.applicationInfo.id" +
        " join a.lkSupportServices sv " +
        " where sv.code= :serviceCode " +
        "And (:customerId is null or ac.customerId = :customerId ) " +
        "And (a.paymentStatus = 'PAID') ")
    List<Long> getApplicationIdsForSpecificSupportService(@Param("serviceCode") SupportServiceType serviceCode,@Param("customerId")Long customerId);

    @Query("""
    select asst.applicationInfo.id
    from ApplicationSupportServicesType asst
    where asst.requestNumber= :serviceNumber
""")
    Long getApplicationIdByServiceNumber(@Param("serviceNumber") String serviceNumber);


    @Query("""
        select t.id
        from ApplicationSupportServicesType t
        where
        t.id =(
        select max(t2.id)
        from ApplicationSupportServicesType t2
        join t2.applicationInfo ai2
        join t2.requestStatus st2
        join t2.lkSupportServices lk2
        where ai2.id = :appId
        and (:type is null or lk2.code = :type)
        and (COALESCE(:status) is null or st2.code in (:status))
        )
    """)
    Long getLastSupportServiceByTypeAndApplicationِAndStatus(
            @Param("type")SupportServiceType type,
            @Param("appId")Long appId,
            @Param("status")List<String> status);


    @Modifying
    @Query("""
        update ApplicationSupportServicesType ser set ser.requestStatus.id =
        (select st.id from LKSupportServiceRequestStatus st where st.code = :code)
        where ser.id = :id
      """)
    void updatePaymentStatusAndRequestStatus(@Param("id") Long id, @Param("code")  String code);


    @Query("""
        select ser from ApplicationSupportServicesType ser
        where ser.id = (
            select max(ser2.id) from ApplicationSupportServicesType ser2
            join ser2.applicationInfo ai
            join ser2.lkSupportServices lks
            where
            lks.code = :serviceCode and ai.id = :appId
        )
    """)
    ApplicationSupportServicesType getLastServiceForApplicationByServiceCode(@Param("appId") Long appId, @Param("serviceCode")  SupportServiceType serviceCode);

    @Query("select count(a) > 0 from ApplicationSupportServicesType a where a.applicationInfo.id = :appId and a.requestStatus.code = 'LICENSED'")
    boolean applicationSupportServicesTypeLicencedExists(@Param("appId") Long appId);


    @Query("""
    select ser.lkSupportServices.code
    from ApplicationSupportServicesType ser
    where ser.id = :id
""")
    SupportServiceType getServiceTypeByServiceId(@Param("id") Long id);

    @Query(""" 
            SELECT reqStat.code FROM ApplicationSupportServicesType asst
            JOIN asst.requestStatus reqStat
            WHERE asst.id = :serviceId
            """)
    String getServiceTypeStatus(@Param("serviceId") Long serviceId);
}

