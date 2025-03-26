package gov.saip.applicationservice.common.repository;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.Suspcion.SubClassificationDetailsProjection;
import gov.saip.applicationservice.common.model.ApplicationSubClassification;
import gov.saip.applicationservice.common.model.SubClassification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubClassificationRepository extends BaseRepository<SubClassification, Long> {
    @Query(value = "SELECT subClass " +
            "FROM SubClassification subClass " +
            "JOIN subClass.classification classification " +
            "WHERE subClass.enabled = true " +
            "  AND subClass.isVisible = true " +
            "  AND classification.id = :classId " +
            "  AND subClass.isShortcut = :isShortcut " +
            "  AND (:query IS NULL " +
            "       OR UPPER(subClass.nameEn) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.nameAr) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.code) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.descriptionEn) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.descriptionAr) LIKE UPPER(CONCAT('%', :query, '%'))) " +
            "  AND (:basicNumber IS NULL OR subClass.basicNumber = :basicNumber)")
    Page<SubClassification> findBySearch(@Param("query") String query, @Param("isShortcut") Boolean isShortcut
                                 , @Param("classId") long classId, @Param("basicNumber") Long basicNumber, Pageable pageable);


    @Query(value = "SELECT subClass " +
            "FROM SubClassification subClass " +
            "WHERE" +
            " classification.id = :classId " +
            "  AND (:query IS NULL " +
            "       OR UPPER(subClass.nameEn) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.nameAr) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.code) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.descriptionEn) LIKE UPPER(CONCAT('%', :query, '%')) " +
//            "       OR subClass.basicNumber IS NULL " +
            "       OR UPPER(subClass.descriptionAr) LIKE UPPER(CONCAT('%', :query, '%'))) " )
    Page<SubClassification> findByClassificationId(@Param("query") String query, @Param("classId") long classId, Pageable pageable);



    @Query(value = """
            SELECT 
            sc.id as id,
            sc.code as code,
            ai.titleAr as trademarkNameAr,
            ai.titleEn as trademarkNameEn,
            ai.applicationNumber as trademarkNumber,
             sc.serialNumberAr as serialNumberAr,
              sc.serialNumberEn as serialNumberEn,
               sc.basicNumber as basicNumber, 
               sc.nameAr as nameAr, 
               sc.nameEn as nameEn, 
               c.nameAr as classificationNameAr, 
               c.nameEn as classificationNameEn
            FROM  ApplicationSubClassification ascc
            JOIN ascc.applicationInfo ai
            JOIN ascc.subClassification sc
            JOIN ai.niceClassifications anc
            JOIN anc.classification c
            WHERE ai.id = :trademarkId
             AND (:subClassificationId is null or sc.id = :subClassificationId)  
             AND (:code is null or sc.code = :code)
""")
    Page<SubClassificationDetailsProjection> findSubClassificationByTrademarkId(@Param("trademarkId") Long trademarkId,@Param("subClassificationId") Long subClassificationId,@Param("code") String code, Pageable pageable);


    @Query("select count(prod)>0 from ApplicationSubClassification prod" +
            " join prod.applicationInfo det" +
            " join prod.subClassification subClass " +
            " where subClass.id = :subClassId" +
            " and det.id = :applicationId")
    boolean checkSubClassSelected(@Param("subClassId")long subClassId, @Param("applicationId") long applicationId);
    
    @Query("select prod from ApplicationSubClassification prod" +
            " join prod.applicationInfo det" +
            " join prod.subClassification subClass " +
            " where subClass.id in (:subClassIds)" +
            " and det.id = :applicationId")
    List<ApplicationSubClassification> getAppSubClassSelected(@Param("subClassIds") List<Long> subClassIds, @Param("applicationId") long applicationId);

    List<SubClassification> findByIdIn(List<Long> ids);

    List<SubClassification> findSubClassificationByIsShortcutAndClassificationId(boolean isShortcut, Long classId);


    @Query(value = "SELECT subClass " +
            "FROM ApplicationSubClassification appSubClass" +
            " JOIN appSubClass.subClassification subClass " +
            " JOIN subClass.classification classification " +
            " WHERE  " +
            "   appSubClass.applicationInfo.id = :applicationId" +
            "   AND (:classificationId IS NULL OR classification.id = :classificationId) " +
            "   AND (" +
            "       :query IS NULL" +
            "       OR UPPER(subClass.nameEn) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.nameAr) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.descriptionEn) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "       OR UPPER(subClass.descriptionAr) LIKE UPPER(CONCAT('%', :query, '%'))" +
            "       OR UPPER(CAST(subClass.id AS string)) LIKE UPPER (CONCAT('%', :query, '%'))" +
            "   ) " +
            "   AND (:basicNumber IS NULL OR subClass.basicNumber = :basicNumber)")
    Page<SubClassification> getSubClassificationByApplicationId(
            @Param("classificationId") Long classificationId,
            @Param("applicationId") Long applicationId,
            @Param("query") String query,
            @Param("basicNumber") Long basicNumber,
            Pageable pageable
    );



}
