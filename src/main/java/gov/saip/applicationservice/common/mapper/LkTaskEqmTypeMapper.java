package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkTaskEqmTypeDto;
import gov.saip.applicationservice.common.model.LkTaskEqmType;
import org.mapstruct.Mapper;

@Mapper(uses = LkTaskEqmItemMapper.class)
public interface LkTaskEqmTypeMapper extends BaseMapper<LkTaskEqmType, LkTaskEqmTypeDto> {

}
