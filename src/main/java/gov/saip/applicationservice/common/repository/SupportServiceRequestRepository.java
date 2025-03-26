package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceHelperInfoDto;
import gov.saip.applicationservice.common.enums.SupportServicePaymentStatus;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import gov.saip.applicationservice.common.model.projections.ApplicationSupportServiceProjectionDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface SupportServiceRequestRepository<E extends ApplicationSupportServicesType> extends BaseRepository<E, Long> {


    @Query("""
        select e from #{#entityName} e
        join fetch e.lkSupportServices lks
        left join fetch e.requestStatus st
        where e.id = :id
    """)
    Optional<E> findById(@Param("id") Long id);

    List<E> findByApplicationInfoIdAndLkSupportServicesCode(@Param("id") Long id, @Param("code") SupportServiceType code);
    List<E> findByApplicationInfoIdAndLkSupportServicesCodeOrderByCreatedDateDesc(@Param("id") Long id, @Param("code") SupportServiceType code);


    List<E> findByIdIn(List<ApplicationSupportServicesType> applicationInfoAndLkSupportServices);
    
    @Query(value = "SELECT concat('SS-', nextval('application.application_support_services_type_seq'))", nativeQuery = true)
    String getLastRequestNumber();

    @Modifying
    @Query("""
        update ApplicationSupportServicesType service set service.paymentStatus = :paymentStatus where service.id = :id
    """)
    void updatePaymentStatus(@Param("paymentStatus") SupportServicePaymentStatus paymentStatus, @Param("id")Long id);

    @Modifying
    @Query("""
        update ApplicationSupportServicesType service set service.paymentStatus = :paymentStatus, service.requestStatus.id = :statusId where service.id = :id
    """)
    void updatePaymentStatusAndRequestStatus(@Param("paymentStatus") SupportServicePaymentStatus paymentStatus, @Param("statusId") Integer statusId, @Param("id")Long id);

    @Modifying
    @Query("""
        update ApplicationSupportServicesType service set service.requestStatus.id = :statusId where service.id = :id
    """)
    void updateRequestStatus(@Param("statusId") Integer statusId, @Param("id")Long id);

    @Query("select ser.applicationInfo from ApplicationSupportServicesType ser where ser.id = :id")
    ApplicationInfo getApplicationByServiceId(@Param("id") Long id);

    @Query("select count(ser.id) from ApplicationSupportServicesType ser where ser.applicationInfo.id = :appId")
    Long countServicesByApplicationId(@Param("appId") Long appId);

    @Query("select case when (count(service) > 0)  then true else false end from ApplicationSupportServicesType service where service.applicationInfo.id = :appId AND service.requestStatus.code in :supportServiceRequestStatuses " +
            "and service.lkSupportServices.code not in :excludedUnderProcedureSupportServiceTypes ")
    boolean checkApplicationHasAnotherUnderProcedureRequest(@Param("appId") Long appId, @Param("supportServiceRequestStatuses") List<String> supportServiceRequestStatuses,
                                                            @Param("excludedUnderProcedureSupportServiceTypes") List<SupportServiceType> excludedUnderProcedureSupportServiceTypes);

    @Query("""
        select
        case when (count(service) > 0)  then true 
        else false 
        end 
        from ApplicationSupportServicesType service
        where service.applicationInfo.id = :appId 
        and service.lkSupportServices.code in :serviceType
        AND service.createdByCustomerCode = :currentApplicantCustomerCode
        AND service.requestStatus.code in :supportServiceRequestStatuses
""")
    boolean isThereUnderProcedureRequestsForCurrentApplicant(@Param("appId") Long appId,
                                                            @Param("supportServiceRequestStatuses") List<String> supportServiceRequestStatuses,
                                                            @Param("currentApplicantCustomerCode") String currentApplicantCustomerCode,
                                                            @Param("serviceType") List<SupportServiceType> serviceType);


    @Query("select service.requestStatus.code from ApplicationSupportServicesType service where service.id = :id ")
    String getSupportServiceStatusCodeByServiceId(@Param("id") Long id);

    @Query("""
        select e from #{#entityName} e
        join fetch e.lkSupportServices lks
        left join fetch e.requestStatus st
        where e.requestNumber = :requestNumber
    """)
    E getSupportServiceByRequestNumber(@Param("requestNumber") String requestNumber);
    
    @Query("""
    select e as request, ai.applicationNumber as applicationNumber,
    ai.ownerNameAr as ownerNameAr, ai.ownerNameEn as ownerNameEn,
    t.tagTypeDesc as markTag, t.markType as markType
    from #{#entityName} e
    join e.lkSupportServices lks
    left join e.requestStatus st
    join fetch e.applicationInfo ai
    left join TrademarkDetail t on t.applicationId = ai.id
    where e.id = :id
""")
    ApplicationSupportServiceProjectionDetails<E> findApplicationDetailsBySupportServiceId(@Param("id") Long id);

    @Modifying
    @Query("""
        update ApplicationSupportServicesType service set service.processRequestId = :processRequestId where service.id = :id
    """)
    void updateProcessRequestId(@Param("processRequestId") Long processRequestId, @Param("id")Long id);

   @Query("select service.requestNumber from ApplicationSupportServicesType service where service.id = :id ")
    String getSupportServiceRequestNumberCodeByServiceId(@Param("id") Long id);

    @Query(value = """
            SELECT asst.requestStatus.code as requestStatusCode, 
                   asst.modifiedDate as decisionDate
            FROM ApplicationSupportServicesType asst
            WHERE asst.id =
            (
                SELECT max(asst2.id) FROM ApplicationSupportServicesType asst2
                WHERE asst2.applicationInfo.id = :appId 
                  AND asst2.lkSupportServices.code = :supportServiceCode
            )
        """)
    Map<String, Object> getLastSupportServiceRequestStatusAndModifiedDate(@Param("appId") Long appId, @Param("supportServiceCode") SupportServiceType supportServiceCode);

    @Query(value = """
           SELECT max(asst.id) FROM ApplicationSupportServicesType asst
           WHERE asst.applicationInfo.id = :appId 
             AND asst.lkSupportServices.code = :supportServiceCode
        """)
    Long getLastSupportServiceRequestServiceId(@Param("appId") Long appId, @Param("supportServiceCode") SupportServiceType supportServiceCode);

    @Query("""
        select ser.requestStatus from ApplicationSupportServicesType ser
        where ser.id = :id
    """)
    LKSupportServiceRequestStatus getStatusBySupportServiceId(@Param("id") Long id);

    @Query("""
        select ser.processRequestId from ApplicationSupportServicesType ser
        where ser.id = :id
    """)
    String getProcessRequestIdById(Long id);

    boolean existsByIdAndCreatedByCustomerCode(Long parentServiceId, String customerCode);

    @Query("""
        select new gov.saip.applicationservice.common.dto.supportService.SupportServiceHelperInfoDto(ser.createdByCustomerCode, ser.applicationInfo.id)from ApplicationSupportServicesType ser
        where ser.id = :id
    """)
    SupportServiceHelperInfoDto getCreatedByCustomerCodeAndApplicationIdById(Long id);

    @Query(value = """
            select  asst.processRequestId
            from ApplicationSupportServicesType asst
            left join asst.requestStatus lssrs
            left join ApplicationInfo ai on asst.applicationInfo.id=ai.id
            left join ai.category lac
            where  (:requestNumber is null or  asst.requestNumber = :requestNumber)
                and (DATE(:fromFilingDate) is null or DATE(asst.createdDate) >= DATE(:fromFilingDate))
                and (DATE(:toFilingDate) is null or DATE(asst.createdDate) <= DATE(:toFilingDate))
                and (:supportServiceStatusCode is null or lssrs.code = :supportServiceStatusCode)
                and (:supportServiceTypeId is null or asst.lkSupportServices.id = :supportServiceTypeId)
                AND (:categoryId is null 
                    or (lac.id is null and asst.lkSupportServices.code = 'TRADEMARK_APPLICATION_SEARCH' and  :categoryId = (select c.id from LkApplicationCategory c where c.saipCode = 'TRADEMARK'))
                    or (lac.id is null and asst.lkSupportServices.code = 'PETITION_REQUEST_NATIONAL_STAGE' and :categoryId = (select c.id from LkApplicationCategory c where c.saipCode = 'PATENT'))    
                    or lac.id = :categoryId
                and asst.processRequestId is not null
                )
               """)
    List<Long> findProcessesRequestsIdsByFilters(@Param("requestNumber") String requestNumber, @Param("fromFilingDate") LocalDateTime fromFilingDate,
                                                 @Param("toFilingDate") LocalDateTime toFilingDate, @Param("supportServiceStatusCode") String supportServiceStatusCode,
                                                 @Param("supportServiceTypeId") Long supportServiceTypeId,
                                                 @Param("categoryId") Long categoryId);


@Query(" select ast from ApplicationSupportServicesType ast where ast.applicationInfo.id = :applicationId and ast.lkSupportServices.code = 'OWNERSHIP_CHANGE' and ast.requestStatus.code='TRANSFERRED_OWNERSHIP'")
    List<ApplicationSupportServicesType> getChangeOwnerShipServiceId(@Param("applicationId") Long applicationId);

}