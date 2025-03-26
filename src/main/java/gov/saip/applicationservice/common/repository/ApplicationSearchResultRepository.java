package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationOtherDocument;
import gov.saip.applicationservice.common.model.ApplicationSearchResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationSearchResultRepository extends BaseRepository<ApplicationSearchResult, Long> {

    List<ApplicationSearchResult> findByApplicationInfoId(Long appId);
}
