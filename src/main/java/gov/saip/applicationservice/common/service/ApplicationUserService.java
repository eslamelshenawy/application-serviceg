package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.model.ApplicationUser;

import java.util.List;


public interface ApplicationUserService extends BaseService<ApplicationUser, Long> {

    void updateApplicationUser(String userName, Long applicationId, ApplicationUserRoleEnum userRole);

    List<String> listUsersByApplicationIdAndRoleName(Long appId, List<ApplicationUserRoleEnum> roleNames);

    List<String> listUsernamesByAppAndRoles(Long appId, List<ApplicationUserRoleEnum> roleNames);

    String getUsernameByAppAndRole(Long appId, ApplicationUserRoleEnum role);

    List<String> listUsersByApplicationId(Long appId);

    List<ApplicationUserDto> getUserByRoles(Long appId, List<ApplicationUserRoleEnum> roleNames);

    void checkApplicationHasUserRole(Long appId, ApplicationUserRoleEnum role);

    Boolean checkUserByAppIdAndUserName(Long appId, String userName);
    public Boolean checkApplicationHasSpecificUserRole(Long appId, ApplicationUserRoleEnum role);
     List<String> listUsersByByApplicationIdAndRole(Long appId, ApplicationUserRoleEnum role);
}