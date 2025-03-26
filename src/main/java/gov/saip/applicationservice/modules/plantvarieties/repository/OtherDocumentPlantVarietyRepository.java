package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.model.OtherPlantVarietyDocuments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherDocumentPlantVarietyRepository extends BaseRepository<OtherPlantVarietyDocuments, Long> {


    @Query("select doc from OtherPlantVarietyDocuments doc where doc.application.id = :applicationId and doc.document.documentType.code='OTHER_DOCUMENTS_PLANT_VARIETIES'")
    List<OtherPlantVarietyDocuments> findAllByApplicationId(@Param("applicationId") Long applicationId);

}