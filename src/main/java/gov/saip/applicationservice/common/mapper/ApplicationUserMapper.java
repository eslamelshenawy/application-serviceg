package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationUserDto;
import gov.saip.applicationservice.common.model.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ApplicationInfoMapper.class})
public interface ApplicationUserMapper extends BaseMapper<ApplicationUser, ApplicationUserDto> {

    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationUser unMap(ApplicationUserDto applicationUserDto);
}
