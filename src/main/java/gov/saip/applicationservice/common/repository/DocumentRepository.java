package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.Document;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends BaseRepository<Document, Long> {
    List<Document> findByIdIn(List<Long> ids);
    @Query("SELECT SUM(d.noOfDocumentPages) FROM Document d WHERE d.applicationInfo.id= :applicationInfoId AND lower(d.documentType.name)  LIKE %:documentTypeName%")
    Integer calculateSumOfDocumentPages(@Param("applicationInfoId") Long applicationInfoId, @Param("documentTypeName") String documentTypeName);
    @Query("SELECT SUM(d.noOfDocumentPages) FROM Document d WHERE d.applicationInfo.id= :applicationInfoId AND lower(d.documentType.name)  NOT LIKE '%claims%' AND lower(d.documentType.name) NOT LIKE '%shape%'")
    Integer calculateSumOfNormalDocumentPages(@Param("applicationInfoId") Long applicationInfoId);

    @Query("SELECT d FROM Document d" +
            " join d.documentType type" +
            " join d.applicationInfo appInfo" +
            " where" +
            " type.name like :typeName" +
            " and appInfo.id = :applicationId" +
            " order by type.docOrder")
    List<Document> findDocumentByApplicationId(@Param("applicationId") Long applicationId,
                                               @Param("typeName") String typeName);
    
    @Query("SELECT d FROM Document d" +
            " join d.documentType type" +
            " join d.applicationInfo appInfo" +
            " where" +
            " type.name like :typeName" +
            " and appInfo.id = :applicationId" +
            " order by d.id DESC")
    List<Document> findLatestDocumentByApplicationId(@Param("applicationId") Long applicationId,
                                               @Param("typeName") String typeName);



    @Query(
            """
    SELECT distinct d FROM Document d 
    left join fetch d.documentComments 
    join fetch d.applicationInfo ai 
    join fetch d.documentType dt 
    WHERE ai.id = :appId AND dt.category = :category AND ( COALESCE (:types) IS null OR
    dt.name IN (:types)) AND (COALESCE(:extypes) IS null OR dt.name NOT IN (:extypes)) and 
    d.isDeleted = 0
     """
    )

    List<Document> getApplictionDocumentsTypes(@Param("appId") Long appId, @Param("category") String category, @Param("types") List<String> types, @Param("extypes") List<String> excludeTypes);

    @Query("SELECT d FROM Document d" +
            " join d.documentType type" +
            " join d.applicationInfo appInfo" +
            " where" +
            " type.name like :typeName" +
            " and appInfo.id in (:applicationIds) " +
            " order by type.docOrder")
    List<Document> findDocumentsByApplicationIds(@Param("applicationIds") List<Long> applicationIds,
                                                 @Param("typeName") String typeName);
    
    @Query("SELECT d FROM Document d" +
            " join d.documentType type" +
            " join d.applicationInfo appInfo" +
            " where" +
            " type.name like :typeName" +
            " and appInfo.id in (:applicationIds) " +
            " order by d.id desc")
    List<Document> findLatestDocumentsByApplicationIds(@Param("applicationIds") List<Long> applicationIds,
                                                 @Param("typeName") String typeName);

    @Modifying
    @Transactional
    @Query("update Document d set d.applicationInfo.id = :appId where d.id = :id")
    void linkApplicationToDocument(@Param("id") Long id, @Param("appId") Long appId);


    @Query("""
            SELECT d FROM Document d
            join d.documentType type
            join d.applicationInfo appInfo
            where
            appInfo.id = :applicationId and 
            (COALESCE(:typeCodes) is null or type.code in (:typeCodes))
            """)
    List<Document> getApplicationDocsByApplicationIdAndTypes(@Param("applicationId") Long applicationId, @Param("typeCodes") List<String> typeCode);


    Optional<Document> getDocumentByNexuoId(String nexuoId);

    @Query("SELECT distinct d FROM Document d left join fetch d.documentComments join fetch d.applicationInfo ai join fetch d.documentType dt WHERE ai.id = :appId AND dt.category = :category ")

    List<Document> getApplicationDocumentsWithComments(@Param("appId") Long appId, @Param("category") String category);


    @Query("SELECT d FROM Document d WHERE d.applicationInfo.id = :applicationId")
    @EntityGraph(attributePaths = "applicationInfo")
    List<Document> findNexuoIdByApplicationId(@Param("applicationId") Long applicationId);


    @Query("SELECT d.nexuoId " +
            "FROM LkTagTypeDesc tag " +
            "JOIN TrademarkDetail td ON td.tagTypeDesc.id = tag.id " +
            "JOIN Document d ON td.applicationId = d.applicationInfo.id " +
            "JOIN LkDocumentType ldt ON d.documentType.id = ldt.id " +
            "WHERE tag.id = :tagId " +
            "AND d.applicationInfo.id = :applicationId " +
            "AND ldt.code = :documentTypeCode")
    String getNexuoIdsByApplicationIdAndTagIdAndDocumentTypeCode(Integer tagId, Long applicationId, String documentTypeCode);


    List<Document> getByApplicationInfoId(Long id);

    @Query(value = " SELECT is_deleted FROM application.documents d WHERE id = :id ", nativeQuery = true)
    Long getIsDeletedByDocumentId(@Param("id") Long id);

    @Query("""
            SELECT d.nexuoId FROM Document d
            join d.documentType type
            join d.applicationInfo appInfo
            where
            appInfo.id = :applicationId and 
            (COALESCE(:typeCodes) is null or type.code in (:typeCodes))
            """)
    List<String> getNexuoIdsByApplicationIdAndTypes(@Param("applicationId") Long applicationId, @Param("typeCodes") List<String> typeCode);

    @Modifying
    @Query(""" 
            update Document doc 
            set doc.isDeleted = 1 where doc.id = (select d.id from Document d where d.applicationInfo.id = :appId and d.documentType.code = :typeCode)
            """)
    void deleteDocumentByApplicationIdAndDocType(@Param("appId") Long appId, @Param("typeCode") String typeCode);

    @Query(value = " SELECT * FROM application.documents d WHERE d.is_deleted = 1 and d.id in :ids ", nativeQuery = true)
    List<Document> findDeletedDocuments(List<Long> ids);

    @Query("SELECT d FROM Document d WHERE d.applicationInfo.id = :applicationId AND d.documentType.name = :documentTypeName AND d.isDeleted = 0 ORDER BY d.createdDate DESC")
    List<Document> findLastDocumentByApplicationIdAndType(@Param("applicationId") Long applicationId, @Param("documentTypeName") String documentTypeName);


}
