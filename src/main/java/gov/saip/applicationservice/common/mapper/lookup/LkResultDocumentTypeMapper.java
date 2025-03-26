package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkResultDocumentTypeDto;
import gov.saip.applicationservice.common.model.LkResultDocumentType;
import org.mapstruct.Mapper;

@Mapper
public interface LkResultDocumentTypeMapper extends BaseMapper<LkResultDocumentType, LkResultDocumentTypeDto> {

}
