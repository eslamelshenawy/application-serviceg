package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationPriorityStatusDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import org.mapstruct.Mapper;

@Mapper
public interface LkApplicationStatusMapper extends BaseMapper<LkApplicationStatus, LkApplicationStatusDto> {

 }
