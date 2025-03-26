package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.model.SupportServiceUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface SupportServiceUserRepository extends BaseRepository<SupportServiceUser, Long> {

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update SupportServiceUser appUser set appUser.userName = :userName" +
            " where" +
            " appUser.applicationSupportServicesType.id = :supportServiceId" +
            " and appUser.userRole = :userRole")
    void updateSupportServiceUser(@Param("userName") String userName, @Param("supportServiceId") Long supportServiceId
            , @Param("userRole") ApplicationUserRoleEnum userRole);

    Optional<SupportServiceUser> findByUserNameAndApplicationSupportServicesTypeIdAndUserRole(@Param("userName") String userName, @Param("supportServiceId") Long supportServiceId
            , @Param("userRole") ApplicationUserRoleEnum userRole);

    Optional<SupportServiceUser> findByApplicationSupportServicesTypeIdAndUserRole(@Param("supportServiceId") Long supportServiceId
            , @Param("userRole") ApplicationUserRoleEnum userRole);

    @Query("SELECT  distinct user.userName FROM SupportServiceUser user" +
            " where user.applicationSupportServicesType.id = :appId and (COALESCE(:roleName) is null or user.userRole in :roleName)")
    List<String> getUserByAppIdAndRoleName(@Param("appId")Long appId, @Param("roleName") List<ApplicationUserRoleEnum> roleName);
    @Query("SELECT  distinct user FROM SupportServiceUser user where user.applicationSupportServicesType.id = :appId and (COALESCE(:roleName) is null or user.userRole in :roleName)")
    List<SupportServiceUser> getUserByAppIdAndRoleNames(@Param("appId")Long appId, @Param("roleName") List<ApplicationUserRoleEnum> roleName);

    @Query("SELECT  distinct user.userName FROM SupportServiceUser user where user.applicationSupportServicesType.id = :appId")
    List<String> getUserByAppId(@Param("appId")Long appId);

    @Query("SELECT  distinct user.userName FROM SupportServiceUser user where user.applicationSupportServicesType.id = :appId and user.userRole = :roleName ")
    String getUserByAppIdAndRoleName(@Param("appId")Long appId, @Param("roleName") ApplicationUserRoleEnum roleName);

    @Query("select case when (count(a) > 0)  then true else false end from SupportServiceUser a where a.applicationSupportServicesType.id = :appId and a.userRole = :roleName")
    boolean checkApplicationHasUserRole(@Param("appId") long appId, @Param("roleName") ApplicationUserRoleEnum roleName);

    @Query("SELECT COUNT(user) > 0 FROM SupportServiceUser user WHERE user.applicationSupportServicesType.id = :appId AND user.userName = :userName")
    Boolean checkUserByAppIdAndUserName(@Param("appId") Long appId, @Param("userName") String userName);


}
