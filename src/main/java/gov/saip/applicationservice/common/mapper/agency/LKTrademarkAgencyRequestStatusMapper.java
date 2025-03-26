package gov.saip.applicationservice.common.mapper.agency;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.agency.LKTrademarkAgencyRequestStatusDto;
import gov.saip.applicationservice.common.model.agency.LKTrademarkAgencyRequestStatus;
import org.mapstruct.Mapper;

@Mapper
public interface LKTrademarkAgencyRequestStatusMapper extends BaseMapper<LKTrademarkAgencyRequestStatus, LKTrademarkAgencyRequestStatusDto> {
}
