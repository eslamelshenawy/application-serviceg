package gov.saip.applicationservice.common.repository.industrial;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.industrial.DesignSample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignSampleRepository extends BaseRepository<DesignSample, Long> {
    @Query("Select Count(*) from DesignSample d where d.industrialDesignId = :id ")
    Long countByIndustrialDesignId(@Param("id") Long industrialDesignId);

    @Query("Select d from DesignSample d left join IndustrialDesignDetail i  ON d.industrialDesignId = i.id  " +
            " where  i.applicationId = :appId " +
            " and (:query IS NULL OR UPPER(d.name) LIKE CONCAT('%',UPPER(:query),'%') ) and " +
            " (:withDescription IS NULL OR d.description IS NOT NULL)")
    Page<DesignSample> findDesignSamplesByIndustrialDesignId(@Param("appId") Long appId,
                                                             @Param("query") String query ,
                                                             @Param("withDescription") Boolean withDescription,
                                                             Pageable pageable);
    @Query("Select d from DesignSample d Left join IndustrialDesignDetail i on i.id = d.industrialDesignId where i.applicationId = :appId ")
    List<DesignSample> findDesignSamplesByAppId(@Param("appId") Long appId);

    @Modifying
    @Query(value =
            "WITH ranked_samples AS ( " +
                    "    SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS new_code " +
                    "    FROM " +
                    "        application.design_sample d " +
                    "    WHERE " +
                    "        industrial_design_id  = :industrialDesignId  and d.is_deleted = 0 " +
                    "    ORDER BY " +
                    "        id " +
                    ") " +
                    "UPDATE " +
                    "    application.design_sample ds " +
                    "SET " +
                    "    code = rs.new_code " +
                    "FROM " +
                    "    ranked_samples rs " +
                    "WHERE " +
                    "    ds.id = rs.id and ds.is_deleted = 0", nativeQuery = true)
    void reIndexDesignSampleCode(@Param("industrialDesignId") Long industrialDesignId);


    @Modifying
    @Query(value =
            "DELETE FROM application.design_sample_sub_classifications dssc  " +
                    "USING application.sub_classifications sc,  " +
                    "      application.classifications ac,  " +
                    "      application.lk_classification_units lu,  " +
                    "      application.design_sample ds,  " +
                    "      application.industrial_design_details idd,  " +
                    "      application.applications_info ai  " +
                    "WHERE sc.id = dssc.sub_classification_id  " +
                    "  AND ac.id = sc.classification_id  " +
                    "  AND lu.id = ac.unit_id  " +
                    "  AND ds.id = dssc.design_sample_id  " +
                    "  AND idd.id = ds.industrial_design_id  " +
                    "  AND ai.id = idd.application_id  " +
                    "  AND lu.version_id = (select max(id) from application.lk_classification_versions lcv where lcv.category_id = 2)  " +
                    "  AND ai.lk_category_id = 2  " +
                    "  AND ai.application_status_id IN (1, 2, 3)  ", nativeQuery = true)
    void deleteWithOldLocarno();

}
