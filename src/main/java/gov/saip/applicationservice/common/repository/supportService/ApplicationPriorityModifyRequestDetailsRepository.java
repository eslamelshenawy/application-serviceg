package gov.saip.applicationservice.common.repository.supportService;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationPriority;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequestDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationPriorityModifyRequestDetailsRepository extends BaseRepository<ApplicationPriorityModifyRequestDetails, Long> {

    @Modifying
    @Query("DELETE FROM ApplicationPriorityModifyRequestDetails apmrd WHERE apmrd.applicationPriorityModifyRequest.id = :applicationPriorityModifyRequestId")
    int hardDeleteByApplicationPriorityModifyRequestId(@Param("applicationPriorityModifyRequestId")Long applicationPriorityModifyRequestId);
}
