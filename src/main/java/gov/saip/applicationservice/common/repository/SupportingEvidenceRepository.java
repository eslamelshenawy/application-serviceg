package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.SupportingEvidence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SupportingEvidenceRepository extends BaseRepository<SupportingEvidence, Long> {

    @Query(value = """
            SELECT  s
            FROM SupportingEvidence s
            WHERE (:applicationId is null or s.applicationInfo.id = :applicationId )
        """
    )
    Page<SupportingEvidence> getSupportingEvidenceForApplicationInfo(
            @Param("applicationId") Long applicationId,
            Pageable pageable
    );
}
