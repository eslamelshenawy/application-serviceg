package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ApplicationCustomerDto;
import gov.saip.applicationservice.common.dto.ApplicationNotificationData;
import gov.saip.applicationservice.common.dto.KeyValueDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.ApplicationCustomer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationCustomerRepository extends BaseRepository<ApplicationCustomer, Long>{
    @Modifying
    @Query("delete from ApplicationCustomer appCust where appCust.customerId = :customerId and appCust.application.id = :appId")
    void deleteByAppAndCustomerId(@Param("customerId") Long customerId,  @Param("appId")Long appId);

    @Modifying
    @Query("delete from ApplicationCustomer appCust where appCust.application.id = :appId")
    void deleteByApplicationId(@Param("appId")Long appId);

    @Modifying
    @Query("delete from ApplicationCustomer appCust where appCust.customerCode = :customerCode and appCust.application.id = :appId")
    void deleteByAppAndCustomerCode(@Param("customerCode") String customerCode, @Param("appId")Long appId);

    @Modifying
    @Query("delete from ApplicationCustomer appCust where appCust.customerType = :applicationCustomerType and appCust.application.id in (:appIds)")
    void deleteApplicationCustomersByTypeAndAppIds(@Param("applicationCustomerType") ApplicationCustomerType applicationCustomerType, @Param("appIds") List<Long> appIds);

    @Modifying
    @Query("delete from ApplicationCustomer appCust where appCust.customerType = :applicationCustomerType and appCust.application.id in (:appId)")
    void deleteApplicationCustomersByTypeAndAppId(@Param("applicationCustomerType") ApplicationCustomerType applicationCustomerType, @Param("appId") Long appId);

    @Modifying
    @Query("update ApplicationCustomer appCust set appCust.customerType = :newType where appCust.customerType = :oldType and appCust.application.id = :appId")
    void updateApplicationCustomerTypeByApplication(@Param("newType") ApplicationCustomerType newType, @Param("oldType") ApplicationCustomerType oldType, @Param("appId") Long appId);

    @Query("select distinct new gov.saip.applicationservice.common.dto.ApplicationCustomerDto(appCust.customerId, appCust.customerType)  from ApplicationCustomer appCust where appCust.application.id = :appId")
    List<ApplicationCustomerDto> findAllApplicationCustomerTypeAndCustomerIdByApplicationId(@Param("appId") Long applicationId);

    @Query("""
        select ac.customerCode as value, ai.id as key
        from ApplicationCustomer ac
        join ac.application ai
        where ai.id in (:ids)
        and ac.customerType = :customerType
    """)
    List<KeyValueDto<Long, String>> getCustomerCodesByAppIdsAndCustomerType(@Param("ids")List<Long> ids, @Param("customerType") ApplicationCustomerType customerType);




    @Query("""
        select ac.customerCode as value, ai.id as key
        from ApplicationCustomer ac
        join ac.application ai
        where ai.id in (:ids)
        and ac.customerType = :customerType
    """)
    List<KeyValueDto<Long, String>> getCustomer(@Param("ids")List<Long> ids, @Param("customerType") ApplicationCustomerType customerType);


    @Query("""
        select ac from ApplicationCustomer ac
        where
        ac.application.id = :appId and
        (:customerType is null or ac.customerType = :customerType) and
        (:customerId is null or ac.customerId = :customerId) and
        (:customerCode is null or ac.customerCode = :customerCode)
    """)
    List<ApplicationCustomer> getAppCustomersByTypeOrCodeOrCustomerId(
            @Param("customerId") Long customerId,
            @Param("customerCode") String customerCode,
            @Param("customerType") ApplicationCustomerType customerType,
            @Param("appId") Long appId
    );


    @Query("""
        SELECT ac
        FROM ApplicationCustomer ac
        WHERE
        ac.application.id in (:apps)
        AND ac.customerType = :customerType
    """)
    List<ApplicationCustomer> getAppCustomersByIdsAndType(@Param("apps") List<Long> apps,
                                                @Param("customerType") ApplicationCustomerType applicationCustomerType);
    
    @Query("""
        SELECT ac
        FROM ApplicationCustomer ac
        WHERE
        ac.application.id = :appId
        AND ac.customerType = :customerType
    """)
    ApplicationCustomer getAppCustomerByAppIdAndType(@Param("appId") Long appId,
                                                          @Param("customerType") ApplicationCustomerType applicationCustomerType);

    @Query(value = """
            SELECT NEW gov.saip.applicationservice.common.dto.ApplicationNotificationData(ai.email  , ai.mobileCode , ai.mobileNumber , ai.id  ,ac.customerId,ac.customerType,ac.customerCode)
            FROM ApplicationCustomer ac
            JOIN ApplicationInfo ai ON ac.application.id = ai.id
            WHERE ai.id = :appId
            """)
    List<ApplicationNotificationData> findapplicationNotificationData(@Param("appId") Long appId);
    @Query(value = """
            SELECT NEW gov.saip.applicationservice.common.dto.ApplicationNotificationData(ai.email  , ai.mobileCode , ai.mobileNumber , ai.id  ,ac.customerId,ac.customerType,ac.customerCode)
            FROM ApplicationCustomer ac
            JOIN ApplicationInfo ai ON ac.application.id = ai.id
            WHERE ai.id = :appId and ac.customerType = :customerType
            """)
   ApplicationNotificationData findApplicationByCustomerTypeNotificationData(@Param("appId") Long appId,@Param("customerType")ApplicationCustomerType customerType);


        @Query(value = """
        SELECT ssc.customer_code
        FROM application.support_service_customer ssc
        JOIN application.application_support_services_type asst ON asst.id = ssc.support_service_id
        JOIN application.applications_info ai ON asst.application_info_id = ai.id
        JOIN application.lk_support_services lss on asst.lk_support_service_type_id = lss.id
        WHERE ai.id = :applicationId and lss.code in :servicesCodes
        
        UNION
        
        SELECT req.agent_customer_code
        FROM application.trademark_agency_requests req
        WHERE req.application_id = :applicationId
        
        UNION
        
        SELECT ac.customer_code
        FROM application.application_customers ac
        JOIN application.applications_info ai ON ac.application_id = ai.id
        WHERE ai.id = :applicationId
        
        UNION
        
        SELECT c.code
        from application.change_ownership_request cor
        left join application.application_support_services_type asst on asst.id= cor.id
        left join customer.customer c on cor.customer_id = c.id
        WHERE asst.application_info_id = :applicationId and asst.is_deleted = 0
""", nativeQuery = true)
    List<String> getApplicationVerifiedCustomerCodes(@Param("applicationId") Long applicationId, @Param("servicesCodes")List<String> servicesCodes);

    @Query("""
            select ac from ApplicationCustomer ac where ac.application.id= :applicationId and ac.customerType ='MAIN_OWNER'
            """)
    ApplicationCustomer getCustomerCodeFromApplicationCustomer(@Param("applicationId") Long applicationId);

    @Query("""
        select distinct appCust.customerId 
        from ApplicationCustomer appCust 
        where appCust.application.id in (
            select ac.application.id 
            from ApplicationCustomer ac 
            where ac.customerId = :customerId
        )
        and appCust.customerType = 'AGENT'
        """)
    List<Long> findAllAgentsByApplicationIdsId(@Param("customerId") Long customerId);
}
