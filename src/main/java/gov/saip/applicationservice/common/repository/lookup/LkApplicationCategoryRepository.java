package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LkApplicationCategoryRepository extends BaseRepository<LkApplicationCategory, Long> {



    Optional<LkApplicationCategory> findByApplicationCategoryDescEn(String applicationCategoryDescAr);

    Optional<LkApplicationCategory> findBySaipCode(String saipCode);

    @Query("""
        SELECT LK FROM LkApplicationCategory LK WHERE LK.applicationCategoryIsActive = TRUE 
        """)
    List<LkApplicationCategory> findAllActiveCategories();
    
    @Query("""
            SELECT category FROM LkApplicationCategory category WHERE category.applicationCategoryIsActive = TRUE AND category.saipCode IN (:codes)
            """)
    List<LkApplicationCategory> findCategoriesByCodes(@Param("codes") List<String> codes);

}
