package gov.saip.applicationservice.common.mapper;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationRelevantmapDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantRequestsDto;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import org.mapstruct.Mapper;

@Mapper()
public interface ApplicationRelvantTypeMapper extends BaseMapper<ApplicationRelevantType, ApplicationRelevantmapDto> {

//   ApplicationRelevantType mapRequestToEntity(ApplicationRelevantRequestsDto dto);





}