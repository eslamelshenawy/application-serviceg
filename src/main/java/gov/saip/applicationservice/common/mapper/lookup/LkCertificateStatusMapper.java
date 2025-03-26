package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkCertificateStatusDto;
import gov.saip.applicationservice.common.model.LkCertificateStatus;
import org.mapstruct.Mapper;

@Mapper
public interface LkCertificateStatusMapper extends BaseMapper<LkCertificateStatus, LkCertificateStatusDto> {

}