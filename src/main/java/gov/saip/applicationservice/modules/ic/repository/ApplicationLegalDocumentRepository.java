package gov.saip.applicationservice.modules.ic.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.ic.model.ApplicationLegalDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationLegalDocumentRepository extends BaseRepository<ApplicationLegalDocument, Long> {

    @Query("select doc from ApplicationLegalDocument doc where doc.application.id = :applicationId")
    List<ApplicationLegalDocument> findAllByApplicationId(@Param("applicationId") Long applicationId);

    Optional<ApplicationLegalDocument> findByApplicationId(Long applicationId);
}

