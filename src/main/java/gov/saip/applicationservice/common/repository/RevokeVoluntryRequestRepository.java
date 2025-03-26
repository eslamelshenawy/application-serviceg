package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.model.RevokeVoluntaryRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RevokeVoluntryRequestRepository extends SupportServiceRequestRepository<RevokeVoluntaryRequest> {



    @Query("""
            SELECT distinct rvr FROM RevokeVoluntaryRequest rvr  where rvr.applicationInfo.id = :appId and rvr.requestStatus.code = 'TRADMARK_REVOKED'
            """)
    RevokeVoluntaryRequest findByAppId(Long appId);

}
