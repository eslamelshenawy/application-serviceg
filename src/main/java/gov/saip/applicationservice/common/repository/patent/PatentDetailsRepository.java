package gov.saip.applicationservice.common.repository.patent;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDataDto;
import gov.saip.applicationservice.common.model.patent.PatentDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatentDetailsRepository extends BaseRepository<PatentDetails, Long> {
    Optional<PatentDetails> findByApplicationId(Long id);

    @Query("SELECT new gov.saip.applicationservice.common.dto.patent.PatentApplicationInfoXmlDataDto(ai.id, ai.email, " +
            "          pd.ipdSummaryAr , pd.ipdSummaryEn) " +
            "FROM ApplicationInfo ai " +
            "JOIN PatentDetails pd ON ai.id = pd.applicationId " +
            "WHERE ai.id = :applicationId")
    Optional<PatentApplicationInfoXmlDataDto> getApplicationInfoXmlDataDto(@Param("applicationId") Long applicationId);

    @Modifying
    @Query("update PatentDetails  pd set pd.patentOpposition = false where pd.applicationId = :applicationId")
    void setPatentOppositionFlag(@Param("applicationId") Long applicationId);
    @Modifying
    @Query("update PatentDetails  pd set pd.patentOpposition = true where pd.applicationId = :applicationId")
    void setPatentOppositionFlagWithTrue(@Param("applicationId") Long applicationId);
    @Query("select pd.patentOpposition from PatentDetails pd where pd.applicationId = :applicationId")
    Boolean getPatentOpposition(@Param("applicationId") Long applicationId);

}
