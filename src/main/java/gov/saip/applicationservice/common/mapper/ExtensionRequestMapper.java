package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ExtensionRequestDto;
import gov.saip.applicationservice.common.model.ExtensionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DocumentMapper.class})
public interface ExtensionRequestMapper extends BaseMapper<ExtensionRequest, ExtensionRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ExtensionRequestDto map(ExtensionRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ExtensionRequest unMap(ExtensionRequestDto applicationDatabaseDto);

}
