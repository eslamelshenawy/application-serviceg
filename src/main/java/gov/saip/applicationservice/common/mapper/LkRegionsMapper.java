package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkRegionsDto;
import gov.saip.applicationservice.common.model.LkRegions;
import org.mapstruct.Mapper;

@Mapper(uses = {LkAttributeMapper.class})
public interface LkRegionsMapper extends BaseMapper<LkRegions, LkRegionsDto> {

    @Override
    LkRegionsDto map(LkRegions lkRegions);


    @Override
    LkRegions unMap(LkRegionsDto lkRegionsDto);

}
