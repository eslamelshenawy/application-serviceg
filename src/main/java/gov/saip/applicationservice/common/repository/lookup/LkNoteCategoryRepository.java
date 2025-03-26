package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkNoteCategory;
import gov.saip.applicationservice.common.model.LkSection;
import org.springframework.stereotype.Repository;

@Repository
public interface LkNoteCategoryRepository extends BaseLkRepository<LkNoteCategory, Integer> {

}
