package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.AgentSubstitutionRequestDto;
import gov.saip.applicationservice.common.dto.LKSupportServicesDto;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.LKSupportServices;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface LKSupportServicesMapper extends BaseMapper<LKSupportServices, LKSupportServicesDto> {

    @Override
    LKSupportServicesDto map(LKSupportServices applicationDatabase);

    LKSupportServices unMap(LKSupportServicesDto applicationDatabaseDto);


}
