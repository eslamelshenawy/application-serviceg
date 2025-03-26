package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.TaskEqm;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskEqmRepository extends BaseRepository<TaskEqm, Long> {

    Optional<TaskEqm> findByTaskId(String taskId);
    @Query("select taskEqm from TaskEqm taskEqm" +
            " join taskEqm.applicationInfo ai" +
            " where (:applicationId is null or (ai.id = :applicationId and taskEqm.taskId is null))")
    Optional<TaskEqm> findByEqmApplicationId(Long applicationId);

    @Query("select count(taskEqm) from TaskEqm taskEqm" +
            " join taskEqm.applicationInfo ai" +
            " where (:applicationId is null or (ai.id = :applicationId and taskEqm.taskId is null))")
    Long countByEqmApplicationId(Long applicationId);

    List<TaskEqm> findAllByApplicationInfoId(Long applicationId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from TaskEqmRatingItem rate where  rate.taskEqm.id = :taskEqmId")
    int deleteByTaskEqmId (@Param("taskEqmId") Long taskEqmId);

    @Query("""
        select eqm from TaskEqm eqm
        where eqm.serviceId = :serviceId and eqm.taskEqmType.code = :typeCode
    """)
    Optional<TaskEqm> findByServiceIdAndTypeCode( @Param("serviceId") Long serviceId, @Param("typeCode") String typeCode);

    @Query("""
        select eqm from TaskEqm eqm
        where eqm.serviceId = :serviceId and eqm.taskEqmType.code = :typeCode
    """)
    List<TaskEqm> findListByServiceIdAndTypeCode( @Param("serviceId") Long serviceId, @Param("typeCode") String typeCode);


}
