package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.UserManageClient;
import gov.saip.applicationservice.common.dto.ApplicationUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.mapper.ApplicationUserMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationUser;
import gov.saip.applicationservice.common.repository.ApplicationUserRepository;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationUserService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl extends BaseServiceImpl<ApplicationUser, Long> implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserManageClient userManageClient;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationUserMapper applicationUserMapper;
    @Override
    protected BaseRepository<ApplicationUser, Long> getRepository() {
        return applicationUserRepository;
    }
    @Override
    public void updateApplicationUser(String userName, Long applicationId, ApplicationUserRoleEnum userRole) {
        applicationUserRepository.updateApplicationUser(userName, applicationId, userRole);
        updateLastApplicationInternalUserName( applicationId, userName,LocalDateTime.now());

    }

    @Override
    @Transactional
    public ApplicationUser insert(ApplicationUser entity) {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findByApplicationInfoIdAndUserRole(
                 entity.getApplicationInfo().getId(), entity.getUserRole());
        if (!applicationUser.isPresent()) {
            ApplicationInfo applicationInfo = applicationInfoService.findById(entity.getApplicationInfo().getId());
            entity.setApplicationInfo(applicationInfo);
            super.insert(entity);
            updateLastApplicationInternalUserName( entity.getApplicationInfo().getId(), entity.getUserName(), LocalDateTime.now());
        }else {
            entity = this.update(entity);
        }


        return entity;
    }

    @Override
    @Transactional
    public ApplicationUser update(ApplicationUser entity) {
        String[] params = {entity.getApplicationInfo().getId().toString() + " and " + entity.getUserRole().name()};
        ApplicationUser applicationUser = applicationUserRepository.findByApplicationInfoIdAndUserRole
                (entity.getApplicationInfo().getId(), entity.getUserRole()).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
        applicationUser.setUserName(entity.getUserName());
        updateLastApplicationInternalUserName( entity.getApplicationInfo().getId(), entity.getUserName(),LocalDateTime.now());
        return super.update(applicationUser);

    }

    private void updateLastApplicationInternalUserName(Long id, String userName,LocalDateTime lastUserModifiedDate) {
        applicationUserRepository.updateLastInternalApplicationUserName(id,userName,lastUserModifiedDate);
    }

    @Override
    public List<String> listUsersByApplicationIdAndRoleName(Long appId, List<ApplicationUserRoleEnum> roleNames) {


        List<String> userNames = applicationUserRepository.getUserByAppIdAndRoleName(appId, roleNames);

        return userManageClient.getFullNameByUsernames(userNames).getPayload();
    }

    @Override
    public List<String> listUsernamesByAppAndRoles(Long appId, List<ApplicationUserRoleEnum> roleNames) {
        return applicationUserRepository.getUserByAppIdAndRoleName(appId, roleNames);
    }

    @Override
    public String getUsernameByAppAndRole(Long appId, ApplicationUserRoleEnum role) {
        return applicationUserRepository.getUserByAppIdAndRoleName(appId, role);
    }

    @Override
    public List<String> listUsersByApplicationId(Long appId) {
        return applicationUserRepository.getUserByAppId(appId);
    }
    public List<String> listUsersByByApplicationIdAndRole(Long appId, ApplicationUserRoleEnum role){
     return  applicationUserRepository.getUserByAppIdAndRole(appId, role);
    }


    @Override
    public List<ApplicationUserDto> getUserByRoles(Long appId, List<ApplicationUserRoleEnum> roleNames) {
        List<ApplicationUserDto> users = applicationUserMapper.map(applicationUserRepository.getUserByAppIdAndRoleNames(appId, roleNames));


        return users;
    }

    @Override
    public void checkApplicationHasUserRole(Long appId, ApplicationUserRoleEnum role) {
        boolean hasRole = applicationUserRepository.checkApplicationHasUserRole(appId, role);
        if (hasRole)
            throw new BusinessException(Constants.ErrorKeys.APPLICATION_ALREADY_ASSIGNED_TO_HEAD_OF_CHECKER,
                    HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public Boolean checkUserByAppIdAndUserName(Long appId, String userName) {
        return applicationUserRepository.checkUserByAppIdAndUserName(appId, userName);
    }

    @Override
    public Boolean checkApplicationHasSpecificUserRole(Long appId, ApplicationUserRoleEnum role) {
        return applicationUserRepository.checkApplicationHasUserRole(appId, role);
    }
}
