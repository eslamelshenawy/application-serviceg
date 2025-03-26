package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationDrawingDto;
import gov.saip.applicationservice.common.model.ApplicationDrawing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ApplicationDrawingMapper extends BaseMapper<ApplicationDrawing, ApplicationDrawingDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationDrawingDto map(ApplicationDrawing entity);

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    List<ApplicationDrawingDto> map(List<ApplicationDrawing> entities);

    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationDrawing unMap(ApplicationDrawingDto dto);

    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    List<ApplicationDrawing> unMap(List<ApplicationDrawingDto> dtos);
}
