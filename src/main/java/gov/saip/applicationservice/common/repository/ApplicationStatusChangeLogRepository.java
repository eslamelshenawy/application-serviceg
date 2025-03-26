package gov.saip.applicationservice.common.repository;

import feign.Param;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationStatusChangeLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplicationStatusChangeLogRepository extends BaseRepository<ApplicationStatusChangeLog, Long> {

    List<ApplicationStatusChangeLog> findByApplicationId(Long appId);

    @Query("SELECT a FROM ApplicationStatusChangeLog a WHERE a.application.id = :appId and a.newStatus.code = 'WAIVED' and a.newStatus.code != a.previousStatus.code ORDER BY a.id DESC")
    List<ApplicationStatusChangeLog> findLastLogByApplicationId(@Param("appId") Long appId, Pageable pageable);

    boolean existsByApplicationIdAndTaskDefinitionKey(Long applicationId, String taskKey);
}
