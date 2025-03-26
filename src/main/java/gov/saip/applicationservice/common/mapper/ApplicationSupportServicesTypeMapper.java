package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationSupportServicesTypeDto;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import org.mapstruct.Mapper;

@Mapper(uses = ApplicationInfoMapper.class)
public interface ApplicationSupportServicesTypeMapper extends BaseMapper<ApplicationSupportServicesType, ApplicationSupportServicesTypeDto> {

    @Override
    ApplicationSupportServicesTypeDto map(ApplicationSupportServicesType applicationDatabase);

    @Override
     ApplicationSupportServicesType unMap(ApplicationSupportServicesTypeDto ApplicationSupportServicesTypeDto);

}
