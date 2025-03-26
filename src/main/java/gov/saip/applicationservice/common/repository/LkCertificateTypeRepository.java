package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkCertificateType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LkCertificateTypeRepository extends BaseLkRepository<LkCertificateType, Integer> {

    @Query("SELECT lct " +
            "FROM LkCertificateType lct " +
            "JOIN lct.applicationCategories ac " +
            "WHERE (:categoryId IS NULL OR ac.id = :categoryId)" +
            "AND lct.enabled = TRUE")
    List<LkCertificateType> findCertificateTypesByCategoryId(@Param("categoryId") Long categoryId);
    
    
    @Query("SELECT lct " +
            "FROM LkCertificateType lct " +
            "WHERE lct.code = :code")
    List<LkCertificateType> findByCertificateCode(@Param("code") String code);
}
