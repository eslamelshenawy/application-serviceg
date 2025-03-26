package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.PublicationIssueApplicationPublication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationIssueApplicationRepository extends BaseRepository<PublicationIssueApplicationPublication, Long> {
    
    @Query("select piap from PublicationIssueApplicationPublication piap " +
            "join piap.applicationPublication ap " +
            "join piap.publicationIssue pi " +
            "join ap.publicationType " +
            "where ap.applicationInfo.id=:appId and " +
            "ap.publicationType.code=:pubType")
    PublicationIssueApplicationPublication findByAppIdAndType(@Param("appId") Long appId, @Param("pubType") String pubType);
}
