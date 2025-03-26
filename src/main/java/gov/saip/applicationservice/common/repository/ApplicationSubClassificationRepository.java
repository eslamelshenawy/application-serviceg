package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationSubClassification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ApplicationSubClassificationRepository extends BaseRepository<ApplicationSubClassification, Long> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from ApplicationSubClassification appClass " +
            "where appClass.id in (select appClass2.id from ApplicationSubClassification appClass2 join appClass2.applicationInfo app join appClass2.subClassification subClass join subClass.classification cat " +
            "where app.id = :applicationId and cat.id = :classificationId)")
    void deleteByApplicationIdAndClassificationId(@Param("applicationId")Long applicationId, @Param("classificationId")Long classificationId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from ApplicationSubClassification appClass " +
            "where appClass.id in (select appClass2.id from ApplicationSubClassification appClass2 join appClass2.applicationInfo app join appClass2.subClassification subClass join subClass.classification cat " +
            "where subClass.id in (:ids) and app.id = :applicationId and cat.id = :classificationId)")
    void deleteBySubClassIdInAndApplicationId(@Param("ids") List<Long> ids, @Param("applicationId") Long applicationId, @Param("classificationId") Long classificationId);

    List<ApplicationSubClassification> findByApplicationInfoId(Long applicationId);
    Page<ApplicationSubClassification> findByApplicationInfoId(Long applicationId, Pageable pageable);

    long countByApplicationInfoId(Long applicationId);

    @Query("select subClass.id from ApplicationSubClassification appClass " +
            "join appClass.applicationInfo app join appClass.subClassification subClass join subClass.classification cat " +
            "where app.id = :applicationId and cat.id = :classificationId ")
    Set<Long> getPersistedSubClassificationsByAppIdAndClassificationId(@Param("applicationId") Long applicationId, @Param("classificationId") Long classificationId);

    @Modifying
    @Query("delete from ApplicationSubClassification appClass " +
            "where appClass.subClassification.id in :subClassificationIds and appClass.applicationInfo.id = :applicationId")
    void deleteByApplicationIdAndSubClassificationIds(@Param("applicationId")Long applicationId, @Param("subClassificationIds")List<Long> subClassificationIds);

    @Modifying
    @Query(" delete from ApplicationSubClassification appClass where appClass.applicationInfo.id = :applicationId ")
    void deleteAllByApplicationId(@Param("applicationId") Long applicationId);
}
