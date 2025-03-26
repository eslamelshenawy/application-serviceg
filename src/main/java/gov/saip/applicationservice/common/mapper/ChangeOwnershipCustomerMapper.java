package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ChangeOwnershipCustomerDto;
import gov.saip.applicationservice.common.model.ChangeOwnershipCustomer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {DocumentMapper.class})
public interface ChangeOwnershipCustomerMapper extends BaseMapper<ChangeOwnershipCustomer, ChangeOwnershipCustomerDto> {



}
