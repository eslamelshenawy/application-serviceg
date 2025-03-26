package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationPreviousRequests;
import gov.saip.applicationservice.common.dto.ApplicationPreviousRequestsDto;
import gov.saip.applicationservice.common.dto.ApplicationSupportServicesTypeDto;
import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.model.LKSupportServices;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {LkApplicationCategoryMapper.class, LKSupportServicesMapper.class})
public interface ApplicationPreviousRequestsMapper extends BaseMapper<ApplicationPreviousRequests, ApplicationPreviousRequestsDto> {

    @Override
    ApplicationPreviousRequestsDto map(ApplicationPreviousRequests applicationDatabase);

    ApplicationPreviousRequests unMap(ApplicationPreviousRequestsDto applicationDatabaseDto);

}
