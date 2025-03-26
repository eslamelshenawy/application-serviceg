package gov.saip.applicationservice.common.repository.patent;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ProtectionElementCounts;
import gov.saip.applicationservice.common.model.patent.ProtectionElements;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectionElementRepository extends BaseRepository<ProtectionElements, Long> {

    @Query("""
select p
from ProtectionElements p
WHERE p.application.id = :applicationId AND p.parentElement.id is null AND p.isEnglish = :isEnglish  AND p.isDeleted = 0
order by p.createdDate asc 
""")
    List<ProtectionElements> getParentProtectionElements(@Param("applicationId") Long applicationId, @Param("isEnglish") Boolean isEnglish);
    @Query("""
select p
from ProtectionElements p
WHERE p.application.id = :applicationId AND p.parentElement.id is null AND p.isDeleted = 0
order by p.createdDate asc
""")
    List<ProtectionElements> getParentProtectionElementsByApplicationId(@Param("applicationId") Long applicationId);

    @Query("""
select p
from ProtectionElements p
WHERE p.application.id = :applicationId AND p.parentElement.id = :parentId AND p.isDeleted = 0
order by p.createdDate asc 
""")
    List<ProtectionElements> getChildrenProtectionElements(@Param("applicationId") Long applicationId, @Param("parentId") Long parentId);

    @Query("""
    select count(p)
    from ProtectionElements p
    WHERE p.application.id = :applicationId AND p.parentElement.id is null AND p.isDeleted = 0
""")
    Long getCountParentProtectionElementsByApplicationId(@Param("applicationId") Long applicationId);
    @Query("""
    select count(p)
    from ProtectionElements p
    WHERE p.application.id = :applicationId AND p.parentElement.id is not null AND p.isDeleted = 0
""")
    Long getCountChildrenProtectionElementsByApplicationId(@Param("applicationId") Long applicationId);


    @Query("""
    SELECT new gov.saip.applicationservice.common.dto.ProtectionElementCounts( 
    SUM(CASE WHEN p.parentElement.id IS NULL THEN 1 ELSE 0 END),
    SUM(CASE WHEN p.parentElement.id IS NOT NULL THEN 1 ELSE 0 END),0L
        )
    FROM ProtectionElements p
    WHERE p.application.id = :applicationId AND p.isDeleted = 0
""")
    ProtectionElementCounts getProtectionElementCountsByApplicationId(@Param("applicationId") Long applicationId);

    @Query("""
             select pe.description from ProtectionElements pe
             join pe.application ai
             where ai.id = :applicationId
             order by pe.createdDate asc
             """)
    List<String> getProtectionElementsDescByApplicationId (@Param("applicationId") Long applicationId);
}
