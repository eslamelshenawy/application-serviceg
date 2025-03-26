package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationCategoryToPublicationCountProjection;
import gov.saip.applicationservice.common.model.ApplicationPublication;
import gov.saip.applicationservice.common.model.ApplicationPublicationSummaryProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ApplicationPublicationRepository extends BaseRepository<ApplicationPublication, Long> {
    //TODO Fix statistics query
    @Query("""
            SELECT new gov.saip.applicationservice.common.model.ApplicationCategoryToPublicationCountProjection(
                ac.id,
                ac.saipCode,
                ac.applicationCategoryDescEn,
                ac.applicationCategoryDescAr,
                
                (
                         SELECT COUNT(ap.id)
                         FROM ApplicationPublication ap
                         LEFT JOIN ap.applicationInfo ai
                         WHERE ac.id = ai.category.id
                 )
                 AS count
            )
            FROM LkApplicationCategory ac
            """)
    List<ApplicationCategoryToPublicationCountProjection> countPublicationsByApplicationCategory(@Param("now") LocalDateTime now);


    @Query("""
            SELECT ap
            FROM ApplicationPublication ap
            WHERE ap.applicationInfo.id = :applicationId
            ORDER BY ap.createdDate DESC
            """)
    Optional<List<ApplicationPublication>> findByApplicationId(@Param("applicationId") Long applicationId);
    
    @Query("""
            SELECT ap.publicationDate
            FROM ApplicationPublication ap
            WHERE ap.id = :id
            """)
    Optional<LocalDateTime> findPublicationDateById(@Param("id") Long id);


    @Query("""
            select ap.publicationDateHijri from ApplicationPublication ap join ap.publicationType pt 
            where ap.applicationInfo.id= :appId 
            and ap.applicationSupportServicesType.id= :serviceId
            and pt.code= :publicationCode
            """)
    String findApplicationPublicationDateByAppIdAndSupportServiceIdAndType(@Param("appId")Long appId,
                                                                              @Param("serviceId")Long serviceId,
                                                                              @Param("publicationCode")String publicationCode);


    @Query("""
            select max (ap.publicationDate) from ApplicationPublication ap join ap.publicationType pt where ap.applicationInfo.id= :appId and pt.code='TRADEMARK_REGISTERATION'
            """)
    Optional<LocalDate> findApplicationPublicationDateByAppId(@Param("appId")Long appId);




    @Query("""
            select max (ap.publicationDate) from ApplicationPublication ap join ap.publicationType pt where ap.applicationInfo.id= :appId and pt.code= :publicationType
            """)
    Optional<LocalDate> findApplicationPublicationDateByAppIdAndType(@Param("appId")Long appId,@Param("publicationType")String publicationType);

    @Query("""
            SELECT new gov.saip.applicationservice.common.model.ApplicationPublicationSummaryProjection(ap.publicationNumber, ap.publicationDate, ap.publicationType.code, ap.applicationInfo.registrationDate, pi.issueNumber)
            FROM ApplicationPublication ap
            left join PublicationIssueApplicationPublication piap on piap.applicationPublication.id=ap.id
            left join piap.publicationIssue pi
            where ap.id = (
            select max(ap2.id)
            from ApplicationPublication ap2
            join ap2.applicationInfo ai
            left join PublicationIssueApplicationPublication piap2 on piap2.applicationPublication.id=ap2.id
            where ai.id = :applicationId and (:publicationType is null or ap2.publicationType.code = :publicationType))
            """)
    ApplicationPublicationSummaryProjection getApplicationPublicationSummaryProjection(@Param("applicationId") Long applicationId,
                                                                                       @Param("publicationType") String publicationType);


    @Modifying
    @Query("""
            update ApplicationPublication p set p.publicationNumber = :publicationNumber where p.id = :id
            """)
    void updatePublicationNumber(@Param("id") Long id, @Param("publicationNumber") String publicationNumber);

}
