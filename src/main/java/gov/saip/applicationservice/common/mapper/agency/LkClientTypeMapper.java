package gov.saip.applicationservice.common.mapper.agency;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.agency.LkClientTypeDto;
import gov.saip.applicationservice.common.model.agency.LkClientType;
import org.mapstruct.Mapper;

@Mapper
public interface LkClientTypeMapper extends BaseMapper<LkClientType, LkClientTypeDto> {

//    @Override
//    LkClientTypeDto map(LkClientType lkClientType);
//
//    @Override
//    LkClientType unMap(LkClientTypeDto lkClientTypeDto);
}
