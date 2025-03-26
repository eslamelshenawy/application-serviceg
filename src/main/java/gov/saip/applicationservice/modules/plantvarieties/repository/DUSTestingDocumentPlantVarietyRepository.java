package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DUSTestingDocumentPlantVarietyRepository extends BaseRepository<DUSTestingDocument, Long> {


    @Query("select doc from DUSTestingDocument doc where doc.application.id = :applicationId")
    List<DUSTestingDocument> findAllByApplicationId(@Param("applicationId") Long applicationId);

    Optional<DUSTestingDocument> findByApplicationId(Long applicationId);

}