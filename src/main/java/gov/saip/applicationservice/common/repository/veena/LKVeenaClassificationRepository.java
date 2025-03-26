package gov.saip.applicationservice.common.repository.veena;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LKVeenaClassificationRepository extends BaseLkRepository<LKVeenaClassification, Long> {


    @Query("Select veena.veenaClassification from ApplicationVeenaClassification veena where veena.application.id= :appId")
    List<LKVeenaClassification> getVeenaClassificationsByApplicationId(@Param("appId") Long appId);

    @Query("SELECT DISTINCT venna FROM LKVeenaClassification venna " +
            "WHERE UPPER(venna.nameAr) LIKE CONCAT('%', UPPER(:search), '%') " +
            "OR UPPER(venna.nameEn) LIKE CONCAT('%', UPPER(:search), '%')")
    Page<LKVeenaClassification> getAllVeenaClassifications(@Param("search") String search, Pageable pageable);

}
