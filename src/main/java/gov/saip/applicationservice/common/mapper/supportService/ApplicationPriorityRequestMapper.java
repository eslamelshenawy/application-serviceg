package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityRequestDto;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface ApplicationPriorityRequestMapper extends BaseMapper<ApplicationPriorityRequest, ApplicationPriorityRequestDto> {
    
    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationPriorityRequestDto map(ApplicationPriorityRequest entity);
    
    
    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationPriorityRequest unMap(ApplicationPriorityRequestDto dto);
}