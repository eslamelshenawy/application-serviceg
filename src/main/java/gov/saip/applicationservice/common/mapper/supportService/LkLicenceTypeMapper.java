package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.license.LkLicenceTypeDto;
import gov.saip.applicationservice.common.model.supportService.LkLicenceType;
import org.mapstruct.Mapper;

@Mapper
public interface LkLicenceTypeMapper  extends BaseMapper<LkLicenceType, LkLicenceTypeDto> {
}
