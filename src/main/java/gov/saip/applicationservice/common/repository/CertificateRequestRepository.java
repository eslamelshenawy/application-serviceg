package gov.saip.applicationservice.common.repository;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ApplicationInfoResultDto;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestProj;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestProjection;
import gov.saip.applicationservice.common.dto.certs.CertificateRequestVerificationDto;
import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.CertificateRequest;
import gov.saip.applicationservice.common.model.LkCertificateStatus;
import gov.saip.applicationservice.common.model.LkCertificateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRequestRepository extends BaseRepository<CertificateRequest, Long> {

    @Query(value = """
       SELECT cr.id as certificateRequestId, 
              art.type as applicationRelevantType, 
              ai.id AS applicationId, 
              cr.created_date AS depositDate,
              (CASE WHEN (ai.application_request_number IS NOT NULL) 
                    THEN ai.application_request_number ELSE ai.application_number END) AS applicationNumber,
              ai.title_ar AS applicationTitleAr, 
              ai.title_en AS applicationTitleEn,
              cr.certificate_type_id AS certificateType, 
              cr.certificate_status_id AS certificateStatus, 
              art.customer_code AS customerCode
       FROM application.certificates_request cr
       JOIN application.applications_info ai ON ai.id = cr.application_info_id
       JOIN application.application_customers ac ON ai.id = ac.application_id
       JOIN application.application_relevant_type art ON ai.id = art.application_info_id
       JOIN application.application_support_services_type supp ON ai.id = supp.application_info_id
       JOIN application.lk_support_service_type st ON supp.lk_support_service_type_id = st.id
       LEFT JOIN application.lk_application_category cat ON ai.lk_category_id = cat.id  
       WHERE cr.is_deleted = 0
         AND art.is_deleted = 0
         AND (:requestType IS NULL OR ai.lk_category_id = CAST(:requestType AS BIGINT))
         AND (:serviceType IS NULL OR st.type = CAST(:serviceType AS VARCHAR))
            AND (:depositDate IS NULL
                 OR (cr.created_date >= CAST(:depositDate AS TIMESTAMP)
                     AND cr.created_date < CAST(:depositDate AS TIMESTAMP) + '1 day'))
        AND (:requestNumber IS NULL OR cr.request_number  || '' LIKE '%' || COALESCE(:requestNumber, '') || '%')
        AND (:applicationNumber IS NULL OR LOWER(ai.application_number) LIKE LOWER(CONCAT('%', :applicationNumber, '%')))
       """, nativeQuery = true)
    Page<CertificateRequestProj> listCertificateRequest(
            Pageable pageable,
            @Param("applicationNumber") String applicationNumber,
            @Param("requestType") Long requestType,
            @Param("serviceType") String serviceType,
            @Param("depositDate") LocalDate depositDate,
            @Param("requestNumber") String requestNumber
            );



    //should check the status?
    @Query("""
            SELECT 
                cr.id as certificateRequestId, art as applicationRelevantType, ai.id AS applicationId, ai.filingDate AS depositDate, 
                ai.applicationNumber As applicationNumber, ai.titleAr As applicationTitleAr, ai.titleEn As applicationTitleEn,
                cr.certificateType AS certificateType, cr.certificateStatus AS certificateStatus, art.customerCode as customerCode
            FROM CertificateRequest cr 
            LEFT JOIN FETCH ApplicationInfo ai ON ai.id = cr.applicationInfo.id 
            LEFT JOIN ApplicationRelevantType art on ai.id = art.applicationInfo.id 
            WHERE (:id IS NULL OR cr.id = :id) 
            AND (:applicationNumber IS NULL 
                  OR UPPER(ai.applicationNumber) LIKE CONCAT('%',UPPER(:applicationNumber),'%') 
                  OR UPPER(ai.grantNumber) LIKE CONCAT('%',UPPER(:applicationNumber),'%') 
                  OR UPPER(ai.applicationRequestNumber) LIKE CONCAT('%',UPPER(:applicationNumber),'%') 
                  OR ai.idOld LIKE UPPER('%:applicationNumber%') 
               )
            AND (:applicationId IS NULL OR ai.id = :applicationId )
            AND (:applicationTitle IS NULL OR ai.titleAr = :applicationTitle OR ai.titleEn = :applicationTitle) 
            AND (:certificateTypeName IS NULL OR cr.certificateType.nameAr = :certificateTypeName OR cr.certificateType.nameEn = :certificateTypeName) 
            AND (:certificateStatusName IS NULL OR cr.certificateStatus.nameAr = :certificateStatusName OR cr.certificateStatus.nameEn = :certificateStatusName) 
            AND (:depositDate IS NULL OR Date(ai.filingDate) = :depositDate) 
            AND (:customerCode IS NULL OR art.customerCode = :customerCode) 
            AND (COALESCE (:customersCodes) IS NULL OR art.customerCode in :customersCodes)
            AND (:statusCode IS NULL OR cr.certificateStatus.code = :statusCode) 
            AND art.type = :relevantType
            """
    )
    Page<CertificateRequestProjection> listPreviousCertificateRequests(@Param("id") Long id,
                                                               @Param("applicationNumber") String applicationNumber,
                                                               @Param("applicationTitle") String applicationTitle,
                                                               @Param("certificateTypeName") String certificateTypeName,
                                                               @Param("certificateStatusName") String certificateStatusName,
                                                               @Param("depositDate") LocalDate depositDate,
                                                               @Param("customerCode") String customerCode,
                                                               @Param("customersCodes") List<String> customersCodes,
                                                               @Param("statusCode") String statusCode,
                                                               @Param("relevantType") ApplicationRelevantEnum relevant,
                                                               @Param("applicationId") Long applicationId,
                                                               Pageable pageable);
    
    @Query("SELECT MAX(cr.serial) FROM CertificateRequest cr")
    Long getMaxId();
    
    @Query("SELECT new gov.saip.applicationservice.common.dto.certs.CertificateRequestVerificationDto(" +
            "ai.id, ai.applicationNumber, ai.titleAr, ai.titleEn, cr.certificateType, ai.filingDate, cr.requestNumber, ai.applicationRequestNumber, " +
            "c.saipCode, c.applicationCategoryDescAr, c.applicationCategoryDescEn) " +
            "FROM CertificateRequest cr " +
            "LEFT JOIN ApplicationInfo ai ON ai.id = cr.applicationInfo.id " +
            "LEFT JOIN LkApplicationCategory c on c.id = ai.category.id " +
            "WHERE (:applicationNumber IS NULL OR ai.applicationNumber = :applicationNumber OR ai.grantNumber = :applicationNumber OR ai.idOld = :applicationNumber OR ai.applicationRequestNumber = :applicationNumber) " +
            " AND (:applicationId IS NULL OR ai.id = :applicationId ) " +
            "AND (:requestNumber IS NULL OR cr.requestNumber = :requestNumber) " +
            "AND (:typeCode IS NULL OR cr.certificateType.code = :typeCode) " +
            "AND cr.certificateStatus.code = :statusCode " +
            "ORDER BY cr.id DESC "
    )
    List<CertificateRequestVerificationDto> checkCertificateRequest(@Param("applicationNumber") String applicationNumber,
                                                              @Param("requestNumber") String requestNumber,
                                                              @Param("typeCode") String typeCode,
                                                              @Param("statusCode") String statusCode,
                                                              @Param("applicationId") Long applicationId);

    
    @Query("SELECT new gov.saip.applicationservice.common.dto.ApplicationInfoResultDto(" +
            "ai.id, ai.category.saipCode, ai.applicationNumber, cr.requestNumber) " +
            "FROM CertificateRequest cr " +
            "JOIN ApplicationInfo ai ON ai.id = cr.applicationInfo.id " +
            "WHERE cr.id = :id "
    )
    ApplicationInfoResultDto getApplicationInfoByCertificateRequestId(@Param("id") Long id);
    
    CertificateRequest findByApplicationInfoAndCertificateTypeAndCertificateStatus
            (ApplicationInfo applicationInfo, LkCertificateType type, LkCertificateStatus status);


    @Query(value = "select cr.applicationInfo.id from CertificateRequest cr where cr.id = :certificateId")
    Long findApplicationInfoIdById(@Param("certificateId") Long certificateId);



    @Query("""
           select cr.requestNumber from CertificateRequest cr join cr.certificateType ct
           where cr.id = (select max(cr2.id) from CertificateRequest cr2
           join cr2.certificateType ct2 where
           cr2.applicationInfo.id= :appId and ct2.code= :certCode
           and cr2.certificateStatus.code = :statusCode)
           """)
    String findGrantCertificateNumberByAppId(@Param(value = "appId") Long appId,
                                             @Param("certCode")String certCode,
                                             @Param("statusCode") String statusCode);

    @Query("select distinct c from CertificateRequest c " +
            "where c.isDeleted = 0 and c.applicationInfo.id = :applicationId and c.certificateType.code = :type " +
            "and (:status is null or c.certificateStatus.code = :status)")
    CertificateRequest findByApplicationInfoAndCertificateTypeAndStatus(
            @Param("applicationId") Long applicationId,
            @Param("type") String type,
            @Param("status") String status
    );

    @Query("""
            SELECT cr.id as certificateRequestId, art as applicationRelevantType, ai.id AS applicationId, cr.createdDate AS depositDate,
            ai.applicationNumber As applicationNumber,
            ai.titleAr As applicationTitleAr, ai.titleEn As applicationTitleEn,
            cr.certificateType AS certificateType, cr.certificateStatus AS certificateStatus, art.customerCode as customerCode , cr.document as document
            FROM CertificateRequest cr
            JOIN ApplicationInfo ai ON ai.id = cr.applicationInfo.id
            JOIN ApplicationRelevantType art on ai.id = art.applicationInfo.id
            LEFT JOIN cr.document documents
            WHERE
            ai.id = :appId
            """
    )
    List<CertificateRequestProjection> listCertificateByApplicationId(@Param("appId") Long appId);

    @Query("""
        select c
        from CertificateRequest c
        where c.id = (
        select max(cr.id)
        from CertificateRequest cr
        join cr.applicationInfo ai
        join cr.certificateType ct
        join cr.certificateStatus cs
        where ai.id = :applicationId and ct.code = :certificateType and cs.code = 'COMPLETED'
        )
    """)
    Optional<CertificateRequest> getLastCertificate(@Param("applicationId") Long applicationId, @Param("certificateType") String certificateType);

    @Query("""
           select doc.id 
           from CertificateRequest cr 
           join cr.document doc
           where cr.requestNumber = :requestNumber
           """)
    Long findGrantCertificateDocumentIdByRequestNumber(@Param(value = "requestNumber") String requestNumber);
}
