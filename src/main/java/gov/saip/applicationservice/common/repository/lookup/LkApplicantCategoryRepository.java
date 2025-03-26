package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LkApplicantCategoryRepository extends BaseRepository<LkApplicantCategory, Long> {
}
