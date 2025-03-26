package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationSimilarDocument;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationSimilarDocumentRepository extends BaseRepository<ApplicationSimilarDocument, Long> {
    List<ApplicationSimilarDocument> findByApplicationInfoId(Long appId);

}
