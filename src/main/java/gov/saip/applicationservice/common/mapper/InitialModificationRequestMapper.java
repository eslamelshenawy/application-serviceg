package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.InitialModificationRequestDto;
import gov.saip.applicationservice.common.model.InitialModificationRequest;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import gov.saip.applicationservice.common.service.LKSupportServiceTypeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { LKSupportServiceTypeService.class })
public interface InitialModificationRequestMapper extends BaseMapper<InitialModificationRequest, InitialModificationRequestDto> {

    @Override
    @Mapping(source = "applicationInfo.id", target = "applicationId")
    InitialModificationRequestDto map(InitialModificationRequest applicationDatabase);

    @Mapping(source = "applicationId", target = "applicationInfo.id")
    @Mapping(source = "lkSupportServiceType.id", target = "lkSupportServiceType", qualifiedByName = "customMappingEntities")
    InitialModificationRequest unMap(InitialModificationRequestDto applicationDatabaseDto);

}
