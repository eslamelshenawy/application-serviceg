package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.DocumentsTemplate;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsTemplateRepository extends BaseRepository<DocumentsTemplate, Long> {
    List<DocumentsTemplate> findByCategory(LkApplicationCategory category);
}
