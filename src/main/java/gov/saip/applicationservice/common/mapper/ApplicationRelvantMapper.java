package gov.saip.applicationservice.common.mapper;


import gov.saip.applicationservice.common.dto.ApplicationRelevantmapDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantRequestsDto;
import gov.saip.applicationservice.common.model.ApplicationRelevant;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper()
public interface ApplicationRelvantMapper extends BaseMapper<ApplicationRelevant, ApplicationRelevantmapDto> {

    ApplicationRelevant mapRequestToEntity(ApplicationRelevantRequestsDto applicationRelevantRequestsDto);


   ApplicationRelevant mapRequestToEntity(@MappingTarget ApplicationRelevant applicationRelevant1, ApplicationRelevant applicationRelevant2);


}