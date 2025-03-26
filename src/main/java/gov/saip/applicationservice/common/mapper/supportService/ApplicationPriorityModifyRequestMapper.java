package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityModifyRequestDto;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface ApplicationPriorityModifyRequestMapper extends BaseMapper<ApplicationPriorityModifyRequest, ApplicationPriorityModifyRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    @Mapping(source = "applicationPriorityModifyRequestDetails", target = "applicationPriorityDtoList")
    ApplicationPriorityModifyRequestDto map(ApplicationPriorityModifyRequest entity);

    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    @Mapping(source = "applicationPriorityDtoList", target = "applicationPriorityModifyRequestDetails")
    ApplicationPriorityModifyRequest unMap(ApplicationPriorityModifyRequestDto dto);
}