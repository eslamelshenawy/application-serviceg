package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationInfoDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantTypeLightDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ApplicationRelevantTypeMapper extends BaseMapper<ApplicationRelevantType, ApplicationRelevantTypeDto> {

    @Mapping(target = "type", expression = "java(entity.getType().name())")
    ApplicationRelevantTypeLightDto toLightDto(ApplicationRelevantType entity);
}