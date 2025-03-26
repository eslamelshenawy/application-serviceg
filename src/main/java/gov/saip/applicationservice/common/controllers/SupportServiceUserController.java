package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SupportServiceUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.mapper.SupportServiceUserMapper;
import gov.saip.applicationservice.common.model.SupportServiceUser;
import gov.saip.applicationservice.common.service.SupportServiceUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/support-service-user", "/internal-calling/support-service-user","pb/support-service-user"})
@RequiredArgsConstructor
@Slf4j
public class SupportServiceUserController extends BaseController<SupportServiceUser, SupportServiceUserDto, Long> {

    private final SupportServiceUserService supportServiceUserService;
    private final SupportServiceUserMapper supportServiceUserMapper;

    @Override
    protected BaseService<SupportServiceUser, Long> getService() {
        return  supportServiceUserService;
    }

    @Override
    protected BaseMapper<SupportServiceUser, SupportServiceUserDto> getMapper() {
        return supportServiceUserMapper;
    }
    @PutMapping("/reassign")
    public void updateSupportServiceUser(@RequestParam("userName") String userName
            ,@RequestParam("supportServiceId") Long supportServiceId
            ,@RequestParam("userRole") ApplicationUserRoleEnum userRole) {
        supportServiceUserService.updateSupportServiceUser(userName, supportServiceId, userRole);
    }


    @GetMapping("/user-names")
    public ApiResponse<List<String>> ListUsers(
            @RequestParam(value = "supportServiceId") Long supportServiceId
            , @RequestParam(required = false,value ="userRoles") List<ApplicationUserRoleEnum> userRoles) {
        return ApiResponse.ok( supportServiceUserService.listUsersByApplicationIdAndRoleName(supportServiceId,userRoles));
    }

    @GetMapping("/usernames")
    public ApiResponse<List<String>> listUsernamesByAppAndRoles(@RequestParam(value = "supportServiceId") Long supportServiceId
            , @RequestParam(required = false,value ="userRoles") List<ApplicationUserRoleEnum> userRoles) {
        return ApiResponse.ok( supportServiceUserService.listUsernamesByAppAndRoles(supportServiceId,userRoles));
    }
    @GetMapping("/user-roles")
    public ApiResponse<List<SupportServiceUserDto>> getSupportServiceUserRoles(@RequestParam(value = "supportServiceId") Long supportServiceId
            , @RequestParam(required = false,value ="userRoles") List<ApplicationUserRoleEnum> userRoles) {
        return ApiResponse.ok( supportServiceUserService.getUserByRoles(supportServiceId,userRoles));
    }


    @GetMapping("/user-authorized")
    public ApiResponse<Boolean> checkUserByAppIdAndUserName(@RequestParam(value = "supportServiceId") Long supportServiceId
            , @RequestParam(required = false,value ="userName") String userName) {
        return ApiResponse.ok( supportServiceUserService.checkUserByAppIdAndUserName(supportServiceId,userName));
    }
	

}
