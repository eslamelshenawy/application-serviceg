package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.SupportServiceUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.model.SupportServiceUser;

import java.util.List;


public interface SupportServiceUserService extends BaseService<SupportServiceUser, Long> {

    void updateSupportServiceUser(String userName, Long supportServiceId, ApplicationUserRoleEnum userRole);

    List<String> listUsersByApplicationIdAndRoleName(Long appId, List<ApplicationUserRoleEnum> roleNames);

    List<String> listUsernamesByAppAndRoles(Long appId, List<ApplicationUserRoleEnum> roleNames);

    String getUsernameByAppAndRole(Long appId, ApplicationUserRoleEnum role);

    List<String> listUsersByApplicationId(Long appId);

    List<SupportServiceUserDto> getUserByRoles(Long appId, List<ApplicationUserRoleEnum> roleNames);

    void checkApplicationHasUserRole(Long appId, ApplicationUserRoleEnum role);

    Boolean checkUserByAppIdAndUserName(Long appId, String userName);

}