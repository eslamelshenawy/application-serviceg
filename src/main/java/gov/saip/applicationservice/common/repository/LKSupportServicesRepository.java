package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.SupportServiceType;
import gov.saip.applicationservice.common.model.LKSupportServices;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LKSupportServicesRepository extends BaseRepository<LKSupportServices, Long> {

    List<LKSupportServices> findByCode(SupportServiceType code);
    @Query("""
            select supp from LKSupportServices supp
            join supp.applicationCategories categ
            where
            supp.code = :code and
            (:saipCode is null or categ.saipCode = :saipCode)
            """)
    List<LKSupportServices> findByCodeAndCategory(@Param("code") SupportServiceType code, @Param("saipCode") String saipCode);
    @Query(value = "SELECT DISTINCT s FROM LKSupportServices s " +
            "JOIN s.applicationCategories c " +
            "WHERE (:categoryId IS NULL OR c.id = :categoryId) " +
            "AND s.code != 'OPPOSITION_REVOKE_LICENCE_REQUEST' " +
            "AND s.code != 'AGENT_SUBSTITUTION' " +
            "AND (:categoryId != 1 OR (s.code IS NOT NULL)) " +
            "AND s.isDeleted = 0")
    List<LKSupportServices> findServicesByCategoryId(@Param("categoryId") Long categoryId);
    @Query("""
                select s from LKSupportServices s
                where s.id in (:ids)
            """)
    List<LKSupportServices> getServicesByIds(List<Long> ids);
    
    
    @EntityGraph(attributePaths = {"supportServiceRequestStatuses"})
    @Query("SELECT ss FROM LKSupportServices ss WHERE ss.id = :id")
    LKSupportServices findSupportServiceTypeStatusesByRequestType(Long id);
}
