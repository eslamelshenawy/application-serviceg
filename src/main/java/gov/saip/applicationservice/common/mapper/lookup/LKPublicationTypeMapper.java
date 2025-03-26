package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LKPublicationTypeDto;
import gov.saip.applicationservice.common.model.LKPublicationType;
import org.mapstruct.Mapper;


@Mapper
public interface LKPublicationTypeMapper extends BaseMapper<LKPublicationType, LKPublicationTypeDto> {



}
