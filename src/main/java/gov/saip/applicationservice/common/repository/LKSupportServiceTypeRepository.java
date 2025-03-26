package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.enums.SupportedServiceCode;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LKSupportServiceTypeRepository extends BaseRepository<LKSupportServiceType, Long> {

    @Query("SELECT s FROM LKSupportServiceType s WHERE s.type = :requestType AND s.isDeleted = 0")
    List<LKSupportServiceType> findByType(SupportServiceType requestType);

    LKSupportServiceType findByCode(SupportedServiceCode code);
}
