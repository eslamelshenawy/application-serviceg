package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ChangeOwnershipRequestDto;
import gov.saip.applicationservice.common.model.ChangeOwnershipRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {DocumentMapper.class})
public interface ChangeOwnershipRequestMapper extends BaseMapper<ChangeOwnershipRequest, ChangeOwnershipRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ChangeOwnershipRequestDto map(ChangeOwnershipRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ChangeOwnershipRequest unMap(ChangeOwnershipRequestDto applicationDatabaseDto);


}
