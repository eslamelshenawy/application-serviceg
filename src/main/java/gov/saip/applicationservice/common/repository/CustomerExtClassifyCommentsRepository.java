package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.CustomerExtClassifyComments;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerExtClassifyCommentsRepository  extends BaseRepository<CustomerExtClassifyComments, Long> {
}
