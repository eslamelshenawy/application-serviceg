package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationWord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationWordRepository extends BaseRepository<ApplicationWord, Long> {

    List<ApplicationWord> findByApplicationInfoId(Long appId);
}
