package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkSectionDto;
import gov.saip.applicationservice.common.model.LkSection;
import org.mapstruct.Mapper;

@Mapper
public interface LkSectionsMapper extends BaseMapper<LkSection, LkSectionDto> {

}
