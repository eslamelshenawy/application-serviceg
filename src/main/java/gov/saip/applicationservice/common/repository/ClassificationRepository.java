package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ClassificationProjection;
import gov.saip.applicationservice.common.model.Classification;
import gov.saip.applicationservice.common.model.LkClassificationVersion;
import gov.saip.applicationservice.common.model.SubClassification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassificationRepository extends BaseRepository<Classification, Long> {
    @Query(value = "select classification from Classification classification" +
            " join classification.version version" +
            " join classification.category category" +

            " where " +
            " classification.enabled = true" +
            " AND (:categoryId is null or category.id = :categoryId)" +

            " AND (:versionId is null or version.id = :versionId)" +
            " AND (:saipCode is null or category.saipCode = :saipCode)" +
            " AND (:search is null or UPPER(classification.nameEn) LIKE UPPER(CAST(CONCAT('%', :search, '%') AS string))" +
            " OR UPPER(classification.nameAr) LIKE UPPER(CAST(CONCAT('%', :search, '%') AS string))" +
            " OR UPPER(classification.descriptionEn) LIKE UPPER(CAST(CONCAT('%', :search, '%') AS string))" +
            " OR UPPER(classification.descriptionAr) LIKE UPPER(CAST(CONCAT('%', :search, '%') AS string)))" +
            " order by classification.nameEn")
    Page<Classification> findBySearchAndCategory(@Param("search") String search, @Param("versionId") Integer versionId,
                                      @Param("saipCode") String saipCode, @Param("categoryId") Long categoryId, Pageable pageable);
    @Query("SELECT c FROM Classification c " +
            "JOIN c.version v " +
            "JOIN c.category cat " +
            "JOIN c.unit u " +
            "WHERE c.enabled = true " +
            "AND (:categorySaipCode IS NULL OR cat.saipCode = :categorySaipCode) " +
            "AND (:unitId IS NULL OR u.id = :unitId) " +
            "AND (:versionId IS NULL OR v.id = :versionId)"
    )
    List<Classification> findByUnitIdAndCategory(@Param("versionId") Integer versionId,
                                                 @Param("categorySaipCode") String categorySaipCode,
                                                 @Param("unitId") Long unitId);



    @Query(value = "select c from Classification c where c.category.id = :categoryId and c.version.id = :versionId order by c.id ASC")
    List<Classification> findByCategoryId(@Param("categoryId") Long categoryId, @Param("versionId") Integer versionId);

    @Query(value = "select c from Classification c where c.category.saipCode = :saipCode and c.version.id = :versionId order by c.id ASC")
    List<Classification> findByCategorySaipCode(@Param("saipCode") String saipCode, @Param("versionId") Integer versionId);

    @Query(value = """
    select
        c
    from
        Classification c
    where
        c.unit.id in ?1
        and c.version.id in (
        select
            distinct(lcv.id)
        from
            LkClassificationUnit lcu
        left join LkClassificationVersion lcv on
            lcu.category.id = lcv.category.id
        where
            lcu.id in ?1)
    """)
    List<Classification> findByUnitIdIn(List<Long> unitIds);
    List<Classification> findByIdIn(List<Long> inventoryIdList);

    @Query("""
            select c from Classification c
            left join c.category category
            left join c.unit unit
            where
            c.enabled = true and c.isDeleted = 0 and (c.unit.id IS NULL OR (c.unit.id IS NOT NULL AND unit.isDeleted=0))
            and category.saipCode = :saipCode
            and (:versionId IS NULL OR c.version.id = :versionId)
            order by c.id
            """)
    public List<Classification> getAllClassifications(@Param("saipCode") String saipCode, @Param("versionId") Integer versionId);

    @Query("""
        select prod.subClassification
        from ApplicationSubClassification prod
        join prod.subClassification sub
        where prod.applicationInfo.id = :id
    """)
    List<SubClassification> getSubClassificationList(@Param("id") Long id);

    @Query("""
        select prod.subClassification
        from ApplicationSubClassification prod
        join prod.subClassification sub
        where prod.applicationInfo.id = :id
        and sub.classification.id = :classificationId
    """)
    List<SubClassification> getSubClassificationList(@Param("id") Long id, @Param("classificationId") Long classificationId);

    @Query(value = """
        select
            clas.id as id,
        	clas.code as code,
            clas.name_Ar as nameAr,
            clas.name_En as nameEn,
            clas.version_id as versionId,
            v.code AS versionCode,
            v.name_ar AS versionNameAr,
            v.name_en AS versionNameEn,
            v.category_id AS versionCategoryId
       from application.Application_Nice_Classifications nice
       join application.classifications clas ON clas.id = nice.classification_id\s
       JOIN application.applications_info ai ON ai.id = nice.application_id
       JOIN application.lk_classification_versions v ON v.id = clas.version_id
       where ai.id = :appId
    """, nativeQuery = true)
    List<ClassificationProjection> getClassificationList(@Param("appId") Long appId);

    @Query("""
    select c.version
    from Classification c
    where c.id = :id
""")
    LkClassificationVersion getClassificationVersion(@Param("id") Long id);

    @Query("""
            select count(c) > 0 from Classification c
            join c.unit unit
            where unit.id = :unitId
            """)
    public boolean hasClassificationsByVersionId(@Param("unitId") Long unitId);

    @Query(value = "select classification from Classification classification" +
            " where " +
            " classification.enabled = true" +
            " AND (:unitId is null or classification.unit.id = :unitId)" +
            " order by classification.id")
    Page<Classification> findByUnitId(@Param("unitId") Long unitId, Pageable pageable);

    @Query("""
            select c from Classification c
            where
            c.unit.id = :unitId
            order by c.id
            """)
    List<Classification> getClassificationsByUnit(@Param("unitId") Long unitId);

}
