package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.PublicationSchedulingConfig;
import gov.saip.applicationservice.common.model.PublicationTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicationSchedulingConfigRepository extends BaseRepository<PublicationSchedulingConfig, Long> {
    @Query("""
            SELECT p
            FROM PublicationSchedulingConfig p
            JOIN FETCH p.publicationTimes
            JOIN FETCH p.applicationCategory
            WHERE p.applicationCategory.saipCode = :applicationCategorySaipCode
            """)
    Optional<PublicationSchedulingConfig> findByApplicationCategorySaipCode(@Param("applicationCategorySaipCode") String applicationCategorySaipCode);
}
