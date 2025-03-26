package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LkClassificationVersionRepository extends BaseLkRepository<LkClassificationVersion, Integer> {
    @Query(value = """
            select MAX(lcv.id) from LkClassificationVersion lcv join Classification c on lcv.id = c.version.id where lcv.category.saipCode = :saipCode
            """)
    public Integer getLatestVersionIdBySaipCode(@Param("saipCode") String saipCode);

    @Query(value = """
            select MAX(lcv.id) from LkClassificationVersion lcv join Classification c on lcv.id = c.version.id where c.category.id = :categoryId
            """)
    public Integer getLatestVersionIdByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT DISTINCT lcv from LkClassificationVersion lcv " +
            "WHERE (:categoryId IS NULL OR lcv.category.id = :categoryId ) AND  (:search IS NULL OR (lower(lcv.nameAr) like CONCAT('%',:search,'%')) OR (lower(lcv.code) like CONCAT('%',:search,'%')))")
    Page<LkClassificationVersion> getClassificationVersionsBySearch(@Param("search") String search,@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT lcv FROM LkClassificationVersion lcv " +
            "WHERE lcv.category.id = :categoryId " +
            "AND lcv.id = (SELECT MAX(lcv2.id) FROM LkClassificationVersion lcv2 WHERE lcv2.category.id = :categoryId)")
    LkClassificationVersion getLatestVersionByCategoryId(@Param("categoryId") Long categoryId);

    List<LkClassificationVersion> findAllByCategoryId(Long categoryId);

    @Query(value = """
            select lkcv FROM LkClassificationVersion lkcv 
            where lkcv.id = (select MAX(lcv.id) FROM LkClassificationVersion lcv 
                            join Classification c 
                            on lcv.id = c.version.id
                            where c.category.id = :categoryId)
            """)
    LkClassificationVersion findLatestClassificationVersionsWithClassificationByCategory(Long categoryId);

    @Modifying
    @Transactional
    @Query("update LkClassificationVersion t SET t.isDeleted = 1 WHERE t.id = :id")
    int updateIsDeleted(@Param("id") Integer id);


}
