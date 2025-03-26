package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.EvictionRequestDto;
import gov.saip.applicationservice.common.model.EvictionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DocumentMapper.class})
public interface EvictionRequestMapper extends BaseMapper<EvictionRequest, EvictionRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    EvictionRequestDto map(EvictionRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    EvictionRequest unMap(EvictionRequestDto applicationDatabaseDto);

}
