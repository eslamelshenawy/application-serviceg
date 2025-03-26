package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LKNexuoUserDto;
import gov.saip.applicationservice.common.model.LkNexuoUser;
import org.mapstruct.Mapper;

@Mapper
public interface LkNexuoUserMapper extends BaseMapper<LkNexuoUser, LKNexuoUserDto> {

 }
