package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkStepsDto;
import gov.saip.applicationservice.common.model.LkStep;
import org.mapstruct.Mapper;

@Mapper
public interface LkStepMapper extends BaseMapper<LkStep, LkStepsDto> {


}
