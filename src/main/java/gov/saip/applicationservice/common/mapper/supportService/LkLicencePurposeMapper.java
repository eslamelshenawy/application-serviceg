package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.license.LkLicencePurposeDto;
import gov.saip.applicationservice.common.model.supportService.LkLicencePurpose;
import org.mapstruct.Mapper;

@Mapper
public interface LkLicencePurposeMapper  extends BaseMapper<LkLicencePurpose, LkLicencePurposeDto> {
}
