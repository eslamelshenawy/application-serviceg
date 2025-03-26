package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.AgentSubstitutionRequestDto;
import gov.saip.applicationservice.common.dto.LKSupportServiceTypeDto;
import gov.saip.applicationservice.common.model.AgentSubstitutionRequest;
import gov.saip.applicationservice.common.model.LKSupportServiceType;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface LKSupportServiceTypeMapper extends BaseMapper<LKSupportServiceType, LKSupportServiceTypeDto> {

    @Override
    LKSupportServiceTypeDto map(LKSupportServiceType applicationDatabase);

    LKSupportServiceType unMap(LKSupportServiceTypeDto applicationDatabaseDto);

}
