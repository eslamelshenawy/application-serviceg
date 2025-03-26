package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.SupportServiceUserDto;
import gov.saip.applicationservice.common.enums.ApplicationUserRoleEnum;
import gov.saip.applicationservice.common.enums.AssistiveSupportServiceSpecialistDecision;
import gov.saip.applicationservice.common.model.SupportServiceUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = {ApplicationInfoMapper.class})
public interface SupportServiceUserMapper extends BaseMapper<SupportServiceUser, SupportServiceUserDto> {
    @Override
    @Mapping(source = "applicationSupportServicesTypeId", target = "applicationSupportServicesType.id")
    @Mapping(source = "userRole", target = "userRole", qualifiedByName = "stringToEnum")
    SupportServiceUser unMap(SupportServiceUserDto applicationUserDto);

    @Override
    @Mapping(source = "applicationSupportServicesType.id", target = "applicationSupportServicesTypeId")
    @Mapping(source = "userRole", target = "userRole", qualifiedByName = "enumToString")
    SupportServiceUserDto map(SupportServiceUser applicationUser);

    @Named("enumToString")
    default String enumToString(ApplicationUserRoleEnum userRole) {
        if (userRole == null) {
            return null;
        }
        return userRole.name();
    }

    @Named("stringToEnum")
    default ApplicationUserRoleEnum stringToEnum(String userRole) {
        if (userRole == null || userRole.isEmpty()) {
            return null;
        }

        return ApplicationUserRoleEnum.valueOf(userRole);

    }
}
