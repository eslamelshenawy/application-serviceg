package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.mapper.ApplicationUserMapper;
import gov.saip.applicationservice.common.model.ApplicationUser;
import gov.saip.applicationservice.common.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/kc/application-user", "/internal-calling/application-user","pb/application-user"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationUserController extends BaseController<ApplicationUser, ApplicationUserDto, Long> {

    private final ApplicationUserService applicationUserService;
    private final ApplicationUserMapper applicationUserMapper;
    @Override
    protected BaseService<ApplicationUser, Long> getService() {
        return applicationUserService;
    }

    @Override
    protected BaseMapper<ApplicationUser, ApplicationUserDto> getMapper() {
        return applicationUserMapper;
    }
    @PutMapping("/reassign")
    public void updateApplicationUser(@RequestParam("userName") String userName
            ,@RequestParam("applicationId") Long applicationId
            ,@RequestParam("userRole") ApplicationUserRoleEnum userRole) {
        applicationUserService.updateApplicationUser(userName, applicationId, userRole);
    }


    @GetMapping("/user-names")
    public ApiResponse<List<String>> ListUsers(
            @RequestParam(value = "applicationId") Long applicationId
            , @RequestParam(required = false,value ="userRoles") List<ApplicationUserRoleEnum> userRoles) {
        return ApiResponse.ok( applicationUserService.listUsersByApplicationIdAndRoleName(applicationId,userRoles));
    }

    @GetMapping("/usernames")
    public ApiResponse<List<String>> listUsernamesByAppAndRoles(@RequestParam(value = "applicationId") Long applicationId
            , @RequestParam(required = false,value ="userRoles") List<ApplicationUserRoleEnum> userRoles) {
        return ApiResponse.ok( applicationUserService.listUsernamesByAppAndRoles(applicationId,userRoles));
    }
    @GetMapping("/user-roles")
    public ApiResponse<List<ApplicationUserDto>> getApplicationUserRoles(@RequestParam(value = "applicationId") Long applicationId
            , @RequestParam(required = false,value ="userRoles") List<ApplicationUserRoleEnum> userRoles) {
        return ApiResponse.ok( applicationUserService.getUserByRoles(applicationId,userRoles));
    }


    @GetMapping("/user-authorized")
    public ApiResponse<Boolean> checkUserByAppIdAndUserName(@RequestParam(value = "applicationId") Long applicationId
            , @RequestParam(required = false,value ="userName") String userName) {
        return ApiResponse.ok( applicationUserService.checkUserByAppIdAndUserName(applicationId,userName));
    }


	

}
