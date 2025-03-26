package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import gov.saip.applicationservice.common.model.LkCertificateType;
import org.mapstruct.Mapper;

@Mapper
public interface LkCertificateTypeMapper extends BaseMapper<LkCertificateType, LkCertificateTypeDto> {

}
