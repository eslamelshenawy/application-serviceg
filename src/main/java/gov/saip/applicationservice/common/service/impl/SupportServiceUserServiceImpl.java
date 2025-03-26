package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.clients.rest.feigns.UserManageClient;
import gov.saip.applicationservice.common.dto.SupportServiceUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.mapper.SupportServiceUserMapper;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.SupportServiceUser;
import gov.saip.applicationservice.common.repository.SupportServiceUserRepository;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.SupportServiceUserService;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupportServiceUserServiceImpl extends BaseServiceImpl<SupportServiceUser, Long> implements SupportServiceUserService {

    private final SupportServiceUserRepository applicationUserRepository;
    private final UserManageClient userManageClient;
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;
    private final SupportServiceUserMapper supportServiceUserMapper;

    @Override
    protected BaseRepository<SupportServiceUser, Long> getRepository() {
        return applicationUserRepository;
    }

    @Override
    public void updateSupportServiceUser(String userName, Long supportServiceId, ApplicationUserRoleEnum userRole) {
        applicationUserRepository.updateSupportServiceUser(userName, supportServiceId, userRole);
    }

    @Override
    public SupportServiceUser insert(SupportServiceUser entity) {
        SupportServiceUserDto supportServiceUserDto = supportServiceUserMapper.map(entity);
        Optional<SupportServiceUser> applicationUser = applicationUserRepository.findByUserNameAndApplicationSupportServicesTypeIdAndUserRole(
                entity.getUserName(), entity.getApplicationSupportServicesType().getId(), entity.getUserRole());
        if (!applicationUser.isPresent()) {
            ApplicationSupportServicesType applicationSupportServicesType = applicationSupportServicesTypeService.findById(entity.getApplicationSupportServicesType().getId());
            entity.setApplicationSupportServicesType(applicationSupportServicesType);
        } else
            return applicationUser.get();

        return super.insert(supportServiceUserMapper.unMap(supportServiceUserDto));
    }

    @Override
    public SupportServiceUser update(SupportServiceUser entity) {
        String[] params = {entity.getApplicationSupportServicesType().getId().toString() + " and " + entity.getUserRole().name()};
        SupportServiceUser applicationUser = applicationUserRepository.findByApplicationSupportServicesTypeIdAndUserRole
                (entity.getApplicationSupportServicesType().getId(), entity.getUserRole()).orElseThrow(() ->
                new BusinessException(Constants.ErrorKeys.EXCEPTION_RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, params));
        applicationUser.setUserName(entity.getUserName());
        return super.update(applicationUser);

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


    @Override
    public List<SupportServiceUserDto> getUserByRoles(Long appId, List<ApplicationUserRoleEnum> roleNames) {
        List<SupportServiceUserDto> users = supportServiceUserMapper.map(applicationUserRepository.getUserByAppIdAndRoleNames(appId, roleNames));
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

}
