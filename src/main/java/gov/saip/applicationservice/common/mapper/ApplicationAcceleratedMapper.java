package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationAcceleratedDto;
import gov.saip.applicationservice.common.dto.ApplicationAcceleratedLightDto;
import gov.saip.applicationservice.common.model.ApplicationAccelerated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface ApplicationAcceleratedMapper extends BaseMapper<ApplicationAccelerated, ApplicationAcceleratedDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ApplicationAcceleratedDto map (ApplicationAccelerated entity);

    ApplicationAccelerated unMap (ApplicationAcceleratedDto dto);

    ApplicationAcceleratedLightDto mapAppAccelerated(ApplicationAccelerated entity);
}