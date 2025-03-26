package gov.saip.applicationservice.common.repository.veena;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.veena.ApplicationVeenaClassification;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ApplicationVeenaClassificationRepository extends BaseRepository<ApplicationVeenaClassification, Long> {

    List<ApplicationVeenaClassification> findByApplicationId(Long applicationId);
    @Query("""
        select veena.veenaClassification from ApplicationVeenaClassification veena 
        where veena.application.id = :appId
    """)
    List<LKVeenaClassification> getVeenaClassificationsByAppId(@Param("appId") Long id);


    void deleteByApplication_Id(Long id);

    @Transactional
    @Modifying
    @Query("update ApplicationVeenaClassification a set a.isDeleted = ?1 where a.application.id = ?2")
    int updateIsDeletedByApplication_id(int isDeleted, Long id);

}
