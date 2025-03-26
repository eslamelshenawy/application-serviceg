package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LkApplicationStatusRepository extends BaseRepository<LkApplicationStatus, Long> {

    Optional<LkApplicationStatus> findByCode (String code);


    Optional<LkApplicationStatus> findByCodeAndApplicationCategoryId(String code , Long categoryId);


    @Query("""
        select distinct st from LkApplicationStatus st
        join LkApplicationCategory cat on cat.id = st.applicationCategory.id
        where cat.saipCode = :category
    """)
    List<LkApplicationStatus> getStatusByCategory(@Param("category") String category);

    @Query("""
        select status from LkApplicationStatus status
        join ApplicationInfo ai on ai.applicationStatus.id = status.id and ai.id = :appId
    """)
    LkApplicationStatus getStatusByApplicationId(@Param("appId") Long appId);


    @Query("SELECT DISTINCT s FROM LkApplicationStatus s " +
            "JOIN s.applicationCategory c " +
            "WHERE c.id = :categoryId " +
            "and lower(s.ipsStatusDescAr) like CONCAT('%',:search,'%')")
    Page<LkApplicationStatus> findAllApplicationStatusByCategory(@Param("categoryId") Long applicationCategoryId, @Param("search") String search, Pageable pageable);


    @Query("""
        select status from LkApplicationStatus status
        join ApplicationInfo ai on ai.category.id = status.applicationCategory.id 
        where ai.id = :applicationId and status.code = :code
    """)
    Optional<LkApplicationStatus> findByCodeAndApplicationId(@Param("code") String code, @Param("applicationId") Long applicationId);
}
