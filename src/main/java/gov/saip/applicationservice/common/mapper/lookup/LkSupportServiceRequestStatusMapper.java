package gov.saip.applicationservice.common.mapper.lookup;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.lookup.LkSupportServiceRequestStatusDto;
import gov.saip.applicationservice.common.model.LKSupportServiceRequestStatus;
import org.mapstruct.Mapper;

@Mapper
public interface LkSupportServiceRequestStatusMapper extends BaseMapper<LKSupportServiceRequestStatus, LkSupportServiceRequestStatusDto> {

 }
