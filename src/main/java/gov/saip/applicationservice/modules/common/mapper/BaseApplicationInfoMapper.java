package gov.saip.applicationservice.modules.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import org.mapstruct.Mapper;

@Mapper
public interface BaseApplicationInfoMapper extends BaseMapper<ApplicationInfo, ApplicationInfoDto>  {

}
