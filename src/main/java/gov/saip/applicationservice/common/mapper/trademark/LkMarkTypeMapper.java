package gov.saip.applicationservice.common.mapper.trademark;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.trademark.LkMarkTypeDto;
import gov.saip.applicationservice.common.model.trademark.LkMarkType;
import org.mapstruct.Mapper;

@Mapper
public interface LkMarkTypeMapper extends BaseMapper<LkMarkType, LkMarkTypeDto> {
}
