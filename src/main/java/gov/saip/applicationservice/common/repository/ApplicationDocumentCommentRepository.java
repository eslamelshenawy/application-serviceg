package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationDocumentComment;
import gov.saip.applicationservice.common.model.ApplicationWord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationDocumentCommentRepository extends BaseRepository<ApplicationDocumentComment, Long> {

    List<ApplicationDocumentComment> findByDocumentId(Long documentId);
}
