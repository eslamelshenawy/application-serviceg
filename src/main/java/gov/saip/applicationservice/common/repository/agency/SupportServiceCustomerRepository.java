package gov.saip.applicationservice.common.repository.agency;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.KeyValueDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.common.model.SupportServiceCustomer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportServiceCustomerRepository extends BaseRepository<SupportServiceCustomer, Long> {


    @Query("""
            select ssc from SupportServiceCustomer ssc where ssc.applicationSupportServices.id= :applicationSupportServicesId and  ssc.customerType= :customerType""")
    SupportServiceCustomer findByApplicationSupportServicesId(Long applicationSupportServicesId,ApplicationCustomerType customerType);

    @Query("""
            select ssc.customerType from SupportServiceCustomer ssc where ssc.applicationSupportServices.id= :applicationSupportServicesId""")
    List<ApplicationCustomerType> findSupportServiceCustomerTypeByServiceId(Long applicationSupportServicesId);



    @Query("""
        select ssc.customerType as key, ssc.customerCode as value
        from SupportServiceCustomer ssc
        join ssc.applicationSupportServices ss
        where ss.id = :supportServicesId
    """)
    List<KeyValueDto<ApplicationCustomerType, String>> getServiceCustomerCodes(@Param("supportServicesId") Long supportServicesId);

    @Query(value = """
        select  ssc.customer_code
                from application.support_service_customer ssc
                join application.application_support_services_type ss on ssc.support_service_id = ss.id
                where ss.id = :supportServicesId
        union
                select ac.customer_code
                from application.applications_info ai
                join application.application_customers ac on ac.application_id  = ai.id
                join application.application_support_services_type sst on sst.application_info_id = ai.id
                where sst.id = :supportServicesId
        union
                SELECT req.agent_customer_code
                FROM application.trademark_agency_requests req
                join application.application_support_services_type asst on req.application_id = asst.application_info_id
                WHERE asst.id = :supportServicesId
""", nativeQuery = true)
    List<String> getCustomerCodesByServiceId(@Param("supportServicesId") Long supportServicesId);

    @Query("""
        select ssc.customerCode
        from SupportServiceCustomer ssc
        where ssc.applicationSupportServices.id = :id and ssc.customerType =:applicationCustomerType
""")
    List<String> getAgentsCustomerCodeByServiceId(@Param("id")Long id,@Param("applicationCustomerType") ApplicationCustomerType applicationCustomerType);
   @Query("""
        select ssc.customerId
        from SupportServiceCustomer ssc
        where ssc.applicationSupportServices.id = :id and ssc.customerType =:applicationCustomerType
""")
    List<Long> getCustomerIdsByServiceId(@Param("id")Long id, @Param("applicationCustomerType") ApplicationCustomerType applicationCustomerType);
    
    @Query("""
            select ssc from SupportServiceCustomer ssc where ssc.applicationSupportServices.id= :applicationSupportServicesId and  ssc.customerType= :customerType""")
    List<SupportServiceCustomer> findBySupportServicesId(Long applicationSupportServicesId,ApplicationCustomerType customerType);

    @Query("""
            select ssc from SupportServiceCustomer ssc 
            join ssc.applicationSupportServices asst
            join asst.applicationInfo ai
            where ai.id = :applicationId 
            and  ssc.customerType= :customerType""")
    List<SupportServiceCustomer> findBySupportServiceCustomerByApplicationIdAndType(Long applicationId, ApplicationCustomerType customerType);
    
}
