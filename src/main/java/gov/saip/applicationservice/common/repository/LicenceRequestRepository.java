package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.dto.LicenceRequestListDto;
import gov.saip.applicationservice.common.enums.LicenceTypeEnum;
import gov.saip.applicationservice.common.model.LicenceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenceRequestRepository extends SupportServiceRequestRepository<LicenceRequest> {

    @Query("""
            select req.licenceTypeEnum from LicenceRequest req where req.id = :id 
           """)
    Optional<String> findLicenceTypeEnumById(Long id);




    @Query("""
       SELECT  distinct lr.customerId from LicenceRequest lr where lr.applicationInfo.id = :applicationInfoId
       """)
    List<Long> getAllIdsOfLicensedCustomers(Long applicationInfoId);

    @Query("select count(lic) > 0 from LicenceRequest lic" +
            " where" +
            " lic.applicationInfo.id = :applicationId and lic.requestStatus.code like 'LICENSED' ")
    boolean checkApplicationHaveLicence(Long applicationId);
   @Query(" select rq from LicenceRequest rq where rq.requestNumber = :requestNumber")
    LicenceRequest getLienceRequestsByRquestNumber(String requestNumber);
    @Query("SELECT lr.id FROM RevokeLicenceRequest rlr " +
            "JOIN rlr.licenceRequest lr " +
            "JOIN ApplicationSupportServicesType asst ON asst.id = lr.id " +
            "WHERE asst.requestNumber = :requestNumber")
    List<Long> getCheckedLicenceRequestByRequestNumber( String requestNumber);
    @Query("""
            select new gov.saip.applicationservice.common.dto.LicenceRequestListDto
            (asst.id, asst.requestNumber, ai.filingDate, ai.titleEn, ai.titleAr
            , lr.requestStatus ,lr.notes, lr.fromDate, lr.toDate, ai.id)
             from ApplicationSupportServicesType asst
            JOIN  LicenceRequest lr ON asst.id = lr.id
            join  ApplicationInfo  ai ON asst.applicationInfo.id = ai.id
            JOIN  asst.requestStatus lssrs
            where lssrs.code in (:approveStatus)
            and (:query is null
             OR asst.requestNumber like :query
             OR ai.applicationNumber like :query
             OR UPPER(ai.titleAr) LIKE CONCAT('%', UPPER(:query), '%')
             OR UPPER(ai.titleEn) LIKE CONCAT('%', UPPER(:query), '%')
             OR UPPER(lr.notes) LIKE CONCAT('%', UPPER(:query), '%'))
            and ( asst.createdByCustomerCode = :currentCustomerCode 
                OR lr.customerId = :currentCustomerId
                OR  :currentCustomerCode  in (SELECT a.customerCode FROM ApplicationRelevantType a 
                                        join a.applicationInfo info
                                        where 
                                        info.id = ai.id  AND a.type = 'Applicant_MAIN'))
                                       
            and lr.id not in ( select lreq.licenceRequest.id from RevokeLicenceRequest lreq where
                                       lreq.requestStatus.code in ('OPPOSITION_WATING' , 'COMPLETED', 'APPROVED', 'UNDER_OPPOSITION'))                           
            """)
    Page<LicenceRequestListDto> getAllApprovedLicenseRequests(@Param("approveStatus") List<String> approveStatus
            , @Param("query") String query, Pageable pageable, @Param("currentCustomerCode") String currentCustomerCode, @Param("currentCustomerId") Long currentCustomerId );

    @Query("""
        select new gov.saip.applicationservice.common.dto.LicenceRequestListDto
        (rlr.id, asst.requestNumber, ai.filingDate, ai.titleEn, ai.titleAr
        , rlr.requestStatus, lr.notes, lr.fromDate, lr.toDate, ai.id)


        from RevokeLicenceRequest  rlr 
        join ApplicationSupportServicesType  rasst ON rasst.id = rlr.id
        join rasst.requestStatus rlssrs
        
        
        JOIN LicenceRequest lr ON rlr.licenceRequest.id = lr.id
        JOIN ApplicationSupportServicesType asst ON asst.id = lr.id
        join asst.requestStatus  lssrs
        
        join ApplicationInfo  ai ON asst.applicationInfo.id = ai.id
        
        where rlssrs.code in (:status)
        and (:query is null
         OR asst.requestNumber like :query
         OR ai.applicationNumber like :query
         OR UPPER(ai.titleAr) LIKE CONCAT('%', UPPER(:query), '%')
         OR UPPER(ai.titleEn) LIKE CONCAT('%', UPPER(:query), '%')
         OR UPPER(lr.notes) LIKE CONCAT('%', UPPER(:query), '%'))
        and ( rasst.createdByCustomerCode = :currentCustomerCode 
                OR lr.customerId = :currentCustomerId
                OR  :currentCustomerCode  = (SELECT a.customerCode FROM ApplicationRelevantType a 
                                        join a.applicationInfo info
                                        where 
                                        info.id = ai.id  AND a.type = 'Applicant_MAIN'))                               
        """)
    Page<LicenceRequestListDto> getAllRevokedLicenseRequests(@Param("status") List<String> status
            , @Param("query") String query, Pageable pageable, @Param("currentCustomerCode") String currentCustomerCode, @Param("currentCustomerId") Long currentCustomerId );



    @Query("""
        select new gov.saip.applicationservice.common.dto.LicenceRequestListDto
        (orlr.id, asst.requestNumber, ai.filingDate, ai.titleEn, ai.titleAr
        ,orlr.requestStatus, lr.notes, lr.fromDate, lr.toDate, ai.id)
        
        from OppositionRevokeLicenceRequest orlr 
        JOIN ApplicationSupportServicesType  orasst ON orasst.id = orlr.id
        JOIN orasst.requestStatus orlssrs
        
        join RevokeLicenceRequest  rlr ON orlr.revokeLicenceRequest.id = rlr.id
        join ApplicationSupportServicesType  rasst ON rasst.id = rlr.id
        join rasst.requestStatus rlssrs
        
        
        JOIN LicenceRequest lr ON rlr.licenceRequest.id = lr.id 
        join ApplicationSupportServicesType asst  ON asst.id = lr.id
        join asst.requestStatus lssrs
        
        join ApplicationInfo  ai ON asst.applicationInfo.id = ai.id
      
        
        where orlssrs.code in (:status)
        and (:query is null
         OR asst.requestNumber like :query
         OR ai.applicationNumber like :query
         OR UPPER(ai.titleAr) LIKE CONCAT('%', UPPER(:query), '%')
         OR UPPER(ai.titleEn) LIKE CONCAT('%', UPPER(:query), '%')
         OR UPPER(lr.notes) LIKE CONCAT('%', UPPER(:query), '%'))
        and ( orasst.createdByCustomerCode = :currentCustomerCode 
                OR lr.customerId = :currentCustomerId
                OR  :currentCustomerCode  = (SELECT a.customerCode FROM ApplicationRelevantType a 
                                        join a.applicationInfo info
                                        where 
                                        info.id = ai.id  AND a.type = 'Applicant_MAIN'))                              
        """)
    Page<LicenceRequestListDto> getAllOppositionLicenseRequests(@Param("status") List<String> status,
                                                                @Param("query") String query, Pageable pageable, @Param("currentCustomerCode") String currentCustomerCode, @Param("currentCustomerId") Long currentCustomerId );

    @Query("""
            select req.customerId from LicenceRequest req where req.id = :id 
           """)
    Long findLicenceCustomerId(Long id);
    @Query("""
            select new gov.saip.applicationservice.common.dto.LicenceRequestListDto
            (asst.id, asst.requestNumber, ai.filingDate, ai.titleEn, ai.titleAr
            , lr.requestStatus ,lr.notes, lr.fromDate, lr.toDate, ai.id,lr.licenceValidityNumber)
             from LicenceRequest lr
            left join  ApplicationSupportServicesType asst on  lr.id = asst.id
            left join  ApplicationInfo  ai ON  ai.id =asst.applicationInfo.id
            left JOIN  ChangeOwnershipRequest cor on cor.id =asst.id
            left join RevokeLicenceRequest rlr on rlr.licenceRequest.id = lr.id
            where asst.requestStatus.code  in('LICENSED') and  (:appId is null or ai.id = :appId)
            and (asst.requestStatus.code not in ('OPPOSITION_WATING' , 'COMPLETED', 'APPROVED', 'UNDER_OPPOSITION'))                
            """)
    List<LicenceRequestListDto> getAllApprovedLicensedRequests( @Param("appId") Long appId);

    @Modifying
    @Transactional
    @Query("""
             update LicenceRequest lr set  lr.licenceValidityNumber = :licenceValidityNumber
             where lr.applicationInfo.id = :appId
             and lr.licenceTypeEnum = 'CONTRACTUAL' and lr.id = :mainRequestId
         """)
    void changeLicenceValidityNumber(@Param("appId") Long appId,@Param("licenceValidityNumber") Integer licenceValidityNumber, @Param("mainRequestId") Long mainRequestId);

    @Modifying
    @Transactional
    @Query("""
            update LicenceRequest lr set lr.licenceTypeEnum = 'CANCEL_LICENCE'
            where lr.applicationInfo.id = :appId
            and lr.id = :mainRequestId
        """)
    void makeCancelLicenceRequest(@Param("appId") Long appId, @Param("mainRequestId") Long mainRequestId);

    @Query("select count(lic) > 0 from LicenceRequest lic" +
            " where" +
            " lic.id = :id and lic.licenceTypeEnum = 'CANCEL_LICENCE' and lic.requestStatus.code = 'LICENSED'")
    boolean checkApplicationCancelLicence(Long id);



}
