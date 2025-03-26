package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.UserManageClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.mapper.ApplicationUserMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationUser;
import gov.saip.applicationservice.common.repository.ApplicationUserRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationUserServiceImplTest {

    @InjectMocks
    @Spy
    private ApplicationUserServiceImpl applicationUserService;

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Mock
    private UserManageClient userManageClient;

    @Mock
    private ApplicationInfoService applicationInfoService;

    @Mock
    private ApplicationUserMapper applicationUserMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsert() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUserName("testUser");
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        applicationUser.setApplicationInfo(applicationInfo);
        applicationUser.setUserRole(ApplicationUserRoleEnum.CHECKER);

        when(applicationUserRepository.findByUserNameAndApplicationInfoIdAndUserRole("testUser", 1L, ApplicationUserRoleEnum.CHECKER))
                .thenReturn(Optional.empty());

        when(applicationInfoService.findById(1L)).thenReturn(applicationInfo);

        when(applicationUserRepository.save(applicationUser)).thenReturn(applicationUser);

        doReturn(applicationUser).when(applicationUserService).insert(applicationUser);

        ApplicationUser result = applicationUserService.insert(applicationUser);


        assertEquals(applicationUser, result);
    }

    @Test
    public void testUpdate() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUserName("testUser");
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(1L);
        applicationUser.setApplicationInfo(applicationInfo);
        applicationUser.setUserRole(ApplicationUserRoleEnum.CHECKER);

        when(applicationUserRepository.findByApplicationInfoIdAndUserRole(1L, ApplicationUserRoleEnum.CHECKER))
                .thenReturn(Optional.of(applicationUser));

        when(applicationUserRepository.save(applicationUser)).thenReturn(applicationUser);

        doReturn(applicationUser).when(applicationUserService).update(applicationUser);

        ApplicationUser result = applicationUserService.update(applicationUser);

        assertEquals(applicationUser, result);
    }

    @Test
    public void testListUsersByApplicationIdAndRoleName() {
        Long appId = 1L;
        List<ApplicationUserRoleEnum> roleNames = Collections.singletonList(ApplicationUserRoleEnum.CHECKER);
        List<String> userNames = Collections.singletonList("testUser");
        ApiResponse apiResponse = ApiResponse.ok(userNames);
        when(applicationUserRepository.getUserByAppIdAndRoleName(appId, roleNames)).thenReturn(userNames);

        when(userManageClient.getFullNameByUsernames(userNames)).thenReturn(apiResponse);

        List<String> result = applicationUserService.listUsersByApplicationIdAndRoleName(appId, roleNames);

        assertEquals(userNames, result);
    }

    @Test
    public void testListUsernamesByAppAndRoles() {
        Long appId = 1L;
        List<ApplicationUserRoleEnum> roleNames = Collections.singletonList(ApplicationUserRoleEnum.CHECKER);
        List<String> userNames = Collections.singletonList("testUser");

        when(applicationUserRepository.getUserByAppIdAndRoleName(appId, roleNames)).thenReturn(userNames);

        List<String> result = applicationUserService.listUsernamesByAppAndRoles(appId, roleNames);

        assertEquals(userNames, result);
    }

    @Test
    public void testGetUsernameByAppAndRole() {
        Long appId = 1L;
        ApplicationUserRoleEnum role = ApplicationUserRoleEnum.CHECKER;
        String userName = "testUser";

        when(applicationUserRepository.getUserByAppIdAndRoleName(appId, role)).thenReturn(userName);

        String result = applicationUserService.getUsernameByAppAndRole(appId, role);

        assertEquals(userName, result);
    }

    @Test
    public void testListUsersByApplicationId() {
        Long appId = 1L;
        List<String> userNames = Collections.singletonList("testUser");

        when(applicationUserRepository.getUserByAppId(appId)).thenReturn(userNames);

        List<String> result = applicationUserService.listUsersByApplicationId(appId);

        assertEquals(userNames, result);
    }

    @Test
    public void testGetUserByRoles() {
        Long appId = 1L;
        List<ApplicationUserRoleEnum> roleNames = Collections.singletonList(ApplicationUserRoleEnum.CHECKER);
        List<ApplicationUser> users = Collections.singletonList(new ApplicationUser());
        List<ApplicationUserDto> userDtos = Collections.singletonList(new ApplicationUserDto());
        when(applicationUserRepository.getUserByAppIdAndRoleNames(appId, roleNames)).thenReturn(users);
       when(applicationUserMapper.map(users)).thenReturn(userDtos);
        List<ApplicationUserDto> result = applicationUserService.getUserByRoles(appId, roleNames);

        assertEquals(userDtos, result);
    }

    @Test
    public void testUpdateApplicationUser() {
        String userName = "testUser";
        Long applicationId = 1L;
        ApplicationUserRoleEnum userRole = ApplicationUserRoleEnum.CHECKER;

        applicationUserService.updateApplicationUser(userName, applicationId, userRole);

        verify(applicationUserRepository).updateApplicationUser(userName, applicationId, userRole);
    }
}

