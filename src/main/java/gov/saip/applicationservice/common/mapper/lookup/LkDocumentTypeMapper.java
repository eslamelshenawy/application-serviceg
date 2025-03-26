package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LKDocumentTypeDto;
import gov.saip.applicationservice.common.model.LkDocumentType;
import org.mapstruct.Mapper;

@Mapper
public interface LkDocumentTypeMapper extends BaseMapper<LkDocumentType, LKDocumentTypeDto> {

 }
