package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.model.ApplicationUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ApplicationUserRepository extends BaseRepository<ApplicationUser, Long> {

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update ApplicationUser appUser set appUser.userName = :userName" +
            " where" +
            " appUser.applicationInfo.id = :applicationId" +
            " and appUser.userRole = :userRole")
    void updateApplicationUser(@Param("userName") String userName, @Param("applicationId") Long applicationId
            , @Param("userRole") ApplicationUserRoleEnum userRole);

    Optional<ApplicationUser> findByUserNameAndApplicationInfoIdAndUserRole(@Param("userName") String userName, @Param("applicationId") Long applicationId
            , @Param("userRole") ApplicationUserRoleEnum userRole);

    Optional<ApplicationUser> findByApplicationInfoIdAndUserRole(@Param("applicationId") Long applicationId
            , @Param("userRole") ApplicationUserRoleEnum userRole);

    @Query("SELECT  distinct user.userName FROM ApplicationUser user where user.applicationInfo.id = :appId and (COALESCE(:roleName) is null or user.userRole in :roleName)")
    List<String> getUserByAppIdAndRoleName(@Param("appId")Long appId, @Param("roleName") List<ApplicationUserRoleEnum> roleName);
    @Query("SELECT  distinct user FROM ApplicationUser user where user.applicationInfo.id = :appId and (COALESCE(:roleName) is null or user.userRole in :roleName)")
    List<ApplicationUser> getUserByAppIdAndRoleNames(@Param("appId")Long appId, @Param("roleName") List<ApplicationUserRoleEnum> roleName);

    @Query("SELECT  distinct user.userRole  FROM ApplicationUser user where user.applicationInfo.id = :appId ")
    List<String> getUserByAppId(@Param("appId")Long appId);

    @Query("SELECT  distinct user.userName  FROM ApplicationUser user where user.applicationInfo.id = :appId and user.userRole= :roleName ")
    List<String> getUserByAppIdAndRole(@Param("appId")Long appId, @Param("roleName") ApplicationUserRoleEnum roleName);

    @Query("SELECT  distinct user.userName FROM ApplicationUser user where user.applicationInfo.id = :appId and user.userRole = :roleName ")
    String getUserByAppIdAndRoleName(@Param("appId")Long appId, @Param("roleName") ApplicationUserRoleEnum roleName);

    @Query("select case when (count(a) > 0)  then true else false end from ApplicationUser a where a.applicationInfo.id = :appId and a.userRole = :roleName")
    boolean checkApplicationHasUserRole(@Param("appId") long appId, @Param("roleName") ApplicationUserRoleEnum roleName);

    @Query("SELECT COUNT(user) > 0 FROM ApplicationUser user WHERE user.applicationInfo.id = :appId AND user.userName = :userName")
    Boolean checkUserByAppIdAndUserName(@Param("appId") Long appId, @Param("userName") String userName);

    @Modifying
    @Transactional
    @Query(value = """
            update ApplicationInfo ap
             set ap.lastInternalUserName = :userName,
             ap.lastUserModifiedDate= :modifiedDate
                where
                ap.id = :appId
              
               """)
    void updateLastInternalApplicationUserName(@Param("appId") Long appId, @Param("userName") String userName, @Param("modifiedDate")LocalDateTime modifiedDate);
}
