package gov.saip.applicationservice.common.repository;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;
import gov.saip.applicationservice.common.model.Classification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationNiceClassificationRepository extends BaseRepository<ApplicationNiceClassification, Long> {

    @Modifying
    @Query("delete from ApplicationNiceClassification nice where nice.application.id = :applicationId and nice.classification.id = :classificationId ")
    void deleteByApplicationIdAndClassificationId(@Param("applicationId") Long applicationId, @Param("classificationId")Long classificationId);


    @Query("select appNiceClass from ApplicationNiceClassification appNiceClass join fetch appNiceClass.application app join fetch appNiceClass.classification cat " +
            "where app.id = :applicationId ")
    List<ApplicationNiceClassification> getByApplicationId(@Param("applicationId") Long applicationId);

    @Query("select appNiceClass from ApplicationNiceClassification appNiceClass join fetch appNiceClass.application app join fetch appNiceClass.classification cat " +
            "where app.id = :applicationId and cat.id = :category ")
    ApplicationNiceClassification getByApplicationIdAndCategory(@Param("applicationId") Long applicationId, @Param("category") Long category);
    @Query("select appNiceClass from ApplicationNiceClassification appNiceClass join fetch appNiceClass.application app join fetch appNiceClass.classification cat" +
            " where app.id = :appId ")
    List<ApplicationNiceClassification> getSelectedApplicationClassifications( @Param("appId") Long appId);

    @Query("""
        select nice.classification from ApplicationNiceClassification nice 
        where nice.application.id = :id
    """)
    List<Classification> getLightNiceClassificationsByAppId(@Param("id") Long id);

    @Modifying
    @Query("""
        delete from ApplicationNiceClassification nice
        where nice.application.id = :applicationId
    """)
    void deleteAllByApplicationId(@Param("applicationId") Long applicationId);
}
