package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkAttributeDto;
import gov.saip.applicationservice.common.model.LkAttribute;
import org.mapstruct.Mapper;

@Mapper
public interface LkAttributeMapper extends BaseMapper<LkAttribute, LkAttributeDto> {
}
