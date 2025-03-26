package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.ApplicationEditTrademarkImageRequestDto;
import gov.saip.applicationservice.common.mapper.DocumentMapper;
import gov.saip.applicationservice.common.model.projections.ApplicationSupportServiceProjectionDetails;
import gov.saip.applicationservice.common.model.supportService.application_edit_trademark_image_request.ApplicationEditTrademarkImageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface ApplicationEditTrademarkImageRequestMapper extends BaseMapper<ApplicationEditTrademarkImageRequest, ApplicationEditTrademarkImageRequestDto> {
    
    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationEditTrademarkImageRequestDto map(ApplicationEditTrademarkImageRequest entity);
    
    
    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationEditTrademarkImageRequest unMap(ApplicationEditTrademarkImageRequestDto dto);
    
    
    @Mapping(source = "projection.applicationNumber", target = "applicationNumber")
    @Mapping(source = "projection.ownerNameAr", target = "ownerNameAr")
    @Mapping(source = "projection.ownerNameEn", target = "ownerNameEn")
    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "applicationId", source = "request.applicationInfo.id")
    @Mapping(target = "oldDocument", source = "request.oldDocument")
    @Mapping(target = "newDocument", source = "request.newDocument")
    @Mapping(target = "oldDescription", source = "request.oldDescription")
    @Mapping(target = "newDescription", source = "request.newDescription")
    @Mapping(target = "oldNameAr", source = "request.oldNameAr")
    @Mapping(target = "newNameAr", source = "request.newNameAr")
    @Mapping(target = "oldNameEn", source = "request.oldNameEn")
    @Mapping(target = "newNameEn", source = "request.newNameEn")
    @Mapping(target = "requestNumber", source = "request.requestNumber")
    @Mapping(target = "requestStatus", source = "request.requestStatus")
    @Mapping(target = "createdDate", source = "request.createdDate")
    @Mapping(target = "createdByUser", source = "request.createdByUser")
    ApplicationEditTrademarkImageRequestDto mapProjectionToDto(ApplicationSupportServiceProjectionDetails<ApplicationEditTrademarkImageRequest> projection);
    
}