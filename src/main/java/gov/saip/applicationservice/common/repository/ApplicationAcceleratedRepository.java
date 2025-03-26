package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.dto.ApplicationAcceleratedDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.model.ApplicationAccelerated;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationAcceleratedRepository extends JpaRepository<ApplicationAccelerated, Long> {
    Optional<ApplicationAccelerated> findByApplicationInfo(ApplicationInfo applicationInfo);
    @Query(value = "SELECT COUNT(aa) > 0 FROM ApplicationAccelerated aa WHERE aa.applicationInfo.id = :id AND aa.decision = 'ACCEPTED'")
    Boolean checkIfApplicationAccelrated(@Param(value = "id") Long id);
    @Query(value = "SELECT COUNT(aa) > 0 FROM ApplicationAccelerated aa WHERE aa.applicationInfo.id = :id ")
    Boolean checkIfApplicationAccelratedExist(@Param(value = "id") Long id);

@Query("""
        select acc FROM ApplicationAccelerated  acc 
        join fetch ApplicationInfo  app on acc.applicationInfo.id =app.id
        where app.id =:appId
        """)
    Optional<ApplicationAccelerated> findOptionalAcceleratedByApplicationId(@Param(value = "appId") Long appId);


    @Query(value = "SELECT new ApplicationAccelerated(aa.id, aa.createdDate) from " +
            "ApplicationAccelerated aa " +
            "where aa.applicationInfo.id = :id order by aa.createdDate desc")
    List<ApplicationAccelerated> findByApplicationInfoId(@Param(value = "id") Long id);
    
    @Modifying
    @Query("delete from ApplicationAccelerated ac where ac.applicationInfo.id = :appId")
    void deleteByApplicationId(@Param("appId")Long appId);

    @Query("SELECT ac FROM ApplicationAccelerated ac " +
            "WHERE ac.applicationInfo.id in (:appIds)")
    List<ApplicationAccelerated> findByApplicationIds(@Param("appIds") List<Long> appIds);


    @Query("select case when (count(a) > 0)  then true else false end from ApplicationAccelerated a where a.applicationInfo.id = :appId and a.decision is not null ")
    boolean checkApplicationAcceleratedHasDecisionTakenYet(@Param("appId") long appIde);


    @Query("""
        select acc FROM ApplicationAccelerated  acc
        join fetch ApplicationInfo  app on acc.applicationInfo.id =app.id
        where app.id =:appId
        """)
    ApplicationAcceleratedDto findAcceleratedByApplicationId(@Param(value = "appId") Long appId);
}
