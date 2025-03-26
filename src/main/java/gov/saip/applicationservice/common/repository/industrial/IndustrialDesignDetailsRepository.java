package gov.saip.applicationservice.common.repository.industrial;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.model.industrial.IndustrialDesignDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndustrialDesignDetailsRepository extends BaseRepository<IndustrialDesignDetail, Long> {

    Optional<IndustrialDesignDetail> findByApplicationId(Long applicationId);

    Optional<IndustrialDesignDetail> findById(Long id);

    @Query("SELECT new gov.saip.applicationservice.common.dto.industrial.IndustrialDesignApplicationInfoXmlDataDto" +
            "    (ai.id, ai.email, idd.explanationAr , idd.explanationEn, idd.requestType) " +
            "FROM ApplicationInfo ai " +
            "JOIN IndustrialDesignDetail idd ON ai.id = idd.applicationId " +
            "WHERE ai.id = :applicationId")
    Optional<IndustrialDesignApplicationInfoXmlDataDto> getApplicationInfoXmlDataDto(@Param("applicationId") Long applicationId);

}
