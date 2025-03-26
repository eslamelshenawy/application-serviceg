package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.model.ApplicationWord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationOtherDocumentRepository extends BaseRepository<ApplicationOtherDocument, Long> {

    List<ApplicationOtherDocument> findByApplicationInfoId(Long appId);
}
