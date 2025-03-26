package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.ApplicationSupportServicesTypeCommentDto;
import gov.saip.applicationservice.common.model.supportService.ApplicationSupportServicesTypeComment;
import gov.saip.applicationservice.util.ApplicationSupportServicesTypeMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ApplicationSupportServicesTypeMapperUtil.class })
public interface ApplicationSupportServicesTypeCommentMapper extends BaseMapper<ApplicationSupportServicesTypeComment, ApplicationSupportServicesTypeCommentDto> {

    @Mapping(source = "applicationSupportServicesTypeId", target = "applicationSupportServicesType", qualifiedByName = "mappingApplicationSupportServicesTypeEntity")
    ApplicationSupportServicesTypeComment unMap (ApplicationSupportServicesTypeCommentDto dto);

    ApplicationSupportServicesTypeCommentDto map(ApplicationSupportServicesTypeComment entity);

}