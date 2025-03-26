package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.model.RevokeProducts;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevokeProductsRepository extends SupportServiceRequestRepository<RevokeProducts> {

    @Query("SELECT d.id FROM RevokeProducts r JOIN r.subClassifications d where r.applicationInfo.id =:appId")
    List<Long> getRevokedSubClassificationsIdByApplicationId(@Param("appId") Long appId);
    
    @Query("SELECT d.id FROM RevokeProducts r JOIN r.subClassifications d where r.applicationInfo.id =:appId and r.id = :supportServiceId")
    List<Long> getRevokedSubClassificationsIdByApplicationIdAndSupportServiceId(@Param("appId") Long appId, @Param("supportServiceId") Long supportServiceId);

}
