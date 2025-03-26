package gov.saip.applicationservice.common.repository;

import feign.Param;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.SimilarTrademark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimilarTrademarkRepository extends BaseRepository<SimilarTrademark, Long> {
    List<SimilarTrademark> findByTaskInstanceId(String taskInstanceId);
    @Query("select i FROM SimilarTrademark i WHERE i.applicationInfo.id = :applicationInfo ")
    List<SimilarTrademark> findByApplicationInfoId(@Param("applicationId") Long applicationInfo);

}
