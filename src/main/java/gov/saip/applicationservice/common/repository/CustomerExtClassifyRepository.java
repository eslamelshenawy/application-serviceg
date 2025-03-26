package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.enums.CustomerExtClassifyEnum;
import org.springframework.data.repository.query.Param;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.CustomerExtClassify;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerExtClassifyRepository extends BaseRepository<CustomerExtClassify, Long> {
    @Query("SELECT cec FROM CustomerExtClassify cec WHERE cec.applicationInfo.id = :applicationId and cec.customerExtClassifyType = :type")
    List<CustomerExtClassify> findByApplicationId(@Param("applicationId") Long applicationId, @Param("type") CustomerExtClassifyEnum type);
}
