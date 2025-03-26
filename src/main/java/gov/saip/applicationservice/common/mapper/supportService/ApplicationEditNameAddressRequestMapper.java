package gov.saip.applicationservice.common.mapper.supportService;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.supportService.ApplicationEditNameAddressRequestDto;
import gov.saip.applicationservice.common.model.supportService.application_edit_name_address.ApplicationEditNameAddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface ApplicationEditNameAddressRequestMapper extends BaseMapper<ApplicationEditNameAddressRequest, ApplicationEditNameAddressRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    ApplicationEditNameAddressRequestDto map(ApplicationEditNameAddressRequest entity);


    @Override
    @Mapping(source = "applicationId", target = "applicationInfo.id")
    ApplicationEditNameAddressRequest unMap(ApplicationEditNameAddressRequestDto dto);


}
