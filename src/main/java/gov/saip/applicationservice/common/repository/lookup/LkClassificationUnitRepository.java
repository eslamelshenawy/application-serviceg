package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkClassificationUnit;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LkClassificationUnitRepository extends BaseRepository<LkClassificationUnit, Long> {
    @Query(value = "SELECT distinct  lcu FROM LkClassificationUnit lcu " +
            "JOIN Classification clas on clas.unit = lcu" +
            " JOIN LkApplicationCategory lac ON lac =clas.category " +
            "where lac.saipCode in :code ")
    List<LkClassificationUnit> getClassificationCategoryUnits(@Param("code") List<String> code);

    List<LkClassificationUnit> findByCategoryId(Long categoryId);

    @Query("SELECT lcu FROM LkClassificationUnit lcu" +
            " where (:categoryId IS NULL OR lcu.category.id= :categoryId )" +
            " AND (:versionId IS NULL OR lcu.version.id = :versionId) " +
            " AND (:search is null or UPPER(lcu.nameEn) LIKE UPPER(CAST(CONCAT('%', :search, '%') AS string))" +
            " OR UPPER(lcu.nameAr) LIKE UPPER(CAST(CONCAT('%', :search, '%') AS string)))")
    Page<LkClassificationUnit> filter(@Param("search") String search,@Param("categoryId")Long categoryId,@Param("versionId") Integer versionId , Pageable pageable);

    @Query("select c from LkClassificationUnit c where c.isDeleted = 0")
    public List<LkClassificationUnit> getAllClassificationUnits();

    @Query("FROM LkClassificationUnit u")
    Page<LkClassificationUnit> getAllClassificationUnitsWithIds(Pageable pageable);
    @Query("select c.id from Classification c where c.unit.id = :id")
    List<Long> classificationUnitInClassification(@Param("id") Long id);

    @Query("select count(*) from ApplicationInfo ai where ai.classificationUnit.id = :id")
    Long classificationUnitInApplicationInfo(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from Classification c where c.unit.id in :id")
    void deleteClassificationByUnitId(@Param("id") List<Long> id);

    List<LkClassificationUnit> findByVersion(@Param("id") LkClassificationVersion id);

}
