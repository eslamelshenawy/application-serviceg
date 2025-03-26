package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationDatabase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationDatabaseRepository extends BaseRepository<ApplicationDatabase, Long> {
    List<ApplicationDatabase> findByApplicationInfoId(Long appId);

}
