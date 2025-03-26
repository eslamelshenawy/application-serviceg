package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationPriorityDto;
import gov.saip.applicationservice.common.dto.ApplicationPriorityListDto;
import gov.saip.applicationservice.common.dto.ApplicationPriorityResponseDto;
import gov.saip.applicationservice.common.model.ApplicationPriority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface ApplicationPriorityMapper extends BaseMapper<ApplicationPriority, ApplicationPriorityListDto> {




//    ApplicationPriorityListDto mapToResponse(ApplicationPriority applicationPriority);
//
//    List<ApplicationPriorityListDto> mapToResponse(List<ApplicationPriority> applicationPriority);


}
