package gov.saip.applicationservice.common.mapper.veena;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationDto;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationRequestDto;
import gov.saip.applicationservice.common.model.veena.ApplicationVeenaClassification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {LKVeenaClassificationMapper.class, LKVeenaDepartmentMapper.class, LKVeenaAssistantDepartmentMapper.class})
public interface ApplicationVeenaClassificationMapper extends BaseMapper<ApplicationVeenaClassification, ApplicationVeenaClassificationRequestDto> {

    @Override
    @Mapping(source = "applicationId", target = "application.id")
    @Mapping(source = "veenaClassificationId", target = "veenaClassification.id")
    @Mapping(source = "veenaDepartmentId", target = "veenaDepartment.id")
    @Mapping(source = "veenaAssistantDepartmentId", target = "veenaAssistantDepartment.id")
    ApplicationVeenaClassification unMap(ApplicationVeenaClassificationRequestDto dto);


    ApplicationVeenaClassificationDto mapToApplicationVeenaClassificationDto(ApplicationVeenaClassification entity);
}
