package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkDayOfWeekDto;
import gov.saip.applicationservice.common.model.LkDayOfWeek;
import org.mapstruct.Mapper;

@Mapper
public interface LkDayOfWeekMapper extends BaseMapper<LkDayOfWeek, LkDayOfWeekDto> {
}
