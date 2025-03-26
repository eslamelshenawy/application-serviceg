package gov.saip.applicationservice.common.repository.patent;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ApplicationCertificateDocumentDto;
import gov.saip.applicationservice.common.model.patent.ApplicationCertificateDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationCertificateDocumentRepository extends BaseRepository<ApplicationCertificateDocument, Long> {

       @Query("""
        select cert from ApplicationCertificateDocument cert
        where
        cert.application.id = :applicationId
        and cert.id = (
        select max(cert2.id) from ApplicationCertificateDocument
          cert2 where cert2.application.id = :applicationId 
        )
    """)
       Optional<ApplicationCertificateDocument> getMostRecentCertificateDocumentByApplicationId(@Param("applicationId") Long applicationId);

       @Query("""
        select max(cert.version) from ApplicationCertificateDocument cert
        where
        cert.application.id = :applicationId
        """)
       Integer getMaxVersionByApplicationId(@Param("applicationId") Long applicationId);

       @Query("""
        select min(cert.version) from ApplicationCertificateDocument cert
        where
        cert.application.id = :applicationId
        """)
       Integer getMinVersionByApplicationId(@Param("applicationId") Long applicationId);

       @Query("""
        select cert from ApplicationCertificateDocument cert
        where
        cert.application.id = :applicationId AND (cert.generationStatus is null or cert.generationStatus != 'FAILED')
        order by cert.version desc
        """)
       List<ApplicationCertificateDocument> findByApplicationIdOrderByVersionDesc(@Param("applicationId") Long applicationId);

       @Query("""
        select cert from ApplicationCertificateDocument cert
        where
        cert.generationStatus = 'FAILED'
    """)
       List<ApplicationCertificateDocument> findAllFailed();

}

