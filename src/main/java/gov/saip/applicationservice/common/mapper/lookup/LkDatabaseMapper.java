package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkDatabaseDto;
import gov.saip.applicationservice.common.model.LkDatabase;
import org.mapstruct.Mapper;

@Mapper
public interface LkDatabaseMapper extends BaseMapper<LkDatabase, LkDatabaseDto> {

}
