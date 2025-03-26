package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationPriority;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationPriorityRepository extends BaseRepository<ApplicationPriority, Long> {
    Optional<ApplicationPriority> findByPriorityApplicationNumber(String priorityApplicationNumber);

    @EntityGraph(attributePaths = {"applicationInfo"})
    @Query("SELECT ap FROM ApplicationPriority ap join fetch ap.applicationInfo app " +
            "where (ap.isExpired is null or ap.isExpired = false  ) and ap.provideDocLater = true and (ap.translatedDocument = null or ap.priorityDocument = null  )" +
            "and app.filingDate is not null and ABS(DATE_PART('day', (CURRENT_TIMESTAMP - app.filingDate))) >= :days ")
    List<ApplicationPriority> getAppsOutOfDate(@Param("days") Long days);


    @EntityGraph(attributePaths = {"applicationInfo"})
    @Query("SELECT ap FROM ApplicationPriority ap JOIN ap.applicationInfo app" +
            " WHERE (ap.isExpired IS NULL OR ap.isExpired = FALSE) AND ap.provideDocLater = TRUE AND (ap.translatedDocument IS NULL OR ap.priorityDocument IS NULL) AND app.filingDate IS NOT NULL " +
            "AND abs(DATE_PART('day',(CURRENT_TIMESTAMP - app.filingDate)) )=:days and abs(  DATE_PART('hour',(CURRENT_TIMESTAMP - app.filingDate))  ) = :hours")
    List<ApplicationPriority> getAppsAboutToExpire(@Param("days") Long days, @Param("hours") Long hours);


    @Query("SELECT ap FROM ApplicationPriority ap JOIN FETCH ap.applicationInfo app where app.id = :id AND ap.isDeleted = :isDeleted And ap.isExpired is null order by  ap.id asc")
    List<ApplicationPriority> getPrioritesOfByApplicationId(@Param("id")Long id, @Param("isDeleted") int isDeleted);



    @Query("""
            SELECT ap FROM ApplicationPriority ap JOIN ap.applicationInfo app 
            where app.id = :appId AND ap.isDeleted =0 And
            ap.priorityDocument.id is  null order by  ap.id asc
            """)
    List<ApplicationPriority> getPrioritiesThatHaveNotPriorityDocument(@Param("appId")Long appId);

    @Query("""
            SELECT case 
            when (count(*) > 0)  then true else false end
            FROM ApplicationPriority ap
             JOIN  ap.applicationInfo app where app.id = :id 
             AND ap.isDeleted = :isDeleted And ap.isExpired is null 
             And ap.priorityStatus is null
            """)
    Boolean doesApplicationHasPrioritiesThatNoActionTaken(@Param("id") Long id, @Param("isDeleted") int isDeleted);
    @Modifying
    @Transactional
    @Query(value = "delete from application.application_priority where application_info_id = :applicationInfoId", nativeQuery = true)
    int hardDeleteByApplicationInfoId(@Param("applicationInfoId") Long applicationInfoId);

    @Modifying
    @Query("update  ApplicationPriority app set app.isDeleted='1'  " +
            "WHERE app.applicationInfo.id = :appId ")
    void softdeleteAppById(@Param("appId") Long appId);


    @Query(value = """
    select case when (count(app) > 0) then true else false end 
        from ApplicationPriority app
        where app.applicationInfo.id = :appId
        and app.provideDocLater = true
        and app.isDeleted = 0
    """)
    Boolean checkApplicationPrioritiesProvideDocLater(@Param("appId")Long appId);

    @Query(value = """
    select case when (count(app) > 0) then true else false end 
        from ApplicationPriority app
        where app.applicationInfo.id = :appId
        and app.isDeleted = 0 and app.priorityDocument is null
    """)
    Boolean checkApplicationPrioritiesDocuments(@Param("appId")Long appId);
    @Query(value = """
    select case when (count(app) > 0) then true else false end 
        from ApplicationPriority app
        where app.applicationInfo.id = :appId
        and app.isDeleted = 0
    """)
    Boolean checkApplicationPriorities(@Param("appId")Long appId);
    @Query("SELECT app FROM ApplicationPriority ap JOIN ap.applicationInfo app where ap.id = :id ")
    ApplicationInfo findApplicationInfoBy(@Param("id")Long id);
}
