package gov.saip.applicationservice.common.mapper.industrial;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.industrial.LkShapeDto;
import gov.saip.applicationservice.common.model.industrial.LkShapeType;
import org.mapstruct.Mapper;

@Mapper
public interface LkShapeMapper extends BaseMapper<LkShapeType, LkShapeDto> {
}
