package gov.saip.applicationservice.common.mapper.agency;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.agency.ApplicationAgentDto;
import gov.saip.applicationservice.common.model.agency.ApplicationAgent;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ApplicationAgentMapper extends BaseMapper<ApplicationAgent, ApplicationAgentDto> {

    @Override
    @Mapping(target = "applicationId", source = "application.id")
    ApplicationAgentDto map(ApplicationAgent applicationAgent);

    @Override
    @Mapping(source = "applicationId", target = "application.id")
    ApplicationAgent unMap(ApplicationAgentDto applicationAgentDto);


    @AfterMapping
    default void afterUnMapDtoToEntity(@MappingTarget  ApplicationAgent applicationAgent, ApplicationAgentDto applicationAgentDto) {
        if (applicationAgentDto.getApplicationId() == null) {
            applicationAgent.setApplication(null);
        }

    }

    @AfterMapping
    default void afterMapDtoToEntity(ApplicationAgent applicationAgent,  @MappingTarget  ApplicationAgentDto applicationAgentDto) {

    }


}
